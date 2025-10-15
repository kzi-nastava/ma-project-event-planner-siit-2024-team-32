package com.example.eventplanner.model;

import androidx.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import retrofit2.Response;

public class ServiceAndProductCategory {
    @SerializedName("id")
    @Expose
    private Integer id;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("description")
    @Expose
    private String description;

    @SerializedName("serviceAndProductCategoryStatus")
    @Expose
    private String serviceAndProductCategoryStatus;

    public ServiceAndProductCategory() {
    }

    public ServiceAndProductCategory(Integer id, String name, String description,
                                     String serviceAndProductCategoryStatus) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.serviceAndProductCategoryStatus = serviceAndProductCategoryStatus;
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
    public String getServiceAndProductCategoryStatus() {
        return serviceAndProductCategoryStatus;
    }
    public void setServiceAndProductCategoryStatus(String serviceAndProductCategoryStatus) {
        this.serviceAndProductCategoryStatus = serviceAndProductCategoryStatus;
    }

    @NonNull
    @Override
    public String toString() {
        return "ServiceAndProductCategory{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", serviceAndProductCategoryStatus='" + serviceAndProductCategoryStatus + '\'' +
                '}';
    }
}
