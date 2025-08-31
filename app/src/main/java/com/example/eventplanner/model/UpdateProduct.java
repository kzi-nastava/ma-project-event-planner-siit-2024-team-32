package com.example.eventplanner.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UpdateProduct {

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

    @SerializedName("discount")
    @Expose
    private String discount;

    @SerializedName("status")
    @Expose
    private String status;

    @SerializedName("eventTypes")
    @Expose
    private String eventTypes;

    public UpdateProduct() {

    }

    public UpdateProduct(Integer id, String price, String name, String description, String discount, String status,
                            String eventTypes) {
        super();
        this.id = id;
        this.price = price;
        this.name = name;
        this.description = description;
        this.discount = discount;
        this.status = status;
        this.eventTypes = eventTypes;
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

}
