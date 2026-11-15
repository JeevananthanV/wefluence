<%@ page import="java.sql.*, javax.servlet.*, javax.servlet.http.*" %>
<%@ page import="com.dev.influencermanagementview.InfluencerProfileData" %>
<%@ page contentType="text/html; charset=UTF-8" %>

<%
    String influencerId = request.getParameter("influencer_id");
    InfluencerProfileData influencer = null;

    if (influencerId != null) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            ServletContext context = getServletContext();
            String driver = context.getInitParameter("driver");
            String jdbcUrl = context.getInitParameter("jdbcurl");
            String username = context.getInitParameter("username");
            String password = context.getInitParameter("password");

            Class.forName(driver);
            connection = DriverManager.getConnection(jdbcUrl, username, password);

            String query = "SELECT * FROM InfluencerProfiles WHERE influencer_id = ?";
            statement = connection.prepareStatement(query);
            statement.setString(1, influencerId);
            resultSet = statement.executeQuery();

            if (resultSet.next()) {
                influencer = new InfluencerProfileData(
                    resultSet.getString("influencer_id"),
                    resultSet.getString("fullName"),
                    resultSet.getString("contactNumber"),
                    resultSet.getString("emailAddress"),
                    resultSet.getString("gender"),
                    resultSet.getString("city"),
                    resultSet.getString("state"),
                    resultSet.getString("instagramHandle"),
                    resultSet.getString("youtubeChannel"),
                    resultSet.getString("primaryCategory"),
                    resultSet.getString("otherCategory"),
                    resultSet.getString("engagementRate"),
                    resultSet.getString("longTermPartnership"),
                    resultSet.getString("notes"),
                    resultSet.getTimestamp("submissionDate").toLocalDateTime()
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (resultSet != null) resultSet.close();
            if (statement != null) statement.close();
            if (connection != null) connection.close();
        }
    }
%>
<html>
<head>
    <title>Edit Influencer</title>
    <style>
       body {
    font-family: Arial, sans-serif;
    background-color: #f4f4f4;
    margin: 0;
    padding: 20px; /* Added padding */
    display: flex;
    justify-content: center;
    align-items: center;
    min-height: 100vh; /* Ensures content expands */
}

.container {
    background: #fff;
    padding: 20px;
    border-radius: 8px;
    box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
    max-width: 600px; /* Adjust width */
    width: 100%;
}

h2 {
    text-align: center;
    color: #333;
}

label {
    font-weight: bold;
    display: block;
    margin-top: 10px;
}

input, textarea {
    width: calc(100% - 16px); /* Prevents overflow */
    padding: 10px;
    margin-top: 5px;
    border: 1px solid #ccc;
    border-radius: 5px;
    font-size: 14px;
}

textarea {
    height: 100px;
    resize: vertical;
}

.form-group {
    margin-bottom: 15px;
}

button {
    background: #007bff;
    color: white;
    padding: 12px;
    border: none;
    border-radius: 5px;
    width: 100%;
    cursor: pointer;
    font-size: 16px;
    margin-top: 10px;
}

button:hover {
    background: #0056b3;
}

input, textarea, select {
    width: calc(100% - 16px);
    padding: 10px;
    margin-top: 5px;
    border: 1px solid #ccc;
    border-radius: 5px;
    font-size: 14px;
}


    </style>
