package com.dev.enquiremanagement;

import java.sql.Timestamp;

public class Enquiry {
    private int id;
    private String firstName;
    private String lastName;
    private String phone;
    private String email;
    private String message;
    private Timestamp createdAt;

    public Enquiry(int id, String firstName, String lastName, String phone, String email, String message, Timestamp createdAt) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.email = email;
        this.message = message;
        this.createdAt = createdAt;
    }

    // Getters
    public int getId() { return id; }
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public String getPhone() { return phone; }
    public String getEmail() { return email; }
    public String getMessage() { return message; }
    public Timestamp getCreatedAt() { return createdAt; }
}
