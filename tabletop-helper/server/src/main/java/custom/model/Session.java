package custom.model;

import java.time.Duration;
import java.time.LocalDateTime;

public class Session {

    private int sessionId;
    private GameMode gameMode;
    private int createdById;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String sessionDuration;

    public Session(int sessionId, GameMode gameMode, int createdById, LocalDateTime startTime, LocalDateTime endTime, String sessionDuration) {
        this.sessionId = sessionId;
        this.gameMode = gameMode;
        this.createdById = createdById;
        this.startTime = startTime;
        this.endTime = endTime;
        this.sessionDuration = sessionDuration;
    }

    public Session() {}

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

    public String getSessionDuration() {
        if (endTime == null) {
            return "Ongoing Session";
        }
        Duration duration = Duration.between(startTime, endTime);
        long hours = duration.toHours();
        long minutes = duration.toMinutes() % 60;
        long seconds = duration.toSeconds();
        if (seconds < 60) {
            return String.format("%d sec%s", seconds, seconds <= 1 ? "" : "s");
        }  else if (hours <= 1 && minutes <= 1) {
            return String.format("%d hr %d min", hours, minutes);
        }  else {
            return String.format("%d hrs %d mins", hours, minutes);
        }
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public void setSessionDuration(String sessionDuration) {
        this.sessionDuration = sessionDuration;
    }

}
