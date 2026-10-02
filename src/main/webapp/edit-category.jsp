<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page pageEncoding="UTF-8" %>
<%@ page import="com.booknook.entity.Category, com.booknook.dao.CategoryDAO" %>
<%@ page import="java.sql.SQLException" %>
<!DOCTYPE html>
<html>
<head>
    <title>Edit Category</title>
    <style>
        body { font-family: Arial, sans-serif; text-align: center; }
        form { display: inline-block; text-align: left; }
        .nav-button { background-color: #007BFF; color: white; padding: 10px; text-decoration: none; border-radius: 5px; }
    </style>
</head>
<body>

    <h2>Edit Category</h2>

    <%
        String categoryIdParam = request.getParameter("id");
        Category category = null;

        if (categoryIdParam != null) {
            try {
                int categoryId = Integer.parseInt(categoryIdParam);
                CategoryDAO categoryDAO = new CategoryDAO();
                category = categoryDAO.getCategoryById(categoryId);
            } catch (SQLException | NumberFormatException e) {
                application.log("Could not load category", e);
            }
        }

        if (category != null) {
    %>

    <form action="EditCategoryServlet" method="POST">
        <input type="hidden" name="id" value="<%= category.getId() %>">

        <label>Category Name:</label>
        <input type="text" name="categoryName" value="<%= category.getName() %>" required><br><br>

        <button type="submit">Save Changes</button>
    </form>

    <br><br>
    <a href="ManageCategoriesServlet" class="nav-button">Cancel</a>

    <%
        } else {
    %>
        <p>Category not found.</p>
        <a href="ManageCategoriesServlet" class="nav-button">Back to Category Management</a>
    <%
        }
    %>

</body>
</html>
