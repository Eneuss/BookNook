<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page pageEncoding="UTF-8" %>
<%@ page import="com.booknook.util.Html" %>
<%@ page import="java.util.List, java.util.Map, com.booknook.entity.Book, com.booknook.entity.Accessory" %>
<!DOCTYPE html>
<html>
<head>
    <title>Search Books & Accessories</title>
    <script>
        function filterBooks() {
            var selectedCategory = document.getElementById("categoryFilter").value;
            var books = document.getElementsByClassName("book-item");

            for (var i = 0; i < books.length; i++) {
                if (selectedCategory === "all") {
                    books[i].style.display = "block";
                } else {
                    var bookCategory = books[i].getAttribute("data-category");
                    if (bookCategory === selectedCategory) {
                        books[i].style.display = "block";
                    } else {
                        books[i].style.display = "none";
                    }
                }
            }
        }
    </script>
</head>
<body>
    <h2>Search for Books & Accessories</h2>

    <form method="GET" action="SearchServlet">
        <label for="searchQuery">Search:</label>
        <input type="text" id="searchQuery" name="searchQuery" placeholder="Enter book title or accessory name">
        <button type="submit">Search</button>
    </form>

    <h3>Filter Books by Category</h3>
    <select id="categoryFilter" onchange="filterBooks()">
        <option value="all">All Categories</option>
        <%
            Map<Integer, String> categoryMap = (Map<Integer, String>) session.getAttribute("categories");
            if (categoryMap != null) {
                for (Map.Entry<Integer, String> entry : categoryMap.entrySet()) {
        %>
            <option value="<%= Html.escape(entry.getValue()) %>"><%= Html.escape(entry.getValue()) %></option>
        <%
                }
            }
        %>
    </select>

    <h3>Books</h3>
    <%
        Integer userId = (Integer) session.getAttribute("userId");
        String userRole = (String) session.getAttribute("role");
        boolean showCartOptions = (userId != null && !"admin".equals(userRole));
        List<Book> books = (List<Book>) session.getAttribute("books");

        if (books != null && !books.isEmpty()) {
    %>
        <ul>
            <% for (Book book : books) { %>
                <li class="book-item" data-category="<%= Html.escape(categoryMap.get(book.getCategoryId())) %>">
                    <b><%= Html.escape(book.getTitle()) %></b> by <%= Html.escape(book.getAuthor()) %>
                    - Category: <%= Html.escape(categoryMap.get(book.getCategoryId())) %>
                    - Price: $<%= Html.escape(book.getPrice()) %>
                    - Stock: <%= Html.escape(book.getStock()) %>

                    <% if (showCartOptions) { %> 
                        <!--here we show "Add to Cart" only for regular users -->
                        <form action="AddToCartServlet" method="POST">
                            <input type="hidden" name="productType" value="book">
                            <input type="hidden" name="productId" value="<%= Html.escape(book.getId()) %>">
                            <button type="submit">Add to Cart</button>
                        </form>
                    <% } %>

                </li>
            <% } %>
        </ul>
    <% } else { %>
        <p>No books available.</p>
    <% } %>

    <h3>Accessories</h3>
    <%
        List<Accessory> accessories = (List<Accessory>) session.getAttribute("accessories");

        if (accessories != null && !accessories.isEmpty()) {
    %>
        <ul>
            <% for (Accessory accessory : accessories) { %>
                <li>
                    <b><%= Html.escape(accessory.getName()) %></b>
                    - Price: $<%= Html.escape(accessory.getPrice()) %>
                    - Stock: <%= Html.escape(accessory.getStock()) %>

                    <% if (showCartOptions) { %> 
                        <form action="AddToCartServlet" method="POST">
                            <input type="hidden" name="productType" value="accessory">
                            <input type="hidden" name="productId" value="<%= Html.escape(accessory.getId()) %>">
                            <button type="submit">Add to Cart</button>
                        </form>
                    <% } %>

                </li>
            <% } %>
        </ul>
    <% } else { %>
        <p>No accessories available.</p>
    <% } %>

    <% if (showCartOptions) { %>
        <!-- show "View Cart" only for regular users -->
        <br>
        <a href="cart.jsp">View Cart</a>
    <% } %>

    <br><br>

    <%
        String redirectPage = "home.jsp"; // Default for guests

        if (userId != null) {
            if ("admin".equals(userRole)) {
                redirectPage = "admin-dashboard.jsp";
            } else {
                redirectPage = "user-dashboard.jsp";
            }
        }
    %>

    <a href="<%= Html.escape(redirectPage) %>" class="nav-button">Return to Dashboard</a>

</body>
</html>
