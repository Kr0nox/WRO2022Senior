package team.brickfire.robotParts.sensors.doble_color;

import java.util.HashMap;
import java.util.TreeMap;

public abstract class ColorMap {

    private final HashMap<Integer, Integer> valueMapping;
    private final HashMap<Integer, Integer> priorities;

    protected ColorMap(HashMap<Integer, Integer> valueMapping, HashMap<Integer, Integer> priorities) {
        this.valueMapping = valueMapping;
        this.priorities = priorities;
    }

    public int[] mappedValues(int... values) {
        int[] mapped = new int[values.length];
        for (int i = 0; i < values.length; i++) {
            mapped[i] = valueMapping.get(values[i]);
        }
        return mapped;
    }

    public int getPrioritisedValue(int... values) {
        TreeMap<Integer, Integer> prioritySum = new TreeMap<>();
        for (int v : values) {
            int temp = prioritySum.containsKey(v) ? prioritySum.get(v) : 0;
            prioritySum.remove(v);
            prioritySum.put(v, temp + priorities.get(v));
        }
        return prioritySum.lastKey();
    }

    public int valueWithMaxPriority(int ... values) {
        int maxvalue = -1;
        int maxPriority = -1;
        for (int v : values) {
            if (priorities.get(v) > maxPriority) {
                maxPriority = priorities.get(v);
                maxvalue = v;
            }
        }
        return maxvalue;
    }
}
