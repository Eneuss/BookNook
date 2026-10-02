<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page pageEncoding="UTF-8" %>
<%@ page import="java.util.List, com.booknook.entity.Cart, com.booknook.dao.CartDAO, com.booknook.dao.BookDAO, com.booknook.dao.AccessoryDAO, com.booknook.entity.Book, com.booknook.entity.Accessory" %>
<%@ page import="java.sql.SQLException" %>
<!DOCTYPE html>
<html>
<head>
    <title>Shopping Cart</title>
    <style>
        body { font-family: Arial, sans-serif; text-align: center; }
        table { width: 80%; margin: auto; border-collapse: collapse; }
        th, td { padding: 10px; border: 1px solid #ddd; }
        .nav-button, .action-button {
            padding: 10px;
            margin: 5px;
            border-radius: 5px;
            text-decoration: none;
        }
        .checkout-button { background-color: #007BFF; color: white; }
        .remove-button { background-color: red; color: white; }
    </style>
</head>
<body>
    <h2>Your Shopping Cart</h2>
    <%
        Integer userId = (Integer) session.getAttribute("userId");
        List<Cart> cartItems = null;
        if (userId != null) {
            try {
                CartDAO cartDAO = new CartDAO();
                cartItems = cartDAO.getCartItems(userId);
            } catch (SQLException e) {
                application.log("Could not load cart", e);
            }
        }
        
        if (cartItems != null && !cartItems.isEmpty()) {
    %>
        <table>
            <tr>
                <th>Product Name</th><th>Price</th><th>Quantity</th><th>Actions</th>
            </tr>
            <% 
                BookDAO bookDAO = new BookDAO();
                AccessoryDAO accessoryDAO = new AccessoryDAO();
                for (Cart item : cartItems) {
                    String productName = "";
                    double productPrice = 0.0;

                    if ("book".equals(item.getItemType())) {
                        Book book = bookDAO.getBookById(item.getItemId());
                        if (book != null) {
                            productName = book.getTitle();
                            productPrice = book.getPrice();
                        }
                    } else {
                        Accessory accessory = accessoryDAO.getAccessoryById(item.getItemId());
                        if (accessory != null) {
                            productName = accessory.getName();
                            productPrice = accessory.getPrice();
                        }
                    }
            %>
                <tr>
                    <td><%= productName %></td>
                    <td>$<%= productPrice %></td>
                    <td><%= item.getQuantity() %></td>
                    <td>
                        <a href="RemoveFromCartServlet?id=<%= item.getId() %>" class="action-button remove-button">Remove</a>
                    </td>
                </tr>
            <% } %>
        </table>
        <br>
        <form action="CheckoutServlet" method="POST">
            <button type="submit" class="nav-button checkout-button">Proceed to Checkout</button>
        </form>
    <%
        } else {
    %>
        <p>Your cart is empty.</p>
    <%
        }
    %>
    <br><br>
    <a href="SearchServlet" class="nav-button">Return to Search</a>

</body>
</html>
