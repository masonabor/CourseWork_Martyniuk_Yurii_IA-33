package com.coursework.coursework.ServiceLayer;

import jakarta.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "tender_reviews")
public class TenderReview {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "review_id", updatable = false, nullable = false)
    private UUID reviewId;

    // зв’язок до тендера
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "tender_id", nullable = false)
    private Tender tender;

    // зв’язок до автора (User)
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "author_id", nullable = false)
    private User author;

    @Column(name = "review", columnDefinition = "TEXT", nullable = false)
    private String review;

    @Column(name = "phone_number", nullable = false)
    private String phoneNumber;

    @Column(name = "company_name", nullable = false)
    private String companyName;

    protected TenderReview() { /* для JPA */ }

    public TenderReview(Tender tender, User author, String review,
                        String phoneNumber, String companyName) {
        this.tender = tender;
        this.author = author;
        this.review = review;
        this.phoneNumber = "+380" + phoneNumber;
        this.companyName = companyName;
    }

    // === Геттери/Сеттери ===

    public UUID getReviewId() { return reviewId; }

    public Tender getTender() { return tender; }
    public void setTender(Tender tender) { this.tender = tender; }

    public User getAuthor() { return author; }
    public void setAuthor(User author) { this.author = author; }

    public String getReview() { return review; }
    public void setReview(String review) { this.review = review; }

    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = "+380" + phoneNumber;
    }

    public String getCompanyName() { return companyName; }
    public void setCompanyName(String companyName) { this.companyName = companyName; }
}
