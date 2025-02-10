<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Add Category</title>
    <style>
        body { font-family: Arial, sans-serif; text-align: center; }
        form { display: inline-block; text-align: left; }
        .nav-button { background-color: #007BFF; color: white; padding: 10px; text-decoration: none; border-radius: 5px; }
    </style>
</head>
<body>

    <h2>Add Category</h2>

    <form action="AddCategoryServlet" method="POST">
        <label>Category Name:</label>
        <input type="text" name="categoryName" required><br><br>
        <button type="submit">Add Category</button>
    </form>

    <br><br>
    <a href="ManageCategoriesServlet" class="nav-button">Cancel</a>

</body>
</html>
