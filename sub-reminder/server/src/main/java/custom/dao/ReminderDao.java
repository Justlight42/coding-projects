package custom.dao;

import custom.dto.SubReminderDto;
import custom.model.Reminder;

import java.util.List;

public interface ReminderDao {

    Reminder getReminderById(int reminderId);

    List<SubReminderDto> getAllReminders(int userId);

    Reminder createReminder(Reminder newReminder);

    Reminder updateReminder(Reminder reminder);

    List<SubReminderDto> getRemindersForBilling(int days);

    Boolean setSentToTrue(int reminderId);

    int deleteReminder(int reminderId);

    int getUserIdForReminder(int reminderId);

}
