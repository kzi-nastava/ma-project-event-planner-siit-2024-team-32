package com.example.eventplanner.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UpdateEventType {
    @SerializedName("id")
    @Expose
    private Integer id;

    @SerializedName("description")
    @Expose
    private String description;

    @SerializedName("eventTypeStatus")
    @Expose
    private String eventTypeStatus;

    @SerializedName("recommendedServiceAndProductCategories")
    @Expose
    private String recommendedServiceAndProductCategories;

    public UpdateEventType(Integer id,String description,String eventTypeStatus,String recommendedServiceAndProductCategories){
        this.id=id;
        this.description=description;
        this.eventTypeStatus=eventTypeStatus;
        this.recommendedServiceAndProductCategories=recommendedServiceAndProductCategories;
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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


    public void setRecommendedServiceAndProductCategories(
            String recommendedServiceAndProductCategories) {
        this.recommendedServiceAndProductCategories = recommendedServiceAndProductCategories;
    }
}
