/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import entity.Book;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookDAO {
    //add a book
    public void addBook(String title, String author, double price, int stock, int categoryId) throws SQLException {
        String sql = "INSERT INTO Books (title, author, price, stock, category_id) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, title);
            stmt.setString(2, author);
            stmt.setDouble(3, price);
            stmt.setInt(4, stock);
            stmt.setInt(5, categoryId);
            stmt.executeUpdate();
        }
    }
    
    //update a book
    public void updateBook(int bookId, String title, String author, double price, int stock, int categoryId) throws SQLException {
        String sql = "UPDATE Books SET title = ?, author = ?, price = ?, stock = ?, category_id = ? WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, title);
            stmt.setString(2, author);
            stmt.setDouble(3, price);
            stmt.setInt(4, stock);
            stmt.setInt(5, categoryId);
            stmt.setInt(6, bookId);
            stmt.executeUpdate();
        }
    }

    //delete a book
    public void deleteBook(int bookId) throws SQLException {
        String sql = "DELETE FROM Books WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, bookId);
            stmt.executeUpdate();
        }
    }

    public List<Book> getAllBooks() throws SQLException {
        List<Book> books = new ArrayList<>();
        String sql = "SELECT * FROM Books";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                books.add(new Book(
                        rs.getInt("id"),
                        rs.getString("title"),
                        rs.getString("author"),
                        rs.getDouble("price"),
                        rs.getInt("stock"),
                        rs.getInt("category_id")
                ));
            }
        }
        return books;
    }
    
    //retrieve a book by ID
    public Book getBookById(int bookId) throws SQLException {
        String sql = "SELECT id, title, author, price, stock, category_id FROM Books WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, bookId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Book(
                        rs.getInt("id"),
                        rs.getString("title"),
                        rs.getString("author"),
                        rs.getDouble("price"),
                        rs.getInt("stock"),
                        rs.getInt("category_id")
                    );
                }
            }
        }
        return null;
    }
    
    
    // Search for books by title or author
    public List<Book> searchBooks(String query) throws SQLException {
        List<Book> books = new ArrayList<>();
        String sql = "SELECT * FROM Books WHERE title LIKE ? OR author LIKE ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, "%" + query + "%");
            stmt.setString(2, "%" + query + "%");
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                books.add(new Book(
                        rs.getInt("id"),
                        rs.getString("title"),
                        rs.getString("author"),
                        rs.getDouble("price"),
                        rs.getInt("stock"),
                        rs.getInt("category_id")
                ));
            }
        }
        return books;
    }
    
}
