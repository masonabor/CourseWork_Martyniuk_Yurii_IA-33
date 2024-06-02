package com.coursework.coursework.Listeners;

import com.coursework.coursework.DAOs.TendersDAO;
import com.coursework.coursework.ServiceLayer.Tender;
import com.coursework.coursework.ServiceLayer.TenderProposal;
import com.coursework.coursework.ServiceLayer.User;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

import java.time.LocalDate;

@WebListener
public class TendersListener implements ServletContextListener {

    private TendersDAO tendersDataBase;

    @Override
    public void contextInitialized(ServletContextEvent context) {

        tendersDataBase = new TendersDAO();

        User user1 = new User("Brigadir", "12345677");
        User user2 = new User("user", "12345678");
        Tender tender = new Tender("Виготовлення 250 столів", "Потрібно виготовити 250 столів та стільців з деревини", LocalDate.of(2024, 8, 17), 10000000, user1);
        user1.addTender(tender);
        tender.addTenderProposal(new TenderProposal(tender.getId(), "MMM", "230 стільців до дедлайну", 1000.0, user2));
        tendersDataBase.addTender(tender);

        context.getServletContext().setAttribute("tendersDataBase", tendersDataBase);
        System.out.println("contextTenderInitialized");
    }

    @Override
    public void contextDestroyed(ServletContextEvent context) {
        tendersDataBase = null;

    }
}