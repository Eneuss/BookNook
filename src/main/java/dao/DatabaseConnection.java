/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String URL = "jdbc:sqlite:C:/Users/Admin/Desktop/prov.db";  

    public static Connection getConnection() throws SQLException {
        try {
            Class.forName("org.sqlite.JDBC");  // ✅ Manually load SQLite driver
            return DriverManager.getConnection(URL);
        } catch (ClassNotFoundException e) {
            throw new SQLException("SQLite JDBC Driver not found.", e);
        }
    }
}
