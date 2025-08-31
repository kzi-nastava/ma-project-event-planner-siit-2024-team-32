package com.example.eventplanner.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetProduct {

    @SerializedName("id")
    @Expose
    private Integer id;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("price")
    @Expose
    private String price;

    @SerializedName("serviceAndProductCategory")
    @Expose
    private String serviceAndProductCategory;

    public GetProduct(Integer id,String name,String price,String serviceAndProductCategory) {
        this.id = id;
        this.price = price;
        this.name = name;
        this.serviceAndProductCategory = serviceAndProductCategory;
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

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getServiceAndProductCategory() {
        return serviceAndProductCategory;
    }

    public void setServiceAndProductCategory(String serviceAndProductCategory) {
        this.serviceAndProductCategory = serviceAndProductCategory;
    }
}
