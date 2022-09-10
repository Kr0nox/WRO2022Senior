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
        boolean driveDeep = thingsOnLeft && !bottles[1] || !thingsOnLeft && bottles[0];
        bottles[driveDeep && thingsOnLeft || !driveDeep && !thingsOnLeft ? 0 : 1] = false;
        // Drive to table
        System.out.println(driveDeep);
        System.out.println(Arrays.toString(bottles));
        setDrivingSpeed(80, 120);
        drive(driveDeep ? 19 : 14);
        while (getDistance() < 6);
        LaundryAction.getInstance().scanBlock();
        blockArm.move(BlockArm.HIGHEST);
        turn(thingsOnLeft ? 88 : -88);
        blockArm.move(BlockArm.MIDDLE,true);
        drive(-21, 100);
        // Drop off bottle
        blockArm.move(BlockArm.LOWEST, true);
        waterBottleArm.move(WaterBottleArm.TABLE);
        // TODO: Leave room
        drive(22);
        System.out.println("completed drive");
        waterBottleArm.move(WaterBottleArm.OVER_TABLE);
        System.out.println("completed overtable");
        blockArm.move(BlockArm.MIDDLE, true);
        turn(thingsOnLeft ? 90 : -90);
        // TODO: Different distance
        setDrivingSpeed(100,100);
        drive(driveDeep ? 25.5 : 20.5);
    }
}
