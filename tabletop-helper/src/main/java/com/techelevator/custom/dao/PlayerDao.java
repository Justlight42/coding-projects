package com.techelevator.custom.dao;

import com.techelevator.custom.model.Player;

import java.util.List;

public interface PlayerDao {

    Player getPlayerById(int playerId);

    List<Player> getAllPlayersBySessionId(int sessionId);

    Player createPlayer(Player player);

    int deletePlayer(int playerId);

}
