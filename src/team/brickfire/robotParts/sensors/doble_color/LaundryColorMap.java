package team.brickfire.robotParts.sensors.doble_color;

import lejos.robotics.Color;

import java.util.HashMap;

public class LaundryColorMap extends ColorMap {
    protected LaundryColorMap() {
        HashMap<Integer, Integer> valueMapping = new HashMap<>();
        HashMap<Integer, Integer> priorities = new HashMap<>();

        // -1
        valueMapping.put(Color.NONE, Color.NONE);
        priorities.put(Color.NONE, 1);
        // 0
        valueMapping.put(Color.RED, Color.);
        priorities.put(Color.RED, );
        // 1
        valueMapping.put(Color.GREEN, Color.);
        priorities.put(Color.GREEN, );
        // 2
        valueMapping.put(Color.BLUE, Color.);
        priorities.put(Color.BLUE, );
        // 3
        valueMapping.put(Color.YELLOW, Color.);
        priorities.put(Color.YELLOW, );
        // 4
        valueMapping.put(Color.MAGENTA, Color.);
        priorities.put(Color.MAGENTA, );
        // 5
        valueMapping.put(Color., Color.);
        priorities.put(Color., );
        // 6
        valueMapping.put(Color., Color.);
        priorities.put(Color., );
        // 7
        valueMapping.put(Color., Color.);
        priorities.put(Color., );
        // 8
        valueMapping.put(Color., Color.);
        priorities.put(Color., );
        // 9
        valueMapping.put(Color., Color.);
        priorities.put(Color., );
        // 10
        valueMapping.put(Color., Color.);
        priorities.put(Color., );
        // 11
        valueMapping.put(Color., Color.);
        priorities.put(Color., );
        // 12
        valueMapping.put(Color., Color.);
        priorities.put(Color., );
        // 13
        valueMapping.put(Color., Color.);
        priorities.put(Color., );


        super(valueMapping, priorities);
    }
}
