package team.brickfire.robotParts.arms;

import lejos.hardware.Button;
import lejos.hardware.lcd.LCD;
import lejos.hardware.motor.BaseRegulatedMotor;
import lejos.hardware.motor.EV3MediumRegulatedMotor;
import lejos.hardware.port.MotorPort;

public class ArmConstruct extends ArmLift implements ArmBlockCarrier {

    private final BaseRegulatedMotor armBlockCarrier;

    public ArmConstruct() {
        this.armBlockCarrier = new EV3MediumRegulatedMotor(MotorPort.A);
        armBlockCarrier.setSpeed(1500);
    }

    public void pickUp() {
        movePickUp();
        moveHigh();
        armBlockCarrier.stop();
    }

    public void moveTransportBlock() {
        armBlockCarrier.forward();
        motor.rotateTo(240);
    }

    public void drop() {
        armBlockCarrier.rotate(-300);
        armBlockCarrier.rotate(180);
    }

    public void calibrateArm() {
        while (Button.ENTER.isUp()) {
            if (Button.UP.isDown()) {
                motor.backward();
            } else if (Button.DOWN.isDown()) {
                motor.forward();
            } else {
                motor.stop();
            }
            if (Button.LEFT.isDown()) {
                armBlockCarrier.backward();
            } else if (Button.RIGHT.isDown()) {
                armBlockCarrier.forward();
            } else {
                armBlockCarrier.stop();
            }
            LCD.clearDisplay();
            LCD.drawString("Lift: " + motor.getTachoCount(), 1, 1);
            LCD.drawString("Whoop: " + motor.getTachoCount(), 1, 2);
        }

        motor.resetTachoCount();
        armBlockCarrier.resetTachoCount();

    }
}
