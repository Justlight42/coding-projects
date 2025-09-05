package custom.dao;

import custom.model.Subscription;

import java.time.LocalDate;
import java.util.List;

public interface SubscriptionDao {

    Subscription getSubById(int subId);

    List<Subscription> getAllSubsByUserId(int userId, boolean orderByDate);

    Subscription createSub(Subscription newSub);

    Subscription updateSub(Subscription sub);

    Subscription updateBillingDate(int subId, LocalDate updateDate);

    int deleteSub(int subId);

    List<Subscription> getSubsNeedRefresh();

}
