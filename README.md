# 🌟 WeFluence — Connect Brands & Creators with Privacy

[![React](https://img.shields.io/badge/React-18-61DAFB?style=for-the-badge&logo=react&logoColor=black)](https://react.dev/)
[![Tailwind CSS](https://img.shields.io/badge/Tailwind_CSS-38B2AC?style=for-the-badge&logo=tailwind-css&logoColor=white)](https://tailwindcss.com/)
[![Node.js](https://img.shields.io/badge/Node.js-339933?style=for-the-badge&logo=nodedotjs&logoColor=white)](https://nodejs.org/)
[![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)](https://www.java.com/)
[![MySQL](https://img.shields.io/badge/MySQL-005C84?style=for-the-badge&logo=mysql&logoColor=white)](https://www.mysql.com/)
[![Vercel](https://img.shields.io/badge/Vercel-Deployed-black?style=for-the-badge&logo=vercel&logoColor=white)](https://wefluence.vercel.app)

> **Live Demo:** [https://wefluence.vercel.app](https://wefluence.vercel.app)  
> **Portfolio Reference:** [Jeevananthan V Portfolio](https://jeevananthan.vercel.app)

---

## 📖 Overview

**WeFluence** is an end-to-end influencer booking and brand collaboration platform designed to eliminate friction in creator marketing. It connects verified content creators, influencers, and fashion models with brands while maintaining strict privacy safeguards until contracts are formalized.

The platform streamlines discovery, campaign creation, price negotiation, direct messaging, and performance analytics into a single unified workspace.

---

## ✨ Key Features

- **🔍 Influencer Discovery Interface:** Search and filter creators by niche (Tech, Fashion, Fitness, Lifestyle, Gaming), follower count, engagement rate, and location.
- **💼 Brand Collaboration Portal:** Marketing teams can publish campaign briefs, review creator applications, and book talent directly.
- **💬 Real-Time Messaging & Inquiries:** Secure communication channels for brand-creator negotiations with proposal attachments.
- **📈 Analytics & ROI Dashboard:** Real-time metrics for impressions, engagement rates, click-through performance, and campaign ROI.
- **🔐 Privacy & Verification:** Creator contact information and private data remain shielded until mutual collaboration terms are signed.
- **🛡️ Master Admin Command Center (`/admin`):** Full operational back-office for managing Creator approvals, Brand verifications, Model rosters, Campaign milestones, Escrow payouts, Inbound inquiries, KYC documents, and Platform take-rate configuration.

---

## ⚡ Admin Command Center Highlights (`/admin`)

Access the dedicated admin portal at `http://localhost:3002/admin` or click the floating quick-access button on the homepage:

- **📊 KPI Dashboard & Analytics:** Gross Booking Value ($184,520 GMV), Platform Revenue ($27,678 commission), 1,280 Verified Creators, 142 Active Collaborations, with interactive Chart.js growth breakdowns.
- **🌟 Influencer Roster & Approvals:** Status toggles (Active, Pending Review, Suspended), Tier & Niche filters, live search, and Media Kit inspection modals.
- **🏢 Brand & Corporate Oversight:** Verification badges, campaign audit counts, total corporate spend tracking, and representative contacts.
- **💃 Fashion & Commercial Models:** Specs, height, day rates, and photo portfolio approvals.
- **📢 Campaigns & Escrow Oversight:** Milestone tracking from Briefing to Verification, with one-click Escrow Payout release.
- **📩 Inbound Leads & Enquiries:** Filter by category (Brand Enquiries, Influencer Leads, Model Applications) with direct status updates (Contacted, Converted).
- **🛡️ KYC & Identity Verification:** Government ID review with AI identity match verification scores.
- **💰 Financial Ledger:** 15% Platform take-rate ledger, gross transactions, escrow holds, and PDF invoice downloads.
- **⚙️ Platform Settings:** Dynamic take-rate commission slider, escrow hold periods, and auto-approval rules.

---

## 🔑 Demo Access Credentials

The platform includes built-in demo authentication to test all portal roles:

| Role | Email | Password | Access Highlights |
| :--- | :--- | :--- | :--- |
| **Creator** | `creator@example.com` | `password123` | Creator Dashboard, Portfolio Manager, Campaign Invitations |
| **Brand** | `brand@example.com` | `password123` | Influencer Discovery, Campaign Creation, Direct Inquiries |
| **Admin** | `admin@example.com` | `password123` | Platform Moderation, Verification, System Health |

---

## 🛠️ Tech Stack & Architecture

- **Frontend:** React 18, React Router v6, TanStack Query, Tailwind CSS, Lucide Icons, Vite
- **Web Server:** Node.js HTTP Server with full Single Page Application (SPA) routing fallback
- **Enterprise Backend:** Java Servlets (com.dev.brand, com.dev.influencer, com.dev.booking, com.dev.model)
- **Database:** MySQL relational schema for brands, creators, inquiries, and bookings
- **Deployment:** Vercel Edge Network

---

## 🚀 Quick Start (Local Run)

### Prerequisites
- Node.js (v16 or higher)

### Installation & Run

```bash
# Clone the repository
git clone https://github.com/JeevananthanV/wefluence.git
cd wefluence

# Run the local server
npm start
```

The application will be live at:
```
http://localhost:3002
```

---

## 📂 Project Structure

```
wefluence/
├── index.html                  # Root SPA HTML template
├── server.js                   # Node.js SPA & static asset server
├── package.json                # Project scripts and metadata
├── README.md                   # Documentation
├── .gitignore                  # Git ignore rules
├── assets/                     # Production React JS & CSS bundles
│   ├── index-Lh5jPitu.js
│   ├── index-C9c_KVgG.css
│   ├── react-Gm9i_4Ku.js
│   ├── query-BSbhj0N5.js
│   └── router-BhmjhLpB.js
└── Wefluence/                  # Java EE Dynamic Web Application
    ├── src/main/java/          # Java Servlets & Controller logic
    ├── src/main/webapp/        # Web application views, JSPs, and styles
    ├── Uploads/                # Sample media & profile assets
    └── Wefluence_User_Manual_Clean.pdf # Detailed platform manual
```

---

## 👤 Author

**Jeevananthan V**  
- GitHub: [@JeevananthanV](https://github.com/JeevananthanV)  
- Portfolio: [https://jeevananthan.vercel.app](https://jeevananthan.vercel.app)  
- Email: [jeevananthanjeeva170902@gmail.com](mailto:jeevananthanjeeva170902@gmail.com)

