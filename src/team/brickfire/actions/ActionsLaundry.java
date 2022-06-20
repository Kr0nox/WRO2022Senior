package team.brickfire.actions;

import lejos.utility.Delay;
import team.brickfire.robotParts.ColorPool;
import team.brickfire.robotParts.Robot;

import java.util.*;

// package-private
public class ActionsLaundry extends BaseAction {

    private final Stack<Integer> blocks;
    private final int[] baskets;
    private static final int BASKET_DISTANCE = -4;

    private int position;

    /**
     * Creates an ActionsLaundry Object
     *
     * @param robot The robot
     */
    public ActionsLaundry(Robot robot) {
        super(robot);
        blocks = new Stack<>();
        baskets = new int[3];
    }

    public void collectBlock(boolean mirrored) {
        robot.travel(-13);
        blocks.push(scanColor());
        Delay.msDelay(1000);
        //pick up laundry block
    }

    public void deliverLaundry() {
        robot.travel(-5);
        // special turn
        // drive over Baskets and scan them

        for (int i = 0; i < 3; i++) {
            baskets[i] = scanColor();
            // if the basket corresponds to the lowest Block: Drop it
            if (baskets[i] == blocks.peek()) {
                dropBlock();
            }
            if (i < 2) {
                robot.travel(BASKET_DISTANCE);
                position = i + 1;
            }
        }
        // Deliver remaining blocks
        for (int i = 0; i < blocks.size(); i++) {
            travelToBasket(findBasket(blocks.peek()));
        }

        // end over last basket
        travelToBasket(2);
    }

    private int scanColor() {
        return robot.getColor(ColorPool.LAUNDRY);
    }

    private int findBasket(int c) {
        for (int i = 0; i < baskets.length; i++) {
            if (c == baskets[i]) {
                return i;
            }
        }
        return -1;
    }

    private void dropBlock() {
        robot.armConstruct().drop();
        blocks.pop();
    }

    private void travelToBasket(int i) {
        robot.travel((i - position) * BASKET_DISTANCE);
    }


}
