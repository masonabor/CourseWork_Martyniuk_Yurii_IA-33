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

    public Tender(String name, String description, LocalDate deadline, double cost) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.description = description;
        this.deadline = deadline;
        this.tenderProposals = new ArrayList<>();
        this.cost = cost;
        this.status = Status.ACTIVE;
    }

    public Tender(String name, String description, LocalDate deadline, double cost, User user) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.description = description;
        this.deadline = deadline;
        this.tenderProposals = new ArrayList<>();
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

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDeadline(LocalDate deadline) {
        this.deadline = deadline;
    }

    public void setTenderProposals(ArrayList<TenderProposal> tenderProposals) {
        this.tenderProposals = tenderProposals;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public void addTenderProposal(TenderProposal proposal) {
        tenderProposals.add(proposal);
    }


    public enum Status {
        ACTIVE, INACTIVE;
    }
}
