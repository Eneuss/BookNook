/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package servlets;

import dao.UserDAO;
import dao.CartDAO;
import entity.User;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
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
    private final CartDAO cartDAO = new CartDAO();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        try {
            User user = userDAO.authenticateUser(username, password);
            if (user != null) {
                HttpSession session = request.getSession(true); // ✅ Ensure session is created

                session.setAttribute("userId", user.getId()); // ✅ Store user ID
                session.setAttribute("username", user.getUsername());
                session.setAttribute("role", user.getRole()); // ✅ Store role

                // ✅ Debugging
                System.out.println("✅ User ID set: " + session.getAttribute("userId"));
                System.out.println("✅ Role set: " + session.getAttribute("role"));

                // ✅ Load user's cart from database
                List<Object> cart = cartDAO.getUserCart(user.getId());
                session.setAttribute("cart", cart);

                // ✅ Redirect to correct dashboard
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




/*
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
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        UserDAO userDAO = new UserDAO();
        try {
            User user = userDAO.authenticateUser(username, password);
            if (user != null) {
                // Store user details in session
                HttpSession session = request.getSession();
                session.setAttribute("user", user);
                session.setAttribute("username", user.getUsername());
                session.setAttribute("role", user.getRole());

                // Redirect based on role
                if ("admin".equals(user.getRole())) {
                    response.sendRedirect("admin-dashboard.jsp");
                } else {
                    response.sendRedirect("user-dashboard.jsp");
                }
            } else {
                // Login failed, redirect back to login page with error message
                request.setAttribute("errorMessage", "Invalid username or password.");
                request.getRequestDispatcher("login.jsp").forward(request, response);
            }
        } catch (SQLException e) {
            throw new ServletException("Database error during login", e);
        }
    }
}
*/
