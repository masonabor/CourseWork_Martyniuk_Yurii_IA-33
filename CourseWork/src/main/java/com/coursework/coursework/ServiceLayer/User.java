package com.coursework.coursework.ServiceLayer;

import com.coursework.coursework.Interfaces.ModelsInterfaces.UserInterface;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.coursework.coursework.ServiceLayer.PasswordHashing.hashPassword;

public class User implements UserInterface {

    private UUID id;
    private String login;
    private String password;
    private Map<UUID, Tender> tenders;
    private Map<UUID, TenderProposal> tenderProposals;

    public User(String login, String password) {
        this.id = UUID.randomUUID();
        this.login = login;
        this.password = hashPassword(password);
        this.tenders = new HashMap<>();
        this.tenderProposals = new HashMap<>();
    }

    @Override
    public void setLogin(String login) {
        this.login = login;
    }

    @Override
    public void setPassword(String password) {
        this.password = hashPassword(password);
    }

    @Override
    public void setId(UUID id) {
        this.id = id;
    }

    @Override
    public void addTender(Tender tender) {
        this.tenders.put(tender.getId(), tender);
    }

    @Override
    public void addTenderProposal(TenderProposal tenderProposal) {
        this.tenderProposals.put(tenderProposal.getId(), tenderProposal);
    }

    @Override
    public String getLogin() {
        return login;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public UUID getUserId() {
        return id;
    }

    @Override
    public HashMap<UUID, Tender> getTenders() {
        return (HashMap<UUID, Tender>) tenders;
    }

    @Override
    public HashMap<UUID, TenderProposal> getTenderProposals() {
        return (HashMap<UUID, TenderProposal>) tenderProposals;
    }

    @Override
    public void setTenders(HashMap<UUID, Tender> tenders) {
        this.tenders = tenders;
    }

    @Override
    public void setTenderProposals(HashMap<UUID, TenderProposal> tenderProposals) {
        this.tenderProposals = tenderProposals;
    }

    @Override
    public List<Tender> searchUserTenders(String keyword) {
        return tenders.values().stream()
                .filter(tender -> tender.getName().toLowerCase().contains(keyword.toLowerCase()))
                .collect(Collectors.toList());
    }

    @Override
    public void deleteUserTender(Tender tender) {
            tenders.remove(tender.getId());
    }

    @Override
    public void deleteUserProposal(TenderProposal proposal) {
        tenderProposals.remove(proposal.getId());
    }
}
