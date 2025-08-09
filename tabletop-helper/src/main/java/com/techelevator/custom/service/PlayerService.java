package com.techelevator.custom.service;

import com.techelevator.custom.model.Player;
import com.techelevator.custom.model.PlayerAction;
import com.techelevator.custom.model.Session;

public interface PlayerService {

    Player updatePlayerCondition(Player player, PlayerAction playerAction, Session session);

    Player revertAction(int actionId);

}
