package com.coursework.coursework.DAOs;

import com.coursework.coursework.Interfaces.DAOsInterfaces.TendersDAOInterface;
import com.coursework.coursework.ServiceLayer.Tender;
import com.coursework.coursework.ServiceLayer.User;

import java.util.*;
import java.util.stream.Collectors;

public class TendersDAO implements TendersDAOInterface {
    private Map<UUID, Tender> tendersDataBase = new LinkedHashMap<>(15, 0.65f);

    @Override
    public LinkedHashMap<UUID, Tender> getAllTenders() {
        return tendersDataBase.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue(Comparator.comparing(Tender::getName)))
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (e1, e2) -> e1,
                        LinkedHashMap::new));
    }

    @Override
    public void setTendersDataBase(HashMap<UUID, Tender> tendersDataBase) {
        this.tendersDataBase = tendersDataBase;
    }

    @Override
    public void addTender(Tender tender) {
        tendersDataBase.put(tender.getId(), tender);
    }

    @Override
    public Tender getTenderById(UUID id) {
        return tendersDataBase.get(id);
    }

    @Override
    public boolean isTenderInDataBase(UUID id) {
        return tendersDataBase.containsKey(id);
    }

    @Override
    public boolean deleteTender(Tender tender) {
        User user = tender.getAuthor();
        if (isTenderInDataBase(tender.getId())) {
            tendersDataBase.remove(tender.getId());
            user.deleteUserTender(tender);
            tender.deleteAllProposals();
            return true;
        } else return false;
    }

    @Override
    public synchronized List<Tender> searchTenders(String keyword) {
        return tendersDataBase.values().stream()
                .filter(tender -> tender.getStatus() == Tender.Status.ACTIVE)
                .filter(tender -> tender.getName().toLowerCase().contains(keyword.toLowerCase()))
                .collect(Collectors.toList());
    }
}
