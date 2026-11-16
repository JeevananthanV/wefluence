package com.dev.influencermanagementview;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import javax.servlet.RequestDispatcher;

@WebServlet("/InfluencerManagement")
public class InfluencerManagement extends HttpServlet {
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

            // Load MySQL JDBC Driver and establish connection
            Class.forName(driver);
            connection = DriverManager.getConnection(jdbcUrl, username, password);
            System.out.println("Database connection established successfully.");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            throw new ServletException("Database connection error", e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        List<InfluencerProfileData> influencerProfiles = new ArrayList<>();
        String searchQuery = request.getParameter("search");

        // SQL Query
        String selectQuery = "SELECT * FROM InfluencerProfiles ";
        if (searchQuery != null && !searchQuery.isEmpty()) {
            selectQuery += "WHERE influencer_id = ? OR fullName LIKE ? OR emailAddress LIKE ? OR state LIKE ? OR city LIKE ? OR gender LIKE ? ";
        }
        selectQuery += "ORDER BY submissionDate DESC";

        try (PreparedStatement selectStatement = connection.prepareStatement(selectQuery)) {
            if (searchQuery != null && !searchQuery.isEmpty()) {
                selectStatement.setString(1, searchQuery);
                String searchPattern = "%" + searchQuery + "%";
                selectStatement.setString(2, searchPattern);
                selectStatement.setString(3, searchPattern);
                selectStatement.setString(4, searchPattern);
                selectStatement.setString(5, searchPattern);
                selectStatement.setString(6, searchPattern);
            }

            try (ResultSet resultSet = selectStatement.executeQuery()) {
                while (resultSet.next()) {
                    influencerProfiles.add(new InfluencerProfileData(
                            resultSet.getString("influencer_id"),
                            resultSet.getString("fullName"),
                            resultSet.getString("contactNumber"),
                            resultSet.getString("emailAddress"),
                            resultSet.getString("state"),
                            resultSet.getString("city"),
                            resultSet.getString("gender"),
                            resultSet.getString("instagramHandle"),
                            resultSet.getString("youtubeChannel"),
                            resultSet.getString("primaryCategory"),
                            resultSet.getString("otherCategory"),
                            resultSet.getString("engagementRate"),
                            resultSet.getString("longTermPartnership"),
                            resultSet.getString("notes"),
                            resultSet.getTimestamp("submissionDate") != null
                                    ? resultSet.getTimestamp("submissionDate").toLocalDateTime()
                                    : null
                    ));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Forward results to JSP for display
        request.setAttribute("influencerProfiles", influencerProfiles);
        RequestDispatcher dispatcher = request.getRequestDispatcher("InfluencerManagement.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    public void destroy() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("Database connection closed successfully.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
