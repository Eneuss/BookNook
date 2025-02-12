/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.IndividualBookNook.tests;

/**
 *
 * @author Admin
 */

import dao.AccessoryDAO;
import entity.Accessory;
import java.sql.SQLException;
import java.util.List;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

public class TestAccessoryDAO {

    private final AccessoryDAO accessoryDAO = new AccessoryDAO();

    @Test
    public void testGetAllAccessories() throws SQLException {
        List<Accessory> accessories = accessoryDAO.getAllAccessories();
        assertNotNull(accessories, "Accessory list should not be null.");
        assertFalse(accessories.isEmpty(), "Accessory list should not be empty.");
    }
}
