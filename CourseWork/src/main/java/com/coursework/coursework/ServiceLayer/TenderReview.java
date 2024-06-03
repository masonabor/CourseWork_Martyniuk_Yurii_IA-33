package com.coursework.coursework.ServiceLayer;

import java.util.UUID;

public class TenderReview {

    private UUID reviewId;
    private UUID tenderId;
    private UUID authorId;
    private String review;
    private String phoneNumber;
    private String companyName;

    public TenderReview(UUID tenderId, UUID authorId, String review, String phoneNumber, String companyName) {
        this.reviewId = UUID.randomUUID();
        this.tenderId = tenderId;
        this.authorId = authorId;
        this.review = review;
        this.phoneNumber = "+380" + phoneNumber;
        this.companyName = companyName;
    }

    public UUID getReviewId() {
        return reviewId;
    }

    public UUID getTenderId() {
        return tenderId;
    }

    public UUID getAuthorId() {
        return authorId;
    }

    public String getReview() {
        return review;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setReviewId(UUID reviewId) {
        this.reviewId = reviewId;
    }

    public void setTenderId(UUID tenderId) {
        this.tenderId = tenderId;
    }

    public void setAuthorId(UUID authorId) {
        this.authorId = authorId;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = "+380" + phoneNumber;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
}
