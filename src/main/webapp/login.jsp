<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/admin.css">
    <title>Admin Login - Wefluence</title>
    <style>
        :root {
            --txtb: rgb(0, 0, 0);
            --txtw: rgb(255, 255, 255);
            --primary-color: #191919;
            --back-color: #333333;
            --second-back-color: #666666;
            --mainheading: #ffd700;
            --subheadsimg: white;
            --paracolor: rgb(220, 220, 220);
            --mainheadfont: "Montserrat", serif;
            --subheadfont: "Montserrat", serif;
            --parafont: "Montserrat", serif;
            --white: #ffffff;
        }

        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
            font-family: var(--parafont);
        }

        body {
            display: flex;
            height: 100vh;
            align-items: center;
            justify-content: center;
            background: var(--primary-color);
        }

        .login-container {
            background: var(--back-color);
            padding: 20px;
            border-radius: 12px;
            box-shadow: 0 4px 6px rgba(0, 0, 0, 0.2);
            text-align: center;
            width: 320px;
        }

        h2 {
            color: var(--mainheading);
            margin-bottom: 20px;
            font-family: var(--mainheadfont);
        }

        .input-group {
            position: relative;
            margin-bottom: 20px;
        }

        .input-group input {
            width: 100%;
            padding: 10px;
            border: none;
            outline: none;
            background: transparent;
            border-bottom: 2px solid var(--white);
            color: var(--white);
            font-size: 16px;
        }

        .input-group label {
            position: absolute;
            left: 10px;
            top: 10px;
            transition: 0.3s;
            color: var(--paracolor);
            font-size: 14px;
        }

        .input-group input:focus + label,
        .input-group input:valid + label {
            top: -10px;
            font-size: 12px;
            color: var(--mainheading);
        }

        .toggle-password {
            position: absolute;
            right: 10px;
            top: 10px;
            cursor: pointer;
            color: var(--paracolor);
        }

        button {
            background: var(--mainheading);
            color: var(--txtb);
            border: none;
            padding: 10px;
            width: 100%;
            cursor: pointer;
            transition: 0.3s;
            font-size: 16px;
        }

        button:hover {
            background: var(--subheadsimg);
            color: var(--primary-color);
        }
    </style>
</head>
<body>

    <div class="login-container">
        <form action="LoginServlet" method="post">
            <h2>Admin Login</h2>

            <div class="input-group">
                <input type="email" name="email" required>
                <label>Email</label>
            </div>

            <div class="input-group">
                <input type="password" name="password" id="password" required>
                <label>Password</label>
                <span class="toggle-password" onclick="togglePassword()">👁</span>
            </div>

            <button type="submit">Login</button>
            <c:if test="${not empty errorMessage}">
                <div class="auth-error">${errorMessage}</div>               
            </c:if>
        </form>
    </div>

    <script>
        function togglePassword() {
            let passwordField = document.getElementById("password");
            passwordField.type = passwordField.type === "password" ? "text" : "password";
        }
    </script>

</body>
</html>
