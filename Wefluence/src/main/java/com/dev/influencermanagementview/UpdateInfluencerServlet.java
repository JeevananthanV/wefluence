package com.dev.influencermanagementview;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/UpdateInfluencerServlet")
public class UpdateInfluencerServlet extends HttpServlet {
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

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        PreparedStatement statement = null;

        try {
            String influencerId = request.getParameter("influencer_id");
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

            // SQL Query to update influencer profile
            String updateQuery = "UPDATE InfluencerProfiles SET fullName=?, contactNumber=?, emailAddress=?, state=?, city=?, gender=?, " +
                    "instagramHandle=?, youtubeChannel=?, primaryCategory=?, otherCategory=?, engagementRate=?, longTermPartnership=?, notes=? " +
                    "WHERE influencer_id=?";
            statement = connection.prepareStatement(updateQuery);

            // Setting parameters from request
            statement.setString(1, fullName);
            statement.setString(2, contactNumber);
            statement.setString(3, emailAddress);
            statement.setString(4, state);
            statement.setString(5, city);
            statement.setString(6, gender);
            statement.setString(7, instagramHandle);
            statement.setString(8, youtubeChannel);
            statement.setString(9, primaryCategory);
            statement.setString(10, otherCategory);
            statement.setString(11, engagementRate);
            statement.setString(12, longTermPartnership);
            statement.setString(13, notes);
            statement.setString(14, influencerId);

            // Execute update
            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated > 0) {
                // Redirect to InfluencerManagement upon success
                response.sendRedirect("InfluencerManagement");
            } else {
                response.getWriter().println("Update failed. No record found with the provided ID.");
            }

        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().println("An error occurred while updating the data.");
        } finally {
            // Closing resources
            try {
                if (statement != null) statement.close();
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
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
