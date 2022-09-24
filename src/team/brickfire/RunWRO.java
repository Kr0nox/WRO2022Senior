package team.brickfire;

import lejos.hardware.Button;
import lejos.hardware.Sound;
import team.brickfire.actions.Main;
import team.brickfire.robot_parts.Robot;

/**
 * Utility class that starts the program and handles the order in which things happen
 * @version 3.0
 * @author Team BrickFire
 */
public final class RunWRO {

    /**
     * This method gets called when the program starts
     * Also executes actions
     * @param args Command-line arguments
     */
    public static void main(String[] args) {
        Robot r = Robot.create(6.24, 8.85);
        r.drive(0.01);
        Sound.beep();
        Button.waitForAnyPress();
        Main.getInstance().execute();
    }
}

