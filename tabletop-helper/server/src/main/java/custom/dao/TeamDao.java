package custom.dao;

import custom.model.Team;

import java.util.List;

public interface TeamDao {

    Team getTeamId(int teamId);

    List<Team> getTeamsBySessionId(int sessionId);

    Team createTeam(Team team);

    Team updateTeamName(Team team);

    int deleteTeam(int teamId);

    boolean teamNameExist(int sessionId, String teamName);

}
