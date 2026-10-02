package com.booknook.servlet;

import com.booknook.dao.CartDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet("/RemoveFromCartServlet")
public class RemoveFromCartServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final Logger LOG = Logger.getLogger(RemoveFromCartServlet.class.getName());
    private final CartDAO cartDAO = new CartDAO();

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        Integer userId = (Integer) session.getAttribute("userId");

        if (userId == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        try {
            int cartItemId = Integer.parseInt(request.getParameter("id"));
            cartDAO.removeFromCart(cartItemId, userId);

            response.sendRedirect("cart.jsp");

        } catch (SQLException | NumberFormatException e) {
            LOG.log(Level.WARNING, "Could not remove cart item", e);
            response.sendRedirect("cart.jsp");
        }
    }
}
