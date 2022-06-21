package team.brickfire.robotParts.arms;

import lejos.hardware.motor.EV3MediumRegulatedMotor;
import lejos.hardware.port.MotorPort;

public interface ArmBlockCarrier {

    void drop();

    void pickUp();

}
