package team.brickfire.actions;

import lejos.robotics.Color;
import team.brickfire.robotParts.Robot;

import java.util.*;

// package-private
public class ActionsLaundry extends BaseAction {

    private final Stack<Integer> blocks;
    private final int[] baskets;

    private int lastColor;
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
        baskets = new int[]{-2, -2, -2} ;

        lastColor = Color.NONE;
    }

    public ActionsLaundry(Robot robot, int i1, int i2, int i3) {
        this(robot);

        blocks.push(i1);
        blocks.push(i2);
        blocks.push(i3);
    }

    public void collectBlock() {
        int c = robot.scanner().laundryColor();
            blocks.push(c);
            robot.armConstruct().moveTransportBlock();

        lastColor = c;
        System.out.println(blocks);
    }


    public int getLastBlockColor() {
       return lastColor;
    }

    public int amountCollectedBlocks() {
        return blocks.size();
    }

    /**
     * Put Laundry blocks into their baskets
     */
    public void deliverLaundry() {
        robot.setLinearSpeed(90);
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
        // Deliver remaining blocks
        while (!blocks.empty()) {
            if (blocks.peek() == Color.NONE) {
                blocks.pop();
            }
            travelToBasket(findBasket(blocks.peek()));
            dropBlock();
        }

        // end over last basket
        travelToBasket(2);
    }

    private int findBasket(int c) {
        for (int i = 0; i < baskets.length; i++) {
            if (c == baskets[i]) {
                return i;
            }
        }
        for (int i = 0; i < baskets.length; i++) {
            if (baskets[i] >= 0) {
                return i;
            }
        }

        return -1;
    }

    private void dropBlock() {
        if (position >= 0) {
            baskets[position] = -2;
        }
        robot.armConstruct().drop();
        blocks.pop();
    }

    private void travelToBasket(int i) {
        robot.travel((i - position) * BASKET_DISTANCE);
        position = i;
    }


}
