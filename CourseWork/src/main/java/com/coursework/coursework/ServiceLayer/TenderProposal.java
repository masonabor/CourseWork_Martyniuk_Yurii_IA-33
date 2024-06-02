package com.coursework.coursework.ServiceLayer;

import com.coursework.coursework.Interfaces.ModelsInterfaces.TenderProposalInterface;

import java.util.UUID;

public class TenderProposal implements TenderProposalInterface {
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

    @Override
    public UUID getId() {
        return id;
    }

    @Override
    public UUID getTenderId() {
        return tenderId;
    }

    @Override
    public String getCompanyName() {
        return companyName;
    }

    @Override
    public String getProposalDetails() {
        return proposalDetails;
    }

    @Override
    public double getPrice() {
        return price;
    }

    @Override
    public void setId(UUID id) {
        this.id = id;
    }

    @Override
    public void setTenderId(UUID tenderId) {
        this.tenderId = tenderId;
    }

    @Override
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    @Override
    public UUID getChatId() {
        return chatId;
    }

    @Override
    public void setChatId(UUID chatId) {
        this.chatId = chatId;
    }

    @Override
    public void setProposalDetails(String proposalDetails) {
        this.proposalDetails = proposalDetails;
    }

    @Override
    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public void setAuthor(User author) {
        this.author = author;
    }

    @Override
    public User getAuthor() {
        return author;
    }
}
