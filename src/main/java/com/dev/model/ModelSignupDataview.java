package com.dev.model;

public class ModelSignupDataview {
    private String fullName;
    private String emailAddress;
    private String contactNumber;
    private int age;

    public ModelSignupDataview(String fullName, String emailAddress, String contactNumber, int age) {
        this.fullName = fullName;
        this.emailAddress = emailAddress;
        this.contactNumber = contactNumber;
        this.age = age;
    }

    public String getFullName() { return fullName; }
    public String getEmailAddress() { return emailAddress; }
    public String getContactNumber() { return contactNumber; }
    public int getAge() { return age; }
}
