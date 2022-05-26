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
        robot.arm().moveArmTo(175);
        robot.travel(-2.8);
        robot.arm().moveArmTo(-150);
    }

    public void playGame (boolean forward) {
        robot.arm().moveArmTo(-200);
        robot.travel(-9);
        robot.motorLeft().rotate(-100);
        robot.arm().closeClawTo(0);
    }
}



