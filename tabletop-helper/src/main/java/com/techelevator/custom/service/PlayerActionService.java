package com.techelevator.custom.service;

import com.techelevator.custom.dao.PlayerDao;
import com.techelevator.custom.model.Player;
import com.techelevator.custom.model.PlayerAction;
import org.springframework.stereotype.Service;

@Service
public class PlayerActionService implements PlayerService {

    private final PlayerAction playerAction;

    private final PlayerDao playerDao;

    public PlayerActionService(PlayerAction playerAction, PlayerDao playerDao) {
        this.playerAction = playerAction;
        this.playerDao = playerDao;
    }

    @Override
    public Player updatePlayerCondition(Player player) {
        return null;
    }

    @Override
    public Player revertAction(int actionId, boolean actionReverted) {
        return null;
    }

    
}
