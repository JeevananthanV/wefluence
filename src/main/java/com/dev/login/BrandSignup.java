package com.dev.login;

import java.io.IOException;
import java.io.PrintWriter;
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

@WebServlet("/BrandSignup")
public class BrandSignup extends HttpServlet {
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

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        String brandName = request.getParameter("brandName");
        String contactPerson = request.getParameter("contactPerson");
        String emailAddress = request.getParameter("emailAddress");
        String phoneNumber = request.getParameter("phoneNumber");

        PreparedStatement checkStmt = null;
        PreparedStatement insertStmt = null;
        ResultSet rs = null;

        try {
            // 1️⃣ Check if email already exists
            String checkQuery = "SELECT COUNT(*) FROM BrandSignupdata WHERE emailAddress = ?";
            checkStmt = connection.prepareStatement(checkQuery);
            checkStmt.setString(1, emailAddress);

            rs = checkStmt.executeQuery();
            rs.next();
            int count = rs.getInt(1);

            if (count > 0) {
                // Email already exists, show alert and redirect
                out.println("<script type=\"text/javascript\">");
                out.println("alert('This email is already registered. Please use a different email.');");
                out.println("window.location.href='Brandsignup.html';");
                out.println("</script>");
            } else {
                // 2️⃣ Insert new brand signup record
                String insertQuery = "INSERT INTO BrandSignupdata (brandName, contactPerson, emailAddress, phoneNumber) VALUES (?, ?, ?, ?)";
                insertStmt = connection.prepareStatement(insertQuery);
                insertStmt.setString(1, brandName);
                insertStmt.setString(2, contactPerson);
                insertStmt.setString(3, emailAddress);
                insertStmt.setString(4, phoneNumber);

                int result = insertStmt.executeUpdate();

                if (result > 0) {
                    out.println("<script type=\"text/javascript\">");
                    out.println("alert('Brand signup successful!');");
                    out.println("window.location.href='branding.html';");
                    out.println("</script>");
                } else {
                    out.println("<script type=\"text/javascript\">");
                    out.println("alert('Brand signup failed. Please try again.');");
                    out.println("window.location.href='error.html';");
                    out.println("</script>");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            out.println("<script type=\"text/javascript\">");
            out.println("alert('An error occurred while signing up. Please try again later.');");
            out.println("window.location.href='error.html';");
            out.println("</script>");
        } finally {
            try {
                if (rs != null) rs.close();
                if (checkStmt != null) checkStmt.close();
                if (insertStmt != null) insertStmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void destroy() {
        try {
            if (connection != null) {
                connection.close();
                System.out.println("Database connection closed.");
            }

            
            System.out.println("JDBC Driver unregistered successfully.");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
