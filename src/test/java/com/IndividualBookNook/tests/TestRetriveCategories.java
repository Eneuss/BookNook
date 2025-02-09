/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.IndividualBookNook.tests;

/**
 *
 * @author Admin
 */
import dao.CategoryDAO;
import entity.Category;
import java.sql.SQLException;
import java.util.List;

public class TestRetriveCategories {

    public static void main(String[] args) {
        CategoryDAO categoryDAO = new CategoryDAO();

        try {
            List<Category> categories = categoryDAO.getAllCategoriesWithId();
            System.out.println("Total Categories Retrieved: " + categories.size());

            for (Category category : categories) {
                System.out.println(category.getId() + " - " + category.getName());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}