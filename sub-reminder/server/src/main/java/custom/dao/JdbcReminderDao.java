package custom.dao;

import custom.dto.SubReminderDto;
import custom.exception.DaoException;
import custom.model.Reminder;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcReminderDao implements ReminderDao {

    private final JdbcTemplate jdbcTemplate;
    private final String REMINDERS_SQL = "SELECT * FROM reminders ";
    private final String REMINDERSDTO_SQL = "SELECT re.reminder_id, sub.sub_name, re.reminder_date, re.sent FROM reminders re " +
            "JOIN subscriptions sub ON re.sub_id = sub.sub_id " +
            "JOIN users us ON sub.user_id = us.user_id ";

    public JdbcReminderDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Reminder getReminderById(int reminderId) {
        try {
            String sql = REMINDERS_SQL + "WHERE reminder_id = ?";
            SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sql, reminderId);
            if (rowSet.next()) {
                return mapToReminderRowSet(rowSet);
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        }
        return null;
    }

    @Override
    public List<SubReminderDto> getAllReminders(int userId) {
        List<SubReminderDto> reminders = new ArrayList<>();
        try {
            String sql = REMINDERSDTO_SQL + "WHERE us.user_id = ? ORDER BY re.reminder_date ASC";
            SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sql, userId);
            while (rowSet.next()) {
                reminders.add(mapToSubReminderRowSet(rowSet));
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        }
        return reminders;
    }

    @Override
    public Reminder createReminder(Reminder reminder) {
        try {
            String countSql = "SELECT COUNT(*) FROM reminders WHERE sub_id = ?";
            int count = jdbcTemplate.queryForObject(countSql, int.class, reminder.getSubId());
            if (count > 0) {
                throw new DaoException("This subscription already has a reminder!");
            }

            String sql = "INSERT INTO reminders (sub_id, reminder_date, sent) VALUES (?, ?, ?) RETURNING reminder_id";
            int reminderId = jdbcTemplate.queryForObject(sql, int.class, reminder.getSubId(), reminder.getReminderDate(), reminder.isSent());
            return getReminderById(reminderId);
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
    }

    @Override
    public Reminder updateReminder(Reminder reminder) {
        try {
            String sql = "UPDATE reminders SET sub_id = ?, reminder_date = ?, sent = ? WHERE reminder_id = ?";
            jdbcTemplate.update(sql, reminder.getSubId(), reminder.getReminderDate(), reminder.isSent(), reminder.getReminderId());
            return getReminderById(reminder.getReminderId());
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
    }

    @Override
    public List<SubReminderDto> getRemindersForBilling(int days, boolean useReminderDate) {
        List<SubReminderDto> notify = new ArrayList<>();
        try {
            String sql;
            LocalDate targetDate = LocalDate.now().plusDays(days);
            if (useReminderDate) {
                sql = REMINDERSDTO_SQL + "WHERE re.reminder_date = ? AND re.sent = FALSE";
            } else {
                sql = REMINDERSDTO_SQL + "WHERE sub.next_billing_date = ? AND re.sent = FALSE";
            }

            SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sql, targetDate);
            while (rowSet.next()) {
                notify.add(mapToSubReminderRowSet(rowSet));
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        }
        return notify;
    }

    @Override
    public Boolean setSentToTrue(int reminderId) {
        try {
            String sql = "UPDATE reminders SET sent = ? WHERE reminder_id = ? AND sent = FALSE";
            int rowsAffected = jdbcTemplate.update(sql, true, reminderId);
            return rowsAffected > 0;
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        }
    }

    @Override
    public int deleteReminder(int reminderId) {
        try {
            String sql = "DELETE FROM reminders WHERE reminder_id = ?";
            return jdbcTemplate.update(sql, reminderId);
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
    }

    @Override
    public int getUserIdForReminder(int reminderId) {
        try {
            String sql = "SELECT us.user_id FROM reminders re " +
                    "JOIN subscriptions sub ON re.sub_id = sub.sub_id " +
                    "JOIN users us ON sub.user_id = us.user_id " +
                    "WHERE re.reminder_id = ?";
            return jdbcTemplate.queryForObject(sql, int.class, reminderId);
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        }
    }

    public static Reminder mapToReminderRowSet(SqlRowSet rowSet) {
        int reminderId = rowSet.getInt("reminder_id");
        int subId = rowSet.getInt("sub_id");
        LocalDate reminderDate = rowSet.getDate("reminder_date").toLocalDate();
        boolean sent = rowSet.getBoolean("sent");
        return new Reminder(reminderId, subId, reminderDate, sent);
    }

    public static SubReminderDto mapToSubReminderRowSet(SqlRowSet rowSet) {
        SubReminderDto reminderDto = new SubReminderDto();
        reminderDto.setReminderId(rowSet.getInt("reminder_id"));
        reminderDto.setSubName(rowSet.getString("sub_name"));
        reminderDto.setReminderDate(rowSet.getDate("reminder_date").toLocalDate());
        reminderDto.setSent(rowSet.getBoolean("sent"));
        return reminderDto;
    }

}
