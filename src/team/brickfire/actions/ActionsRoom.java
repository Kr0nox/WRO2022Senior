package team.brickfire.actions;

import lejos.robotics.Color;
import lejos.utility.Delay;
import team.brickfire.robotParts.ColorPool;
import team.brickfire.robotParts.Robot;

// package-private
public class ActionsRoom extends BaseAction {

    public boolean isRoomGame;

    private final ActionsLaundry laundry;
    private final ActionsWater water;


    /**
     * Stores the robot in the local variable
     *
     * @param robot The robot class why is representing the robot
     */
    public ActionsRoom(Robot robot, ActionsLaundry laundry, ActionsWater water) {
        super(robot);
        this.laundry = laundry;
        this.water = water;
    }

    /**
     * drive to roomBlock and scan
     */
    public void scanBlock () {
        robot.travel(-23);
        isRoomGame = robot.getColor(ColorPool.ROOM) == Color.GREEN;
        Delay.msDelay(1000);
    }

    public void doRoom (boolean mirrored) {
        scanBlock();
        new ActionsLaundry(robot).collectBlock(mirrored);
        if (isRoomGame) {
            playGame(mirrored);
        } else {
            new ActionsWater(robot).deliverWater(mirrored);
        }
    }

    /**
     * Pick up ball and put it in basket
     *
     * @param mirrored The direction
     */
    public void playGame (boolean mirrored) {
        robot.travel(-16);
        robot.armConstruct().moveLow();
        robot.armConstruct().moveHigh();
        robot.travel(6);
        robot.turn(mirrored ? -70 : 70);
        robot.travel(-25);
        robot.travel(5);
    }
}



