<%@ page import="java.sql.*, com.dev.brandmanagementview.BrandProfile" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    String emailAddress = request.getParameter("emailAddress");
    BrandProfile brandProfile = null;

    String jdbcUrl = application.getInitParameter("jdbcurl");
    String dbUser = application.getInitParameter("username");
    String dbPass = application.getInitParameter("password");

    Connection conn = null;
    PreparedStatement stmt = null;
    ResultSet rs = null;

    try {
        Class.forName("com.mysql.cj.jdbc.Driver");
        conn = DriverManager.getConnection(jdbcUrl, dbUser, dbPass);

        String query = "SELECT * FROM BrandProfiles WHERE email_address = ?";
        stmt = conn.prepareStatement(query);
        stmt.setString(1, emailAddress);
        rs = stmt.executeQuery();

        if (rs.next()) {
            brandProfile = new BrandProfile(
                rs.getInt("brand_id"),
                rs.getString("brand_name"),
                rs.getString("website_url"),
                rs.getString("industry_category"),
                rs.getString("custom_industry"),
                rs.getString("company_size"),
                rs.getString("contact_person"),
                rs.getString("email_address"),
                rs.getString("phone_number"),
                rs.getString("designation"),
                rs.getString("budget_range"),
                rs.getString("social_media_platforms"),
                rs.getString("social_media_other"),
                rs.getString("special_requirements"),
                rs.getTimestamp("created_at").toLocalDateTime()
            );
        }
    } catch (Exception e) {
        e.printStackTrace();
    } finally {
        if (conn != null) try { conn.close(); } catch (SQLException ignore) {}
    }
%>

<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Brand Form Sign-Up</title>
  <style>
    /* Keep your existing styles */
    :root {
      --txtb: rgb(0, 0, 0);
      --txtw: rgb(255, 255, 255);
      --primary-color: #191919;
      --back-color: #333333;
      --second-back-color: #666666;
      --mainheading: #ffd700;
      --subheadsimg: white;
      --paracolor: rgb(220, 220, 220);
      --mainheadfont: "Montserrat", serif;
      --subheadfont: "Montserrat", serif;
      --parafont: "Montserrat", serif;
      --white: #ffffff;
    }

    * {
      margin: 0;
      padding: 0;
      box-sizing: border-box;
      font-family: var(--parafont);
      color: var(--txtw);
    }

    body {
      min-height: 100vh;
      display: flex;
      justify-content: center;
      align-items: center;
      background-color: var(--primary-color);
    }

    .container {
      display: flex;
      width: 80%;
      max-width: 1200px;
      overflow: hidden;
      border-radius: 20px;
      box-shadow: 0 8px 15px rgba(0, 0, 0, 0.2);
    }

    .form-container {
      width: 100%;
      background-color: var(--back-color);
      padding: 40px;
    }

    .form-header {
      text-align: center;
      margin-bottom: 30px;
    }

    .form-header h2 {
      font-size: 28px;
      color: var(--mainheading);
      font-weight: 600;
    }

    .sub-section-title {
      font-size: 22px;
      color: var(--mainheading);
      font-weight: bold;
      margin-bottom: 20px;
      text-align: left;
    }

    .form-group {
      margin-bottom: 24px;
    }

    label {
      display: block;
      font-size: 16px;
      margin-bottom: 8px;
      color: var(--subheadsimg);
    }

    .required::after {
      content: " *";
      color: red;
    }

    .optional {
      font-size: 14px;
      color: var(--second-back-color);
    }

    .form-input, select {
      margin: 16px 0;
      border: none;
      font-size: 18px;
      outline: none;
      width: 100%;
      border-bottom: 1px solid var(--white);
      background-color: transparent;
      color: var(--txtw);
    }

    .form-input::placeholder {
      color: var(--second-back-color);
    }

    select option {
      color: black;
    }

    /* Checkbox Row Styling */
    .checkbox-group {
      display: flex;
      flex-wrap: wrap;
      gap: 15px;
      margin: 10px 0;
    }

    /* Button Styles */
    .submit-btn {
      width: 100%;
      padding: 14px;
      font-size: 18px;
      font-weight: bold;
      color: black;
      background-color: var(--white);
      border: none;
      border-radius: 8px;
      cursor: pointer;
      transition: background-color 0.3s ease;
    }

    .submit-btn:hover {
      background-color: var(--mainheading);
    }

    .checkbox-group {
      display: flex;
      flex-wrap: wrap;
      gap: 20px;
    }

    .styled-checkbox {
      display: flex;
      align-items: center;
      position: relative;
      cursor: pointer;
    }

    .styled-checkbox input[type="checkbox"] {
      appearance: none;
      width: 20px;
      height: 20px;
      background-color: var(--second-back-color);
      border: 2px solid var(--white);
      border-radius: 5px;
      transition: all 0.2s ease-in-out;
      cursor: pointer;
    }

    .styled-checkbox input[type="checkbox"]:checked {
      background-color: var(--mainheading);
      border-color: var(--mainheading);
      box-shadow: 0px 0px 5px var(--mainheading);
    }

    .styled-checkbox span {
      margin-left: 10px;
      color: var(--txtw);
      font-size: 16px;
    }

    .small-input {
      margin-left: 10px;
      border: none;
      font-size: 14px;
      width: 200px;
      border-bottom: 1px solid var(--white);
      background-color: transparent;
      color: var(--txtw);
    }

    .small-input::placeholder {
      color: var(--second-back-color);
    }
  </style>
