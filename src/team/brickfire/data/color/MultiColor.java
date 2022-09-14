package team.brickfire.data.color;

public class MultiColor {

    final Color[] colors;

    public MultiColor(Color[] colors) {
        this.colors = colors;
    }

    public Color getColor(ColorMap colorMap) {
        return colorMap.getPrioritisedValueBySum(colorMap.mappedValues(colorMap.repeatByPriority(colors)));
    }

    public double colorPercent(ColorMap colorMap, Color color) {
        Color[] repeat = colorMap.repeatByPriority(colorMap.mappedValues(colors));
        int count = 0;
        for (Color c : repeat) {
            if (c == color) {
                count++;
            }
        }
        return count / (double) repeat.length;
    }
}
