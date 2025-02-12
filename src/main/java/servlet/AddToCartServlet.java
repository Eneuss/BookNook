/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package servlets;

import dao.CartDAO;
import java.io.IOException;
import java.sql.SQLException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/AddToCartServlet")
public class AddToCartServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final CartDAO cartDAO = new CartDAO();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Integer userId = (Integer) session.getAttribute("userId");

        if (userId == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        try {
            int itemId = Integer.parseInt(request.getParameter("productId"));
            String itemType = request.getParameter("productType");
            double price = Double.parseDouble(request.getParameter("productPrice"));

            //add item to the cart or update quantity
            cartDAO.addOrUpdateCartItem(userId, itemId, itemType, price);

            //store a confirmation message in session
            session.setAttribute("cartMessage", "Product added to cart successfully!");

            //redirect back to `search.jsp`
            response.sendRedirect("search.jsp");

        } catch (SQLException | NumberFormatException e) {
            e.printStackTrace();
            response.sendRedirect("search.jsp"); //redirect back to product search in case of error
        }
    }
}
