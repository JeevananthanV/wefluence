// Updated BrandProfile.java
package com.dev.brandmanagementview;

import java.time.LocalDateTime;

public class BrandProfile {
    private int brandId; // Added brand_id field
    private String brandName;
    private String websiteUrl;
    private String industryCategory;
    private String customIndustry;
    private String companySize;
    private String contactPerson;
    private String emailAddress;
    private String phoneNumber;
    private String designation;
    private String budgetRange;
    private String socialMediaPlatforms;
    private String socialMediaOther;
    private String specialRequirements;
    private LocalDateTime createdAt; // Use LocalDateTime for created_at

    // Constructor
    public BrandProfile(int brandId, String brandName, String websiteUrl, String industryCategory, String customIndustry,
                        String companySize, String contactPerson, String emailAddress, String phoneNumber,
                        String designation, String budgetRange, String socialMediaPlatforms,
                        String socialMediaOther, String specialRequirements, LocalDateTime createdAt) {
        this.brandId = brandId;
        this.brandName = brandName;
        this.websiteUrl = websiteUrl;
        this.industryCategory = industryCategory;
        this.customIndustry = customIndustry;
        this.companySize = companySize;
        this.contactPerson = contactPerson;
        this.emailAddress = emailAddress;
        this.phoneNumber = phoneNumber;
        this.designation = designation;
        this.budgetRange = budgetRange;
        this.socialMediaPlatforms = socialMediaPlatforms;
        this.socialMediaOther = socialMediaOther;
        this.specialRequirements = specialRequirements;
        this.createdAt = createdAt;
    }

    // Getters and Setters
    public int getBrandId() { return brandId; }
    public void setBrandId(int brandId) { this.brandId = brandId; }

    public String getBrandName() { return brandName; }
    public void setBrandName(String brandName) { this.brandName = brandName; }

    public String getWebsiteUrl() { return websiteUrl; }
    public void setWebsiteUrl(String websiteUrl) { this.websiteUrl = websiteUrl; }

    public String getIndustryCategory() { return industryCategory; }
    public void setIndustryCategory(String industryCategory) { this.industryCategory = industryCategory; }

    public String getCustomIndustry() { return customIndustry; }
    public void setCustomIndustry(String customIndustry) { this.customIndustry = customIndustry; }

    public String getCompanySize() { return companySize; }
    public void setCompanySize(String companySize) { this.companySize = companySize; }

    public String getContactPerson() { return contactPerson; }
    public void setContactPerson(String contactPerson) { this.contactPerson = contactPerson; }

    public String getEmailAddress() { return emailAddress; }
    public void setEmailAddress(String emailAddress) { this.emailAddress = emailAddress; }

    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }

    public String getDesignation() { return designation; }
    public void setDesignation(String designation) { this.designation = designation; }

    public String getBudgetRange() { return budgetRange; }
    public void setBudgetRange(String budgetRange) { this.budgetRange = budgetRange; }

    public String getSocialMediaPlatforms() { return socialMediaPlatforms; }
    public void setSocialMediaPlatforms(String socialMediaPlatforms) { this.socialMediaPlatforms = socialMediaPlatforms; }

    public String getSocialMediaOther() { return socialMediaOther; }
    public void setSocialMediaOther(String socialMediaOther) { this.socialMediaOther = socialMediaOther; }

    public String getSpecialRequirements() { return specialRequirements; }
    public void setSpecialRequirements(String specialRequirements) { this.specialRequirements = specialRequirements; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
