package com.coursework.coursework.ServiceLayer;

import jakarta.persistence.*;

import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "tenderProposals")
public class TenderProposal {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "tender_id", nullable = false)
    private Tender tender;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "author_id", nullable = false)
    private User author;
    private String companyName;
    private String proposalDetails;
    private double price;
    private UUID chatId;

    public TenderProposal(Tender tender, String companyName, String proposalDetails, double price, User user) {
        this.id = UUID.randomUUID();
        this.companyName = companyName;
        this.tender = tender;
        this.proposalDetails = proposalDetails;
        this.price = price;
        this.author = user;
        this.chatId = null;
    }

    public TenderProposal() {

    }

    public UUID getId() {
        return id;
    }

    public Tender getTender() {
        return tender;
    }

    public String getCompanyName() {
        return companyName;
    }

    public String getProposalDetails() {
        return proposalDetails;
    }

    public double getPrice() {
        return price;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public void setTender(Tender tender) {
        this.tender = tender;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public UUID getChatId() {
        return chatId;
    }

    public void setChatId(UUID chatId) {
        this.chatId = chatId;
    }

    public void setProposalDetails(String proposalDetails) {
        this.proposalDetails = proposalDetails;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public User getAuthor() {
        return author;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TenderProposal that = (TenderProposal) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
