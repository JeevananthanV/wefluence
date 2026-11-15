const getInfluencerId = () => {
  const params = new URLSearchParams(window.location.search);
  return params.get("id");
};

const loadInfluencerDetails = async () => {
  const id = getInfluencerId();
  const profilePicture = document.getElementById("profile-picture");
  const name = document.getElementById("model-name");
  const influencerId = document.getElementById("model-id");
  const city = document.getElementById("model-city");
  const state = document.getElementById("model-state");
  const gender = document.getElementById("model-gender");
  const category = document.getElementById("model-category");
  const skills = document.getElementById("model-skills");
  const about = document.getElementById("model-about");
  const galleryGrid = document.getElementById("gallery-grid");
  const bookButton = document.getElementById("book-button");

  try {
    const res = await fetch("json/influencers.json");
    if (!res.ok) throw new Error("Could not fetch data");

    const data = await res.json();
    const influencer = data.influencers.find(i => i.influencer_id === id);

    if (!influencer) {
      document.getElementById("details-container").innerHTML = "<p>Influencer not found.</p>";
      return;
    }

    profilePicture.style.backgroundImage = `url('${influencer.profileImage}')`;
    name.textContent = influencer.fullName || "N/A";
    influencerId.textContent = `Influencer ID: ${influencer.influencer_id}`;
    city.textContent = `City: ${influencer.city || "N/A"}`;
    state.textContent = `State: ${influencer.state || "N/A"}`;
    gender.textContent = `Gender: ${influencer.gender || "N/A"}`;
    category.textContent = `Category: ${influencer.primaryCategory || "N/A"}`;
    skills.textContent = `Long-term Partnership: ${influencer.longTermPartnership || "N/A"}`;
    about.textContent = `Notes: ${influencer.notes || "N/A"}`;

    // Populate gallery
    galleryGrid.innerHTML = influencer.images && influencer.images.length
      ? influencer.images.map((img, index) => `
          <img src="${img}" alt="${influencer.fullName} - Image ${index + 1}" 
               onerror="this.src='images/default-placeholder.jpg';" />
        `).join("")
      : "<p>No gallery images available.</p>";

    // Redirect on book button click
    if (bookButton) {
      bookButton.onclick = () => {
        window.location.href = `book_influencer.html?id=${encodeURIComponent(influencer.influencer_id)}&name=${encodeURIComponent(influencer.fullName)}`;
      };
    }

  } catch (error) {
    console.error("Error loading influencer details:", error);
    document.getElementById("details-container").innerHTML = "<p>Error loading details.</p>";
  }
};

document.addEventListener("DOMContentLoaded", loadInfluencerDetails);
