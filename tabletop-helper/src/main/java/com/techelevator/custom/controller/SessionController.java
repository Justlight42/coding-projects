package com.techelevator.custom.controller;

import com.techelevator.custom.dao.SessionDao;
import com.techelevator.custom.exception.DaoException;
import com.techelevator.custom.model.Session;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping(path = "api/session")
public class SessionController {

    private final SessionDao sessionDao;

    public SessionController(SessionDao sessionDao) {
        this.sessionDao = sessionDao;
    }

    @GetMapping(path = "/{sessionId}")
    public Session getSessionById(@PathVariable int sessionId) {
        try {
            Session session = sessionDao.getSessionById(sessionId);
            if (session == null) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Could not find session with the ID: " + sessionId);
            }
            return session;
        } catch (DaoException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Could not get session: " + sessionId + e.getMessage());
        }
    }

    @GetMapping(path = "/userSessions/{userId}")
    public List<Session> getSessionByUserId(int userId) {
        try {
            List<Session> userSessions = sessionDao.getSessionByUserId(userId);
            if (userSessions.isEmpty()) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Could not find sessions with the user ID: " + userId);
            }
            return userSessions;
        } catch (DaoException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Could not get sessions using user ID: " + userId + e.getMessage());
        }
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("isAuthenticated")
    public Session createSession(@RequestBody Session session) {
        Session newSession = sessionDao.createSession(session);
        if (newSession == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Failed to create a session.");
        }
        return newSession;
    }

    @PutMapping(path = "/{sessionId}")
    @PreAuthorize("isAuthenticated")
    public Session endSession(@RequestBody Session session, @PathVariable int sessionId) {
        checkIfSessionExist(sessionId);
        Session updateSession = new Session(sessionId, session.getGameMode(), session.getCreatedById(),
                session.getStartTime(), session.getEndTime(), session.getSessionDuration());
        return sessionDao.endSession(updateSession);
    }

    @DeleteMapping(path = "/{sessionId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("isAuthenticated")
    public int deleteSession(int sessionId) {
        checkIfSessionExist(sessionId);
        return sessionDao.deleteSession(sessionId);
    }

    private void checkIfSessionExist(int sessionId) {
        Session session = sessionDao.getSessionById(sessionId);
        if (session == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Could not find session with the ID: " + sessionId);
        }
    }

}
