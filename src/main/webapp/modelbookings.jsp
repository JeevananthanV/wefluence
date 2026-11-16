 <%@ page import="java.util.*, com.dev.booking.ModelBookingData" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%@ page import="java.util.List" %>


<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Model Booking Management</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.2/css/all.min.css">
    <link rel="stylesheet" href="<%= request.getContextPath() %>/assets/css/styles.css">
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

    
    </style>
    <script>
        function toggleStatus(id) {
            if (!confirm('Are you sure you want to change the status?')) return;
            fetch('ActiveModel', {
                method: 'POST',
                headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
                body: 'model_id=' + id
            })
            .then(response => response.text())
            .then(() => location.reload())
            .catch(error => console.error('Error:', error));
        }
    </script>
</head>
<body>

<!-- Wrapper -->
<div class="wrapper">
    
    <!-- Sidebar -->
    <aside class="sidebar" id="sidebar">
    <div class="sidebar-header">
        <i class="fas fa-bars toggle-btn" id="toggleSidebar"></i>
        <span>Admin Panel</span>
    </div>

    <ul class="menu">
        <li><a href="DashboardServlet"><i class="fas fa-tachometer-alt"></i> Dashboard</a></li>

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
                <li class="active"><a href="ActiveModel"><i class="fas fa-user"></i> Active Model Users</a></li>
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
        <li class="has-dropdown active">
            <a href="#"><i class="fas fa-calendar-check"></i> Booking Management <i class="fas fa-chevron-down"></i></a>
            <ul class="dropdown">
                <li class="active"><a href="ModelBooking"><i class="fas fa-user"></i> Model Booking</a></li>
                <li><a href="InfluencerBooking"><i class="fas fa-user"></i> Influencer Booking</a></li>
            </ul>
        </li>

        <li><a href="ControlManagement.html"><i class="fas fa-cogs"></i> Control Management</a></li>

        <li class="logout"><a href="LogoutServlet"><i class="fas fa-sign-out-alt"></i> Logout</a></li>
    </ul>
</aside>


    <!-- Main Content -->
    <main class="dashboard-container">
    <div class="container">
        <h1>Model Bookings</h1>

        <!-- Search Form -->
        <form action="ModelBooking" method="GET" class="search-form">
            <input type="text" name="search" class="search-input"
                   placeholder="Search by Model, Brand or Booker"
                   value="${param.search != null ? param.search : ''}">
            <button type="submit" class="btn-search">Search</button>
            <button type="button" class="btn-reset" onclick="window.location.href='ModelBooking'">Reset</button>
        </form>

        <!-- Booking Table -->
        <div class="table-container">
            <table class="leads-table">
                <thead>
                    <tr>
                        <th class="table-header">Model ID</th>
                        <th class="table-header">Model Name</th>
                        <th class="table-header">Brand</th>
                        <th class="table-header">Booked By</th>
                        <th class="table-header">Email</th>
                        <th class="table-header">Phone</th>
                        <th class="table-header">Shoot Start</th>
                        <th class="table-header">Shoot End</th>
                        <th class="table-header">Location</th>
                        <th class="table-header">Duration (hrs)</th>
                        <th class="table-header">Requests</th>
                        <th class="table-header">Booked At</th>
                    </tr>
                </thead>
                <tbody>
                    <%
                        List<ModelBookingData> bookings = (List<ModelBookingData>) request.getAttribute("bookings");
                        boolean dataFound = false;
                        if (bookings != null && !bookings.isEmpty()) {
                            dataFound = true;
                            for (ModelBookingData booking : bookings) {
                    %>
                    <tr class="table-row">
                        <td class="table-data"><%= booking.getModelId() %></td>
                        <td class="table-data"><%= booking.getModelName() %></td>
                        <td class="table-data"><%= booking.getBrandName() %></td>
                        <td class="table-data"><%= booking.getBookingPerson() %></td>
                        <td class="table-data"><%= booking.getContactEmail() %></td>
                        <td class="table-data"><%= booking.getContactPhone() %></td>
                        <td class="table-data"><%= booking.getShootStartDate() %></td>
                        <td class="table-data"><%= booking.getShootEndDate() %></td>
                        <td class="table-data"><%= booking.getLocation() %></td>
                        <td class="table-data"><%= booking.getDuration() %></td>
                        <td class="table-data"><%= booking.getSpecialRequests() %></td>
                        <td class="table-data"><%= booking.getBookingTimestamp() %></td>
                    </tr>
                    <%
                            }
                        }
                        if (!dataFound) {
                    %>
                    <tr>
                        <td colspan="12" class="no-data">No matching bookings found for "<%= request.getParameter("search") %>".</td>
                    </tr>
                    <% } %>
                </tbody>
            </table>
        </div>
    </div>
</main>

</div>

<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script>
    $("#toggleSidebar").click(function() {
        $(".sidebar").toggleClass("collapsed");
        $(".dashboard-container").toggleClass("collapsed");
    });
</script>
<script>
    function toggleStatus(id) {
        if (!confirm('Are you sure you want to change the status?')) return;
        fetch('ActiveModel', {
            method: 'POST',
            headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
            body: 'model_id=' + id
        })
        .then(response => response.text())
        .then(() => location.reload())
        .catch(error => console.error('Error:', error));
    }
</script>

</body>
</html>
