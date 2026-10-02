package com.booknook.servlet;

import com.booknook.dao.CheckoutException;
import com.booknook.dao.OrderDAO;
import java.io.IOException;
import java.sql.SQLException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/CheckoutServlet")
public class CheckoutServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final OrderDAO orderDAO = new OrderDAO();

    //checkout only happens via POST from the cart page
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.sendRedirect("cart.jsp");
    }

    //handle POST requests process checkout
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Integer userId = (Integer) session.getAttribute("userId");

        if (userId == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        try {
            //create the order, save its items, update stock and clear the cart in one transaction
            orderDAO.checkout(userId);
            response.sendRedirect("order-confirmation.jsp");

        } catch (CheckoutException e) {
            //show the reason on the cart page; nothing was saved
            session.setAttribute("cartError", e.getMessage());
            response.sendRedirect("cart.jsp");
        } catch (SQLException e) {
            throw new ServletException("Error processing checkout", e);
        }
    }
}

