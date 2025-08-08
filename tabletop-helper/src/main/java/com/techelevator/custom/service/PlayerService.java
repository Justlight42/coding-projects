package com.techelevator.custom.service;

import com.techelevator.custom.model.Player;

public interface PlayerService {

    Player updatePlayerCondition(Player player);

    Player revertAction(int actionId, boolean actionReverted);

}
