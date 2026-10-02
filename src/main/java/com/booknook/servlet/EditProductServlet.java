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

@WebServlet("/EditProductServlet")
public class EditProductServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final Logger LOG = Logger.getLogger(EditProductServlet.class.getName());
    private final BookDAO bookDAO = new BookDAO();
    private final AccessoryDAO accessoryDAO = new AccessoryDAO();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            int productId = Integer.parseInt(request.getParameter("id"));
            String productType = request.getParameter("productType");
            String productName = request.getParameter("productName");
            double price = Double.parseDouble(request.getParameter("price"));
            int stock = Integer.parseInt(request.getParameter("stock"));

            if ("book".equals(productType)) {
                String author = request.getParameter("author");
                int categoryId = Integer.parseInt(request.getParameter("categoryId"));
                bookDAO.updateBook(productId, productName, author, price, stock, categoryId);
            } else {
                accessoryDAO.updateAccessory(productId, productName, price, stock);
            }

            response.sendRedirect("ManageProductsServlet"); //refresh product list

        } catch (SQLException | NumberFormatException e) {
            LOG.log(Level.WARNING, "Could not update product", e);
            response.sendRedirect("ManageProductsServlet"); //redirect in case of error
        }
    }
}
