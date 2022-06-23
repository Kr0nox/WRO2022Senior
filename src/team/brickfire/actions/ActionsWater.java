package team.brickfire.actions;

import lejos.robotics.Color;
import team.brickfire.robotParts.Robot;

public class ActionsWater extends BaseAction {

    private int bottleCount;
    private final ActionsLaundry laundry;

    /**
     * Creates an ActionsWater Object
     * @param robot The robot
     */
    public ActionsWater(Robot robot, ActionsLaundry laundry) {
        super(robot);
        bottleCount = 2;
        this.laundry = laundry;
    }

    public ActionsWater(Robot robot, int bottleCount) {
        this(robot, new ActionsLaundry(robot));
        this.bottleCount = bottleCount;
    }

    public void collectBottles() {
        // TODO: 6) make perfect
        robot.travel(6.85, 90);
        robot.turn(-45);
        robot.armConstruct().moveLow(true);
        robot.travel(31.5);
        robot.armConstruct().moveHigh();
        robot.turn(119);
    }

    public void deliverWater(boolean mirrored) {
        robot.armConstruct().moveTransportBlock();
        // 2 & M | 1 & !M
        // TODO: 2) figure these two out
        robot.travel(bottleCount == 2 && mirrored || bottleCount == 1 && !mirrored ? 0.5 : -8);

        robot.turn(mirrored ? 90 : -90);
        // TODO: 3) Check the next two
        robot.travel(18);
        robot.armConstruct().moveHigh();
        robot.travel(6.5);
        robot.armConstruct().moveTable();

        bottleCount--;
    }

    public void leaveRoomWater(boolean mirrored) {
        // TODO: 4) figure the next three out
        robot.travel(-5);
            robot.armConstruct().moveHigh();

        robot.travel(-4);
        if (laundry.getLastBlockColor() != Color.NONE) {
            robot.armConstruct().moveTransportBlock();
        }
        robot.travel(-15);
        if (laundry.getLastBlockColor() != Color.NONE) {
            robot.armConstruct().pickUp();
        }
        robot.turn(mirrored ? 90 : -90);
        // TODO: 5) Adjust these
        robot.travel(bottleCount == 1 && mirrored || bottleCount == 0 && !mirrored ? -15 : -22);
    }

}