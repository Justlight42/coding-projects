package com.techelevator.custom.service;

import com.techelevator.custom.dao.PlayerActionDao;
import com.techelevator.custom.dao.PlayerDao;
import com.techelevator.custom.exception.DaoException;
import com.techelevator.custom.exception.PlayerException;
import com.techelevator.custom.model.GameMode;
import com.techelevator.custom.model.Player;
import com.techelevator.custom.model.PlayerAction;
import com.techelevator.custom.model.Session;
import org.springframework.stereotype.Service;

@Service
public class PlayerActionService implements PlayerService {

    private final PlayerActionDao playerActionDao;

    private final PlayerDao playerDao;

    public PlayerActionService(PlayerActionDao playerActionDao, PlayerDao playerDao) {
        this.playerActionDao = playerActionDao;
        this.playerDao = playerDao;
    }

    @Override
    public Player updatePlayerCondition(Player player, PlayerAction playerAction, Session session) {
        GameMode gameMode = session.getGameMode();
        String actionType = playerAction.getActionType();
        int amount = playerAction.getAmount();

        if (player.getHealth() <= 0 && gameMode == GameMode.HEALTH) {
            throw new DaoException(player.getName() + " has no more health and is out!");
        }

        if (gameMode == GameMode.SCORE && actionType.equals("score")) {
            player.setScore(player.getScore() + amount);
        } else if (gameMode == GameMode.HEALTH && actionType.equals("health")) {
            player.setHealth((player.getHealth() + amount));
        }

        return player;
    }

    @Override
    public Player revertAction(int actionId) {
        PlayerAction action = playerActionDao.getActionById(actionId);
        if (action == null) {
            throw new PlayerException("The action id: " + actionId + " was not found.");
        }
        Player player = playerDao.getPlayerById(action.getPlayerId());
        String actionType = action.getActionType();
        int amount = action.getAmount();

        if (actionType.equals("score")) {
            player.setScore(player.getScore() - amount);
        } else if (actionType.equals("health")) {
            player.setHealth(player.getHealth() - amount);
        }
        playerDao.updatePlayer(player);
        playerActionDao.revertAction(actionId);
        return player;
    }

}
