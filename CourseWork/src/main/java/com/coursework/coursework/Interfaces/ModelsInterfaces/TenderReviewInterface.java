package com.coursework.coursework.Interfaces.ModelsInterfaces;

import java.util.UUID;

public interface TenderReviewInterface {
    UUID getReviewId();
    UUID getTenderId();
    UUID getAuthorId();
    String getReview();
    String getPhoneNumber();
    String getCompanyName();

    void setReviewId(UUID reviewId);
    void setTenderId(UUID tenderId);
    void setAuthorId(UUID authorId);
    void setReview(String review);
    void setPhoneNumber(String phoneNumber);
    void setCompanyName(String companyName);
}
