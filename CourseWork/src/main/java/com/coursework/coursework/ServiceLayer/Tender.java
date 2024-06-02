package com.coursework.coursework.ServiceLayer;

import com.coursework.coursework.Interfaces.ModelsInterfaces.TenderInterface;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.UUID;

public class Tender implements TenderInterface, Comparable<Tender> {
    private UUID id;
    private String name;
    private String description;
    private LocalDate deadline;
    private double cost;
    private User author;

    private Status status;

    private TenderProposal winnerProposal;

    private ArrayList<TenderProposal> tenderProposals;
    private ArrayList<TenderReview> tenderReviews;

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
        this.winnerProposal = null;
    }

    @Override
    public UUID getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public UUID getAuthorId() {
        return author.getUserId();
    }

    @Override
    public ArrayList<TenderReview> getTenderReviews() {
        return tenderReviews;
    }

    @Override
    public void setTenderReviews(ArrayList<TenderReview> tenderReviews) {
        this.tenderReviews = tenderReviews;
    }

    @Override
    public double getCost() {
        return cost;
    }

    @Override
    public TenderProposal getWinnerProposal() {
        return winnerProposal;
    }

    @Override
    public void setWinnerProposal(TenderProposal winnerProposal) {
        this.winnerProposal = winnerProposal;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public LocalDate getDeadline() {
        return deadline;
    }

    @Override
    public void setAuthor(User author) {
        this.author = author;
    }

    @Override
    public User getAuthor() {
        return author;
    }

    @Override
    public ArrayList<TenderProposal> getTenderProposals() {
        return tenderProposals;
    }

    @Override
    public void setId(UUID id) {
        this.id = id;
    }

    @Override
    public void updateName(String name) {
        this.name = name;
    }

    @Override
    public void updateDescription(String description) {
        this.description = description;
    }

    @Override
    public void updateDeadline(LocalDate deadline) {
        this.deadline = deadline;
    }

    @Override
    public void setTenderProposals(ArrayList<TenderProposal> tenderProposals) {
        this.tenderProposals = tenderProposals;
    }

    @Override
    public void updateCost(double cost) {
        this.cost = cost;
    }

    @Override
    public Status getStatus() {
        return status;
    }

    @Override
    public void updateStatus(TenderInterface.Status status) {
        this.status = status;
    }

    @Override
    public void addTenderProposal(TenderProposal proposal) {
        tenderProposals.add(proposal);
    }

    @Override
    public void addTenderReview(TenderReview review) {
        tenderReviews.add(review);
    }

    @Override
    public TenderProposal findProposalById(UUID proposalId) {
        for (TenderProposal proposal: tenderProposals) {
            if (proposal.getId().equals(proposalId)) {
                return proposal;
            }
        }
        return null;
    }

    @Override
    public ArrayList<TenderProposal> sortedProposalsByPrice() {
        ArrayList<TenderProposal> sortedProposals = tenderProposals;
        sortedProposals.sort(Comparator.comparing(TenderProposal::getPrice));
        return sortedProposals;
    }

    @Override
    public void deleteProposalById(UUID proposalId, User user) {
        tenderProposals.remove(findProposalById(proposalId));
        user.getTenderProposals().remove(proposalId);
    }

    @Override
    public void deleteAllProposals() {
        tenderProposals = null;
    }

    @Override
    public boolean isAfterDeadline() {
        return LocalDate.now().isAfter(deadline);
    }

    @Override
    public int compareTo(Tender other) {
        return this.name.compareTo(other.name);
    }
}
