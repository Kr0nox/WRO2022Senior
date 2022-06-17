package team.brickfire.actions;

import team.brickfire.robotParts.Robot;
import team.brickfire.robotParts.arms.ArmPosition;

// package-private
public class ActionsWater extends BaseAction {

    /**
     * Creates an ActionsWater Object
     * @param robot The robot
     */
    public ActionsWater(Robot robot) {
        super(robot);
    }

    public void driveToBottles () {
        robot.travel(-3, 200);
        robot.turn(45, 150);
        robot.lineFollowing(300);
        robot.travel(-2.5);
        robot.turn(90);
        robot.lineFollowing(300);
        robot.travel(20, 100);
        robot.arm().move(ArmPosition.CLAW_OPEN);
        robot.travel(-10,20);
        robot.turn(-90);
    }

    public void collectBottles() {
        robot.travel(-40,40);
        robot.travel(-40, 120);
        robot.arm().move(ArmPosition.DISCARD_EXTRA_BOTTLE);
        robot.arm().move(ArmPosition.MAIN);
    }

    public void deliverWater(boolean forward) {
        if (forward == true) {
            robot.motorLeft().rotate(-460);
            robot.travel(-2);
            robot.arm().moveArmTo(65);
            robot.travel(8.2);
            robot.arm().closeClawTo(8);
            robot.travel(-3.2);
            robot.arm().moveArmTo(-210);
            robot.travel(-12);
            robot.arm().closeClawTo(80);
        } else {}
    }

    public void skip(boolean forward) {
        if (forward == true) {

        } else {}

    }
}