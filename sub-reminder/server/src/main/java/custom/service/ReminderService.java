package custom.service;

import custom.dao.ReminderDao;
import custom.dao.SubscriptionDao;
import custom.dto.SubReminderDto;
import custom.exception.DaoException;
import custom.model.Reminder;
import custom.model.Subscription;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class ReminderService {

    private final ReminderDao reminderDao;
    private final SubscriptionDao subscriptionDao;

    public ReminderService(ReminderDao reminderDao, SubscriptionDao subscriptionDao) {
        this.reminderDao = reminderDao;
        this.subscriptionDao = subscriptionDao;
    }

    public List<SubReminderDto> getNotifications(int days) {
        List<SubReminderDto> reminders = reminderDao.getRemindersForBilling(days);

        for (SubReminderDto remind: reminders) {
            reminderDao.setSentToTrue(remind.getReminderId());
        }
        return reminders;
    }

    @Scheduled(cron = "0 0 8 * * ?") // 08:00 AM
    public void autoSendNotifications() {
        getNotifications(0);
    }

    public boolean checkDateLessThanBilling(Reminder reminder) {
        Subscription sub = subscriptionDao.getSubById(reminder.getSubId());
        if (sub == null) {
            throw new DaoException("Cant find subscription to check billing date.");
        }
        LocalDate today = LocalDate.now();
        LocalDate reminderDate = reminder.getReminderDate();
        LocalDate billingDate = sub.getNextBilling();
        if (reminderDate.isBefore(today)) {
            throw new DaoException("Reminder date can't be before today's date " + today);
        }
        if (!reminderDate.isBefore(billingDate)) {
            throw new DaoException("Reminder date must be before the billing date");
        }
        return true;
    }

}
