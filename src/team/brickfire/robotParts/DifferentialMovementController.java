package team.brickfire.robotParts;

import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.Port;
import lejos.hardware.port.TachoMotorPort;
import lejos.hardware.port.UARTPort;
import lejos.robotics.Color;
import lejos.robotics.RegulatedMotor;
import lejos.robotics.chassis.Wheel;
import lejos.robotics.chassis.WheeledChassis;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 * Controls the movement via a MovePilot.
 * All Movement is differential.
 * Implements own methods for the different ways of moving that the MovePilot provides and adds additional features
 *
 * @author Team BrickFire
 * @version 1.2
 */
public class DifferentialMovementController extends MovementController {

    private final RegulatedMotor motorLeft;
    private final RegulatedMotor motorRight;

    private final ColorSensor colorSensorLeft;
    private final ColorSensor colorSensorRight;

    /**
     * Maximum difference between the reflected light of the two color sensors when squaring with line
     */
    private final float finalAdjustmentForSquaring = 0.1f;

    /**
     * Creates an DifferentialMovementController with the given parameters
     *
     * @param wheelDiameter  Diameter of the wheel
     * @param offset         Distance of the wheel from the middle of their axis
     * @param portMotorLeft  Port of the left driving motor
     * @param portMotorRight Port of the right driving motor
     * @param sensorLeft     Port of the left orientation sensor
     * @param sensorRight    Port of the right orientation sensor
     */
    public DifferentialMovementController(double wheelDiameter, double offset, Port portMotorLeft,
                                          Port portMotorRight, Port sensorLeft, Port sensorRight) {
        super(new Wheel[]{
                        WheeledChassis.modelWheel(new EV3LargeRegulatedMotor((TachoMotorPort) portMotorRight),
                                wheelDiameter).offset(offset),
                        WheeledChassis.modelWheel(new EV3LargeRegulatedMotor((TachoMotorPort) portMotorLeft),
                                wheelDiameter).offset(-offset)},
                WheeledChassis.TYPE_DIFFERENTIAL);
        this.motorLeft = new EV3LargeRegulatedMotor((TachoMotorPort) portMotorLeft);
        this.motorRight = new EV3LargeRegulatedMotor((TachoMotorPort) portMotorRight);
        this.colorSensorLeft = new ColorSensor((UARTPort) sensorLeft);
        this.colorSensorRight = new ColorSensor((UARTPort) sensorRight);
    }

    @Override
    public void alignLine(boolean forward, double speed) {
        char finishedFirst = driveTillLine(forward, speed);
        motorLeft.resetTachoCount();
        motorRight.resetTachoCount();
        motorLeft.setSpeed((int) speed * 2 / 3);
        motorRight.setSpeed((int) speed * 2 / 3);
        while (!isStalled()) {
            if ((finishedFirst == 'r' && colorSensorLeft.isColor(Color.BLACK))
                    || (finishedFirst == 'l' && colorSensorRight.isColor(Color.BLACK))) {
                break;
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
        } while (Math.abs(valueLeft - valueRight) < finalAdjustmentForSquaring);
        motorLeft.stop();
        motorRight.stop();
    }

    @Override
    public char driveTillLine(boolean forward, double speed) {
        setLinearSpeed(speed);
        if (forward) {
            pilot.forward();
        } else {
            pilot.backward();
        }
        while (!isStalled()) {
            if (colorSensorLeft.isColor(Color.BLACK) || colorSensorRight.isColor(Color.BLACK)) {
                break;
            }
        }
        if (colorSensorRight.isColor(Color.BLACK)) {
            return 'r';
        } else {
            return 'l';
        }
    }

    @Override
    public void alignWall(boolean forward) {
        if (forward) {
            pilot.forward();
        } else {
            pilot.backward();
        }
        if (isStalled()) {
            pilot.stop();
        }
    }

    // TODO: Line following
    @Override
    public void lineFollowing(double distance, boolean forward) {
        // Robot should recognize which sensor is on the line
        throw new NotImplementedException();
    }

    @Override
    public boolean isStalled() {
        return motorLeft.isStalled() || motorRight.isStalled();
    }

}
