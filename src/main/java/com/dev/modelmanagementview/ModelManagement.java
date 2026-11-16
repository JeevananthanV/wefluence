package com.dev.modelmanagementview;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dev.modelmanagementview.ModelProfileData.ModelingExperience;

import javax.servlet.RequestDispatcher;

@WebServlet("/ModelManagement")
public class ModelManagement extends HttpServlet {
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
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        List<ModelProfileData> modelProfiles = new ArrayList<>();
        PreparedStatement selectStatement = null;
        ResultSet resultSet = null;

        // Get search parameter from request (optional)
        String searchQuery = request.getParameter("search");

        try {
            String selectQuery;

            if (searchQuery != null && !searchQuery.isEmpty()) {
                // Search query including model_id (as a String)
                selectQuery = "SELECT * FROM ModelProfiles WHERE model_id = ? OR full_name LIKE ? OR email_address LIKE ? OR city LIKE ? OR state LIKE ? ORDER BY created_at DESC";
                selectStatement = connection.prepareStatement(selectQuery);

                // Set model_id as an exact match (since it's a UUID, not numeric)
                selectStatement.setString(1, searchQuery); // model_id (Exact match)

                // Set wildcard search parameters for text fields
                String searchPattern = "%" + searchQuery + "%";
                selectStatement.setString(2, searchPattern); // full_name
                selectStatement.setString(3, searchPattern); // email_address
                selectStatement.setString(4, searchPattern); // city
                selectStatement.setString(5, searchPattern); // state

            } else {
                // Default query if no search term is provided
                selectQuery = "SELECT * FROM ModelProfiles ORDER BY created_at DESC";
                selectStatement = connection.prepareStatement(selectQuery);
            }

            resultSet = selectStatement.executeQuery();

            while (resultSet.next()) {
                // Extract image file names from paths
                String photo1 = resultSet.getString("portfolio_photo1");
                String photo2 = resultSet.getString("portfolio_photo2");
                String photo1FileName = (photo1 != null) ? new File(photo1).getName() : "N/A";
                String photo2FileName = (photo2 != null) ? new File(photo2).getName() : "N/A";
                String instagramLink = resultSet.getString("instagram_link");
                if (instagramLink != null && !instagramLink.startsWith("http")) {
                    instagramLink = "https://" + instagramLink;
                } else if (instagramLink == null) {
                    instagramLink = "N/A";
                }

                


                modelProfiles.add(new ModelProfileData(
                        resultSet.getString("model_id") != null ? resultSet.getString("model_id") : "N/A",
                        resultSet.getString("full_name") != null ? resultSet.getString("full_name") : "N/A",
                        resultSet.getDate("dob") != null ? resultSet.getDate("dob").toLocalDate() : null,
                        resultSet.getInt("age"),
                        resultSet.getString("gender") != null ? resultSet.getString("gender") : "N/A",
                        resultSet.getString("contact_number") != null ? resultSet.getString("contact_number") : "N/A",
                        resultSet.getString("email_address") != null ? resultSet.getString("email_address") : "N/A",
                        resultSet.getString("city") != null ? resultSet.getString("city") : "N/A",
                        resultSet.getString("state") != null ? resultSet.getString("state") : "N/A",
                        resultSet.getString("height") != null ? resultSet.getString("height") : "N/A",
                        resultSet.getString("weight") != null ? resultSet.getString("weight") : "N/A",
                        resultSet.getString("hair_color") != null ? resultSet.getString("hair_color") : "N/A",
                        resultSet.getString("eye_color") != null ? resultSet.getString("eye_color") : "N/A",
                        resultSet.getString("skin_tone") != null ? resultSet.getString("skin_tone") : "N/A",
                        resultSet.getString("previous_works") != null ? resultSet.getString("previous_works") : "N/A",
                        resultSet.getString("modeling_experience") != null
                                ? ModelingExperience.valueOf(resultSet.getString("modeling_experience").toUpperCase())
                                : ModelingExperience.FRESHER, // Default to FRESHER if null
                        resultSet.getString("specialized_category") != null
                                ? resultSet.getString("specialized_category")
                                : "N/A",
                                instagramLink,

//                                this.instagramLink = (instagramLink != null && !instagramLink.isEmpty()) ? instagramLink : "N/A",
//                        resultSet.getString("instagram_link") != null ? resultSet.getString("instagram_link") : "N/A",
                        resultSet.getString("about") != null ? resultSet.getString("about") : "N/A",
                        resultSet.getString("special_skills") != null ? resultSet.getString("special_skills") : "N/A",
                        photo1FileName,
                        photo2FileName,
                        resultSet.getTimestamp("created_at") != null
                                ? resultSet.getTimestamp("created_at").toLocalDateTime()
                                : null));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Forward results to JSP for display
        request.setAttribute("modelProfiles", modelProfiles);
        RequestDispatcher dispatcher = request.getRequestDispatcher("ModelManagement.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    public void destroy() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("Database connection closed successfully.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
