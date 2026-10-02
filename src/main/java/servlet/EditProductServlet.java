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

@WebServlet("/EditProductServlet")
public class EditProductServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final BookDAO bookDAO = new BookDAO();
    private final AccessoryDAO accessoryDAO = new AccessoryDAO();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            int productId = Integer.parseInt(request.getParameter("id"));
            String productType = request.getParameter("productType");
            String productName = request.getParameter("productName");
            double price = Double.parseDouble(request.getParameter("price"));
            int stock = Integer.parseInt(request.getParameter("stock"));

            if ("book".equals(productType)) {
                String author = request.getParameter("author");
                int categoryId = Integer.parseInt(request.getParameter("categoryId"));
                bookDAO.updateBook(productId, productName, author, price, stock, categoryId);
            } else {
                accessoryDAO.updateAccessory(productId, productName, price, stock);
            }

            response.sendRedirect("ManageProductsServlet"); //refresh product list

        } catch (SQLException | NumberFormatException e) {
            e.printStackTrace();
            response.sendRedirect("ManageProductsServlet"); //redirect in case of error
        }
    }
}
