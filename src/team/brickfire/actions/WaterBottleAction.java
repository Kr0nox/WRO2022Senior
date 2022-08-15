package team.brickfire.actions;

import team.brickfire.robot_parts.arms.BlockArm;
import team.brickfire.robot_parts.arms.WaterBottleArm;

/**
 * <p>Action for everything to do with Water Bottles</p>
 *
 * @version 2.0
 * @author Team BrickFire
 */
public final class WaterBottleAction extends BaseAction {

    private static WaterBottleAction instance;
    private final boolean[] bottles;

    private WaterBottleAction() {
        super();
        this.bottles = new boolean[]{true, true};
    }

    /**
     * <p>Returns the instance of this class</p>
     * <p><i>Singleton-Pattern</i></p>
     * @return The instance of WaterBottleAction
     */
    public static WaterBottleAction getInstance() {
        if (instance == null) {
            instance = new WaterBottleAction();
        }
        return instance;
    }

    /**
     * <p>Delivers a water bottle and leaves the room</p>
     * @param thingsOnLeft Whether the table is on the left side of the room
     */
    public void deliverBottle(boolean thingsOnLeft) {
        boolean driveDeep = thingsOnLeft && !bottles[1] || !thingsOnLeft && bottles[0];
        bottles[driveDeep && thingsOnLeft || !driveDeep && !thingsOnLeft ? 0 : 1] = false;
        // Drive to table
        setDrivingSpeed(80, 120);
        drive(-6);
        blockArm.move(BlockArm.NUDGE);
        drive(driveDeep ? -19 : -12);
        LaundryAction.getInstance().scanBlock();
        blockArm.move(BlockArm.HIGHEST);
        turn(88);
        drive(28, 100);
        // Drop off bottle
        waterBottleArm.move(WaterBottleArm.TABLE);
        // TODO: Leave room
        drive(-15);
        waterBottleArm.move(WaterBottleArm.OVER_TABLE);
    }
}
