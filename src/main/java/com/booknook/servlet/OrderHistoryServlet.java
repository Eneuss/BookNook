package com.booknook.servlet;

import com.booknook.dao.OrderDAO;
import com.booknook.entity.Order;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.HttpServletRequest;

@WebServlet("/OrderHistoryServlet")
public class OrderHistoryServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final OrderDAO orderDAO = new OrderDAO();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Integer userId = (Integer) session.getAttribute("userId");

        if (userId == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        try {
            List<Order> orders = orderDAO.getUserOrders(userId);

            //store orders in session (same method as cart and search)
            session.setAttribute("orders", orders);

            response.sendRedirect("order-history.jsp"); //redirect instead of forwarding
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendRedirect("user-dashboard.jsp");
        }
    }
}