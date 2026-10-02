package com.IndividualBookNook.tests;

import dao.UserDAO;
import entity.User;
import java.sql.SQLException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TestUserAccounts extends DatabaseTest {
    private final UserDAO userDAO = new UserDAO();

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
    public void updateAndDeleteUser() throws SQLException {
        assertTrue(userDAO.updateUser(2, "johnny", "johnny@example.com", "newpass1"));
        assertNotNull(userDAO.authenticateUser("johnny", "newpass1"));

        userDAO.deleteUser(2);
        assertNull(userDAO.getUserById(2));
    }
}
