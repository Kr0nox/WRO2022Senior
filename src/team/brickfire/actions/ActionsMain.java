package team.brickfire.actions;

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
        this.water = new ActionsWater(robot);
    }

    /**
     * Executes all the tasks
     */
    public void execute() {
        water.collectBottles();

        waterToSide();

        new ActionsSide(robot, laundry, water).doSide();
        robot.travel(-12);
        robot.turn(-90);

        //align for journey
        robot.alignLine(true,30);

        //journey
        /*robot.setLinearSpeed(60);
        robot.travel(70);
        robot.turn(12.5);
        robot.travel(65);
        robot.turn(-12);*/
        robot.curveLeft(3, 1000);
        robot.turn(4.5);
        robot.curveLeft(3.6, 1000);

        new ActionsSide(robot, laundry, water).doSide();
        robot.driveTillLine(false, 40);
        robot.turn(90);

        //drive to laundry
        sideToLaundry();

        //deliver laundry
    }


    public void waterToSide() {
        robot.curveLeft(3.9, 1000);
        robot.turn(-32);
    }

    public void sideToLaundry() {
        robot.travel(-20);
        robot.turn(75);
        robot.travel(-50);
        robot.turnLeft(-380, 800);
    }

    public void laundryToCenter() {
        robot.travel(-10);
        robot.turn(41);
    }
}