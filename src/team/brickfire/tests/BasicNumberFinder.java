package team.brickfire.tests;

import lejos.hardware.Button;
import lejos.hardware.lcd.LCD;

public abstract class BasicNumberFinder {

    private final String[] names;
    protected final float[] values;
    private final int[] powersForChange;
    private int index = 0;
    private boolean quit = false;
    private NumberFinderMode mode;

    public BasicNumberFinder(String[] names, float[] startValues, int[] powersForChange) {
        if (names.length != startValues.length || names.length != powersForChange.length) {
            throw new IllegalArgumentException("All arrays should have same length");
        }
        this.names = names;
        this.values = startValues;
        this.powersForChange = powersForChange;
        mode = NumberFinderMode.VALUE;
    }

    public void start() {
        do {
            drawDisplay();
            int id = Button.waitForAnyPress();
            switch (id) {
                case Button.ID_ENTER:
                    if (mode == NumberFinderMode.START) {
                        execute();
                    } else {
                        quit = true;
                    }
                    break;
                case Button.ID_UP:
                    changeValue(1);
                    break;
                case Button.ID_DOWN:
                    changeValue(-1);
                    break;
                case Button.ID_LEFT:
                    changeIndex(-1);
                    break;
                case Button.ID_RIGHT:
                    changeIndex(1);
                    break;
                case Button.ID_ESCAPE:
                    quit = true;
                    break;
            }
        } while (!quit);
    }

    private void drawDisplay() {
        LCD.clearDisplay();
        switch (mode) {
            case START:
                LCD.drawString("Start", 2, 3);
                return;
            case VALUE:
                LCD.drawString("Werte ändern:", 0, 0);
                break;
            case CHANGE_AMOUNT:
                LCD.drawString("Größe der Änderung:", 0, 0);
        }

        for (int i = -1; i < Math.max(2, values.length - 1); i++) {
            LCD.drawString(names[indexInRange(index + i)], i * 6, 1, i == 1);
            LCD.drawString((mode == NumberFinderMode.VALUE ? values[index + i]+""
                            : Math.pow(10, powersForChange[index + i])+""), i * 6, 2);
        }
    }

    private void changeValue(int amount) {
        if (mode == NumberFinderMode.VALUE) {
            values[index] += amount * Math.pow(10, powersForChange[index]);
        } else if (mode == NumberFinderMode.CHANGE_AMOUNT) {
            powersForChange[index] += amount;
        }
    }

    private int indexInRange(int number) {
        return number % values.length;
    }

    private void changeIndex(int amount) {
        index += amount;
        mode.change(index, values.length - 1);
        index = indexInRange(index);
    }

    protected abstract void execute();

}
