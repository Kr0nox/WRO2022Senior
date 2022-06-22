package team.brickfire.robotParts.sensors.doble_color;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public abstract class ColorMap {

    protected final Map<Integer, Integer> valueMapping;
    protected final Map<Integer, Integer> priorities;

    protected ColorMap(Map<Integer, Integer> valueMapping, Map<Integer, Integer> priorities) {
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

        int maxvalue = -1;
        int maxPriority = -1;
        for (int k : prioritySum.keySet()) {
            if (prioritySum.get(k) > maxPriority) {
                maxPriority = prioritySum.get(k);
                maxvalue = k;
            }
        }
        return maxvalue;
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

    protected static Map<Integer, Integer> mapOfValues(int... pairs) {
        if ((pairs.length & 2) == 1) {
            return new HashMap<>();
        }

        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < pairs.length; i += 2) {
            map.put(pairs[i], pairs[i + 1]);
        }
        return map;
    }
}
