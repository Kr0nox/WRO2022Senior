package team.brickfire.actions;

import team.brickfire.robotParts.Robot;

// package-private
public class ActionsWater extends BaseAction {

    private boolean bothBottles;

    /**
     * Creates an ActionsWater Object
     * @param robot The robot
     */
    public ActionsWater(Robot robot) {
        super(robot);
    }

    public void collectBottles() {
        robot.turn(45);
        robot.travel(12);
        //pick up water bottle
        robot.travel(-3);
        robot.turn(90);
        robot.travel(8);

        bothBottles = true;
    }

    public void deliverWater(boolean mirrored) {
        robot.travel(bothBottles ? 1 : 2);
        robot.turn(mirrored ? -90 : 90);

        robot.travel(12);
        //lower water bottle
    }

}