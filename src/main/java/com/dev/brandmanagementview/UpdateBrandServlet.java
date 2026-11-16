package com.dev.brandmanagementview;

import java.io.IOException;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/UpdateBrandServlet")
public class UpdateBrandServlet extends HttpServlet {
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
            System.out.println("✅ Database connection established.");

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            throw new ServletException("Database connection error", e);
        }
    }

    // ✅ **GET Request: Fetch Brand Data & Forward to JSP**
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String brandId = request.getParameter("brandId");

        if (brandId == null || brandId.trim().isEmpty()) {
            response.getWriter().println("Error: Brand ID is required.");
            return;
        }

        try (PreparedStatement statement = connection.prepareStatement(
                "SELECT * FROM BrandProfiles WHERE brand_id = ?")) {
            statement.setInt(1, Integer.parseInt(brandId));
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                BrandProfile brandProfile = new BrandProfile(
                        resultSet.getInt("brand_id"),
                        resultSet.getString("brand_name"),
                        resultSet.getString("website_url"),
                        resultSet.getString("industry_category"),
                        resultSet.getString("custom_industry"),
                        resultSet.getString("company_size"),
                        resultSet.getString("contact_person"),
                        resultSet.getString("email_address"),
                        resultSet.getString("phone_number"),
                        resultSet.getString("designation"),
                        resultSet.getString("budget_range"),
                        resultSet.getString("social_media_platforms"),
                        resultSet.getString("social_media_other"),
                        resultSet.getString("special_requirements"),
                        resultSet.getTimestamp("created_at").toLocalDateTime()
                );

                // ✅ Send brand profile data to JSP
                request.setAttribute("brandProfile", brandProfile);
                RequestDispatcher dispatcher = request.getRequestDispatcher("editBrand.jsp");
                dispatcher.forward(request, response);
            } else {
                response.getWriter().println("❌ No brand found with ID: " + brandId);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            response.getWriter().println("❌ Error fetching brand details.");
        } catch (NumberFormatException e) {
            e.printStackTrace();
            response.getWriter().println("❌ Invalid Brand ID format.");
        }
    }

    // ✅ **POST Request: Update Brand Data**
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Retrieve form values
        String brandId = request.getParameter("brandId");
        String brandName = request.getParameter("brandName");
        String websiteURL = request.getParameter("websiteURL");  
        String industryCategory = request.getParameter("industryCategory");
        String customIndustry = request.getParameter("customIndustry");
        String companySize = request.getParameter("companySize");
        String contactPerson = request.getParameter("contactPerson");
        String emailAddress = request.getParameter("emailAddress");
        String phoneNumber = request.getParameter("phoneNumber");
        String designation = request.getParameter("designation");
        String budgetRange = request.getParameter("budgetRange");
        String[] socialMediaArray = request.getParameterValues("socialMedia[]");
        String socialMediaPlatforms = (socialMediaArray != null) ? String.join(",", socialMediaArray) : "";
        String specialRequirements = request.getParameter("specialRequirements");

        // ✅ Debugging: Print all received values before updating
        System.out.println("🔹 Updating brand ID: " + brandId);
        System.out.println("🔹 Received Website URL: " + websiteURL);

        if (brandId == null || brandId.trim().isEmpty()) {
            response.getWriter().println("Error: Brand ID is required.");
            return;
        }

        // ✅ Ensure empty values are handled correctly
        if (websiteURL == null || websiteURL.trim().isEmpty()) {
            websiteURL = "N/A";  // 👈 **Set a default value if empty**
        }

        // ✅ SQL Query for Update
        String updateQuery = "UPDATE BrandProfiles SET brand_name=?, website_url=?, industry_category=?, custom_industry=?, company_size=?, contact_person=?, email_address=?, phone_number=?, designation=?, budget_range=?, social_media_platforms=?, special_requirements=? WHERE brand_id=?";

        try (PreparedStatement statement = connection.prepareStatement(updateQuery)) {

            statement.setString(1, brandName);
            statement.setString(2, websiteURL); // ✅ Ensure the website URL is updated
            statement.setString(3, industryCategory);
            statement.setString(4, customIndustry);
            statement.setString(5, companySize);
            statement.setString(6, contactPerson);
            statement.setString(7, emailAddress);
            statement.setString(8, phoneNumber);
            statement.setString(9, designation);
            statement.setString(10, budgetRange);
            statement.setString(11, socialMediaPlatforms);
            statement.setString(12, specialRequirements);
            statement.setString(13, brandId);

            int rowsUpdated = statement.executeUpdate();

            if (rowsUpdated > 0) {
                System.out.println("✅ Update successful for brand ID: " + brandId);
                response.sendRedirect("BrandManagement");
            } else {
                System.out.println("❌ No record found with ID: " + brandId);
                response.getWriter().println("Update failed. No record found with the provided ID.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            response.getWriter().println("❌ An error occurred while updating the brand data.");
        }
    }

    @Override
    public void destroy() {
        try {
            if (connection != null) {
                connection.close();
                System.out.println("✅ Database connection closed successfully.");
            }

            // ✅ Deregister MySQL Driver to prevent memory leaks
            while (DriverManager.getDrivers().hasMoreElements()) {
                Driver driver = DriverManager.getDrivers().nextElement();
                try {
                    DriverManager.deregisterDriver(driver);
                    System.out.println("✅ Deregistered JDBC Driver: " + driver);
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    System.out.println("❌ Error deregistering driver: " + driver);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("❌ Error closing database connection.");
        }
    }
}
