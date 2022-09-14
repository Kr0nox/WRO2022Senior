package team.brickfire.robot_parts.sensors;

import lejos.hardware.port.Port;
import lejos.hardware.sensor.EV3ColorSensor;
import team.brickfire.data.color.Color;
import team.brickfire.data.color.ColorMap;
import team.brickfire.data.color.MultiColor;

import java.util.Arrays;

/**
 * <p>Implementation of an {@link lejos.hardware.sensor.EV3ColorSensor EV3ColorSensor}</p>
 * <p><i>Follows a variation of the Singleton-Pattern</i></p>
 * @version 2.0
 * @author Team Brickfire
 */
public class ColorSensor extends Sensor<EV3ColorSensor> {

    /**
     * Creates an EV3 color sensor
     * @param port Port the sensor is plugged into
     */
    protected ColorSensor(Port port) {
        super(new EV3ColorSensor(port));
        sensor.setCurrentMode(0);
    }

    /**
     * <p>Returns the color sensor from the given port</p>
     *
     * @param port Port the sensor is plugged into (1-4)
     * @return The color sensor
     */
    public static ColorSensor get(int port) {
        if (portsUsed[port - 1] == null) {
            try {
                portsUsed[port - 1] = new ColorSensor(ports[port - 1]);
            } catch (Exception e) {
                throw new NoSensorFoundException(e.getClass().getName() + " at port " + port + ": " + e.getMessage());
            }
        }
        if (portsUsed[port - 1] instanceof ColorSensor) {
            return (ColorSensor) portsUsed[port - 1];
        }
        throw new NoSensorFoundException("No ColorSensor connected to port " + port);
    }

    /**
     * Returns the currently seen {@link Color color}
     *
     * @return Seen color
     */
    public Color getColor() {
        sensor.setCurrentMode(0);
        return Color.fromLeJOSID(sensor.getColorID());
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
     * Compares the seen {@link Color color} to the given
     * @param colorID {@link Color Color} to compare to
     * @return Whether colors are equal
     */
    public boolean isColor(Color colorID) {
        return getColor() == colorID;
    }

    /**
     * <p>Gets the {@link Color color} n-times and then maps it according to the given {@link ColorMap map}</p>
     * @param colorMap Map to use
     * @param n Number of tries of getting color
     * @return The mapped color
     */
    public MultiColor multiColor(int n) {
        Color[] colors = new Color[n];
        for (int i = 0; i < colors.length; i++) {
            colors[i] = getColor();
        }

        return new MultiColor(colors);
    }
}
