package custom.service;

import custom.dao.ReminderDao;
import custom.dto.SubReminderDto;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReminderService {

    private final ReminderDao reminderDao;

    public ReminderService(ReminderDao reminderDao) {
        this.reminderDao = reminderDao;
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
        getNotifications(3);
    }

}
