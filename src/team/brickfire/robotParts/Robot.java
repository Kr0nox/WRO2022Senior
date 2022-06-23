package team.brickfire.robotParts;

import lejos.hardware.lcd.LCD;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.*;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.robotics.Color;
import team.brickfire.robotParts.arms.ArmConstruct;
import team.brickfire.robotParts.sensors.doble_color.MiddleScanner;

/**
 * Robot class contains Movement. In this case it's differential, since it is the most useful for this case
 * Has attributes for the additional functional 'arms' and sensors
 * @version 2.1
 * @author Team BrickFire
 */
public class Robot extends DifferentialMovementController {

    private final ArmConstruct armConstruct;
    private final MiddleScanner scanner;

    /**
     * Creates a robot object with the given parameters
     * @param wheelDiameter Diameter of the wheel
     * @param offset Distance of the wheel from the middle of their axis
     * @param portMotorLeft Port of the left driving motor
     * @param portMotorRight Port of the right driving motor
     * @param portSensorLeft Port of the left orientation sensor
     * @param portSensorRight Port of the right orientation sensor
     */
    public Robot(double wheelDiameter, double offset, Port portMotorLeft, Port portMotorRight,Port portSensorLeft, Port portSensorRight) {
        super(wheelDiameter, offset, new EV3LargeRegulatedMotor(portMotorLeft),
                new EV3LargeRegulatedMotor(portMotorRight), new EV3ColorSensor(portSensorLeft),
                new EV3ColorSensor(portSensorRight));
        LCD.clearDisplay();
        setAngularSpeed(400);
        setLinearSpeed(110);
        scanner = new MiddleScanner();
        armConstruct = new ArmConstruct();
    }

    /**
     * returns ColorSensor for scanning laundry
     * @return laundryBasketColorSensor
     */
    public MiddleScanner scanner() {
        return scanner;
    }

    public ArmConstruct armConstruct() {
        return armConstruct;
    }

    public void curveLeft(double distance, int speed) {
        motorLeft.setSpeed((int) (speed * 1.35));
        motorRight.setSpeed(speed);
        motorLeft.rotate((int) (distance * 360), true);
        motorRight.rotate((int) (distance * 360));
        motorLeft.setSpeed(speed);
    }

    public int driveToRoom() {
        pilot.setLinearSpeed(15);
        pilot.backward();
        int i = 0;
        while (colorSensorLeft.isColor(Color.WHITE) && colorSensorRight.isColor(Color.WHITE)) {
            System.out.println("A");
            i++;
        }
        pilot.stop();
        while (scanner.roomBlockColor() == Color.NONE) {
            travel(1);
        }
        return scanner.roomBlockColor();

    }
}
