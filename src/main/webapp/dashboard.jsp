<%@ page import="java.sql.*, javax.servlet.*" %>

<%
    // Fetch database credentials from web.xml
    ServletContext context = request.getServletContext();
    String driver = context.getInitParameter("driver");
    String jdbcUrl = context.getInitParameter("jdbcurl");
    String username = context.getInitParameter("username");
    String password = context.getInitParameter("password");

    // Declare SQL objects
    Connection conn = null;
    PreparedStatement stmt = null;
    ResultSet rs = null;

    // Variables to store count values
    int totalModelLeads = 0, totalBrandLeads = 0, totalInfluencerLeads = 0;
    int totalModelProfiles = 0, totalBrandProfiles = 0, totalInfluencerProfiles = 0;
    int totalEnquiries = 0, modelEnquiries = 0, influencerEnquiries = 0, brandEnquiries = 0;
    int activeModels = 0, inactiveModels = 0, activeBrands = 0, inactiveBrands = 0, activeInfluencers = 0, inactiveInfluencers = 0;

    try {
        // Load MySQL JDBC Driver
        Class.forName(driver);
        conn = DriverManager.getConnection(jdbcUrl, username, password);

        // SQL Query to Fetch All Counts
        String sql = "SELECT " +
                     "(SELECT COUNT(*) FROM Enquiries) AS total_enquiries, " +
                     "(SELECT COUNT(*) FROM Enquiries WHERE category = 'model') AS model_enquiries, " +
                     "(SELECT COUNT(*) FROM Enquiries WHERE category = 'influence') AS influencer_enquiries, " +
                     "(SELECT COUNT(*) FROM Enquiries WHERE category = 'brand') AS brand_enquiries, " +
                     "(SELECT COUNT(*) FROM ModelSignUpData) AS total_model_leads, " +
                     "(SELECT COUNT(*) FROM BrandSignUpData) AS total_brand_leads, " +
                     "(SELECT COUNT(*) FROM InfluencerSignUpData) AS total_influencer_leads, " +
                     "(SELECT COUNT(*) FROM ModelProfiles) AS total_model_profiles, " +
                     "(SELECT COUNT(*) FROM BrandProfiles) AS total_brand_profiles, " +
                     "(SELECT COUNT(*) FROM InfluencerProfiles) AS total_influencer_profiles, " +
                     "(SELECT COUNT(*) FROM ModelApprovalDetails WHERE status = 'Active') AS active_models, " +
                     "(SELECT COUNT(*) FROM ModelApprovalDetails WHERE status = 'Inactive') AS inactive_models, " +
                     "(SELECT COUNT(*) FROM BrandCampaignDetails WHERE is_active = TRUE) AS active_brands, " +
                     "(SELECT COUNT(*) FROM BrandCampaignDetails WHERE is_active = FALSE) AS inactive_brands, " +
                     "(SELECT COUNT(*) FROM InfluencerApprovalDetails WHERE active_status = 'Active') AS active_influencers, " +
                     "(SELECT COUNT(*) FROM InfluencerApprovalDetails WHERE active_status = 'Inactive') AS inactive_influencers";

        // Execute Query
        stmt = conn.prepareStatement(sql);
        rs = stmt.executeQuery();

        // Store results in variables
        if (rs.next()) {
            totalEnquiries = rs.getInt("total_enquiries");
            modelEnquiries = rs.getInt("model_enquiries");
            influencerEnquiries = rs.getInt("influencer_enquiries");
            brandEnquiries = rs.getInt("brand_enquiries");

            totalModelLeads = rs.getInt("total_model_leads");
            totalBrandLeads = rs.getInt("total_brand_leads");
            totalInfluencerLeads = rs.getInt("total_influencer_leads");

            totalModelProfiles = rs.getInt("total_model_profiles");
            totalBrandProfiles = rs.getInt("total_brand_profiles");
            totalInfluencerProfiles = rs.getInt("total_influencer_profiles");

            activeModels = rs.getInt("active_models");
            inactiveModels = rs.getInt("inactive_models");
            activeBrands = rs.getInt("active_brands");
            inactiveBrands = rs.getInt("inactive_brands");
            activeInfluencers = rs.getInt("active_influencers");
            inactiveInfluencers = rs.getInt("inactive_influencers");
        }
%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Admin Dashboard</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
    
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.2/css/all.min.css">
    <script src="https://kit.fontawesome.com/a076d05399.js" crossorigin="anonymous"></script>
    <style>
        body { background-color: #f8f9fa; }
        .card { box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1); text-align: center; }
        .chart-container { width: 100%; height: 400px; }
    </style>
    <style>
    /* Global Styles */
body {
    font-family: 'Poppins', sans-serif;
    margin: 0;
    padding: 0;
    background: #f4f6f9;
    color: #333;
}

/* Wrapper */
.wrapper {
    display: flex;
}

/* Sidebar */
.sidebar {
    width: 250px;
    height: 100vh;
    background: rgba(17, 17, 17, 0.95);
    color: white;
    position: fixed;
    transition: all 0.3s ease-in-out;
    box-shadow: 4px 0px 10px rgba(0, 0, 0, 0.3);
}

.sidebar-header {
    padding: 20px;
    display: flex;
    justify-content: space-between;
    align-items: center;
    background: rgba(34, 34, 34, 0.9);
    font-size: 18px;
    font-weight: bold;
}

.toggle-btn {
    cursor: pointer;
    color: white;
    font-size: 22px;
}

/* Sidebar Menu */
.menu {
    list-style: none;
    padding: 0;
    margin: 0;
}

.menu li {
    padding: 15px 20px;
    cursor: pointer;
    display: flex;
    align-items: center;
    transition: background 0.3s;
}

.menu li a {
    color: white;
    text-decoration: none;
    display: flex;
    align-items: center;
    width: 100%;
    font-size: 16px;
}

.menu li i {
    margin-right: 12px;
    font-size: 18px;
}

.menu li:hover, .menu .active {
    background: rgba(40, 167, 69, 0.8);
    border-left: 4px solid #28a745;
}

/* Dropdown Menu */
.has-dropdown {
    position: relative;
}

.has-dropdown > a {
    display: flex;
    justify-content: space-between;
}

.has-dropdown .dropdown {
    display: none;
    list-style: none;
    padding-left: 20px;
    background: rgba(34, 34, 34, 0.9);
    position: absolute;
    width: 220px;
    left: 100%;
    top: 0;
    opacity: 0;
    transition: all 0.3s ease-in-out;
    border-radius: 5px;
}

.has-dropdown:hover .dropdown {
    display: block;
    opacity: 1;
}

/* Dashboard Content */
.dashboard-container {
    margin-left: 260px;
    padding: 20px;
    width: calc(94% - 260px);
    background: #fff;
    min-height: 100vh;
    border-radius: 10px;
    box-shadow: inset 0px 4px 10px rgba(0, 0, 0, 0.05);
}

/* Page Title */
.page-title {
    font-size: 32px;
    font-weight: bold;
    color: #222;
    margin-bottom: 25px;
}

/* Search Form */
.search-form {
    display: flex;
    gap: 15px;
    align-items: center;
    margin-bottom: 20px;
    background: rgba(255, 255, 255, 0.8);
    padding: 12px;
    border-radius: 10px;
    box-shadow: 0px 2px 5px rgba(0, 0, 0, 0.1);
}

.search-input {
    padding: 12px;
    width: 100%;
    max-width: 350px;
    border: 1px solid #ddd;
    border-radius: 6px;
    font-size: 14px;
}

.btn-search, .btn-reset {
    padding: 12px 18px;
    border: none;
    cursor: pointer;
    border-radius: 6px;
    font-size: 14px;
    transition: background 0.3s;
}

.btn-search {
    background-color: #28a745;
    color: white;
}

.btn-search:hover {
    background-color: #218838;
}

.btn-reset {
    background-color: #dc3545;
    color: white;
}

.btn-reset:hover {
    background-color: #c82333;
}

/* Table Styles */
.table-container {
    overflow-x: auto;
    background: rgba(255, 255, 255, 0.95);
    padding: 15px;
    border-radius: 12px;
    box-shadow: 0px 4px 10px rgba(0, 0, 0, 0.1);
    
    height:70vh;
}

.leads-table {
    width: 100%;
    border-collapse: collapse;
    margin-top: 15px;
    border-radius: 12px;
}

.leads-table thead {
    background: #28a745;
    color: white;
    font-size: 16px;
}

.table-header {
    padding: 14px;
    text-align: left;
    font-weight: bold;
}

.table-data {
    padding: 14px;
    border-bottom: 1px solid #ddd;
    font-size: 14px;
}

.table-data:nth-child(even) {
    background-color: #f8f9fa;
}

.table-data:hover {
    background-color: #d4edda;
    transition: background 0.3s;
}

/* No Data Message */
.no-data {
    text-align: center;
    padding: 20px;
    font-weight: bold;
    color: #dc3545;
}

/* Stats Cards */
.stats-container {
    display: flex;
    justify-content: space-between;
    gap: 20px;
}

.stat-card {
    flex: 1;
    background: linear-gradient(135deg, #28a745, #218838);
    color: white;
    padding: 25px;
    border-radius: 12px;
    text-align: center;
    box-shadow: 0px 4px 10px rgba(0, 0, 0, 0.2);
    display: flex;
    align-items: center;
    justify-content: center;
}

.stat-card .icon {
    font-size: 42px;
    margin-right: 12px;
}

.stat-info h3 {
    font-size: 34px;
    margin: 0;
}

.stat-info p {
    font-size: 14px;
    margin: 5px 0 0;
}
 

/* Responsive Sidebar */
@media (max-width: 768px) {
    .sidebar {
        width: 80px;
    }

    .sidebar.collapsed {
        width: 0;
        overflow: hidden;
    }

    .dashboard-container {
        margin-left: 90px;
        width: calc(100% - 90px);
    }

    .search-form {
        flex-direction: column;
        align-items: flex-start;
    }

    .btn-search, .btn-reset {
        width: 100%;
    }

    .stats-container {
        flex-direction: column;
    }

    .stat-card {
        width: 100%;
        margin-bottom: 12px;
    }
}
 

/* Styling for Edit and Approve buttons */
.edit-button, .approve-button {
    padding: 6px 12px;
    margin: 2px;
    border: none;
    cursor: pointer;
    font-size: 14px;
    border-radius: 4px;
    transition: background-color 0.3s ease;
}

.edit-button {
    background-color: #4CAF50; /* Green for Edit */
    color: white;
}

.edit-button:hover {
    background-color: #45a049; /* Darker green on hover */
}

.approve-button {
    background-color: #008CBA; /* Blue for Approve */
    color: white;
}

.approve-button:hover {
    background-color: #007bb5; /* Darker blue on hover */
}



/* Profile Image */
.profile-img {
    width: 50px;  /* Small thumbnail */
    height: 50px;
    object-fit: cover;
    border-radius: 5px;
    transition: transform 0.2s ease-in-out;
}

.profile-img:hover {
    transform: scale(1.1);
}

/* Gallery Images */
.gallery-img {
    width: 40px;  /* Small size */
    height: 40px;
    object-fit: cover;
    border-radius: 5px;
    margin: 3px;
    transition: transform 0.2s ease-in-out;
}

.gallery-img:hover {
    transform: scale(1.1);
}

/* Image Gallery Layout */
.image-gallery {
    display: flex;
    flex-direction: row; /* Fixed typo */
    gap: 5px;
    max-width: 180px;  /* Restrict width */
    overflow-x: auto;
}


.dashboard-section { margin-top: 20px; font-weight: bold; }
        .dashboard-card {
            border-radius: 10px;
            padding: 20px;
            color: white;
            text-align: center;
            font-size: 1.2rem;
            transition: transform 0.3s ease-in-out;
            box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1);
        }
        .dashboard-card:hover {
            transform: scale(1.05);
        }
        .icon {
            font-size: 2rem;
            margin-bottom: 10px;
        }
        section-title { margin-top: 20px; font-weight: bold; text-align: center; }
        .info-card {
            border-radius: 10px;
            padding: 20px;
            color: white;
            text-align: center;
            font-size: 1.2rem;
            transition: transform 0.3s ease-in-out;
            box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1);
        }
        .info-card:hover {
            transform: scale(1.05);
        }
        .icon {
            font-size: 2rem;
            margin-bottom: 10px;
        }
        .chart-container {
            width: 100%;
            height: 300px;
            display: flex;
            justify-content: center;
            align-items: center;
        }
        .section-title { margin-top: 30px; font-weight: bold; text-align: center; font-size: 1.5rem; }
        .info-card {
            border-radius: 12px;
            padding: 25px;
            color: white;
            text-align: center;
            font-size: 1.3rem;
            transition: transform 0.3s ease-in-out;
            box-shadow: 0 5px 15px rgba(0, 0, 0, 0.15);
            width: 230px;
        }
        .info-card:hover {
            transform: scale(1.05);
        }
        .icon {
            font-size: 2.5rem;
            margin-bottom: 15px;
        }
        .chart-container {
            width: 100%;
            height: 450px;  /* Increased height for larger pie charts */
            display: flex;
            justify-content: center;
            align-items: center;
        }
    
    </style>
