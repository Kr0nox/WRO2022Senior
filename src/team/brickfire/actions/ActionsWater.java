package team.brickfire.actions;

import lejos.hardware.lcd.LCD;
import team.brickfire.robotParts.Robot;

// package-private
public class ActionsWater extends BaseAction {

    private int bottleCount;

    /**
     * Creates an ActionsWater Object
     * @param robot The robot
     */
    public ActionsWater(Robot robot) {
        super(robot);
        bottleCount = 2;
    }

    public void collectBottles() {
        robot.travel(6.85, 90);
        robot.turn(-45);
        robot.armConstruct().moveLow(true);
        robot.travel(31.5);
        robot.armConstruct().moveHigh();
        robot.turn(119);
    }

    public void deliverWater(boolean mirrored) {
        LCD.clearDisplay();
        System.out.println(bottleCount);

        // 2 & M | 1 & !M
        robot.travel(bottleCount == 2 && mirrored || bottleCount == 1 && !mirrored ? 0.5 : -8);

        robot.turn(mirrored ? 90 : -90);
        robot.travel(24.5);
        robot.armConstruct().moveTable();

        bottleCount--;
    }

    public void leaveRoomWater(boolean mirrored) {
        //robot.armConstruct().moveHigh();
        //robot.armConstruct().moveTransportBlock();
        robot.travel(-24);
        //robot.armConstruct().pickUp();
        robot.armConstruct().moveHigh(true);
        robot.turn(mirrored ? 90 : -90);
        robot.travel(bottleCount == 1 && mirrored || bottleCount == 0 && !mirrored ? -15 : -22);
    }

}