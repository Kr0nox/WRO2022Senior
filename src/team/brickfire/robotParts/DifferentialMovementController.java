package team.brickfire.robotParts;

import lejos.hardware.port.Port;
import lejos.robotics.Color;
import lejos.robotics.chassis.WheeledChassis;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 * Controls the movement via a MovePilot.
 * All Movement is differential.
 * Implements own methods for the different ways of moving that the MovePilot provides and adds additional features
 * @version 1.1.1
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

    @Override
    public void alignLine(boolean forward, double speed) {
        char finishedFirst = driveTillLine(forward, speed);
        motorLeft.resetTachoCount();
        motorRight.resetTachoCount();
        motorLeft.setSpeed((int) speed * 2 / 3);
        motorRight.setSpeed((int) speed * 2 / 3);
        boolean finishedAll = false;
        while (!finishedAll) {
            if (finishedFirst == 'r' && colorSensorLeft.getColorID() == Color.BLACK) {
                finishedAll = true;
            } else if (finishedFirst == 'l' && colorSensorRight.getColorID() == Color.BLACK) {
                finishedAll = true;
            }
        }
        // Why this calculation?
        int avgTachoCount = (motorLeft.getTachoCount() + motorRight.getTachoCount()) / 2;
        double distCm = avgTachoCount * 0.0488 / 3.7;
        double degrees = Math.toDegrees(Math.atan(distCm) * (finishedFirst == 'r' ? 1 : -1));
        turn(degrees, speed / 3);

        squareWithLine(speed / 3);
    }


    @Override
    public void squareWithLine(double speed) {
        motorLeft.setSpeed((int) checkSpeed(speed));
        motorRight.setSpeed((int) checkSpeed(speed));
        float valueLeft;
        float valueRight;
        do {
            valueLeft = colorSensorLeft.getReflectedLight();
            valueRight = colorSensorRight.getReflectedLight();
            if (valueLeft > valueRight) {
                motorLeft.forward();
                motorRight.backward();
            } else if (valueLeft < valueRight) {
                motorLeft.backward();
                motorRight.forward();
            }
        } while (Math.abs(valueLeft - valueLeft) < finalAdjustmentForSquaring);
        motorLeft.stop();
        motorRight.stop();
    }

    @Override
    public void alignWall(boolean forward) {
        if (forward) {
            pilot.forward();
        } else {
            pilot.backward();
        }
        if (motorLeft.isStalled() || motorRight.isStalled()) {
            pilot.stop();
        }
    }

    // TODO: Line following
    @Override
    public void lineFollowing(double distance, boolean forward) {
        // Robot should recognize which sensor is on the line
        throw new NotImplementedException();
    }


}
