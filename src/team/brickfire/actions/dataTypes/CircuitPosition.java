package team.brickfire.actions.dataTypes;

import team.brickfire.actions.Circuit;

public enum CircuitPosition {
    NORTH(0, 1),
    NORTHEAST(1, 1),
    EAST(1, 0),
    SOUTHEAST(1, -1),
    SOUTH(0, -1),
    SOUTHWEST(-1, -1),
    WEST(-1, 0),
    NORTHWEST(-1, 1),
    MIDDLE(0, 0);

    public static final CircuitPosition[] POSITION_ORDER
            = new CircuitPosition[]{NORTH, NORTHEAST, EAST, SOUTHEAST, SOUTH, SOUTHWEST, WEST, NORTHWEST};

    private final int xPosition;
    private final int yPosition;


    CircuitPosition(int xPosition, int yPosition) {
        this.xPosition = xPosition;
        this.yPosition = yPosition;
    }

    public boolean isAdjacent(CircuitPosition compare) {
        return (this.xPosition == compare.xPosition && Math.abs(this.yPosition + compare.yPosition) == 1
                || this.yPosition == compare.yPosition && Math.abs(this.xPosition + compare.xPosition) == 1);
    }

    public boolean isCorner() {
        return xPosition != 0 && yPosition != 0;
    }

    public boolean isEdge() {
        return xPosition != 0 ^ yPosition != 0;
    }

    public boolean isCenter() {
        return xPosition == 0 && yPosition == 0;
    }

    public int getX() {
        return xPosition;
    }

    public int getY() {
        return yPosition;
    }

    public CircuitPosition getAdjazent(Orientation o) {
        return getPosition(this.xPosition + o.getX(), this.yPosition + o.getY());
    }

    private CircuitPosition getPosition(int x, int y) {
        for (CircuitPosition p : CircuitPosition.values()) {
            if (p.xPosition == x && p.yPosition == y) {
                return p;
            }
        }
        return MIDDLE;
    }
}
