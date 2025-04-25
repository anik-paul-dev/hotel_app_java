package com.example.hotel_booking_app.models;

public class Query {
    private int id;
    private String name;
    private String email;
    private String subject;
    private String message;
    private String date;
    private boolean isRead;

    public Query() {}

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getSubject() { return subject; }
    public void setSubject(String subject) { this.subject = subject; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }

    public boolean isRead() { return isRead; }
    public void setRead(boolean read) { isRead = read; }
}