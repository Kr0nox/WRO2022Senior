package team.brickfire.robotParts;

import lejos.robotics.Color;

public enum ColorPool {

    LAUNDRY(new int[] {Color.BLACK, Color.YELLOW, Color.RED}),

    ROOM(new int[] {Color.GREEN, Color.WHITE});

    private final int[] colors;

    ColorPool(int[] colors) {
        this.colors = colors;
    }

    public boolean contains(int i) {
        for (int c : colors) {
            if (i == c) {
                return true;
            }
        }

        return false;
    }
}
