package team.brickfire.actions;

import team.brickfire.data.color.Color;
import team.brickfire.data.color.ColorMap;
import team.brickfire.data.color.LaundryBasketColorMap;
import team.brickfire.data.color.AdvancedColor;
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
    private List<AdvancedColor> blockScans;
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
     *
     * @param prev The before already entered scan
     */
    public void enterScan(AdvancedColor prev) {
        prev.setErrorValues(colorSensorBlocks, colorMap);
        blockScans.add(prev);
        System.out.println("Laundry block: " + prev.getColor());
    }

    /**
     * <p>Puts the laundry blocks into the correct baskets</p>
     * <p>Starting position is in front of the first east basket</p>
     * @return The laundry basket the robot finishes this method in front of (0 = west; 1 = center; 2 = east)
     */
    public int deliverBlocks() {
        setDrivingSpeed(100, 200);
        blocks = evaluateLaundryBlocks();
        System.out.println("Blocks stored: " + blocks);

        Color[] allColors = new Color[]{Color.YELLOW, Color.RED, Color.BLACK};
        int currentBasket = 0;

        // Scan baskets
        for (int i = 0; i < baskets.length - 1; i++) {
            baskets[i] = colorSensorBaskets.getColor(colorMap, 10);
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


    private Queue<Color> evaluateLaundryBlocks() {
        System.out.println("\n Pre eval: " + blockScans);
        Color[] allColors = new Color[]{Color.YELLOW, Color.RED, Color.BLACK};
        List<Color> foundColors = new ArrayList<>();

        // if by any chance we actually only scanned 3 blocks and the last was something different
        for (AdvancedColor color : blockScans) {
            Color c = color.getColor();
            if (c != Color.NONE_MATCHING && c != Color.NO_COLOR) {
                foundColors.add(c);
            }
        }
        if (validList(foundColors, allColors)) {
            return new LinkedList<>(foundColors);
        }
        System.out.println("part 2");

        // Throw out blocks with the smallest error, till only one block remains
        // The Smallest error ==> Most likely to have seen the floor in both scans
        ArrayList<AdvancedColor> nonErrorColors;
        int blackCount, redCount, yellowCount;
        do {
            double minError = blockScans.get(0).error();
            double minErrorIndex = 0;
            for (int i = 1; i < blockScans.size(); i++) {
                if (blockScans.get(i).error() < minError) {
                    minError = blockScans.get(i).error();
                    minErrorIndex = i;
                }
            }

            nonErrorColors = new ArrayList<>();
            blackCount = 0;
            redCount = 0;
            yellowCount = 0;
            for (int i = 0; i < blockScans.size(); i++) {
                if (i != minErrorIndex) {
                    nonErrorColors.add(blockScans.get(i));
                    if (blockScans.get(i).getColor() == Color.BLACK) {
                        blackCount++;
                    }
                    if (blockScans.get(i).getColor() == Color.RED) {
                        redCount++;
                    }
                    if (blockScans.get(i).getColor() == Color.YELLOW) {
                        yellowCount++;
                    }
                }
            }
            blockScans = new ArrayList<>(nonErrorColors);
            System.out.println("nonErrorColors: " + nonErrorColors);
            System.out.println("blocksacna: " + blockScans);
            System.out.println(blackCount + " " + redCount + " " + yellowCount);
        } while (blackCount > 1 || redCount > 1 || yellowCount > 1);

        foundColors = new ArrayList<>();
        System.out.println("adter loop: " + nonErrorColors);
        for (AdvancedColor nonErrorColor : nonErrorColors) {
            foundColors.add(nonErrorColor.getColor());
        }

        return new LinkedList<>(foundColors);

    }

    private boolean validList(List<Color> list, Color[] allColors) {
        System.out.println(list.size() + " " + allColors.length);
        if (list.size() > allColors.length) {
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
}
