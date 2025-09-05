package custom.service;

import custom.dao.SubscriptionDao;
import custom.model.Subscription;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class SubscriptionService {

    private final SubscriptionDao subDao;

    public SubscriptionService(SubscriptionDao subDao) {
        this.subDao = subDao;
    }

    public void adjustBillingDates() {
        List<Subscription> currentDueSubs = subDao.getSubsNeedRefresh();
        for (Subscription sub : currentDueSubs) {
            LocalDate updateDate = sub.getBillingCycle().equalsIgnoreCase("monthly") ?
                    sub.getNextBilling().plusDays(30) : sub.getNextBilling().plusDays(365);
            subDao.updateBillingDate(sub.getSubId(), updateDate);
        }

    }

    // cron is the sec of any minute, the min of any hr, the hr of military time day, day of month, month, day of week
    @Scheduled(cron = "0 0 3 * * ?") // 03:00 AM
    public void autoRenewSubs() {
        System.out.println("Does this work?");
        adjustBillingDates();
    }

}