</head>
<body>
  <div class="container">
    <div class="form-container">
      <div class="form-header">
        <h2>Brand Form</h2>
      </div>
      <form action="BrandSaveServlet" method="post">
 		<input type="hidden" name="state" value="Active">
        <input type="hidden" name="brandId" value="<%= (brandProfile != null) ? brandProfile.getBrandId() : "" %>">

        <div class="form-group">
          <label for="brand-name" class="required">Brand Name:</label>
          <input type="text" id="brand-name" name="brandName" class="form-input" required 
                 value="<%= (brandProfile != null) ? brandProfile.getBrandName() : "" %>" 
                 placeholder="Enter brand name" />
        </div>

        <div class="form-group">
          <label for="website-url">Website URL: <span class="optional">(Optional)</span></label>
          <input type="url" id="website-url" name="websiteUrl" class="form-input"
                 value="<%= (brandProfile != null) ? brandProfile.getWebsiteUrl() : "" %>" 
                 placeholder="Enter website URL" />
        </div>

        <div class="form-group">
          <label for="industry-category" class="required">Industry Category:</label>
          <select id="industry-category" name="industryCategory" required onchange="handleIndustryChange()">
            <option value="">Select Industry</option>
            <option value="Fashion" <%= (brandProfile != null && "Fashion".equals(brandProfile.getIndustryCategory())) ? "selected" : "" %>>Fashion</option>
            <option value="Tech" <%= (brandProfile != null && "Tech".equals(brandProfile.getIndustryCategory())) ? "selected" : "" %>>Tech</option>
            <option value="FMCG" <%= (brandProfile != null && "FMCG".equals(brandProfile.getIndustryCategory())) ? "selected" : "" %>>FMCG</option>
            <option value="Beauty" <%= (brandProfile != null && "Beauty".equals(brandProfile.getIndustryCategory())) ? "selected" : "" %>>Beauty</option>
            <option value="Other" <%= (brandProfile != null && "Other".equals(brandProfile.getIndustryCategory())) ? "selected" : "" %>>Other</option>
          </select>
          <input type="text" id="custom-industry" name="customIndustry" class="small-input"
                 value="<%= (brandProfile != null) ? brandProfile.getCustomIndustry() : "" %>"
                 placeholder="Please specify" style="<%= (brandProfile != null && "Other".equals(brandProfile.getIndustryCategory())) ? "display:block;" : "display:none;" %>">
        </div>

        <div class="form-group">
          <label for="company-size" class="required">Company Size:</label>
          <select id="company-size" name="companySize" required>
            <option value="">Select Company Size</option>
            <option value="1-10" <%= (brandProfile != null && "1-10".equals(brandProfile.getCompanySize())) ? "selected" : "" %>>1-10</option>
            <option value="11-50" <%= (brandProfile != null && "11-50".equals(brandProfile.getCompanySize())) ? "selected" : "" %>>11-50</option>
            <option value="51-200" <%= (brandProfile != null && "51-200".equals(brandProfile.getCompanySize())) ? "selected" : "" %>>51-200</option>
            <option value="201-500" <%= (brandProfile != null && "201-500".equals(brandProfile.getCompanySize())) ? "selected" : "" %>>201-500</option>
            <option value="500+" <%= (brandProfile != null && "500+".equals(brandProfile.getCompanySize())) ? "selected" : "" %>>500+</option>
          </select>
        </div>

        <div class="form-group">
          <label for="contact-person" class="required">Contact Person Name:</label>
          <input type="text" id="contact-person" name="contactPerson" class="form-input" required 
                 value="<%= (brandProfile != null) ? brandProfile.getContactPerson() : "" %>"
                 placeholder="Enter contact person name" />
        </div>

        <div class="form-group">
          <label for="email-address" class="required">Email Address:</label>
          <input type="email" id="email-address" name="emailAddress" class="form-input" required 
                 value="<%= (brandProfile != null) ? brandProfile.getEmailAddress() : "" %>"
                 placeholder="Enter email address" />
        </div>

        <div class="form-group">
          <label for="phone-number" class="required">Phone Number:</label>
          <input type="tel" id="phone-number" name="phoneNumber" class="form-input" required 
                 value="<%= (brandProfile != null) ? brandProfile.getPhoneNumber() : "" %>"
                 placeholder="Enter phone number" />
        </div>

        <div class="form-group">
          <label for="designation" class="required">Designation:</label>
          <input type="text" id="designation" name="designation" class="form-input" required 
                 value="<%= (brandProfile != null) ? brandProfile.getDesignation() : "" %>"
                 placeholder="Enter designation" />
        </div>
