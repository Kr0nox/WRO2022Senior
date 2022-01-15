package team.brickfire.actions;

import team.brickfire.robotParts.Robot;

/**
 * The "main" part of your actions or challenges. All coordination of the robot happens in here
 * @version 2.0
 * @author Team BrickFire
 */
public class ActionsMain extends BaseAction {

    private final ActionsGame actionGame;
    private final ActionsLaundry actionLaundry;
    private final ActionsRoom actionRoom;
    private final ActionsWater actionWater;
    private final Circuit circuit;

    /**
     * Creates an ActionsMain Object
     * @param robot The robot
     */
    public ActionsMain(Robot robot) {
        super(robot);
        this.actionGame = new ActionsGame(robot);
        this.actionLaundry = new ActionsLaundry(robot);
        this.actionRoom = new ActionsRoom(robot);
        this.actionWater = new ActionsWater(robot);
        this.circuit = new Circuit(robot);
    }

    /**
     * Executes all the tasks
     */
    public void execute() {

    }
}
