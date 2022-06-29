package team.brickfire.robotParts;

import lejos.hardware.motor.BaseRegulatedMotor;
import lejos.hardware.motor.EV3MediumRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.robotics.chassis.Wheel;
import lejos.robotics.chassis.WheeledChassis;
import team.brickfire.data.color.Color;
import team.brickfire.robotParts.base.CompetitionFeatures;
import team.brickfire.robotParts.base.DrivingBase;
import team.brickfire.robotParts.custom_lejos.CustomMovePilot;
import team.brickfire.robotParts.sensors.ColorSensor;

/**
 * <p>Represents the robot. <br>
 * It contains all the functions necessary for driving and some competition features like line following and aligning</p>
 * <p>It is modeled after the Singleton-Pattern</p>
 *
 * @version 3.0
 * @author Team BrickFire
 */
public class Robot extends DrivingBase implements CompetitionFeatures {

    private final BaseRegulatedMotor motorLeft;
    private final BaseRegulatedMotor motorRight;
    private final ColorSensor colorSensorLeft;
    private final ColorSensor colorSensorRight;
    private final CustomMovePilot pilot;
    private final double wheelOffset;
    private final double wheelDiameter;

    /**
     * Constructor used by the Singleton-Pattern
     * @param wheelDiameter Diameter of the wheels
     * @param wheelOffset Distance of the wheels from the robots center
     */
    private Robot(double wheelDiameter, double wheelOffset) {
        this.motorLeft = new EV3MediumRegulatedMotor(MotorPort.B);
        this.motorRight = new EV3MediumRegulatedMotor(MotorPort.C);
        this.pilot = new CustomMovePilot(new WheeledChassis(new Wheel[]{
                WheeledChassis.modelWheel(this.motorLeft, wheelDiameter).offset(wheelOffset),
                WheeledChassis.modelWheel(this.motorLeft, wheelDiameter).offset(-wheelOffset)},
                WheeledChassis.TYPE_DIFFERENTIAL));
        this.wheelDiameter = wheelDiameter;
        this.wheelOffset = wheelOffset;

        this.colorSensorLeft = ColorSensor.get(2);
        this.colorSensorRight = ColorSensor.get(3);
    }

    // Singleton-Pattern
    private static Robot instance;

    /**
     * Gives you the instance of the robot
     * @param wheelDiameter Diameter of the wheel
     * @param wheelOffset Distance of the wheels from the robots center
     * @return The robot
     */
    public static Robot create(double wheelDiameter, double wheelOffset) {
        if (instance == null) {
            instance = new Robot(wheelDiameter, wheelOffset);
        }

        return instance;
    }

    /**
     * Constructor used by actions
     */
    protected Robot() {
        if (instance == null) {
            throw new RuntimeException("Robot was not created yet");
        }

        this.motorRight = instance.motorRight;
        this.motorLeft = instance.motorLeft;
        this.pilot = instance.pilot;
        this.wheelOffset = instance.wheelOffset;
        this.wheelDiameter = instance.wheelDiameter;

        this.colorSensorLeft = instance.colorSensorLeft;
        this.colorSensorRight = instance.colorSensorRight;
    }

    @Override
    public void drive(double distance, boolean immediateReturn) {
        pilot.travel(distance, immediateReturn);
    }

    @Override
    public void turn(double angle, boolean immediateReturn) {
        pilot.rotate(angle, immediateReturn);
    }

    @Override
    public void turnLeftWheel(double angle, boolean immediateReturn) {
        pilot.arc(angle, wheelOffset, immediateReturn);
    }

    @Override
    public void turnRightWheel(double angle, boolean immediateReturn) {
        pilot.arc(angle, -wheelOffset, immediateReturn);
    }

    @Override
    public void driveForward() {
        pilot.forward();
    }

    @Override
    public void driveBackward() {
        pilot.backward();
    }

    @Override
    public void stop(boolean immediateReturn) {
        if (immediateReturn) {
            pilot.instantStop();
        } else {
            pilot.stop();
        }
    }

    @Override
    public void setDrivingSpeed(double speed, double acceleration) {
        speed = limitSpeed(speed / 100 * pilot.getMaxLinearSpeed(), 0, pilot.getMaxLinearSpeed());
        acceleration = limitSpeed(acceleration / 100 * pilot.getMaxLinearSpeed(), 0, Double.POSITIVE_INFINITY);
        pilot.setLinearSpeed(speed);
        pilot.setLinearAcceleration(acceleration);
    }

    @Override
    public void setTurningSpeed(double speed, double acceleration) {
        speed = limitSpeed(speed / 100 * pilot.getMaxAngularSpeed(), 0, pilot.getAngularSpeed());
        acceleration = limitSpeed(acceleration / 100 * pilot.getMaxAngularSpeed(), 0, Double.POSITIVE_INFINITY);
        pilot.setAngularSpeed(speed);
        pilot.setAngularAcceleration(acceleration);
    }


    /**
     * <p>Sets the speed and acceleration of the given motor</p>
     * @param motor Motor which gets its speed and acceleration set
     * @param speed Speed at which the motor rotates (0-100% of maximum capability).
     * @param acceleration Speed at which the motor accelerates (0-100% of max speed)
     */
    private void setMotorSpeed(BaseRegulatedMotor motor,double speed, double acceleration) {
        speed = limitSpeed(speed / 100 * motor.getMaxSpeed(), 0, motor.getMaxSpeed());
        acceleration = limitSpeed(acceleration / 100 * motor.getMaxSpeed(), 0, Double.POSITIVE_INFINITY);
        motor.setSpeed((int)speed);
        motor.setAcceleration((int)acceleration);
    }

