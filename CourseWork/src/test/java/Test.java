import com.coursework.coursework.ServiceLayer.Tender;
import com.coursework.coursework.ServiceLayer.TenderProposal;
import com.coursework.coursework.ServiceLayer.User;

import java.time.LocalDate;

public class Test {
    public static void main(String[] args) {
        User user = new User("user", "12345678");
        User user1 = new User("user1", "12345678");
        Tender tender = new Tender("1", "opis", LocalDate.of(2024, 6, 3), 3000.0, user1);
        user1.addTender(tender);
        TenderProposal proposal = new TenderProposal(tender.getId(), "123", "12345", 1000.0, user);
        user.addTenderProposal(proposal);
        tender.addTenderProposal(proposal);

        TenderProposal proposalGettedById = tender.findProposalById(proposal.getId());

        System.out.println(proposalGettedById);
        System.out.println(tender.getTenderProposals());
        System.out.println(user.getTenderProposals());
    }
}
