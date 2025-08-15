package custom.dto;

public class GameModeDTO {

    private int id;
    private String name;

    public GameModeDTO(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

}
