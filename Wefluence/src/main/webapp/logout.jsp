<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="javax.servlet.http.*, javax.servlet.*" %>

<%
    // Invalidate the current session
    if (session != null) {
        session.invalidate();
    }
%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Logging Out...</title>
    <link rel="stylesheet" href="css/style.css">
    <style>
    .logout-message {
    text-align: center;
    margin-top: 20%;
    font-family: Arial, sans-serif;
}

.logout-message h2 {
    color: #ff4d4d;
}

.logout-message p {
    font-size: 18px;
    color: #555;
}
    </style>
    <script>
        // Redirect to login page after 2 seconds
        setTimeout(function() {
            window.location.href = "login.jsp";
        }, 2000);
    </script>
</head>
<body>
    <div class="logout-message">
        <h2>You have been logged out.</h2>
        <p>Redirecting to the login page...</p>
    </div>
</body>
</html>
