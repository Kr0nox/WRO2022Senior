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
        if (forward == true) {
            isRoomGame = robot.scanner().isColor(Color.GREEN);
            robot.arm().moveArmTo(190);
            robot.travel(-3.2);
            robot.arm().moveArmTo(-150);
        } else {}
    }

    public void doRoomTask (boolean forward) {
        scanBlock(forward);
        new ActionsLaundry(robot).collectBlock(forward);
        if (isRoomGame == true) {
            playGame(forward);
        } else {
            new ActionsWater(robot).deliver(forward);
        }
    }


    public void playGame (boolean forward) {
        if (forward == true) {
            robot.arm().moveArmTo(-145);
            robot.travel(-6);
            robot.motorLeft().rotate(-70);
            robot.travel(-3);
            robot.arm().closeClawTo(30);
            robot.arm().moveArmTo(0);
            robot.motorLeft().rotate(-275);
            robot.travel(-4.5);
            robot.motorRight().rotate(-25);
            robot.arm().moveArmTo(-350);
            robot.travel(-7.85);
            robot.motorLeft().rotate(-40);
            robot.arm().closeClawTo(170);
        } else {}
    }
}



