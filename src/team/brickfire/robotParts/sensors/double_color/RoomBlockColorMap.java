package team.brickfire.robotParts.sensors.double_color;

import lejos.robotics.Color;

public class RoomBlockColorMap extends ColorMap {

    public RoomBlockColorMap() {
        super(mapOfValues(Color.NONE, Color.NONE,
                Color.RED, Color.WHITE,
                Color.GREEN, Color.GREEN,
                Color.BLUE, Color.WHITE,
                Color.YELLOW, Color.WHITE,
                Color.MAGENTA, Color.WHITE,
                Color.ORANGE, Color.WHITE,
                Color.WHITE, Color.WHITE,
                Color.BLACK, Color.NONE,
                Color.PINK, Color.WHITE,
                Color.GRAY, Color.GREEN,
                Color.LIGHT_GRAY, Color.WHITE,
                Color.DARK_GRAY, Color.GREEN,
                Color.CYAN, Color.GREEN,
                Color.BROWN, Color.GREEN),

                mapOfValues(Color.NONE, 2,
                        Color.GREEN, 10,
                        Color.WHITE, 5)
        );
    }

}
