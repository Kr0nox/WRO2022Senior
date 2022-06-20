package team.brickfire.robotParts.arms;

import lejos.hardware.motor.EV3MediumRegulatedMotor;
import lejos.hardware.port.MotorPort;

public class ArmBlockCarrier extends Arm {


    public ArmBlockCarrier() {
        super (new EV3MediumRegulatedMotor(MotorPort.A));
    }

    public void drop() {
        motor.rotate(0);
    }

    public void pickUp() {
        motor.rotate(0, true);
    }

}
