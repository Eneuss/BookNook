/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import entity.Accessory;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AccessoryDAO {

    // adds an accessory
    public void addAccessory(String name, double price, int stock) throws SQLException {
        String sql = "INSERT INTO Accessories (name, price, stock) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, name);
            stmt.setDouble(2, price);
            stmt.setInt(3, stock);
            stmt.executeUpdate();
        }
    }
    
     //update an accessory
    public void updateAccessory(int accessoryId, String name, double price, int stock) throws SQLException {
        String sql = "UPDATE Accessories SET name = ?, price = ?, stock = ? WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, name);
            stmt.setDouble(2, price);
            stmt.setInt(3, stock);
            stmt.setInt(4, accessoryId);
            stmt.executeUpdate();
        }
    }

    //delete an accessory
    public void deleteAccessory(int accessoryId) throws SQLException {
        String sql = "DELETE FROM Accessories WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, accessoryId);
            stmt.executeUpdate();
        }
    }

    public List<Accessory> getAllAccessories() throws SQLException {
        List<Accessory> accessories = new ArrayList<>();
        String sql = "SELECT * FROM Accessories";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                accessories.add(new Accessory(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getDouble("price"),
                        rs.getInt("stock")
                ));
            }
        }
        return accessories;
    }
    
    
    //retrieve an accessory by ID
    public Accessory getAccessoryById(int accessoryId) throws SQLException {
        String sql = "SELECT id, name, price, stock FROM Accessories WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, accessoryId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Accessory(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getDouble("price"),
                        rs.getInt("stock")
                    );
                }
            }
        }
        return null;
    }
    
    
     // Search for accessories by name
    public List<Accessory> searchAccessories(String query) throws SQLException {
        List<Accessory> accessories = new ArrayList<>();
        String sql = "SELECT * FROM Accessories WHERE name LIKE ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, "%" + query + "%");
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                accessories.add(new Accessory(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getDouble("price"),
                        rs.getInt("stock")
                ));
            }
        }
        return accessories;
    }
}

