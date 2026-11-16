package com.dev.influencermanagementview;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/ToggleInfluencerStatus")
public class ToggleInfluencerStatus extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String influencerId = request.getParameter("influencer_id");
        if (influencerId == null || influencerId.isEmpty()) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("Invalid ID");
            return;
        }

        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            String jdbcUrl = getServletContext().getInitParameter("jdbcurl");
            String dbUser = getServletContext().getInitParameter("username");
            String dbPass = getServletContext().getInitParameter("password");

            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(jdbcUrl, dbUser, dbPass);

            // Toggle status query
            String toggleSQL = "UPDATE InfluencerApprovalDetails SET active_status = IF(active_status='active', 'inactive', 'active') WHERE influencer_id=?";
            stmt = conn.prepareStatement(toggleSQL);
            stmt.setString(1, influencerId);

            int updatedRows = stmt.executeUpdate();
            if (updatedRows > 0) {
                // Fetch the new status
                String statusSQL = "SELECT active_status FROM InfluencerSaveProfiles WHERE influencer_id=?";
                stmt = conn.prepareStatement(statusSQL);
                stmt.setString(1, influencerId);

                var rs = stmt.executeQuery();
                if (rs.next()) {
                    response.getWriter().write(rs.getString("active_status"));
                }
            } else {
                response.getWriter().write("Error updating status");
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().write("Error: " + e.getMessage());
        } finally {
            try { if (stmt != null) stmt.close(); if (conn != null) conn.close(); } catch (Exception ex) { ex.printStackTrace(); }
        }
    }
}
