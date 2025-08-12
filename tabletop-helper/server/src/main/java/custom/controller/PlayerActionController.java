package custom.controller;

import custom.dao.PlayerActionDao;
import custom.exception.DaoException;
import custom.model.Player;
import custom.model.PlayerAction;
import custom.service.PlayerActionService;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping(path = "api/playerAction")
public class PlayerActionController {

    private final PlayerActionDao playerActionDao;
    private final PlayerActionService playerActionService;

    public PlayerActionController(PlayerActionDao playerActionDao, PlayerActionService playerActionService) {
        this.playerActionDao = playerActionDao;
        this.playerActionService = playerActionService;
    }

    @GetMapping(path = "/{actionId}")
    public PlayerAction getActionById(@PathVariable int actionId) {
        try {
            PlayerAction action = playerActionDao.getActionById(actionId);
            if (action == null) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Could not find action with the ID: " + actionId);
            }
            return action;
        } catch (DaoException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Could not get action: " + actionId + e.getMessage());
        }
    }

    @GetMapping(path = "/actions/{playerId}")
    List<PlayerAction> getActionsByPlayerId(@PathVariable int playerId) {
        try {
            List<PlayerAction> playerActionList = playerActionDao.getActionsByPlayerId(playerId);
            if (playerActionList.isEmpty()) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Could not find actions with the player ID: " + playerId);
            }
            return playerActionList;
        } catch (DaoException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Could not get action with the player ID: " + playerId + e.getMessage());
        }
    }

    @GetMapping(path = "/sessionActions/{sessionId}")
    List<PlayerAction> getAllActionsInSession(@PathVariable int sessionId) {
        try {
            List<PlayerAction> playerActionList = playerActionDao.getAllActionsInSession(sessionId);
            if (playerActionList.isEmpty()) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Could not find actions with the session ID: " + sessionId);
            }
            return playerActionList;
        } catch (DaoException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Could not get action with the session ID: " + sessionId + e.getMessage());
        }
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("isAuthenticated")
    PlayerAction createAction(@RequestBody PlayerAction playerAction) {
        PlayerAction action = playerActionDao.createAction(playerAction);
        if (action == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Failed to create a player action.");
        }
        return action;
    }

    @PutMapping(path = "/{actionId}/revert")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("isAuthenticated")
    Player revertAction(@PathVariable int actionId) {
        PlayerAction action = playerActionDao.getActionById(actionId);
        if (action == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Failed to get a player action with ID: " + actionId);
        }
        return playerActionService.revertAction(actionId);
    }

}
