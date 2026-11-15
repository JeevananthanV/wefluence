package com.dev.booking;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import javax.servlet.RequestDispatcher;

@WebServlet("/InfluencerBooking")
public class InfluencerBooking extends HttpServlet {
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

        List<InfluencerBookingData> bookings = new ArrayList<>();
        PreparedStatement selectStatement = null;
        ResultSet resultSet = null;

        String searchQuery = request.getParameter("search");

        try {
            String selectQuery;
            if (searchQuery != null && !searchQuery.isEmpty()) {
                selectQuery = "SELECT * FROM InfluencerBooking WHERE influencer_name LIKE ? OR platform LIKE ? OR contact_name LIKE ? ORDER BY booking_timestamp DESC";
                selectStatement = connection.prepareStatement(selectQuery);
                String pattern = "%" + searchQuery + "%";
                selectStatement.setString(1, pattern);
                selectStatement.setString(2, pattern);
                selectStatement.setString(3, pattern);
            } else {
                selectQuery = "SELECT * FROM InfluencerBooking ORDER BY booking_timestamp DESC";
                selectStatement = connection.prepareStatement(selectQuery);
            }

            resultSet = selectStatement.executeQuery();

            while (resultSet.next()) {
                bookings.add(new InfluencerBookingData(
                        resultSet.getString("influencer_id"),
                        resultSet.getString("influencer_name"),
                        resultSet.getString("contact_name"),
                        resultSet.getString("email"),
                        resultSet.getString("phone"),
                        resultSet.getString("platform"),
                        resultSet.getString("profile_link"),
                        resultSet.getDouble("budget"),
                        resultSet.getDate("start_date"),
                        resultSet.getDate("end_date"),
                        resultSet.getString("details"),
                        resultSet.getTimestamp("booking_timestamp")
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

        request.setAttribute("bookings", bookings);
        request.setAttribute("searchQuery", searchQuery);
        RequestDispatcher dispatcher = request.getRequestDispatcher("influencerbookings.jsp");
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
