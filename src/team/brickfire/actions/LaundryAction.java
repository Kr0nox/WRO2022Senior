package team.brickfire.actions;

import team.brickfire.data.color.Color;
import team.brickfire.data.color.ColorMap;
import team.brickfire.data.color.LaundryBasketColorMap;
import team.brickfire.data.color.MultiColor;
import team.brickfire.robot_parts.arms.WaterBottleArm;

import java.util.*;

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
    private final List<MultiColor> blockScans;
    private final Color[] baskets;
    private final ColorMap colorMap;

    private Queue<Color> blocks;

    /**
     * <p>Creates a new LaundryBlockAction</p>
     */
    private LaundryAction() {
        super();
        this.blockScans = new ArrayList<>();
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
        MultiColor color = colorSensorBlocks.multiColor(10);
        blockScans.add(color);
        System.out.println("Laundry block: " + color.getColor(colorMap));
        return color.getColor(colorMap) != Color.NONE_MATCHING && color.getColor(colorMap) != Color.NO_COLOR;
    }

    /**
     * <p>Puts the laundry blocks into the correct baskets</p>
     * <p>Starting position is {@link team.brickfire.actions.circuit_drive.CircuitPosition South-West}
     * facing {@link team.brickfire.actions.circuit_drive.CircuitOrientation South}</p>
     * @return The laundry basket the robot finishes this method in front of (0 = west; 1 = center; 2 = east)
     */
    public int deliverBlocks() {
        setDrivingSpeed(100,200);
        blocks = evaluateLaundryBlocks();
        System.out.println("Blocks stored: " + blocks);

        Color[] allColors = new Color[]{Color.YELLOW, Color.RED, Color.BLACK};
        int currentBasket = 0;

        // Scan baskets
        for (int i = 0; i < baskets.length - 1; i++) {
            baskets[i] = colorSensorBaskets.multiColor(10).getColor(colorMap);
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
        for (Color allColor : allColors) {
            if (allColor != Color.NO_COLOR) {
                baskets[baskets.length - 1] = allColor;
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


    public Queue<Color> evaluateLaundryBlocks() {
        Color[] allColors = new Color[]{Color.YELLOW, Color.RED, Color.BLACK};
        List<Color> foundColors = new ArrayList<>();

        for (MultiColor color : blockScans) {
            Color c = color.getColor(colorMap);
            if (c != Color.NONE_MATCHING && c != Color.NO_COLOR) {
                foundColors.add(c);
            }
        }
        if (validList(foundColors, allColors)) {
            return new LinkedList<>(foundColors);
        }

        foundColors = new ArrayList<>();
        for (MultiColor color : blockScans) {
            foundColors.add(Color.NONE_MATCHING);
        }

        double[][] percentages = new double[allColors.length][blockScans.size()];
        for (int i = 0; i < allColors.length; i++) {
            for (int j = 0; j < blockScans.size(); j++) {
                percentages[i][j] = blockScans.get(j).colorPercent(colorMap, allColors[i]);
            }
        }

        System.out.println("here");
        for (int i = 0; i < allColors.length; i++) {
            double max = -1;
            int maxIndex = -1;
            do {
                if (maxIndex != -1) {
                    percentages[i][maxIndex] = -1;
                }
                maxIndex = -1;
                max = -1;
                for (int j = 0; j < blockScans.size(); j++) {
                    if (percentages[i][j] > max) {
                        max = percentages[i][j];
                        maxIndex = j;
                    }
                }
            } while (foundColors.get(maxIndex) != Color.NONE_MATCHING);

            foundColors.set(maxIndex, allColors[i]);
        }

        List<Color> returnQ = new ArrayList<>();
        for (Color c : foundColors) {
            if (c != Color.NONE_MATCHING && c != Color.NO_COLOR) {
                returnQ.add(c);
            }
        }
        if (validList(returnQ, allColors)) {
            return new LinkedList<>(returnQ);
        }

        returnQ.clear();

        for (MultiColor c : blockScans) {
            Color color = c.getColor(colorMap);
            if (color != Color.NONE_MATCHING && color != Color.NO_COLOR) {
                returnQ.add(color);
            }
        }
        return new LinkedList<>(returnQ);
    }

    private boolean validList(List<Color> list, Color[] allColors) {
        if (list.size() != allColors.length) {
            return false;
        }
        boolean error = false;
        for (Color color : allColors) {
            if (!list.contains(color)) {
                error = true;
                break;
            }
        }
        return !error;
    }

    public void addBlockScan(MultiColor color) {
        blockScans.add(color);
    }
}
