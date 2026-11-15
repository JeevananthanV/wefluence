package com.dev.login;

import java.io.IOException;
import java.io.PrintWriter;
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

@WebServlet("/BrandIndepthForm")
public class BrandIndepthForm extends HttpServlet {
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

        String brandName = request.getParameter("brandName");
        String websiteURL = request.getParameter("websiteURL");
        String industryCategory = request.getParameter("industryCategory");
        String industryOther = request.getParameter("IndustryOther");  // Updated column name
        String companySize = request.getParameter("companySize");
        String contactPerson = request.getParameter("contactPerson");
        String emailAddress = request.getParameter("emailAddress");
        String phoneNumber = request.getParameter("phoneNumber");
        String designation = request.getParameter("designation");
        String budgetRange = request.getParameter("budgetRange");
        String specialRequirements = request.getParameter("specialRequirements");

        // Handling multiple social media platforms
        String[] socialMedia = request.getParameterValues("socialMedia[]");
        String socialMediaPlatforms = String.join(",", socialMedia != null ? socialMedia : new String[0]);

        String socialMediaOther = request.getParameter("socialMediaOther");
        if (socialMediaOther != null && !socialMediaOther.trim().isEmpty()) {
            socialMediaPlatforms += ",Other:" + socialMediaOther;
        }

        try {
//            Class.forName("com.mysql.cj.jdbc.Driver");
//            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/your_database", "your_username", "your_password");
//            
            String insertQuery = "INSERT INTO BrandProfiles (brand_name, website_url, industry_category, custom_industry, " +
                    "company_size, contact_person, email_address, phone_number, designation, budget_range, " +
                    "social_media_platforms, social_media_other, special_requirements) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

            PreparedStatement preparedStatement = connection.prepareStatement(insertQuery);
            preparedStatement.setString(1, brandName);
            preparedStatement.setString(2, websiteURL);
            preparedStatement.setString(3, industryCategory);
            preparedStatement.setString(4, industryOther);
            preparedStatement.setString(5, companySize);
            preparedStatement.setString(6, contactPerson);
            preparedStatement.setString(7, emailAddress);
            preparedStatement.setString(8, phoneNumber);
            preparedStatement.setString(9, designation);
            preparedStatement.setString(10, budgetRange);
            preparedStatement.setString(11, socialMediaPlatforms);
            preparedStatement.setString(12, socialMediaOther);
            preparedStatement.setString(13, specialRequirements);

            int result = preparedStatement.executeUpdate();

            response.setContentType("text/html");
            PrintWriter out = response.getWriter();

            if (result > 0) {
                // Success alert and redirection
                out.println("<script type=\"text/javascript\">");
                out.println("alert('Brand profile submitted successfully!');");
                out.println("window.location.href='branding.html';");
                out.println("</script>");
            } else {
                // Failure alert and redirection
                out.println("<script type=\"text/javascript\">");
                out.println("alert('Braand profile submission failed. Please try again.');");
                out.println("window.location.href='error.html';");
                out.println("</script>");
            }
        } catch (SQLException e) {
            e.printStackTrace();
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
