package com.coursework.coursework.Interfaces.DAOsInterfaces;

import com.coursework.coursework.ServiceLayer.Tender;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public interface TendersDAOInterface {
    HashMap<UUID, Tender> getAllTenders();
    void setTendersDataBase(HashMap<UUID, Tender> tendersDataBase);
    void addTender(Tender tender);
    Tender getTenderById(UUID id);
    boolean isTenderInDataBase(UUID id);
    boolean deleteTender(Tender tender);
    List<Tender> searchTenders(String keyword);
}
