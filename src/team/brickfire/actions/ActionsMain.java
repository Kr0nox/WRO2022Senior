package team.brickfire.actions;

import team.brickfire.actions.dataTypes.CircuitPosition;
import team.brickfire.actions.dataTypes.Orientation;
import team.brickfire.robotParts.Robot;
import team.brickfire.robotParts.sensors.Sensor;

/**
 * The "main" part of your actions or challenges. All coordination of the robot happens in here
 * @version 2.0
 * @author Team BrickFire
 */
public class ActionsMain extends BaseAction {

    private final Circuit circuit;
    private final ActionsLaundry laundry;
    private final ActionsWater water;

    /**
     * Creates an ActionsMain Object
     * @param robot The robot
     */
    public ActionsMain(Robot robot) {
        super(robot);
        this.circuit = new Circuit(robot, CircuitPosition.MIDDLE, Orientation.NONE);
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

        //align for journey
        robot.alignLine(true,30);

        //journey
        robot.setLinearSpeed(60);
        robot.travel(70);
        robot.turn(12.5);
        robot.travel(65);
        robot.turn(-12);

        new ActionsSide(robot, laundry, water).doSide();

        /*
        collectWater();
        firstSide();
        secondSide();
        driveHome();
        */
    }


    public void waterToSide() {
        robot.travel(78);
        robot.turn(-35);
    }

}