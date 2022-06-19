package team.brickfire.actions;

import lejos.robotics.Color;
import team.brickfire.robotParts.Robot;

// package-private
public class ActionsRoom extends BaseAction {

    public boolean isRoomGame;

    /**
     * Stores the robot in the local variable
     *
     * @param robot The robot class why is representing the robot
     */
    public ActionsRoom(Robot robot) {
        super(robot);
    }

    public void scanBlock (boolean mirrored) {
        if (mirrored == true) {
            //Drive to roomBlock
            isRoomGame = robot.scanner().isColor(Color.GREEN);
            robot.travel(-4);
        } else {
            //Drive to roomBlock
            isRoomGame = robot.scanner().isColor(Color.GREEN);
            robot.travel(-3.6);
        }
    }

    public void doRoom (boolean mirrored) {
        scanBlock(mirrored);
        new ActionsLaundry(robot).collectBlock(mirrored);
        if (isRoomGame == true) {
            playGame(mirrored);
        } else {
            new ActionsWater(robot).deliverWater(mirrored);
        }
    }


    public void playGame (boolean mirrored) {
            robot.travel(-17);
            //Pick up Ball
            robot.travel(4.5);
            robot.turn(mirrored?90:-90);
            robot.travel(-12);
            //put ball on table
    }
}



