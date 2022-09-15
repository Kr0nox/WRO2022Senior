package team.brickfire.data.color;

import team.brickfire.robot_parts.sensors.ColorSensor;

import java.util.Arrays;

public class AdvancedColor {

    private final Color color;
    private final float reflect;
    private final float[] rgb;

    private Color errorColor;
    private float errorReflect;
    private float[] errorRGB;

    public AdvancedColor(ColorSensor sensor, ColorMap map) {
        this.color = sensor.getColor(map, 10);
        reflect = sensor.getReflectedLight();
        rgb = sensor.getRGB();
    }

    public void getErrorValues(ColorSensor sensor, ColorMap map) {
        errorColor = sensor.getColor(map, 10);
        errorReflect = sensor.getReflectedLight();
        errorRGB = sensor.getRGB();
    }

    public Color getErrorColor() {
        return errorColor;
    }

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
}
