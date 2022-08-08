package team.brickfire.robot_parts.arms;

/**
 * <p>Movement for rotating an {@link Arm Arm} by a certain distance</p>
 *
 * @version 1.0
 * @author Team BrickFire
 */
public class RotateDistanceArmMovement extends ArmMovement {

    /**
     * <p>Creates a RotateDistanceArmMovement</p>
     * @param distance Distance to rotate
     */
    protected RotateDistanceArmMovement(int distance) {
        super(distance);
    }

    /**
     * <p>Creates a RotateDistanceArmMovement</p>
     * @param distance Distance to rotate
     * @param speed Speed to rotate at, ranging from -100 to 100
     */
    protected RotateDistanceArmMovement(int distance, double speed) {
        super(distance, speed);
    }

    @Override
    protected void move(Arm arm, boolean immediateReturn) {
        arm.getMotor().rotate(distance, immediateReturn);
    }


    @Override
    public ArmMovement copy() {
        if (speed > 0) {
            return new RotateDistanceArmMovement(distance, speed);
        } else {
            return new RotateDistanceArmMovement(distance);
        }
    }
}
