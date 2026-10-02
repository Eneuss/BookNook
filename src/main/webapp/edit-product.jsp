<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page pageEncoding="UTF-8" %>
<%@ page import="com.booknook.entity.Book, com.booknook.entity.Accessory, com.booknook.entity.Category, com.booknook.dao.BookDAO, com.booknook.dao.AccessoryDAO, com.booknook.dao.CategoryDAO" %>
<%@ page import="java.sql.SQLException, java.util.List" %>
<!DOCTYPE html>
<html>
<head>
    <title>Edit Product</title>
    <style>
        body { font-family: Arial, sans-serif; text-align: center; }
        form { display: inline-block; text-align: left; }
        .nav-button { background-color: #007BFF; color: white; padding: 10px; text-decoration: none; border-radius: 5px; }
    </style>
</head>
<body>

    <h2>Edit Product</h2>

    <%
        String productIdParam = request.getParameter("id");
        String productType = request.getParameter("type");
        Book book = null;
        Accessory accessory = null;
        List<Category> categories = null;

        if (productIdParam != null) {
            try {
                int productId = Integer.parseInt(productIdParam);
                CategoryDAO categoryDAO = new CategoryDAO();
                categories = categoryDAO.getAllCategoriesWithId();
                
                if ("book".equals(productType)) {
                    BookDAO bookDAO = new BookDAO();
                    book = bookDAO.getBookById(productId);
                } else {
                    AccessoryDAO accessoryDAO = new AccessoryDAO();
                    accessory = accessoryDAO.getAccessoryById(productId);
                }
            } catch (SQLException | NumberFormatException e) {
                application.log("Could not load product", e);
            }
        }

        if (book != null || accessory != null) {
    %>

    <form action="EditProductServlet" method="POST">
        <input type="hidden" name="id" value="<%= book != null ? book.getId() : accessory.getId() %>">
        <input type="hidden" name="productType" value="<%= productType %>">

        <label>Product Name:</label>
        <input type="text" name="productName" value="<%= book != null ? book.getTitle() : accessory.getName() %>" required><br><br>

        <label>Price:</label>
        <input type="number" step="0.01" name="price" value="<%= book != null ? book.getPrice() : accessory.getPrice() %>" required><br><br>

        <label>Stock:</label>
        <input type="number" name="stock" value="<%= book != null ? book.getStock() : accessory.getStock() %>" required><br><br>

        <% if (book != null) { %>
            <label>Author:</label>
            <input type="text" name="author" value="<%= book.getAuthor() %>"><br><br>

            <label>Category:</label>
            <select name="categoryId">
                <% for (Category category : categories) { %>
                <option value="<%= category.getId() %>" <%= book.getCategoryId() == category.getId() ? "selected" : "" %>><%= category.getName() %></option>
                <% } %>
            </select><br><br>
        <% } %>

        <button type="submit">Save Changes</button>
    </form>

    <br><br>
    <a href="ManageProductsServlet" class="nav-button">Cancel</a>

    <%
        } else {
    %>
        <p>Product not found.</p>
        <a href="ManageProductsServlet" class="nav-button">Back to Product Management</a>
    <%
        }
    %>

</body>
</html>
