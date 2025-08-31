package com.example.eventplanner.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Product {
    @SerializedName("id")
    @Expose
    private Integer id;

    @SerializedName("price")
    @Expose
    private String price;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("description")
    @Expose
    private String description;

    @SerializedName("serviceAndProductCategory")
    @Expose
    private String serviceAndProductCategory;

    @SerializedName("discount")
    @Expose
    private String discount;

    @SerializedName("status")
    @Expose
    private String status;

    @SerializedName("eventTypes")
    @Expose
    private String eventTypes;

    @SerializedName("recCat")
    @Expose
    private String recCat;

    public Product() {
    }

    public Product(Integer id, String price, String name, String description, String serviceAndProductCategory,
                            String discount, String status, String eventTypes, String recCat) {
        this.id = id;
        this.price = price;
        this.name = name;
        this.description = description;
        this.serviceAndProductCategory = serviceAndProductCategory;
        this.discount = discount;
        this.status = status;
        this.eventTypes = eventTypes;
        this.recCat = recCat;
    }
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
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

    public String getServiceAndProductCategory() {
        return serviceAndProductCategory;
    }

    public void setServiceAndProductCategory(String serviceAndProductCategory) {
        this.serviceAndProductCategory = serviceAndProductCategory;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getEventTypes() {
        return eventTypes;
    }

    public void setEventTypes(String eventTypes) {
        this.eventTypes = eventTypes;
    }

    public String getRecCat() {
        return recCat;
    }

    public void setRecCat(String recCat) {
        this.recCat = recCat;
    }
}
