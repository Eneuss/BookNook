package com.booknook.dao;

import com.booknook.entity.Order;
import com.booknook.entity.OrderItem;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderDAO {

    //create a new order and return the order ID
    public int createOrder(int userId, double totalAmount) throws SQLException {
        String sql = "INSERT INTO Orders (user_id, total_price) VALUES (?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, userId);
            stmt.setDouble(2, totalAmount);
            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getInt(1); //return generated order ID
                }
            }
        }
        throw new SQLException("Failed to create order.");
    }

    //save books and accessories in Order_Books and Order_Accessories
     public void saveOrderItems(int userId, int orderId) throws SQLException {
        String bookSql = "INSERT INTO Order_Books (order_id, book_id, quantity, price_at_purchase) " +
                         "SELECT ?, c.item_id, c.quantity, b.price FROM Cart c " +
                         "JOIN Books b ON c.item_id = b.id " +
                         "WHERE c.user_id = ? AND c.item_type = 'book'";

        String accessorySql = "INSERT INTO Order_Accessories (order_id, accessory_id, quantity, price_at_purchase) " +
                              "SELECT ?, c.item_id, c.quantity, a.price FROM Cart c " +
                              "JOIN Accessories a ON c.item_id = a.id " +
                              "WHERE c.user_id = ? AND c.item_type = 'accessory'";

        try (Connection conn = DatabaseConnection.getConnection()) {
            try (PreparedStatement stmt = conn.prepareStatement(bookSql)) {
                stmt.setInt(1, orderId);
                stmt.setInt(2, userId);
                stmt.executeUpdate();
            }

            try (PreparedStatement stmt = conn.prepareStatement(accessorySql)) {
                stmt.setInt(1, orderId);
                stmt.setInt(2, userId);
                stmt.executeUpdate();
            }
        }
    }

    //update stock quantities after purchase
    public void updateStockAfterPurchase(int userId) throws SQLException {
        String bookStockSql = "UPDATE Books SET stock = stock - (SELECT quantity FROM Cart "
                            + "WHERE user_id = ? AND item_type = 'book' AND Books.id = Cart.item_id) "
                            + "WHERE id IN (SELECT item_id FROM Cart WHERE user_id = ? AND item_type = 'book')";

        String accessoryStockSql = "UPDATE Accessories SET stock = stock - (SELECT quantity FROM Cart "
                                 + "WHERE user_id = ? AND item_type = 'accessory' AND Accessories.id = Cart.item_id) "
                                 + "WHERE id IN (SELECT item_id FROM Cart WHERE user_id = ? AND item_type = 'accessory')";

        try (Connection conn = DatabaseConnection.getConnection()) {
            try (PreparedStatement stmt = conn.prepareStatement(bookStockSql)) {
                stmt.setInt(1, userId);
                stmt.setInt(2, userId);
                stmt.executeUpdate();
            }

            try (PreparedStatement stmt = conn.prepareStatement(accessoryStockSql)) {
                stmt.setInt(1, userId);
                stmt.setInt(2, userId);
                stmt.executeUpdate();
            }
        }
    }
    
    
    //retrieve all orders for a user
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
                    
                    //fetch books & accessories for this order
                    List<OrderItem> items = getOrderItems(orderId, conn);

                    orders.add(new Order(orderId, userId, orderDate, totalPrice, items));
                }
            }
        }
        return orders;
    }

    //retrieve books & accessories for an order
    private List<OrderItem> getOrderItems(int orderId, Connection conn) throws SQLException {
        List<OrderItem> items = new ArrayList<>();
        String sql = "SELECT 'book' AS type, b.title, ob.quantity, ob.price_at_purchase FROM Order_Books ob " +
                     "JOIN Books b ON ob.book_id = b.id WHERE ob.order_id = ? " +
                     "UNION " +
                     "SELECT 'accessory' AS type, a.name, oa.quantity, oa.price_at_purchase FROM Order_Accessories oa " +
                     "JOIN Accessories a ON oa.accessory_id = a.id WHERE oa.order_id = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, orderId);
            stmt.setInt(2, orderId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    items.add(new OrderItem(
                        rs.getString("type"),
                        rs.getString("title"),
                        rs.getInt("quantity"),
                        rs.getDouble("price_at_purchase")
                    ));
                }
            }
        }
        return items;
    }
    
}

