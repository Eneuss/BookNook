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

    // ✅ Handle GET requests (Redirect to a checkout confirmation page)
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.sendRedirect("checkout-confirmation.jsp");
    }

    // ✅ Handle POST requests (Process checkout)
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Integer userId = (Integer) session.getAttribute("userId");

        if (userId == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        try {
            // ✅ Step 1: Calculate total price and create an order
            double totalAmount = cartDAO.calculateTotalCartPrice(userId);
            int orderId = orderDAO.createOrder(userId, totalAmount);

            // ✅ Step 2: Store ordered items in `Order_Books` and `Order_Accessories`
            orderDAO.saveOrderItems(userId, orderId);

            // ✅ Step 3: Update stock quantities
            orderDAO.updateStockAfterPurchase(userId);

            // ✅ Step 4: Clear the cart after checkout
            cartDAO.clearCart(userId);
            session.removeAttribute("cart");

            // ✅ Step 5: Ensure order history integration works correctly
            session.setAttribute("lastOrderId", orderId); // Store last order ID for confirmation

            // ✅ Step 6: Redirect to order confirmation page
            response.sendRedirect("order-confirmation.jsp");

        } catch (SQLException e) {
            throw new ServletException("Error processing checkout", e);
        }
    }
}


/*
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

    // ✅ Handle GET requests (Redirect to a checkout confirmation page)
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.sendRedirect("checkout-confirmation.jsp");
    }

    // ✅ Handle POST requests (Process checkout)
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Integer userId = (Integer) session.getAttribute("userId");

        if (userId == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        try {
            // ✅ Step 1: Calculate total price and create an order
            double totalAmount = cartDAO.calculateTotalCartPrice(userId);
            int orderId = orderDAO.createOrder(userId, totalAmount);

            // ✅ Step 2: Store ordered items in `Order_Books` and `Order_Accessories`
            orderDAO.saveOrderItems(userId, orderId);

            // ✅ Step 3: Update stock quantities
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

*/