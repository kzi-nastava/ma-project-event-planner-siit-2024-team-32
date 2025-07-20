package com.example.eventplanner.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ServiceAndProductProvider {
    @SerializedName("id")
    @Expose
    private Integer id;

    @SerializedName("email")
    @Expose
    private String email;

    @SerializedName("password")
    @Expose
    private String password;

    @SerializedName("firstName")
    @Expose
    private String firstName;

    @SerializedName("lastName")
    @Expose
    private String lastName;

    @SerializedName("phoneNumber")
    @Expose
    private String phoneNumber;

    @SerializedName("city")
    @Expose
    private String city;

    @SerializedName("address")
    @Expose
    private String address;

    @SerializedName("country")
    @Expose
    private String country;

    @SerializedName("company")
    @Expose
    private String company;

    @SerializedName("description")
    @Expose
    private String description;

    public ServiceAndProductProvider(Integer id,String email,String password,String firstName,String lastName,String phoneNumber,String city,String address,String country,String company,String description){
        this.id=id;
        this.email=email;
        this.password=password;
        this.firstName=firstName;
        this.lastName=lastName;
        this.phoneNumber=phoneNumber;
        this.city=city;
        this.address=address;
        this.country=country;
        this.company=company;
        this.description=description;
    }

    public Integer getId() {
        return id;
    }


    public void setId(Integer id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public String getLastName() {
        return lastName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    public String getPhoneNumber() {
        return phoneNumber;
    }
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
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
    public String getCountry() {
        return country;
    }
    public void setCountry(String country) {
        this.country = country;
    }

    public String getCompanyName() {
        return company;
    }
    public void setCompanyName(String company) {
        this.company = company;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
}

