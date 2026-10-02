package com.booknook.servlet;

import com.booknook.dao.UserDAO;
import com.booknook.entity.User;
import java.io.IOException;
import java.sql.SQLException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/EditUserServlet")
public class EditUserServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final UserDAO userDAO = new UserDAO();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            int userId = Integer.parseInt(request.getParameter("id"));
            String username = request.getParameter("username");
            String email = request.getParameter("email");
            String password = request.getParameter("password");

            userDAO.updateUser(userId, username, email, password);
            response.sendRedirect("ManageUsersServlet");

        } catch (SQLException | NumberFormatException e) {
            e.printStackTrace();
            response.sendRedirect("ManageUsersServlet");
        }
    }
}