package com.example.eventplanner.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CreatedEvent {

    @SerializedName("id")
    @Expose
    private Integer id;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("privacy")
    @Expose
    private String privacy;

    @SerializedName("description")
    @Expose
    private String description;

    @SerializedName("country")
    @Expose
    private String country;

    @SerializedName("city")
    @Expose
    private String city;

    @SerializedName("address")
    @Expose
    private String address;

    @SerializedName("participants")
    @Expose
    private String participants;

    @SerializedName("dateTime")
    @Expose
    private String dateTime;

    @SerializedName("eventType")
    @Expose
    private String eventType;

    @SerializedName("recEvType")
    @Expose
    private String recEvType;

    public CreatedEvent() {
    }


    public CreatedEvent(Integer id, String name, String privacy, String description, String country, String city,
                        String address, String participants, String dateTime, String eventType,String recEvType) {
        this.id = id;
        this.name = name;
        this.privacy = privacy;
        this.description = description;
        this.country = country;
        this.city = city;
        this.address = address;
        this.participants = participants;
        this.dateTime = dateTime;
        this.eventType = eventType;
        this.recEvType=recEvType;
    }



    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getPrivacy() {
        return privacy;
    }
    public void setPrivacy(String privacy) {
        this.privacy = privacy;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public String getCountry() {
        return country;
    }
    public void setCountry(String country) {
        this.country = country;
    }
    public String getCity() {
        return city;
    }
    public void setCity(String city) {
        this.city = city;
    }
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public String getParticipants() {
        return participants;
    }
    public void setParticipants(String participants) {
        this.participants = participants;
    }
    public String getDateTime() {
        return dateTime;
    }
    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }
    public String getEventType() {
        return eventType;
    }
    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public String getRecEvType() {
        return recEvType;
    }
    public void setRecEvType(String recEvType) {
        this.recEvType = recEvType;
    }
}
