package com.dev.login;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/InfluFullForm")
public class InfluFullForm extends HttpServlet {
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
            throw new ServletException("Database connection error", e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        // Retrieve form data
        String fullName = request.getParameter("fullName");
        String contactNumber = request.getParameter("contactNumber");
        String emailAddress = request.getParameter("emailAddress");
        String state = request.getParameter("state");
        String city = request.getParameter("city");
        String gender = request.getParameter("gender");
        String instagramHandle = request.getParameter("instagramHandle");
        String youtubeChannel = request.getParameter("youtubeChannel");
        String primaryCategory = request.getParameter("primaryCategory");
        String otherCategory = request.getParameter("otherCategory");
        String engagementRate = request.getParameter("engagementRate");
        String longTermPartnership = request.getParameter("longTermPartnership");
        String notes = request.getParameter("notes");

        // ✅ Input Validation - Ensure required fields are filled
        if (fullName == null || fullName.trim().isEmpty() ||
            emailAddress == null || emailAddress.trim().isEmpty() ||
            contactNumber == null || contactNumber.trim().isEmpty() ||
            state == null || state.trim().isEmpty() ||
            city == null || city.trim().isEmpty() ||
            gender == null || (!gender.equalsIgnoreCase("Male") && !gender.equalsIgnoreCase("Female") && !gender.equalsIgnoreCase("Other"))) {
            
            out.println("<script>alert('Full Name, Email, Contact Number, State, City, and valid Gender are required!'); window.location.href='infludepth.html';</script>");
            return;
        }

        String insertQuery = "INSERT INTO InfluencerProfiles (fullName, contactNumber, emailAddress, state, city, gender, instagramHandle, " +
                "youtubeChannel, primaryCategory, otherCategory, engagementRate, longTermPartnership, notes) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        // Using try-with-resources to automatically close PreparedStatement
        try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {

            preparedStatement.setString(1, fullName);
            preparedStatement.setString(2, contactNumber);
            preparedStatement.setString(3, emailAddress);
            preparedStatement.setString(4, state);
            preparedStatement.setString(5, city);
            preparedStatement.setString(6, gender);
            preparedStatement.setString(7, instagramHandle);
            preparedStatement.setString(8, youtubeChannel);
            preparedStatement.setString(9, primaryCategory);
            preparedStatement.setString(10, otherCategory);
            preparedStatement.setString(11, engagementRate);
            preparedStatement.setString(12, longTermPartnership);
            preparedStatement.setString(13, notes);

            int result = preparedStatement.executeUpdate();

            if (result > 0) {
                // Success message with redirection
                out.println("<script>alert('Influencer form submitted successfully!'); window.location.href='InfluencerManagement.jsp';</script>");
            } else {
                // Failure message with redirection
                out.println("<script>alert('Form submission failed. Please try again.'); window.location.href='error.html';</script>");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            out.println("<script>alert('An error occurred while processing your request. Please try again later.'); window.location.href='error.html';</script>");
        }
    }

    @Override
    public void destroy() {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
