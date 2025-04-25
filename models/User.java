package com.example.hotel_booking_app.models;

public class User {
    private String id;
    private String name;
    private String phone;
    private String email;
    private String address;
    private String pincode;
    private String dob;
    private String imageUrl;
    private String status;
    private boolean isActive;

    public User() {}

    public User(String id, String name, String phone, String email, String address, String pincode, String dob, String imageUrl, String status, boolean isActive) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.pincode = pincode;
        this.dob = dob;
        this.imageUrl = imageUrl;
        this.status = status;
        this.isActive = isActive;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public String getPincode() { return pincode; }
    public void setPincode(String pincode) { this.pincode = pincode; }

    public String getDob() { return dob; }
    public void setDob(String dob) { this.dob = dob; }

    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public boolean isActive() { return isActive; }
    public void setActive(boolean active) { isActive = active; }
}