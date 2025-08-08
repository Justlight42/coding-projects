package com.techelevator.custom.dao;

import com.techelevator.custom.model.Player;
import com.techelevator.custom.model.PlayerAction;

import java.util.List;

public interface PlayerActionDao {

    PlayerAction getActionById(int actionId);

    List<PlayerAction> getActionsByPlayerId(int playerId);

    List<PlayerAction> getAllActionsInSession(int sessionId);

    PlayerAction createAction(PlayerAction playerAction);

}
