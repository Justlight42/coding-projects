package custom.dao;

import custom.model.Subscription;

import java.time.LocalDate;
import java.util.List;

public interface SubscriptionDao {

    Subscription getSubById(int subId);

    List<Subscription> getAllSubsByUserId(int userId);

    List<Subscription> getAllUpcomingBilling(LocalDate billDate);

    Subscription createSub(Subscription newSub);

    Subscription updateBillingDate(int subId, LocalDate newDate);

    int deleteSub(int subId);

}
