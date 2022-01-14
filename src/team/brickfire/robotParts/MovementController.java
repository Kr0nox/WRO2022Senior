package team.brickfire.robotParts;

import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.Port;
import lejos.hardware.port.TachoMotorPort;
import lejos.hardware.port.UARTPort;
import lejos.robotics.Color;
import lejos.robotics.RegulatedMotor;
import lejos.robotics.chassis.Wheel;
import lejos.robotics.chassis.WheeledChassis;
import lejos.robotics.navigation.MovePilot;

/**
 * The base for different types of MovementControllers.
 * Movement happens through lejos MovePilot.
 * Implements or sets template for own methods for the different ways of moving that the MovePilot provides
 * and adds additional features
 * @version 1.0.1
 * @author Team BrickFire
 */
public abstract class MovementController {

    /**
     * Maximum difference between the reflected light of the two color sensors
     */
    protected final float finalAdjustmentForSquaring = 0.1f;

    protected final RegulatedMotor motorLeft;
    protected final RegulatedMotor motorRight;
    /**
     * Controls all Movements
     */
    protected final MovePilot pilot;

    protected final ColorSensor colorSensorLeft;
    protected final ColorSensor colorSensorRight;

    /**
     * Creates an DifferentialMovementController with the given parameters
     * @param wheelDiameter Diameter of the wheel
     * @param offset Distance of the wheel from the middle of their axis
     * @param portMotorLeft Port of the left driving motor
     * @param portMotorRight Port of the right driving motor
     * @param sensorLeft Port of the left orientation sensor
     * @param sensorRight Port of the right orientation sensor
     * @param chassisType The type of the WheeledChassis on which the MovePilot operates
     */
    public MovementController(double wheelDiameter, double offset, Port portMotorLeft,
                                          Port portMotorRight, Port sensorLeft, Port sensorRight, int chassisType) {
        this.motorLeft = new EV3LargeRegulatedMotor((TachoMotorPort) portMotorLeft);
        this.motorRight = new EV3LargeRegulatedMotor((TachoMotorPort) portMotorRight);
        Wheel wheelLeft = WheeledChassis.modelWheel(this.motorLeft, wheelDiameter).offset(-offset);
        Wheel wheelRight = WheeledChassis.modelWheel(this.motorRight, wheelDiameter).offset(offset);
        this.pilot = new MovePilot(new WheeledChassis(new Wheel[] {wheelRight, wheelLeft}, chassisType));

        this.colorSensorLeft = new ColorSensor((UARTPort) sensorLeft);
        this.colorSensorRight = new ColorSensor((UARTPort) sensorRight);
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
    public void arc(double radius, double distance) {
        pilot.travelArc(radius, distance);
    }
    /**
     * The robot drives an arch around the given radius at the given speed (inaccurate)
     * Positive radius = arc to the left
     * @param radius Radius of the arc
     * @param distance Distance the robot should travel on the arc
     * @param speed The speed at which the robot travels
     */
    public void arc(double radius, double distance, double speed) {
        setLinearSpeed(speed);
        arc(radius, distance);
    }

    /**
     * The robot drives until one sensor reached a black line and returns which sensor first saw the line
     * @param forward The direction the robot should drive in
     * @param speed The speed at which the robot should travel
     * @return 'r' if the right saw the line first, 'l' otherwise
     */
    public char driveTillLine(boolean forward, double speed) {
        setLinearSpeed(speed);
        if (forward) {
            pilot.forward();
        } else {
            pilot.backward();
        }
        while (colorSensorRight.getColorID() != Color.BLACK || colorSensorLeft.getColorID() != Color.BLACK);
        if (colorSensorRight.isColor(Color.BLACK)) {
            return 'r';
        } else {
            return 'l';
        }
    }

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
     * @param distance The distance it should drive along the line in cm
     * @param forward Direction the Robot should drive
     */
    public abstract void lineFollowing(double distance, boolean forward);

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
    }
    /**
     * Sets the speed at which the robot turns (in turn)
     * @param speed The speed for turning
     */
    private void setAngularSpeed(double speed) {
        pilot.setAngularSpeed(checkSpeed(speed));
    }
}
