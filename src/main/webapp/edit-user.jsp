<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page pageEncoding="UTF-8" %>
<%@ page import="com.booknook.util.Html" %>
<%@ page import="com.booknook.entity.User" %>
<%@ page import="com.booknook.dao.UserDAO" %>
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
                application.log("Could not load user", e);
            }
        }

        if (user != null) {
    %>

    <form action="EditUserServlet" method="POST">
        <input type="hidden" name="id" value="<%= Html.escape(user.getId()) %>">

        <label>Username:</label>
        <input type="text" name="username" value="<%= Html.escape(user.getUsername()) %>" required><br><br>

        <label>Email:</label>
        <input type="email" name="email" value="<%= Html.escape(user.getEmail()) %>" required><br><br>

        <label>New password:</label>
        <input type="password" name="password" autocomplete="new-password"><br>
        <small>(Leave blank to keep the current password)</small><br><br>

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
