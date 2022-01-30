package team.brickfire;

import lejos.hardware.Button;
import lejos.hardware.lcd.LCD;
import lejos.robotics.RegulatedMotor;
import team.brickfire.robotParts.Robot;
import team.brickfire.robotParts.sensors.ColorSensor;

public class LineFollowingTester {

    private final RegulatedMotor motorLeft;
    private final RegulatedMotor motorRight;
    private final ColorSensor colorSensorLeft;
    private final ColorSensor colorSensorRight;
    private boolean valueMode = true;
    // p i d correctionPersecntage
    private String[] names = new String[]{"P", "I", "D", "C"};
    private float[] values = new float[]{20, 0.5f, 1, 0.01f};
    private int[] potenz = new int[]{1, -1, -1, -2};
    private int index = 0;
    private boolean quit = false;

    public LineFollowingTester(Robot robot) {
        motorLeft = robot.getLeftMotor();
        motorRight = robot.getRightMotor();
        colorSensorLeft = robot.getLeftColorSensor();
        colorSensorRight = robot.getRightColorSensor();
    }

    private void start() {
        do {
            drawDisplay();
            int id = Button.waitForAnyPress();
            switch (id) {
                case Button.ID_ENTER:
                    break;
                case Button.ID_UP:
                    changeValue(1);
                    break;
                case Button.ID_DOWN:
                    changeValue(-1);
                    break;
                case Button.ID_LEFT:
                    changeIndex(-1);
                    break;
                case Button.ID_RIGHT:
                    changeIndex(1);
                    break;
                case Button.ID_ESCAPE:
                    quit = true;
                    break;
            }
        } while (!quit);
    }

    private void drawDisplay() {
        LCD.clearDisplay();
        LCD.drawString(valueMode ? "Werte ändern:" : "Größe der Änderung:", 0, 0);
        for (int i = 0; i < names.length; i++) {
            LCD.drawString(names[i], i * 2, 1, i == index);
            LCD.drawString((valueMode ? values[i]+"" : Math.pow(10, potenz[i])+""), i * 2, 2);
        }
    }

    private void changeValue(int amount) {
        if (valueMode) {
            values[index] += amount * Math.pow(10, potenz[index]);
        } else {
            potenz[index] += amount;
        }
    }

    private void changeIndex(int amount) {
        index = (index + amount) % values.length;
    }

    public void lineFollowing() {
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
