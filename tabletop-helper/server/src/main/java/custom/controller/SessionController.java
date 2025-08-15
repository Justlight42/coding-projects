package custom.controller;

import custom.dao.SessionDao;
import custom.dto.GameModeDTO;
import custom.exception.DaoException;
import custom.model.Session;
import custom.service.SessionService;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping(path = "api/session")
public class SessionController {

    private final SessionDao sessionDao;
    private final SessionService sessionService;

    public SessionController(SessionDao sessionDao, SessionService sessionService) {
        this.sessionDao = sessionDao;
        this.sessionService = sessionService;
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
    public List<Session> getSessionByUserId(@PathVariable int userId) {
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
    public Session endSession(@PathVariable int sessionId) {
        checkIfSessionExist(sessionId);
        return sessionDao.endSession(sessionDao.getSessionById(sessionId));
    }

    @DeleteMapping(path = "/{sessionId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("isAuthenticated")
    public int deleteSession(@PathVariable int sessionId) {
        checkIfSessionExist(sessionId);
        return sessionDao.deleteSession(sessionId);
    }

    private void checkIfSessionExist(int sessionId) {
        Session session = sessionDao.getSessionById(sessionId);
        if (session == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Could not find session with the ID: " + sessionId);
        }
    }

    @GetMapping(path = "/game-modes")
    public List<GameModeDTO> getGameModes() {
        return sessionService.getGameModes();
    }

}
