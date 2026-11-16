package com.dev.modelmanagementview;

import java.io.*;
import java.sql.*;
import java.util.*;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import org.json.*;

@WebServlet("/ActiveModel")
public class ActiveModel extends HttpServlet {
    private static final String JSON_FILE_PATH = "C:\\Users\\jeeva\\OneDrive\\New folder\\Desktop\\Wefluence\\src\\main\\webapp\\json\\models.json";
    private Connection connection;

    @Override
    public void init() throws ServletException {
        try {
            ServletContext context = getServletContext();
            String driver = context.getInitParameter("driver");
            String jdbcUrl = context.getInitParameter("jdbcurl");
            String username = context.getInitParameter("username");
            String password = context.getInitParameter("password");

            Class.forName(driver);
            connection = DriverManager.getConnection(jdbcUrl, username, password);
            System.out.println("✅ Database connected successfully.");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            throw new ServletException("Database connection error", e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String searchQuery = request.getParameter("search");
        updateJsonFile(searchQuery);
        request.getRequestDispatcher("viewModels.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String modelId = request.getParameter("model_id");
        System.out.println("🔍 Received model_id: " + modelId);

        if (modelId == null || modelId.trim().isEmpty()) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("Invalid ID");
            return;
        }

        try (PreparedStatement stmt = connection.prepareStatement(
                "UPDATE ModelApprovalDetails SET status = IF(status='Active', 'Inactive', 'Active') WHERE model_id=?")) {

            stmt.setString(1, modelId);
            int updatedRows = stmt.executeUpdate();
            System.out.println("🔄 Rows updated: " + updatedRows);

            if (updatedRows > 0) {
                updateJsonFile(null);
                response.getWriter().write("success");
            } else {
                response.getWriter().write("Error updating status");
            }

        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().write("Error: " + e.getMessage());
        }
    }

    private void updateJsonFile(String searchQuery) {
        System.out.println("🛠️ updateJsonFile called with searchQuery: " + searchQuery);
        try (PreparedStatement stmt = connection.prepareStatement(
                searchQuery != null && !searchQuery.trim().isEmpty()
                        ? "SELECT * FROM ModelApprovalDetails WHERE model_id = ? OR full_name LIKE ? OR email_address LIKE ? OR city LIKE ? ORDER BY model_id DESC"
                        : "SELECT * FROM ModelApprovalDetails ORDER BY model_id DESC")) {

            if (searchQuery != null && !searchQuery.trim().isEmpty()) {
                stmt.setString(1, searchQuery);
                String searchPattern = "%" + searchQuery + "%";
                stmt.setString(2, searchPattern);
                stmt.setString(3, searchPattern);
                stmt.setString(4, searchPattern);
            }

            ResultSet rs = stmt.executeQuery();
            JSONArray modelsArray = new JSONArray();

            while (rs.next()) {
                JSONObject model = new JSONObject();
                model.put("model_id", rs.getString("model_id"));
                model.put("full_name", rs.getString("full_name"));
                model.put("dob", rs.getString("dob"));
                model.put("age", rs.getInt("age"));
                model.put("gender", rs.getString("gender"));
                model.put("contact_number", rs.getString("contact_number"));
                model.put("email_address", rs.getString("email_address"));
                model.put("city", rs.getString("city"));
                model.put("state", rs.getString("state"));
                model.put("height", rs.getString("height"));
                model.put("weight", rs.getString("weight"));
                model.put("specialized_category", rs.getString("specialized_category"));
                model.put("about", rs.getString("about"));
                model.put("special_skills", rs.getString("special_skills"));
                model.put("profile_image", rs.getString("profile_image"));
                model.put("status", rs.getString("status"));

                JSONArray images = new JSONArray();
                for (int i = 1; i <= 12; i++) {
                    String image = rs.getString("image" + i);
                    if (image != null && !image.trim().isEmpty()) {
                        images.put(image);
                    }
                }
                model.put("images", images);

                modelsArray.put(model);
            }

            JSONObject result = new JSONObject();
            result.put("models", modelsArray);

            try (FileWriter file = new FileWriter(JSON_FILE_PATH)) {
                file.write(result.toString(4));
                System.out.println("✅ JSON File Updated Successfully at " + JSON_FILE_PATH);
            } catch (IOException e) {
                System.err.println("❌ Error writing JSON file: " + e.getMessage());
                e.printStackTrace();
            }

        } catch (Exception e) {
            System.err.println("❌ Error generating JSON: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void destroy() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("🛑 Database connection closed successfully.");
            }
            deregisterJDBCDriver();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void deregisterJDBCDriver() {
        Enumeration<Driver> drivers = DriverManager.getDrivers();
        while (drivers.hasMoreElements()) {
            Driver driver = drivers.nextElement();
            try {
                DriverManager.deregisterDriver(driver);
                System.out.println("🧹 Deregistered JDBC Driver: " + driver);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        try {
            com.mysql.cj.jdbc.AbandonedConnectionCleanupThread.checkedShutdown();
            System.out.println("🧼 MySQL Cleanup Thread Stopped.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
