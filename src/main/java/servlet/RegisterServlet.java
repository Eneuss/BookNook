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

@WebServlet("/RegisterServlet")
public class RegisterServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String role = request.getParameter("role");

        UserDAO userDAO = new UserDAO();

        try {
            //check if the username is already taken
            if (userDAO.doesUsernameExist(username)) {
                request.setAttribute("errorMessage", "Username already exists. Choose another one.");
                request.getRequestDispatcher("register.jsp").forward(request, response);
                return;
            }

            //register new user
            User newUser = new User(username, email, password, role);
            userDAO.registerUser(newUser);
            
            //redirect to login page
            request.setAttribute("successMessage", "Registration successful! Please log in.");
            request.getRequestDispatcher("login.jsp").forward(request, response);
            
        } catch (SQLException e) {
            throw new ServletException("Database error during registration", e);
        }
    }
}
