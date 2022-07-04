package team.brickfire.actions;

import lejos.hardware.Button;
import lejos.utility.Delay;
import team.brickfire.actions.circuit_drive.CircuitDrive;
import team.brickfire.actions.circuit_drive.CircuitOrientation;
import team.brickfire.actions.circuit_drive.CircuitPosition;

import java.awt.*;

public class TestingAction extends BaseAction {

    public TestingAction() {
        super();
    }

    public void test() {
        setDrivingSpeed(25,50);
        drive(40);
        Button.waitForAnyPress();
        drive(100);

        Button.waitForAnyPress();
        setDrivingSpeed(50,100);
        drive(40);
        Button.waitForAnyPress();
        drive(100);

        Button.waitForAnyPress();
        setDrivingSpeed(100,200);
        drive(40);
        Button.waitForAnyPress();
        drive(100);
    }
}
