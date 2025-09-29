package com.example.eventplanner.dto;

import androidx.annotation.NonNull;

import com.example.eventplanner.model.enums.ServiceAndProductCategoryStatus;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CreateServiceAndProductCategoryDTO {
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("serviceAndProductCategoryStatus")
    @Expose
    private ServiceAndProductCategoryStatus serviceAndProductCategoryStatus;

    public CreateServiceAndProductCategoryDTO() {
    }

    public CreateServiceAndProductCategoryDTO(String name, String description, ServiceAndProductCategoryStatus serviceAndProductCategoryStatus) {
        this.name = name;
        this.description = description;
        this.serviceAndProductCategoryStatus = serviceAndProductCategoryStatus;
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

    public ServiceAndProductCategoryStatus getServiceAndProductCategoryStatus() {
        return serviceAndProductCategoryStatus;
    }

    public void setServiceAndProductCategoryStatus(ServiceAndProductCategoryStatus serviceAndProductCategoryStatus) {
        this.serviceAndProductCategoryStatus = serviceAndProductCategoryStatus;
    }

    @NonNull
    @Override
    public String toString() {
        return "CreateServiceAndProductCategoryDTO{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", serviceAndProductCategoryStatus=" + serviceAndProductCategoryStatus +
                '}';
    }
}
