package com.techelevator.custom.model;

import java.time.LocalDateTime;

public class PlayerAction {

    private int actionId;
    private int sessionId;
    private int playerId;
    private String actionType; // Score or Health
    private int amount; // (+/- Score/Health)
    private LocalDateTime actionTime;
    private boolean actionReverted;

    public PlayerAction(int actionId, int sessionId, int playerId, String actionType, int amount, LocalDateTime actionTime, boolean actionReverted) {
        this.actionId = actionId;
        this.sessionId = sessionId;
        this.playerId = playerId;
        this.actionType = actionType;
        this.amount = amount;
        this.actionTime = actionTime;
        this.actionReverted = actionReverted;
    }

    public PlayerAction(int sessionId, int playerId, String actionType, int amount) {
        this.sessionId = sessionId;
        this.playerId = playerId;
        this.actionType = actionType;
        this.amount = amount;
        this.actionTime = LocalDateTime.now();
        this.actionReverted = false;
    }

    public int getActionId() {
        return actionId;
    }

    public int getSessionId() {
        return sessionId;
    }

    public int getPlayerId() {
        return playerId;
    }

    public String getActionType() {
        return actionType;
    }

    public int getAmount() {
        return amount;
    }

    public LocalDateTime getActionTime() {
        return actionTime;
    }

    public boolean isActionReverted() {
        return actionReverted;
    }

    public void setActionReverted(boolean actionReverted) {
        this.actionReverted = actionReverted;
    }
}
