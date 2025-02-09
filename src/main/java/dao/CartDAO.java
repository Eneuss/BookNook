/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import entity.Book;
import entity.Accessory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CartDAO {

    // add an item to the cart
    public void addItemToCart(int userId, String itemType, int itemId, int quantity) throws SQLException {
        String sql = "INSERT INTO Cart (user_id, item_type, item_id, quantity) VALUES (?, ?, ?, ?) "
                   + "ON CONFLICT(user_id, item_type, item_id) DO UPDATE SET quantity = quantity + ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            stmt.setString(2, itemType);
            stmt.setInt(3, itemId);
            stmt.setInt(4, quantity);
            stmt.setInt(5, quantity);
            stmt.executeUpdate();
        }
    }

    //retrieve all cart items for a logged user
    public List<Object> getUserCart(int userId) throws SQLException {
        List<Object> cart = new ArrayList<>();
        String sql = "SELECT item_type, item_id, quantity FROM Cart WHERE user_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    String itemType = rs.getString("item_type");
                    int itemId = rs.getInt("item_id");
                    int quantity = rs.getInt("quantity");

                    if ("book".equals(itemType)) {
                        cart.add(new Book(itemId, "Book Title", "", 0, quantity, 0));
                    } else if ("accessory".equals(itemType)) {
                        cart.add(new Accessory(itemId, "Accessory Name", 0, quantity));
                    }
                }
            }
        }
        return cart;
    }

    //remove an item from the cart
    public void removeItemFromCart(int userId, String itemType, int itemId) throws SQLException {
        String sql = "DELETE FROM Cart WHERE user_id = ? AND item_type = ? AND item_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            stmt.setString(2, itemType);
            stmt.setInt(3, itemId);
            stmt.executeUpdate();
        }
    }

    //clear the cart after checkout
    public void clearCart(int userId) throws SQLException {
        String sql = "DELETE FROM Cart WHERE user_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            stmt.executeUpdate();
        }
    }
    
    //calculate total cart price for current user
    public double calculateTotalCartPrice(int userId) throws SQLException {
        double totalPrice = 0.0;
        String sql = "SELECT SUM(CASE WHEN item_type = 'book' THEN (SELECT price FROM Books WHERE id = Cart.item_id) " +
                     "WHEN item_type = 'accessory' THEN (SELECT price FROM Accessories WHERE id = Cart.item_id) END * quantity) " +
                     "AS total_price FROM Cart WHERE user_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    totalPrice = rs.getDouble("total_price");
                }
            }
        }
        return totalPrice;
    }
}

