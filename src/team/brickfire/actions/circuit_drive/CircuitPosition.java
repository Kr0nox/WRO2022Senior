package team.brickfire.actions.circuit_drive;

/**
 * <p>Representation of all places in which the robot can stand in during {@link CircuitDrive}</p>
 *
 * @author Team BrickFire
 * @version 2.0
 */
public enum CircuitPosition {

    CENTER(new Vector2D(0, 0)),
    NORTH(new Vector2D(0, 36.2)),
    NORTH_EAST(new Vector2D(36.2, 36.2)),
    EAST(new Vector2D(36.2, 0)),
    SOUTH_EAST(new Vector2D(36.2, -36.2)),
    SOUTH(new Vector2D(0, -36.2)),
    SOUTH_WEST(new Vector2D(-36.2, -36.2)),
    WEST(new Vector2D(-36.2, 0)),
    NORTH_WEST(new Vector2D(-36.2, 36.2)),

    EAST_ROOMS_CLOSE(new Vector2D(48.1, 0)),
    EAST_ROOMS_FAR(new Vector2D(67, 0)),

    WEST_ROOMS_CLOSE(new Vector2D(-48.1, 0)),
    WEST_ROOMS_FAR(new Vector2D(-67, 0)),

    RED_CLOSE(new Vector2D(48.1, 17.2)),
    RED_FAR(new Vector2D(67, 20.4)),
    GREEN_CLOSE(new Vector2D(48.1, -17.2)),
    GREEN_FAR(new Vector2D(67, -20.4)),
    YELLOW_CLOSE(new Vector2D(-48.1, 17.2)),
    YELLOW_FAR(new Vector2D(-67, 20.4)),
    BLUE_CLOSE(new Vector2D(-48.1, -17.2)),
    BLUE_FAR(new Vector2D(-67, -20.4));

    private final Vector2D pos;

    CircuitPosition(Vector2D pos) {
        this.pos = pos;
    }

    /**
     * <p>Gets the position as a {@link Vector2D vector}. Origin is the center of the base (center of the playing field).
     * 1 unit equals 1cm</p>
     *
     * @return The vector of this position
     */
    public Vector2D getAsVector() {
        return pos;
    }
}
