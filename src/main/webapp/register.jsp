<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page pageEncoding="UTF-8" %>
<%@ page import="com.booknook.util.Html" %>
<!DOCTYPE html>
<html>
<head>
    <title>Register - BookNook</title>
</head>
<body>
    <h2>Register</h2>
    
    <% if (request.getAttribute("errorMessage") != null) { %>
        <p style="color:red;"><%= Html.escape(request.getAttribute("errorMessage")) %></p>
    <% } %>
    
    <form action="RegisterServlet" method="post">
        <label>Username:</label>
        <input type="text" name="username" required><br>
        
        <label>Email:</label>
        <input type="email" name="email" required><br>
        
        <label>Password:</label>
        <input type="password" name="password" required><br>
        
        <input type="submit" value="Register">
    </form>
    
    <p>Already have an account? <a href="login.jsp">Login here</a></p>
    <p>Or </p>
    <a href="home.jsp" class="nav-button">Return to Homepage</a>

</body>
</html>
