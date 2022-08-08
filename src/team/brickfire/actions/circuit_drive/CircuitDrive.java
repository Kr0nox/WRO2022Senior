package team.brickfire.actions.circuit_drive;

import team.brickfire.robot_parts.Robot;

/**
 * <p>Lets the robot drive around the network of black lines on the field</p>
 *
 * @author Team Brickfire
 * @version 3.0
 */
public class CircuitDrive {

    private static final double DRIVING_SPEED = 50;
    private static final double TURNING_SPEED = 40;

    private final CircuitNetwork circuit;
    private final Robot robot;
    private CircuitOrientation facing;
    private CircuitPosition position;


    /**
     * <p>Creates an Action object for the circuit</p>
     *
     * @param robot The robot to drive around the {@link CircuitNetwork network}
     * @param startingPosition {@link CircuitPosition Position} the robot starts in
     * @param startingOrientation {@link CircuitOrientation Orientation} the robot starts in
     */
    public CircuitDrive(Robot robot, CircuitPosition startingPosition, CircuitOrientation startingOrientation) {
        super();
        this.facing = startingOrientation;
        this.position = startingPosition;
        this.circuit = new CircuitNetwork();
        this.robot = robot;
    }

    /**
     * <p>Makes the robot drive to the specified place</p>
     *
     * @param goalPosition {@link CircuitPosition Position} the robot should stop in
     * @param goalOrientation {@link CircuitOrientation Direction} the robot should face in after stopping
     */
    public void driveTo(CircuitPosition goalPosition, CircuitOrientation goalOrientation) {
        for (CircuitPosition c : circuit.getPath(position, goalPosition, facing, goalOrientation)) {
            driveStraightDistance(c);
        }
        turnTo(goalOrientation);
    }

    /**
     * <p>Makes the robot turn to the said {@link CircuitOrientation Orientation}</p>
     *
     * @param goalOrientation Orientation the robot should turn to
     */
    public void turnTo(CircuitOrientation goalOrientation) {
        if (facing == goalOrientation) {
            return;
        }

        Vector2D v1 = facing.getAsVector();
        Vector2D v2 = goalOrientation.getAsVector();
        System.out.println(Vector2D.angle(v1, v2) * (v1.getX() * v2.getY() - v2.getX() * v1.getY()));
        robot.turn((Vector2D.angle(v1, v2)) * (v1.getX() * v2.getY() - v2.getX() * v1.getY()), TURNING_SPEED);
        facing = goalOrientation;
    }

    /**
     * <p>Sets the current {@link CircuitOrientation position} for when the robot was moved by other means than this action</p>
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
        robot.resetDistance();
        robot.lineFollowing(dif.length(), DRIVING_SPEED);
        position = goalPosition;
        robot.alignMotorRotations();
    }

}
