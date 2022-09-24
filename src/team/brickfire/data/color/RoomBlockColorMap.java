package team.brickfire.data.color;

/**
 * <p>ColorMap for scanning RoomBlocks</p>
 *
 * @version 2.0
 * @author Team BrickFire
 */
public class RoomBlockColorMap extends ColorMap {

    /**
     * <p>Creates a RoomBlockColorMap</p>
     */
    public RoomBlockColorMap() {
        super();
        valueMapping.put(Color.NONE_MATCHING, Color.NONE_MATCHING);
        valueMapping.put(Color.NO_COLOR, Color.NO_COLOR);
        valueMapping.put(Color.BLACK, Color.WHITE);
        valueMapping.put(Color.WHITE, Color.WHITE);
        valueMapping.put(Color.RED, Color.NONE_MATCHING);
        valueMapping.put(Color.GREEN, Color.GREEN);
        valueMapping.put(Color.BLUE, Color.WHITE);
        valueMapping.put(Color.YELLOW, Color.NONE_MATCHING);
        valueMapping.put(Color.BROWN, Color.NONE_MATCHING);

        priorities.put(Color.NONE_MATCHING, 1);
        priorities.put(Color.NO_COLOR, 3);
        priorities.put(Color.BLACK, 1);
        priorities.put(Color.WHITE, 10);
        priorities.put(Color.RED, 1);
        priorities.put(Color.GREEN, 5);
        priorities.put(Color.BLUE, 4);
        priorities.put(Color.YELLOW, 1);
        priorities.put(Color.BROWN, 1);
    }
}
