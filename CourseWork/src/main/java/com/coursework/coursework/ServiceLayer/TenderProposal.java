package com.coursework.coursework.ServiceLayer;

import java.util.UUID;

public class TenderProposal {
    private UUID id;
    private UUID tenderId;
    private User author;
    private String companyName;
    private String proposalDetails;
    private double price;
    private UUID chatId;

    public TenderProposal(UUID tenderId, String companyName, String proposalDetails, double price, User user) {
        this.id = UUID.randomUUID();
        this.tenderId = tenderId;
        this.companyName = companyName;
        this.proposalDetails = proposalDetails;
        this.price = price;
        this.author = user;
        this.chatId = null;
    }

    public UUID getId() {
        return id;
    }

    public UUID getTenderId() {
        return tenderId;
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

    public void setTenderId(UUID tenderId) {
        this.tenderId = tenderId;
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
}
