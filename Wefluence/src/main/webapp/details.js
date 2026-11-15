const urlParams = new URLSearchParams(window.location.search);
const id = urlParams.get("id");

const fetchDetails = async () => {
  try {
    const response = await fetch("data.json");
    if (!response.ok) {
      throw new Error("Failed to fetch data");
    }

    const data = await response.json();
    const item = data.find((item) => item.id == id);

    if (item) {
      // Populate the details section
      document.getElementById(
        "profile-picture"
      ).style.backgroundImage = `url(${item.picture})`;
      document.getElementById("model-name").innerText = item.name;
      document.getElementById("model-age").innerText = `Age: ${item.age}`;
      document.getElementById(
        "model-height"
      ).innerText = `Height: ${item.height}`;
      document.getElementById(
        "model-weight"
      ).innerText = `Weight: ${item.weight}`;
      document.getElementById("model-city").innerText = `City: ${item.city}`;
      document.getElementById("model-state").innerText = `State: ${item.state}`;
      document.getElementById(
        "model-joined"
      ).innerText = `Joined: ${item.joined_date}`;
      document.getElementById(
        "model-gender"
      ).innerText = `Gender: ${item.gender}`;
      document.getElementById("model-about").innerText = `About: ${item.about}`;

      // Populate the gallery section with 12 dummy images
      const galleryGrid = document.getElementById("gallery-grid");
      item.gallery.forEach((imageUrl, index) => {
        const img = document.createElement("img");
        img.src = imageUrl; // Use different dummy images from the gallery array
        img.alt = `${item.name} - Gallery Image ${index + 1}`;
        galleryGrid.appendChild(img);
      });
    } else {
      document.getElementById("details-container").innerText =
        "Details not found.";
    }
  } catch (error) {
    console.error("Error fetching details:", error);
  }
};

document.getElementById("book-button").onclick = () => {
  alert("Booking functionality not implemented yet!");
};

document.addEventListener("DOMContentLoaded", fetchDetails);
