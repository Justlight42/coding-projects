package custom.controller;

import custom.dao.SubscriptionDao;
import custom.exception.DaoException;
import custom.model.Subscription;
import custom.service.AuthenticationService;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;
import java.util.List;

@RestController
@PreAuthorize("isAuthenticated")
@RequestMapping(path = "api/sub")
public class SubscriptionController {

    private final SubscriptionDao subDao;
    private final AuthenticationService authService;

    public SubscriptionController(SubscriptionDao subDao, AuthenticationService authService) {
        this.subDao = subDao;
        this.authService = authService;
    }

    @GetMapping(path = "/{subId}")
    public Subscription getSubById(@PathVariable int subId) {
        try {
            Subscription sub = subDao.getSubById(subId);
            if (sub == null) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Could not find a subscription with that ID " + subId);
            }
            return sub;
        } catch (DaoException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @GetMapping(path = "/user/{userId}")
    public List<Subscription> getAllSubsByUserId(@PathVariable int userId, boolean orderByDate) {
        try {
            List<Subscription> allSubs = subDao.getAllSubsByUserId(userId, orderByDate);
            if (allSubs.isEmpty()) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Could not find a subscriptions with that user ID " + userId);
            }
            return allSubs;
        } catch (DaoException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Subscription createSub(@RequestBody Subscription sub, Principal principal) {
        checkIfOwner(principal, sub);
        Subscription newSub = subDao.createSub(sub);
        if (newSub == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cant create this subscription");
        }
        return newSub;
    }

    @PutMapping(path = "/{subId}")
    public Subscription updateSub(@RequestBody Subscription sub, Principal principal, @PathVariable int subId) {
        Subscription passedSub = subDao.getSubById(subId);
        if (passedSub == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Can't find this subscription with ID " + subId);
        }
        checkIfOwner(principal, sub);
        Subscription newSub = new Subscription(subId, sub.getUserId(), sub.getSubName(), sub.getCost(),
                sub.getBillingCycle(), sub.getNextBilling(), sub.getSiteURL());
        return subDao.updateSub(newSub);
    }

    @DeleteMapping(path = "/{subId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public int deleteSub(@PathVariable int subId, Principal principal) {
        Subscription sub = subDao.getSubById(subId);
        if (sub.getUserId() != authService.getCurrentUserId(principal)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You can't access this subscription");
        }
        return subDao.deleteSub(subId);
    }

    private void checkIfOwner(Principal principal, Subscription newSub) {
        if (newSub.getUserId() != authService.getCurrentUserId(principal)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You can't access this subscription");
        }
    }

}
