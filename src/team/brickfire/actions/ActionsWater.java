package team.brickfire.actions;

import lejos.hardware.Sound;
import lejos.robotics.Color;
import team.brickfire.robotParts.Robot;

public class ActionsWater extends BaseAction {

    private boolean[] bottles;
    private final ActionsLaundry laundry;

    /**
     * Creates an ActionsWater Object
     * @param robot The robot
     */
    public ActionsWater(Robot robot, ActionsLaundry laundry) {
        super(robot);
        bottles = new boolean[] {true, true};
        this.laundry = laundry;
    }

    public void collectBottles() {
        // TODO: 1.6) make perfect (maybe done)
        robot.travel(7.85, 90);
        robot.turn(-45);
        robot.armConstruct().moveLow(true);
        robot.travel(31.5);
        robot.armConstruct().moveHigh();
        robot.turn(121);
    }

    public void deliverWater(boolean mirrored) {
        // 2 & M | 1 & !M
        if (bottles[mirrored ? 0 : 1]) {
            robot.travel(-13);
            bottles[mirrored ? 0 : 1] = false;
        } else {
            robot.travel(-8);
        }

        robot.turn(mirrored ? 90 : -90);
        robot.travel(18);
        robot.armConstruct().moveHigh();
        robot.travel(6.5);
        robot.armConstruct().moveTable();

    }

    public void leaveRoomWater(boolean mirrored) {

        robot.travel(-5);
            robot.armConstruct().moveHigh();

        robot.travel(-7);
        if (laundry.getLastBlockColor() != Color.NONE) {
            robot.armConstruct().moveTransportBlock();
        }
        robot.travel(-13);
        if (laundry.getLastBlockColor() != Color.NONE) {
            robot.armConstruct().pickUp(laundry.amountCollectedBlocks());
        }
        robot.turn(mirrored ? 91.5 : -91.5);
        robot.travel(-17);
    }

}