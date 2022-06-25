package team.brickfire.actions;

import lejos.hardware.Sound;
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

        robot.travel(-4);
        robot.turn(45);
        robot.curveLeft(3.5, 1000);
        robot.turn(-3);

        new ActionsSide(robot, laundry, water).doSide();

        switchSides();

        new ActionsSide(robot, laundry, water).doSide();


        sideToLaundry();
        Sound.beep();
        laundry.deliverLaundry();

        // deliver laundry
    }

    public void waterToSide() {
        robot.curveLeft(3.9, 1000);
        robot.turn(-32);
    }

    public void switchSides() {
        robot.alignLine(false, 50);
        robot.travel(2);
        robot.turn(90);
        robot.alignLine(true,30);
        robot.curveLeft(3.6, 1000);
        Sound.beep();
        robot.turn(15);
        robot.curveLeft(2.9, 1000);
        robot.turn(-15);
    }

    public void sideToLaundry() {
        robot.alignLine(false, 50);
        robot.travel(2);
        robot.turn(90);
        robot.alignLine(true,30);
        robot.setLinearSpeed(90);
        robot.travel(20);
        robot.turn(90);
        robot.setLinearSpeed(110);
        robot.travel(65);
        robot.setLinearSpeed(40);
        robot.travel(5);
        robot.setLinearSpeed(110);
        robot.travel(-15);
        robot.turnLeft(455, 800);
        robot.alignLine(true,30);
        robot.travel(-39);
        robot.turn(3);
        /*
        robot.driveTillLine(false, 40);
        robot.travel(2);
        robot.turn(90);
        robot.setLinearSpeed(110);
        robot.driveTillLine(false, 40);
        robot.turn(90);
        robot.driveTillLine(false, 40);
        // robot.travel(-42.5);
        robot.turnLeft(-380, 800);
        robot.driveToWashingArea();*/
    }

    public void laundryToCenter() {
        robot.travel(-10);
        robot.turn(41);
    }
}