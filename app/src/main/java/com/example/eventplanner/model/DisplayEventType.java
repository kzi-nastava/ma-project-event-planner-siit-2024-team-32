package com.example.eventplanner.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DisplayEventType {
    @SerializedName("id")
    @Expose
    private Integer id;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("description")
    @Expose
    private String description;

    @SerializedName("eventTypeStatus")
    @Expose
    private String eventTypeStatus;

    @SerializedName("recommendedServiceAndProductCategories")
    @Expose
    private String recommendedServiceAndProductCategories;

    public DisplayEventType(Integer id, String name, String description, String eventTypeStatus,
                               String recommendedServiceAndProductCategories) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.eventTypeStatus = eventTypeStatus;
        this.recommendedServiceAndProductCategories = recommendedServiceAndProductCategories;
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
    public String getEventTypeStatus() {
        return eventTypeStatus;
    }
    public void setEventTypeStatus(String eventTypeStatus) {
        this.eventTypeStatus = eventTypeStatus;
    }
    public String getRecommendedServiceAndProductCategories() {
        return recommendedServiceAndProductCategories;
    }
    public void setRecommendedServiceAndProductCategories(String recommendedServiceAndProductCategories) {
        this.recommendedServiceAndProductCategories = recommendedServiceAndProductCategories;
    }

}
