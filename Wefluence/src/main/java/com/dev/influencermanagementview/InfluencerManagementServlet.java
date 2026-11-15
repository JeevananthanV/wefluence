package com.dev.influencermanagementview;

import java.io.*;
import java.sql.*;
import java.util.*;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import org.json.*;

@WebServlet("/InfluencerManagementServlet")
public class InfluencerManagementServlet extends HttpServlet {
    private static final String JSON_FILE_PATH = "C:\\\\Users\\\\jeeva\\\\OneDrive\\\\New folder\\\\Desktop\\\\Wefluence\\\\src\\\\main\\\\webapp\\\\json\\\\influencers.json";
    private Connection connection;

    @Override
    public void init() throws ServletException {
        try {
            ServletContext context = getServletContext();
            String driver = context.getInitParameter("driver");
            String jdbcUrl = context.getInitParameter("jdbcurl");
            String username = context.getInitParameter("username");
            String password = context.getInitParameter("password");

            Class.forName(driver);
            connection = DriverManager.getConnection(jdbcUrl, username, password);

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            throw new ServletException("Database connection error", e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String searchQuery = request.getParameter("search");
        updateJsonFile(searchQuery);
        request.getRequestDispatcher("viewInfluencers.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String influencerId = request.getParameter("influencer_id");

        if (influencerId == null || influencerId.isEmpty()) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("Invalid ID");
            return;
        }

        try (PreparedStatement stmt = connection.prepareStatement(
                "UPDATE InfluencerApprovalDetails SET active_status = IF(active_status='Active', 'Inactive', 'Active') WHERE influencer_id=?")) {

            stmt.setString(1, influencerId);
            int updatedRows = stmt.executeUpdate();

            if (updatedRows > 0) {
                updateJsonFile(null);
                response.getWriter().write("success");
            } else {
                response.getWriter().write("Error updating status");
            }

        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().write("Error: " + e.getMessage());
        }
    }

    private void updateJsonFile(String searchQuery) {
        try (PreparedStatement stmt = connection.prepareStatement(
                searchQuery != null && !searchQuery.isEmpty()
                        ? "SELECT * FROM InfluencerApprovalDetails WHERE influencer_id = ? OR fullName LIKE ? OR emailAddress LIKE ? ORDER BY influencer_id DESC"
                        : "SELECT * FROM InfluencerApprovalDetails ORDER BY influencer_id DESC")) {

            if (searchQuery != null && !searchQuery.isEmpty()) {
                stmt.setString(1, searchQuery);
                String searchPattern = "%" + searchQuery + "%";
                stmt.setString(2, searchPattern);
                stmt.setString(3, searchPattern);
            }

            ResultSet rs = stmt.executeQuery();
            JSONArray influencersArray = new JSONArray();

            while (rs.next()) {
                JSONObject influencer = new JSONObject();
                influencer.put("influencer_id", rs.getString("influencer_id"));
                influencer.put("fullName", rs.getString("fullName"));
                influencer.put("contactNumber", rs.getString("contactNumber"));
                influencer.put("emailAddress", rs.getString("emailAddress"));
                influencer.put("state", rs.getString("state"));
                influencer.put("city", rs.getString("city"));
                influencer.put("gender", rs.getString("gender"));
                influencer.put("instagramHandle", rs.getString("instagramHandle"));
                influencer.put("youtubeChannel", rs.getString("youtubeChannel"));
                influencer.put("primaryCategory", rs.getString("primaryCategory"));
                influencer.put("otherCategory", rs.getString("otherCategory"));
                influencer.put("engagementRate", rs.getString("engagementRate"));
                influencer.put("longTermPartnership", rs.getString("longTermPartnership"));
                influencer.put("notes", rs.getString("notes"));
                influencer.put("profileImage", rs.getString("profileImage"));
                influencer.put("active_status", rs.getString("active_status"));

                JSONArray images = new JSONArray();
                for (int i = 1; i <= 12; i++) {
                    String image = rs.getString("image" + i);
                    if (image != null && !image.isEmpty()) {
                        images.put(image);
                    }
                }
                influencer.put("images", images);

                influencersArray.put(influencer);
            }

            JSONObject result = new JSONObject();
            result.put("influencers", influencersArray);

            try (FileWriter file = new FileWriter(JSON_FILE_PATH)) {
                file.write(result.toString(4));
            }

            System.out.println("✅ JSON File Updated Successfully");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void destroy() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("Database connection closed successfully.");
            }
            deregisterJDBCDriver();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void deregisterJDBCDriver() {
        Enumeration<Driver> drivers = DriverManager.getDrivers();
        while (drivers.hasMoreElements()) {
            Driver driver = drivers.nextElement();
            try {
                DriverManager.deregisterDriver(driver);
                System.out.println("Deregistered JDBC Driver: " + driver);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        try {
            com.mysql.cj.jdbc.AbandonedConnectionCleanupThread.checkedShutdown();
            System.out.println("MySQL Cleanup Thread Stopped.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
