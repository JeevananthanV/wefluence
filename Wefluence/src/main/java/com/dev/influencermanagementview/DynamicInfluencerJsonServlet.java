package com.dev.influencermanagementview;

import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.File;

@WebServlet("/DynamicInfluencerJsonServlet")
public class DynamicInfluencerJsonServlet extends HttpServlet {

    private static final String JSON_FILE_PATH = "C:\\Users\\jeeva\\eclipse-workspace\\Wefluence\\influencer\\influencers.json"; // Change this path

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json;charset=UTF-8");
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            // Database connection
            String jdbcUrl = getServletContext().getInitParameter("jdbcurl");
            String dbUser = getServletContext().getInitParameter("username");
            String dbPass = getServletContext().getInitParameter("password");

            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(jdbcUrl, dbUser, dbPass);

            // Query active influencers
            String sql = "SELECT * FROM InfluencerApprovalDetails WHERE active_status = 'Active'";
            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();

            JSONArray influencersArray = new JSONArray();

            while (rs.next()) {
                JSONObject influencer = new JSONObject();
                influencer.put("influencer_id", rs.getString("influencer_id"));
                influencer.put("fullName", rs.getString("fullName"));
                influencer.put("contactNumber", rs.getString("contactNumber"));
                influencer.put("emailAddress", rs.getString("emailAddress"));
                influencer.put("location", rs.getString("location"));
                influencer.put("instagramHandle", rs.getString("instagramHandle"));
                influencer.put("youtubeChannel", rs.getString("youtubeChannel"));
                influencer.put("primaryCategory", rs.getString("primaryCategory"));
                influencer.put("otherCategory", rs.getString("otherCategory"));
                influencer.put("engagementRate", rs.getString("engagementRate"));
                influencer.put("longTermPartnership", rs.getString("longTermPartnership"));
                influencer.put("notes", rs.getString("notes"));
                influencer.put("profileImage", rs.getString("profileImage"));

                // Fetch images
                JSONArray images = new JSONArray();
                for (int i = 1; i <= 12; i++) {
                    String image = rs.getString("image" + i);
                    if (image != null && !image.isEmpty()) {
                        images.put(image);
                    }
                }
                influencer.put("images", images);

                influencersArray.put(influencer);
            }

            // Convert to JSON
            JSONObject result = new JSONObject();
            result.put("influencers", influencersArray);

            // Write to JSON file
            writeJsonToFile(result);

            // Return JSON response
            response.getWriter().write(result.toString());

        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().write("{\"error\": \"" + e.getMessage() + "\"}");
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    private void writeJsonToFile(JSONObject json) {
        try (FileWriter file = new FileWriter(JSON_FILE_PATH)) {
            file.write(json.toString(4)); // Pretty-print with indentation
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
