package team.brickfire.actions;

import team.brickfire.robotParts.Robot;

public class ActionsSide extends BaseAction {

    private final ActionsLaundry laundry;
    private final ActionsWater water;

    public ActionsSide(Robot robot, ActionsLaundry laundry, ActionsWater water) {
        super(robot);
        this.laundry = laundry;
        this.water = water;
    }

    public void doSide() {
        //align for first room
        robot.alignLine(true, 20);
        robot.setLinearSpeed(110);
        robot.travel(-11);
        // TODO: 2.1) turn, dTL, aL(false) --> turn, aL(true)
        robot.turn(-90);
        robot.alignLine(true, 30);

        //do first room
        new ActionsRoom(robot, laundry, water).doRoom(true);

        //align for second room
        robot.alignLine(false,35);

        new ActionsRoom(robot, laundry, water).doRoom(false);


    }
}
