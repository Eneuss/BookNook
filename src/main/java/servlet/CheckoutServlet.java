/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package servlets;

import dao.CartDAO;
import dao.OrderDAO;
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

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Integer userId = (Integer) session.getAttribute("userId");

        if (userId == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        try {
            // ✅ Step 1: Create a new order and get order ID
            double totalAmount = cartDAO.calculateTotalCartPrice(userId);
            int orderId = orderDAO.createOrder(userId, totalAmount);

            // ✅ Step 2: Store ordered items in Order_Books and Order_Accessories
            orderDAO.saveOrderItems(userId, orderId);

            // ✅ Step 3: Update stock quantities for purchased items
            orderDAO.updateStockAfterPurchase(userId);

            // ✅ Step 4: Clear the cart after checkout
            cartDAO.clearCart(userId);
            session.removeAttribute("cart");

            // ✅ Step 5: Redirect to order confirmation page
            response.sendRedirect("order-confirmation.jsp");

        } catch (SQLException e) {
            throw new ServletException("Error processing checkout", e);
        }
    }
}
