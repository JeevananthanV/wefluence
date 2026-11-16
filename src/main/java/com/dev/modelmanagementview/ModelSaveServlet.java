package com.dev.modelmanagementview;

import java.io.File;
import java.io.IOException;
import java.sql.*;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/ModelSaveServlet")
@MultipartConfig(
    fileSizeThreshold = 1024 * 1024 * 2, // 2MB
    maxFileSize = 1024 * 1024 * 10, // 10MB
    maxRequestSize = 1024 * 1024 * 50 // 50MB
)
public class ModelSaveServlet extends HttpServlet {
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
            System.out.println("✅ Database connection established.");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            throw new ServletException("Database connection error", e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String emailAddress = request.getParameter("email_address");
            String modelId = fetchModelIdByEmail(emailAddress);

            if (modelId == null) {
                sendErrorResponse(response);
                return;
            }

            boolean recordExists = checkIfModelExists(modelId);
            saveModelData(request, modelId, recordExists);

            sendSuccessResponse(response);
        } catch (Exception e) {
            e.printStackTrace();
            sendErrorResponse(response);
        }
    }

    private String fetchModelIdByEmail(String emailAddress) throws SQLException {
        String modelId = null;
        String query = "SELECT model_id FROM modelProfiles WHERE email_address = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, emailAddress);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    modelId = rs.getString("model_id");
                }
            }
        }
        return modelId;
    }

    private boolean checkIfModelExists(String modelId) throws SQLException {
        String query = "SELECT model_id FROM ModelApprovalDetails WHERE model_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, modelId);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next();
            }
        }
    }

    private void saveModelData(HttpServletRequest request, String modelId, boolean recordExists)
            throws SQLException, IOException, ServletException {
        String sql = recordExists ? getUpdateQuery() : getInsertQuery();

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            setQueryParameters(stmt, request, modelId, recordExists);
            stmt.executeUpdate();
        }
    }

    private String getUpdateQuery() {
        return "UPDATE ModelApprovalDetails SET full_name=?, dob=?, age=?, gender=?, contact_number=?, city=?, state=?, " +
                "height=?, weight=?, specialized_category=?, about=?, special_skills=?, profile_image=?, " +
                "image1=?, image2=?, image3=?, image4=?, image5=?, image6=?, image7=?, image8=?, image9=?, image10=?, " +
                "image11=?, image12=?, status=? WHERE model_id=?";
    }

    private String getInsertQuery() {
        return "INSERT INTO ModelApprovalDetails (model_id, full_name, dob, age, gender, contact_number, email_address, city, state, " +
                "height, weight, specialized_category, about, special_skills, profile_image, image1, image2, image3, image4, image5, image6, " +
                "image7, image8, image9, image10, image11, image12, status) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    }

    private void setQueryParameters(PreparedStatement stmt, HttpServletRequest request, String modelId, boolean recordExists)
            throws SQLException, IOException, ServletException {

        int index = 1;
        if (!recordExists) {
            stmt.setString(index++, modelId);
        }

        stmt.setString(index++, request.getParameter("full_name"));
        stmt.setString(index++, request.getParameter("dob"));
        stmt.setInt(index++, Integer.parseInt(request.getParameter("age")));
        stmt.setString(index++, request.getParameter("gender"));
        stmt.setString(index++, request.getParameter("contact_number"));

        if (!recordExists) {
            stmt.setString(index++, request.getParameter("email_address"));
        }

        stmt.setString(index++, request.getParameter("city"));
        stmt.setString(index++, request.getParameter("state"));
        stmt.setString(index++, request.getParameter("height"));
        stmt.setString(index++, request.getParameter("weight"));
        stmt.setString(index++, request.getParameter("specialized_category"));
        stmt.setString(index++, request.getParameter("about"));
        stmt.setString(index++, request.getParameter("special_skills"));
        stmt.setString(index++, uploadFile(request.getPart("profile_image"), "profile_" + modelId));

        for (int i = 1; i <= 12; i++) {
            stmt.setString(index++, uploadFile(request.getPart("image" + i), "image" + i + "_" + modelId));
        }

        stmt.setString(index++, request.getParameter("status"));

        if (recordExists) {
            stmt.setString(index, modelId);
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

    private void sendSuccessResponse(HttpServletResponse response) throws IOException {
        response.sendRedirect("ModelManagement");
    }

    private void sendErrorResponse(HttpServletResponse response) throws IOException {
        response.sendRedirect("error.html");
    }

    @Override
    public void destroy() {
        try {
            if (connection != null) {
                connection.close();
                System.out.println("✅ Database connection closed.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
