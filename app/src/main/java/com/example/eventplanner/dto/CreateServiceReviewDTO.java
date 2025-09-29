package com.example.eventplanner.dto;

import androidx.annotation.NonNull;

import com.example.eventplanner.model.enums.ReviewStatus;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CreateServiceReviewDTO {
    @SerializedName("rating")
    @Expose
    private Integer rating;
    @SerializedName("comment")
    @Expose
    private String comment;
    @SerializedName("reviewStatus")
    @Expose
    private ReviewStatus reviewStatus;
    @SerializedName("serviceId")
    @Expose
    private Integer serviceId;
    @SerializedName("reviewerId")
    @Expose
    private Integer reviewerId;

    public CreateServiceReviewDTO() {
    }

    public CreateServiceReviewDTO(Integer rating, String comment, ReviewStatus reviewStatus, Integer serviceId, Integer reviewerId) {
        this.rating = rating;
        this.comment = comment;
        this.reviewStatus = reviewStatus;
        this.serviceId = serviceId;
        this.reviewerId = reviewerId;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public ReviewStatus getReviewStatus() {
        return reviewStatus;
    }

    public void setReviewStatus(ReviewStatus reviewStatus) {
        this.reviewStatus = reviewStatus;
    }

    public Integer getServiceId() {
        return serviceId;
    }

    public void setServiceId(Integer serviceId) {
        this.serviceId = serviceId;
    }

    public Integer getReviewerId() {
        return reviewerId;
    }

    public void setReviewerId(Integer reviewerId) {
        this.reviewerId = reviewerId;
    }

    @NonNull
    @Override
    public String toString() {
        return "CreateServiceReviewDTO{" +
                "rating=" + rating +
                ", comment='" + comment + '\'' +
                ", reviewStatus=" + reviewStatus +
                ", serviceId=" + serviceId +
                ", reviewerId=" + reviewerId +
                '}';
    }
}
