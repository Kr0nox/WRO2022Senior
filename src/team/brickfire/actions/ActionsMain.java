package team.brickfire.actions;

import lejos.utility.Delay;
import team.brickfire.robotParts.Robot;

/**
 * The "main" part of your actions or challenges. All coordination of the robot happens in here
 * @version 2.0
 * @author Team BrickFire
 */
public class ActionsMain extends BaseAction {

    private final ActionsLaundry laundry;
    private final ActionsWater water;

    /**
     * Creates an ActionsMain Object
     * @param robot The robot
     */
    public ActionsMain(Robot robot) {
        super(robot);
        this.laundry = new ActionsLaundry(robot);
        this.water = new ActionsWater(robot, laundry);
    }

    /**
     * Executes all the tasks
     */
    public void execute() {
        Delay.msDelay(250);

        water.collectBottles();

        waterToSide();

        new ActionsSide(robot, laundry, water).doSide();

        switchSides();

        new ActionsSide(robot, laundry, water).doSide();


        sideToLaundry();

        // deliver laundry
    }

    public void waterToSide() {
        robot.curveLeft(3.9, 1000);
        robot.turn(-32);
    }

    public void switchSides() {
        robot.driveTillLine(false, 50);
        robot.travel(4);
        robot.turn(-90);
        robot.alignLine(true,30);
        // TODO: 2.3) one large travel?
        robot.curveLeft(3.6, 1000);
        robot.turn(17);
        robot.curveLeft(3, 1000);
        robot.turn(-17);
    }

    public void sideToLaundry() {
        robot.driveTillLine(false, 40);
        robot.turn(90);
        robot.setLinearSpeed(110);
        robot.travel(-25);
        robot.turn(75);
        robot.travel(-42.5);
        robot.turnLeft(-380, 800);
        // TODO: 1.1) finish this
    }

    public void laundryToCenter() {
        robot.travel(-10);
        robot.turn(41);
    }
}