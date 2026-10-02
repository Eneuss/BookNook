package com.booknook.servlet;

import com.booknook.dao.CartDAO;
import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/AddToCartServlet")
public class AddToCartServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final Logger LOG = Logger.getLogger(AddToCartServlet.class.getName());
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

            //add item to the cart or update quantity
            cartDAO.addOrUpdateCartItem(userId, itemId, itemType);

            //redirect back to `search.jsp`
            response.sendRedirect("search.jsp");

        } catch (SQLException | NumberFormatException e) {
            LOG.log(Level.WARNING, "Could not add item to cart", e);
            response.sendRedirect("search.jsp"); //redirect back to product search in case of error
        }
    }
}
