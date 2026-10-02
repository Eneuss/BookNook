package com.booknook.servlet;

import com.booknook.dao.CategoryDAO;
import com.booknook.entity.Category;
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

@WebServlet("/ManageCategoriesServlet")
public class ManageCategoriesServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final Logger LOG = Logger.getLogger(ManageCategoriesServlet.class.getName());
    private final CategoryDAO categoryDAO = new CategoryDAO();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            List<Category> categories = categoryDAO.getAllCategoriesWithId();
            request.setAttribute("categories", categories);
        } catch (SQLException e) {
            LOG.log(Level.WARNING, "Could not load categories", e);
        }

        request.getRequestDispatcher("manage-categories.jsp").forward(request, response);
    }
}
