package com.dev.brandmanagementview;

import java.io.*;
import java.sql.*;
import java.util.*;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import org.json.*;

@WebServlet("/ActiveBrand")
public class ActiveBrand extends HttpServlet {
    private static final String JSON_FILE_PATH = "C:\\Users\\jeeva\\OneDrive\\New folder\\Desktop\\Wefluence\\src\\main\\webapp\\json\\brands.json";
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

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            throw new ServletException("Database connection error", e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String searchQuery = request.getParameter("search");
        updateJsonFile(searchQuery);
        request.getRequestDispatcher("viewBrands.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String brandId = request.getParameter("brand_id");

        if (brandId == null || brandId.isEmpty()) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("Invalid ID");
            return;
        }

        try (PreparedStatement stmt = connection.prepareStatement(
                "UPDATE BrandCampaignDetails SET is_active = IF(is_active=1, 0, 1) WHERE brand_id=?")) {

            stmt.setString(1, brandId);
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
                        ? "SELECT * FROM BrandCampaignDetails WHERE brand_id = ? OR brand_name LIKE ? OR email_address LIKE ? OR industry_category LIKE ? ORDER BY brand_id DESC"
                        : "SELECT * FROM BrandCampaignDetails ORDER BY brand_id DESC")) { // ✅ Order by brand_id

            if (searchQuery != null && !searchQuery.isEmpty()) {
                stmt.setString(1, searchQuery);
                String searchPattern = "%" + searchQuery + "%";
                stmt.setString(2, searchPattern);
                stmt.setString(3, searchPattern);
                stmt.setString(4, searchPattern);
            }

            ResultSet rs = stmt.executeQuery();
            JSONArray brandsArray = new JSONArray();

            while (rs.next()) {
                JSONObject brand = new JSONObject();
                brand.put("brand_id", rs.getString("brand_id"));
                brand.put("brand_name", rs.getString("brand_name"));
                brand.put("website_url", rs.getString("website_url"));
                brand.put("industry_category", rs.getString("industry_category"));
                brand.put("custom_industry", rs.getString("custom_industry"));
                brand.put("company_size", rs.getString("company_size"));
                brand.put("contact_person", rs.getString("contact_person"));
                brand.put("email_address", rs.getString("email_address"));
                brand.put("phone_number", rs.getString("phone_number"));
                brand.put("designation", rs.getString("designation"));
                brand.put("budget_range", rs.getString("budget_range"));
                brand.put("social_media_platforms", rs.getString("social_media_platforms"));
                brand.put("social_media_other", rs.getString("social_media_other"));
                brand.put("special_requirements", rs.getString("special_requirements"));
                brand.put("is_active", rs.getBoolean("is_active"));

                brandsArray.put(brand);
            }

            // Save JSON data to file
            JSONObject result = new JSONObject();
            result.put("brands", brandsArray);

            try (FileWriter file = new FileWriter(JSON_FILE_PATH)) {
                file.write(result.toString(4)); // ✅ Pretty-print JSON
            }

            System.out.println("✅ JSON File Updated Successfully");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void destroy() {
        try {
            // Close the database connection
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("Database connection closed successfully.");
            }

            // Unregister MySQL JDBC driver
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

        // Stop MySQL Cleanup Thread
        try {
            com.mysql.cj.jdbc.AbandonedConnectionCleanupThread.checkedShutdown();
            System.out.println("MySQL Cleanup Thread Stopped.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
