package com.booknook.servlet;

import com.booknook.dao.BookDAO;
import com.booknook.dao.AccessoryDAO;
import com.booknook.entity.Book;
import com.booknook.entity.Accessory;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/ManageProductsServlet")
public class ManageProductsServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final Logger LOG = Logger.getLogger(ManageProductsServlet.class.getName());
    private final BookDAO bookDAO = new BookDAO();
    private final AccessoryDAO accessoryDAO = new AccessoryDAO();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            List<Book> books = bookDAO.getAllBooks();
            List<Accessory> accessories = accessoryDAO.getAllAccessories();
            request.setAttribute("books", books);
            request.setAttribute("accessories", accessories);
        } catch (SQLException e) {
            LOG.log(Level.WARNING, "Could not load products", e);
        }

        request.getRequestDispatcher("manage-products.jsp").forward(request, response);
    }
}

