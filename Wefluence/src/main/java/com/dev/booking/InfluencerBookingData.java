package com.dev.booking;

import java.sql.Date;
import java.sql.Timestamp;

public class InfluencerBookingData {
    private String influencerId;
    private String influencerName;
    private String contactName;
    private String email;
    private String phone;
    private String platform;
    private String profileLink;
    private double budget;
    private Date startDate;
    private Date endDate;
    private String details;
    private Timestamp bookingTimestamp;

    // Constructors
    public InfluencerBookingData() {}

    public InfluencerBookingData(String influencerId, String influencerName, String contactName, String email,
                                 String phone, String platform, String profileLink, double budget,
                                 Date startDate, Date endDate, String details, Timestamp bookingTimestamp) {
        this.influencerId = influencerId;
        this.influencerName = influencerName;
        this.contactName = contactName;
        this.email = email;
        this.phone = phone;
        this.platform = platform;
        this.profileLink = profileLink;
        this.budget = budget;
        this.startDate = startDate;
        this.endDate = endDate;
        this.details = details;
        this.bookingTimestamp = bookingTimestamp;
    }

    // Getters and Setters
    public String getInfluencerId() { return influencerId; }
    public void setInfluencerId(String influencerId) { this.influencerId = influencerId; }

    public String getInfluencerName() { return influencerName; }
    public void setInfluencerName(String influencerName) { this.influencerName = influencerName; }

    public String getContactName() { return contactName; }
    public void setContactName(String contactName) { this.contactName = contactName; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getPlatform() { return platform; }
    public void setPlatform(String platform) { this.platform = platform; }

    public String getProfileLink() { return profileLink; }
    public void setProfileLink(String profileLink) { this.profileLink = profileLink; }

    public double getBudget() { return budget; }
    public void setBudget(double budget) { this.budget = budget; }

    public Date getStartDate() { return startDate; }
    public void setStartDate(Date startDate) { this.startDate = startDate; }

    public Date getEndDate() { return endDate; }
    public void setEndDate(Date endDate) { this.endDate = endDate; }

    public String getDetails() { return details; }
    public void setDetails(String details) { this.details = details; }

    public Timestamp getBookingTimestamp() { return bookingTimestamp; }
    public void setBookingTimestamp(Timestamp bookingTimestamp) { this.bookingTimestamp = bookingTimestamp; }
}
