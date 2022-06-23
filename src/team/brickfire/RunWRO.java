package team.brickfire;

import lejos.hardware.Button;
import lejos.hardware.Sound;
import lejos.hardware.lcd.LCD;
import lejos.hardware.port.MotorPort;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.utility.Delay;
import team.brickfire.actions.*;
import team.brickfire.robotParts.Robot;
import team.brickfire.robotParts.sensors.ColorSensor;

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
    public static final double OFFSET = 8;

    /**
     * This method gets called when the program starts
     * Also executes actions
     * @param args Command-line arguments
     */

    public static void main(String[] args) {

        Robot robot = new Robot(WHEEL_DIAMETER, OFFSET, MotorPort.B,
                MotorPort.C, SensorPort.S1, SensorPort.S4);
        ActionsMain main = new ActionsMain(robot);

        //LCD.drawString("Ready", 2, 2);
        Sound.beep();
        //Button.waitForAnyPress();

        robot.armConstruct().calibrateArm();

        //new ActionsSide(robot, new ActionsLaundry(robot), new ActionsWater(robot)).doSide();

        main.execute();



        /*Delay.msDelay(5000);
        robot.armConstruct().moveLow();*/
    }
}
