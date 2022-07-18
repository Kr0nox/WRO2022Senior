package team.brickfire.robotParts.arms;

import lejos.hardware.motor.BaseRegulatedMotor;
import team.brickfire.SpeedUtility;

/**
 * <p>Represents a Motor that does stuff on the robot</p>
 *
 * @version 2.0
 * @author Team BrickFire
 */
public abstract class Arm {

    private final ArmMovement startPosition;
    private final BaseRegulatedMotor motor;
    private final double accelerationFactor;

    /**
     * <p>Creates an Arm object</p>
     *
     * @param motor Motor of this arm
     * @param startPosition Position it starts in
     * @param speed Speed it should rotate in
     * @param accelerationFactor How many times the speed, is the acceleration
     */
    public Arm(BaseRegulatedMotor motor, ArmMovement startPosition, double speed, double accelerationFactor) {
        this.startPosition = startPosition;
        this.motor = motor;
        SpeedUtility.setMotorSpeed(motor, Math.abs(speed), Math.abs(speed) * accelerationFactor);
        this.accelerationFactor = accelerationFactor;
    }

    /**
     * <p>Moves the arm to the desired position</p>
     * @param goal Goal position
     * @param immediateReturn whether the method should immediately return after starting the motor
     */
    public void move(ArmMovement goal, boolean immediateReturn) {
        goal.execute(this, immediateReturn);
    }

    /**
     * <p>Sets the motors speed to the given value und sets the acceleration according to the accelerationFactor given
     * in the Constructor</p>
     *
     * @param speed Speed at which the robot drives (0-100% of maximum capability).
     */
    public void setSpeed(int speed) {
        SpeedUtility.setMotorSpeed(motor, Math.abs(speed), Math.abs(speed) * accelerationFactor);
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
    ArmMovement getStartPosition() {
        return startPosition;
    }
}
