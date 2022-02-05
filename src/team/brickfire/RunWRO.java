package team.brickfire;

import lejos.hardware.Button;
import lejos.hardware.lcd.LCD;
import lejos.hardware.port.MotorPort;
import lejos.hardware.port.SensorPort;
import team.brickfire.actions.ActionsMain;
import team.brickfire.robotParts.Robot;
import team.brickfire.robotParts.arms.ArmLaundryBlock;
import team.brickfire.robotParts.arms.dataTypes.StatesArmLaundryBlock;
import team.brickfire.tests.LineFollowingTester;

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
    public static final double OFFSET = 51; //49

    /**
     * This method gets called when the program starts
     * Also executes actions
     * @param args Command-line arguments
     */
    public static void main(String[] args) {

        Robot robot = new Robot(WHEEL_DIAMETER, OFFSET, MotorPort.B,
                MotorPort.C, MotorPort.A, MotorPort.A,
                SensorPort.S2, SensorPort.S3, SensorPort.S1, SensorPort.S4);

        LCD.drawString("ready", 0, 0);
        Button.waitForAnyPress();
        robot.turn(90);
        Button.waitForAnyPress();
        robot.turn(180);
        Button.waitForAnyPress();
        robot.turn(360);
        Button.waitForAnyPress();
        robot.turn(-90);
    }
}