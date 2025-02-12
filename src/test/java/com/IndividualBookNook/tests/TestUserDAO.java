/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.IndividualBookNook.tests;

/**
 *
 * @author Admin
 */

import dao.UserDAO;
import entity.User;
import java.sql.SQLException;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

public class TestUserDAO {

    private static final UserDAO userDAO = new UserDAO();

    @Test
    public void testAuthenticateUser() throws SQLException {
        User user = userDAO.authenticateUser("admin", "admin123");
        assertNotNull(user, "Authentication should succeed for admin.");
        assertEquals("admin", user.getRole(), "User role should be 'admin'.");
    }
}