</head>
<body>
<div class="wrapper">
 <!-- Sidebar -->
    <aside class="sidebar" id="sidebar">
    <div class="sidebar-header">
        <i class="fas fa-bars toggle-btn" id="toggleSidebar"></i>
        <span>Admin Panel</span>
    </div>

    <ul class="menu">
        <li class="active"><a href="DashboardServlet"><i class="fas fa-tachometer-alt"></i> Dashboard</a></li>

        <li class="has-dropdown  ">
            <a href="#"><i class="fas fa-user-plus"></i> Sign Up Leads <i class="fas fa-chevron-down"></i></a>
            <ul class="dropdown">
                <li><a href="Modelview"><i class="fas fa-user"></i> Model Leads</a></li>
                <li><a href="Influencerview"><i class="fas fa-user"></i> Influencer Leads</a></li>
                <li><a href="Brandview"><i class="fas fa-building"></i> Brand Leads</a></li>
            </ul>
        </li>

        <li class="has-dropdown ">
            <a href="#"><i class="fas fa-users"></i> User Management <i class="fas fa-chevron-down"></i></a>
            <ul class="dropdown">
                <li ><a href="ModelManagement"><i class="fas fa-user"></i> Model Section</a></li>
                <li><a href="InfluencerManagement"><i class="fas fa-user"></i> Influencer Section</a></li>
                <li  ><a href="BrandManagement"><i class="fas fa-building"></i> Brand Section</a></li>
            </ul>
        </li>
        <!-- ✅ New Active User Management Section -->
        <li class="has-dropdown ">
            <a href="#"><i class="fas fa-user-check"></i> Active User Management <i class="fas fa-chevron-down"></i></a>
            <ul class="dropdown">
                <li><a href="ActiveBrand"><i class="fas fa-building"></i> Active Brand Users</a></li>
                <li ><a href="InfluencerManagementServlet"><i class="fas fa-user"></i> Active Influencer Users</a></li>
                <li ><a href="ActiveModel"><i class="fas fa-user"></i> Active Model Users</a></li>
            </ul>
        </li>
        <li class="has-dropdown ">
            <a href="#"><i class="fas fa-envelope"></i> Enquiry Management <i class="fas fa-chevron-down"></i></a>
            <ul class="dropdown">
                <li><a href="BrandEnquiry"><i class="fas fa-building"></i> Brand Enquiry</a></li>
                <li ><a href="ModelEnquiry"><i class="fas fa-user"></i> Model Enquiry</a></li>
                <li><a href="InfluencerEnquiry"><i class="fas fa-user"></i> Influencer Enquiry</a></li>
            </ul>
        </li>
        <li class="has-dropdown">
            <a href="#"><i class="fas fa-calendar-check"></i> Booking Management <i class="fas fa-chevron-down"></i></a>
            <ul class="dropdown">
                <li><a href="ModelBooking"><i class="fas fa-user"></i> Model Booking</a></li>
                <li><a href="InfluencerBooking"><i class="fas fa-user"></i> Influencer Booking</a></li>
            </ul>
        </li>

        <li><a href="ControlManagement.html"><i class="fas fa-cogs"></i> Control Management</a></li>

        <li class="logout"><a href="LogoutServlet"><i class="fas fa-sign-out-alt"></i> Logout</a></li>
    </ul>
