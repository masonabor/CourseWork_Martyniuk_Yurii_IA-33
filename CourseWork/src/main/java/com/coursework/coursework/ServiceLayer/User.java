package com.coursework.coursework.ServiceLayer;

import com.coursework.coursework.Interfaces.ModelsInterfaces.UserInterface;
import jakarta.persistence.*;
import jakarta.transaction.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Entity
@Table(name = "users")
public class User implements UserInterface {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(nullable = false, unique = true)
    private String login;

    @Column(nullable = false)
    private String password;

    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Tender> tenders = new HashSet<>();

    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<TenderProposal> tenderProposals = new HashSet<>();

    public User() {}

    public User(String login, String password) {
        this.login = login;
        this.password = PasswordHashing.hashPassword(password);
    }

    public UUID getUserId() {
        return id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = PasswordHashing.hashPassword(password);
    }

    public Set<Tender> getTenders() {
        return tenders;
    }

    public void setTenders(Set<Tender> tenders) {
        this.tenders = tenders;
    }

    public Set<TenderProposal> getTenderProposals() {
        return tenderProposals;
    }

    public void setTenderProposals(Set<TenderProposal> tenderProposals) {
        this.tenderProposals = tenderProposals;
    }

    @Override
    public void addTender(Tender tender) {
        this.tenders.add(tender);
        tender.setAuthor(this);
    }

    @Override
    public void addTenderProposal(TenderProposal tenderProposal) {
        this.tenderProposals.add(tenderProposal);
        tenderProposal.setAuthor(this);
    }

    @Override
    public List<Tender> searchUserTenders(String keyword) {
        return tenders.stream()
                .filter(t -> t.getName().toLowerCase().contains(keyword.toLowerCase()))
                .collect(Collectors.toList());
    }

    @Override
    public void deleteUserTender(Tender tender) {
        tenders.remove(tender);
    }

    @Override
    public void deleteUserProposal(TenderProposal proposal) {
        tenderProposals.remove(proposal);
    }
}
