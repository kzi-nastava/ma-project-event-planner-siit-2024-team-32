package com.example.eventplanner.model;

import androidx.annotation.NonNull;

import com.example.eventplanner.model.enums.UserStatus;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;

public class RegisteredUser {
    @SerializedName("reviewStatus")
    @Expose
    private Integer id;
    @SerializedName("reviewStatus")
    @Expose
    private String email;
    @SerializedName("reviewStatus")
    @Expose
    private String password;
    @SerializedName("reviewStatus")
    @Expose
    private String firstName;
    @SerializedName("reviewStatus")
    @Expose
    private String lastName;
    @SerializedName("reviewStatus")
    @Expose
    private String phoneNumber;
    @SerializedName("reviewStatus")
    @Expose
    private Boolean alertsSilenced;
    @SerializedName("reviewStatus")
    @Expose
    private Timestamp lastPasswordResetDate;
    @SerializedName("reviewStatus")
    @Expose
    private Collection<RegisteredUser> blockedUsers=new ArrayList<RegisteredUser>();
    @SerializedName("reviewStatus")
    @Expose
    private LocalDateTime lastSuspensionDateTime;
    @SerializedName("reviewStatus")
    @Expose
    private UserStatus status;
    @SerializedName("reviewStatus")
    @Expose
    private Location location;
    @SerializedName("reviewStatus")
    @Expose
    private Collection<Service> favoriteServices=new ArrayList<Service>();
    @SerializedName("reviewStatus")
    @Expose
    private Collection<Product> favoriteProducts=new ArrayList<Product>();
    @SerializedName("reviewStatus")
    @Expose
    private Collection<Event> favoriteEvents=new ArrayList<Event>();
    @SerializedName("reviewStatus")
    @Expose
    private String profilePicture;
    @SerializedName("reviewStatus")
    @Expose
    private Collection<Event> registeredEvents=new ArrayList<Event>();
    @SerializedName("reviewStatus")
    @Expose
    private String authorities;

    public RegisteredUser() {
    }

    public RegisteredUser(Integer id, String email, String password, String firstName, String lastName, String phoneNumber, Boolean alertsSilenced, Timestamp lastPasswordResetDate, Collection<RegisteredUser> blockedUsers, LocalDateTime lastSuspensionDateTime, UserStatus status, Location location, Collection<Service> favoriteServices, Collection<Product> favoriteProducts, Collection<Event> favoriteEvents, String profilePicture, Collection<Event> registeredEvents, String authorities) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.alertsSilenced = alertsSilenced;
        this.lastPasswordResetDate = lastPasswordResetDate;
        this.blockedUsers = blockedUsers;
        this.lastSuspensionDateTime = lastSuspensionDateTime;
        this.status = status;
        this.location = location;
        this.favoriteServices = favoriteServices;
        this.favoriteProducts = favoriteProducts;
        this.favoriteEvents = favoriteEvents;
        this.profilePicture = profilePicture;
        this.registeredEvents = registeredEvents;
        this.authorities = authorities;
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

    public Boolean getAlertsSilenced() {
        return alertsSilenced;
    }

    public void setAlertsSilenced(Boolean alertsSilenced) {
        this.alertsSilenced = alertsSilenced;
    }

    public Timestamp getLastPasswordResetDate() {
        return lastPasswordResetDate;
    }

    public void setLastPasswordResetDate(Timestamp lastPasswordResetDate) {
        this.lastPasswordResetDate = lastPasswordResetDate;
    }

    public Collection<RegisteredUser> getBlockedUsers() {
        return blockedUsers;
    }

    public void setBlockedUsers(Collection<RegisteredUser> blockedUsers) {
        this.blockedUsers = blockedUsers;
    }

    public LocalDateTime getLastSuspensionDateTime() {
        return lastSuspensionDateTime;
    }

    public void setLastSuspensionDateTime(LocalDateTime lastSuspensionDateTime) {
        this.lastSuspensionDateTime = lastSuspensionDateTime;
    }

    public UserStatus getStatus() {
        return status;
    }

    public void setStatus(UserStatus status) {
        this.status = status;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Collection<Service> getFavoriteServices() {
        return favoriteServices;
    }

    public void setFavoriteServices(Collection<Service> favoriteServices) {
        this.favoriteServices = favoriteServices;
    }

    public Collection<Product> getFavoriteProducts() {
        return favoriteProducts;
    }

    public void setFavoriteProducts(Collection<Product> favoriteProducts) {
        this.favoriteProducts = favoriteProducts;
    }

    public Collection<Event> getFavoriteEvents() {
        return favoriteEvents;
    }

    public void setFavoriteEvents(Collection<Event> favoriteEvents) {
        this.favoriteEvents = favoriteEvents;
    }

    public String getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }

    public Collection<Event> getRegisteredEvents() {
        return registeredEvents;
    }

    public void setRegisteredEvents(Collection<Event> registeredEvents) {
        this.registeredEvents = registeredEvents;
    }

    public String getAuthorities() {
        return authorities;
    }

    public void setAuthorities(String authorities) {
        this.authorities = authorities;
    }

    @NonNull
    @Override
    public String toString() {
        return "RegisteredUser{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", alertsSilenced=" + alertsSilenced +
                ", lastPasswordResetDate=" + lastPasswordResetDate +
                ", blockedUsers=" + blockedUsers +
                ", lastSuspensionDateTime=" + lastSuspensionDateTime +
                ", status=" + status +
                ", location=" + location +
                ", favoriteServices=" + favoriteServices +
                ", favoriteProducts=" + favoriteProducts +
                ", favoriteEvents=" + favoriteEvents +
                ", profilePicture='" + profilePicture + '\'' +
                ", registeredEvents=" + registeredEvents +
                ", authorities='" + authorities + '\'' +
                '}';
    }
}
