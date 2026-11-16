const words = [
  {
    title: "WHY YOU CHOOSE INFLUENCER",
    content: [
      "Engage directly with your audience.",
      "Share authentic experiences.",
      "Boost your brand's credibility.",
      "Drive meaningful connections.",
    ],
    image: "./assets/img/img1.webp",
  },
  {
    title: "WHY YOU CHOOSE BRAND",
    content: [
      "Reach the right audience.",
      "Create memorable campaigns.",
      "Collaborate with top influencers.",
      "Build trust and loyalty.",
    ],
    image: "./assets/img/img1.webp",
  },
  {
    title: "WHY YOU CHOOSE MODELING",
    content: [
      "Showcase creativity and style.",
      "Represent top-tier brands.",
      "Inspire new trends.",
      "Create timeless visuals.",
    ],
    image: "./assets/img/img1.webp",
  },
];

const staticTitle = document.getElementById("static-title");
const line1 = document.getElementById("line1");
const line2 = document.getElementById("line2");
const line3 = document.getElementById("line3");
const line4 = document.getElementById("line4");
const imageElement = document.getElementById("dynamic-image");

let currentIndex = 0;
let interId;

function updateContent() {
  const { title, content, image } = words[currentIndex];

  // Display the title
  setTimeout(() => {
    staticTitle.textContent = title;
    staticTitle.style.opacity = 1;
  }, 500);

  // Sequentially display the content lines and the image
  setTimeout(() => {
    line1.textContent = content[0];
    line1.style.opacity = 1;
  }, 600);

  setTimeout(() => {
    line2.textContent = content[1];
    line2.style.opacity = 1;
  }, 700);

  setTimeout(() => {
    line3.textContent = content[2];
    line3.style.opacity = 1;
  }, 800);

  setTimeout(() => {
    line4.textContent = content[3];
    line4.style.opacity = 1;
  }, 900);

  setTimeout(() => {
    imageElement.src = image;
    imageElement.style.opacity = 1;
  }, 1000);
}

function hideContent(callback) {
  staticTitle.style.opacity = 0;
  line1.style.opacity = 0;
  line2.style.opacity = 0;
  line3.style.opacity = 0;
  line4.style.opacity = 0;
  imageElement.style.opacity = 0;

  setTimeout(callback, 700);
}

document.getElementById("prev-btn").addEventListener("click", () => {
  clearInterval(interId);
  hideContent(() => {
    currentIndex = (currentIndex - 1 + words.length) % words.length;
    updateContent();
  });
});

document.getElementById("next-btn").addEventListener("click", () => {
  clearInterval(interId);
  hideContent(() => {
    currentIndex = (currentIndex + 1) % words.length;
    updateContent();
  });
});

function startAutoSlide() {
  interId = setInterval(() => {
    hideContent(() => {
      currentIndex = (currentIndex + 1) % words.length;
      updateContent();
    });
  }, 10000); // Total interval: 5 seconds content display + transition time
}

updateContent();
startAutoSlide();
   
