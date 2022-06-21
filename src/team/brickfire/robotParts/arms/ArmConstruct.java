package team.brickfire.robotParts.arms;

import lejos.hardware.motor.BaseRegulatedMotor;
import lejos.hardware.motor.EV3MediumRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.utility.Delay;

public class ArmConstruct extends ArmLift implements ArmBlockCarrier {

    private final BaseRegulatedMotor armBlockCarrier;

    public ArmConstruct() {
        this.armBlockCarrier = new EV3MediumRegulatedMotor(MotorPort.A);
    }

    public void pickUp() {
        armBlockCarrier.forward();
        moveBlock();
        Delay.msDelay(150);
        movePickUp();
        moveHigh();
        armBlockCarrier.stop();
    }

    public void drop() {
        armBlockCarrier.rotate(-260);
    }
}
