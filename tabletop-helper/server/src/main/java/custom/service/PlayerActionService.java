package custom.service;

import custom.dao.PlayerActionDao;
import custom.dao.PlayerDao;
import custom.exception.DaoException;
import custom.exception.PlayerException;
import custom.model.GameMode;
import custom.model.Player;
import custom.model.PlayerAction;
import custom.model.Session;
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
