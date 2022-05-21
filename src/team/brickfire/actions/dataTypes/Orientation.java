package team.brickfire.actions.dataTypes;

public enum Orientation {
    NORTH(0, 1),
    EAST(1, 0),
    SOUTH(0, -1),
    WEST(-1, 0),
    NONE(0, 0);

    private final int xPosition;
    private final int yPosition;

    Orientation(int xPosition, int yPosition) {
        this.xPosition = xPosition;
        this.yPosition = yPosition;
    }

    public static Orientation getOrientation(int x, int y) {
        for (Orientation o : Orientation.values()) {
            if (o.xPosition == x && o.yPosition == y) {
                return o;
            }
        }
        return NONE;
    }

    public int getX() {
        return xPosition;
    }

    public int getY() {
        return yPosition;
    }
}
