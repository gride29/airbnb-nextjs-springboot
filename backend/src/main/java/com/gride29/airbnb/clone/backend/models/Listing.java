package com.gride29.airbnb.clone.backend.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Listing {

    @Id
    private String id;

    private String title;

    private String description;

    private String category;

    private String imageSrc;

    private String locationValue;

    private int guestCount;

    private int roomCount;

    private int bathroomCount;

    private int price;

    public Listing() {

    }

    public Listing(String title, String description, String category, String imageSrc, String locationValue, int guestCount, int roomCount, int bathroomCount, int price) {
        this.title = title;
        this.description = description;
        this.category = category;
        this.imageSrc = imageSrc;
        this.locationValue = locationValue;
        this.guestCount = guestCount;
        this.roomCount = roomCount;
        this.bathroomCount = bathroomCount;
        this.price = price;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getImageSrc() {
        return imageSrc;
    }

    public void setImageSrc(String imageSrc) {
        this.imageSrc = imageSrc;
    }

    public String getLocationValue() {
        return locationValue;
    }

    public void setLocationValue(String locationValue) {
        this.locationValue = locationValue;
    }

    public int getGuestCount() {
        return guestCount;
    }

    public void setGuestCount(int guestCount) {
        this.guestCount = guestCount;
    }

    public int getRoomCount() {
        return roomCount;
    }

    public void setRoomCount(int roomCount) {
        this.roomCount = roomCount;
    }

    public int getBathroomCount() {
        return bathroomCount;
    }

    public void setBathroomCount(int bathroomCount) {
        this.bathroomCount = bathroomCount;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Listing{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", category='" + category + '\'' +
                ", imageSrc='" + imageSrc + '\'' +
                ", locationValue='" + locationValue + '\'' +
                ", guestCount=" + guestCount +
                ", roomCount=" + roomCount +
                ", bathroomCount=" + bathroomCount +
                ", price=" + price +
                '}';
    }
}