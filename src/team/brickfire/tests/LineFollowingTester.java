package team.brickfire.tests;

import lejos.hardware.Button;
import lejos.hardware.lcd.LCD;
import lejos.robotics.RegulatedMotor;
import team.brickfire.robotParts.Robot;
import team.brickfire.robotParts.sensors.ColorSensor;

public class LineFollowingTester extends BasicNumberFinder {

    private final RegulatedMotor motorLeft;
    private final RegulatedMotor motorRight;
    private final ColorSensor colorSensorLeft;
    private final ColorSensor colorSensorRight;

    public LineFollowingTester(Robot robot) {
        // p i d correctionPercentage
        super(new String[]{"P", "I", "D", "C"}, new float[]{20, 0.5f, 1, 0.01f}, new int[]{1, -1, -1, -2});
        motorLeft = robot.getLeftMotor();
        motorRight = robot.getRightMotor();
        colorSensorLeft = robot.getLeftColorSensor();
        colorSensorRight = robot.getRightColorSensor();
    }

    public void execute() {
        int baseSpeed = 250;
        float error;
        float lastError = 0;
        float derivative;
        float integral = 0;

        motorLeft.setSpeed(baseSpeed);
        motorRight.setSpeed(baseSpeed);

        motorLeft.forward();
        motorRight.forward();

        float lightLeft = 0;
        float lightRight = 0;
        float correctionValue;


        while (Button.ESCAPE.isUp()) {
            lightLeft = colorSensorLeft.getReflectedLight();
            lightRight = colorSensorRight.getReflectedLight();
            error = lightLeft - lightRight;
            integral += error;
            derivative = error - lastError;
            lastError = error;
            correctionValue = (error * values[0])
                    + (integral * values[1])
                    + (derivative * values[2]);
            motorLeft.setSpeed((int) (baseSpeed - correctionValue * values[3] * baseSpeed));
            motorRight.setSpeed((int) (baseSpeed + correctionValue * values[3] * baseSpeed));
        }
        motorLeft.stop();
        motorRight.stop();
    }
}
