package team.brickfire.actions;

import team.brickfire.challengeParts.LaundryBasket;
import team.brickfire.robotParts.Robot;

public class ActionsLaundry extends BaseAction {

    private final LaundryBasket[] laundryBaskets;

    /**
     * Creates an ActionsLaundry Object
     *
     * @param robot The robot
     */
    public ActionsLaundry(Robot robot) {
        super(robot);
        laundryBaskets = new LaundryBasket[3];
    }
}
