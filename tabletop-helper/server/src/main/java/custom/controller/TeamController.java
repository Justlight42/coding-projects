package custom.controller;

import custom.dao.TeamDao;
import custom.exception.DaoException;
import custom.model.Team;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping(path = "api/team")
public class TeamController {

    private final TeamDao teamDao;

    public TeamController(TeamDao teamDao) {
        this.teamDao = teamDao;
    }

    @GetMapping(path = "/{teamId}")
    public Team getTeamId(@PathVariable int teamId) {
        try {
            Team team = teamDao.getTeamId(teamId);
            if (team == null) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Could not find team with the ID: " + teamId);
            }
            return team;
        } catch (DaoException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Could not get team using ID: " + teamId + e.getMessage());
        }
    }

    @GetMapping(path = "/session/{sessionId}")
    public List<Team> getTeamsBySessionId(@PathVariable int sessionId) {
        try {
            List<Team> teamList = teamDao.getTeamsBySessionId(sessionId);
            if (teamList.isEmpty()) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Could not find teams with the session ID: " + sessionId);
            }
            return teamList;
        } catch (DaoException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Could not get teams using session ID: " + sessionId + e.getMessage());
        }
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("isAuthenticated")
    public Team createTeam(@RequestBody Team team) {
        Team newTeam = teamDao.createTeam(team);
        if (newTeam == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Failed to create a team.");
        }
        return newTeam;
    }

    @PutMapping(path = "/{teamId}")
    @PreAuthorize("isAuthenticated")
    public Team updateTeamName(@RequestBody Team team, @PathVariable int teamId) {
        checkIfTeamExist(teamId);
        Team updatedTeam = new Team(teamId, team.getSessionId(), team.getTeamName());
        return teamDao.updateTeamName(updatedTeam);
    }

    @DeleteMapping(path = "/{teamId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("isAuthenticated")
    public int deleteTeam(@PathVariable int teamId) {
        checkIfTeamExist(teamId);
        return teamDao.deleteTeam(teamId);
    }

    private void checkIfTeamExist(int teamId) {
        Team team = teamDao.getTeamId(teamId);
        if (team == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Could not find team with the ID: " + teamId);
        }
    }

}
