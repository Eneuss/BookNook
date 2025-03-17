/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package servlets;

import dao.BookDAO;
import dao.AccessoryDAO;
import java.io.IOException;
import java.sql.SQLException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/DeleteProductServlet")
public class DeleteProductServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final BookDAO bookDAO = new BookDAO();
    private final AccessoryDAO accessoryDAO = new AccessoryDAO();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            int productId = Integer.parseInt(request.getParameter("id"));
            String productType = request.getParameter("type");

            if ("book".equals(productType)) {
                bookDAO.deleteBook(productId);
            } else {
                accessoryDAO.deleteAccessory(productId);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        response.sendRedirect("ManageProductsServlet"); //refresh product list
    }
}
