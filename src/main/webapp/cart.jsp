<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page pageEncoding="UTF-8" %>
<%@ page import="java.util.List, entity.Book, entity.Accessory" %>
<!DOCTYPE html>
<html>
<head>
    <title>Shopping Cart</title>
</head>
<body>
    <h2>Your Shopping Cart</h2>

    <%
        Integer userId = (Integer) session.getAttribute("userId");
        List<Object> cart = (List<Object>) session.getAttribute("cart");

        if (cart != null && !cart.isEmpty()) {
    %>
        <ul>
            <% for (Object item : cart) { %>
                <li>
                    <% if (item instanceof Book) { %>
                        <b>Book:</b> <%= ((Book) item).getTitle() %> - $<%= ((Book) item).getPrice() %>
                    <% } else if (item instanceof Accessory) { %>
                        <b>Accessory:</b> <%= ((Accessory) item).getName() %> - $<%= ((Accessory) item).getPrice() %>
                    <% } %>
                    <form action="RemoveFromCartServlet" method="POST">
                        <input type="hidden" name="productIndex" value="<%= cart.indexOf(item) %>">
                        <button type="submit">Remove</button>
                    </form>
                </li>
            <% } %>
        </ul>

        <br>
        <%
            if (userId != null) {
        %>
            <form action="CheckoutServlet" method="POST">
                <button type="submit">Proceed to Checkout</button>
            </form>
        <%
            } else {
        %>
            <p><a href="login.jsp">Login</a> to proceed to checkout.</p>
        <%
            }
        %>

    <% } else { %>
        <p>Your cart is empty.</p>
    <% } %>

    <br>
    <a href="SearchServlet">Continue Shopping</a>

</body>
</html>
