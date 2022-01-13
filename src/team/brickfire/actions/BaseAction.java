package team.brickfire.actions;

import team.brickfire.robotParts.Robot;

/**
 * Template for all actions and challenges
 * @version 1.0
 * @author Team BrickFire
 */
public abstract class BaseAction {

    /**
     * So every action has access to the robot
     */
    protected Robot robot;

    /**
     * Stores the robot in the local variable
     * @param robot The robot class why is representing the robot
     */
    public BaseAction(Robot robot) {
        this.robot = robot;
    }

}
