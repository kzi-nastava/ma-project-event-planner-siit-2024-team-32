package com.example.eventplanner.model;

import androidx.annotation.NonNull;

import com.example.eventplanner.model.enums.ReviewStatus;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ServiceReview {
    @SerializedName("id")
    @Expose
    private Integer id;

    @SerializedName("rating")
    @Expose
    private int rating;

    @SerializedName("comment")
    @Expose
    private String comment;

    @SerializedName("reviewStatus")
    @Expose
    private ReviewStatus reviewStatus;

    @SerializedName("service")
    @Expose
    private Service service;

    @SerializedName("reviewer")
    @Expose
    private RegisteredUser reviewer;

    public ServiceReview() {
    }

    public ServiceReview(Integer id, int rating, String comment, ReviewStatus reviewStatus, Service service, RegisteredUser reviewer) {
        this.id = id;
        this.rating = rating;
        this.comment = comment;
        this.reviewStatus = reviewStatus;
        this.service = service;
        this.reviewer = reviewer;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
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

    public Service getService() {
        return service;
    }

    public void setService(Service service) {
        this.service = service;
    }

    public RegisteredUser getReviewer() {
        return reviewer;
    }

    public void setReviewer(RegisteredUser reviewer) {
        this.reviewer = reviewer;
    }

    @NonNull
    @Override
    public String toString() {
        return "ServiceReview{" +
                "id=" + id +
                ", rating=" + rating +
                ", comment='" + comment + '\'' +
                ", reviewStatus=" + reviewStatus +
                ", service=" + service +
                ", reviewer=" + reviewer +
                '}';
    }
}
