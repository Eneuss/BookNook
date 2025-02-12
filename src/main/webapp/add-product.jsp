<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page pageEncoding="UTF-8" %>
<%@ page import="java.util.List, entity.Category, dao.CategoryDAO" %>
<%@ page import="java.sql.SQLException" %>
<!DOCTYPE html>
<html>
<head>
    <title>Add Product</title>
    <style>
        body { font-family: Arial, sans-serif; text-align: center; }
        form { display: inline-block; text-align: left; }
        .nav-button { background-color: #007BFF; color: white; padding: 10px; text-decoration: none; border-radius: 5px; }
    </style>
</head>
<body>

    <h2>Add Product</h2>

    <form action="AddProductServlet" method="POST">
        <label>Product Type:</label>
        <select name="productType" id="productType" onchange="toggleFields()">
            <option value="book">Book</option>
            <option value="accessory">Accessory</option>
        </select><br><br>

        <label>Product Name:</label>
        <input type="text" name="productName" required><br><br>

        <label>Price:</label>
        <input type="number" step="0.01" name="price" required><br><br>

        <label>Stock:</label>
        <input type="number" name="stock" required><br><br>

        <div id="bookFields">
            <label>Author:</label>
            <input type="text" name="author"><br>
            <small>(Leave blank for accessories)</small><br><br>

            <label>Category:</label>
            <select name="categoryId">
                <%
                    CategoryDAO categoryDAO = new CategoryDAO();
                    List<Category> categories = categoryDAO.getAllCategoriesWithId();
                    for (Category category : categories) {
                %>
                <option value="<%= category.getId() %>"><%= category.getName() %></option>
                <%
                    }
                %>
            </select><br>
            <small>(Leave blank for accessories)</small><br><br>
        </div>

        <button type="submit">Add Product</button>
    </form>

    <br><br>
    <a href="ManageProductsServlet" class="nav-button">Cancel</a>

    <script>
        function toggleFields() {
            var productType = document.getElementById("productType").value;
            var bookFields = document.getElementById("bookFields");

            if (productType === "book") {
                bookFields.style.display = "block";
            } else {
                bookFields.style.display = "none";
            }
        }
        
        window.onload = toggleFields;
    </script>

</body>
</html>
