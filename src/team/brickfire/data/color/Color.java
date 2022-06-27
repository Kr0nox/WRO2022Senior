package team.brickfire.data.color;

/**
 * <p>Representation of Colors the EV3-Color-Sensor can see</p>
 *
 * @version 1.0
 * @author Team BrickFire
 */
public enum Color {

    NONE_MATCHING(-2),
    NO_COLOR(lejos.robotics.Color.NONE),
    BLACK(lejos.robotics.Color.BLACK),
    WHITE(lejos.robotics.Color.WHITE),
    RED(lejos.robotics.Color.RED),
    GREEN(lejos.robotics.Color.GREEN),
    BLUE(lejos.robotics.Color.BLUE),
    YELLOW(lejos.robotics.Color.YELLOW),
    BROWN(lejos.robotics.Color.BROWN);

    private final int lejosColorID;

    Color(int lejosColorID) {
        this.lejosColorID = lejosColorID;
    }

    /**
     * Returns the custom Color for a given id from lejos.robotics.Color
     *
     * @param id LeJOS color id
     * @return The corresponding color
     */
    public static Color fromLeJOSID(int id) {
        for (Color v : Color.values()) {
            if (v.lejosColorID == id) {
                return v;
            }
        }
        return Color.NO_COLOR;
    }
}
