package com.dev.login;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

@WebServlet("/ModelFullForm")
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, // 2MB
                 maxFileSize = 1024 * 1024 * 10,      // 10MB
                 maxRequestSize = 1024 * 1024 * 50)   // 50MB
public class ModelFullForm extends HttpServlet {
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
        String fullName = request.getParameter("fullName");
        String dob = request.getParameter("dob");
        int age = Integer.parseInt(request.getParameter("age"));
        String gender = request.getParameter("gender");
        String contactNumber = request.getParameter("contactNumber");
        String emailAddress = request.getParameter("emailAddress");
        String city = request.getParameter("city");
        String state = request.getParameter("state");
        String height = request.getParameter("height");
        String weight = request.getParameter("weight");
        String hairColor = request.getParameter("hairColor");
        String eyeColor = request.getParameter("eyeColor");
        String skinTone = request.getParameter("skinTone");
        String previousWorks = request.getParameter("previousWorks");
        String modelingExperience = request.getParameter("modelingExperience");
        String instagramLink = request.getParameter("instagramLink");
        String about = request.getParameter("about");
        String specialSkills = request.getParameter("specialSkills");

        Part filePart1 = request.getPart("portfolioPhoto1");
        Part filePart2 = request.getPart("portfolioPhoto2");

        String uploadDir = "C:\\Users\\jeeva\\eclipse-workspace\\Wefluence\\Uploads\\";
        File uploadFolder = new File(uploadDir);
        if (!uploadFolder.exists()) uploadFolder.mkdir();

        String fileName1 = Paths.get(filePart1.getSubmittedFileName()).getFileName().toString();
        String filePath1 = uploadDir + File.separator + fileName1;
        filePart1.write(filePath1);

        String fileName2 = Paths.get(filePart2.getSubmittedFileName()).getFileName().toString();
        String filePath2 = uploadDir + File.separator + fileName2;
        filePart2.write(filePath2);

        String[] categories = request.getParameterValues("category[]");
        String specializedCategory = (categories != null) ? String.join(",", categories) : "";

        PreparedStatement preparedStatement = null;
        PreparedStatement idStatement = null;
        PreparedStatement insertStatement = null;
        ResultSet resultSet = null;

        try {
        	 String getLastIdQuery = "SELECT model_id FROM ModelProfiles ORDER BY model_id DESC LIMIT 1";
             preparedStatement = connection.prepareStatement(getLastIdQuery);
             resultSet = preparedStatement.executeQuery();

             String newModelId = "model_0001"; // Default for first entry
             if (resultSet.next()) {
                 String lastId = resultSet.getString("model_id");
                 int lastNumber = Integer.parseInt(lastId.replace("model_", "")); // Extract numeric part
                 newModelId = String.format("model_%04d", lastNumber + 1); // Increment
             }
            String insertQuery = "INSERT INTO ModelProfiles (model_id,full_name, dob, age, gender, contact_number, email_address, city, state, portfolio_photo1, portfolio_photo2, height, weight, hair_color, eye_color, skin_tone, previous_works, modeling_experience, specialized_category, instagram_link, about, special_skills) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?)";
            
            preparedStatement = connection.prepareStatement(insertQuery);
            preparedStatement.setString(1, newModelId);
            preparedStatement.setString(2, fullName);
            preparedStatement.setString(3, dob);
            preparedStatement.setInt(4, age);
            preparedStatement.setString(5, gender);
            preparedStatement.setString(6, contactNumber);
            preparedStatement.setString(7, emailAddress);
            preparedStatement.setString(8, city);
            preparedStatement.setString(9, state);
            preparedStatement.setString(10, filePath1);
            preparedStatement.setString(11, filePath2);
            preparedStatement.setString(12, height);
            preparedStatement.setString(13, weight);
            preparedStatement.setString(14, hairColor);
            preparedStatement.setString(15, eyeColor);
            preparedStatement.setString(16, skinTone);
            preparedStatement.setString(17, previousWorks);
            preparedStatement.setString(18, modelingExperience);
            preparedStatement.setString(19, specializedCategory);
            preparedStatement.setString(20, instagramLink);
            preparedStatement.setString(21, about);
            preparedStatement.setString(22, specialSkills);

            int result = preparedStatement.executeUpdate();

            response.setContentType("text/html");
            PrintWriter out = response.getWriter();

            if (result > 0) {
                // Success alert and redirection
                out.println("<script type=\"text/javascript\">");
                out.println("alert('Model profile submitted successfully!');");
                out.println("window.location.href='ModelManagement.jsp';");
                out.println("</script>");
            } else {
                // Failure alert and redirection
                out.println("<script type=\"text/javascript\">");
                out.println("alert('Model profile submission failed. Please try again.');");
                out.println("window.location.href='error.html';");
                out.println("</script>");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (preparedStatement != null) preparedStatement.close(); // Close statement
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void destroy() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("Database connection closed.");
            }

            // Unregister JDBC driver to prevent memory leaks
            DriverManager.deregisterDriver(DriverManager.getDrivers().nextElement());
            System.out.println("JDBC driver deregistered.");

            // Stop MySQL abandoned connection cleanup thread
            com.mysql.cj.jdbc.AbandonedConnectionCleanupThread.uncheckedShutdown();
            System.out.println("MySQL AbandonedConnectionCleanupThread stopped.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
