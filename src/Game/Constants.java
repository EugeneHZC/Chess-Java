package Game;

public enum Constants {
    ROW(8),
    COL(8),
    CELL_DIMENSION(70);

    private final int value;

    Constants(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
