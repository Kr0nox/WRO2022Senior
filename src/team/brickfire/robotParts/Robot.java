package team.brickfire.robotParts;

import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.motor.EV3MediumRegulatedMotor;
import lejos.hardware.port.*;
import lejos.hardware.sensor.EV3ColorSensor;
import team.brickfire.robotParts.arms.ArmLaundryBlock;
import team.brickfire.robotParts.arms.ArmWaterBottle;
import team.brickfire.robotParts.sensors.ColorSensor;

/**
 * Robot class contains Movement. In this case it's differential, since it is the most useful for this case
 * Has attributes for the additional functional 'arms' and sensors
 * @version 2.1
 * @author Team BrickFire
 */
public class Robot extends DifferentialMovementController {

    // Add extra two sensors (maybe classes, depending on whether they need to save data)
    private final ColorSensor blockColorSensor;
    private final ColorSensor laundryBasketColorSensor;
    // Add extra Motors (probably as own objects)
    private final ArmLaundryBlock armLaundryBlock;
    private final ArmWaterBottle armWaterBottle;



    /**
     * Creates a robot object with the given parameters
     * @param wheelDiameter Diameter of the wheel
     * @param offset Distance of the wheel from the middle of their axis
     * @param portMotorLeft Port of the left driving motor
     * @param portMotorRight Port of the right driving motor
     * @param portArm1 Port of the first functional motor
     * @param portArm2 Port of the second functional motor
     * @param portSensorLeft Port of the left orientation sensor
     * @param portSensorRight Port of the right orientation sensor
     * @param portS1 Port of the first functional sensor
     * @param portS2 Port of the second functional sensor
     */
    public Robot(double wheelDiameter, double offset, Port portMotorLeft, Port portMotorRight, Port portArm1,
                 Port portArm2, Port portSensorLeft, Port portSensorRight, Port portS1, Port portS2) {
        super(wheelDiameter, offset, new EV3LargeRegulatedMotor(portMotorLeft),
                new EV3LargeRegulatedMotor(portMotorRight), new EV3ColorSensor(portSensorLeft),
                new EV3ColorSensor(portSensorRight));
        blockColorSensor = new ColorSensor(new EV3ColorSensor(portS1));
        laundryBasketColorSensor = new ColorSensor(new EV3ColorSensor(portS2));
        // Create objects for the two "arms"
        armLaundryBlock = new ArmLaundryBlock(new EV3MediumRegulatedMotor(portArm2));
        armWaterBottle = new ArmWaterBottle(new EV3MediumRegulatedMotor(portArm1));
    }

    /**
     * returns ColorSensor for scanning blocks
     * @return blockColorSensor
     */
    public ColorSensor blockColorSensor() {
        return blockColorSensor;
    }

    /**
     * returns ColorSensor for scanning laundry
     * @return laundryBasketColorSensor
     */
    public ColorSensor laundryBasketColorSensor() {
        return laundryBasketColorSensor;
    }

    /**
     * returns arm for picking up laundry
     * @return armLaundryBlock
     */
    public ArmLaundryBlock armLaundryBlock() {
        return armLaundryBlock;
    }

    /**
     * returns arm for picking up water bottles
     * @return armWaterBottle
     */
    public ArmWaterBottle armWaterBottle() {
        return armWaterBottle;
    }
}
