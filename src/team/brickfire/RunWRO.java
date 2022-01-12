package team.brickfire;

import lejos.hardware.Button;
import lejos.hardware.lcd.LCD;
import lejos.hardware.port.MotorPort;
import lejos.hardware.port.SensorPort;
import team.brickfire.RobotParts.Robot;

public class RunWRO {

    public static void main(String[] args) {
        Robot robot = new Robot(5.9, 7.2, MotorPort.B, MotorPort.C, MotorPort.A, MotorPort.A,
                SensorPort.S2, SensorPort.S3, SensorPort.S1, SensorPort.S4);
        LCD.drawString("Ready for start", 2, 2);
        Button.waitForAnyPress();
        // Actions
   }
}