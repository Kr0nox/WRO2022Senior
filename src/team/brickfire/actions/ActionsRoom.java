package team.brickfire.actions;

import lejos.hardware.Sound;
import lejos.robotics.Color;
import lejos.utility.Delay;
import team.brickfire.robotParts.Robot;

public class ActionsRoom extends BaseAction {

    public boolean isRoomGame;

    private final ActionsLaundry laundry;
    private final ActionsWater water;


    private static final int WATER_ROOM_COLOR = Color.WHITE;
    private static final int GAME_ROOM_COLOR = Color.GREEN;

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


    public void doRoom (boolean mirrored) {
        isRoomGame = robot.driveToRoom(mirrored) == GAME_ROOM_COLOR;
        robot.setLinearSpeed(110);
        robot.travel(-8.8);
        laundry.collectBlock();
       if (isRoomGame) {
            playGame(mirrored);
            leaveRoomBall(mirrored);
        } else {
            water.deliverWater(mirrored);
            water.leaveRoomWater(mirrored);
        }

    }

    /**
     * Pick up ball and put it in basket
     *
     * @param mirrored The direction
     */
    public void playGame (boolean mirrored) {
        //pin ball to wall
        robot.travel(mirrored ? -20.7 : -21);
        //collect ball
        if (laundry.getLastBlockColor() != Color.NONE) {
            robot.armConstruct().pickUp();
        } else {
            robot.armConstruct().moveLow();
            robot.armConstruct().moveHigh();
        }
        //align with basket
        robot.travel(mirrored ? 8 : 7);
        robot.turn(mirrored ? -68 : 71);
        //travel to basket and drop off
        robot.travel(mirrored ? -23.5: -22.5);
    }

    public void leaveRoomBall(boolean mirrored) {
        robot.travel(mirrored ? 24.5: 23.5);
        robot.turn(mirrored ? -108.5 : 108.5);
        robot.travel(-20);
    }

}



