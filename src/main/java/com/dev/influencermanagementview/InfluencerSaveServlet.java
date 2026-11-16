package com.dev.influencermanagementview;

import java.io.File;
import java.io.IOException;
import java.sql.*;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/InfluencerSaveServlet")
@MultipartConfig(
    fileSizeThreshold = 1024 * 1024 * 2,
    maxFileSize = 1024 * 1024 * 10,
    maxRequestSize = 1024 * 1024 * 50
)
public class InfluencerSaveServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final String UPLOAD_DIR = "C:/Users/jeeva/eclipse-workspace/Wefluence/Uploads";
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
            System.out.println("✅ Influencer DB connection established.");
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServletException("DB connection error", e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String influencerId = request.getParameter("influencer_id");
            boolean recordExists = checkIfInfluencerExists(influencerId);

            saveInfluencerData(request, influencerId, recordExists);

            response.sendRedirect("InfluencerManagement");
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("error.html");
        }
    }

    private boolean checkIfInfluencerExists(String influencerId) throws SQLException {
        String query = "SELECT influencer_id FROM InfluencerApprovalDetails WHERE influencer_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, influencerId);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next();
            }
        }
    }

    private void saveInfluencerData(HttpServletRequest request, String influencerId, boolean recordExists)
            throws SQLException, IOException, ServletException {
        String sql = recordExists ? getUpdateQuery() : getInsertQuery();

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            setQueryParameters(stmt, request, influencerId, recordExists);
            stmt.executeUpdate();
        }
    }

    private String getUpdateQuery() {
        return "UPDATE InfluencerApprovalDetails SET fullName=?, contactNumber=?, emailAddress=?, state=?, city=?, gender=?, " +
               "instagramHandle=?, youtubeChannel=?, primaryCategory=?, otherCategory=?, engagementRate=?, longTermPartnership=?, notes=?, " +
               "profileImage=?, image1=?, image2=?, image3=?, image4=?, image5=?, image6=?, image7=?, image8=?, image9=?, image10=?, image11=?, image12=?, active_status=? " +
               "WHERE influencer_id=?";
    }

    private String getInsertQuery() {
        return "INSERT INTO InfluencerApprovalDetails (influencer_id, fullName, contactNumber, emailAddress, state, city, gender, instagramHandle, " +
               "youtubeChannel, primaryCategory, otherCategory, engagementRate, longTermPartnership, notes, profileImage, image1, image2, image3, image4, image5, " +
               "image6, image7, image8, image9, image10, image11, image12, active_status) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    }

    private void setQueryParameters(PreparedStatement stmt, HttpServletRequest request, String influencerId, boolean recordExists)
            throws SQLException, IOException, ServletException {
        int index = 1;

        if (!recordExists) {
            stmt.setString(index++, influencerId);
        }

        stmt.setString(index++, request.getParameter("fullName"));
        stmt.setString(index++, request.getParameter("contactNumber"));
        stmt.setString(index++, request.getParameter("emailAddress"));
        stmt.setString(index++, request.getParameter("state"));
        stmt.setString(index++, request.getParameter("city"));
        stmt.setString(index++, request.getParameter("gender"));
        stmt.setString(index++, request.getParameter("instagramHandle"));
        stmt.setString(index++, request.getParameter("youtubeChannel"));
        stmt.setString(index++, request.getParameter("primaryCategory"));
        stmt.setString(index++, request.getParameter("otherCategory"));
        stmt.setString(index++, request.getParameter("engagementRate"));
        stmt.setString(index++, request.getParameter("longTermPartnership"));
        stmt.setString(index++, request.getParameter("notes"));
        stmt.setString(index++, uploadFile(request.getPart("profileImage"), "profile_" + influencerId));

        for (int i = 1; i <= 12; i++) {
            stmt.setString(index++, uploadFile(request.getPart("images" + i), "image" + i + "_" + influencerId));
        }

        stmt.setString(index++, request.getParameter("active_status"));

        if (recordExists) {
            stmt.setString(index, influencerId);
        }
    }

    private String uploadFile(Part part, String fileName) throws IOException {
        if (part != null && part.getSize() > 0) {
            String ext = getFileExtension(part);
            String filePath = UPLOAD_DIR + File.separator + fileName + ext;
            part.write(filePath);
            return "/uploads/" + fileName + ext;
        }
        return null;
    }

    private String getFileExtension(Part part) {
        String contentType = part.getContentType();
        if ("image/jpeg".equals(contentType)) return ".jpg";
        else if ("image/png".equals(contentType)) return ".png";
        else return "";
    }

    @Override
    public void destroy() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("✅ Influencer DB connection closed.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Cleanup JDBC Driver (prevent memory leak)
        try {
            Driver driver = DriverManager.getDriver("jdbc:mysql://localhost"); // or your DB URL
            DriverManager.deregisterDriver(driver);
            System.out.println("✅ MySQL JDBC Driver deregistered.");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        com.mysql.cj.jdbc.AbandonedConnectionCleanupThread.checkedShutdown();
		System.out.println("✅ MySQL cleanup thread stopped.");
    }

}
