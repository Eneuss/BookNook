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

public class TestAccessoryDAO {
    public static void main(String[] args) {
        AccessoryDAO accessoryDAO = new AccessoryDAO();

        try {
            

            // Retrieve all accessories
            List<Accessory> accessories = accessoryDAO.getAllAccessories();
            System.out.println("Accessories in the database:");
            for (Accessory accessory : accessories) {
                System.out.println(accessory.getId() + ": " + accessory.getName() +
                        " - Price: $" + accessory.getPrice() + " - Stock: " + accessory.getStock());
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
