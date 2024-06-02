package com.coursework.coursework.ServiceLayer;

import com.coursework.coursework.Interfaces.ModelsInterfaces.TenderReviewInterface;

import java.util.UUID;

public class TenderReview implements TenderReviewInterface {

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

    @Override
    public UUID getReviewId() {
        return reviewId;
    }

    @Override
    public UUID getTenderId() {
        return tenderId;
    }

    @Override
    public UUID getAuthorId() {
        return authorId;
    }

    @Override
    public String getReview() {
        return review;
    }

    @Override
    public String getPhoneNumber() {
        return phoneNumber;
    }

    @Override
    public String getCompanyName() {
        return companyName;
    }

    @Override
    public void setReviewId(UUID reviewId) {
        this.reviewId = reviewId;
    }

    @Override
    public void setTenderId(UUID tenderId) {
        this.tenderId = tenderId;
    }

    @Override
    public void setAuthorId(UUID authorId) {
        this.authorId = authorId;
    }

    @Override
    public void setReview(String review) {
        this.review = review;
    }

    @Override
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = "+380" + phoneNumber;
    }

    @Override
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
}
