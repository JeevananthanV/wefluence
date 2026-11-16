package com.dev.influencermanagementview;

import java.time.LocalDateTime;

public class InfluencerProfileData {
    private String influencerId; // Custom influencer ID with prefix
    private String fullName;
    private String contactNumber;
    private String emailAddress;
    private String state;
    private String city;
    private String gender;
    private String instagramHandle;
    private String youtubeChannel;
    private String primaryCategory;
    private String otherCategory;
    private String engagementRate;
    private String longTermPartnership; // Yes or No
    private String notes;
    private LocalDateTime submissionDate; // TIMESTAMP in SQL

    // Constructor
    public InfluencerProfileData(String influencerId, String fullName, String contactNumber, String emailAddress,
                                 String state, String city, String gender, String instagramHandle, String youtubeChannel,
                                 String primaryCategory, String otherCategory, String engagementRate,
                                 String longTermPartnership, String notes, LocalDateTime submissionDate) {
        this.influencerId = influencerId;
        this.fullName = fullName;
        this.contactNumber = contactNumber;
        this.emailAddress = emailAddress;
        this.state = state;
        this.city = city;
        this.gender = gender;
        this.instagramHandle = (instagramHandle != null && !instagramHandle.isEmpty()) ? instagramHandle : "N/A";
        this.youtubeChannel = (youtubeChannel != null && !youtubeChannel.isEmpty()) ? youtubeChannel : "N/A";
        this.primaryCategory = primaryCategory;
        this.otherCategory = otherCategory;
        this.engagementRate = engagementRate;
        this.longTermPartnership = longTermPartnership;
        this.notes = notes;
        this.submissionDate = submissionDate;
    }

    // Getters and Setters
    public String getInfluencerId() {
        return influencerId;
    }

    public void setInfluencerId(String influencerId) {
        this.influencerId = influencerId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getInstagramHandle() {
        return instagramHandle;
    }

    public void setInstagramHandle(String instagramHandle) {
        this.instagramHandle = instagramHandle;
    }

    public String getYoutubeChannel() {
        return youtubeChannel;
    }

    public void setYoutubeChannel(String youtubeChannel) {
        this.youtubeChannel = youtubeChannel;
    }

    public String getPrimaryCategory() {
        return primaryCategory;
    }

    public void setPrimaryCategory(String primaryCategory) {
        this.primaryCategory = primaryCategory;
    }

    public String getOtherCategory() {
        return otherCategory;
    }

    public void setOtherCategory(String otherCategory) {
        this.otherCategory = otherCategory;
    }

    public String getEngagementRate() {
        return engagementRate;
    }

    public void setEngagementRate(String engagementRate) {
        this.engagementRate = engagementRate;
    }

    public String getLongTermPartnership() {
        return longTermPartnership;
    }

    public void setLongTermPartnership(String longTermPartnership) {
        this.longTermPartnership = longTermPartnership;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public LocalDateTime getSubmissionDate() {
        return submissionDate;
    }

    public void setSubmissionDate(LocalDateTime submissionDate) {
        this.submissionDate = submissionDate;
    }
}
