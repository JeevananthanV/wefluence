package com.dev.modelmanagementview;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class ModelProfileData {
    private String modelId; // UUID (CHAR(36) in SQL)
    private String fullName;
    private LocalDate dob;
    private int age;
    private String gender;
    private String phoneNumber;
    private String emailAddress;
    private String city;
    private String state;
    private String height;
    private String weight;
    private String hairColor;
    private String eyeColor;
    private String skinTone;
    private String previousWorks;
    private ModelingExperience modelingExperience; // Using Enum
    private String specializedCategory;
    private String instagramLink;
    private String about;
    private String specialSkills;
    private String portfolioPhoto1;
    private String portfolioPhoto2;
    private LocalDateTime createdAt; // TIMESTAMP in SQL

    // Enum for modeling experience levels
    public enum ModelingExperience {
        FRESHER, BEGINNER, INTERMEDIATE, EXPERIENCED;
    }

    // Constructor
    public ModelProfileData(String modelId, String fullName, LocalDate dob, int age, String gender, 
                        String phoneNumber, String emailAddress, String city, String state, 
                        String height, String weight, String hairColor, String eyeColor, 
                        String skinTone, String previousWorks, ModelingExperience modelingExperience, 
                        String specializedCategory, String instagramLink, String about, 
                        String specialSkills, String portfolioPhoto1, String portfolioPhoto2, 
                        LocalDateTime createdAt) {
        this.modelId = modelId;
        this.fullName = fullName;
        this.dob = dob;
        this.age = age;
        this.gender = gender;
        this.phoneNumber = phoneNumber;
        this.emailAddress = emailAddress;
        this.city = city;
        this.state = state;
        this.height = height;
        this.weight = weight;
        this.hairColor = hairColor;
        this.eyeColor = eyeColor;
        this.skinTone = skinTone;
        this.previousWorks = previousWorks;
        this.modelingExperience = modelingExperience;
        this.specializedCategory = specializedCategory;
        this.instagramLink = instagramLink != null ? instagramLink : "N/A";
        this.about = about;
        this.specialSkills = specialSkills;
        this.portfolioPhoto1 = portfolioPhoto1;
        this.portfolioPhoto2 = portfolioPhoto2;
        this.createdAt = createdAt;
    }

    // Getters and Setters
    public String getModelId() {
        return modelId;
    }

    public void setModelId(String modelId) {
        this.modelId = modelId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public LocalDate getDob() {
        return dob;
    }

    public void setDob(LocalDate dob) {
        this.dob = dob;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getHairColor() {
        return hairColor;
    }

    public void setHairColor(String hairColor) {
        this.hairColor = hairColor;
    }

    public String getEyeColor() {
        return eyeColor;
    }

    public void setEyeColor(String eyeColor) {
        this.eyeColor = eyeColor;
    }

    public String getSkinTone() {
        return skinTone;
    }

    public void setSkinTone(String skinTone) {
        this.skinTone = skinTone;
    }

    public String getPreviousWorks() {
        return previousWorks;
    }

    public void setPreviousWorks(String previousWorks) {
        this.previousWorks = previousWorks;
    }

    public ModelingExperience getModelingExperience() {
        return modelingExperience;
    }

    public void setModelingExperience(ModelingExperience modelingExperience) {
        this.modelingExperience = modelingExperience;
    }

    public String getSpecializedCategory() {
        return specializedCategory;
    }

    public void setSpecializedCategory(String specializedCategory) {
        this.specializedCategory = specializedCategory;
    }

    public String getInstagramLink() {
        return instagramLink;
    }
    public void setInstagramLink(String instagramLink) {
        this.instagramLink = instagramLink;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public String getSpecialSkills() {
        return specialSkills;
    }

    public void setSpecialSkills(String specialSkills) {
        this.specialSkills = specialSkills;
    }

    public String getPortfolioPhoto1() {
        return portfolioPhoto1;
    }

    public void setPortfolioPhoto1(String portfolioPhoto1) {
        this.portfolioPhoto1 = portfolioPhoto1;
    }

    public String getPortfolioPhoto2() {
        return portfolioPhoto2;
    }

    public void setPortfolioPhoto2(String portfolioPhoto2) {
        this.portfolioPhoto2 = portfolioPhoto2;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
