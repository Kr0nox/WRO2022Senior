package team.brickfire.robotParts;

import lejos.hardware.sensor.EV3ColorSensor;
import lejos.robotics.Color;
import lejos.robotics.RegulatedMotor;
import lejos.robotics.chassis.Wheel;
import lejos.robotics.chassis.WheeledChassis;
import team.brickfire.robotParts.sensors.ColorSensor;

/**
 * Controls the movement via a MovePilot.
 * All Movement is differential.
 * Implements own methods for the different ways of moving that the MovePilot provides and adds additional features
 *
 * @author Team BrickFire
 * @version 1.2
 */
public class DifferentialMovementController extends MovementController {

    /**
     * Maximum difference between the reflected light of the two color sensors when squaring with line
     */
    private static final float FINAL_ADJUSTMENT_FOR_SQUARING = 0.1f;

    private static final float CORRECTION_LINE_FOLLOWING_ERROR = 1;
    private static final float CORRECTION_LINE_FOLLOWING_INTEGRAL = 1;
    private static final float CORRECTION_LINE_FOLLOWING_DERIVATIVE = 1;
    private static final float THRESHOLD_LINE_FOLLOWING = 0;
    private static final float PERCENTAGE_CORRECTION_LINE_FOLLOWING = 0.1f;

    private final RegulatedMotor motorLeft;
    private final RegulatedMotor motorRight;

    private final ColorSensor colorSensorLeft;
    private final ColorSensor colorSensorRight;



    /**
     * Creates an DifferentialMovementController with the given parameters
     *
     * @param wheelDiameter   Diameter of the wheel
     * @param offset          Distance of the wheel from the middle of their axis
     * @param motorLeft       Port of the left driving motor
     * @param motorRight      Port of the right driving motor
     * @param portSensorLeft  Port of the left orientation sensor
     * @param portSensorRight Port of the right orientation sensor
     */
    public DifferentialMovementController(double wheelDiameter, double offset, RegulatedMotor motorLeft,
                                          RegulatedMotor motorRight,
                                          EV3ColorSensor portSensorLeft, EV3ColorSensor portSensorRight) {
        super(new Wheel[]{
                        WheeledChassis.modelWheel(motorRight,
                                wheelDiameter).offset(offset),
                        WheeledChassis.modelWheel(motorLeft,
                                wheelDiameter).offset(-offset)},
                WheeledChassis.TYPE_DIFFERENTIAL);
        System.out.println("Differential super reached");
        this.motorLeft = motorLeft;
        this.motorRight = motorRight;
        this.colorSensorLeft = new ColorSensor(portSensorLeft);
        this.colorSensorRight = new ColorSensor(portSensorRight);
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
        } while (Math.abs(valueLeft - valueRight) < FINAL_ADJUSTMENT_FOR_SQUARING);
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

    public void lineFollowing(boolean forward, double minRotations) {
        lineFollowing(pilot.getLinearSpeed(), forward, minRotations);
    }

    public void lineFollowing(boolean forward) {
        lineFollowing(forward, 0);
    }

    public void lineFollowing(double speed, boolean forward) {
        lineFollowing(speed, forward, 0);
    }

    @Override
    public void lineFollowing(double speed, boolean forward, double minRotations) {
        int baseSpeed = (int) checkSpeed(speed);
        float error;
        float lastError = 0;
        float derivative;
        float integral = 0;

        motorLeft.setSpeed(baseSpeed);
        motorRight.setSpeed(baseSpeed);

        if (forward) {
            motorLeft.forward();
            motorRight.forward();
        } else {
            motorLeft.backward();
            motorRight.backward();
        }

        float lightLeft = 0;
        float lightRight = 0;
        float correctionValue;

        while (Math.abs(motorRight.getTachoCount() + motorLeft.getTachoCount()) / 720.0 > minRotations || (!isStalled()
            && (lightRight = colorSensorRight.getReflectedLight()) > THRESHOLD_LINE_FOLLOWING
                && (lightLeft = colorSensorLeft.getReflectedLight()) > THRESHOLD_LINE_FOLLOWING)) {
            error = lightLeft - lightRight;
            integral += error;
            derivative = error - lastError;
            lastError = error;
            correctionValue = (error * CORRECTION_LINE_FOLLOWING_ERROR)
                    + (integral * CORRECTION_LINE_FOLLOWING_INTEGRAL)
                    + (derivative * CORRECTION_LINE_FOLLOWING_DERIVATIVE);
            motorLeft.setSpeed((int) (baseSpeed - correctionValue * PERCENTAGE_CORRECTION_LINE_FOLLOWING * baseSpeed));
            motorRight.setSpeed((int) (baseSpeed + correctionValue * PERCENTAGE_CORRECTION_LINE_FOLLOWING * baseSpeed));
        }
        motorLeft.stop();
        motorRight.stop();
    }

    @Override
    public boolean isStalled() {
        return motorLeft.isStalled() || motorRight.isStalled();
    }
}
