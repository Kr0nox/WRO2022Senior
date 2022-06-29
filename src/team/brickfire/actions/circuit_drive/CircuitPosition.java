package team.brickfire.actions.circuit_drive;

public enum CircuitPosition {

    CENTER(0, 0),
    NORTH(0, 10),
    NORTH_EAST(10, 10),
    EAST(10, 0),
    SOUTH_EAST(10, -10),
    SOUTH(0, -10),
    SOUTH_WEST(-10, -10),
    WEST(-10, 0),
    NORTH_WEST(-10, 10),

    EAST_ROOMS_CLOSE(15, 0),
    EAST_ROOMS_FAR(20, 0),

    WEST_ROOMS_CLOSE(-15, 0),
    WEST_ROOMS_FAR(-20, 0),

    RED_CLOSE(15, 5),
    RED_FAR(20, 5),
    GREEN_CLOSE(15, -5),
    GREEN_FAR(20, -5),
    YELLOW_CLOSE(-15, 5),
    YELLOW_FAR(-20, 5),
    BLUE_CLOSE(-15, -5),
    BLUE_FAR(-20, -5);

    private final double xPos;
    private final double yPos;


    CircuitPosition(double xPos, double yPos) {
        this.xPos = xPos;
        this.yPos = yPos;
    }

    public double getX() {
        return xPos;
    }

    public double getY() {
        return yPos;
    }

    public double getDistance(CircuitPosition p) {

        double xDistance = p.xPos - this.xPos;
        double yDistance = p.yPos - this.yPos;
        return Math.sqrt(xDistance * xDistance + yDistance * yDistance);
    }
}
