package team.brickfire.actions;

import lejos.utility.Delay;
import team.brickfire.robotParts.Robot;

import java.util.*;

// package-private
public class ActionsLaundry extends BaseAction {

    private final Stack<Integer> blocks;
    private final int[] baskets;
    private static final double BASKET_DISTANCE = -10.8;

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

    public ActionsLaundry(Robot robot, int i1, int i2, int i3) {
        this(robot);

        blocks.push(i1);
        blocks.push(i2);
        blocks.push(i3);
    }

    public void collectBlock(boolean mirrored) {
        robot.travel(mirrored ? -8.8 : -8.8);
        blocks.push(robot.scanner().laundryColor());
        System.out.println(robot.scanner().laundryColor());
        robot.travel(-1.5);
        robot.armConstruct().moveTransportBlock();
        Delay.msDelay(2000);
        //robot.armConstruct().pickUp();
    }

    /**
     * Put Laundry blocks into their baskets
     */
    public void deliverLaundry() {
        //robot.travel(-5);
        // special turn
        // drive over Baskets and scan them

        for (int i = 0; i < 3; i++) {
            baskets[i] = robot.scanner().laundryColor();
            // if the basket corresponds to the lowest Block: Drop it
            if (baskets[i] == blocks.peek()) {
                dropBlock();
            }
            if (i < 2) {
                robot.travel(BASKET_DISTANCE);
                position = i + 1;
            }
        }
        System.out.println("Blcoks: " + blocks.toString());

        System.out.println(Arrays.toString(baskets));
        // Deliver remaining blocks
        while (!blocks.empty()) {
            travelToBasket(findBasket(blocks.peek()));
            dropBlock();
        }

        // end over last basket
        travelToBasket(2);
    }

    private int findBasket(int c) {
        for (int i = 0; i < baskets.length; i++) {
            if (c == baskets[i]) {
                System.out.println("Find: " + i);
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
        System.out.println("Travel to: " + i);
        robot.travel((i - position) * BASKET_DISTANCE);
        position = i;
    }


}
