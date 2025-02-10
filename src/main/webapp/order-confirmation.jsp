<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page pageEncoding="UTF-8" %>
<%@ page import="java.util.List, entity.Cart" %>
<!DOCTYPE html>
<html>
<head>
    <title>Order Confirmation</title>
    <style>
        body { font-family: Arial, sans-serif; text-align: center; }
        .nav-button {
            padding: 10px;
            margin: 10px;
            border-radius: 5px;
            text-decoration: none;
            background-color: #007BFF;
            color: white;
            display: inline-block;
        }
    </style>
</head>
<body>

    <h2>Order Confirmation</h2>

    <p>Thank you for your purchase, <b><%= session.getAttribute("username") %></b>!</p>
    <p>Your order has been successfully placed.</p>

    <a href="user-dashboard.jsp" class="nav-button">Return to Dashboard</a>

</body>
</html>
