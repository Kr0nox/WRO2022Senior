package team.brickfire.robotParts;

import lejos.robotics.chassis.Wheel;
import lejos.robotics.chassis.WheeledChassis;
import lejos.robotics.navigation.MovePilot;

/**
 * The base for different types of MovementControllers.
 * Movement happens through lejos MovePilot.
 * Implements or sets template for own methods for the different ways of moving that the MovePilot provides
 * and adds additional features
 * @version 1.1
 * @author Team BrickFire
 */
public abstract class MovementController {

    /**
     * Controls all Movements
     */
    protected final MovePilot pilot;

    /**
     * Creates an DifferentialMovementController with the given parameters
     * @param wheels Wheels of the robot
     * @param chassisType The type of the WheeledChassis on which the MovePilot operates
     */
    public MovementController(Wheel[] wheels, int chassisType) {
        this.pilot = new MovePilot(new WheeledChassis(wheels, chassisType));
    }

    /**
     * Drives the given Distance with the last set speed
     * @param distance The distance the robot should drive in cm
     */
    public void travel(double distance) {
        pilot.travel(distance);
    }
    /**
     * Drives the given Distance with the given speed
     * @param distance The distance the robot should drive in cm
     * @param speed The speed the robot should drive with in cm/s
     */
    public void travel(double distance, double speed) {
        setLinearSpeed(speed);
        travel(distance);
    }
    /**
     * Drives the given Distance with the given speed optional stop
     * @param distance The distance the robot should drive in cm
     * @param speed The speed the robot should drive with in cm/s
     * @param stop Whether the robot should stop at the end or keep on driving
     */
    public void travel(double distance, double speed, boolean stop) {
        travel(distance, speed);
        if (stop)
            pilot.stop();
    }

    /**
     * The robot turns
     * @param angle The angle the robot should rotate
     */
    public void turn(double angle) {
        pilot.rotate(angle);
    }
    /**
     * The robot turns with the given speed
     * @param angle The angle the robot should rotate
     * @param speed The speed at which the robot rotates
     */
    public void turn(double angle, double speed) {
        setAngularSpeed(speed);
        turn(angle);
    }

    /**
     * The robot drives an arch around the given radius
     * Positive radius = arc to the left
     * @param radius Radius of the arc
     * @param distance Distance the robot should travel on the arc
     */
    public void travelArc(double radius, double distance) {
        pilot.travelArc(radius, distance);
    }
    /**
     * The robot drives an arch around the given radius at the given speed (inaccurate)
     * Positive radius = arc to the left
     * @param radius Radius of the arc
     * @param distance Distance the robot should travel on the arc
     * @param speed The speed at which the robot travels
     */
    public void travelArc(double radius, double distance, double speed) {
        setLinearSpeed(speed);
        travelArc(radius, distance);
    }

    /**
     * The robot turns on an arch around the given radius
     * Positive radius = arc to the left
     * @param radius Radius of the arc
     * @param degrees Distance the robot should travel on the arc
     */
    public void turnArc(double radius, double degrees) {
        pilot.arc(radius, degrees);
    }
    /**
     * The robot turns on an arch around the given radius at the given speed (inaccurate)
     * Positive radius = arc to the left
     * @param radius Radius of the arc
     * @param degrees Distance the robot should travel on the arc
     * @param speed The speed at which the robot travels
     */
    public void turnArc(double radius, double degrees, double speed) {
        setLinearSpeed(speed);
        turnArc(radius, degrees);
    }

    /**
     * The robot drives until one sensor reached a black line and returns which sensor first saw the line
     * @param forward The direction the robot should drive in
     * @param speed The speed at which the robot should travel
     * @return 'r' if the right saw the line first, 'l' otherwise
     */
    public abstract char driveTillLine(boolean forward, double speed);

    /**
     * The robot squares up with a line it drives towards with the given speed
     * @param forward The direction in which the robot drives to reach the line
     * @param speed The speed at which the robot moves
     */
    public abstract void alignLine(boolean forward, double speed);
    /**
     * The robot squares up with a line it drives towards with the last given speed
     * @param forward The direction in which the robot drives to reach the line
     */
    public void alignLine(boolean forward) {
        alignLine(forward, 300);
    }
    /**
     * The robot squares up with a line in front of him with the last given speed
     */
    public void alignLine() {
        alignLine(true);
    }

    /**
     * Aligns the robot against the wall
     * @param forward Direction in which the wall is
     */
    public abstract void alignWall(boolean forward);
    /**
     * Aligns the robot against the wall with the given speed
     * @param speed The speed the robot should drive with
     * @param forward Direction in which the wall is
     */
    public void alignWall(boolean forward, double speed) {
        setLinearSpeed(speed);
        alignWall(forward);
    }

    /**
     * The robot turns, so both sensor see the same reflected light
     * @param speed The speed at which robot adjusts
     */
    public abstract void squareWithLine(double speed);

    /**
     * The Robot follows a black line
     * @param speed The speed it should drive
     * @param forward Direction the Robot should drive
     * @param minRotations Minimum rotations to do before breaking
     */
    public abstract void lineFollowing(double speed, boolean forward, double minRotations);

    /**
     * The Robot follows a black line
     * @param speed The speed it should drive
     * @param forward Direction the Robot should drive
     */
    public void lineFollowing(double speed, boolean forward) {
        lineFollowing(speed, forward, 0);
    }

    /**
     * Returns the absolute value of the speed
     * @param speed The given speed
     * @return Positive speed value
     */
    protected double checkSpeed(double speed) {
        return Math.abs(speed);
    }

    /**
     * Sets the speed at which the robot drives (in travel and arc)
     * @param speed The speed for driving
     */
    public void setLinearSpeed(double speed) {
        pilot.setLinearSpeed(checkSpeed(speed));
        pilot.setLinearAcceleration(0.5 * speed);
    }
    /**
     * Sets the speed at which the robot turns (in turn)
     * @param speed The speed for turning
     */
    protected void setAngularSpeed(double speed) {
        pilot.setAngularSpeed(checkSpeed(speed));
        pilot.setAngularAcceleration(speed);
    }

    /**
     * Checks whether the robot is still able to move
     * @return Whether the motors are stalled
     */
    public abstract boolean isStalled();
}
