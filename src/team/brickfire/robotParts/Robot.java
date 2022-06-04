package team.brickfire.robotParts;

import lejos.hardware.lcd.LCD;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.motor.EV3MediumRegulatedMotor;
import lejos.hardware.port.*;
import lejos.hardware.sensor.EV3ColorSensor;
import team.brickfire.robotParts.arms.Arm;
import team.brickfire.robotParts.sensors.ColorSensor;

/**
 * Robot class contains Movement. In this case it's differential, since it is the most useful for this case
 * Has attributes for the additional functional 'arms' and sensors
 * @version 2.1
 * @author Team BrickFire
 */
public class Robot extends DifferentialMovementController {

    // Add extra two sensors (maybe classes, depending on whether they need to save data)
    //private final ColorSensor scanner;
    // Add extra Motors (probably as own objects)
    private final Arm arm;
    private final ColorSensor scanSensor;



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
                new EV3ColorSensor(portSensorRight), new EV3ColorSensor(SensorPort.S1));
        LCD.clearDisplay();
        setAngularSpeed(200);
        setLinearSpeed(50);
        this.arm = new Arm(new EV3MediumRegulatedMotor(MotorPort.D), new EV3MediumRegulatedMotor(MotorPort.A));
        //this.scanner = new ColorSensor(new EV3ColorSensor(SensorPort.S4));

        scanSensor = new ColorSensor(new EV3ColorSensor(SensorPort.S4));
    }

    /**
     * returns ColorSensor for scanning laundry
     * @return laundryBasketColorSensor
     */
    public ColorSensor scanner() {return scanSensor;}

    public Arm arm() {
        return arm;
    }
}
