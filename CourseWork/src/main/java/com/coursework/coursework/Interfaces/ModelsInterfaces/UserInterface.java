package com.coursework.coursework.Interfaces.ModelsInterfaces;

import com.coursework.coursework.ServiceLayer.Tender;
import com.coursework.coursework.ServiceLayer.TenderProposal;
import java.util.List;

public interface UserInterface {
    void addTender(Tender tender);
    void addTenderProposal(TenderProposal tenderProposal);
    List<Tender> searchUserTenders(String keyword);
    void deleteUserTender(Tender tender);
    void deleteUserProposal(TenderProposal proposal);
}
