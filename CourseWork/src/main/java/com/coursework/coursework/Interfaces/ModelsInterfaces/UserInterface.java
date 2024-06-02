package com.coursework.coursework.Interfaces.ModelsInterfaces;

import com.coursework.coursework.ServiceLayer.Tender;
import com.coursework.coursework.ServiceLayer.TenderProposal;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public interface UserInterface {
    void setLogin(String login);
    void setPassword(String password);
    void setId(UUID id);
    void addTender(Tender tender);
    void addTenderProposal(TenderProposal tenderProposal);
    String getLogin();
    String getPassword();
    UUID getUserId();
    HashMap<UUID, Tender> getTenders();
    HashMap<UUID, TenderProposal> getTenderProposals();
    void setTenders(HashMap<UUID, Tender> tenders);
    void setTenderProposals(HashMap<UUID, TenderProposal> tenderProposals);
    List<Tender> searchUserTenders(String keyword);
    void deleteUserTender(Tender tender);
    void deleteUserProposal(TenderProposal proposal);
}
