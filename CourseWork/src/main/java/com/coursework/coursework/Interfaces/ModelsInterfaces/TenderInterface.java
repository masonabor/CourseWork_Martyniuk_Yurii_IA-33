package com.coursework.coursework.Interfaces.ModelsInterfaces;

import com.coursework.coursework.ServiceLayer.Tender;
import com.coursework.coursework.ServiceLayer.TenderProposal;
import com.coursework.coursework.ServiceLayer.TenderReview;
import com.coursework.coursework.ServiceLayer.User;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.UUID;

public interface TenderInterface {
    UUID getId();
    String getName();
    UUID getAuthorId();
    ArrayList<TenderReview> getTenderReviews();
    void setTenderReviews(ArrayList<TenderReview> tenderReviews);
    double getCost();
    TenderProposal getWinnerProposal();
    void setWinnerProposal(TenderProposal winnerProposal);
    String getDescription();
    LocalDate getDeadline();
    void setAuthor(User author);
    User getAuthor();
    ArrayList<TenderProposal> getTenderProposals();
    void setId(UUID id);
    void updateName(String name);
    void updateDescription(String description);
    void updateDeadline(LocalDate deadline);
    void setTenderProposals(ArrayList<TenderProposal> tenderProposals);
    void updateCost(double cost);
    Tender.Status getStatus();
    void updateStatus(Status status);
    void addTenderProposal(TenderProposal proposal);
    void addTenderReview(TenderReview review);
    TenderProposal findProposalById(UUID proposalId);
    ArrayList<TenderProposal> sortedProposalsByPrice();
    void deleteProposalById(UUID proposalId, User user);
    void deleteAllProposals();
    boolean isAfterDeadline();

    enum Status {
        ACTIVE, INACTIVE
    }
}
