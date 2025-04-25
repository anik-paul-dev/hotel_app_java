package com.example.hotel_booking_app.models;

import java.util.List;

public class Room {
    private int id;
    private String name;
    private int area;
    private double price;
    private int quantity;
    private int maxAdults;
    private int maxChildren;
    private String description;
    private List<String> images;
    private List<String> features;
    private List<String> facilities;
    private String status;
    private double rating;

    public Room() {}

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public int getArea() { return area; }
    public void setArea(int area) { this.area = area; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }

    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }

    public int getMaxAdults() { return maxAdults; }
    public void setMaxAdults(int maxAdults) { this.maxAdults = maxAdults; }

    public int getMaxChildren() { return maxChildren; }
    public void setMaxChildren(int maxChildren) { this.maxChildren = maxChildren; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public List<String> getImages() { return images; }
    public void setImages(List<String> images) { this.images = images; }

    public List<String> getFeatures() { return features; }
    public void setFeatures(List<String> features) { this.features = features; }

    public List<String> getFacilities() { return facilities; }
    public void setFacilities(List<String> facilities) { this.facilities = facilities; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public double getRating() { return rating; }
    public void setRating(double rating) { this.rating = rating; }

    public boolean isBooked() { return "booked".equalsIgnoreCase(status); }
}