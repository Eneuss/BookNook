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

    public void addAccessory(Accessory accessory) throws SQLException {
        String sql = "INSERT INTO Accessories (name, price, stock) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, accessory.getName());
            stmt.setDouble(2, accessory.getPrice());
            stmt.setInt(3, accessory.getStock());
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

