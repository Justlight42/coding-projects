package custom.service;

import custom.dto.GameModeDTO;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SessionService {

    public List<GameModeDTO> getGameModes() {
        List<GameModeDTO> gameModes = new ArrayList<>();
        gameModes.add(new GameModeDTO(0, "SCORE"));
        gameModes.add(new GameModeDTO(1, "HEALTH"));
        return gameModes;
    }
}
