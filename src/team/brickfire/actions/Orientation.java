package team.brickfire.actions;

public enum Orientation {
    NORTH(0, 1),
    EAST(1, 0),
    SOUTH(0, -1),
    WEST(-1, 0),
    NONE(0, 0);

    private int xPosition;
    private int yPosition;

    Orientation(int xPosition, int yPosition) {
        this.xPosition = xPosition;
        this.yPosition = yPosition;
    }

    public static Orientation getOrientation(int x, int y) {
        if (x == 0 && y == 1) {
            return NORTH;
        }
        if (x == 1 && y == 0) {
            return EAST;
        }
        if (x == 0 && y == -1) {
            return SOUTH;
        }
        if (x == -1 && y == 0) {
            return WEST;
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
