package team.brickfire.robotParts.custom_lejos;

import lejos.robotics.chassis.Chassis;
import lejos.robotics.navigation.MovePilot;

/**
 * <p>Extension of the LeJOS provided MovePilot with some more features</p>
 *
 * @version 1.0
 * @author Team BrickFire
 */
public class CustomMovePilot extends MovePilot {

    private final Chassis superChassis;

    /**
     * <p>Creates a move {@link MovePilot pilot}</p>
     *
     * @param chassis Chassis of the pilot
     */
    public CustomMovePilot(Chassis chassis) {
        super(chassis);
        superChassis = chassis;
    }

    /**
     * <p>Stops the robot and immediately returns</p>
     */
    public void instantStop() {
        superChassis.stop();
    }

}
