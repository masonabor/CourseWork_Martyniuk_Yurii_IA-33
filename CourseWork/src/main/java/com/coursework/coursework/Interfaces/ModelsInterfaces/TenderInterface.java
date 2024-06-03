package com.coursework.coursework.Interfaces.ModelsInterfaces;

import com.coursework.coursework.ServiceLayer.TenderProposal;
import com.coursework.coursework.ServiceLayer.TenderReview;
import com.coursework.coursework.ServiceLayer.User;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.UUID;

public interface TenderInterface {
    void updateName(String name);
    void updateDescription(String description);
    void updateDeadline(LocalDate deadline);
    void updateCost(double cost);
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
