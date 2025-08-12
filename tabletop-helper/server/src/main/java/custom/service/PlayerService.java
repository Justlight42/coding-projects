package custom.service;

import custom.model.Player;
import custom.model.PlayerAction;
import custom.model.Session;

public interface PlayerService {

    Player updatePlayerCondition(Player player, PlayerAction playerAction, Session session);

    Player revertAction(int actionId);

}
