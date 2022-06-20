package team.brickfire.robotParts.arms;

import lejos.hardware.motor.EV3MediumRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.hardware.port.Port;

public class ArmLift extends Arm {
    public ArmLift() {
        super (new EV3MediumRegulatedMotor(MotorPort.D));
        motor.setSpeed(1100);
    }

    public void moveHigh(boolean immediateReturn) {
        motor.rotateTo(0 , immediateReturn);
    }

    public void moveHigh() {
        moveHigh(false);
    }

    public void moveLow() {
        motor.rotateTo(785);
    }

    public void moveBasket() {
        motor.rotateTo(100);
    }

    protected void movePickUp() {
        motor.rotateTo(0);
    }

    public void moveTable() {
        motor.rotateTo(160);
    }
}
