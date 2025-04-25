package com.example.hotel_booking_app.models;

import java.util.List;

public class Settings {
    private String websiteTitle;
    private String websiteDescription;
    private String address;
    private List<String> phoneNumbers;
    private String email;
    private String googleMapIframe;
    private boolean shutdown;
    private List<String> socialLinks;
    private String hotelImage;
    private String aboutDescription;
    private int roomsCount;
    private int customersCount;
    private int reviewsCount;
    private int staffsCount;

    public Settings() {}

    public String getWebsiteTitle() { return websiteTitle; }
    public void setWebsiteTitle(String websiteTitle) { this.websiteTitle = websiteTitle; }

    public String getWebsiteDescription() { return websiteDescription; }
    public void setWebsiteDescription(String websiteDescription) { this.websiteDescription = websiteDescription; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public List<String> getPhoneNumbers() { return phoneNumbers; }
    public void setPhoneNumbers(List<String> phoneNumbers) { this.phoneNumbers = phoneNumbers; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getGoogleMapIframe() { return googleMapIframe; }
    public void setGoogleMapIframe(String googleMapIframe) { this.googleMapIframe = googleMapIframe; }

    public boolean isShutdown() { return shutdown; }
    public void setShutdown(boolean shutdown) { this.shutdown = shutdown; }

    public List<String> getSocialLinks() { return socialLinks; }
    public void setSocialLinks(List<String> socialLinks) { this.socialLinks = socialLinks; }

    public String getHotelImage() { return hotelImage; }
    public void setHotelImage(String hotelImage) { this.hotelImage = hotelImage; }

    public String getAboutDescription() { return aboutDescription; }
    public void setAboutDescription(String aboutDescription) { this.aboutDescription = aboutDescription; }

    public int getRoomsCount() { return roomsCount; }
    public void setRoomsCount(int roomsCount) { this.roomsCount = roomsCount; }

    public int getCustomersCount() { return customersCount; }
    public void setCustomersCount(int customersCount) { this.customersCount = customersCount; }

    public int getReviewsCount() { return reviewsCount; }
    public void setReviewsCount(int reviewsCount) { this.reviewsCount = reviewsCount; }

    public int getStaffsCount() { return staffsCount; }
    public void setStaffsCount(int staffsCount) { this.staffsCount = staffsCount; }
}