</aside>
 <div class="dashboard-container">
<div class="container mt-4">

    <h2 class="text-center mb-4">Admin Dashboard</h2>

    

    



<!-- 🚀 Section 1: Enquiries -->
    <h4 class="dashboard-section">Enquiries</h4>
    <div class="row">
        <div class="col-md-4"><div class="dashboard-card bg-primary"><i class="fas fa-question-circle icon"></i> Model Enquiries: <strong><%= modelEnquiries %></strong></div></div>
        <div class="col-md-4"><div class="dashboard-card bg-success"><i class="fas fa-user-check icon"></i> Influencer Enquiries: <strong><%= influencerEnquiries %></strong></div></div>
        <div class="col-md-4"><div class="dashboard-card bg-danger"><i class="fas fa-building icon"></i> Brand Enquiries: <strong><%= brandEnquiries %></strong></div></div>
    </div>
    <!-- 🚀 Section 1: Enquiries -->
    <div class="chart-container"><canvas id="enquiriesChart"></canvas></div>
<!-- 🚀 Section 2: Leads -->
    <h4 class="dashboard-section">Leads</h4>
    <div class="row">
        <div class="col-md-4"><div class="dashboard-card bg-info"><i class="fas fa-user icon"></i> Total Model Leads: <strong><%= totalModelLeads %></strong></div></div>
        <div class="col-md-4"><div class="dashboard-card bg-warning text-dark"><i class="fas fa-users icon"></i> Total Brand Leads: <strong><%= totalBrandLeads %></strong></div></div>
        <div class="col-md-4"><div class="dashboard-card bg-secondary"><i class="fas fa-user-friends icon"></i> Total Influencer Leads: <strong><%= totalInfluencerLeads %></strong></div></div>
    </div>
    <!-- 🚀 Section 2: Leads -->
    <div class="chart-container"><canvas id="leadsChart"></canvas></div>
