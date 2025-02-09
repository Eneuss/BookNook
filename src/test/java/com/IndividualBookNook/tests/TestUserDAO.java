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
import java.util.List;

public class TestUserDAO {
    public static void main(String[] args) {
        UserDAO userDAO = new UserDAO();

        try {
            // Register test users
            userDAO.registerUser(new User("admin", "admin@email.com", "admin123", "admin"));
            userDAO.registerUser(new User("johnDoe", "john@email.com", "password123", "user"));
            userDAO.registerUser(new User("janeDoe", "jane@email.com", "password456", "user"));

            // Check if username exists
            System.out.println("Does 'johnDoe' exist? " + userDAO.doesUsernameExist("johnDoe")); // Expected: true
            System.out.println("Does 'newUser' exist? " + userDAO.doesUsernameExist("newUser")); // Expected: false

            // Authenticate users
            User authenticatedUser = userDAO.authenticateUser("johnDoe", "password123");
            System.out.println("Authentication for johnDoe: " + (authenticatedUser != null ? "Success" : "Failed"));

            // Retrieve user by ID
            User userById = userDAO.getUserById(1);
            if (userById != null) {
                System.out.println("User ID 1: " + userById.getUsername() + " - " + userById.getRole());
            }

            // Retrieve all users
            List<User> users = userDAO.getAllUsers();
            System.out.println("All users in the database:");
            for (User user : users) {
                System.out.println(user.getId() + ": " + user.getUsername() + " - " + user.getEmail() + " - Role: " + user.getRole());
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
