
// Updated BrandManagement.java
package com.dev.brandmanagementview;

import java.io.IOException;
import java.sql.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/BrandManagement")
public class BrandManagement extends HttpServlet {
    private static final long serialVersionUID = 1L;
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
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            throw new ServletException("Database connection error", e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        List<BrandProfile> brandProfiles = new ArrayList<>();
        PreparedStatement selectStatement = null;
        ResultSet resultSet = null;
        String searchQuery = request.getParameter("search");

        try {
            String selectQuery;
            if (searchQuery != null && !searchQuery.isEmpty()) {
                selectQuery = "SELECT * FROM BrandProfiles WHERE brand_id = ? OR brand_name LIKE ? OR contact_person LIKE ? OR email_address LIKE ? OR phone_number LIKE ? ORDER BY created_at DESC";
                selectStatement = connection.prepareStatement(selectQuery);
                selectStatement.setString(1, searchQuery);
                String searchPattern = "%" + searchQuery + "%";
                selectStatement.setString(2, searchPattern);
                selectStatement.setString(3, searchPattern);
                selectStatement.setString(4, searchPattern);
                selectStatement.setString(5, searchPattern);
            } else {
                selectQuery = "SELECT * FROM BrandProfiles ORDER BY created_at DESC";
                selectStatement = connection.prepareStatement(selectQuery);
            }

            resultSet = selectStatement.executeQuery();

            while (resultSet.next()) {
                brandProfiles.add(new BrandProfile(
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
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        request.setAttribute("brandProfiles", brandProfiles);
        request.setAttribute("searchQuery", searchQuery);
        RequestDispatcher dispatcher = request.getRequestDispatcher("brand_indepth_view.jsp");
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
