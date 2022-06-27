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

    public CustomMovePilot(Chassis chassis) {
        super(chassis);
        superChassis = chassis;
    }

    public void instantStop() {
        superChassis.stop();
    }


}
