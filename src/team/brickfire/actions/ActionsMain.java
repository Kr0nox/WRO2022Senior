package team.brickfire.actions;

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

    /**
     * Creates an ActionsMain Object
     * @param robot The robot
     */
    public ActionsMain(Robot robot) {
        super(robot);
        this.circuit = new Circuit(robot);
        this.laundry = new ActionsLaundry(robot);
    }

    /**
     * Executes all the tasks
     */
    public void execute() {
        collectWater();
        firstSide();
        secondSide();
        driveHome();
    }

    // Pink
    private void collectWater() {

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
