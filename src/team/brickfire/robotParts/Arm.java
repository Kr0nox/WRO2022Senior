package team.brickfire.robotParts;

import lejos.robotics.RegulatedMotor;

public abstract class Arm<S> {

    protected final RegulatedMotor motor;

    public Arm(RegulatedMotor motor, int speed) {
        this.motor = motor;
        this.motor.setSpeed(speed);
    }

    public abstract void rotateTo(S state);


}
