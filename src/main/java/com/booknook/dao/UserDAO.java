package com.booknook.dao;

import com.booknook.entity.User;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import org.mindrot.jbcrypt.BCrypt;

/**
 * User accounts. Passwords are stored as BCrypt hashes and are never loaded
 * into User objects returned by this class.
 */
public class UserDAO {

    public List<User> getAllRegularUsers() throws SQLException {
        List<User> users = new ArrayList<>();
        String sql = "SELECT id, username, email FROM Users WHERE role = 'user'";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                users.add(new User(
                    rs.getInt("id"),
                    rs.getString("username"),
                    rs.getString("email"),
                    null,
                    "user"
                ));
            }
        }
        return users;
    }

    //update a user; a null or blank password keeps the current one
    public boolean updateUser(int userId, String username, String email, String password) throws SQLException {
        boolean changePassword = password != null && !password.isBlank();
        String sql = changePassword
                ? "UPDATE Users SET username = ?, email = ?, password = ? WHERE id = ?"
                : "UPDATE Users SET username = ?, email = ? WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            int i = 1;
            stmt.setString(i++, username);
            stmt.setString(i++, email);
            if (changePassword) {
                stmt.setString(i++, hash(password));
            }
            stmt.setInt(i, userId);

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        }
    }

    public void deleteUser(int userId) throws SQLException {
        String sql = "DELETE FROM Users WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            stmt.executeUpdate();
        }
    }

    //register a new user
    public void registerUser(User user) throws SQLException {
        String sql = "INSERT INTO Users (username, email, password, role) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getEmail());
            stmt.setString(3, hash(user.getPassword()));
            stmt.setString(4, user.getRole());
            stmt.executeUpdate();
        }
    }

    //check if a username already exists
    public boolean doesUsernameExist(String username) throws SQLException {
        String sql = "SELECT id FROM Users WHERE username = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next(); //returns true if user exists
            }
        }
    }

    //verify user credentials for login
    public User authenticateUser(String username, String password) throws SQLException {
        String sql = "SELECT id, username, email, password, role FROM Users WHERE username = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next() && matches(password, rs.getString("password"))) {
                    return new User(
                            rs.getInt("id"),
                            rs.getString("username"),
                            rs.getString("email"),
                            null,
                            rs.getString("role")
                    );
                }
            }
        }
        return null; //return null if authentication fails
    }

    //retrieve user details by ID
    public User getUserById(int userId) throws SQLException {
        String sql = "SELECT id, username, email FROM Users WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new User(
                        rs.getInt("id"),
                        rs.getString("username"),
                        rs.getString("email"),
                        null,
                        "user"
                    );
                }
            }
        }
        return null; //return null if user is not found
    }

    private static String hash(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    private static boolean matches(String password, String storedHash) {
        if (password == null || storedHash == null) {
            return false;
        }
        try {
            return BCrypt.checkpw(password, storedHash);
        } catch (IllegalArgumentException e) {
            return false; //stored value is not a BCrypt hash
        }
    }
}
