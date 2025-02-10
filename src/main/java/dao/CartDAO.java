/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import entity.Cart;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CartDAO {

    // ✅ Add an item to the cart
    public void addToCart(int userId, int itemId, String itemType, int quantity) throws SQLException {
        String sql = "INSERT INTO Cart (user_id, item_type, item_id, quantity) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            stmt.setString(2, itemType);
            stmt.setInt(3, itemId);
            stmt.setInt(4, quantity);
            stmt.executeUpdate();
        }
    }

    // ✅ Retrieve all cart items for a user
    public List<Cart> getCartItems(int userId) throws SQLException {
        List<Cart> cartItems = new ArrayList<>();
        String sql = "SELECT id, user_id, item_type, item_id, quantity FROM Cart WHERE user_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    cartItems.add(new Cart(
                        rs.getInt("id"),
                        rs.getInt("user_id"),
                        rs.getString("item_type"),
                        rs.getInt("item_id"),
                        rs.getInt("quantity")
                    ));
                }
            }
        }
        return cartItems;
    }

    // ✅ Remove an item from the cart
    public void removeFromCart(int cartId) throws SQLException {
        String sql = "DELETE FROM Cart WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, cartId);
            stmt.executeUpdate();
        }
    }

    // ✅ Clear all cart items for a user after checkout
    public void clearCart(int userId) throws SQLException {
        String sql = "DELETE FROM Cart WHERE user_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            stmt.executeUpdate();
        }
    }
    
    // ✅ Calculate the total cart price for a user
    public double calculateTotalCartPrice(int userId) throws SQLException {
        double totalPrice = 0;

        // ✅ Calculate total price for books
        String bookQuery = "SELECT SUM(c.quantity * b.price) AS total FROM Cart c " +
                           "JOIN Books b ON c.item_id = b.id WHERE c.user_id = ? AND c.item_type = 'book'";

        // ✅ Calculate total price for accessories
        String accessoryQuery = "SELECT SUM(c.quantity * a.price) AS total FROM Cart c " +
                                "JOIN Accessories a ON c.item_id = a.id WHERE c.user_id = ? AND c.item_type = 'accessory'";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement bookStmt = conn.prepareStatement(bookQuery);
             PreparedStatement accessoryStmt = conn.prepareStatement(accessoryQuery)) {

            bookStmt.setInt(1, userId);
            accessoryStmt.setInt(1, userId);

            // ✅ Sum book prices
            try (ResultSet rs = bookStmt.executeQuery()) {
                if (rs.next()) {
                    totalPrice += rs.getDouble("total");
                }
            }

            // ✅ Sum accessory prices
            try (ResultSet rs = accessoryStmt.executeQuery()) {
                if (rs.next()) {
                    totalPrice += rs.getDouble("total");
                }
            }
        }
        return totalPrice;
    }
    
    
    
    //add item to cart or update quantity if already exists
    public void addOrUpdateCartItem(int userId, int itemId, String itemType, double price) throws SQLException {
        String checkSql = "SELECT quantity FROM Cart WHERE user_id = ? AND item_id = ? AND item_type = ?";
        String updateSql = "UPDATE Cart SET quantity = quantity + 1 WHERE user_id = ? AND item_id = ? AND item_type = ?";
        String insertSql = "INSERT INTO Cart (user_id, item_type, item_id, quantity) VALUES (?, ?, ?, 1)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement checkStmt = conn.prepareStatement(checkSql)) {
            checkStmt.setInt(1, userId);
            checkStmt.setInt(2, itemId);
            checkStmt.setString(3, itemType);
            ResultSet rs = checkStmt.executeQuery();

            if (rs.next()) {
                try (PreparedStatement updateStmt = conn.prepareStatement(updateSql)) {
                    updateStmt.setInt(1, userId);
                    updateStmt.setInt(2, itemId);
                    updateStmt.setString(3, itemType);
                    updateStmt.executeUpdate();
                }
            } else {
                try (PreparedStatement insertStmt = conn.prepareStatement(insertSql)) {
                    insertStmt.setInt(1, userId);
                    insertStmt.setString(2, itemType);
                    insertStmt.setInt(3, itemId);
                    insertStmt.executeUpdate();
                }
            }
        }
    }
    
    
}


