package com.booknook.servlet;

import com.booknook.dao.CategoryDAO;
import java.io.IOException;
import java.sql.SQLException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/DeleteCategoryServlet")
public class DeleteCategoryServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final CategoryDAO categoryDAO = new CategoryDAO();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            int categoryId = Integer.parseInt(request.getParameter("id"));
            categoryDAO.deleteCategory(categoryId); //delete category from DB
        } catch (SQLException e) {
            e.printStackTrace();
        }

        response.sendRedirect("ManageCategoriesServlet"); //refresh category list
    }
}
