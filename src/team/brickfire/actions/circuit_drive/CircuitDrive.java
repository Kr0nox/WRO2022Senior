package team.brickfire.actions.circuit_drive;

import team.brickfire.actions.BaseAction;

/**
 * Lets the robot drive around the network of black lines on the field
 *
 * @author Team Brickfire
 * @version 3.0
 */
public class CircuitDrive extends BaseAction {

    private static final double DRIVING_SPEED = 100;
    private static final double TURNING_SPEED = 100;

    private final CircuitNetwork circuit;
    private CircuitOrientation facing;
    private CircuitPosition position;

    /**
     * Creates an Action object for the circuit
     *
     * @param startingPosition Position the robot starts in
     * @param startingOrientation Orientation the robot starts in
     */
    public CircuitDrive(CircuitPosition startingPosition, CircuitOrientation startingOrientation) {
        super();
        this.facing = startingOrientation;
        this.position = startingPosition;
        this.circuit = new CircuitNetwork();
    }

    /**
     * Makes the robot drive to the specified place
     *
     * @param goalPosition Position the robot should stop in
     * @param goalOrientation Direction the robot should face in after stopping
     */
    public void driveTo(CircuitPosition goalPosition, CircuitOrientation goalOrientation) {
        for (CircuitPosition c : circuit.getPath(position, goalPosition, facing, goalOrientation)) {
            driveStraightDistance(c);
        }
        turnTo(goalOrientation);
    }

    /**
     * Makes the robot turn to the said Orientation
     *
     * @param goalOrientation Orientation the robot should turn to
     */
    public void turnTo(CircuitOrientation goalOrientation) {
        if (facing == goalOrientation) {
            return;
        }

        turn((Vector2D.angle(goalOrientation.getAsVector(), facing.getAsVector())), TURNING_SPEED);
        facing = goalOrientation;
    }

    /**
     * Sets the current position for when the robot was moved by other means than this action
     *
     * @param position Robots new position
     * @param orientation Robots new orientation
     */
    public void setPosition(CircuitPosition position, CircuitOrientation orientation) {
        this.position = position;
        this.facing = orientation;
    }

    private void driveStraightDistance(CircuitPosition goalPosition) {
        Vector2D dif = goalPosition.getAsVector().subtract(position.getAsVector());
        turnTo(CircuitOrientation.get(dif.normalized()));
        resetDistance();
        lineFollowing(dif.length(), DRIVING_SPEED);
        position = goalPosition;
        alignMotorRotations();
    }




}
