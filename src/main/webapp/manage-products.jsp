<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page pageEncoding="UTF-8" %>
<%@ page import="com.booknook.util.Html" %>
<%@ page import="java.util.List, com.booknook.entity.Book, com.booknook.entity.Accessory" %>
<!DOCTYPE html>
<html>
<head>
    <title>Manage Books & Accessories</title>
    <style>
        body { font-family: Arial, sans-serif; text-align: center; }
        table { width: 80%; margin: auto; border-collapse: collapse; }
        th, td { padding: 10px; border: 1px solid #ddd; }
        .nav-container { text-align: center; margin-bottom: 20px; }
        .nav-button {
            display: inline-block; 
            padding: 10px 20px;
            margin: 10px;
            border-radius: 5px;
            text-decoration: none;
            font-size: 16px;
        }
        .dashboard-button { background-color: #007BFF; color: white; }
        .add-product-button { background-color: #28a745; color: white; }
        .edit-button { background-color: #ffc107; color: black; }
        .delete-button { background-color: red; color: white; }
        .inline-form { display: inline; }
        button.delete-button, button.remove-button { border: none; cursor: pointer; }
    </style>
</head>
<body>

    <h2>Manage Books & Accessories</h2>

    <div class="nav-container">
        <a href="admin-dashboard.jsp" class="nav-button dashboard-button">Return to Dashboard</a>
        <a href="add-product.jsp" class="nav-button add-product-button">Add New Product</a>
    </div>

    <h3>Books</h3>
    <table>
        <tr>
            <th>Title</th><th>Author</th><th>Price</th><th>Stock</th><th>Actions</th>
        </tr>
        <%
            List<Book> books = (List<Book>) request.getAttribute("books");
            if (books != null && !books.isEmpty()) {
                for (Book book : books) {
        %>
            <tr>
                <td><%= Html.escape(book.getTitle()) %></td>
                <td><%= Html.escape(book.getAuthor()) %></td>
                <td>$<%= Html.escape(book.getPrice()) %></td>
                <td><%= Html.escape(book.getStock()) %></td>
                <td>
                    <a href="edit-product.jsp?id=<%= Html.escape(book.getId()) %>&type=book" class="edit-button">Edit</a>
                    <form action="DeleteProductServlet" method="POST" class="inline-form"><input type="hidden" name="type" value="book"><input type="hidden" name="id" value="<%= Html.escape(book.getId()) %>"><button type="submit" class="delete-button">Delete</button></form>
                </td>
            </tr>
        <%
                }
            } else {
        %>
            <tr><td colspan="5">No books available.</td></tr>
        <%
            }
        %>
    </table>
    
    <h3>Accessories</h3>
    <table>
        <tr>
            <th>Name</th><th>Price</th><th>Stock</th><th>Actions</th>
        </tr>
        <%
            List<Accessory> accessories = (List<Accessory>) request.getAttribute("accessories");
            if (accessories != null && !accessories.isEmpty()) {
                for (Accessory accessory : accessories) {
        %>
            <tr>
                <td><%= Html.escape(accessory.getName()) %></td>
                <td>$<%= Html.escape(accessory.getPrice()) %></td>
                <td><%= Html.escape(accessory.getStock()) %></td>
                <td>
                    <a href="edit-product.jsp?id=<%= Html.escape(accessory.getId()) %>&type=accessory" class="edit-button">Edit</a>
                    <form action="DeleteProductServlet" method="POST" class="inline-form"><input type="hidden" name="type" value="accessory"><input type="hidden" name="id" value="<%= Html.escape(accessory.getId()) %>"><button type="submit" class="delete-button">Delete</button></form>
                </td>
            </tr>
        <%
                }
            } else {
        %>
            <tr><td colspan="4">No accessories available.</td></tr>
        <%
            }
        %>
    </table>

</body>
</html>
