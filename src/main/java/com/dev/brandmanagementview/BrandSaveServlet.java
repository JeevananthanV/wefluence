package com.dev.brandmanagementview;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/BrandSaveServlet")
public class BrandSaveServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
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
            System.out.println("✅ Database connection established.");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            throw new ServletException("Database connection error", e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            // Fetch form values
            String brandId = request.getParameter("brandId");
            String emailAddress = request.getParameter("emailAddress");

            // If brandId is empty, fetch from email
            if (brandId == null || brandId.isEmpty()) {
                brandId = fetchBrandIdByEmail(emailAddress);
            }

            // If no brand found, return error
            if (brandId == null) {
                sendErrorResponse(response, "No matching brand found for email: " + emailAddress);
                return;
            }

            // Check if brand exists
            boolean recordExists = checkIfBrandExists(brandId);
            saveBrandData(request, brandId, recordExists);

            // Success response
            sendSuccessResponse(response, "Brand details saved successfully! Brand ID: " + brandId);

        } catch (Exception e) {
            e.printStackTrace();
            sendErrorResponse(response, "Error: " + e.getMessage());
        }
    }

    // Fetch brand_id from BrandDetails using email_address
    private String fetchBrandIdByEmail(String emailAddress) throws SQLException {
        String brandId = null;
        String query = "SELECT brand_id FROM BrandDetails WHERE email_address = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, emailAddress);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    brandId = rs.getString("brand_id");
                }
            }
        }
        return brandId;
    }

    // Check if the brand exists in BrandCampaignDetails
    private boolean checkIfBrandExists(String brandId) throws SQLException {
        String query = "SELECT brand_id FROM BrandCampaignDetails WHERE brand_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, brandId);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next();
            }
        }
    }

    // Save Brand Data (Insert/Update)
    private void saveBrandData(HttpServletRequest request, String brandId, boolean recordExists) throws SQLException {
        String sql = recordExists ? getUpdateQuery() : getInsertQuery();

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            setQueryParameters(stmt, request, brandId, recordExists);
            stmt.executeUpdate();
        }
    }

    // SQL Query for Update
    private String getUpdateQuery() {
        return "UPDATE BrandCampaignDetails SET brand_name=?, website_url=?, industry_category=?, custom_industry=?, " +
                "company_size=?, contact_person=?, email_address=?, phone_number=?, designation=?, budget_range=?, " +
                "social_media_platforms=?, social_media_other=?, special_requirements=?, is_active=? WHERE brand_id=?";
    }

    // SQL Query for Insert
    private String getInsertQuery() {
        return "INSERT INTO BrandCampaignDetails (brand_id, brand_name, website_url, industry_category, custom_industry, " +
                "company_size, contact_person, email_address, phone_number, designation, budget_range, social_media_platforms, " +
                "social_media_other, special_requirements, is_active) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    }

    // Set SQL Parameters
    private void setQueryParameters(PreparedStatement stmt, HttpServletRequest request, String brandId, boolean recordExists)
            throws SQLException {

        // Set brand_id for insert
        if (!recordExists) {
            stmt.setString(1, brandId);
        }

        stmt.setString(recordExists ? 1 : 2, request.getParameter("brandName"));
        stmt.setString(recordExists ? 2 : 3, request.getParameter("websiteUrl"));
        stmt.setString(recordExists ? 3 : 4, request.getParameter("industryCategory"));
        stmt.setString(recordExists ? 4 : 5, request.getParameter("customIndustry"));
        stmt.setString(recordExists ? 5 : 6, request.getParameter("companySize"));
        stmt.setString(recordExists ? 6 : 7, request.getParameter("contactPerson"));
        stmt.setString(recordExists ? 7 : 8, request.getParameter("emailAddress"));
        stmt.setString(recordExists ? 8 : 9, request.getParameter("phoneNumber"));
        stmt.setString(recordExists ? 9 : 10, request.getParameter("designation"));
        stmt.setString(recordExists ? 10 : 11, request.getParameter("budgetRange"));

        // Handle social media platforms (avoid null values)
        String[] socialMediaArray = request.getParameterValues("socialMedia[]");
        String socialMediaPlatforms = (socialMediaArray != null) ? String.join(",", socialMediaArray) : "";
        stmt.setString(recordExists ? 11 : 12, socialMediaPlatforms);
        stmt.setString(recordExists ? 12 : 13, request.getParameter("socialMediaOther"));
        stmt.setString(recordExists ? 13 : 14, request.getParameter("specialRequirements"));

        // Fix `is_active` handling (default to `false` if null)
        boolean activeStatus = request.getParameter("isActive") != null;
        stmt.setBoolean(recordExists ? 14 : 15, activeStatus);

        // Set brand_id for update
        if (recordExists) {
            stmt.setString(15, brandId);
        }
    }

    // Send Success Response
    private void sendSuccessResponse(HttpServletResponse response, String message) throws IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.println("<script>alert('" + message + "'); window.location='ActiveBrand';</script>");
    }

    // Send Error Response
    private void sendErrorResponse(HttpServletResponse response, String message) throws IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.println("<script>alert('" + message + "'); window.location='ActiveBrand';</script>");
    }

    @Override
    public void destroy() {
        try {
            if (connection != null) {
                connection.close();
                System.out.println("✅ Database connection closed.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
