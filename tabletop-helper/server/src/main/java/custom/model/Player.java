package custom.model;

public class Player {

    private int playerId;
    private Integer teamId;
    private Integer userId;
    private String name;
    private Integer health;
    private Integer score;

    public Player(int playerId, Integer teamId, Integer userId, String name, Integer health, Integer score) {
        this.playerId = playerId;
        this.teamId = teamId;
        this.userId = userId;
        this.name = name;
        this.health = health;
        this.score = score;
    }

    public Player() {}

    public int getPlayerId() {
        return playerId;
    }

    public Integer getTeamId() {
        return teamId;
    }

    public Integer getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    public Integer getHealth() {
        return health;
    }

    public Integer getScore() {
        return score;
    }

    public void setHealth(Integer health) {
        this.health = health;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public boolean hasHealth() {
        return health != null;
    }

    public boolean hasScore() {
        return score != null;
    }

}
