package custom.dao;

import custom.model.Reminder;

import java.util.List;

public interface ReminderDao {

    Reminder getReminderById(int reminderId);

    List<Reminder> getAllReminders(int userId);

    Reminder createReminder(Reminder newReminder);

    Reminder updateReminder(Reminder reminder);

    int deleteReminder(int reminderId);

}
