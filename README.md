# 🌟 WeFluence — Connect Brands & Creators with Privacy

[![Java](https://img.shields.io/badge/Java-Servlets-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)](https://www.java.com/)
[![JSP](https://img.shields.io/badge/JSP-Jakarta%20EE-007396?style=for-the-badge&logo=java&logoColor=white)](https://jakarta.ee/)
[![MySQL](https://img.shields.io/badge/MySQL-8.0-005C84?style=for-the-badge&logo=mysql&logoColor=white)](https://www.mysql.com/)
[![HTML5](https://img.shields.io/badge/HTML5-E34F26?style=for-the-badge&logo=html5&logoColor=white)](https://developer.mozilla.org/en-US/docs/Web/HTML)
[![CSS3](https://img.shields.io/badge/CSS3-1572B6?style=for-the-badge&logo=css3&logoColor=white)](https://developer.mozilla.org/en-US/docs/Web/CSS)
[![JavaScript](https://img.shields.io/badge/JavaScript-ES6+-F7DF1E?style=for-the-badge&logo=javascript&logoColor=black)](https://developer.mozilla.org/en-US/docs/Web/JavaScript)

> **Portfolio Reference:** [Jeevananthan V Portfolio](https://github.com/JeevananthanV/portfolio)  
> **Author:** Jeevananthan V ([@JeevananthanV](https://github.com/JeevananthanV))

---

## 📖 Overview

**WeFluence** is an influencer booking and brand collaboration platform designed to eliminate friction in creator marketing. It connects verified content creators, influencers, and fashion models with corporate brands while maintaining privacy safeguards.

The platform streamlines discovery, campaign inquiries, model booking, direct messaging, and management into a unified system.

---

## ✨ Key Features & Portals

- **🔍 Influencer Discovery & Showcase (`influencing.html`):** Browse, filter, and inspect verified content creators with detailed metrics, niches, and media portfolios.
- **🏢 Brand Collaboration Portal (`branding.html`):** Brands can register, submit collaboration briefs, and initiate talent inquiries.
- **💃 Fashion & Commercial Models (`modeling.html`):** Explore models categorized by runway, commercial, editorial, and fitness with detailed attribute cards.
- **📝 In-Depth Onboarding Forms:**
  - `Brand-Indepth_Form.html`
  - `Influencer-Indepth-Form.html`
  - `Model-Indepth-Form.html`
- **📅 Direct Booking Systems:**
  - `book_influencer.html`
  - `book_model.html`
- **🛡️ Control & Enquiry Management (`ControlManagement.html`, `dashboard.jsp`):** Admin portal for reviewing submissions, approving creator accounts, and managing booking leads.

---

## 🛠️ Project Structure

```
Wefluence/
├── src/
│   ├── main/
│   │   ├── java/com/dev/        # Java Servlets, Models, & Controllers
│   │   │   ├── booking/         # InfluencerBooking, ModelBooking servlets
│   │   │   ├── brand/           # Brand onboarding & DAO logic
│   │   │   ├── influencer/      # Influencer controllers & verification
│   │   │   ├── model/           # Fashion model DAO & controllers
│   │   │   └── login/           # Authentication controllers
│   │   └── webapp/              # Web application root
│   │       ├── assets/          # Images, logos, and stylesheets
│   │       ├── js/              # Client-side handlers & filters
│   │       ├── json/            # Mock dataset for brands, influencers, models
│   │       ├── WEB-INF/         # web.xml configuration and library JARs
│   │       ├── index.html       # Platform homepage
│   │       ├── AboutUs.html     # About WeFluence
│   │       ├── branding.html    # Brand portal
│   │       ├── modeling.html    # Model portal
│   │       ├── influencing.html # Influencer portal
│   │       └── contactus.html   # Contact & inquiry form
├── Uploads/                     # User-uploaded assets & documents
├── server.js                    # Built-in lightweight HTTP preview server
├── package.json                 # Node runner scripts
├── .classpath                   # Eclipse / Java IDE classpath configuration
├── .project                     # Eclipse project definition
└── README.md                    # Documentation
```

---

## 🚀 Getting Started

### Option 1: Quick Run with Node.js
```bash
# Start the local development server (serves on port 5501)
npm start
# or
node server.js
```
Open [http://localhost:5501](http://localhost:5501) in your browser.

### Option 2: Run with VS Code Live Server
1. Open this folder in **VS Code**.
2. Right-click on `src/main/webapp/index.html`.
3. Select **"Open with Live Server"** (configured for port `5501` in `.vscode/settings.json`).

### Option 3: Deploy to Apache Tomcat (Java EE)
1. Import into **Eclipse IDE** (`File > Import > Existing Projects into Workspace`).
2. Add Apache Tomcat 9/10 server runtime.
3. Configure MySQL database using credentials in `src/main/java/com/dev/...`.
4. Deploy to Tomcat and navigate to `http://localhost:8080/Wefluence/`.

---

## 📄 License

This project is licensed under the MIT License — created by [Jeevananthan V](https://github.com/JeevananthanV).
