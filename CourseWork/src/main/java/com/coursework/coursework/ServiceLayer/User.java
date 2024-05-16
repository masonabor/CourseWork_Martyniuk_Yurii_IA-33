package com.coursework.coursework.ServiceLayer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeSet;
import java.util.UUID;

public class User {

    private UUID id;
    private String login;
    private String password;
    private HashMap<UUID, Tender> tenders;
    private HashMap<UUID, TenderProposal> tenderProposals;
    private ROLE role;

    public User(String login, String password) {
        this.id = UUID.randomUUID();
        this.login = login;
        this.password = password;
        this.role = ROLE.USER;
        this.tenders = new HashMap<>();
        this.tenderProposals = new HashMap<>();
    }

    public User(String login, String password, ROLE role) {
        this.id = UUID.randomUUID();
        this.login = login;
        this.password = password;
        this.role = role;
        this.tenders = new HashMap<>();
        this.tenderProposals = new HashMap<>();
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRole(ROLE role) {
        this.role = role;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public void addTender(Tender tender) {
        this.tenders.put(tender.getId(), tender);
    }

    public void addTenderProposal(TenderProposal tenderProposal) {
        this.tenderProposals.put(tenderProposal.getId(), tenderProposal);
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public UUID getUserId() {
        return id;
    }

    public ROLE getRole() {
        return role;
    }

    public HashMap<UUID, Tender> getTenders() {
        return tenders;
    }

    public HashMap<UUID, TenderProposal> getTenderProposals() {
        return tenderProposals;
    }

    public void setTenders(HashMap<UUID, Tender> tenders) {
        this.tenders = tenders;
    }

    public void setTenderProposals(HashMap<UUID, TenderProposal> tenderProposals) {
        this.tenderProposals = tenderProposals;
    }

    public enum ROLE {
        ADMIN, USER
    }
}
