package team.brickfire.robotParts.sensors;

import lejos.hardware.sensor.EV3ColorSensor;

/**
 * Implementation of an EV3ColorSensor
 * @version 1.0
 * @author Team Brickfire
 */
public class ColorSensor extends Sensor<EV3ColorSensor> {

    /**
     * Creates an EV3 color sensor
     * @param port Port the sensor is plugged into
     */
    public ColorSensor(EV3ColorSensor port) {
        super(port);
    }

    /**
     * Returns the ID of the color seen
     * @return The seen color
     */
    public int getColorID() {
        return sensor.getColorID();
    }

    /**
     * Returns the reflected light value in red mode
     * @return The reflected light
     */
    public float getReflectedLight() {
        float[] value = {0};
        sensor.getRedMode().fetchSample(value, 0);
        return value[0];
    }

    /**
     * Conpares the seen color to the given
     * @param colorID Color to compare to
     * @return Whether colors are equal
     */
    public boolean isColor(int colorID) {
        return getColorID() == colorID;
    }

}
