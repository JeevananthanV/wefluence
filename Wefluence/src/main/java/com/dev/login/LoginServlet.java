package com.dev.login;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

//@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private Connection connection;

    @Override
    public void init() throws ServletException {
        try {
            // Retrieve database connection parameters from web.xml
            ServletContext context = getServletContext();
            String driver = context.getInitParameter("driver");
            String jdbcUrl = context.getInitParameter("jdbcurl");
            String username = context.getInitParameter("username");
            String password = context.getInitParameter("password");

            // Load MySQL JDBC Driver
            Class.forName(driver);

            // Establish Connection
            connection = DriverManager.getConnection(jdbcUrl, username, password);

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            throw new ServletException("Database connection error", e);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String email = request.getParameter("email");
        String password = request.getParameter("password");

        try {
            // Prepare SQL query to fetch the hashed password
            String query = "SELECT password FROM admin_users WHERE email=?";
            try (PreparedStatement stmt = connection.prepareStatement(query)) {
                stmt.setString(1, email);
                ResultSet rs = stmt.executeQuery();

                if (rs.next()) {
                    String storedHashedPassword = rs.getString("password");

                    if (checkPassword(password, storedHashedPassword)) {
                        // Create a new session
                        HttpSession session = request.getSession(true);
                        session.setAttribute("user", email);
                        session.setMaxInactiveInterval(1800); // 30 minutes
                        response.sendRedirect(request.getContextPath() + "/DashboardServlet");
                        return;
                    }
                }

                request.setAttribute("error", "Invalid Credentials");
                request.getRequestDispatcher("/login.jsp").forward(request, response);
            }
        } catch (SQLException e) {
            request.setAttribute("error", "Database Error: " + e.getMessage());
            request.getRequestDispatcher("/login.jsp").forward(request, response);
        } catch (Exception e) {
            request.setAttribute("error", "Server Error: " + e.getMessage());
            request.getRequestDispatcher("/login.jsp").forward(request, response);
        }
    }

    private boolean checkPassword(String inputPassword, String storedHashedPassword) {
        String hashedInput = hashPassword(inputPassword);
        return MessageDigest.isEqual(hashedInput.getBytes(), storedHashedPassword.getBytes());
    }

    private String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hashedBytes = md.digest(password.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : hashedBytes) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error hashing password", e);
        }
    }
}
