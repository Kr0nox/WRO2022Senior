package team.brickfire.challengeParts;

import lejos.robotics.Color;

public abstract class Block {
    protected final int color; // as in lejos.robotics.Color static variables

    public Block(int color, int[] validColors) {
        for (int c : validColors) {
            if (c == color) {
                this.color = color;
                return;
            }
        }
        this.color = Color.NONE;
    }
    public int getColor() {
        return color;
    }
}
