package team.brickfire.robotParts.arms;

import lejos.hardware.motor.BaseRegulatedMotor;
import lejos.hardware.motor.EV3MediumRegulatedMotor;
import lejos.hardware.port.MotorPort;

public class ArmConstruct extends ArmLift implements ArmBlockCarrier {

    private final BaseRegulatedMotor armBlockCarrier;

    public ArmConstruct() {
        this.armBlockCarrier = new EV3MediumRegulatedMotor(MotorPort.A);
    }

    public void pickUp() {
        armBlockCarrier.rotate(0, true);
        movePickUp();
        // move pickUp2?
        moveHigh();
    }

    public void drop() {
        armBlockCarrier.rotate(0);
    }
}
