package com.dev.modelmanagementview;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Date;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class UpdateModelServlet
 */
@WebServlet("/UpdateModelServlet")
public class UpdateModelServlet extends HttpServlet {
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

        String modelId = request.getParameter("model_id");
        String fullName = request.getParameter("full_name");
        String dob = request.getParameter("dob");
        String age = request.getParameter("age");
        String gender = request.getParameter("gender");
        String contactNumber = request.getParameter("contact_number");
        String emailAddress = request.getParameter("email_address");
        String city = request.getParameter("city");
        String state = request.getParameter("state");
        String height = request.getParameter("height");
        String weight = request.getParameter("weight");
        String hairColor = request.getParameter("hair_color");
        String eyeColor = request.getParameter("eye_color");
        String skinTone = request.getParameter("skin_tone");
        String previousWorks = request.getParameter("previous_works");
        String modelingExperience = request.getParameter("modeling_experience");
        String specializedCategory = request.getParameter("specialized_category");
        String instagramLink = request.getParameter("instagram_link");
        String about = request.getParameter("about");
        String specialSkills = request.getParameter("special_skills");

        PreparedStatement statement = null;

        try {
            String updateQuery = "UPDATE ModelProfiles SET full_name=?, dob=?, age=?, gender=?, contact_number=?, email_address=?, city=?, state=?, height=?, weight=?, hair_color=?, eye_color=?, skin_tone=?, previous_works=?, modeling_experience=?, specialized_category=?, instagram_link=?, about=?, special_skills=? WHERE model_id=?";
            statement = connection.prepareStatement(updateQuery);
            Date sqlDob = Date.valueOf(dob);

            statement.setString(1, fullName);
            statement.setDate(2, sqlDob);
            statement.setInt(3, Integer.parseInt(age));
            statement.setString(4, gender);
            statement.setString(5, contactNumber);
            statement.setString(6, emailAddress);
            statement.setString(7, city);
            statement.setString(8, state);
            statement.setString(9, height);
            statement.setString(10, weight);
            statement.setString(11, hairColor);
            statement.setString(12, eyeColor);
            statement.setString(13, skinTone);
            statement.setString(14, previousWorks);
            statement.setString(15, modelingExperience);
            statement.setString(16, specializedCategory);
            statement.setString(17, instagramLink);
            statement.setString(18, about);
            statement.setString(19, specialSkills);
            statement.setString(20, modelId);

            int rowsUpdated = statement.executeUpdate();

            if (rowsUpdated > 0) {
                // Redirect to ModelManagement if the update is successful
                response.sendRedirect("ModelManagement");
            } else {
                // Send failure message if update fails
                response.getWriter().println("Update failed. No record found with the provided ID.");
            }

        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().println("An error occurred while updating the data.");
        } finally {
            try {
                if (statement != null) {
                    statement.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
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
