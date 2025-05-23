package com.coursework.coursework.DAOs;

import com.coursework.coursework.Interfaces.DAOsInterfaces.TendersDAOInterface;
import com.coursework.coursework.ServiceLayer.Tender;
import com.coursework.coursework.ServiceLayer.TenderProposal;
import com.coursework.coursework.ServiceLayer.TenderReview;
import com.coursework.coursework.ServiceLayer.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.*;
import java.util.stream.Stream;

public class TendersDAO implements TendersDAOInterface {
    private final SessionFactory sessionFactory;

    public TendersDAO(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public boolean isTenderNameInDataBase(String name) {
        try (Session session = sessionFactory.openSession()) {
            Query<Long> query = session.createQuery(
                    "SELECT COUNT(t) FROM Tender t WHERE lower(t.name) = :name", Long.class);
            query.setParameter("name", name.toLowerCase());
            return query.uniqueResult() > 0;
        }
    }

    @Override
    public void addTender(Tender tender) {
        try (Session session = sessionFactory.openSession()) {
            Transaction tx = session.beginTransaction();
            User user = session.get(User.class, tender.getAuthor().getUserId());
            user.addTender(tender);
            session.persist(tender);
            tx.commit();
        }
    }

    @Override
    public Tender getTenderById(UUID id) {
        try (Session session = sessionFactory.openSession()) {
            Tender tender = session.createQuery(
                            "SELECT t FROM Tender t " +
                                    "LEFT JOIN FETCH t.author " +
                                    "LEFT JOIN FETCH t.tenderProposals tp " +
                                    "LEFT JOIN FETCH tp.author " +
                                    "WHERE t.id = :id",
                            Tender.class)
                    .setParameter("id", id)
                    .uniqueResult();

            if (tender != null) {
                tender.setTenderReviews(
                        new HashSet<>(
                                session.createQuery(
                                                "SELECT tr FROM TenderReview tr WHERE tr.tender.id = :tenderId",
                                                TenderReview.class)
                                        .setParameter("tenderId", tender.getId())
                                        .getResultList()
                        )
                );
            }

            return tender;
        }
    }



    public void updateTender(Tender tender) {
        try (Session session = sessionFactory.openSession()) {
            Transaction tx = session.beginTransaction();

            User managedUser = session.get(User.class, tender.getAuthor().getUserId());

            managedUser.getTenders().removeIf(t -> t.getId().equals(tender.getId()));

            managedUser.getTenders().add(tender);
            tender.setAuthor(managedUser);

            session.merge(tender);
            session.merge(managedUser);

            tx.commit();
        }
    }

    public synchronized void updateProposal(Tender tender, TenderProposal proposal) {
        try (Session session = sessionFactory.openSession()) {
            Transaction tx = session.beginTransaction();

            Tender managedTender = session.get(Tender.class, tender.getId());
            User managedUser = session.get(User.class, proposal.getAuthor().getUserId());

            managedTender.addTenderProposal(proposal);
            managedUser.addTenderProposal(proposal);

            session.merge(proposal);
            session.merge(managedUser);

            tx.commit();
        }
    }

    public synchronized void createProposal(TenderProposal proposal) {
        try (Session session = sessionFactory.openSession()) {
            Transaction tx = session.beginTransaction();
            session.persist(proposal);
            tx.commit();
        }
    }

    @Override
    public boolean isTenderInDataBase(UUID id) {
        return getTenderById(id) != null;
    }

    @Override
    public boolean deleteTender(Tender tender) {
        try (Session session = sessionFactory.openSession()) {
            Transaction tx = session.beginTransaction();
            Tender existing = session.get(Tender.class, tender.getId());
            if (existing != null) {
                User user = existing.getAuthor();
                user.deleteUserTender(existing);
                session.remove(existing);
                tx.commit();
                return true;
            }
        }
        return false;
    }

    @Override
    public synchronized List<Tender> searchTenders(String keyword) {
        try (Session session = sessionFactory.openSession()) {
            Query<Tender> query = session.createQuery(
                    "FROM Tender t WHERE lower(t.name) LIKE :keyword AND t.status = 'ACTIVE'",
                    Tender.class);
            query.setParameter("keyword", "%" + keyword.toLowerCase() + "%");
            return query.list();
        }
    }

    public synchronized Map<UUID, Tender> getAllTenders() {
        try (Session session = sessionFactory.openSession()) {
            Query<Tender> query = session.createQuery(
                    "FROM Tender", Tender.class);
            List<Tender> tenders = query.list();
            Map<UUID, Tender> map = new HashMap<>();

            for (Tender tender : tenders) {
                map.put(tender.getId(), tender);
            }
            return map;
        }
    }

    public synchronized void addReview(TenderReview review) {
        try (Session session = sessionFactory.openSession()) {
            Transaction tx = session.beginTransaction();
            Tender tender = session.get(Tender.class, review.getTender().getId());
            tender.addTenderReview(review);
            session.persist(review);
            session.merge(tender);

            tx.commit();
        }
    }

    public synchronized void deleteProposal(TenderProposal proposal) {
        try (Session session = sessionFactory.openSession()) {
            Transaction tx = session.beginTransaction();
            session.remove(proposal);
            tx.commit();
        }
    }
}