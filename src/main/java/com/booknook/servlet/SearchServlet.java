package com.booknook.servlet;

import com.booknook.dao.AccessoryDAO;
import com.booknook.dao.BookDAO;
import com.booknook.dao.CategoryDAO;
import com.booknook.entity.Accessory;
import com.booknook.entity.Book;
import com.booknook.entity.Category;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

@WebServlet("/SearchServlet")
public class SearchServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        BookDAO bookDAO = new BookDAO();
        AccessoryDAO accessoryDAO = new AccessoryDAO();
        CategoryDAO categoryDAO = new CategoryDAO();

        try {
            // fetch books and accessories, filtered by the search query if one is given
            String searchQuery = request.getParameter("searchQuery");
            boolean hasQuery = searchQuery != null && !searchQuery.trim().isEmpty();
            List<Book> books = hasQuery ? bookDAO.searchBooks(searchQuery.trim()) : bookDAO.getAllBooks();
            List<Accessory> accessories =
                    hasQuery ? accessoryDAO.searchAccessories(searchQuery.trim()) : accessoryDAO.getAllAccessories();
            List<Category> categories = categoryDAO.getAllCategoriesWithId();

            // HashMap to store categoryId into categoryName mapping
            HashMap<Integer, String> categoryMap = new HashMap<>();
            for (Category category : categories) {
                categoryMap.put(category.getId(), category.getName());
            }

            // use session attributes instead of request attributes
            request.getSession().setAttribute("books", books);
            request.getSession().setAttribute("accessories", accessories);
            request.getSession().setAttribute("categories", categoryMap);

            // redirect to search.jsp
            response.sendRedirect("search.jsp");

        } catch (SQLException e) {
            throw new ServletException("Database error while fetching products", e);
        }
    }
}
