<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Welcome to BookNook</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            text-align: center;
            padding: 50px;
        }
        h1 {
            color: #333;
        }
        .nav-button {
            display: block;
            width: 200px;
            padding: 15px;
            margin: 10px auto;
            background-color: #007BFF;
            color: white;
            text-decoration: none;
            border-radius: 5px;
        }
        .nav-button:hover {
            background-color: #0056b3;
        }
    </style>
</head>
<body>

    <h1>Welcome to BookNook</h1>
    <p>Your one-stop shop for books and accessories!</p>

    <a href="SearchServlet" class="nav-button">Browse Books & Accessories</a>
    <a href="login.jsp" class="nav-button">Login</a>
    <a href="register.jsp" class="nav-button">Register</a>

</body>
</html>
