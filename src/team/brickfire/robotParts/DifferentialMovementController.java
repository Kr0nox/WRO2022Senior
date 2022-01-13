package team.brickfire.robotParts;

import lejos.hardware.port.Port;
import lejos.robotics.Color;
import lejos.robotics.chassis.WheeledChassis;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 * Controls the movement via a MovePilot.
 * All Movement is differential.
 * Implements own methods for the different ways of moving that the MovePilot provides and adds additional features
 * @version 1.1
 * @author Team BrickFire
 */
public class DifferentialMovementController extends MovementController {

    /**
     * Creates an DifferentialMovementController with the given parameters
     * @param wheelDiameter Diameter of the wheel
     * @param offset Distance of the wheel from the middle of their axis
     * @param motorLeft Port of the left driving motor
     * @param motorRight Port of the right driving motor
     * @param sensorLeft Port of the left orientation sensor
     * @param sensorRight Port of the right orientation sensor
     */
    public DifferentialMovementController(double wheelDiameter, double offset, Port motorLeft,
                                          Port motorRight, Port sensorLeft, Port sensorRight) {
        super(wheelDiameter, offset, motorLeft, motorRight, sensorLeft, sensorRight, WheeledChassis.TYPE_DIFFERENTIAL);
    }

    /**
     * The robot squares up with a line it drives towards
     * @param forward The direction in which the robot drives to reach the line
     * @param speed The speed at which the robot moves
     */
    public void alignLine(boolean forward, double speed) {
        char finishedFirst = driveTillLine(forward, speed);
        wheelRight.getMotor().resetTachoCount();
        wheelLeft.getMotor().resetTachoCount();
        wheelRight.getMotor().setSpeed((int) speed * 2 / 3);
        wheelLeft.getMotor().setSpeed((int) speed * 2 / 3);
        boolean finishedAll = false;
        while (!finishedAll) {
            if (finishedFirst == 'r' && colorSensorLeft.getColorID() == Color.BLACK) {
                finishedAll = true;
            } else if (finishedFirst == 'l' && colorSensorRight.getColorID() == Color.BLACK) {
                finishedAll = true;
            }
        }
        // Why this calculation?
        double distCm = wheelLeft.getMotor().getTachoCount() * 0.0488 / 3.7;
        double degrees = Math.toDegrees(Math.atan(distCm) * (finishedFirst == 'r' ? 1 : -1));
        turn(degrees, speed / 3);

        squareWithLine(speed / 3);
    }


    /**
     * The robot turns, so both sensor see the same reflected light
     * @param speed The speed at which robot adjusts
     */
    public void squareWithLine(double speed) {
        wheelLeft.getMotor().setSpeed((int) checkSpeed(speed));
        wheelLeft.getMotor().setSpeed((int) checkSpeed(speed));
        float[] vl = {1};
        float[] vr = {1};
        do {
            colorSensorLeft.getRedMode().fetchSample(vl, 0);
            colorSensorRight.getRedMode().fetchSample(vr, 0);
            if (vl[0] > vr[0]) {
                wheelLeft.getMotor().forward();
                wheelRight.getMotor().backward();
            } else if (vl[0] < vr[0]) {
                wheelLeft.getMotor().backward();
                wheelRight.getMotor().forward();
            }
        } while (Math.abs(vl[0] - vr[0]) < FINAL_ADJUSTMENT_FOR_LINE_SQUARING);
    }

    /**
     * Aligns the robot against the wall
     * @param forward Direction in which the wall is
     */
    public void alignWall(boolean forward) {
        if (forward) {
            pilot.forward();
        } else {
            pilot.backward();
        }
        if (wheelLeft.getMotor().isStalled() || wheelRight.getMotor().isStalled()) {
            pilot.stop();
        }
    }

    // TODO: Line following
    /**
     * The Robot follows a black line
     * @param distance The distance it should drive along the line in cm
     * @param forward Direction the Robot should drive
     */
    public void lineFollowing(double distance, boolean forward) {
        // Robot should recognize which sensor is on the line
        throw new NotImplementedException();
    }


}
