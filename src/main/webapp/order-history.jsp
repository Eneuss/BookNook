<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page pageEncoding="UTF-8" %>
<%@ page import="com.booknook.util.Html" %>
<%@ page import="java.util.List, com.booknook.entity.Order, com.booknook.entity.OrderItem, java.text.DecimalFormat" %>
<!DOCTYPE html>
<html>
<head>
    <title>Order History</title>
    <style>
        body { font-family: Arial, sans-serif; text-align: center; }
        table { width: 80%; margin: auto; border-collapse: collapse; }
        th, td { padding: 10px; border: 1px solid #ddd; }
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

    <h2>Your Order History</h2>

    <%
        List<Order> orders = (List<Order>) session.getAttribute("orders");
        DecimalFormat df = new DecimalFormat("0.00");

        if (orders != null && !orders.isEmpty()) {
    %>
        <table>
            <tr>
                <th>Order ID</th><th>Date</th><th>Products</th><th>Total Price</th>
            </tr>
            <% for (Order order : orders) { %>
                <tr>
                    <td><%= Html.escape(order.getId()) %></td>
                    <td><%= Html.escape(order.getOrderDate()) %></td>
                    <td>
                        <ul>
                            <% for (OrderItem item : order.getItems()) { %>
                                <li><%= Html.escape(item.getName()) %> (x<%= Html.escape(item.getQuantity()) %>) - $<%= Html.escape(df.format(item.getPriceAtPurchase())) %></li>
                            <% } %>
                        </ul>
                    </td>
                    <td>$<%= Html.escape(df.format(order.getTotalPrice())) %></td>
                </tr>
            <% } %>
        </table>
    <%
        } else {
    %>
        <p>You have no past orders.</p>
    <%
        }
    %>

    <br><br>
    <a href="user-dashboard.jsp" class="nav-button">Return to Dashboard</a>

</body>
</html>
