package com.techelevator.custom.model;

public class Team {

    private int teamId;
    private int sessionId;
    private String teamName;

    public Team(int teamId, int sessionId, String teamName) {
        this.teamId = teamId;
        this.sessionId = sessionId;
        this.teamName = teamName;
    }

    public int getTeamId() {
        return teamId;
    }

    public int getSessionId() {
        return sessionId;
    }

    public String getTeamName() {
        return teamName;
    }

}
