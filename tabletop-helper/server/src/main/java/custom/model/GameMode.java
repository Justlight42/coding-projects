package custom.model;

public enum GameMode {

    SCORE(0),
    HEALTH(1);

    private final int value;

    GameMode(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static GameMode fromIntToMode(int value) {
        switch (value) {
            case 0: return SCORE;
            case 1: return HEALTH;
            default: throw new IllegalArgumentException("That is an invalid game mode:" + value);
        }
    }

}
