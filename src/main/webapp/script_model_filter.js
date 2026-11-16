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




let currentPage = 1;
const itemsPerPage = 12;
let allData = [];

const fetchAndFilterData = async () => {
  const gender = document.getElementById("gender").value;
  const state = document.getElementById("state").value;
  const city = document.getElementById("city").value;
  const loading = document.getElementById("loading");
  const resultsCount = document.getElementById("results-count");

  loading.style.display = "block";

  try {
    const response = await fetch("json/models.json");
    if (!response.ok) throw new Error("Network response was not ok");

    const jsonData = await response.json();
    allData = jsonData.models.filter(item => item.status.toLowerCase() === "active");

    let filteredData = allData.filter(
      item =>
        (!gender || item.gender === gender) &&
        (!state || item.state === state) &&
        (!city || item.city === city)
    );

    filteredData.sort((a, b) => a.model_id - b.model_id);
    currentPage = 1;

    displayData(filteredData);
    updatePaginationButtons(filteredData);
    resultsCount.textContent = `${filteredData.length} result(s) found`;
    document.getElementById("results-section").scrollIntoView({ behavior: "smooth" });

  } catch (error) {
    console.error("Error fetching data:", error);
  } finally {
    loading.style.display = "none";
  }
};

const displayData = (data) => {
  const startIndex = (currentPage - 1) * itemsPerPage;
  const endIndex = startIndex + itemsPerPage;
  const paginatedData = data.slice(startIndex, endIndex);

  const modalResults = document.getElementById("modal-results");
  modalResults.innerHTML = paginatedData.length
    ? paginatedData.map(item => `
      <div class="model-item">
        <div class="image-container" data-id="${item.model_id}">
          <img src="${item.profile_image}" alt="${item.full_name}" class="model-image" />
          <div class="model-info">
            <p><strong>Name:</strong> ${item.full_name}</p>
            <p><strong>Age:</strong> ${item.age}</p>
            <p><strong>City:</strong> ${item.city}</p>
            <p><strong>State:</strong> ${item.state}</p>
          </div>
        </div>
      </div>
    `).join("")
    : "<p>No results found.</p>";

  document.querySelectorAll(".image-container").forEach(container => {
    container.addEventListener("click", () => {
      const id = container.getAttribute("data-id");
      redirectToDetails(id);
    });
  });
};

const redirectToDetails = (id) => {
  window.location.href = `details_model.html?id=${id}`;
};

const updatePaginationButtons = (data) => {
  const totalPages = Math.ceil(data.length / itemsPerPage);
  const prevButton = document.getElementById("prevButton");
  const nextButton = document.getElementById("nextButton");

  prevButton.disabled = currentPage === 1;
  nextButton.disabled = currentPage === totalPages;

  prevButton.onclick = () => {
    if (currentPage > 1) {
      currentPage--;
      displayData(data);
      updatePaginationButtons(data);
    }
  };

  nextButton.onclick = () => {
    if (currentPage < totalPages) {
      currentPage++;
      displayData(data);
      updatePaginationButtons(data);
    }
  };
};

document.addEventListener("DOMContentLoaded", () => {
  const stateSelect = document.getElementById("state");
  const citySelect = document.getElementById("city");

  // Populate state dropdown
  Object.keys(stateCityMap).forEach(state => {
    const option = document.createElement("option");
    option.value = state;
    option.textContent = state;
    stateSelect.appendChild(option);
  });

  // Filter when form changes
  document.getElementById("filter-form").addEventListener("change", fetchAndFilterData);

  // Update city dropdown dynamically
  stateSelect.addEventListener("change", function () {
    const selectedState = this.value;
    citySelect.innerHTML = '<option value="">Select City</option>';
    citySelect.disabled = !stateCityMap[selectedState];

    if (stateCityMap[selectedState]) {
      stateCityMap[selectedState].forEach(city => {
        const option = document.createElement("option");
        option.value = city;
        option.textContent = city;
        citySelect.appendChild(option);
      });
    }

    fetchAndFilterData();
  });

  fetchAndFilterData(); // Initial load
});
