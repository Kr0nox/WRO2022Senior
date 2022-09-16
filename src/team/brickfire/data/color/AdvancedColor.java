package team.brickfire.data.color;

import team.brickfire.robot_parts.sensors.ColorSensor;

/**
 * <p>Saves all readeble values from the color sensor twice and calculates the difference between them (error)</p>
 *
 * @author Team BrickFire
 * @version 1.0
 */
public class AdvancedColor {

    private final Color color;
    private final float reflect;
    private final float[] rgb;

    private Color errorColor;
    private float errorReflect;
    private float[] errorRGB;

    /**
     * <p>Scans the initial values</p>
     *
     * @param sensor Sensor to scan with
     * @param map ColorMap to map scanned color by
     */
    public AdvancedColor(ColorSensor sensor, ColorMap map) {
        this.color = sensor.getColor(map, 10);
        reflect = sensor.getReflectedLight();
        rgb = sensor.getRGB();
    }

    /**
     * <p>Gets the second set of values</p>
     *
     * @param sensor Sensor to scan with
     * @param map ColorMap to map scanned color by
     */
    public void setErrorValues(ColorSensor sensor, ColorMap map) {
        errorColor = sensor.getColor(map, 10);
        errorReflect = sensor.getReflectedLight();
        errorRGB = sensor.getRGB();
    }

    /**
     * <p>Returns the initially scanned color</p>
     *
     * @return Scanned color
     */
    public Color getColor() {
        return color;
    }

    /**
     * <p>Calculates the error</p>
     *
     * @return The error of the two scans
     */
    public double error() {
        if (color != errorColor) {
            return Double.MAX_VALUE;
        }

        double error = Math.abs(reflect - errorReflect);
        for (int i = 0; i < rgb.length; i++) {
            error += Math.abs(rgb[i] - errorRGB[i]);
        }
        return Math.abs(error);
    }

    @Override
    public String toString() {
        return color.name();
    }
}
