package team.brickfire;

import lejos.hardware.Brick;
import lejos.hardware.Button;
import lejos.hardware.Sound;
import lejos.hardware.lcd.LCD;
import lejos.hardware.port.MotorPort;
import lejos.hardware.port.SensorPort;
import lejos.utility.Delay;
import team.brickfire.actions.ActionsLaundry;
import team.brickfire.actions.ActionsMain;
import team.brickfire.actions.ActionsRoom;
import team.brickfire.actions.ActionsWater;
import team.brickfire.robotParts.Robot;
import team.brickfire.robotParts.arms.ArmPosition;

/**
 * Utility class that starts the program and handles the order in which things happen
 * @version 2.0
 * @author Team BrickFire
 */
public class RunWRO {

    /**
     * The diameter of the wheels
     */
    public static final double WHEEL_DIAMETER = 6.24;
    /**
     * Distance between wheels and center of the drive axis
     */
    public static final double OFFSET = 6.8;

    /**
     * This method gets called when the program starts
     * Also executes actions
     * @param args Command-line arguments
     */
    public static void main(String[] args) {

        Robot robot = new Robot(WHEEL_DIAMETER, OFFSET, MotorPort.B,
                MotorPort.C, SensorPort.S2, SensorPort.S3);
        ActionsMain main = new ActionsMain(robot);
        LCD.drawString("Ready", 2, 2);
        Sound.beep();
        Button.ENTER.waitForPress();

        ActionsRoom test = new ActionsRoom(robot);
        test.scanBlock(true);
        new ActionsLaundry(robot).collectBlock(true);
        //new ActionsWater(robot).deliver(true);
        test.playGame(true);
    }
}
