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

@WebServlet("/DeleteProductServlet")
public class DeleteProductServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final Logger LOG = Logger.getLogger(DeleteProductServlet.class.getName());
    private final BookDAO bookDAO = new BookDAO();
    private final AccessoryDAO accessoryDAO = new AccessoryDAO();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            int productId = Integer.parseInt(request.getParameter("id"));
            String productType = request.getParameter("type");

            if ("book".equals(productType)) {
                bookDAO.deleteBook(productId);
            } else {
                accessoryDAO.deleteAccessory(productId);
            }
        } catch (SQLException e) {
            LOG.log(Level.WARNING, "Could not delete product", e);
        }

        response.sendRedirect("ManageProductsServlet"); //refresh product list
    }
}
