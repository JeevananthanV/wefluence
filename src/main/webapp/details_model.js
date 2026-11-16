const urlParams = new URLSearchParams(window.location.search);
const id = urlParams.get("id");

const fetchDetails = async () => {
  try {
    const response = await fetch("json/models.json");
    if (!response.ok) throw new Error("Failed to fetch model data");

    const data = await response.json();
    const item = data.models.find(model => model.model_id === id);

    if (!item) {
      document.getElementById("details-container").innerText = "Model not found.";
      return;
    }

    // Set profile picture
    const profilePic = document.getElementById("profile-picture");
    if (profilePic) {
      profilePic.style.backgroundImage = `url(${item.profile_image})`;
    }

    // Set model details
    document.getElementById("model-name").innerText = item.full_name;
    document.getElementById("model-id").innerText = `Model ID: ${item.model_id}`;
    document.getElementById("model-age").innerText = `Age: ${item.age}`;
    document.getElementById("model-height").innerText = `Height: ${item.height}`;
    document.getElementById("model-weight").innerText = `Weight: ${item.weight}`;
    document.getElementById("model-city").innerText = `City: ${item.city}`;
    document.getElementById("model-state").innerText = `State: ${item.state}`;
    document.getElementById("model-joined").innerText = `DOB: ${item.dob}`;
    document.getElementById("model-gender").innerText = `Gender: ${item.gender}`;
    document.getElementById("model-category").innerText = `Category: ${item.specialized_category}`;
    document.getElementById("model-skills").innerText = `Skills: ${item.special_skills}`;
    document.getElementById("model-about").innerText = `About: ${item.about}`;

    // Populate gallery
    const galleryGrid = document.getElementById("gallery-grid");
    if (galleryGrid) {
      galleryGrid.innerHTML = ""; // Clear existing content

      (item.images || []).forEach((imgUrl, index) => {
        const img = document.createElement("img");
        img.src = imgUrl;
        img.alt = `${item.full_name} - Image ${index + 1}`;
        img.onerror = () => img.src = "images/default-placeholder.jpg"; // fallback
        galleryGrid.appendChild(img);
      });
    }

    // Enable booking button
    document.getElementById("book-button").onclick = () => {
      window.location.href = `book_model.html?id=${encodeURIComponent(item.model_id)}&name=${encodeURIComponent(item.full_name)}`;
    };

  } catch (error) {
    console.error("Error fetching details:", error);
    document.getElementById("details-container").innerText = "Failed to load model data.";
  }
};

document.addEventListener("DOMContentLoaded", fetchDetails);
