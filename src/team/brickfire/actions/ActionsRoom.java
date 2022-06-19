package team.brickfire.actions;

import lejos.robotics.Color;
import lejos.utility.Delay;
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
            //drive to roomBlock and scan
            robot.travel(-23);
            isRoomGame = robot.scanner().isColor(Color.GREEN);
            Delay.msDelay(1000);
        } else {
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
            robot.travel(-16);
            robot.armLift().moveLow();
            robot.armLift().moveHigh();
            robot.travel(6);
            robot.turn(mirrored?-70:70);
            robot.travel(-25);
            robot.travel(5);
    }
}



