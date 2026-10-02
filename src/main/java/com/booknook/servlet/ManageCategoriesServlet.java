package com.booknook.servlet;

import com.booknook.dao.CategoryDAO;
import com.booknook.entity.Category;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/ManageCategoriesServlet")
public class ManageCategoriesServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final CategoryDAO categoryDAO = new CategoryDAO();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            List<Category> categories = categoryDAO.getAllCategoriesWithId();
            request.setAttribute("categories", categories);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        request.getRequestDispatcher("manage-categories.jsp").forward(request, response);
    }
}
