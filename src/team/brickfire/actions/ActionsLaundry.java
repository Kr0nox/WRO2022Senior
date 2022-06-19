package team.brickfire.actions;

import lejos.robotics.Color;
import lejos.utility.Delay;
import team.brickfire.actions.dataTypes.BasketPosition;
import team.brickfire.challengeParts.LaundryBasket;
import team.brickfire.robotParts.Robot;

import java.util.*;

// package-private
public class ActionsLaundry extends BaseAction {

    private static final LaundryBasket[] POSSIBLE_BASKETS = new LaundryBasket[]{new LaundryBasket(Color.BLACK),
            new LaundryBasket(Color.YELLOW), new LaundryBasket(Color.RED)};
    private BasketPosition position;

    private Stack<Integer> blocks;
    private List<Integer> baskets;
    private static final int basketDistance = -4;

    /**
     * Creates an ActionsLaundry Object
     *
     * @param robot The robot
     */
    public ActionsLaundry(Robot robot) {
        super(robot);
        blocks = new Stack<>();
        baskets = new ArrayList();
    }

    public void collectBlock(boolean mirrored) {
        if (mirrored == true) {
            robot.travel(-13);
            robot.scanner().getColorID();
            Delay.msDelay(1000);
            //pick up laundry block
        } else {
        }
    }

    public void deliverLaundry() {
        robot.travel(-5);
        //special turn
        //drive to first basket
        for (int i = 0; i < 3;) {
            baskets.add(robot.scanner().getColorID());
            if (baskets.get(baskets.size() - 1).equals(blocks.peek())) {
                //drop blocks
                blocks.pop();
            }
            robot.travel(basketDistance);
        }
    }


}