    /**
     * Limits the speed between the two given values
     * @param value Speed to limit
     * @param min Minimum value
     * @param max Maximum value
     * @return Limited value
     */
    private double limitSpeed(double value, double min, double max) {
        if (value < min) {
            return min;
        }
        if (value > max) {
            return max;
        }
        return value;
    }

    @Override
    public double getLeftWheelDistance() {
        return calcDistance(motorLeft.getTachoCount());
    }

    @Override
    public double getRightWheelDistance() {
        return calcDistance(motorRight.getTachoCount());
    }

    /**
     * Calculates the distance in cm for one motor
     * @param rotations Amount of rotations the motor did
     * @return Distance driven in cm
     */
    private double calcDistance(double rotations) {
        return (Math.abs(rotations) / 360) * Math.PI * wheelDiameter;
    }

    @Override
    public void resetDistance() {
        motorLeft.resetTachoCount();
        motorRight.resetTachoCount();
    }

    @Override
    public void alignTrigonometry(double speed) {
        setDrivingSpeed(Math.abs(speed));
        setTurningSpeed(Math.abs(speed));

        if (speed >= 0) {
            driveForward();
        } else {
            driveBackward();
        }
        // drive first sensor to line
        boolean leftSeen = colorSensorLeft.isColor(Color.BLACK), rightSeen = colorSensorRight.isColor(Color.BLACK);
        while (!(leftSeen || rightSeen)) {
            leftSeen = colorSensorLeft.isColor(Color.BLACK);
            rightSeen = colorSensorRight.isColor(Color.BLACK);
        }
        boolean leftFirst = leftSeen;

        // measure Distance
        resetDistance();
        setDrivingSpeed(speed / 2);
        while (!(leftSeen && rightSeen)) {
            if (!leftSeen) {
                leftSeen = colorSensorLeft.isColor(Color.BLACK);
            }
            if (!rightSeen) {
                rightSeen = colorSensorRight.isColor(Color.BLACK);
            }
        }

        // correct
        double angle = Math.toDegrees(Math.atan(getDistance() / (2 * wheelOffset))) * (leftFirst ? 1 : -1);
        turn(angle);
    }

    @Override
    public void alignColor(double speed, int repetitions) {
        setDrivingSpeed(Math.abs(speed));
        setTurningSpeed(Math.abs(speed));

        for (int i = 0; i < repetitions; i++) {
            Color targetColor = i % 2 == 0 ? Color.BLACK : Color.WHITE;
            boolean leftSeen = false, rightSeen = false;
            if (((i % 2 == 0) && (speed >= 0)) || ((i % 2 == 0) &&(speed < 0))) {
                motorRight.forward();
                motorLeft.forward();
            } else {
                motorLeft.backward();
                motorRight.backward();
            }
            while (!(leftSeen && rightSeen)) {
                if (!leftSeen && colorSensorLeft.isColor(targetColor)) {
                    motorLeft.stop();
                    leftSeen = true;
                }
                if (!rightSeen && colorSensorRight.isColor(targetColor)) {
                    motorRight.stop();
                    rightSeen = true;
                }
            }
        }
    }

    @Override
    public void alignLightLevel(double speed) {
        float dif = Float.POSITIVE_INFINITY;
        setMotorSpeed(motorLeft, speed, speed * 4);
        setMotorSpeed(motorRight, speed, speed * 4);

        while (Math.abs(dif) >= 0.05) {
            float lv = colorSensorLeft.getReflectedLight();
            float rv = colorSensorRight.getReflectedLight();
            if (lv > rv) {
                motorLeft.backward();
                motorRight.forward();
            } else if (rv > lv) {
                motorRight.backward();
                motorLeft.forward();
            }

            dif = colorSensorLeft.getReflectedLight() - colorSensorRight.getReflectedLight();
        }
        motorLeft.stop(true);
        motorRight.stop();
    }

    @Override
    public void alignMotorRotations() {
        if (motorRight.getTachoCount() > motorLeft.getTachoCount()) {
            motorRight.rotate(motorRight.getTachoCount() - motorLeft.getTachoCount());
        } else {
            motorLeft.rotate(motorLeft.getTachoCount() - motorRight.getTachoCount());
        }
    }

    @Override
    public void lineFollowing(double distance, double speed) {
        double kP = 0.02, kI = 0.01, kD = 0.002;
        double integral = 0, lastError = 0;

        resetDistance();
        setMotorSpeed(motorLeft, speed, speed * 4);
        setMotorSpeed(motorRight, speed, speed * 4);

        if (distance >= 0) {
            motorLeft.forward();
            motorRight.forward();
        } else {
            motorLeft.backward();
            motorRight.backward();
        }

        distance = Math.abs(distance);
        while (getDistance() < distance) {
            double error = colorSensorLeft.getReflectedLight() - colorSensorRight.getReflectedLight();
            integral += error;
            double correction = error * kP + integral * kI + (error - lastError) * kD;
            setMotorSpeed(motorLeft, speed * (1 - correction), speed * 4);
            setMotorSpeed(motorRight, speed * (1 + correction), speed * 4);
        }
        motorLeft.stop(true);
        motorLeft.stop();
    }

    @Override
    public void driveTillColor(double speed, Color color) {
        setDrivingSpeed(speed);
        if (speed >= 0) {
            driveForward();
        } else {
            driveBackward();
        }
        while (!(colorSensorLeft.isColor(color) || colorSensorRight.isColor(color)));
        pilot.stop();
    }
}
