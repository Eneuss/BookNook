package com.booknook.dao;

import com.booknook.entity.User;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class UserAccountsTest extends DatabaseTest {
    private final UserDAO userDAO = new UserDAO();

    @Test
    public void seededAdminCanLogIn() throws SQLException {
        User admin = userDAO.authenticateUser("admin", "admin123");
        assertNotNull(admin);
        assertEquals("admin", admin.getRole());
    }

    @Test
    public void registeredUserCanLogIn() throws SQLException {
        userDAO.registerUser(new User("alice", "alice@example.com", "secret1", "user"));

        User user = userDAO.authenticateUser("alice", "secret1");
        assertNotNull(user);
        assertEquals("user", user.getRole());
        assertEquals("alice@example.com", user.getEmail());
    }

    @Test
    public void wrongPasswordIsRejected() throws SQLException {
        assertNull(userDAO.authenticateUser("johnDoe", "wrong"));
        assertNull(userDAO.authenticateUser("nobody", "password123"));
    }

    @Test
    public void usernameExistenceCheck() throws SQLException {
        assertTrue(userDAO.doesUsernameExist("johnDoe"));
        assertFalse(userDAO.doesUsernameExist("alice"));
    }

    @Test
    public void adminListsOnlyRegularUsers() throws SQLException {
        assertEquals(1, userDAO.getAllRegularUsers().size());
        assertEquals("johnDoe", userDAO.getAllRegularUsers().get(0).getUsername());
    }

    @Test
    public void passwordsAreStoredAsBcryptHashes() throws SQLException {
        userDAO.registerUser(new User("bob", "bob@example.com", "secret2", "user"));

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT password FROM Users WHERE username = 'bob'")) {
            assertTrue(rs.next());
            assertTrue(rs.getString("password").startsWith("$2a$"));
        }
        assertNull(userDAO.authenticateUser("bob", "secret2").getPassword(), "hash must not leave the DAO");
    }

    @Test
    public void blankPasswordOnUpdateKeepsCurrentPassword() throws SQLException {
        assertTrue(userDAO.updateUser(2, "johnDoe", "new@example.com", ""));

        assertNotNull(userDAO.authenticateUser("johnDoe", "password123"));
        assertEquals("new@example.com", userDAO.getUserById(2).getEmail());
    }

    @Test
    public void updateAndDeleteUser() throws SQLException {
        assertTrue(userDAO.updateUser(2, "johnny", "johnny@example.com", "newpass1"));
        assertNotNull(userDAO.authenticateUser("johnny", "newpass1"));

        userDAO.deleteUser(2);
        assertNull(userDAO.getUserById(2));
    }
}
