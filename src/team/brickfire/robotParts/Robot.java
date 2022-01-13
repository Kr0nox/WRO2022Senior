package team.brickfire.robotParts;

import lejos.hardware.port.*;

/**
 * Robot class contains Movement. In this case it's differential, since it is the most useful for this case
 * Has attributes for the additional functional 'arms' and sensors
 * @version 2.1
 * @author Team BrickFire
 */
public class Robot extends DifferentialMovementController {

    // Add extra two sensors (maybe classes, depending on whether they need to save data)

    // Add extra Motors (probably as own objects)

    /**
     * Creates a robot object with the given parameters
     * @param wheelDiameter Diameter of the wheel
     * @param offset Distance of the wheel from the middle of their axis
     * @param motorLeft Port of the left driving motor
     * @param motorRight Port of the right driving motor
     * @param arm1 Port of the first functional motor
     * @param arm2 Port of the second functional motor
     * @param sensorLeft Port of the left orientation sensor
     * @param sensorRight Port of the right orientation sensor
     * @param s1 Port of the first functional sensor
     * @param s2 Port of the second functional sensor
     */
    public Robot(double wheelDiameter, double offset, Port motorLeft, Port motorRight, Port arm1,
                 Port arm2, Port sensorLeft, Port sensorRight, Port s1, Port s2) {
        super(wheelDiameter, offset, motorLeft, motorRight, sensorLeft, sensorRight);
        // Initialize extra sensors
        // Create objects for the two "arms"
    }

}
