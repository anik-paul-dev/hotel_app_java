package com.example.hotel_booking_app.models;

public class Review {
    private int id;
    private int roomId;
    private String userId;
    private String userName;
    private float rating;
    private String review;
    private String date;

    public Review() {}

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getRoomId() { return roomId; }
    public void setRoomId(int roomId) { this.roomId = roomId; }

    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }

    public String getUserName() { return userName; }
    public void setUserName(String userName) { this.userName = userName; }

    public float getRating() { return rating; }
    public void setRating(float rating) { this.rating = rating; }

    public String getReview() { return review; }
    public void setReview(String review) { this.review = review; }

    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }
}