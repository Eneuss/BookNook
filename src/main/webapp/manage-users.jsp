<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page pageEncoding="UTF-8" %>
<%@ page import="java.util.List, com.booknook.entity.User" %>
<!DOCTYPE html>
<html>
<head>
    <title>Manage Users</title>
    <style>
        body { font-family: Arial, sans-serif; text-align: center; }
        table { width: 80%; margin: auto; border-collapse: collapse; }
        th, td { padding: 10px; border: 1px solid #ddd; }
        .nav-button, .action-button {
            padding: 10px; margin: 5px; border-radius: 5px; text-decoration: none;
        }
        .nav-button { background-color: #007BFF; color: white; }
        .edit-button { background-color: #ffc107; color: black; }
        .delete-button { background-color: red; color: white; }
        .inline-form { display: inline; }
        button.delete-button, button.remove-button { border: none; cursor: pointer; }
    </style>
</head>
<body>

    <h2>Manage Users</h2>

    <table>
        <tr>
            <th>Username</th><th>Email</th><th>Actions</th>
        </tr>
        <%
            List<User> users = (List<User>) request.getAttribute("users");
            if (users != null && !users.isEmpty()) {
                for (User user : users) {
        %>
            <tr>
                <td><%= user.getUsername() %></td>
                <td><%= user.getEmail() %></td>
                <td>
                    <a href="edit-user.jsp?id=<%= user.getId() %>" class="edit-button">Edit</a>
                    <form action="DeleteUserServlet" method="POST" class="inline-form"><input type="hidden" name="id" value="<%= user.getId() %>"><button type="submit" class="delete-button">Delete</button></form>
                </td>
            </tr>
        <%
                }
            } else {
        %>
            <tr><td colspan="3">No regular users available.</td></tr>
        <%
            }
        %>
    </table>

    <br><br>
    <a href="admin-dashboard.jsp" class="nav-button">Return to Dashboard</a>

</body>
</html>
