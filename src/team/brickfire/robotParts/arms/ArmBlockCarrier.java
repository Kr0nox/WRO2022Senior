package team.brickfire.robotParts.arms;

import lejos.hardware.motor.EV3MediumRegulatedMotor;
import lejos.hardware.port.MotorPort;

public class ArmBlockCarrier extends Arm {


    public ArmBlockCarrier() {
        super (new EV3MediumRegulatedMotor(MotorPort.D));
    }

    @Override
    public void moveTo(ArmPosition position) {
        motor.rotate(position.getPosition());
    }
}
