<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>User Dashboard</title>
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
            background-color: #007BFF;
            color: white;
            text-decoration: none;
            border-radius: 5px;
        }
        .nav-button:hover {
            background-color: #0056b3;
        }
    </style>
</head>
<body>

    <h1>Welcome, <%= session.getAttribute("username") %>!</h1>
    <h2>User Dashboard</h2>

    <a href="SearchServlet" class="nav-button">Browse Books & Accessories</a>
    <a href="CheckoutServlet" class="nav-button">View Cart</a>
    <a href="OrderHistoryServlet" class="nav-button">Order History</a>
    <a href="LogoutServlet" class="nav-button">Logout</a>


</body>
</html>
