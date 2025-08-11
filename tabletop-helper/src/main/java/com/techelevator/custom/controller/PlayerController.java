package com.techelevator.custom.controller;

import com.techelevator.custom.dao.PlayerDao;
import com.techelevator.custom.exception.DaoException;
import com.techelevator.custom.model.Player;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping(path = "api/player")
public class PlayerController {

    private final PlayerDao playerDao;

    public PlayerController(PlayerDao playerDao) {
        this.playerDao = playerDao;
    }

    @GetMapping(path = "/{playerId}")
    public Player getPlayerById(@PathVariable int playerId) {
        try {
            Player player = playerDao.getPlayerById(playerId);
            checkIfPlayerExist(playerId);
            return player;
        } catch (DaoException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Could not get player: " + playerId + e.getMessage());
        }
    }

    @GetMapping(path = "/session/{sessionId}")
    public List<Player> getAllPlayersBySessionId(@PathVariable int sessionId) {
        try {
            List<Player> playerlist = playerDao.getAllPlayersBySessionId(sessionId);
            if (playerlist.isEmpty()) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Could not find players with the session ID: " + sessionId);
            }
            return playerlist;
        } catch (DaoException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Could not get players in the session: " + sessionId + e.getMessage());
        }
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("isAuthenticated")
    public Player createPlayer(@RequestBody Player player) {
        Player newPlayer = playerDao.createPlayer(player);
        if (newPlayer == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Failed to create a player.");
        }
        return newPlayer;
    }

    @PutMapping(path = "/{playerId}")
    @PreAuthorize("isAuthenticated")
    public Player updatePlayer(@RequestBody Player player, @PathVariable int playerId) {
        checkIfPlayerExist(playerId);
        Player updatePlayer = new Player(playerId, player.getTeamId(), player.getUserId(), player.getName(), player.getHealth(), player.getScore());
        return playerDao.updatePlayer(updatePlayer);
    }

    @DeleteMapping(path = "/{playerId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("isAuthenticated")
    public int deletePlayer(@PathVariable int playerId) {
        checkIfPlayerExist(playerId);
        return playerDao.deletePlayer(playerId);
    }

    private void checkIfPlayerExist(int playerId) {
        Player gamePlayer = playerDao.getPlayerById(playerId);
        if (gamePlayer == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Could not find player with the ID: " + playerId);
        }
    }

}
