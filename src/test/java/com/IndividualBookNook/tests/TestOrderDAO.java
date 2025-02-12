/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.IndividualBookNook.tests;

/**
 *
 * @author Admin
 */
import dao.OrderDAO;
import java.sql.SQLException;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

public class TestOrderDAO {

    private static final OrderDAO orderDAO = new OrderDAO();

    @BeforeEach
    public void setUp() throws SQLException {
        orderDAO.deleteOrder(1); // Remove test orders before each test
    }

    @Test
    public void testCreateOrder() throws SQLException {
        int orderId = orderDAO.createOrder(1, 50.00); // Create order for user 1
        assertTrue(orderId > 0, "Order ID should be a positive integer.");
    }
}