package team.brickfire.robot_parts.arms;

/**
 * <p>Movement for rotating an {@link Arm Arm} to a certain position</p>
 *
 * @version 1.0
 * @author Team BrickFire
 */
public class RotateToArmMovement extends ArmMovement {

    /**
     * <p>Creates a RotateDistanceArmMovement</p>
     * @param distance Position to rotate to
     */
    protected RotateToArmMovement(int distance) {
        super(distance);
    }

    /**
     * <p>Creates a RotateDistanceArmMovement</p>
     * @param distance Position to rotate to
     * @param speed Speed to rotate at, ranging from -100 to 100
     */
    protected RotateToArmMovement(int distance, double speed) {
        super(distance, speed);
    }

    @Override
    protected void move(Arm arm, boolean immediateReturn) {
        arm.getMotor().rotateTo(distance - arm.getStartPosition().distance, immediateReturn);
    }

    @Override
    public ArmMovement copy() {
        if (speed > 0) {
            return new RotateToArmMovement(distance, speed);
        } else {
            return new RotateToArmMovement(distance);
        }
    }
}
