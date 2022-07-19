package team.brickfire.robot_parts.arms;

/**
 * <p>Movements for the Water bottle arm</p>
 *
 * @version 1.0
 * @author Team BrickFire
 */
public class WaterBottleArmMovement extends ArmMovement {

    public static final WaterBottleArmMovement NONE = new WaterBottleArmMovement(0, ArmMovementType.ROTATE);

    protected WaterBottleArmMovement(int distance, ArmMovementType type) {
        super(distance, type);
    }

    protected WaterBottleArmMovement(int distance) {
        super(distance);
    }

    int i = new Integer(1) + new Integer(1);
}
