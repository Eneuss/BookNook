/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package servlets;

import dao.BookDAO;
import dao.AccessoryDAO;
import entity.Book;
import entity.Accessory;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/ManageProductsServlet")
public class ManageProductsServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final BookDAO bookDAO = new BookDAO();
    private final AccessoryDAO accessoryDAO = new AccessoryDAO();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            List<Book> books = bookDAO.getAllBooks();
            List<Accessory> accessories = accessoryDAO.getAllAccessories();
            request.setAttribute("books", books);
            request.setAttribute("accessories", accessories);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        request.getRequestDispatcher("manage-products.jsp").forward(request, response);
    }
}

