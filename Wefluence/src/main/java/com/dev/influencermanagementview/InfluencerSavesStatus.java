package com.dev.influencermanagementview;

import java.io.IOException;
import java.sql.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

@WebServlet("/InfluencerSavesStatus")
public class InfluencerSavesStatus extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            try {
                // Database connection
                String jdbcUrl = getServletContext().getInitParameter("jdbcurl");
                String dbUser = getServletContext().getInitParameter("username");
                String dbPass = getServletContext().getInitParameter("password");

                Class.forName("com.mysql.cj.jdbc.Driver");

                try (Connection conn = DriverManager.getConnection(jdbcUrl, dbUser, dbPass);
                     PreparedStatement stmt = conn.prepareStatement("SELECT * FROM InfluencerApprovalDetails");
                     ResultSet rs = stmt.executeQuery()) {

                    // HTML structure
                    out.println("<html><head><title>Influencer List</title>");
                    out.println("<style>"
                            + "body { font-family: Arial, sans-serif; margin: 20px; }"
                            + "table { width: 100%; border-collapse: collapse; }"
                            + "th, td { border: 1px solid black; padding: 10px; text-align: center; }"
                            + "th { background-color: #f2f2f2; }"
                            + "img { width: 80px; height: 80px; object-fit: cover; border-radius: 5px; }"
                            + "button { background: #007bff; color: white; border: none; padding: 5px 10px; cursor: pointer; }"
                            + "button:hover { background: #0056b3; }"
                            + "</style></head><body>");

                    out.println("<h2>Influencer List</h2>");
                    out.println("<table><tr>"
                            + "<th>ID</th><th>Full Name</th><th>Contact</th><th>Email</th><th>Location</th>"
                            + "<th>Instagram</th><th>YouTube</th><th>Primary Category</th><th>Other Category</th>"
                            + "<th>Engagement Rate</th><th>Long-Term Partnership</th><th>Notes</th>"
                            + "<th>Profile Image</th><th>Images</th><th>Status</th><th>Toggle Status</th></tr>");

                    while (rs.next()) {
                        String influencerId = rs.getString("influencer_id");
                        String fullName = rs.getString("fullName");
                        String contact = rs.getString("contactNumber");
                        String email = rs.getString("emailAddress");
                        String state = rs.getString("state");
                        String city = rs.getString("city");
                        String location = state + ", " + city; // Corrected location format
                        String instagram = rs.getString("instagramHandle");
                        String youtube = rs.getString("youtubeChannel");
                        String primaryCategory = rs.getString("primaryCategory");
                        String otherCategory = rs.getString("otherCategory");
                        String engagementRate = rs.getString("engagementRate");
                        String longTermPartnership = rs.getString("longTermPartnership");
                        String notes = rs.getString("notes");
                        String profileImage = rs.getString("profileImage");
                        String status = rs.getString("active_status");

                        // Retrieve 12 images
                        StringBuilder imagesHtml = new StringBuilder();
                        for (int i = 1; i <= 12; i++) {
                            String imagePath = rs.getString("image" + i);
                            if (imagePath != null && !imagePath.isEmpty()) {
                                imagesHtml.append("<a href='").append(imagePath)
                                        .append("' target='_blank'><img src='").append(imagePath)
                                        .append("'></a>");
                            }
                        }

                        // Display influencer details
                        out.println("<tr><td>" + influencerId + "</td>"
                                + "<td>" + fullName + "</td>"
                                + "<td>" + contact + "</td>"
                                + "<td>" + email + "</td>"
                                + "<td>" + location + "</td>"
                                + "<td>" + instagram + "</td>"
                                + "<td>" + youtube + "</td>"
                                + "<td>" + primaryCategory + "</td>"
                                + "<td>" + otherCategory + "</td>"
                                + "<td>" + engagementRate + "</td>"
                                + "<td>" + longTermPartnership + "</td>"
                                + "<td>" + notes + "</td>"
                                + "<td><a href='" + profileImage + "' target='_blank'><img src='" + profileImage + "'></a></td>"
                                + "<td><div style='display: grid; grid-template-columns: repeat(4, 1fr); gap: 5px;'>" + imagesHtml + "</div></td>"
                                + "<td id='status_" + influencerId + "'>" + status + "</td>"
                                + "<td><button onclick=\"toggleStatus('" + influencerId + "')\">Toggle</button></td></tr>");
                    }

                    out.println("</table>");

                    // JavaScript for toggling status
                    out.println("<script>"
                            + "function toggleStatus(id) {"
                            + "    let confirmation = confirm('Are you sure you want to change the status?');"
                            + "    if (!confirmation) return;"
                            + "    fetch('/Wefluencesignup/ToggleInfluencerStatus', {"
                            + "        method: 'POST',"
                            + "        headers: { 'Content-Type': 'application/x-www-form-urlencoded' },"
                            + "        body: 'influencer_id=' + id"
                            + "    })"
                            + "    .then(response => response.text())"
                            + "    .then(status => {"
                            + "        if (status === 'error') {"
                            + "            alert('Failed to update status.');"
                            + "        } else {"
                            + "            document.getElementById('status_' + id).innerText = status;"
                            + "            alert('Status updated successfully!');"
                            + "        }"
                            + "    })"
                            + "    .catch(error => {"
                            + "        alert('Error updating status.');"
                            + "        console.error('Error:', error);"
                            + "    });"
                            + "}"
                            + "</script>");

                    out.println("</body></html>");
                }
            } catch (Exception e) {
                e.printStackTrace();
                out.println("<h3 style='color:red;'>Error: " + e.getMessage() + "</h3>");
            }
        }
    }
}
