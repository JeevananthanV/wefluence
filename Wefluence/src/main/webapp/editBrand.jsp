<%@ page import="java.sql.*, com.dev.brandmanagementview.BrandProfile" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String brandId = request.getParameter("brandId");
    BrandProfile brandProfile = null;

    // Fetching DB connection parameters from context
    String jdbcUrl = application.getInitParameter("jdbcurl");
    String dbUser = application.getInitParameter("username");
    String dbPass = application.getInitParameter("password");

    if (brandId != null) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(jdbcUrl, dbUser, dbPass);

            String query = "SELECT * FROM BrandProfiles WHERE brand_id = ?";
            statement = connection.prepareStatement(query);
            statement.setString(1, brandId);
            resultSet = statement.executeQuery();

            if (resultSet.next()) {
                brandProfile = new BrandProfile(
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
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (resultSet != null) resultSet.close();
                if (statement != null) statement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
%>


<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Edit Brand Profile</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 20px;
        }
        form {
            width: 60%;
            margin: 0 auto;
            padding: 20px;
            border: 1px solid #ccc;
            border-radius: 10px;
            background-color: #f9f9f9;
        }
        input, textarea, select {
            width: 100%;
            padding: 8px;
            margin: 5px 0;
            box-sizing: border-box;
        }
        button {
            background-color: #4CAF50;
            color: white;
            padding: 10px 20px;
            border: none;
            border-radius: 5px;
            cursor: pointer;
        }
        button:hover {
            background-color: #45a049;
        }
    </style>
</head>
<body>
    <div class="container">
    <center>
        <h2>Edit Brand Profile</h2>
    </center>
        <form action="UpdateBrandServlet" method="post">
            <input type="hidden" name="brandId" value="<%= (brandProfile != null) ? brandProfile.getBrandId() : "" %>">

            <h3>Basic Information</h3>
            <label>Brand Name:</label>
            <input type="text" name="brandName" required value="<%= (brandProfile != null) ? brandProfile.getBrandName() : "" %>" />

            <label>Website URL:</label>
<input type="url" name="websiteURL" 
       value="<%= (brandProfile != null) ? brandProfile.getWebsiteUrl() : "" %>" />
            
            <label>Industry Category:</label>
            <select name="industryCategory">
                <option value="Fashion" <%= (brandProfile != null && "Fashion".equals(brandProfile.getIndustryCategory())) ? "selected" : "" %>>Fashion</option>
                <option value="Tech" <%= (brandProfile != null && "Tech".equals(brandProfile.getIndustryCategory())) ? "selected" : "" %>>Tech</option>
                <option value="FMCG" <%= (brandProfile != null && "FMCG".equals(brandProfile.getIndustryCategory())) ? "selected" : "" %>>FMCG</option>
                <option value="Beauty" <%= (brandProfile != null && "Beauty".equals(brandProfile.getIndustryCategory())) ? "selected" : "" %>>Beauty</option>
                <option value="Other" <%= (brandProfile != null && "Other".equals(brandProfile.getIndustryCategory())) ? "selected" : "" %>>Other</option>
            </select>

            <label>Custom Industry:</label>
            <input type="text" name="customIndustry" value="<%= (brandProfile != null) ? brandProfile.getCustomIndustry() : "" %>" />

            <label>Company Size:</label>
            <input type="text" name="companySize" value="<%= (brandProfile != null) ? brandProfile.getCompanySize() : "" %>" />

            <h3>Contact Information</h3>
            <label>Contact Person:</label>
            <input type="text" name="contactPerson" required value="<%= (brandProfile != null) ? brandProfile.getContactPerson() : "" %>" />

            <label>Email Address:</label>
            <input type="email" name="emailAddress" required value="<%= (brandProfile != null) ? brandProfile.getEmailAddress() : "" %>" />

            <label>Phone Number:</label>
            <input type="tel" name="phoneNumber" required value="<%= (brandProfile != null) ? brandProfile.getPhoneNumber() : "" %>" />

            <label>Designation:</label>
            <input type="text" name="designation" required value="<%= (brandProfile != null) ? brandProfile.getDesignation() : "" %>" />

            <h3>Campaign Objectives</h3>
            <label>Budget Range:</label>
            <select name="budgetRange">
                <option value="Below 10k" <%= (brandProfile != null && "Below 10k".equals(brandProfile.getBudgetRange())) ? "selected" : "" %>>Below ₹10k</option>
                <option value="10k-20k" <%= (brandProfile != null && "10k-20k".equals(brandProfile.getBudgetRange())) ? "selected" : "" %>>₹10k - ₹20k</option>
                <option value="20k-30k" <%= (brandProfile != null && "20k-30k".equals(brandProfile.getBudgetRange())) ? "selected" : "" %>>₹20k - ₹30k</option>
                <option value="30k-50k" <%= (brandProfile != null && "30k-50k".equals(brandProfile.getBudgetRange())) ? "selected" : "" %>>₹30k - ₹50k</option>
            </select>

            <h3>Social Media Preferences</h3>
            <label>Preferred Social Media Platforms:</label>
            <%
                String selectedPlatforms = (brandProfile != null) ? brandProfile.getSocialMediaPlatforms() : "";
            %>
            <input type="checkbox" name="socialMedia[]" value="Instagram" <%= (selectedPlatforms.contains("Instagram")) ? "checked" : "" %>> Instagram
            <input type="checkbox" name="socialMedia[]" value="YouTube" <%= (selectedPlatforms.contains("YouTube")) ? "checked" : "" %>> YouTube
            <input type="checkbox" name="socialMedia[]" value="Twitter" <%= (selectedPlatforms.contains("Twitter")) ? "checked" : "" %>> Twitter
            <input type="checkbox" name="socialMedia[]" value="LinkedIn" <%= (selectedPlatforms.contains("LinkedIn")) ? "checked" : "" %>> LinkedIn
            <input type="checkbox" name="socialMedia[]" value="Other" <%= (selectedPlatforms.contains("Other")) ? "checked" : "" %>> Other:
            <input type="text" name="socialMediaOther" value="<%= (brandProfile != null) ? brandProfile.getSocialMediaOther() : "" %>" />

            <h3>Special Requirements</h3>
            <label>Notes or Special Requirements:</label>
            <textarea name="specialRequirements"><%= (brandProfile != null) ? brandProfile.getSpecialRequirements() : "" %></textarea>

            <button type="submit">Update Brand</button>
        </form>
    </div>
</body>
</html>
