package team.brickfire.actions;

import team.brickfire.data.color.Color;
import team.brickfire.data.color.ColorMap;
import team.brickfire.data.color.LaundryBasketColorMap;
import team.brickfire.robot_parts.arms.WaterBottleArm;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

/**
 * <p>Action for everything to do with laundry blocks</p>
 *
 * @version 2.0
 * @author Team BrickFire
 */
public final class LaundryAction extends BaseAction {

    // TODO: figure these two constants out
    private static final double BASKET_DISTANCE = 10.5;

    private static LaundryAction instance;
    private final Queue<Color> blocks;
    private final Color[] baskets;
    private final ColorMap colorMap;

    /**
     * <p>Creates a new LaundryBlockAction</p>
     */
    private LaundryAction() {
        super();
        this.blocks = new LinkedList<>();
        baskets = new Color[]{Color.NO_COLOR, Color.NO_COLOR, Color.NO_COLOR};
        colorMap = new LaundryBasketColorMap();
    }

    /**
     * <p>Returns the instance of this class</p>
     * <p><i>Singleton-Pattern</i></p>
     * @return The instance of LaundryBlockAction
     */
    public static LaundryAction getInstance() {
        if (instance == null) {
            instance = new LaundryAction();
        }
        return instance;
    }

    /**
     * <p>Scans the block under the sensor</p>
     * @return True if there was a block, false otherwise
     */
    public boolean scanBlock() {
        Color color = colorSensorBlocks.getMappedColor(colorMap, 10);
        blocks.add(color);
        System.out.println("Laundry block: " + color);
        return color != Color.NONE_MATCHING && color != Color.NO_COLOR;
    }

    /**
     * <p>Puts the laundry blocks into the correct baskets</p>
     * <p>Starting position is {@link team.brickfire.actions.circuit_drive.CircuitPosition South-West}
     * facing {@link team.brickfire.actions.circuit_drive.CircuitOrientation South}</p>
     * @return The laundry basket the robot finishes this method in front of (0 = west; 1 = center; 2 = east)
     */
    public int deliverBlocks() {
        setDrivingSpeed(100,200);

        System.out.println("Blocks stored: " + blocks);

        Color[] allColors = new Color[]{Color.YELLOW, Color.RED, Color.BLACK};
        int currentBasket = 0;

        // Scan baskets
        for (int i = 0; i < baskets.length - 1; i++) {
            baskets[i] = colorSensorBaskets.getMappedColor(colorMap, 10);
            for (int j = 0; j < allColors.length; j++) {
                if (baskets[i] == allColors[j]) {
                    allColors[j] = Color.NO_COLOR;
                }
            }

            if (blocks.peek() == baskets[i]) {
                dropOffBlock();
            }

            if (i < baskets.length - 2) {
                drive(BASKET_DISTANCE);
                currentBasket = i + 1;
            }
        }
        for (int j = 0; j < allColors.length; j++) {
            if (allColors[j] != Color.NO_COLOR) {
                baskets[baskets.length - 1] = allColors[j];
                break;
            }
        }
        System.out.println("Baskets scanned : " + Arrays.toString(baskets));
        // deliver remaining blocks
        while (!blocks.isEmpty()) {
            int dist = getDistanceToCorrectBasket(currentBasket);
            drive(BASKET_DISTANCE * dist);
            currentBasket += dist;
            dropOffBlock();
        }
        drive(BASKET_DISTANCE * (2 - currentBasket));
        currentBasket = 2;
        return currentBasket;
    }

    private void dropOffBlock() {
        waterBottleArm.move(WaterBottleArm.DROP_BLOCK);
        blocks.poll();
        waterBottleArm.move(WaterBottleArm.ZERO);
    }

    private int getDistanceToCorrectBasket(int currentBasket) {
        for (int i = 0; i < baskets.length; i++) {
            if (baskets[i] == blocks.peek()) {
                return i - currentBasket;
            }
        }
        return 0;
    }

    public void addBlock(Color color) {
        blocks.add(color);
    }
}
