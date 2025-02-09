/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.IndividualBookNook.tests;
import dao.BookDAO;
import entity.Book;
import java.sql.SQLException;
import java.util.List;
/**
 *
 * @author Admin
 */
public class TestRetriveBooks {

    public static void main(String[] args) {
        BookDAO bookDAO = new BookDAO();

        try {
            List<Book> books = bookDAO.getAllBooks();
            System.out.println("Total Books Retrieved: " + books.size());

            for (Book book : books) {
                System.out.println(book.getId() + " - " + book.getTitle() + " by " + book.getAuthor());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

