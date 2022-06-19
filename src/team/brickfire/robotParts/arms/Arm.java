package team.brickfire.robotParts.arms;

import lejos.hardware.motor.BaseRegulatedMotor;

public abstract class Arm {
    protected final BaseRegulatedMotor motor;

    protected Arm(BaseRegulatedMotor motor) {
        this.motor = motor;
    }
}
