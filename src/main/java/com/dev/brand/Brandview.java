package com.dev.brand;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.RequestDispatcher;

//@WebServlet("/Brandview")
public class Brandview extends HttpServlet {
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

        List<BrandSignupDataview> brandLeads = new ArrayList<>();
        PreparedStatement selectStatement = null;
        ResultSet resultSet = null;

        String searchQuery = request.getParameter("search");

        try {
            String selectQuery;
            if (searchQuery != null && !searchQuery.isEmpty()) {
                selectQuery = "SELECT * FROM BrandSignupdata WHERE brandName LIKE ? OR contactPerson LIKE ? OR emailAddress LIKE ? ORDER BY id DESC";
                selectStatement = connection.prepareStatement(selectQuery);
                String searchPattern = "%" + searchQuery + "%";
                selectStatement.setString(1, searchPattern);
                selectStatement.setString(2, searchPattern);
                selectStatement.setString(3, searchPattern);
            } else {
                selectQuery = "SELECT * FROM BrandSignupdata ORDER BY id DESC";
                selectStatement = connection.prepareStatement(selectQuery);
            }

            resultSet = selectStatement.executeQuery();

            while (resultSet.next()) {
                brandLeads.add(new BrandSignupDataview(
                        resultSet.getString("brandName"),
                        resultSet.getString("contactPerson"),
                        resultSet.getString("emailAddress"),
                        resultSet.getString("phoneNumber")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (resultSet != null) resultSet.close();
                if (selectStatement != null) selectStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        // Pass data to JSP
        request.setAttribute("brandLeads", brandLeads);
        request.setAttribute("searchQuery", searchQuery);
        RequestDispatcher dispatcher = request.getRequestDispatcher("brandleads.jsp");
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
