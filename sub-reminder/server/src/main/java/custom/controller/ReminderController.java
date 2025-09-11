package custom.controller;

import custom.dao.ReminderDao;
import custom.dao.SubscriptionDao;
import custom.dto.SubReminderDto;
import custom.exception.DaoException;
import custom.model.Reminder;
import custom.model.Subscription;
import custom.service.AuthenticationService;
import custom.service.ReminderService;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;
import java.util.List;

@CrossOrigin
@RestController
@PreAuthorize("isAuthenticated")
@RequestMapping(path = "api/reminder")
public class ReminderController {

    private final ReminderDao reminderDao;
    private final AuthenticationService authService;
    private final SubscriptionDao subscriptionDao;
    private final ReminderService reminderService;

    public ReminderController(ReminderDao reminderDao, AuthenticationService authService, SubscriptionDao subscriptionDao, ReminderService reminderService) {
        this.reminderDao = reminderDao;
        this.authService = authService;
        this.subscriptionDao = subscriptionDao;
        this.reminderService = reminderService;
    }

    @GetMapping(path = "/{reminderId}")
    public Reminder getReminderById(@PathVariable int reminderId) {
        try {
            Reminder reminder = reminderDao.getReminderById(reminderId);
            if (reminder == null) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Could not find reminder with the ID " + reminderId);
            }
            return reminder;
        } catch (DaoException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @GetMapping(path = "/list/{userId}")
    public List<SubReminderDto> getAllReminders(@PathVariable int userId) {
        try {
            List<SubReminderDto> reminders = reminderDao.getAllReminders(userId);
            if (reminders.isEmpty()) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Could not find all reminders for the user " + userId);
            }
            return reminders;
        } catch (DaoException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Reminder createReminder(@RequestBody Reminder newReminder, Principal principal) {
        int currentUser = authService.getCurrentUserId(principal);
        Subscription sub = subscriptionDao.getSubById(newReminder.getSubId());
        if (currentUser != sub.getUserId()) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You can't create a reminder for user "
                    + sub.getUserId() + " since you are user " + currentUser);
        }
        reminderService.checkDateLessThanBilling(newReminder);
        Reminder reminder = reminderDao.createReminder(newReminder);
        if (reminder == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Failed to create a reminder");
        }
        return reminder;
    }

    @PutMapping(path = "/{reminderId}")
    public Reminder updateReminder(@RequestBody Reminder reminder, @PathVariable int reminderId, Principal principal) {
        checkIfOwner(reminderId, principal);
        reminderService.checkDateLessThanBilling(reminder);
        Reminder newReminder = new Reminder(reminderId, reminder.getSubId(), reminder.getReminderDate(), reminder.isSent());
        return reminderDao.updateReminder(newReminder);
    }

    @DeleteMapping(path = "/{reminderId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public int deleteReminder(@PathVariable int reminderId, Principal principal) {
        checkIfOwner(reminderId, principal);
        return reminderDao.deleteReminder(reminderId);
    }

    @GetMapping("/notify")
    public List<SubReminderDto> getNotifications(@RequestParam int days) {
        return reminderService.getNotifications(days);
    }

    public void checkIfOwner(int reminderId, Principal principal) {
        int ownerId = reminderDao.getUserIdForReminder(reminderId);
        int userId = authService.getCurrentUserId(principal);
        if (ownerId != userId) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You can't access a reminder for user " + ownerId + " since you are user " + userId);
        }

    }

}
