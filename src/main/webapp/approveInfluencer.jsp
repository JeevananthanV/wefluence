<%@ page import="java.sql.*, javax.servlet.*, javax.servlet.http.*" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    String influencerId = request.getParameter("influencer_id");
    Connection conn = null;
    PreparedStatement stmt = null;
    ResultSet rs = null;

    String jdbcUrl = application.getInitParameter("jdbcurl");
    String dbUser = application.getInitParameter("username");
    String dbPass = application.getInitParameter("password");

    try {
        Class.forName("com.mysql.cj.jdbc.Driver");
        conn = DriverManager.getConnection(jdbcUrl, dbUser, dbPass);

        String query = "SELECT * FROM InfluencerProfiles WHERE influencer_id = ?";
        stmt = conn.prepareStatement(query);
        stmt.setString(1, influencerId);
        rs = stmt.executeQuery();

        if (rs.next()) {
%>

<!DOCTYPE html>
<html>
<head>
    <title>Approve Influencer</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f4f4f4;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
            margin: 0;
            overflow: hidden;
        }

        .container {
            background: #fff;
            padding: 20px;
            border-radius: 10px;
            box-shadow: 0px 0px 10px rgba(0, 0, 0, 0.1);
            max-width: 600px;
            width: 100%;
            max-height: 90vh;
            overflow-y: auto;
        }

        h2 {
            text-align: center;
            color: #333;
        }

        label {
            font-weight: bold;
            display: block;
            margin: 10px 0 5px;
            color: #555;
        }

        input, textarea, select {
            width: 100%;
            padding: 8px;
            margin-bottom: 10px;
            border: 1px solid #ddd;
            border-radius: 5px;
            font-size: 14px;
        }

        textarea {
            resize: vertical;
            min-height: 80px;
        }

        input[type="file"] {
            border: none;
            padding: 5px;
        }

        button {
            background: #007BFF;
            color: #fff;
            border: none;
            padding: 10px;
            width: 100%;
            font-size: 16px;
            border-radius: 5px;
            cursor: pointer;
            transition: 0.3s;
        }

        button:hover {
            background: #0056b3;
        }

        .image-upload-container {
            border: 2px dashed #ccc;
            padding: 10px;
            text-align: center;
            border-radius: 5px;
            margin-bottom: 10px;
        }

    </style>
</head>
<body>

    <div class="container">
        <h2>Approve Influencer</h2>
        <form action="InfluencerSaveServlet" method="post" enctype="multipart/form-data">
            <input type="hidden" name="influencer_id" value="<%= rs.getString("influencer_id") %>">

            <label>Full Name:</label>
            <input type="text" name="fullName" value="<%= rs.getString("fullName") %>" required>

            <label>Contact Number:</label>
            <input type="text" name="contactNumber" value="<%= rs.getString("contactNumber") %>" required>

            <label>Email:</label>
            <input type="email" name="emailAddress" value="<%= rs.getString("emailAddress") %>" required>

            <label>State:</label>
            <input type="text" name="state" value="<%= rs.getString("state") %>" required>

            <label>City:</label>
            <input type="text" name="city" value="<%= rs.getString("city") %>" required>

            <label>Gender:</label>
            <select name="gender" required>
                <option value="Male" <%= rs.getString("gender").equals("Male") ? "selected" : "" %>>Male</option>
                <option value="Female" <%= rs.getString("gender").equals("Female") ? "selected" : "" %>>Female</option>
                <option value="Other" <%= rs.getString("gender").equals("Other") ? "selected" : "" %>>Other</option>
            </select>

            <label>Instagram Handle:</label>
            <input type="text" name="instagramHandle" value="<%= rs.getString("instagramHandle") %>">

            <label>YouTube Channel:</label>
            <input type="text" name="youtubeChannel" value="<%= rs.getString("youtubeChannel") %>">

            <label>Primary Category:</label>
            <input type="text" name="primaryCategory" value="<%= rs.getString("primaryCategory") %>" required>

            <label>Other Category:</label>
            <input type="text" name="otherCategory" value="<%= rs.getString("otherCategory") %>">

            <label>Engagement Rate:</label>
            <input type="text" name="engagementRate" value="<%= rs.getString("engagementRate") %>">

            <label>Long-term Partnership:</label>
            <input type="text" name="longTermPartnership" value="<%= rs.getString("longTermPartnership") %>">

            <label>Notes:</label>
            <textarea name="notes"><%= rs.getString("notes") %></textarea>

            <label>Profile Image:</label>
            <div class="image-upload-container">
                <input type="file" name="profileImage" accept="image/*" required>
            </div>

            <label>Upload 12 Images:</label>
            <div class="image-upload-container">
                <% for (int i = 1; i <= 12; i++) { %>
                    <input type="file" name="images<%= i %>" accept="image/*" >
                <% } %>
            </div>

            <label>Active Status:</label>
            <select name="active_status">
  <option value="Active">Active</option>
  <option value="Inactive">Inactive</option>
</select>


            <button type="submit">Save & Approve</button>
        </form>
    </div>

</body>
</html>

<%
        } else {
            out.println("<h3>No influencer found.</h3>");
        }
    } catch (Exception e) {
        e.printStackTrace();
        out.println("<h3>Error fetching data.</h3>");
    } finally {
        if (rs != null) rs.close();
        if (stmt != null) stmt.close();
        if (conn != null) conn.close();
    }
%>
