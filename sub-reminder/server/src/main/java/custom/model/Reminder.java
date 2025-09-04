package custom.model;

import java.time.LocalDate;

public class Reminder {

    private int reminderId;
    private int subId;
    private LocalDate reminderDate;
    private boolean sent;

    public Reminder(int reminderId, int subId, LocalDate reminderDate, boolean sent) {
        this.reminderId = reminderId;
        this.subId = subId;
        this.reminderDate = reminderDate;
        this.sent = sent;
    }

    public int getReminderId() {
        return reminderId;
    }

    public int getSubId() {
        return subId;
    }

    public LocalDate getReminderDate() {
        return reminderDate;
    }

    public boolean isSent() {
        return sent;
    }

}
