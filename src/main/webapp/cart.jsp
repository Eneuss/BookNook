<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page pageEncoding="UTF-8" %>
<%@ page import="java.util.List, entity.Book, entity.Accessory" %>
<!DOCTYPE html>
<html>
<head>
    <title>Shopping Cart</title>
</head>
<body>

    <%
        Integer userId = (Integer) session.getAttribute("userId");
        if (userId == null) {
            response.sendRedirect("login.jsp?redirect=cart.jsp");
            return;
        }

        List<Object> cart = (List<Object>) session.getAttribute("cart");
        if (cart == null) {
            cart = new java.util.ArrayList<>();
            session.setAttribute("cart", cart);
        }
    %>

    <h2>Your Shopping Cart</h2>

    <% if (!cart.isEmpty()) { %>
        <ul>
            <% for (Object item : cart) { %>
                <li>
                    <% if (item instanceof Book) { %>
                        <b>Book:</b> <%= ((Book) item).getTitle() %> - $<%= ((Book) item).getPrice() %> 
                    <% } else if (item instanceof Accessory) { %>
                        <b>Accessory:</b> <%= ((Accessory) item).getName() %> - $<%= ((Accessory) item).getPrice() %> 
                    <% } %>
                    <form action="RemoveFromCartServlet" method="POST">
                        <input type="hidden" name="productId" value="<%= item instanceof Book ? ((Book) item).getId() : ((Accessory) item).getId() %>">
                        <input type="hidden" name="productType" value="<%= item instanceof Book ? "book" : "accessory" %>">
                        <button type="submit">Remove</button>
                    </form>
                </li>
            <% } %>
        </ul>

        <br>
        <form action="CheckoutServlet" method="POST">
            <button type="submit">Proceed to Checkout</button>
        </form>

    <% } else { %>
        <p>Your cart is empty.</p>
    <% } %>

    <br>
    <a href="SearchServlet">Continue Shopping</a>

</body>
</html>
