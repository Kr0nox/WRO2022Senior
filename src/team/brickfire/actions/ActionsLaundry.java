package team.brickfire.actions;

import lejos.robotics.Color;
import team.brickfire.actions.dataTypes.BasketPosition;
import team.brickfire.challengeParts.LaundryBasket;
import team.brickfire.robotParts.Robot;

import java.util.HashMap;
import java.util.Map;

// package-private
public class ActionsLaundry extends BaseAction {

    private final Map<LaundryBasket, BasketPosition> laundryBaskets;
    private static final LaundryBasket[] POSSIBLE_BASKETS = new LaundryBasket[]{new LaundryBasket(Color.BLACK),
            new LaundryBasket(Color.YELLOW), new LaundryBasket(Color.RED)};
    private BasketPosition position;

    /**
     * Creates an ActionsLaundry Object
     *
     * @param robot The robot
     */
    public ActionsLaundry(Robot robot) {
        super(robot);
        laundryBaskets = new HashMap<>();
    }

    public void collectBlock(boolean forward) {
        robot.arm().moveArmTo(50);
        robot.arm().closeClawTo(90);
        robot.travel(-4.2);
        robot.scanner().getColorID();
        robot.travel(-0.8);
        robot.arm().closeClawTo(40);
        robot.arm().moveArmTo(35);
        robot.arm().closeClawTo(-80);
        robot.travel(-5);
    }

}
