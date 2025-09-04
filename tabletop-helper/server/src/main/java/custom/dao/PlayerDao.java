package custom.dao;

import custom.dto.PlayerViewDTO;
import custom.model.Player;

import java.util.List;

public interface PlayerDao {

    Player getPlayerById(int playerId);

    List<PlayerViewDTO> getPlayerInSession(int sessionId);

    Player createPlayer(Player player);

    Player updatePlayer(Player player);

    int deletePlayer(int playerId);

}
