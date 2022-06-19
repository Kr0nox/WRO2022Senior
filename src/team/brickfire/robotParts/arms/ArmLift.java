package team.brickfire.robotParts.arms;

import lejos.hardware.motor.EV3MediumRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.hardware.port.Port;

public class ArmLift extends Arm {
    public ArmLift() {
        super (new EV3MediumRegulatedMotor(MotorPort.D));
    }
}
