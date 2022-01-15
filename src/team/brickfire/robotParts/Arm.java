package team.brickfire.robotParts;

import lejos.robotics.RegulatedMotor;

public abstract class Arm<S> {

    protected final RegulatedMotor motor;

    public Arm(RegulatedMotor motor) {
        this.motor = motor;
    }

    public abstract void rotateTo(S state);

}
