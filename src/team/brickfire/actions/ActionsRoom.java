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
            robot.arm().moveArmTo(210);
            robot.travel(-4);
            robot.arm().moveArmTo(-150);
        } else {
            isRoomGame = robot.scanner().isColor(Color.GREEN);
            robot.arm().moveArmTo(-210);
            robot.travel(-3.6);
            robot.arm().moveArmTo(150);
        }
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
            robot.arm().moveArmTo(-155);
            robot.travel(-3.15);
            robot.motorLeft().rotate(-58);
            robot.travel(-3.4);
            robot.arm().closeClawTo(30);
            robot.arm().moveArmTo(0);
            robot.motorLeft().rotate(-275);
            robot.travel(-4.5);
            robot.motorRight().rotate(-25);
            robot.arm().moveArmTo(-350);
            robot.travel(-7.1);
            robot.motorLeft().rotate(-90);
            robot.arm().closeClawTo(170);
        } else {
            robot.arm().moveArmTo(155);
            robot.travel(-3.15);
            robot.motorRight().rotate(-58);
            robot.travel(-3.4);
            robot.arm().closeClawTo(30);
            robot.arm().moveArmTo(0);
            robot.motorRight().rotate(-275);
            robot.travel(-4.5);
            robot.motorLeft().rotate(-25);
            robot.arm().moveArmTo(350);
            robot.travel(-7.1);
            robot.motorRight().rotate(-90);
            robot.arm().closeClawTo(170);
        }
    }
}



