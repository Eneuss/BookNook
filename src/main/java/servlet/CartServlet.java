/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package servlets;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import dao.DatabaseConnection;
import entity.Book;
import entity.Accessory;

@WebServlet("/CartServlet")
public class CartServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Integer userId = (Integer) session.getAttribute("userId");

        String productType = request.getParameter("productType");
        int productId = Integer.parseInt(request.getParameter("productId"));
        String productName = request.getParameter("productName");
        double productPrice = Double.parseDouble(request.getParameter("productPrice"));
        int quantity = 1;

        if (userId != null) {
            //store cart item in the database for logged user
            try (Connection conn = DatabaseConnection.getConnection()) {
                String sql = "INSERT INTO Cart (user_id, item_type, item_id, quantity) VALUES (?, ?, ?, ?) "
                           + "ON CONFLICT(user_id, item_type, item_id) DO UPDATE SET quantity = quantity + 1";
                try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                    stmt.setInt(1, userId);
                    stmt.setString(2, productType);
                    stmt.setInt(3, productId);
                    stmt.setInt(4, quantity);
                    stmt.executeUpdate();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            List<Object> cart = (List<Object>) session.getAttribute("cart");
            if (cart == null) {
                cart = new ArrayList<>();
            }

            if ("book".equals(productType)) {
                Book book = new Book(productId, productName, "", productPrice, 1, 0);
                cart.add(book);
            } else if ("accessory".equals(productType)) {
                Accessory accessory = new Accessory(productId, productName, productPrice, 1);
                cart.add(accessory);
            }

            session.setAttribute("cart", cart);
        }

        response.sendRedirect("search.jsp");
    }
}
