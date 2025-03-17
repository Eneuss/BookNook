/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package servlets;

import dao.UserDAO;
import entity.User;
import java.io.IOException;
import java.sql.SQLException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final UserDAO userDAO = new UserDAO();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        try {
            User user = userDAO.authenticateUser(username, password);
            if (user != null) {
                HttpSession session = request.getSession(true);

                session.setAttribute("userId", user.getId());
                session.setAttribute("username", user.getUsername());
                session.setAttribute("role", user.getRole());

                //redirect to correct dashboard
                if ("admin".equals(user.getRole())) {
                    response.sendRedirect("admin-dashboard.jsp");
                } else {
                    response.sendRedirect("user-dashboard.jsp");
                }
                return;
            }
        } catch (SQLException e) {
            throw new ServletException("Database error during login", e);
        }

        request.setAttribute("errorMessage", "Invalid username or password.");
        request.getRequestDispatcher("login.jsp").forward(request, response);
    }
}

