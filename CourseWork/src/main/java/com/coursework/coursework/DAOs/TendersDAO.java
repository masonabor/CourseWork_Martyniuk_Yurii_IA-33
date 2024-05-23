package com.coursework.coursework.DAOs;

import com.coursework.coursework.ServiceLayer.Tender;
import com.coursework.coursework.ServiceLayer.User;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class TendersDAO {
    private Map<UUID, Tender> tendersDataBase = new HashMap<>();

    public HashMap<UUID, Tender> getAllTenders() {
        return (HashMap<UUID, Tender>) tendersDataBase;
    }

    public void setTendersDataBase(HashMap<UUID, Tender> tendersDataBase) {
        this.tendersDataBase = tendersDataBase;
    }

    public void createTender(String name, String description, LocalDate deadline, double cost) {
        Tender tender = new Tender(name, description, deadline, cost);
        tendersDataBase.put(tender.getId(), tender);
    }

    public void createTender(String name, String description, LocalDate deadline, double cost, User user) {
        Tender tender = new Tender(name, description, deadline, cost, user);
        tendersDataBase.put(tender.getId(), tender);
    }

    public void addTender(Tender tender) {
        tendersDataBase.put(tender.getId(), tender);
    }

    public Tender getTenderById(UUID id) {
        return tendersDataBase.get(id);
    }

    public boolean isTenderInDataBase(UUID id) {
        return tendersDataBase.containsKey(id);
    }

    public synchronized boolean deleteTenderById(UUID id) {
        if (isTenderInDataBase(id)) {
            tendersDataBase.remove(id);
            return true;
        } else return false;
    }

    public synchronized List<Tender> searchTenders(String keyword) {
        return tendersDataBase.values().stream()
                .filter(tender -> tender.getName().toLowerCase().contains(keyword.toLowerCase()))
                .collect(Collectors.toList());
    }
}
