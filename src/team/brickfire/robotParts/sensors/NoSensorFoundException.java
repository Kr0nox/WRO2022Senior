package team.brickfire.robotParts.sensors;

/**
 * <p>Exception for when no sensor was found in that port or a different sensor was found</p>
 *
 * @version 1.0
 * @author Team BrickFire
 */
public class NoSensorFoundException extends RuntimeException {

    public NoSensorFoundException(String message) {
        super(message);
    }

}
