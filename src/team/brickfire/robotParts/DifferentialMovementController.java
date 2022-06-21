package team.brickfire.robotParts;

import lejos.hardware.Button;
import lejos.hardware.Sound;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.robotics.Color;
import lejos.robotics.RegulatedMotor;
import lejos.robotics.chassis.Wheel;
import lejos.robotics.chassis.WheeledChassis;
import lejos.utility.Delay;
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

    // TODO: make private again
    protected final RegulatedMotor motorLeft;
    protected final RegulatedMotor motorRight;

    protected final ColorSensor colorSensorLeft;
    protected final ColorSensor colorSensorRight;


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
        motorLeft.setSpeed((int) speed * 10);
        motorRight.setSpeed((int) speed * 10);
        while (!isStalled()) {
            if ((finishedFirst == 'r' && colorSensorLeft.isColor(Color.BLACK))
                    || (finishedFirst == 'l' && colorSensorRight.isColor(Color.BLACK))) {
                break;
            }
        }
        int avgTachoCount = (motorLeft.getTachoCount() + motorRight.getTachoCount()) / 2;
        // Dist = pi * d * rot / 360
        double distCm = 3.14 * 6.24 * avgTachoCount / 360;
        double degrees = Math.toDegrees(Math.atan(distCm / 16) * (finishedFirst == 'r' ? 1 : -1));

        System.out.println(avgTachoCount + " | " + distCm + " | " + degrees);
        turn(degrees, speed * 20);
        //travel(-3.5);

        //squareWithLine(speed * 10);
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
            if (valueLeft < valueRight) {
                motorLeft.forward();
                motorRight.backward();
            } else if (valueLeft > valueRight) {
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


    public void lineFollowing(double speed, int distance) {
        Sound.beep();
        int baseSpeed = (int) checkSpeed(speed);

        motorLeft.setSpeed(baseSpeed);
        motorRight.setSpeed(baseSpeed);


        float lightLeft;
        float lightRight;
        float correctionValue;

        motorLeft.resetTachoCount();
        motorRight.resetTachoCount();
        pilot.forward();

        while (Math.abs(motorLeft.getTachoCount() + motorRight.getTachoCount()) / 2 < distance) {
            lightLeft = colorSensorLeft.getReflectedLight();
            lightRight = colorSensorRight.getReflectedLight();
            correctionValue = (lightLeft - lightRight) * -0.2f * baseSpeed;
            motorLeft.setSpeed((int) (baseSpeed - correctionValue));
            motorRight.setSpeed((int) (baseSpeed + correctionValue));
        }
        setLinearSpeed((int) checkSpeed(speed));
    }


    @Override
    public boolean isStalled() {
        return motorLeft.isStalled() || motorRight.isStalled();
    }


    public RegulatedMotor motorLeft() {
        return motorRight;
    }

    public RegulatedMotor motorRight() {
        return motorLeft;
    }


}
