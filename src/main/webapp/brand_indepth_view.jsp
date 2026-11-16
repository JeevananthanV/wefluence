<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.dev.brandmanagementview.BrandProfile" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="java.util.List" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Brand In-Depth View</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.2/css/all.min.css">
    
    <link rel="stylesheet" href="<%= request.getContextPath() %>/assets/css/styles.css">
<style type="text/css">
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




 /* Center the div */
        .weinfluence-signup-container {
            display: flex;
            justify-content: center;
            align-items: center;
            height: 10vh;
            background-color: #f4f4f4;
        }

        /* Modern Button Style */
        .weinfluence-signup-btn {
            background-color: #28a745; /* Stylish Orange */
            border: none;
            color: white;
            padding: 15px 35px;
            text-align: center;
            text-decoration: none;
            font-size: 18px;
            font-weight: bold;
            border-radius: 8px;
            cursor: pointer;
            box-shadow: 3px 3px 10px rgba(0, 0, 0, 0.2);
            transition: all 0.3s ease-in-out;
        }

        /* Hover Effect */
        .weinfluence-signup-btn:hover {
        	color:#28a745;
            background-color: #FFFFFF;
            transform: scale(1.08);
            box-shadow: 5px 5px 15px rgba(0, 0, 0, 0.3);
        }
    
</style>
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

        <li class="has-dropdown active">
            <a href="#"><i class="fas fa-users"></i> User Management <i class="fas fa-chevron-down"></i></a>
            <ul class="dropdown">
                <li><a href="ModelManagement"><i class="fas fa-user"></i> Model Section</a></li>
                <li><a href="InfluencerManagement"><i class="fas fa-user"></i> Influencer Section</a></li>
                <li  class="active"><a href="BrandManagement"><i class="fas fa-building"></i> Brand Section</a></li>
            </ul>
        </li>
        <!-- ✅ New Active User Management Section -->
        <li class="has-dropdown">
            <a href="#"><i class="fas fa-user-check"></i> Active User Management <i class="fas fa-chevron-down"></i></a>
            <ul class="dropdown">
                <li><a href="ActiveBrand"><i class="fas fa-building"></i> Active Brand Users</a></li>
                <li><a href="InfluencerManagementServlet"><i class="fas fa-user"></i> Active Influencer Users</a></li>
                <li><a href="ActiveModel"><i class="fas fa-user"></i> Active Model Users</a></li>
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


    <!-- Main Content --> 
    <main class="dashboard-container">
           <div class="container">
        <h1>Brand Profiles</h1>
        <div class="weinfluence-signup-container">
        <a href="Branddepth.html" class="weinfluence-signup-btn">Add Profile Brand</a>
    </div>
        
        <!-- Search Form -->
         <form action="BrandManagement" method="GET" class="search-form">
            <input type="text" name="search" class="search-input"  placeholder="Search by Brand Name, Contact Person, Email Address, or Phone Number"
                   value="${param.search != null ? param.search : ''}">
            <button type="submit" class="btn-search">Search</button>
            <button type="button" class="btn-reset" onclick="window.location.href='BrandManagement'">Reset</button>
        </form>

        <!-- Brand Profiles Table -->
        <div class="table-container">
            <table class="leads-table">
                <thead class="fixed-header">
                    <tr>
                        <th class="table-header fixed-column">Brand Name</th>
                        <th class="table-header">Website URL</th>
                        <th class="table-header">Industry Category</th>
                        <th class="table-header">Custom Industry</th>
                        <th class="table-header">Company Size</th>
                        <th class="table-header">Contact Person</th>
                        <th class="table-header">Email Address</th>
                        <th class="table-header">Phone Number</th>
                        <th class="table-header">Designation</th>
                        <th class="table-header">Budget Range</th>
                        <th class="table-header">Social Media Platforms</th>
                        <th class="table-header">Social Media Other</th>
                        <th class="table-header">Special Requirements</th>
                        <th class="table-header">Created At</th>
                        <th class="table-header">Actions</th> <!-- Added a column for actions -->
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="brand" items="${brandProfiles}">
                        <tr class="table-row">
                            <td class="table-data fixed-column">${brand.brandName}</td>
                            <td class="table-data">
                                <a href="${brand.websiteUrl}" target="_blank" class="tooltip">
                                ${brand.websiteUrl != null ? brand.websiteUrl : 'N/A'}</a>
                                
                            </td>
                            <td class="table-data">${brand.industryCategory}</td>
                            <td class="table-data">${brand.customIndustry != null ? brand.customIndustry : 'N/A'}</td>
                            <td class="table-data">${brand.companySize}</td>
                            <td class="table-data">${brand.contactPerson}</td>
                            <td class="table-data">${brand.emailAddress}</td>
                            <td class="table-data">${brand.phoneNumber}</td>
                            <td class="table-data">${brand.designation}</td>
                            <td class="table-data">${brand.budgetRange}</td>
                            <td class="table-data">${brand.socialMediaPlatforms}</td>
                            <td class="table-data">${brand.socialMediaOther != null ? brand.socialMediaOther : 'N/A'}</td>
                            <td class="table-data">${brand.specialRequirements != null ? brand.specialRequirements : 'N/A'}</td>
                            <td class="table-data">${brand.createdAt}</td>
                            <!-- Actions Column with Edit and Approve buttons -->
                           <td class="table-data">
                			<form action="UpdateBrandServlet" method="get" style="display:inline;">
    <input type="hidden" name="brandId" value="${brand.brandId}">
    <button type="submit" class="btn edit-button">Edit</button>
</form>


                				<form action="approveBrand.jsp" method="get" style="display:inline;">
                    				<input type="hidden" name="emailAddress" value="${brand.emailAddress}">
                    				<button type="submit" class="btn approve-button">Approve</button>
                				</form>
                			</td>

                        </tr>
                    </c:forEach>
                    <c:if test="${empty brandProfiles}">
                        <tr>
                            <td colspan="14" class="no-data">No brand profiles found.</td> <!-- Adjusted colspan to 14 -->
                        </tr>
                    </c:if>
                </tbody>
            </table>
        </div>
        

</main>
</div>
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script>
    $(document).ready(function() {
        $("#toggleSidebar").click(function() {
            $(".sidebar").toggleClass("collapsed");
            $(".dashboard-container").toggleClass("collapsed");
        });
    });
</script>


<script>
    // Sidebar Toggle for Mobile
    document.getElementById("toggleSidebar").addEventListener("click", function() {
        document.getElementById("sidebar").classList.toggle("collapsed");
    });
 // Get references to the toggle button and sidebar
    const toggleButton = document.getElementById('toggleSidebar');
    const sidebar = document.getElementById('sidebar');

    // Add an event listener for the click event on the toggle button
    toggleButton.addEventListener('click', function() {
        // Toggle the 'open' class on the sidebar
        sidebar.classList.toggle('open');
    });

</script>

</body>
</html>
