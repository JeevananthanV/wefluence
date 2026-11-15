<%@ page import="java.sql.*, com.dev.modelmanagementview.ModelProfileData" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String modelId = request.getParameter("model_id");
    ModelProfileData model = null;

    // Fetching DB credentials from context parameters
    String jdbcUrl = application.getInitParameter("jdbcurl");
    String dbUser = application.getInitParameter("username");
    String dbPass = application.getInitParameter("password");

    if (modelId != null) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(jdbcUrl, dbUser, dbPass);

            String query = "SELECT * FROM ModelProfiles WHERE model_id = ?";
            statement = connection.prepareStatement(query);
            statement.setString(1, modelId);
            resultSet = statement.executeQuery();

            if (resultSet.next()) {
                model = new ModelProfileData(
                    resultSet.getString("model_id"),
                    resultSet.getString("full_name"),
                    resultSet.getDate("dob").toLocalDate(),
                    resultSet.getInt("age"),
                    resultSet.getString("gender"),
                    resultSet.getString("contact_number"),
                    resultSet.getString("email_address"),
                    resultSet.getString("city"),
                    resultSet.getString("state"),
                    resultSet.getString("height"),
                    resultSet.getString("weight"),
                    resultSet.getString("hair_color"),
                    resultSet.getString("eye_color"),
                    resultSet.getString("skin_tone"),
                    resultSet.getString("previous_works"),
                    ModelProfileData.ModelingExperience.valueOf(resultSet.getString("modeling_experience").toUpperCase()),
                    resultSet.getString("specialized_category"),
                    resultSet.getString("instagram_link"),
                    resultSet.getString("about"),
                    resultSet.getString("special_skills"),
                    resultSet.getString("portfolio_photo1"),
                    resultSet.getString("portfolio_photo2"),
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
<html>
<head>
    <title>Edit Model Profile</title>
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
<center>
<h2>Edit Model Profile</h2>
</center>

<% if (model != null) { %>
<form action="UpdateModelServlet" method="post">
    <input type="hidden" name="model_id" value="<%= model.getModelId() %>">

    <label>Full Name:</label>
    <input type="text" name="full_name" value="<%= model.getFullName() %>">

    <label>Date of Birth:</label>
    <input type="date" name="dob" value="<%= model.getDob() %>">

    <label>Age:</label>
    <input type="number" name="age" value="<%= model.getAge() %>">

    <label>Gender:</label>
    <input type="text" name="gender" value="<%= model.getGender() %>">

    <label>Contact Number:</label>
    <input type="text" name="contact_number" value="<%= model.getPhoneNumber() %>">

    <label>Email Address:</label>
    <input type="email" name="email_address" value="<%= model.getEmailAddress() %>">

    <label>City:</label>
    <input type="text" name="city" value="<%= model.getCity() %>">

    <label>State:</label>
    <input type="text" name="state" value="<%= model.getState() %>">

    <label>Height:</label>
    <input type="text" name="height" value="<%= model.getHeight() %>">

    <label>Weight:</label>
    <input type="text" name="weight" value="<%= model.getWeight() %>">

    <label>Hair Color:</label>
    <input type="text" name="hair_color" value="<%= model.getHairColor() %>">

    <label>Eye Color:</label>
    <input type="text" name="eye_color" value="<%= model.getEyeColor() %>">

    <label>Skin Tone:</label>
    <input type="text" name="skin_tone" value="<%= model.getSkinTone() %>">

    <label>Previous Works:</label>
    <textarea name="previous_works"><%= model.getPreviousWorks() %></textarea>

    <label>Modeling Experience:</label>
    <select name="modeling_experience">
        <option value="FRESHER" <%= model.getModelingExperience().toString().equals("FRESHER") ? "selected" : "" %>>Fresher</option>
        <option value="BEGINNER" <%= model.getModelingExperience().toString().equals("BEGINNER") ? "selected" : "" %>>Beginner</option>
        <option value="INTERMEDIATE" <%= model.getModelingExperience().toString().equals("INTERMEDIATE") ? "selected" : "" %>>Intermediate</option>
        <option value="EXPERIENCED" <%= model.getModelingExperience().toString().equals("EXPERIENCED") ? "selected" : "" %>>Experienced</option>
    </select>

    <label>Specialized Category:</label>
    <input type="text" name="specialized_category" value="<%= model.getSpecializedCategory() %>">

    <label>Instagram Link:</label>
    <input type="text" name="instagram_link" value="<%= model.getInstagramLink() %>">

    <label>About:</label>
    <textarea name="about"><%= model.getAbout() %></textarea>

    <label>Special Skills:</label>
    <textarea name="special_skills"><%= model.getSpecialSkills() %></textarea>

    <button type="submit">Update Profile</button>
</form>
<% } else { %>
    <p>Model not found.</p>
<% } %>

</body>
</html>