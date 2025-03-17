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
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

public class TestBookDAO {

    private final BookDAO bookDAO = new BookDAO();

    @Test
    public void testGetAllBooks() throws SQLException {
        List<Book> books = bookDAO.getAllBooks();
        assertNotNull(books, "Book list should not be null.");
        assertFalse(books.isEmpty(), "Book list should not be empty.");
    }
}
