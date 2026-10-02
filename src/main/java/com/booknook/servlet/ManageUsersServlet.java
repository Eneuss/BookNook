package com.booknook.servlet;

import com.booknook.dao.UserDAO;
import com.booknook.entity.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet("/ManageUsersServlet")
public class ManageUsersServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final Logger LOG = Logger.getLogger(ManageUsersServlet.class.getName());
    private final UserDAO userDAO = new UserDAO();

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            List<User> users = userDAO.getAllRegularUsers();
            request.setAttribute("users", users); // store users in the session
        } catch (SQLException e) {
            LOG.log(Level.WARNING, "Could not load users", e);
        }

        request.getRequestDispatcher("manage-users.jsp").forward(request, response); // forward to jsp
    }
}
