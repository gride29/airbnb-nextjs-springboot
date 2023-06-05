package com.gride29.airbnb.clone.backend.dto;

import com.mongodb.lang.Nullable;

public class ListingSearchRequest {
    @Nullable
    private String userId;

    @Nullable
    private String roomCount;

    @Nullable
    private String guestCount;

    @Nullable
    private String bathroomCount;

    @Nullable
    private String location;

    @Nullable
    private String startDate;

    @Nullable
    private String endDate;

    @Nullable
    private String category;

    public ListingSearchRequest() {
    }

    public ListingSearchRequest(@Nullable String userId, @Nullable String roomCount, @Nullable String guestCount, @Nullable String bathroomCount, @Nullable String location, @Nullable String startDate, @Nullable String endDate, @Nullable String category) {
        this.userId = userId;
        this.roomCount = roomCount;
        this.guestCount = guestCount;
        this.bathroomCount = bathroomCount;
        this.location = location;
        this.startDate = startDate;
        this.endDate = endDate;
        this.category = category;
    }

    @Nullable
    public String getUserId() {
        return userId;
    }

    public void setUserId(@Nullable String userId) {
        this.userId = userId;
    }

    @Nullable
    public String getRoomCount() {
        return roomCount;
    }

    public void setRoomCount(@Nullable String roomCount) {
        this.roomCount = roomCount;
    }

    @Nullable
    public String getGuestCount() {
        return guestCount;
    }

    public void setGuestCount(@Nullable String guestCount) {
        this.guestCount = guestCount;
    }

    @Nullable
    public String getBathroomCount() {
        return bathroomCount;
    }

    public void setBathroomCount(@Nullable String bathroomCount) {
        this.bathroomCount = bathroomCount;
    }

    @Nullable
    public String getLocation() {
        return location;
    }

    public void setLocation(@Nullable String location) {
        this.location = location;
    }

    @Nullable
    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(@Nullable String startDate) {
        this.startDate = startDate;
    }

    @Nullable
    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(@Nullable String endDate) {
        this.endDate = endDate;
    }

    @Nullable
    public String getCategory() {
        return category;
    }

    public void setCategory(@Nullable String category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return "ListingSearchRequest{" +
                "userId='" + userId + '\'' +
                ", roomCount='" + roomCount + '\'' +
                ", guestCount='" + guestCount + '\'' +
                ", bathroomCount='" + bathroomCount + '\'' +
                ", location='" + location + '\'' +
                ", startDate='" + startDate + '\'' +
                ", endDate='" + endDate + '\'' +
                ", category='" + category + '\'' +
                '}';
    }
}