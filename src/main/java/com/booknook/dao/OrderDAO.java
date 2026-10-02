package com.booknook.dao;

import com.booknook.entity.Cart;
import com.booknook.entity.Order;
import com.booknook.entity.OrderItem;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderDAO {

    private final CartDAO cartDAO = new CartDAO();

    /**
     * Turns the user's cart into an order in a single transaction: creates the order,
     * copies the cart lines with their current prices, reduces stock and empties the cart.
     * Nothing is saved if any step fails.
     *
     * @return the new order ID
     * @throws CheckoutException if the cart is empty or an item does not have enough stock
     */
    public int checkout(int userId) throws SQLException, CheckoutException {
        try (Connection conn = DatabaseConnection.getConnection()) {
            conn.setAutoCommit(false);
            try {
                List<Cart> items = cartDAO.getCartItems(conn, userId);
                if (items.isEmpty()) {
                    throw new CheckoutException("Your cart is empty.");
                }
                double total = cartDAO.calculateTotalCartPrice(conn, userId);
                int orderId = createOrder(conn, userId, total);
                saveOrderItems(conn, userId, orderId);
                reduceStock(conn, items);
                cartDAO.clearCart(conn, userId);
                conn.commit();
                return orderId;
            } catch (SQLException | CheckoutException | RuntimeException e) {
                conn.rollback();
                throw e;
            }
        }
    }

    // create a new order and return the order ID
    private int createOrder(Connection conn, int userId, double totalAmount) throws SQLException {
        String sql = "INSERT INTO Orders (user_id, total_price) VALUES (?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, userId);
            stmt.setDouble(2, totalAmount);
            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getInt(1); // return generated order ID
                }
            }
        }
        throw new SQLException("Failed to create order.");
    }

    // save books and accessories in Order_Books and Order_Accessories
    private void saveOrderItems(Connection conn, int userId, int orderId) throws SQLException {
        String bookSql = "INSERT INTO Order_Books (order_id, book_id, quantity, price_at_purchase) "
                + "SELECT ?, c.item_id, c.quantity, b.price FROM Cart c "
                + "JOIN Books b ON c.item_id = b.id "
                + "WHERE c.user_id = ? AND c.item_type = 'book'";

        String accessorySql = "INSERT INTO Order_Accessories (order_id, accessory_id, quantity, price_at_purchase) "
                + "SELECT ?, c.item_id, c.quantity, a.price FROM Cart c "
                + "JOIN Accessories a ON c.item_id = a.id "
                + "WHERE c.user_id = ? AND c.item_type = 'accessory'";

        for (String sql : new String[] {bookSql, accessorySql}) {
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, orderId);
                stmt.setInt(2, userId);
                stmt.executeUpdate();
            }
        }
    }

    // reduce stock for each cart line, failing if any product has too little left
    private void reduceStock(Connection conn, List<Cart> items) throws SQLException, CheckoutException {
        String bookSql = "UPDATE Books SET stock = stock - ? WHERE id = ? AND stock >= ?";
        String accessorySql = "UPDATE Accessories SET stock = stock - ? WHERE id = ? AND stock >= ?";

        for (Cart item : items) {
            String sql = "book".equals(item.getItemType()) ? bookSql : accessorySql;
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, item.getQuantity());
                stmt.setInt(2, item.getItemId());
                stmt.setInt(3, item.getQuantity());
                if (stmt.executeUpdate() == 0) {
                    throw new CheckoutException("Not enough stock for one of the items in your cart.");
                }
            }
        }
    }

    // retrieve all orders for a user
    public List<Order> getUserOrders(int userId) throws SQLException {
        List<Order> orders = new ArrayList<>();
        String sql = "SELECT id, order_date, total_price FROM Orders WHERE user_id = ? ORDER BY order_date DESC";

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    int orderId = rs.getInt("id");
                    String orderDate = rs.getString("order_date");
                    double totalPrice = rs.getDouble("total_price");

                    // fetch books & accessories for this order
                    List<OrderItem> items = getOrderItems(orderId, conn);

                    orders.add(new Order(orderId, userId, orderDate, totalPrice, items));
                }
            }
        }
        return orders;
    }

    // retrieve books & accessories for an order
    private List<OrderItem> getOrderItems(int orderId, Connection conn) throws SQLException {
        List<OrderItem> items = new ArrayList<>();
        String sql = "SELECT 'book' AS type, b.title, ob.quantity, ob.price_at_purchase FROM Order_Books ob "
                + "JOIN Books b ON ob.book_id = b.id WHERE ob.order_id = ? "
                + "UNION "
                + "SELECT 'accessory' AS type, a.name, oa.quantity, oa.price_at_purchase FROM Order_Accessories oa "
                + "JOIN Accessories a ON oa.accessory_id = a.id WHERE oa.order_id = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, orderId);
            stmt.setInt(2, orderId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    items.add(new OrderItem(
                            rs.getString("type"),
                            rs.getString("title"),
                            rs.getInt("quantity"),
                            rs.getDouble("price_at_purchase")));
                }
            }
        }
        return items;
    }
}
