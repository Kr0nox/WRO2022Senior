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
        bottleCount = 1;
    }

    public void collectBottles() {
        robot.turn(45);
        robot.travel(12);
        //pick up water bottle
        robot.travel(-3);
        robot.turn(90);
        robot.travel(8);
    }

    public void deliverWater(boolean mirrored) {
        LCD.clearDisplay();
        System.out.println(bottleCount);

        // 2 & M | 1 & !M
        robot.travel(bottleCount == 2 && mirrored || bottleCount == 1 && !mirrored ? -1 : -9.5);
        robot.turn(mirrored ? 90 : -90);

        robot.travel(24.5);
        robot.armConstruct().moveTable();

        bottleCount--;
    }

    public void leaveRoomWater(boolean mirrored) {
        robot.travel(-26);
        robot.armConstruct().moveHigh(true);
        robot.turn(mirrored ? 90 : -90);
        robot.travel(bottleCount == 1 ? -15 : -24);
    }

}