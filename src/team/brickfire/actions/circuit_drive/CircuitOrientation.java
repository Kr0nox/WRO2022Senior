package team.brickfire.actions.circuit_drive;

/**
 * <p>Representation of all directions in which the robot can look during {@link CircuitDrive CircuitDrive}</p>
 *
 * @author Team BrickFire
 * @version 2.0
 */
public enum CircuitOrientation {

    NORTH(new Vector2D(0, 1)),
    EAST(new Vector2D(1, 0)),
    SOUTH(new Vector2D(0, -1)),
    WEST(new Vector2D(-1, 0)),
    NONE(new Vector2D(0, 0));

    private final Vector2D fac;
    CircuitOrientation(Vector2D fac) {
        this.fac = fac;
    }

    /**
     * <p>Returns the orientation as a normalized {@link Vector2D vector}</p>
     *
     * @return Normalized vector
     */
    public Vector2D getAsVector() {
        return fac.normalized();
    }

    /**
     * <p>Gets a position based on the facing {@link Vector2D vector}</p>
     *
     * @param v Vector indicating a direction
     * @return The corresponding orientation
     */
    public static CircuitOrientation get(Vector2D v) {
        for (CircuitOrientation o : CircuitOrientation.values()) {
            if (o.fac.normalized().equals(v.normalized())) {
                return o;
            }
        }
        return NONE;
    }
}
