package team.brickfire.actions.dataTypes;

public enum CircuitPosition {
    NORTH(0, 1),
    NORTHEAST(1, 1),
    EAST(1, 0),
    SOUTHEAST(1, -1),
    SOUTH(0, -1),
    SOUTHWEST(-1, -1),
    WEST(-1, 0),
    NORTHWEST(-1, 1),
    NONE(0, 0);

    public static final CircuitPosition[] POSITION_ORDER
            = new CircuitPosition[]{NORTH, NORTHEAST, EAST, SOUTHEAST, SOUTH, SOUTHWEST, WEST, NORTHWEST};

    private final int xPosition;
    private final int yPosition;


    CircuitPosition(int xPosition, int yPosition) {
        this.xPosition = xPosition;
        this.yPosition = yPosition;
    }

    public boolean isOpposite(CircuitPosition compare) {
        return xPosition + compare.xPosition + yPosition + compare.yPosition == 0;
    }

    public boolean isAdjacent(CircuitPosition compare) {
        return (this.xPosition == compare.xPosition && Math.abs(this.yPosition + compare.yPosition) == 1
                || this.yPosition == compare.yPosition && Math.abs(this.xPosition + compare.xPosition) == 1);
    }

    public int distance(CircuitPosition compare) {
        if (this == compare) {
            return 0;
        }
        if (isAdjacent(compare)) {
            return 1;
        }
        if (isOpposite(compare)) {
            return 4;
        }
        return Math.abs(this.xPosition + compare.xPosition + this.yPosition + compare.yPosition);
    }

    public int getX() {
        return xPosition;
    }

    public int getY() {
        return yPosition;
    }
}
