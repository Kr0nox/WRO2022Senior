package team.brickfire.actions;

import team.brickfire.robot_parts.arms.BlockArm;
import team.brickfire.robot_parts.arms.WaterBottleArm;

import java.util.Arrays;

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
        boolean driveDeep = thingsOnLeft && !bottles[1] || !thingsOnLeft && !bottles[0];
        bottles[thingsOnLeft && bottles[1] || !thingsOnLeft && !bottles[0] ? 1 : 0] = false;
        // Drive to table
        setDrivingSpeed(80, 150);
        drive(5.5);
        blockArm.move(BlockArm.NUDGE);
        drive(driveDeep ? 16 : 6);
        LaundryAction.getInstance().scanBlock();
        blockArm.move(BlockArm.HIGHEST, true);
        turn(thingsOnLeft ? 91 : -90);
        blockArm.move(BlockArm.MIDDLE,true);
        setDrivingSpeed(100,200);
        drive(-21);
        // Drop off bottle
        blockArm.move(BlockArm.LOWEST, true);
        waterBottleArm.move(WaterBottleArm.TABLE);
        drive(10);
        waterBottleArm.move(WaterBottleArm.OVER_TABLE);
        // Leave room
        drive(12);
        turn(thingsOnLeft ? 91 : -87);
        blockArm.move(BlockArm.MIDDLE, true);
        setDrivingSpeed(100,150);
        drive(driveDeep ? 24 : 15);
    }
}
