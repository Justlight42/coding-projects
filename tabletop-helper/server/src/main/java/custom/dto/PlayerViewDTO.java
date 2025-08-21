package custom.dto;

public class PlayerViewDTO {

    private int playerId;
    private String name;
    private Integer health;
    private Integer score;
    private String teamName;

    public PlayerViewDTO(int playerId, String name, Integer health, Integer score, String teamName) {
        this.playerId = playerId;
        this.name = name;
        this.health = health;
        this.score = score;
        this.teamName = teamName;
    }

    public int getPlayerId() {
        return playerId;
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

    public String getTeamName() {
        return teamName;
    }

}
