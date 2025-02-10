/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Admin
 */
package com.IndividualBookNook.tests;

import dao.CategoryDAO;
import entity.Category;
import java.sql.SQLException;
import java.util.List;

public class TestCategoryDAO {
    public static void main(String[] args) {
        CategoryDAO categoryDAO = new CategoryDAO();

        try {
            

            // Retrieve all categories
            List<Category> categories = categoryDAO.getAllCategoriesWithId();
            System.out.println("Categories in the database:");
            for (Category category : categories) {
                System.out.println(category.getId() + ": " + category.getName());
            }

            // Retrieve category name by ID
            
            

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

