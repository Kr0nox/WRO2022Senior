package team.brickfire.actions.circuit_drive;

public enum CircuitOrientation {

    NORTH(0,1),
    EAST(1,0),
    SOUTH(0,-1),
    WEST(-1,0),
    NONE(0,0);

    private final int xFac;
    private final int yFac;

    CircuitOrientation(int xFac, int yFac) {
        this.xFac = xFac;
        this.yFac = yFac;
    }

    public int getX() {
        return xFac;
    }

    public int getY() {
        return yFac;
    }

    public static CircuitOrientation get(double x, double y) {
        int xF = (int)Math.signum(x);
        int yF = (int)Math.signum(y);
        for (CircuitOrientation o : CircuitOrientation.values()) {
            if (o.xFac == xF && o.yFac == yF) {
                return o;
            }
        }
        return NONE;
    }
}
