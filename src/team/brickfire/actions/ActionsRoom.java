package team.brickfire.actions;

import lejos.robotics.Color;
import lejos.utility.Delay;
import team.brickfire.robotParts.Robot;

public class ActionsRoom extends BaseAction {

    public boolean isRoomGame;

    private final ActionsLaundry laundry;
    private final ActionsWater water;


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
    public void scanBlock () {
        //robot.driveToRoom();
        robot.travel(-18.5);
        isRoomGame = robot.scanner().roomBlockColor() == GAME_ROOM_COLOR;
        Delay.msDelay(1000);
    }

    public void doRoom (boolean mirrored) {
        robot.setLinearSpeed(110);
        scanBlock();
        laundry.collectBlock(mirrored);
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
        robot.travel(mirrored ? -19.8 : -20.5);
        //collect ball
        robot.armConstruct().moveLow();
        robot.armConstruct().moveHigh();
        //align with basket
        robot.travel(8);
        robot.turn(mirrored ? -72.5 : 72.5);
        //travel to basket and drop off
        robot.travel(mirrored ? -24.2: -23.5);
    }

    public void leaveRoomBall(boolean mirrored) {
        robot.travel(25.5);
        robot.turn(mirrored ? -107 : 107);
        robot.travel(-28);
    }

}



