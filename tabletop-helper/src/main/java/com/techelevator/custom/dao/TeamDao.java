package com.techelevator.custom.dao;

import com.techelevator.custom.model.Team;

import java.util.List;

public interface TeamDao {

    Team getTeamId(int teamId);

    List<Team> getTeamBySessionId(int sessionId);

    Team createTeam(int sessionId, String teamName);

    Team updateTeamName(int teamId, String teamName);

    int deleteTeam(int teamId);

    boolean teamNameExist(int sessionId, String teamName);

}
