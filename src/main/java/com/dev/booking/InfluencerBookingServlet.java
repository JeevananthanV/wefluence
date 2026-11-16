package com.dev.booking;

import java.io.IOException;
import java.sql.*;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/InfluencerBookingServlet")
public class InfluencerBookingServlet extends HttpServlet {
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
            System.out.println("Database connection initialized successfully for InfluencerBookingServlet.");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            throw new ServletException("Database connection error", e);
        }
    }

    @Override
    public void destroy() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("Database connection closed successfully for InfluencerBookingServlet.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String influencerId = request.getParameter("influencer-id");
        String influencerName = request.getParameter("influencer-name");
        String contactName = request.getParameter("contact-name");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        String platform = request.getParameter("platform");
        String profileLink = request.getParameter("profile-link");
        String budgetStr = request.getParameter("budget");
        String startDateStr = request.getParameter("start-date");
        String endDateStr = request.getParameter("end-date");
        String details = request.getParameter("details");

        System.out.println("Influencer Booking Request Received: " + influencerId + " - " + influencerName);

        if (startDateStr == null || endDateStr == null || startDateStr.isEmpty() || endDateStr.isEmpty()) {
            sendError(response, "Start and end dates must be provided.");
            return;
        }

        try {
            Date startDate = Date.valueOf(startDateStr);
            Date endDate = Date.valueOf(endDateStr);
            double budget = (budgetStr == null || budgetStr.isEmpty()) ? 0.0 : Double.parseDouble(budgetStr);

            String sql = "INSERT INTO InfluencerBooking (influencer_id, influencer_name, contact_name, email, phone, platform, profile_link, budget, start_date, end_date, details, booking_timestamp) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

            try (PreparedStatement stmt = connection.prepareStatement(sql)) {
                stmt.setString(1, influencerId);
                stmt.setString(2, influencerName);
                stmt.setString(3, contactName);
                stmt.setString(4, email);
                stmt.setString(5, phone);
                stmt.setString(6, platform);
                stmt.setString(7, profileLink);
                stmt.setDouble(8, budget);
                stmt.setDate(9, startDate);
                stmt.setDate(10, endDate);
                stmt.setString(11, details);
                stmt.setTimestamp(12, new Timestamp(System.currentTimeMillis()));

                stmt.executeUpdate();

                response.setContentType("text/html");
                response.getWriter().println("<script type=\"text/javascript\">");
                response.getWriter().println("alert('Influencer booking successful!');");
                response.getWriter().println("window.location.href = 'influencing.html';");
                response.getWriter().println("</script>");
            }

        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            sendError(response, "Invalid date format. Please use yyyy-MM-dd.");
        } catch (Exception e) {
            e.printStackTrace();
            sendError(response, "Error while booking influencer: " + (e.getMessage() != null ? e.getMessage().replace("'", "\\'") : "Unknown error"));
        }
    }

    private void sendError(HttpServletResponse response, String message) throws IOException {
        response.setContentType("text/html");
        response.getWriter().println("<script type=\"text/javascript\">");
        response.getWriter().println("alert('" + message + "');");
        response.getWriter().println("window.location.href = 'influencing.html';");
        response.getWriter().println("</script>");
    }
}
