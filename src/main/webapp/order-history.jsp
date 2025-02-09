<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page pageEncoding="UTF-8" %>
<%@ page import="java.util.List, java.util.Map, entity.Order, entity.Book, entity.Accessory" %>
<!DOCTYPE html>
<html>
<head>
    <title>Order History</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            text-align: center;
        }
        .order {
            padding: 10px;
            margin: 10px;
            border: 1px solid #ddd;
            display: inline-block;
            width: 80%;
        }
        .nav-button {
            display: block;
            width: 200px;
            padding: 10px;
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

    <h2>Your Order History</h2>

    <%
        List<Order> orders = (List<Order>) session.getAttribute("orderHistory");

        if (orders != null && !orders.isEmpty()) {
            for (Order order : orders) {
    %>
        <div class="order">
            <h3>Order ID: <%= order.getId() %></h3>
            <p><b>Order Date:</b> <%= order.getOrderDate() %></p>
            <p><b>Total Price:</b> $<%= order.getTotalPrice() %></p>
            <a href="OrderDetailsServlet?orderId=<%= order.getId() %>" class="nav-button">View Details</a>
        </div>
    <%
            }
        } else {
    %>
        <p>You have no past orders.</p>
    <%
        }
    %>

    <br>
    <a href="SearchServlet" class="nav-button">Continue Shopping</a>

</body>
</html>
