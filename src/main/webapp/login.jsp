<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page pageEncoding="UTF-8" %>
<%@ page import="com.booknook.util.Html" %>
<!DOCTYPE html>
<html>
<head>
    <title>Login - BookNook</title>
</head>
<body>
    <h2>Login</h2>
    <% if (request.getAttribute("errorMessage") != null) { %>
        <p style="color:red;"><%= Html.escape(request.getAttribute("errorMessage")) %></p>
    <% } %>
    <form action="LoginServlet" method="post">
        <label>Username:</label>
        <input type="text" name="username" required><br>
        <label>Password:</label>
        <input type="password" name="password" required><br>
        <input type="submit" value="Login">
    </form>
    
    <p>Don't have an account? <a href="register.jsp">Register here</a></p>
    <p>Or </p>
    <a href="home.jsp" class="nav-button">Return to Homepage</a>

</body>
</html>

