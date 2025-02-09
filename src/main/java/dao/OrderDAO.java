/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import entity.Order;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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
                    return rs.getInt(1); // Return generated order ID
                }
            }
        }
        throw new SQLException("Failed to create order.");
    }

    //save books and accessories in Order_Books and Order_Accessories
    public void saveOrderItems(int userId, int orderId) throws SQLException {
        String bookSql = "INSERT INTO Order_Books (order_id, book_id, quantity, price_at_purchase) "
                        + "SELECT ?, item_id, quantity, 0 FROM Cart WHERE user_id = ? AND item_type = 'book'";

        String accessorySql = "INSERT INTO Order_Accessories (order_id, accessory_id, quantity, price_at_purchase) "
                            + "SELECT ?, item_id, quantity, 0 FROM Cart WHERE user_id = ? AND item_type = 'accessory'";

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
    
     //retrieve all past orders for current user
    public List<Order> getUserOrders(int userId) throws SQLException {
        List<Order> orders = new ArrayList<>();
        String sql = "SELECT id, order_date, total_price FROM Orders WHERE user_id = ? ORDER BY order_date DESC";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    orders.add(new Order(
                        rs.getInt("id"),
                        userId,
                        rs.getTimestamp("order_date"),
                        rs.getDouble("total_price")
                    ));
                }
            }
        }
        return orders;
    }
}

