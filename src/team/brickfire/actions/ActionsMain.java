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
        ActionsRoom test = new ActionsRoom(robot, laundry, water);
        test.doRoom(true);

        /*
        collectWater();
        firstSide();
        secondSide();
        driveHome();
        */
    }

    // Pink
    private void collectWater() {
        ActionsWater water = new ActionsWater(robot);
        water.collectBottles();
    }

    // Blue
    private void firstSide() {

    }

    // Green
    private void secondSide() {

    }

    // Red
    private void driveHome() {

    }
}
