package team.brickfire.actions;

import lejos.robotics.Color;
import team.brickfire.actions.dataTypes.BasketPosition;
import team.brickfire.challengeParts.LaundryBasket;
import team.brickfire.robotParts.Robot;
import team.brickfire.robotParts.arms.ArmMovement;

import java.util.HashMap;
import java.util.Map;

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

    public void scanBlock (boolean forward) {
        isRoomGame = robot.scanner().isColor(Color.GREEN);
        robot.arm().closeClawTo(180);
        robot.arm().moveArmTo(175);
        robot.travel(-2);
        robot.arm().moveArmTo(-120);
    }
}



