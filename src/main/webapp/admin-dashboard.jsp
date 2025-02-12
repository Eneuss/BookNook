<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Admin Dashboard</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            text-align: center;
        }
        .nav-button {
            display: block;
            width: 250px;
            padding: 15px;
            margin: 10px auto;
            background-color: #28a745;
            color: white;
            text-decoration: none;
            border-radius: 5px;
        }
        .nav-button:hover {
            background-color: #218838;
        }
    </style>
</head>
<body>
    <h1>Welcome, Admin <%= session.getAttribute("username") %>!</h1>
    <h2>Admin Dashboard</h2>

    <a href="ManageProductsServlet" class="nav-button">Manage Books & Accessories</a>
    <a href="SearchServlet" class="nav-button">Search Products</a>
    <a href="ManageUsersServlet" class="nav-button">Manage Users</a>
    <a href="ManageCategoriesServlet" class="nav-button">Manage Categories</a>
    <a href="LogoutServlet" class="nav-button">Logout</a>
</body>
</html>
