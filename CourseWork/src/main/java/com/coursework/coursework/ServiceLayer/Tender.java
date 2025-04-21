package com.coursework.coursework.ServiceLayer;

import com.coursework.coursework.Interfaces.ModelsInterfaces.TenderInterface;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Entity
@Table(name = "tenders")
@Access(AccessType.FIELD)
public class Tender implements TenderInterface, Comparable<Tender> {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private String name;
    private String description;
    private LocalDate deadline;
    private double cost;

    @Enumerated(EnumType.STRING)
    private Status status;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "author", nullable = false)
    private User author;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "winner_proposal_id")
    private TenderProposal winnerProposal;

    @OneToMany(mappedBy = "tender", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TenderProposal> tenderProposals = new ArrayList<>();

    @OneToMany(mappedBy = "tender", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<TenderReview> tenderReviews = new HashSet<>();

    public Tender() {}

    public Tender(String name, String description, LocalDate deadline, double cost, User author) {
        this.name = name;
        this.description = description;
        this.deadline = deadline;
        this.cost = cost;
        this.author = author;
        this.status = Status.ACTIVE;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void updateName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void updateDescription(String description) {
        this.description = description;
    }

    public LocalDate getDeadline() {
        return deadline;
    }

    public void updateDeadline(LocalDate deadline) {
        this.deadline = deadline;
    }

    public double getCost() {
        return cost;
    }

    public void updateCost(double cost) {
        this.cost = cost;
    }

    public Status getStatus() {
        return status;
    }

    public void updateStatus(Status status) {
        this.status = status;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public List<TenderProposal> getTenderProposals() {
        return tenderProposals;
    }

    public void setTenderProposals(List<TenderProposal> tenderProposals) {
        this.tenderProposals = tenderProposals;
    }

    public Set<TenderReview> getTenderReviews() {
        return tenderReviews;
    }

    public void setTenderReviews(Set<TenderReview> tenderReviews) {
        this.tenderReviews = tenderReviews;
    }

    public TenderProposal getWinnerProposal() {
        return winnerProposal;
    }

    public void setWinnerProposal(TenderProposal winnerProposal) {
        this.winnerProposal = winnerProposal;
    }

    @Override
    public void addTenderProposal(TenderProposal proposal) {
        if (!tenderProposals.contains(proposal)) {
            tenderProposals.add(proposal);
        }
    }

    @Override
    public void addTenderReview(TenderReview review) {
        tenderReviews.add(review);
        review.setTender(this);
    }

    @Override
    public TenderProposal findProposalById(UUID proposalId) {
        return tenderProposals.stream()
                .filter(p -> p.getId().equals(proposalId))
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<TenderProposal> sortedProposalsByPrice() {
        return tenderProposals.stream()
                .sorted(Comparator.comparingDouble(TenderProposal::getPrice))
                .collect(Collectors.toList());
    }

    @Override
    public void deleteProposalById(UUID proposalId, User user) {
        tenderProposals.removeIf(p -> p.getId().equals(proposalId));
        user.deleteUserProposal(new TenderProposal()); // Можливо, краще передавати реальний об’єкт
    }

    @Override
    public void deleteAllProposals() {
        tenderProposals.clear();
    }

    @Override
    public boolean isAfterDeadline() {
        return LocalDate.now().isAfter(this.deadline);
    }

    @Override
    public int compareTo(Tender o) {
        return this.name.compareTo(o.name);
    }
}

