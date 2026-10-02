/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package servlets;

import dao.BookDAO;
import dao.AccessoryDAO;
import dao.CategoryDAO;
import entity.Book;
import entity.Accessory;
import entity.Category;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/SearchServlet")
public class SearchServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        BookDAO bookDAO = new BookDAO();
        AccessoryDAO accessoryDAO = new AccessoryDAO();
        CategoryDAO categoryDAO = new CategoryDAO();

        try {
            //fetch all books, accessories, and categories
            List<Book> books = bookDAO.getAllBooks();
            List<Accessory> accessories = accessoryDAO.getAllAccessories();
            List<Category> categories = categoryDAO.getAllCategoriesWithId();

            //HashMap to store categoryId into categoryName mapping
            HashMap<Integer, String> categoryMap = new HashMap<>();
            for (Category category : categories) {
                categoryMap.put(category.getId(), category.getName());
            }

            //get search query safely
            String searchQuery = request.getParameter("searchQuery");
            if (searchQuery != null && !searchQuery.trim().isEmpty()) {
                final String searchLower = searchQuery.trim().toLowerCase(); //declare final variable

                //ensure books and accessories are filtered properly
                books.removeIf(book -> !(book.getTitle().toLowerCase().contains(searchLower) ||
                                         book.getAuthor().toLowerCase().contains(searchLower)));

                accessories.removeIf(accessory -> !accessory.getName().toLowerCase().contains(searchLower));
            }

            //use session attributes instead of request attributes
            request.getSession().setAttribute("books", books);
            request.getSession().setAttribute("accessories", accessories);
            request.getSession().setAttribute("categories", categoryMap);

            //redirect to search.jsp
            response.sendRedirect("search.jsp");

        } catch (SQLException e) {
            throw new ServletException("Database error while fetching products", e);
        }
    }
}
