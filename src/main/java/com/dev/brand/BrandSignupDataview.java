package com.dev.brand;

public class BrandSignupDataview {
    private String brandName;
    private String contactPerson;
    private String emailAddress;
    private String phoneNumber;

    // Constructor
    public BrandSignupDataview(String brandName, String contactPerson, String emailAddress, String phoneNumber) {
        this.brandName = brandName;
        this.contactPerson = contactPerson;
        this.emailAddress = emailAddress;
        this.phoneNumber = phoneNumber;
    }

    // Getters and Setters
    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getContactPerson() {
        return contactPerson;
    }

    public void setContactPerson(String contactPerson) {
        this.contactPerson = contactPerson;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
