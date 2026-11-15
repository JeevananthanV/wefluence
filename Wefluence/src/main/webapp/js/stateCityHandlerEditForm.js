document.addEventListener("DOMContentLoaded", function () {
    const stateSelect = document.getElementById("state");
    const citySelect = document.getElementById("city");
    const genderSelect = document.querySelector("select[name='gender']");

    // Mapping states to their cities
    const stateCityMap = {
        'Andhra Pradesh': ['Visakhapatnam', 'Vijayawada', 'Guntur', 'Nellore', 'Kurnool', 'Rajahmundry', 'Tirupati', 'Anantapur'],
        'Karnataka': ['Bengaluru', 'Mysuru', 'Hubballi-Dharwad', 'Mangalore', 'Belagavi', 'Shimoga', 'Ballari', 'Tumakuru'],
        'Maharashtra': ['Mumbai', 'Pune', 'Nagpur', 'Nashik', 'Aurangabad', 'Solapur', 'Thane', 'Satara'],
        'Tamil Nadu': ['Chennai', 'Coimbatore', 'Madurai', 'Trichy', 'Salem', 'Tirunelveli', 'Erode', 'Tanjore'],
        'Kerala': ['Thiruvananthapuram', 'Kochi', 'Kozhikode', 'Kollam', 'Thrissur', 'Palakkad', 'Kannur', 'Kottayam'],
        'Telangana': ['Hyderabad', 'Warangal', 'Khammam', 'Nizamabad', 'Karimnagar', 'Ramagundam', 'Mahbubnagar', 'Suryapet']
    };

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

    // Set saved state and gender
    if (savedState && stateSelect.querySelector(`option[value="${savedState}"]`)) {
        stateSelect.value = savedState;
    }
    if (savedGender && genderSelect.querySelector(`option[value="${savedGender}"]`)) {
        genderSelect.value = savedGender;
    }

    // Trigger city update with saved city selection
    updateCities(savedCity);

    // Attach event listener for state selection change
    stateSelect.addEventListener("change", function () {
        updateCities();
    });
});
