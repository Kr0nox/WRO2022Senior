package team.brickfire.robot_parts.arms.adjusting;

/**
 * <p>Interface for a collection of ArmMovements to be iterated through<br>
 * Can be used in a manual controller</p>
 *
 * @version 1.0
 * @author Team BrickFire
 */
public interface ArmMovementCollection {

    /**
     * <p>Move Arm to the next position</p>
     */
    void next();

    /**
     * <p>Move Arm to the previous position</p>
     */
    void previous();

    /**
     * <p>Gets the amount of allowed positions</p>
     *
     * @return Amount of allowed positions
     */
    int positionCount();
}
