<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page pageEncoding="UTF-8" %>
<%@ page import="entity.User" %>
<%@ page import="dao.UserDAO" %>
<%@ page import="java.sql.SQLException" %>
<!DOCTYPE html>
<html>
<head>
    <title>Edit User</title>
    <style>
        body { font-family: Arial, sans-serif; text-align: center; }
        form { display: inline-block; text-align: left; }
        .nav-button { background-color: #007BFF; color: white; padding: 10px; text-decoration: none; border-radius: 5px; }
    </style>
</head>
<body>

    <h2>Edit User</h2>

    <%
        String userIdParam = request.getParameter("id");
        User user = null;

        if (userIdParam != null) {
            try {
                int userId = Integer.parseInt(userIdParam);
                UserDAO userDAO = new UserDAO();
                user = userDAO.getUserById(userId);
            } catch (SQLException | NumberFormatException e) {
                e.printStackTrace();
            }
        }

        if (user != null) {
    %>

    <form action="EditUserServlet" method="POST">
        <input type="hidden" name="id" value="<%= user.getId() %>">

        <label>Username:</label>
        <input type="text" name="username" value="<%= user.getUsername() %>" required><br><br>

        <label>Email:</label>
        <input type="email" name="email" value="<%= user.getEmail() %>" required><br><br>

        <label>Password:</label>
        <input type="text" name="password" value="<%= user.getPassword() %>" required><br><br>

        <button type="submit">Save Changes</button>
    </form>

    <br><br>
    <a href="ManageUsersServlet" class="nav-button">Cancel</a>

    <%
        } else {
    %>
        <p>User not found.</p>
        <a href="ManageUsersServlet" class="nav-button">Back to User Management</a>
    <%
        }
    %>

</body>
</html>
