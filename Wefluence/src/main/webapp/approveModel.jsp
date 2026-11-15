<%@ page import="java.sql.*" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    String modelId = request.getParameter("model_id");
    Connection conn = null;
    PreparedStatement stmt = null;
    ResultSet resultSet = null;

    String jdbcUrl = application.getInitParameter("jdbcurl");
    String dbUser = application.getInitParameter("username");
    String dbPass = application.getInitParameter("password");

    try {
        Class.forName("com.mysql.cj.jdbc.Driver");
        conn = DriverManager.getConnection(jdbcUrl, dbUser, dbPass);

        String query = "SELECT * FROM ModelProfiles WHERE model_id = ?";
        stmt = conn.prepareStatement(query);
        stmt.setString(1, modelId);
        resultSet = stmt.executeQuery();
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Approve Model</title>
    <style>
        body { font-family: 'Poppins', sans-serif; background-color: #f4f4f9; padding: 20px; }
        .container { max-width: 800px; margin: auto; background: #fff; padding: 20px; box-shadow: 0px 4px 10px rgba(0, 0, 0, 0.1); border-radius: 10px; }
        h2 { text-align: center; color: #007BFF; }
        .form-group { margin-bottom: 15px; }
        label { font-weight: bold; }
        input, select, textarea { width: 100%; padding: 8px; margin-top: 5px; border: 1px solid #ccc; border-radius: 5px; }
        .btn { background-color: #28A745; color: white; padding: 10px 15px; border: none; border-radius: 5px; cursor: pointer; }
        .btn:hover { opacity: 0.9; }
        .uploaded-image { text-align: center; margin-top: 15px; }
        .required:after { content: " *"; color: red; }
    </style>
</head>
<body>

<div class="container">
    <h2>Approve Model</h2>

    <%
        if (resultSet.next()) {
    %>

    <form action="ModelSaveServlet" method="post" enctype="multipart/form-data">
        <div class="form-group">
            <label class="required">Model ID:</label>
            <input type="text" name="model_id" value="<%= resultSet.getString("model_id") %>" readonly required>
        </div>

        <div class="form-group">
            <label class="required">Full Name:</label>
            <input type="text" name="full_name" value="<%= resultSet.getString("full_name") %>" required>
        </div>

        <div class="form-group">
            <label class="required">Date of Birth:</label>
            <input type="date" name="dob" value="<%= resultSet.getString("dob") %>" required>
        </div>

        <div class="form-group">
            <label class="required">Age:</label>
            <input type="number" name="age" value="<%= resultSet.getInt("age") %>" required min="1" max="120">
        </div>

        <div class="form-group">
            <label class="required">Gender:</label>
            <input type="text" name="gender" value="<%= resultSet.getString("gender") %>" required>
        </div>

        <div class="form-group">
            <label>Contact Number:</label>
            <input type="text" name="contact_number" value="<%= resultSet.getString("contact_number") %>">
        </div>

        <div class="form-group">
            <label class="required">Email Address:</label>
            <input type="email" name="email_address" value="<%= resultSet.getString("email_address") %>" required>
        </div>

        <div class="form-group">
            <label>City:</label>
            <input type="text" name="city" value="<%= resultSet.getString("city") %>">
        </div>

        <div class="form-group">
            <label>State:</label>
            <input type="text" name="state" value="<%= resultSet.getString("state") %>">
        </div>

        <div class="form-group">
            <label>Height:</label>
            <input type="text" name="height" value="<%= resultSet.getString("height") %>">
        </div>

        <div class="form-group">
            <label>Weight:</label>
            <input type="text" name="weight" value="<%= resultSet.getString("weight") %>">
        </div>

        <div class="form-group">
            <label class="required">Specialized Category:</label>
            <input type="text" name="specialized_category" value="<%= resultSet.getString("specialized_category") %>" required>
        </div>

        <div class="form-group">
            <label>About:</label>
            <textarea name="about"><%= resultSet.getString("about") %></textarea>
        </div>

        <div class="form-group">
            <label>Special Skills:</label>
            <textarea name="special_skills"><%= resultSet.getString("special_skills") %></textarea>
        </div>

        <!-- Profile Image Upload Form -->
        <div class="form-group">
            <label class="required">Upload Profile Image:</label>
            <input type="file" name="profile_image" accept="image/*" required>
        </div>

        <!-- 12 Additional Image Upload Inputs -->
        <div class="form-group">
            <label>Upload Image 1:</label>
            <input type="file" name="image1" accept="image/*">
        </div>
        <div class="form-group">
            <label>Upload Image 2:</label>
            <input type="file" name="image2" accept="image/*">
        </div>
        <div class="form-group">
            <label>Upload Image 3:</label>
            <input type="file" name="image3" accept="image/*">
        </div>
        <!-- Repeat the above structure for image4 to image12 -->
        <div class="form-group">
            <label>Upload Image 4:</label>
            <input type="file" name="image4" accept="image/*">
        </div>
        <div class="form-group">
            <label>Upload Image 5:</label>
            <input type="file" name="image5" accept="image/*">
        </div>
        <div class="form-group">
            <label>Upload Image 6:</label>
            <input type="file" name="image6" accept="image/*">
        </div>
        <div class="form-group">
            <label>Upload Image 7:</label>
            <input type="file" name="image7" accept="image/*">
        </div>
        <div class="form-group">
            <label>Upload Image 8:</label>
            <input type="file" name="image8" accept="image/*">
        </div>
        <div class="form-group">
            <label>Upload Image 9:</label>
            <input type="file" name="image9" accept="image/*">
        </div>
        <div class="form-group">
            <label>Upload Image 10:</label>
            <input type="file" name="image10" accept="image/*">
        </div>
        <div class="form-group">
            <label>Upload Image 11:</label>
            <input type="file" name="image11" accept="image/*">
        </div>
        <div class="form-group">
            <label>Upload Image 12:</label>
            <input type="file" name="image12" accept="image/*">
        </div>
    <label>Active Status:</label>
            <select name="status" required>
                <option value="Active">Active</option>
                <option value="Inactive">Inactive</option>
            </select>

        <button type="submit" class="btn">Save Model</button>
    </form>

    <%
        } else {
            out.println("<h3>Model not found!</h3>");
        }
    %>

</div>

</body>
</html>

<%
    } catch (Exception e) {
        e.printStackTrace();
        out.println("<h3>Error fetching model data!</h3>");
    } finally {
        if (resultSet != null) try { resultSet.close(); } catch (SQLException ignore) {}
        if (stmt != null) try { stmt.close(); } catch (SQLException ignore) {}
        if (conn != null) try { conn.close(); } catch (SQLException ignore) {}
    }
%>
