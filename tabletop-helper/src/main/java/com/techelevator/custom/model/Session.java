package com.techelevator.custom.model;

import java.time.LocalDateTime;

public class Session {

    private int sessionId;
    private GameMode gameMode;
    private int createdById;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private int sessionDuration;

    public Session(int sessionId, GameMode gameMode, int createdById, LocalDateTime startTime, LocalDateTime endTime, int sessionDuration) {
        this.sessionId = sessionId;
        this.gameMode = gameMode;
        this.createdById = createdById;
        this.startTime = startTime;
        this.endTime = endTime;
        this.sessionDuration = sessionDuration;
    }

    public Session(int sessionId, GameMode gameMode, int createdById, LocalDateTime startTime) {
        this.sessionId = sessionId;
        this.gameMode = gameMode;
        this.createdById = createdById;
        this.startTime = startTime;
    }

    public int getSessionId() {
        return sessionId;
    }

    public GameMode getGameMode() {
        return gameMode;
    }

    public int getCreatedById() {
        return createdById;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public int getSessionDuration() {
        return sessionDuration;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public void setSessionDuration(int sessionDuration) {
        this.sessionDuration = sessionDuration;
    }

}
