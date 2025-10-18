package com.example.eventplanner.model;

import androidx.annotation.NonNull;

import com.example.eventplanner.model.enums.UserStatus;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ServiceAndProductProvider extends RegisteredUser {
    @SerializedName("id")
    @Expose
    private String companyName;
    @SerializedName("id")
    @Expose
    private String description;
    @SerializedName("id")
    @Expose
    private List<String> companyPictures;
    @SerializedName("id")
    @Expose
    private Collection<EventType> offeredEventTypes=new ArrayList<EventType>();

    public ServiceAndProductProvider() {
        super();
    }

    public ServiceAndProductProvider(Integer id, String email, String password, String firstName, String lastName, String phoneNumber, Boolean alertsSilenced, Timestamp lastPasswordResetDate, Collection<RegisteredUser> blockedUsers, LocalDateTime lastSuspensionDateTime, UserStatus status, Location location, Collection<Service> favoriteServices, Collection<Product> favoriteProducts, Collection<Event> favoriteEvents, String profilePicture, Collection<Event> registeredEvents, String authorities, String companyName, String description, List<String> companyPictures, Collection<EventType> offeredEventTypes) {
        super(id, email, password, firstName, lastName, phoneNumber, alertsSilenced, lastPasswordResetDate, blockedUsers, lastSuspensionDateTime, status, location, favoriteServices, favoriteProducts, favoriteEvents, profilePicture, registeredEvents, authorities);
        this.companyName = companyName;
        this.description = description;
        this.companyPictures = companyPictures;
        this.offeredEventTypes = offeredEventTypes;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> getCompanyPictures() {
        return companyPictures;
    }

    public void setCompanyPictures(List<String> companyPictures) {
        this.companyPictures = companyPictures;
    }

    public Collection<EventType> getOfferedEventTypes() {
        return offeredEventTypes;
    }

    public void setOfferedEventTypes(Collection<EventType> offeredEventTypes) {
        this.offeredEventTypes = offeredEventTypes;
    }

    @NonNull
    @Override
    public String toString() {
        return super.toString() +
                "ServiceAndProductProvider{" +
                "companyName='" + companyName + '\'' +
                ", description='" + description + '\'' +
                ", companyPictures=" + companyPictures +
                ", offeredEventTypes=" + offeredEventTypes +
                "}";
    }
}

