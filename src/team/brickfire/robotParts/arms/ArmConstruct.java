package team.brickfire.robotParts.arms;

import lejos.hardware.motor.BaseRegulatedMotor;
import lejos.hardware.motor.EV3MediumRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.utility.Delay;

public class ArmConstruct extends ArmLift implements ArmBlockCarrier {

    private final BaseRegulatedMotor armBlockCarrier;

    public ArmConstruct() {
        this.armBlockCarrier = new EV3MediumRegulatedMotor(MotorPort.A);
        armBlockCarrier.setSpeed(1000);
    }

    public void pickUp() {
        movePickUp();
        moveHigh();
        armBlockCarrier.stop();
    }

    public void lower() {
        armBlockCarrier.forward();
        motor.rotateTo(250);
    }

    public void stopPickUp() {
        armBlockCarrier.stop();
    }

    public void drop() {
        armBlockCarrier.rotate(-320);
        armBlockCarrier.rotate(100);
    }
}
