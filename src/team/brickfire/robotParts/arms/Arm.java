package team.brickfire.robotParts.arms;

import lejos.hardware.motor.BaseRegulatedMotor;
import team.brickfire.SpeedUtility;

public class Arm {

    private final ArmMovement startPosition;
    private final BaseRegulatedMotor motor;
    private final double accelerationFactor;

    public Arm(BaseRegulatedMotor motor, ArmMovement startPosition, double speed, double accelerationFactor) {
        this.startPosition = startPosition;
        this.motor = motor;
        SpeedUtility.setMotorSpeed(motor, speed, speed * accelerationFactor);
        this.accelerationFactor = accelerationFactor;
    }

    public void move(ArmMovement goal, boolean immediateReturn) {
        goal.execute(this, immediateReturn);
    }

    public void setSpeed(int speed) {
        SpeedUtility.setMotorSpeed(motor, speed, speed * accelerationFactor);
    }

    BaseRegulatedMotor getMotor() {
        return motor;
    }

    ArmMovement getStartPosition() {
        return startPosition;
    }
}
