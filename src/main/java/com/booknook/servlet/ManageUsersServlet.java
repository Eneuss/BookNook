package com.booknook.servlet;

import com.booknook.dao.UserDAO;
import com.booknook.entity.User;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/ManageUsersServlet")
public class ManageUsersServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final UserDAO userDAO = new UserDAO();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            List<User> users = userDAO.getAllRegularUsers();
            request.setAttribute("users", users); //store users in the session
        } catch (SQLException e) {
            e.printStackTrace();
        }

        request.getRequestDispatcher("manage-users.jsp").forward(request, response); //forward to jsp
    }
}
