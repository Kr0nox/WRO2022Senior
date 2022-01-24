package team.brickfire.actions;

import team.brickfire.robotParts.Robot;

public class ActionsSide extends BaseAction {

    public ActionsSide(Robot robot) {
        super(robot);
    }

    public void doSide() {
        robot.lineFollowing();
        robot.travel();
        robot.turn();
        ActionsRoom room = new ActionsRoom(robot);
        room.doRoom();
        robot.turn();
        ActionsRoom room1 = new ActionsRoom(robot);
        room1.doRoom();
        robot.lineFollowing();
    }
}
