package team.brickfire.robotParts.arms;

import lejos.hardware.motor.EV3MediumRegulatedMotor;
import lejos.hardware.port.MotorPort;

public class ArmLift extends Arm {
    public ArmLift() {
        super(new EV3MediumRegulatedMotor(MotorPort.D));
        motor.setSpeed(1500);
    }

    public void moveHigh(boolean immediateReturn) {
        motor.rotateTo(0, immediateReturn);
    }

    public void moveHigh() {
        moveHigh(false);
    }

    public void moveLow(boolean immediateReturn) {
        motor.rotateTo(815, immediateReturn);
    }

    public void moveLow() {
        moveLow(false);
    }


    public void movePickUp() {
        motor.rotateTo(725);
    }

    public void moveBlock() {
        motor.rotateTo(400);
    }

    public void moveTable() {
        motor.rotateTo(240);
    }
}
