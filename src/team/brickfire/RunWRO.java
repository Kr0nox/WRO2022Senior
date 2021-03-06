package team.brickfire;

import lejos.hardware.Sound;
import team.brickfire.actions.AdjustArmAction;
import team.brickfire.actions.TestingAction;
import team.brickfire.robot_parts.Robot;

/**
 * Utility class that starts the program and handles the order in which things happen
 * @version 3.0
 * @author Team BrickFire
 */
public class RunWRO {

    /**
     * This method gets called when the program starts
     * Also executes actions
     * @param args Command-line arguments
     */
    public static void main(String[] args) {
        Robot.create(6.24, 6.05);

        Sound.beep();
        new AdjustArmAction().run();

        new TestingAction().test();
    }
}

