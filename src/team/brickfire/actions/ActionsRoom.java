package team.brickfire.actions;

import lejos.robotics.Color;
import lejos.utility.Delay;
import team.brickfire.robotParts.ColorPool;
import team.brickfire.robotParts.Robot;
import team.brickfire.robotParts.arms.ArmConstruct;

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
        robot.travel(-18.5);
        isRoomGame = robot.getColor(ColorPool.ROOM) == Color.GREEN;
        Delay.msDelay(1000);
    }
    static int i = 0;
    public void doRoom (boolean mirrored) {

        robot.setLinearSpeed(110);
        scanBlock();
        laundry.collectBlock();
       if (isRoomGame || i % 2 == 1) {
            playGame(mirrored);
            leaveRoomBall(mirrored);
        } else {
            water.deliverWater(mirrored);
            water.leaveRoomWater(mirrored);
        }

       i++;
    }

    /**
     * Pick up ball and put it in basket
     *
     * @param mirrored The direction
     */
    public void playGame (boolean mirrored) {
        //pin ball to wall
        robot.travel(mirrored ? -20 : -19.3);
        //collect ball
        robot.armConstruct().moveLow();
        robot.armConstruct().moveHigh();
        //align with basket
        robot.travel(8);
        robot.turn(mirrored ? -70 : 70);
        //travel to basket and drop off
        robot.travel(-23.5);
    }

    public void leaveRoomBall(boolean mirrored) {
        robot.travel(24.8);
        robot.turn(mirrored ? -107 : 107);
        robot.travel(-28);
    }

}



