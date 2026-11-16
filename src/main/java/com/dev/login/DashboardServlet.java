package com.dev.login;

import java.io.IOException;
import java.sql.*;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/DashboardServlet")
public class DashboardServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private Connection connection;

    @Override
    public void init() throws ServletException {
        try {
            // Database Connection Setup
            ServletContext context = getServletContext();
            String driver = context.getInitParameter("driver");
            String jdbcUrl = context.getInitParameter("jdbcurl");
            String username = context.getInitParameter("username");
            String password = context.getInitParameter("password");

            Class.forName(driver);
            connection = DriverManager.getConnection(jdbcUrl, username, password);
            System.out.println("✅ Database connection established.");
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("❌ Database Connection Failed: " + e.getMessage());
            throw new ServletException("❌ Database connection error", e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            System.out.println("📊 Fetching dashboard data...");

            // Fetching Lead Counts
            request.setAttribute("totalModelLeads", getCount("ModelSignUpData", null, null));
            request.setAttribute("totalBrandLeads", getCount("BrandSignUpData", null, null));
            request.setAttribute("totalInfluencerLeads", getCount("InfluencerSignUpData", null, null));

            // Fetching Profile Counts
            request.setAttribute("totalModelProfiles", getCount("ModelProfiles", null, null));
            request.setAttribute("totalBrandProfiles", getCount("BrandProfiles", null, null));
            request.setAttribute("totalInfluencerProfiles", getCount("InfluencerProfiles", null, null));

            // Fetching Enquiries
            request.setAttribute("totalEnquiries", getCount("Enquiries", null, null));
            request.setAttribute("modelEnquiries", getCount("Enquiries", "category", "model"));
            request.setAttribute("influencerEnquiries", getCount("Enquiries", "category", "influence"));
            request.setAttribute("brandEnquiries", getCount("Enquiries", "category", "brand"));

            // Fetching Active & Inactive Users
            request.setAttribute("activeBrands", getCount("BrandCampaignDetails", "is_active", true));
            request.setAttribute("inactiveBrands", getCount("BrandCampaignDetails", "is_active", false));

            request.setAttribute("activeInfluencers", getCount("InfluencerApprovalDetails", "active_status", "Active"));
            request.setAttribute("inactiveInfluencers", getCount("InfluencerApprovalDetails", "active_status", "Inactive"));

            request.setAttribute("activeModels", getCount("ModelApprovalDetails", "status", "Active"));
            request.setAttribute("inactiveModels", getCount("ModelApprovalDetails", "status", "Inactive"));

            // Set last updated time
            request.setAttribute("lastUpdated", new java.util.Date());

            System.out.println("📊 Dashboard data successfully retrieved.");

            request.getRequestDispatcher("dashboard.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "❌ Error fetching dashboard data.");
        }
    }

    private int getCount(String tableName, String columnName, Object value) {
        String query;
        if (columnName == null) {
            query = "SELECT COUNT(*) FROM " + tableName;
        } else {
            query = "SELECT COUNT(*) FROM " + tableName + " WHERE " + columnName + " = ?";
        }

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            if (columnName != null) {
                if (value instanceof Boolean) {
                    stmt.setBoolean(1, (Boolean) value);
                } else if (value instanceof String) {
                    stmt.setString(1, (String) value);
                }
            }

            System.out.println("Executing Query: " + query);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("❌ SQL Error in: " + query);
        }
        return 0;
    }

    @Override
    public void destroy() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("✅ Database connection closed.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("❌ Error closing database connection.");
        }
    }
}
