package custom.dao;

import custom.model.PlayerAction;

import java.util.List;

public interface PlayerActionDao {

    PlayerAction getActionById(int actionId);

    List<PlayerAction> getActionsByPlayerId(int playerId);

    List<PlayerAction> getAllActionsInSession(int sessionId);

    PlayerAction createAction(PlayerAction playerAction);

//    PlayerAction updateAction(PlayerAction playerAction);

    PlayerAction revertAction(int actionId);
}
