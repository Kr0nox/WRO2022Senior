package team.brickfire.robot_parts.arms;

import lejos.hardware.motor.BaseRegulatedMotor;
import team.brickfire.robot_parts.base.SpeedUtility;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * <p>Represents a Motor that does stuff on the robot</p>
 *
 * @version 2.1
 * @author Team BrickFire
 */
public class Arm {

    private final RotateToArmMovement startPosition;
    private final BaseRegulatedMotor motor;
    private final double accelerationFactor;

    private final double standardSpeed;

    /**
     * <p>Creates an Arm object</p>
     *
     * @param motor Motor of this arm
     * @param startPosition Position it starts in
     * @param speed Speed it should rotate in
     * @param accelerationFactor How many times the speed, is the acceleration
     */
    protected Arm(BaseRegulatedMotor motor, RotateToArmMovement startPosition, double speed, double accelerationFactor) {
        this.motor = motor;
        this.startPosition = startPosition;//readPosition(startPosition);
        SpeedUtility.setMotorSpeed(motor, Math.abs(speed), Math.abs(speed) * accelerationFactor);
        this.accelerationFactor = accelerationFactor;
        this.standardSpeed = speed;
    }

    /**
     * <p>Moves the arm to the desired {@link ArmMovement position}</p>
     * @param goal Goal position
     * @param immediateReturn whether the method should immediately return after starting the motor
     */
    public void move(ArmMovement goal, boolean immediateReturn) {
        goal.execute(this, immediateReturn);
        writePosition();
    }

    /**
     * <p>Moves the arm to the desired {@link ArmMovement position}</p>
     * @param goal Goal position
     */
    public void move(ArmMovement goal) {
        goal.execute(this, false);
        writePosition();
    }

    /**
     * <p>Sets the motors speed to the given value und sets the acceleration according to the accelerationFactor given
     * in the Constructor</p>
     *
     * @param speed Speed at which the robot drives (0-100% of maximum capability).
     */
    public void setSpeed(double speed) {
        SpeedUtility.setMotorSpeed(motor, Math.abs(speed), Math.abs(speed) * accelerationFactor);
    }

    /**
     * <p>Determines whether the Arm is currently moving</p>
     * @return True if the arm is moving, otherwise false
     */
    public boolean isMoving() {
        return motor.isMoving();
    }

    /**
     * <p>Returns the arms motor</p>
     * <p><i>Package-private</i></p>
     *
     * @return The motor of this arm
     */
    BaseRegulatedMotor getMotor() {
        return motor;
    }

    /**
     * <p>Returns the starting position</p>
     * <p><i>Package-private</i></p>
     *
     * @return The starting position of this arm
     */
    RotateToArmMovement getStartPosition() {
        return startPosition;
    }

    /**
     * <p>Returns the standard speed</p>
     * <p><i>Package-private</i></p>
     *
     * @return The standard speed of this arm
     */
    double getStandardSpeed() {
        return standardSpeed;
    }

    private RotateToArmMovement readPosition(RotateToArmMovement defaultPos) {
        try {
            FileReader fr = new FileReader(this.getClass().getName() + ".arm");

            int ch = 0;
            String out = "";
            while ((ch = fr.read()) != -1) {
                out += (char)ch;
            }
            fr.close();
            return new RotateToArmMovement(Integer.parseInt(out));
        } catch (Exception e) {
            return defaultPos;
        }
    }

    private void writePosition() {
        try {
            FileWriter fw = new FileWriter(this.getClass().getName() + ".arm");
            fw.write((startPosition.distance + motor.getTachoCount()) + "");
            fw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
