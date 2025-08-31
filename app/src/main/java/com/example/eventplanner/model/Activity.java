package com.example.eventplanner.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Activity {
    @SerializedName("id")
    @Expose
    private Integer id;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("description")
    @Expose
    private String description;

    @SerializedName("beginningDateTime")
    @Expose
    private String beginningDateTime;

    @SerializedName("endDateTime")
    @Expose
    private String endDateTime;

    @SerializedName("place")
    @Expose
    private String place;

    public Activity(Integer id,String name, String description, String beginningDateTime,String endDateTime, String place) {
        this.id=id;
        this.name = name;
        this.description = description;
        this.beginningDateTime = beginningDateTime;
        this.endDateTime = endDateTime;
        this.place = place;
    }

    public Activity() {
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getBeginningDateTime() {
        return beginningDateTime;
    }

    public void setBeginningDateTime(String beginningDateTime) {
        this.beginningDateTime = beginningDateTime;
    }

    public String getEndDateTime() {
        return endDateTime;
    }

    public void setEndDateTime(String endDateTime) {
        this.endDateTime = endDateTime;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }
}
