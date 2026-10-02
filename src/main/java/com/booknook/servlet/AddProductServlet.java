package com.booknook.servlet;

import com.booknook.dao.BookDAO;
import com.booknook.dao.AccessoryDAO;
import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/AddProductServlet")
public class AddProductServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final Logger LOG = Logger.getLogger(AddProductServlet.class.getName());
    private final BookDAO bookDAO = new BookDAO();
    private final AccessoryDAO accessoryDAO = new AccessoryDAO();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String productType = request.getParameter("productType");
        String productName = request.getParameter("productName");
        double price = Double.parseDouble(request.getParameter("price"));
        int stock = Integer.parseInt(request.getParameter("stock"));

        try {
            if ("book".equals(productType)) {
                String author = request.getParameter("author");
                int categoryId = Integer.parseInt(request.getParameter("categoryId"));
                bookDAO.addBook(productName, author, price, stock, categoryId);
            } else {
                accessoryDAO.addAccessory(productName, price, stock);
            }
        } catch (SQLException e) {
            LOG.log(Level.WARNING, "Could not add product", e);
        }

        response.sendRedirect("ManageProductsServlet"); //refresh product list
    }
}
