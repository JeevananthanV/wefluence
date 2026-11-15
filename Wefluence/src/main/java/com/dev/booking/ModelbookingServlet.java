package com.dev.booking;

import java.io.IOException;
import java.sql.*;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/ModelbookingServlet")
public class ModelbookingServlet extends HttpServlet {
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
            System.out.println("Database connection initialized successfully.");
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
                System.out.println("Database connection closed successfully.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String modelId = request.getParameter("model-id");
        String modelName = request.getParameter("model-name");
        String brandName = request.getParameter("brand-name");
        String bookingPerson = request.getParameter("booking-person");
        String contactEmail = request.getParameter("contact-email");
        String contactPhone = request.getParameter("contact-phone");
        String shootStartDateStr = request.getParameter("shoot-start-date");
        String shootEndDateStr = request.getParameter("shoot-end-date");
        String location = request.getParameter("location");
        String duration = request.getParameter("duration");
        String specialRequests = request.getParameter("special-requests");

        System.out.println("Start Date Input: " + shootStartDateStr);
        System.out.println("End Date Input: " + shootEndDateStr);

        if (shootStartDateStr == null || shootStartDateStr.isEmpty()
                || shootEndDateStr == null || shootEndDateStr.isEmpty()) {
            sendError(response, "Start and end dates must be provided.");
            return;
        }

        try {
            Date shootStartDate = Date.valueOf(shootStartDateStr);
            Date shootEndDate = Date.valueOf(shootEndDateStr);

            String sql = "INSERT INTO ModelBooking (model_id, model_name, brand_name, booking_person, contact_email, contact_phone, shoot_start_date, shoot_end_date, location, duration, special_requests, booking_timestamp) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

            try (PreparedStatement stmt = connection.prepareStatement(sql)) {
                stmt.setString(1, modelId);
                stmt.setString(2, modelName);
                stmt.setString(3, brandName);
                stmt.setString(4, bookingPerson);
                stmt.setString(5, contactEmail);
                stmt.setString(6, contactPhone);
                stmt.setDate(7, shootStartDate);
                stmt.setDate(8, shootEndDate);
                stmt.setString(9, location);
                stmt.setInt(10, Integer.parseInt(duration));
                stmt.setString(11, specialRequests);
                stmt.setTimestamp(12, new Timestamp(System.currentTimeMillis()));

                stmt.executeUpdate();

                response.setContentType("text/html");
                response.getWriter().println("<script type=\"text/javascript\">");
                response.getWriter().println("alert('Model booking successful!');");
                response.getWriter().println("window.location.href = 'modeling.html';");
                response.getWriter().println("</script>");
            }
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            sendError(response, "Invalid date format. Please use yyyy-MM-dd.");
        } catch (Exception e) {
            e.printStackTrace();
            sendError(response, "Error while booking model: " + (e.getMessage() != null ? e.getMessage().replace("'", "\\'") : "Unknown error"));
        }
    }

    private void sendError(HttpServletResponse response, String message) throws IOException {
        response.setContentType("text/html");
        response.getWriter().println("<script type=\"text/javascript\">");
        response.getWriter().println("alert('" + message + "');");
        response.getWriter().println("window.location.href = 'modeling.html';");
        response.getWriter().println("</script>");
    }
}
