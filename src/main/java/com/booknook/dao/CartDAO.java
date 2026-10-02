package com.booknook.dao;

import com.booknook.entity.Cart;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CartDAO {

    //retrieve all cart items for a user
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

    //remove an item from the cart
    public void removeFromCart(int cartId) throws SQLException {
        String sql = "DELETE FROM Cart WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, cartId);
            stmt.executeUpdate();
        }
    }

    //clear all cart items after checkout
    public void clearCart(int userId) throws SQLException {
        String sql = "DELETE FROM Cart WHERE user_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            stmt.executeUpdate();
        }
    }
    
    //calculate the total cart price
    public double calculateTotalCartPrice(int userId) throws SQLException {
        double totalPrice = 0;

        //calculate total price for books
        String bookQuery = "SELECT SUM(c.quantity * b.price) AS total FROM Cart c " +
                           "JOIN Books b ON c.item_id = b.id WHERE c.user_id = ? AND c.item_type = 'book'";

        //calculate total price for accessories
        String accessoryQuery = "SELECT SUM(c.quantity * a.price) AS total FROM Cart c " +
                                "JOIN Accessories a ON c.item_id = a.id WHERE c.user_id = ? AND c.item_type = 'accessory'";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement bookStmt = conn.prepareStatement(bookQuery);
             PreparedStatement accessoryStmt = conn.prepareStatement(accessoryQuery)) {

            bookStmt.setInt(1, userId);
            accessoryStmt.setInt(1, userId);

            //sum book prices
            try (ResultSet rs = bookStmt.executeQuery()) {
                if (rs.next()) {
                    totalPrice += rs.getDouble("total");
                }
            }

            //sum accessory prices
            try (ResultSet rs = accessoryStmt.executeQuery()) {
                if (rs.next()) {
                    totalPrice += rs.getDouble("total");
                }
            }
        }
        return totalPrice;
    }
    
    
    
    //add item to cart or update quantity if already present
    public void addOrUpdateCartItem(int userId, int itemId, String itemType) throws SQLException {
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


