package com.example.eventplanner.model;

import androidx.annotation.NonNull;

import com.example.eventplanner.model.enums.AppointmentType;
import com.example.eventplanner.model.enums.ServiceStatus;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Service {
    @SerializedName("id")
    @Expose
    private Integer id;

    @SerializedName("serviceAndProductCategory")
    @Expose
    private ServiceAndProductCategory serviceAndProductCategory;

    @SerializedName("price")
    @Expose
    private float price;

    @SerializedName("serviceAndProductProvider")
    @Expose
    private ServiceAndProductProvider serviceAndProductProvider;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("description")
    @Expose
    private String description;

    @SerializedName("specifics")
    @Expose
    private String specifics;

    @SerializedName("discount")
    @Expose
    private float discount;

    @SerializedName("serviceStatus")
    @Expose
    private ServiceStatus serviceStatus;

    @SerializedName("appointmentType")
    @Expose
    private AppointmentType appointmentType;

    @SerializedName("appointmentDurationMinutes")
    @Expose
    private int appointmentDurationMinutes;

    @SerializedName("minimumAppointmentDurationMinutes")
    @Expose
    private int minimumAppointmentDurationMinutes;

    @SerializedName("maximumAppointmentDurationMinutes")
    @Expose
    private int maximumAppointmentDurationMinutes;

    @SerializedName("reservationDeadLineHours")
    @Expose
    private int reservationDeadLineHours;

    @SerializedName("cancelationDeadlineHours")
    @Expose
    private int cancelationDeadlineHours;

    public Service() {
    }

    public Service(Integer id, ServiceAndProductCategory serviceAndProductCategory, float price, ServiceAndProductProvider serviceAndProductProvider, String name, String description, String specifics, float discount, ServiceStatus serviceStatus, AppointmentType appointmentType, int appointmentDurationMinutes, int minimumAppointmentDurationMinutes, int maximumAppointmentDurationMinutes, int reservationDeadLineHours, int cancelationDeadlineHours) {
        this.id = id;
        this.serviceAndProductCategory = serviceAndProductCategory;
        this.price = price;
        this.serviceAndProductProvider = serviceAndProductProvider;
        this.name = name;
        this.description = description;
        this.specifics = specifics;
        this.discount = discount;
        this.serviceStatus = serviceStatus;
        this.appointmentType = appointmentType;
        this.appointmentDurationMinutes = appointmentDurationMinutes;
        this.minimumAppointmentDurationMinutes = minimumAppointmentDurationMinutes;
        this.maximumAppointmentDurationMinutes = maximumAppointmentDurationMinutes;
        this.reservationDeadLineHours = reservationDeadLineHours;
        this.cancelationDeadlineHours = cancelationDeadlineHours;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public ServiceAndProductCategory getServiceAndProductCategory() {
        return serviceAndProductCategory;
    }

    public void setServiceAndProductCategory(ServiceAndProductCategory serviceAndProductCategory) {
        this.serviceAndProductCategory = serviceAndProductCategory;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public ServiceAndProductProvider getServiceAndProductProvider() {
        return serviceAndProductProvider;
    }

    public void setServiceAndProductProvider(ServiceAndProductProvider serviceAndProductProvider) {
        this.serviceAndProductProvider = serviceAndProductProvider;
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

    public String getSpecifics() {
        return specifics;
    }

    public void setSpecifics(String specifics) {
        this.specifics = specifics;
    }

    public float getDiscount() {
        return discount;
    }

    public void setDiscount(float discount) {
        this.discount = discount;
    }

    public ServiceStatus getServiceStatus() {
        return serviceStatus;
    }

    public void setServiceStatus(ServiceStatus serviceStatus) {
        this.serviceStatus = serviceStatus;
    }

    public AppointmentType getAppointmentType() {
        return appointmentType;
    }

    public void setAppointmentType(AppointmentType appointmentType) {
        this.appointmentType = appointmentType;
    }

    public int getAppointmentDurationMinutes() {
        return appointmentDurationMinutes;
    }

    public void setAppointmentDurationMinutes(int appointmentDurationMinutes) {
        this.appointmentDurationMinutes = appointmentDurationMinutes;
    }

    public int getMinimumAppointmentDurationMinutes() {
        return minimumAppointmentDurationMinutes;
    }

    public void setMinimumAppointmentDurationMinutes(int minimumAppointmentDurationMinutes) {
        this.minimumAppointmentDurationMinutes = minimumAppointmentDurationMinutes;
    }

    public int getMaximumAppointmentDurationMinutes() {
        return maximumAppointmentDurationMinutes;
    }

    public void setMaximumAppointmentDurationMinutes(int maximumAppointmentDurationMinutes) {
        this.maximumAppointmentDurationMinutes = maximumAppointmentDurationMinutes;
    }

    public int getReservationDeadLineHours() {
        return reservationDeadLineHours;
    }

    public void setReservationDeadLineHours(int reservationDeadLineHours) {
        this.reservationDeadLineHours = reservationDeadLineHours;
    }

    public int getCancelationDeadlineHours() {
        return cancelationDeadlineHours;
    }

    public void setCancelationDeadlineHours(int cancelationDeadlineHours) {
        this.cancelationDeadlineHours = cancelationDeadlineHours;
    }

    @NonNull
    @Override
    public String toString() {
        return "Service{" +
                "id=" + id +
                ", serviceAndProductCategory=" + serviceAndProductCategory +
                ", price=" + price +
                ", serviceAndProductProvider=" + serviceAndProductProvider +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", specifics='" + specifics + '\'' +
                ", discount=" + discount +
                ", serviceStatus=" + serviceStatus +
                ", appointmentType=" + appointmentType +
                ", appointmentDurationMinutes=" + appointmentDurationMinutes +
                ", minimumAppointmentDurationMinutes=" + minimumAppointmentDurationMinutes +
                ", maximumAppointmentDurationMinutes=" + maximumAppointmentDurationMinutes +
                ", reservationDeadLineHours=" + reservationDeadLineHours +
                ", cancelationDeadlineHours=" + cancelationDeadlineHours +
                '}';
    }
}
