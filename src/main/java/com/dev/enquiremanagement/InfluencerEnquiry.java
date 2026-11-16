package com.dev.enquiremanagement;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.RequestDispatcher;

//@WebServlet("/InfluencerEnquiry")
public class InfluencerEnquiry extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final Logger LOGGER = Logger.getLogger(InfluencerEnquiry.class.getName());
    private Connection connection;

    @Override
    public void init() throws ServletException {
        ServletContext context = getServletContext();
        String driver = context.getInitParameter("driver");
        String jdbcUrl = context.getInitParameter("jdbcurl");
        String username = context.getInitParameter("username");
        String password = context.getInitParameter("password");

        try {
            Class.forName(driver);
            connection = DriverManager.getConnection(jdbcUrl, username, password);
            LOGGER.info("Database connection established successfully.");
        } catch (ClassNotFoundException | SQLException e) {
            LOGGER.log(Level.SEVERE, "Database connection error", e);
            throw new ServletException("Database connection error", e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        List<Enquiry> influencerEnquiries = new ArrayList<>();
        String searchQuery = request.getParameter("search");

        String selectQuery = "SELECT * FROM Enquiries WHERE category = 'influence'";
        if (searchQuery != null && !searchQuery.trim().isEmpty()) {
            selectQuery += " AND (first_name LIKE ? OR last_name LIKE ? OR email LIKE ?)";
        }
        selectQuery += " ORDER BY id DESC";

        try (PreparedStatement pstmt = connection.prepareStatement(selectQuery)) {
            if (searchQuery != null && !searchQuery.trim().isEmpty()) {
                String searchPattern = "%" + searchQuery + "%";
                pstmt.setString(1, searchPattern);
                pstmt.setString(2, searchPattern);
                pstmt.setString(3, searchPattern);
            }

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    influencerEnquiries.add(new Enquiry(
                            rs.getInt("id"),
                            rs.getString("first_name"),
                            rs.getString("last_name"),
                            rs.getString("phone"),
                            rs.getString("email"),
                            rs.getString("message"),
                            rs.getTimestamp("created_at")
                    ));
                }
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error retrieving influencer enquiries", e);
        }

        request.setAttribute("enquiries", influencerEnquiries);
        request.setAttribute("searchQuery", searchQuery);
        RequestDispatcher dispatcher = request.getRequestDispatcher("influencerEnquiry.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    public void destroy() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                LOGGER.info("Database connection closed successfully.");
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error closing database connection", e);
        }
    }
}
