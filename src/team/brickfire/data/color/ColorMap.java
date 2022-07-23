package team.brickfire.data.color;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>Maps colors to compassionate for problems with the sensor</p>
 * <p>Calculates a prioritized color to compensate for problems with the sensor</p>
 *
 * @version 1.0
 * @author Team BrickFire
 */
public abstract class ColorMap {

    protected final Map<Color, Color> valueMapping;
    protected final Map<Color, Integer> priorities;

    /**
     * <p>Creates a ColorMap</p>
     *
     * @param valueMapping Mapping of colors
     * @param priorities Priorities of colors
     */
    protected ColorMap(Map<Color, Color> valueMapping, Map<Color, Integer> priorities) {
        this.valueMapping = valueMapping;
        this.priorities = priorities;
    }

    /**
     * <p>Returns an array of the mapped color values</p>
     *
     * @param values Values to map
     * @return Mapped values
     */
    public Color[] mappedValues(Color... values) {
        Color[] mapped = new Color[values.length];
        for (int i = 0; i < values.length; i++) {
            mapped[i] = valueMapping.get(values[i]);
        }
        return mapped;
    }

    /**
     * <p>Returns the most prioritized color</p>
     *
     * @param values Colors to calculate in the process
     * @return Prioritized color
     */
    public Color getPrioritisedValue(Color... values) {
        Map<Color, Integer> prioritySum = new HashMap<>();
        for (Color v : values) {
            int temp = prioritySum.containsKey(v) ? prioritySum.get(v) : 0;
            prioritySum.remove(v);
            prioritySum.put(v, temp + priorities.get(v));
        }

        Color maxvalue = null;
        int maxPriority = -1;
        for (Color k : prioritySum.keySet()) {
            if (prioritySum.get(k) > maxPriority) {
                maxPriority = prioritySum.get(k);
                maxvalue = k;
            }
        }
        return maxvalue;
    }

    /**
     * <p>Chooses the color with the highest priority</p>
     *
     * @param values Colors to check
     * @return Color with the highest priority
     */
    public Color valueWithMaxPriority(Color ... values) {
        Color maxvalue = null;
        int maxPriority = -1;
        for (Color v : values) {
            if (priorities.get(v) > maxPriority) {
                maxPriority = priorities.get(v);
                maxvalue = v;
            }
        }
        return maxvalue;
    }
}
