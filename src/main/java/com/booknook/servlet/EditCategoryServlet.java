package com.booknook.servlet;

import com.booknook.dao.CategoryDAO;
import java.io.IOException;
import java.sql.SQLException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/EditCategoryServlet")
public class EditCategoryServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final CategoryDAO categoryDAO = new CategoryDAO();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            int categoryId = Integer.parseInt(request.getParameter("id"));
            String categoryName = request.getParameter("categoryName");

            categoryDAO.updateCategory(categoryId, categoryName); //update category in database
            response.sendRedirect("ManageCategoriesServlet"); //refresh category list

        } catch (SQLException | NumberFormatException e) {
            e.printStackTrace();
            response.sendRedirect("ManageCategoriesServlet"); //redirect in case of error
        }
    }
}
