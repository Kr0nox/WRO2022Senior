package team.brickfire.data.color;

/**
 * <p>ColorMap for scanning laundry blocks and laundry baskets</p>
 *
 * @version 2.0
 * @author Team BrickFire
 */
public class LaundryBlockColorMap extends ColorMap {

    /**
     * <p>Creates a LaundryColorMap</p>
     */
    public LaundryBlockColorMap() {
        super();
        valueMapping.put(Color.NONE_MATCHING, Color.NONE_MATCHING);
        valueMapping.put(Color.NO_COLOR, Color.NO_COLOR);
        valueMapping.put(Color.BLACK, Color.BLACK);
        valueMapping.put(Color.WHITE, Color.NONE_MATCHING);
        valueMapping.put(Color.RED, Color.RED);
        valueMapping.put(Color.GREEN, Color.NONE_MATCHING);
        valueMapping.put(Color.BLUE, Color.NO_COLOR);
        valueMapping.put(Color.YELLOW, Color.YELLOW);
        valueMapping.put(Color.BROWN, Color.YELLOW);

        priorities.put(Color.NONE_MATCHING, 1);
        priorities.put(Color.NO_COLOR, 3);
        priorities.put(Color.BLACK, 5);
        priorities.put(Color.WHITE, 0);
        priorities.put(Color.RED, 4);
        priorities.put(Color.GREEN, 0);
        priorities.put(Color.BLUE, 0);
        priorities.put(Color.YELLOW, 7);
        priorities.put(Color.BROWN, 2);
    }
}
