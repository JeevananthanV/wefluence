package com.dev.login;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/ModelFullFormView")
public class ModelFullFormView extends HttpServlet {
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
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        PreparedStatement selectStatement = null;
        ResultSet resultSet = null;

        try {
            out.println("<html><head><title>Model Data</title>");
            // ✅ Internal Professional CSS
            out.println("<style>");
            out.println("body { font-family: 'Poppins', sans-serif; background-color: #f4f4f9; color: #333; margin: 0; padding: 20px; }");
            out.println(".container { max-width: 1200px; margin: auto; background: #fff; padding: 20px; box-shadow: 0px 4px 10px rgba(0, 0, 0, 0.1); border-radius: 10px; }");
            out.println(".table-container { width: 100%; overflow-x: auto; }");
            out.println("table { width: 100%; border-collapse: collapse; background: #fff; }");
            out.println("th, td { padding: 12px 15px; text-align: left; border-bottom: 1px solid #ddd; }");
            out.println("th { background-color: #007BFF; color: white; text-transform: uppercase; letter-spacing: 1px; }");
            out.println("tr:nth-child(even) { background-color: #f9f9f9; }");
            out.println("tr:hover { background-color: #f1f1f1; transition: 0.3s; }");
            out.println(".action-buttons { display: flex; gap: 8px; justify-content: center; }");
            out.println(".btn { display: inline-block; padding: 8px 15px; border: none; border-radius: 5px; font-size: 14px; cursor: pointer; text-align: center; text-decoration: none; transition: all 0.3s ease-in-out; }");
            out.println(".edit-btn { background-color: #FFA500; color: white; }");
            out.println(".approve-btn { background-color: #28A745; color: white; }");
            out.println(".btn:hover { opacity: 0.9; transform: scale(1.05); }");
            out.println("h2 { text-align: center; margin-bottom: 20px; font-size: 24px; color: #007BFF; }");
            out.println("a { color: #007BFF; text-decoration: none; }");
            out.println("a:hover { text-decoration: underline; }");
            out.println("</style>");
            out.println("</head><body>");

            out.println("<div class='container'>");
            out.println("<h2>Model Sign-Up Information</h2>");
            out.println("<div class='table-container'><table border='1'>");
            out.println("<tr><th>S.No.</th><th>Model ID</th><th>Full Name</th><th>Date of Birth</th><th>Age</th>"
                    + "<th>Gender</th><th>Contact Number</th><th>Email Address</th><th>City</th><th>State</th>"
                    + "<th>Height</th><th>Weight</th><th>Hair Color</th><th>Eye Color</th><th>Skin Tone</th>"
                    + "<th>Previous Works</th><th>Modeling Experience</th><th>Specialized Category</th>"
                    + "<th>Instagram Link</th><th>About</th><th>Special Skills</th><th>Portfolio Photo 1</th>"
                    + "<th>Portfolio Photo 2</th><th>Actions</th></tr>");

            // ✅ Fetch data from database, sorted by model_id
            String selectQuery = "SELECT * FROM ModelProfiles ORDER BY created_at DESC";
            selectStatement = connection.prepareStatement(selectQuery);
            resultSet = selectStatement.executeQuery();

            int serialNumber = 1; // ✅ Counter for S.No.

            while (resultSet.next()) {
                String modelId = resultSet.getString("model_id");

                out.println("<tr>");
                out.println("<td>" + serialNumber + "</td>"); // ✅ S.No.
                out.println("<td>" + modelId + "</td>"); // ✅ Model ID
                out.println("<td>" + resultSet.getString("full_name") + "</td>");
                out.println("<td>" + resultSet.getString("dob") + "</td>");
                out.println("<td>" + resultSet.getInt("age") + "</td>");
                out.println("<td>" + resultSet.getString("gender") + "</td>");
                out.println("<td>" + resultSet.getString("contact_number") + "</td>");
                out.println("<td>" + resultSet.getString("email_address") + "</td>");
                out.println("<td>" + resultSet.getString("city") + "</td>");
                out.println("<td>" + resultSet.getString("state") + "</td>");
                out.println("<td>" + resultSet.getString("height") + "</td>");
                out.println("<td>" + resultSet.getString("weight") + "</td>");
                out.println("<td>" + resultSet.getString("hair_color") + "</td>");
                out.println("<td>" + resultSet.getString("eye_color") + "</td>");
                out.println("<td>" + resultSet.getString("skin_tone") + "</td>");
                out.println("<td>" + resultSet.getString("previous_works") + "</td>");
                out.println("<td>" + resultSet.getString("modeling_experience") + "</td>");
                out.println("<td>" + resultSet.getString("specialized_category") + "</td>");
                out.println("<td><a href='" + resultSet.getString("instagram_link") + "' target='_blank'>View</a></td>");
                out.println("<td>" + resultSet.getString("about") + "</td>");
                out.println("<td>" + resultSet.getString("special_skills") + "</td>");

                String photo1 = resultSet.getString("portfolio_photo1");
                String photo2 = resultSet.getString("portfolio_photo2");
                out.println("<td><a href='/uploads/" + new File(photo1).getName() + "' target='_blank'>View</a></td>");
                out.println("<td><a href='/uploads/" + new File(photo2).getName() + "' target='_blank'>View</a></td>");

                out.println("<td>");
                out.println("<div class='action-buttons'>");
                out.println("<form action='editModel.jsp' method='get'>");
                out.println("<input type='hidden' name='model_id' value='" + modelId + "'>");
                out.println("<button type='submit' class='btn edit-btn'>Edit</button>");
                out.println("</form>");
                out.println("<form action='ApproveModel.jsp' method='get'>");
                out.println("<input type='hidden' name='model_id' value='" + resultSet.getString("model_id") + "'>");
                out.println("<button type='submit' class='btn approve-btn'>Approve</button>");
                out.println("</form>");

                out.println("</div>");
                out.println("</td>");

                out.println("</tr>");
                serialNumber++;
            }

            out.println("</table></div></div></body></html>");
        } catch (SQLException e) {
            e.printStackTrace();
            out.println("<h3>Error retrieving model data.</h3>");
        }finally {
            try {
                if (resultSet != null) resultSet.close();
                if (selectStatement != null) selectStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void destroy() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("Database connection closed.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