<!-- Campaign Objective Section -->
<h3 class="sub-section-title">Campaign Objective Section</h3>

<div class="form-group">
  <label for="budget-range" class="required">Budget Range:</label>
  <select id="budget-range" name="budgetRange" required>
    <option value="">Select Budget Range</option>
    <option value="Below 10k" <%= (brandProfile != null && "Below 10k".equals(brandProfile.getBudgetRange())) ? "selected" : "" %>>Below ₹10k</option>
    <option value="10k-20k" <%= (brandProfile != null && "10k-20k".equals(brandProfile.getBudgetRange())) ? "selected" : "" %>>₹10k - ₹20k</option>
    <option value="20k-30k" <%= (brandProfile != null && "20k-30k".equals(brandProfile.getBudgetRange())) ? "selected" : "" %>>₹20k - ₹30k</option>
    <option value="30k-50k" <%= (brandProfile != null && "30k-50k".equals(brandProfile.getBudgetRange())) ? "selected" : "" %>>₹30k - ₹50k</option>
  </select>
</div>

<div class="form-group">
  <label>Preferred Social Media Platforms:<span class="required"></span></label>
  <div class="checkbox-group">
    <%
      String selectedPlatforms = (brandProfile != null) ? brandProfile.getSocialMediaPlatforms() : "";
      String[] platformsArray = (selectedPlatforms != null) ? selectedPlatforms.split(",") : new String[0];
    %>

    <label class="styled-checkbox">
      <input type="checkbox" name="socialMedia[]" value="Instagram" <%= (selectedPlatforms.contains("Instagram")) ? "checked" : "" %>>
      <span>Instagram</span>
    </label>

    <label class="styled-checkbox">
      <input type="checkbox" name="socialMedia[]" value="YouTube" <%= (selectedPlatforms.contains("YouTube")) ? "checked" : "" %>>
      <span>YouTube</span>
    </label>

    <label class="styled-checkbox">
      <input type="checkbox" name="socialMedia[]" value="Twitter" <%= (selectedPlatforms.contains("Twitter")) ? "checked" : "" %>>
      <span>Twitter</span>
    </label>

    <label class="styled-checkbox">
      <input type="checkbox" name="socialMedia[]" value="LinkedIn" <%= (selectedPlatforms.contains("LinkedIn")) ? "checked" : "" %>>
      <span>LinkedIn</span>
    </label>

    <label class="styled-checkbox">
      <input type="checkbox" name="socialMedia[]" value="Other" <%= (selectedPlatforms.contains("Other")) ? "checked" : "" %>>
      <span>Other:</span>
      <input type="text" name="socialMediaOther" class="small-input" 
             value="<%= (brandProfile != null) ? brandProfile.getSocialMediaOther() : "" %>" 
             placeholder="Please specify">
    </label>
  </div>
</div>

<div class="form-group">
  <label for="special-requirements">Notes or Special Requirements: <span class="optional">(Optional)</span></label>
  <textarea id="special-requirements" name="specialRequirements" class="form-input"
            placeholder="Enter any special requirements or notes"><%= (brandProfile != null) ? brandProfile.getSpecialRequirements() : "" %></textarea>
</div>
<div style="display: flex; align-items: center; margin-bottom: 20px;">
    <label for="isActive" style="font-weight: bold; margin-right: 5px;">Active:</label>
    <input type="checkbox" id="isActive" name="isActive" checked style="width: 20px; height: 20px; cursor: pointer;">
</div>
        <button type="submit" class="submit-btn">Submit</button>
      </form>
    </div>
  </div>
</body>
<script>
  function handleIndustryChange() {
    const industrySelect = document.getElementById("industry-category");
    const customIndustryInput = document.getElementById("custom-industry");
    if (industrySelect.value === "Other") {
      customIndustryInput.style.display = "block";
      customIndustryInput.required = true;
    } else {
      customIndustryInput.style.display = "none";
      customIndustryInput.required = false;
    }
  }
</script>
</html>
