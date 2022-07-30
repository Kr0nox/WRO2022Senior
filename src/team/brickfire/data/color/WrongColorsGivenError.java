package team.brickfire.data.color;

/**
 * <p>Error for when a ColorMap was not configured correctly</p>
 *
 * @version 1.0
 * @author Team BrickFire
 */
public class WrongColorsGivenError extends Error {

    /**
     * <p>Creates a new WrongColorsGivenException</p>
     * @param message Message to display
     */
    public WrongColorsGivenError(String message) {
        super(message);
    }
}
