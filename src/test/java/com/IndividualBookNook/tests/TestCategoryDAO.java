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
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

public class TestCategoryDAO {

    private final CategoryDAO categoryDAO = new CategoryDAO();

    @Test
    public void testRetrieveCategories() throws SQLException {
        List<Category> categories = categoryDAO.getAllCategoriesWithId();

        //ensure the category list is not null and has elements
        assertNotNull(categories, "Category list should not be null.");
        assertFalse(categories.isEmpty(), "Category list should not be empty.");
        
        //print retrieved categories for debugging
        System.out.println("Total Categories Retrieved: " + categories.size());
        for (Category category : categories) {
            System.out.println(category.getId() + " - " + category.getName());
        }
    }
}