<!-- 🚀 Section 3: Profiles -->
    <h4 class="dashboard-section">Profiles</h4>
    <div class="row">
        <div class="col-md-4"><div class="dashboard-card bg-danger"><i class="fas fa-id-badge icon"></i> Total Model Profiles: <strong><%= totalModelProfiles %></strong></div></div>
        <div class="col-md-4"><div class="dashboard-card bg-success"><i class="fas fa-briefcase icon"></i> Total Brand Profiles: <strong><%= totalBrandProfiles %></strong></div></div>
        <div class="col-md-4"><div class="dashboard-card bg-primary"><i class="fas fa-user-tie icon"></i> Total Influencer Profiles: <strong><%= totalInfluencerProfiles %></strong></div></div>
    </div>

    <!-- 🚀 Section 3: Profiles -->
    <div class="chart-container"><canvas id="profilesChart"></canvas></div>
    
     <!-- 🚀 Section 1: Active & Inactive Models -->
    <h4 class="section-title">Active & Inactive Models</h4>
    <div class="row align-items-center">
        <!-- Count Display -->
        <div class="col-md-5">
            <div class="row">
                <div class="col-md-6"><div class="dashboard-card bg-primary"><i class="fas fa-user-check icon"></i> Active Models: <strong><%= activeModels %></strong></div></div>
                <div class="col-md-6"><div class="dashboard-card bg-warning text-dark"><i class="fas fa-user-times icon"></i> Inactive Models: <strong><%= inactiveModels %></strong></div></div>
            </div>
        </div>
        <!-- Pie Chart -->
        <div class="col-md-7 chart-container"><canvas id="modelsChart"></canvas></div>
    </div>

    <!-- 🚀 Section 2: Active & Inactive Brands -->
    <h4 class="section-title">Active & Inactive Brands</h4>
    <div class="row align-items-center">
        <!-- Count Display -->
        <div class="col-md-5">
            <div class="row">
                <div class="col-md-6"><div class="dashboard-card bg-success"><i class="fas fa-building icon"></i> Active Brands: <strong><%= activeBrands %></strong></div></div>
                <div class="col-md-6"><div class="dashboard-card bg-danger"><i class="fas fa-ban icon"></i> Inactive Brands: <strong><%= inactiveBrands %></strong></div></div>
            </div>
        </div>
        <!-- Pie Chart -->
        <div class="col-md-7 chart-container"><canvas id="brandsChart"></canvas></div>
    </div>

    <!-- 🚀 Section 3: Active & Inactive Influencers -->
    <h4 class="section-title">Active & Inactive Influencers</h4>
    <div class="row align-items-center">
        <!-- Count Display -->
        <div class="col-md-5">
            <div class="row">
                <div class="col-md-6"><div class="dashboard-card bg-info"><i class="fas fa-user-check icon"></i> Active Influencers: <strong><%= activeInfluencers %></strong></div></div>
                <div class="col-md-6"><div class="dashboard-card bg-secondary"><i class="fas fa-user-times icon"></i> Inactive Influencers: <strong><%= inactiveInfluencers %></strong></div></div>
            </div>
        </div>
        <!-- Pie Chart -->
        <div class="col-md-7 chart-container"><canvas id="influencersChart"></canvas></div>
    </div>
    
       