</head>
<body>
    <div class="container">
        <h2>Edit Influencer Profile</h2>
        <form action="UpdateInfluencerServlet" method="POST">
            <input type="hidden" name="influencer_id" value="<%= (influencer != null) ? influencer.getInfluencerId() : "" %>">

            <div class="form-group">
                <label>Full Name:</label>
                <input type="text" name="fullName" value="<%= (influencer != null) ? influencer.getFullName() : "" %>" required>
            </div>

            <div class="form-group">
                <label>Contact Number:</label>
                <input type="text" name="contactNumber" value="<%= (influencer != null) ? influencer.getContactNumber() : "" %>" required>
            </div>

            <div class="form-group">
                <label>Email Address:</label>
                <input type="email" name="emailAddress" value="<%= (influencer != null) ? influencer.getEmailAddress() : "" %>" required>
            </div>

            



			<div class="form-group">
                <label>Gender:</label>
                <select name="gender" id="gender" required>
                    <option value="Male" <%= (influencer != null && "Male".equals(influencer.getGender())) ? "selected" : "" %>>Male</option>
                    <option value="Female" <%= (influencer != null && "Female".equals(influencer.getGender())) ? "selected" : "" %>>Female</option>
                    <option value="Other" <%= (influencer != null && "Other".equals(influencer.getGender())) ? "selected" : "" %>>Other</option>
                </select>
            </div>

            <div class="form-group">
                <label>State:</label>
                <select name="state" id="state" required>
                    <option value="">Select State</option>
                </select>
            </div>

            <div class="form-group">
                <label>City:</label>
                <select name="city" id="city" required>
                    <option value="">Select City</option>
                </select>
            </div>





            <div class="form-group">
                <label>Instagram Handle:</label>
                <input type="text" name="instagramHandle" value="<%= (influencer != null) ? influencer.getInstagramHandle() : "" %>">
            </div>

            <div class="form-group">
                <label>YouTube Channel:</label>
                <input type="text" name="youtubeChannel" value="<%= (influencer != null) ? influencer.getYoutubeChannel() : "" %>">
            </div>

            <div class="form-group">
                <label>Primary Category:</label>
                <input type="text" name="primaryCategory" value="<%= (influencer != null) ? influencer.getPrimaryCategory() : "" %>" required>
            </div>

            <div class="form-group">
                <label>Other Category:</label>
                <input type="text" name="otherCategory" value="<%= (influencer != null) ? influencer.getOtherCategory() : "" %>">
            </div>

            <div class="form-group">
                <label>Engagement Rate:</label>
                <input type="text" name="engagementRate" value="<%= (influencer != null) ? influencer.getEngagementRate() : "" %>">
            </div>

            <div class="form-group">
                <label>Long-term Partnership:</label>
                <input type="text" name="longTermPartnership" value="<%= (influencer != null) ? influencer.getLongTermPartnership() : "" %>">
            </div>

            <div class="form-group">
                <label>Notes:</label>
                <textarea name="notes"><%= (influencer != null) ? influencer.getNotes() : "" %></textarea>
            </div>

            <button type="submit">Update</button>
        </form>
    </div>
    
    <script >

    document.addEventListener("DOMContentLoaded", function () {
        const stateSelect = document.getElementById("state");
        const citySelect = document.getElementById("city");
        const genderSelect = document.getElementById("gender");

        // Mapping states to their cities
        const stateCityMap = {
  'Andhra Pradesh': [
    'Visakhapatnam', 'Vijayawada', 'Guntur', 'Nellore', 'Kurnool', 'Rajahmundry',
    'Tirupati', 'Anantapur', 'Chittoor', 'Khammam', 'Eluru', 'Srikakulam', 'Kadapa',
    'Bhimavaram', 'Proddatur', 'Machilipatnam', 'Tenali', 'Nandyal', 'Chirala',
    'Peddapuram', 'Palakollu', 'Tadipatri', 'Bapatla', 'Jaggayyapet', 'Chandragiri',
    'Mangalagiri', 'Gudivada', 'Kakinada', 'Pithapuram', 'Rajampet', 'Sullurpet',
    'Vemulawada', 'Srikalahasti', 'Sironcha', 'Kothapet', 'Markapur', 'Ramachandrapuram',
    'Samalkot', 'Rajamahendravaram', 'Anakapalli', 'Idupulapaya', 'Nellimarla', 'Vijayanagaram'
  ],'Karnataka': [
    'Bengaluru', 'Mysuru', 'Hubballi-Dharwad', 'Mangalore', 'Belagavi', 'Shimoga',
    'Ballari', 'Tumakuru', 'Davangere', 'Bijapur', 'Chitradurga', 'Bagalkot',
    'Hassan', 'Udupi', 'Mandya', 'Raichur', 'Kolar', 'Chikkamagaluru', 'Gulbarga',
    'Koppal', 'Karwar', 'Dakshina Kannada', 'Chikballapur', 'Ramanagaram', 'Yadgir',
    'Haveri', 'Bagalkote', 'Bidar', 'Sagar', 'Kundapura', 'Sirsi', 'Kushtagi', 'Bantwal',
    'Yelahanka', 'Shivamogga', 'Tiptur', 'Koppal', 'Puttur', 'Channarayapatna', 'Anekal',
    'Arasikere', 'Aland', 'Haveri', 'Hosanagara', 'Sira', 'Hosapete', 'Hindupur',
    'Sindhanur', 'Rabkavi', 'Alur', 'Madhugiri', 'Nagamangala', 'Mudigere', 'Gadag',
    'Kushtagi', 'Bailhongal', 'Mudhol', 'Bhadravati', 'Kolar Gold Fields', 'Srinivaspur',
    'Devadurga', 'Pavagada', 'Karkala', 'Tirumakudalu Narasipura', 'Honnavar', 'Kumarapattnam'
  ],'Maharashtra': [
    'Mumbai', 'Pune', 'Nagpur', 'Nashik', 'Aurangabad', 'Solapur', 'Thane', 
    'Satara', 'Kolhapur', 'Kolhapur', 'Akola', 'Amravati', 'Jalgaon', 'Chandrapur',
    'Ratnagiri', 'Raigad', 'Latur', 'Beed', 'Sindhudurg', 'Yavatmal', 'Nandurbar',
    'Bhandara', 'Gadchiroli', 'Parbhani', 'Osmanabad', 'Washim', 'Hingoli', 'Sangli',
    'Buldhana', 'Wardha', 'Nagapada', 'Bhiwandi', 'Panvel', 'Kalyan', 'Ulhasnagar',
    'Vasai', 'Virar', 'Dombivli', 'Andheri', 'Borivali', 'Goregaon', 'Malad',
    'Kurla', 'Vikhroli', 'Worli', 'Parel', 'Dadar', 'Lower Parel', 'Chinchpokli',
    'Kandivali', 'Kalyan', 'Dombivli', 'Worli', 'Dadar', 'Vile Parle', 'Bhandup',
    'Mulund', 'Chembur', 'Ghatkopar', 'Byculla', 'Andheri West', 'Andheri East', 
    'Santacruz', 'Mira-Bhayandar', 'Navi Mumbai', 'Thane West', 'Thane East', 'Panvel',
    'Chinchwad', 'Hadapsar', 'Koregaon Park', 'Viman Nagar', 'Shivajinagar', 'Karad',
    'Worli', 'Parel', 'Vile Parle East', 'Vile Parle West', 'Kandivali West', 'Kandivali East',
    'Borivali West', 'Borivali East', 'Mahalaxmi', 'Chembur East', 'Khar', 'Mahalaxmi East'
  ],
  "Tamil Nadu": [
    "Chennai",
    "Coimbatore",
    "Madurai",
    "Trichy",
    "Salem",
    "Tirunelveli",
    "Erode",
    "Tanjore",
    "Vellore",
    "Tirupur",
    "Chidambaram",
    "Dharmapuri",
    "Kanchipuram",
    "Karur",
    "Ramanathapuram",
    "Thanjavur",
    "Nagapattinam",
    "Kanyakumari",
    "Cuddalore",
    "Thoothukudi",
    "Sivakasi",
    "Pollachi",
    "Nagercoil",
    "Dindigul",
    "Villupuram",
    "Perambalur",
    "Arakkonam",
    "Pudukkottai",
    "Tiruvallur",
    "Kancheepuram",
    "Tiruvannamalai",
    "Vikramshila",
    "Pudupatti",
    "Sankarankovil",
    "Srirangam",
    "Bodinayakanur",
    "Rathinapuri",
    "Rishivandiyam",
    "Tiruvarur",
    "Virudhunagar",
    "Mettur",
    "Shenkottai",
    "Kovilpatti",
    "Tiruchengode",
    "Vedaranyam",
    "Karaikal",
    "Ambasamudram",
    "Kundadam",
    "Chetpet",
    "Guduvancheri",
    "Tiruchirapalli",
    "Sankarankovil",
    "Muthur",
    "Sivakasi",
    "Rajapalayam",
    "Thuraiyur",
    "Madhurai",
  ],
  Kerala: [
    "Thiruvananthapuram",
    "Kochi",
    "Kozhikode",
    "Kollam",
    "Thrissur",
    "Palakkad",
    "Kannur",
    "Kottayam",
    "Malappuram",
    "Pathanamthitta",
    "Wayanad",
    "Idukki",
    "Alappuzha",
    "Ernakulam",
    "Punnappra",
    "Vypin",
    "Attingal",
    "Cherthala",
    "Perumbavoor",
    "Muvattupuzha",
    "Paravur",
    "Mattancherry",
    "Kakkanad",
    "Changanassery",
    "Punalur",
    "Ranni",
    "Sabarimala",
    "Kayani",
    "Chalakkudy",
    "Varkala",
    "Kasaragod",
    "Munroe Island",
    "Kattappana",
    "Vadakara",
    "Adoor",
    "Kondotty",
    "Kothamangalam",
    "Karunagappally",
    "Mankada",
    "Tirur",
    "Kuttipuram",
    "Beypore",
    "Ponnani",
    "Peringathur",
    "Edappal",
    "Nellimattom",
    "Kodungallur",
    "Chirayinkeezhu",
    "Sreekariyam",
    "Vennala",
    "Sasthamcotta",
    "Nedumangadu",
    "Kalamassery",
    "Kunnamkulam",
    "Vatakara",
    "Anchal",
    "Azhikode",
    "Chavakkad",
    "Kollamkudimala",
    "Kallettumkara",
    "Chempu",
    "Angamaly",
    "Puthenchira",
    "Pallikkal",
    "Koduvayur",
    "Kuttikkanam",
    "Palai",
    "Kaduthuruthy",
    "Changanassery",
    "Vaikom",
  ],'Telangana': [
    'Hyderabad', 'Warangal', 'Khammam', 'Nizamabad', 'Karimnagar', 'Ramagundam',
    'Mahbubnagar', 'Suryapet', 'Nalgonda', 'Adilabad', 'Kothagudem', 'Jagtial',
    'Medak', 'Sangareddy', 'Peddapalli', 'Nagarkurnool', 'Jangaon', 'Yadadri',
    'Bhongir', 'Kamareddy', 'Kallur', 'Jagitial', 'Peddapalli', 'Mancherial',
    'Rangareddy', 'Vikarabad', 'Zahirabad', 'Medchal', 'Siddipet', 'Malkajgiri',
    'Saroornagar', 'Secunderabad', 'LB Nagar', 'Kukatpally', 'Dilsukhnagar',
    'Boduppal', 'Alwal', 'Madhapur', 'Gachibowli', 'Hitech City', 'Ameerpet',
    'Habsiguda', 'Chandanagar', 'Kothapet', 'LB Nagar', 'Malkajgiri', 'Miyapur',
    'Patancheru', 'Uppal', 'Nagole', 'Ramachandrapuram', 'Shamshabad', 'Tarnaka',
    'Musheerabad', 'Narayanguda', 'Malkajgiri', 'Kapra', 'Langar House', 'Secunderabad Cantonment'
  ]
};

        // Populate State Dropdown
        for (const state in stateCityMap) {
            const option = document.createElement("option");
            option.value = state;
            option.textContent = state;
            stateSelect.appendChild(option);
        }

        function updateCities(selectedCity = "") {
            const selectedState = stateSelect.value;
            citySelect.innerHTML = '<option value="">Select City</option>'; // Reset city dropdown

            if (selectedState && stateCityMap[selectedState]) {
                stateCityMap[selectedState].forEach(city => {
                    const option = document.createElement("option");
                    option.value = city;
                    option.textContent = city;
                    citySelect.appendChild(option);
                });

                // Restore previously selected city if applicable
                if (selectedCity && stateCityMap[selectedState].includes(selectedCity)) {
                    citySelect.value = selectedCity;
                }
            }
        }

        // Fetch saved values from JSP safely
        const savedState = `<%= (influencer != null) ? influencer.getState() : "" %>`.trim();
        const savedCity = `<%= (influencer != null) ? influencer.getCity() : "" %>`.trim();
        const savedGender = `<%= (influencer != null) ? influencer.getGender() : "" %>`.trim();

        // Set saved state first
        if (savedState && stateSelect.querySelector(`option[value="${savedState}"]`)) {
            stateSelect.value = savedState;
        }

        // Trigger city update **after** setting state
        updateCities(savedCity);

        // Set saved gender
        if (savedGender && genderSelect.querySelector(`option[value="${savedGender}"]`)) {
            genderSelect.value = savedGender;
        }

        // Attach event listener for state selection change
        stateSelect.addEventListener("change", function () {
            updateCities();
        });
    });

    </script>
</body>
</html>
