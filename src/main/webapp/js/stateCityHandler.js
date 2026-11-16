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


// Function to populate the state dropdown
function populateStates() {
    const stateSelect = document.getElementById("state");

    // Clear existing options and add the default one
    stateSelect.innerHTML = '<option value="">Select State</option>';

    for (let state in stateCityMap) {
        let option = new Option(state, state);
        stateSelect.add(option);
    }
}

// Function to update cities based on selected state
function updateCities() {
    const stateSelect = document.getElementById("state");
    const citySelect = document.getElementById("city");

    // Clear existing options and add the default one
    citySelect.innerHTML = '<option value="">Select City</option>';

    let cities = stateCityMap[stateSelect.value] || [];
    cities.forEach(city => {
        let option = new Option(city, city);
        citySelect.add(option);
    });
}

// Initialize dropdowns on page load
document.addEventListener("DOMContentLoaded", function() {
    populateStates();
    document.getElementById("state").addEventListener("change", updateCities);
});
