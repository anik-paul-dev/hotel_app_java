package com.example.hotel_booking_app.models;

public class ManagementTeam {
    private int id;
    private String name;
    private String jobTitle;
    private String image;

    public ManagementTeam() {}

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getJobTitle() { return jobTitle; }
    public void setJobTitle(String jobTitle) { this.jobTitle = jobTitle; }

    public String getImage() { return image; }
    public void setImage(String image) { this.image = image; }
}