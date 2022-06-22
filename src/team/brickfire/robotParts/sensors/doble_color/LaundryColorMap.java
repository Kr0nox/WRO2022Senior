package team.brickfire.robotParts.sensors.doble_color;

import lejos.robotics.Color;

public class LaundryColorMap extends ColorMap {
    protected LaundryColorMap() {
        super(mapOfValues(Color.NONE, Color.NONE,
                Color.RED, Color.RED,
                Color.GREEN, Color.BLACK,
                Color.BLUE, Color.BLACK,
                Color.YELLOW, Color.YELLOW,
                Color.MAGENTA, Color.RED,
                Color.ORANGE, Color.YELLOW,
                Color.WHITE, Color.YELLOW,
                Color.BLACK, Color.BLACK,
                Color.PINK, Color.RED,
                Color.GRAY, Color.BLACK,
                Color.LIGHT_GRAY, Color.YELLOW,
                Color.DARK_GRAY, Color.BLACK,
                Color.CYAN, Color.BLACK,
                Color.BROWN, Color.YELLOW),

                mapOfValues(Color.NONE, 2,
                        Color.RED, 5,
                        Color.YELLOW, 7,
                        Color.BLACK, 1)
        );
    }
}
