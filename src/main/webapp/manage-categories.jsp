<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page pageEncoding="UTF-8" %>
<%@ page import="java.util.List, com.booknook.entity.Category" %>
<!DOCTYPE html>
<html>
<head>
    <title>Manage Categories</title>
    <style>
        body { font-family: Arial, sans-serif; text-align: center; }
        table { width: 80%; margin: auto; border-collapse: collapse; }
        th, td { padding: 10px; border: 1px solid #ddd; }
        .nav-button, .action-button {
            padding: 10px; margin: 5px; border-radius: 5px; text-decoration: none;
        }
        .nav-button { background-color: #007BFF; color: white; }
        .edit-button { background-color: #ffc107; color: black; }
        .delete-button { background-color: red; color: white; }
    </style>
</head>
<body>

    <h2>Manage Categories</h2>

    <table>
        <tr>
            <th>Category Name</th><th>Actions</th>
        </tr>
        <%
            List<Category> categories = (List<Category>) request.getAttribute("categories");
            if (categories != null && !categories.isEmpty()) {
                for (Category category : categories) {
        %>
            <tr>
                <td><%= category.getName() %></td>
                <td>
                    <a href="edit-category.jsp?id=<%= category.getId() %>" class="edit-button">Edit</a>
                    <a href="DeleteCategoryServlet?id=<%= category.getId() %>" class="delete-button">Delete</a>
                </td>
            </tr>
        <%
                }
            } else {
        %>
            <tr><td colspan="2">No categories available.</td></tr>
        <%
            }
        %>
    </table>

    <br>
    <a href="add-category.jsp" class="nav-button">Add New Category</a>
    <br><br><br><br>
    <a href="admin-dashboard.jsp" class="nav-button">Return to Dashboard</a>

</body>
</html>
