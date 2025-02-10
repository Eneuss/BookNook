<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page pageEncoding="UTF-8" %>
<%@ page import="java.util.List, entity.Book, entity.Accessory" %>
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
    </style>
</head>
<body>

    <h2>Manage Books & Accessories</h2>

    <!-- ✅ Navigation buttons at the top -->
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
                <td><%= book.getTitle() %></td>
                <td><%= book.getAuthor() %></td>
                <td>$<%= book.getPrice() %></td>
                <td><%= book.getStock() %></td>
                <td>
                    <a href="edit-product.jsp?id=<%= book.getId() %>&type=book" class="edit-button">Edit</a>
                    <a href="DeleteProductServlet?type=book&id=<%= book.getId() %>" class="delete-button">Delete</a>
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
                <td><%= accessory.getName() %></td>
                <td>$<%= accessory.getPrice() %></td>
                <td><%= accessory.getStock() %></td>
                <td>
                    <a href="edit-product.jsp?id=<%= accessory.getId() %>&type=accessory" class="edit-button">Edit</a>
                    <a href="DeleteProductServlet?type=accessory&id=<%= accessory.getId() %>" class="delete-button">Delete</a>
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
