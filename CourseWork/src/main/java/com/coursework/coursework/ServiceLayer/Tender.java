package com.coursework.coursework.ServiceLayer;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.UUID;

public class Tender {
    private UUID id;
    private String name;
    private String description;
    private LocalDate deadline;
    private double cost;
    private User author;

    private Status status;

    private ArrayList<TenderProposal> tenderProposals;
    private ArrayList<TenderReview> tenderReviews;

    public Tender(String name, String description, LocalDate deadline, double cost) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.description = description;
        this.deadline = deadline;
        this.tenderProposals = new ArrayList<>();
        this.tenderReviews = new ArrayList<>();
        this.cost = cost;
        this.status = Status.ACTIVE;
    }

    public Tender(String name, String description, LocalDate deadline, double cost, User user) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.description = description;
        this.deadline = deadline;
        this.tenderProposals = new ArrayList<>();
        this.tenderReviews = new ArrayList<>();
        this.cost = cost;
        this.author = user;
        this.status = Status.ACTIVE;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public UUID getAuthorId() {
        return author.getUserId();
    }

    public ArrayList<TenderReview> getTenderReviews() {
        return tenderReviews;
    }

    public void setTenderReviews(ArrayList<TenderReview> tenderReviews) {
        this.tenderReviews = tenderReviews;
    }

    public double getCost() {
        return cost;
    }

    public String getDescription() {
        return description;
    }

    public LocalDate getDeadline() {
        return deadline;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public User getAuthor() {
        return author;
    }

    public ArrayList<TenderProposal> getTenderProposals() {
        return tenderProposals;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public void updateName(String name) {
        this.name = name;
    }

    public void updateDescription(String description) {
        this.description = description;
    }

    public void updateDeadline(LocalDate deadline) {
        this.deadline = deadline;
    }

    public void setTenderProposals(ArrayList<TenderProposal> tenderProposals) {
        this.tenderProposals = tenderProposals;
    }

    public void updateCost(double cost) {
        this.cost = cost;
    }

    public Status getStatus() {
        return status;
    }

    public void updateStatus(Status status) {
        this.status = status;
    }

    public void addTenderProposal(TenderProposal proposal) {
        tenderProposals.add(proposal);
    }

    public void addTenderReview(TenderReview review) {
        tenderReviews.add(review);
    }

    public TenderProposal findProposalById(UUID proposalId) {
        for (TenderProposal proposal: tenderProposals) {
            if (proposal.getId() == proposalId) {
                return proposal;
            }
        }
        return null;
    }

    public void deleteProposalById(UUID proposalId) {
        tenderProposals.remove(findProposalById(proposalId));
    }

    public enum Status {
        ACTIVE, INACTIVE;
    }
}