</div>
</div>
</div>
<script>
    // Enquiries Chart
    new Chart(document.getElementById('enquiriesChart').getContext('2d'), {
        type: 'bar',
        data: { labels: ['Model', 'Influencer', 'Brand'],
                datasets: [{ data: [<%= modelEnquiries %>, <%= influencerEnquiries %>, <%= brandEnquiries %>], backgroundColor: ['#007bff', '#28a745', '#dc3545'] }]
              }
    });

    // Leads Chart
    new Chart(document.getElementById('leadsChart').getContext('2d'), {
        type: 'bar',
        data: { labels: ['Model', 'Brand', 'Influencer'],
                datasets: [{ data: [<%= totalModelLeads %>, <%= totalBrandLeads %>, <%= totalInfluencerLeads %>], backgroundColor: ['#ffc107', '#17a2b8', '#6f42c1'] }]
              }
    });

    // Profiles Chart
    new Chart(document.getElementById('profilesChart').getContext('2d'), {
        type: 'bar',
        data: { labels: ['Model', 'Brand', 'Influencer'],
                datasets: [{ data: [<%= totalModelProfiles %>, <%= totalBrandProfiles %>, <%= totalInfluencerProfiles %>], backgroundColor: ['#fd7e14', '#20c997', '#6610f2'] }]
              }
    });

    // Individual Pie Charts for Active & Inactive Users
    new Chart(document.getElementById('modelsChart').getContext('2d'), {
        type: 'pie',
        data: { labels: ['Active', 'Inactive'], datasets: [{ data: [<%= activeModels %>, <%= inactiveModels %>], backgroundColor: ['#007bff', '#ffc107'] }] }
    });

    new Chart(document.getElementById('brandsChart').getContext('2d'), {
        type: 'pie',
        data: { labels: ['Active', 'Inactive'], datasets: [{ data: [<%= activeBrands %>, <%= inactiveBrands %>], backgroundColor: ['#28a745', '#dc3545'] }] }
    });

    new Chart(document.getElementById('influencersChart').getContext('2d'), {
        type: 'pie',
        data: { labels: ['Active', 'Inactive'], datasets: [{ data: [<%= activeInfluencers %>, <%= inactiveInfluencers %>], backgroundColor: ['#6f42c1', '#17a2b8'] }] }
    });
</script>

</body>
</html>

<%
    } catch (Exception e) {
        out.println("<p style='color:red;'>❌ Error: " + e.getMessage() + "</p>");
        e.printStackTrace();
    } finally {
        try {
            if (rs != null) rs.close();
            if (stmt != null) stmt.close();
            if (conn != null) conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
%>
