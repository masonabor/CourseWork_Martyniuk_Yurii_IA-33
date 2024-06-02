package com.coursework.coursework.Interfaces.ModelsInterfaces;

import com.coursework.coursework.ServiceLayer.User;

import java.util.UUID;

public interface TenderProposalInterface {
    UUID getId();
    UUID getTenderId();
    String getCompanyName();
    String getProposalDetails();
    double getPrice();
    User getAuthor();
    void setId(UUID id);
    void setTenderId(UUID tenderId);
    void setCompanyName(String companyName);
    void setProposalDetails(String proposalDetails);
    void setPrice(double price);
    void setAuthor(User author);
    void setChatId(UUID chatId);
    UUID getChatId();
}
