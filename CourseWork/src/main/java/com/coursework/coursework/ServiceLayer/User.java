package com.coursework.coursework.ServiceLayer;

import com.coursework.coursework.Interfaces.ModelsInterfaces.UserInterface;
import jakarta.persistence.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.coursework.coursework.ServiceLayer.PasswordHashing.hashPassword;

@Entity
@Table(name = "users")
public class User implements UserInterface {

    @Id()
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(nullable = false, unique = true)
    private String login;

    @Column(nullable = false)
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

    public void setLogin(String login) {
        this.login = login;
    }

    public void setPassword(String password) {
        this.password = hashPassword(password);
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public void addTender(Tender tender) {
        this.tenders.put(tender.getId(), tender);
    }

    @Override
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

    public HashMap<UUID, Tender> getTenders() {
        return (HashMap<UUID, Tender>) tenders;
    }

    public HashMap<UUID, TenderProposal> getTenderProposals() {
        return (HashMap<UUID, TenderProposal>) tenderProposals;
    }

    public void setTenders(HashMap<UUID, Tender> tenders) {
        this.tenders = tenders;
    }

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
