package team.brickfire.actions;

import lejos.hardware.Button;
import lejos.utility.Delay;

import java.awt.*;

public class TestingAction extends BaseAction {

    public TestingAction() {
        super();
    }

    public void test() {
        setDrivingSpeed(50);
        turnLeftWheel(100);
        Button.waitForAnyPress();
        turnRightWheel(100);
    }
}
