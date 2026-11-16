package com.dev.booking;

import java.sql.Date;
import java.sql.Timestamp;

public class ModelBookingData {
    private String modelId;
    private String modelName;
    private String brandName;
    private String bookingPerson;
    private String contactEmail;
    private String contactPhone;
    private Date shootStartDate;
    private Date shootEndDate;
    private String location;
    private int duration;
    private String specialRequests;
    private Timestamp bookingTimestamp;

    // Constructors
    public ModelBookingData() {}

    public ModelBookingData(String modelId, String modelName, String brandName, String bookingPerson,
                            String contactEmail, String contactPhone, Date shootStartDate, Date shootEndDate,
                            String location, int duration, String specialRequests, Timestamp bookingTimestamp) {
        this.modelId = modelId;
        this.modelName = modelName;
        this.brandName = brandName;
        this.bookingPerson = bookingPerson;
        this.contactEmail = contactEmail;
        this.contactPhone = contactPhone;
        this.shootStartDate = shootStartDate;
        this.shootEndDate = shootEndDate;
        this.location = location;
        this.duration = duration;
        this.specialRequests = specialRequests;
        this.bookingTimestamp = bookingTimestamp;
    }

    // Getters and Setters
    public String getModelId() { return modelId; }
    public void setModelId(String modelId) { this.modelId = modelId; }

    public String getModelName() { return modelName; }
    public void setModelName(String modelName) { this.modelName = modelName; }

    public String getBrandName() { return brandName; }
    public void setBrandName(String brandName) { this.brandName = brandName; }

    public String getBookingPerson() { return bookingPerson; }
    public void setBookingPerson(String bookingPerson) { this.bookingPerson = bookingPerson; }

    public String getContactEmail() { return contactEmail; }
    public void setContactEmail(String contactEmail) { this.contactEmail = contactEmail; }

    public String getContactPhone() { return contactPhone; }
    public void setContactPhone(String contactPhone) { this.contactPhone = contactPhone; }

    public Date getShootStartDate() { return shootStartDate; }
    public void setShootStartDate(Date shootStartDate) { this.shootStartDate = shootStartDate; }

    public Date getShootEndDate() { return shootEndDate; }
    public void setShootEndDate(Date shootEndDate) { this.shootEndDate = shootEndDate; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }

    public int getDuration() { return duration; }
    public void setDuration(int duration) { this.duration = duration; }

    public String getSpecialRequests() { return specialRequests; }
    public void setSpecialRequests(String specialRequests) { this.specialRequests = specialRequests; }

    public Timestamp getBookingTimestamp() { return bookingTimestamp; }
    public void setBookingTimestamp(Timestamp bookingTimestamp) { this.bookingTimestamp = bookingTimestamp; }
}
