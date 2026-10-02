package com.booknook.servlet;

import com.booknook.dao.CategoryDAO;
import java.io.IOException;
import java.sql.SQLException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/AddCategoryServlet")
public class AddCategoryServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final CategoryDAO categoryDAO = new CategoryDAO();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String categoryName = request.getParameter("categoryName");

        try {
            categoryDAO.addCategory(categoryName);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        response.sendRedirect("ManageCategoriesServlet"); //refresh category list
    }
}
