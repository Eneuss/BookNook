package com.booknook.servlet;

import com.booknook.dao.CartDAO;
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
    private final CartDAO cartDAO = new CartDAO();
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
            //Calculate total price and create an order
            double totalAmount = cartDAO.calculateTotalCartPrice(userId);
            int orderId = orderDAO.createOrder(userId, totalAmount);

            //store ordered items in `Order_Books` and `Order_Accessories`
            orderDAO.saveOrderItems(userId, orderId);

            //update stock quantities
            orderDAO.updateStockAfterPurchase(userId);

            //clear the cart after checkout
            cartDAO.clearCart(userId);

            //redirect to confirmation page
            response.sendRedirect("order-confirmation.jsp");

        } catch (SQLException e) {
            throw new ServletException("Error processing checkout", e);
        }
    }
}

