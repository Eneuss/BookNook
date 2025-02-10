/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.IndividualBookNook.tests;

/**
 *
 * @author Admin
 */

import dao.BookDAO;
import entity.Book;
import java.sql.SQLException;
import java.util.List;

public class TestBookDAO {
    public static void main(String[] args) {
        BookDAO bookDAO = new BookDAO();

        try {
            // Insert test books
            

            // Retrieve all books
            List<Book> books = bookDAO.getAllBooks();
            System.out.println("Books in the database:");
            for (Book book : books) {
                System.out.println(book.getId() + ": " + book.getTitle() + " by " + book.getAuthor() +
                        " - Price: $" + book.getPrice() + " - Stock: " + book.getStock() + 
                        " - Category ID: " + book.getCategoryId());
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

