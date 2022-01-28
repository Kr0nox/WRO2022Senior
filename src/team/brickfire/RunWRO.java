package team.brickfire;

import lejos.hardware.Button;
import lejos.hardware.lcd.LCD;
import lejos.hardware.port.MotorPort;
import lejos.hardware.port.SensorPort;
import team.brickfire.actions.ActionsMain;
import team.brickfire.robotParts.Robot;
import team.brickfire.robotParts.arms.ArmLaundryBlock;
import team.brickfire.robotParts.arms.dataTypes.StatesArmLaundryBlock;

/**
 * Utility class that starts the program and handles the order in which things happen
 * @version 2.0
 * @author Team BrickFire
 */
public class RunWRO {

    /**
     * The diameter of the wheels
     */
    public static final double WHEEL_DIAMETER = 62.4;
    /**
     * Distance between wheels and center of the drive axis
     */
    public static final double OFFSET = 49;

    /**
     * This method gets called when the program starts
     * Also executes actions
     * @param args Command-line arguments
     */
    public static void main(String[] args) {
        System.out.println("main reached");

        Robot robot = new Robot(WHEEL_DIAMETER, OFFSET, MotorPort.B,
                MotorPort.C, MotorPort.A, MotorPort.A,
                SensorPort.S2, SensorPort.S3, SensorPort.S1, SensorPort.S4);
        System.out.println("robot reached");
        LCD.drawString("Press middle button to start", 2, 2);
        Button.waitForAnyPress();
        // new ActionsMain(robot).execute();
        // TODO: Test movement here
        robot.turn(45);
    }
}