package team.brickfire.robotParts.arms;

import lejos.robotics.RegulatedMotor;
import team.brickfire.robotParts.arms.dataTypes.StatesArmWaterBottle;

public class ArmWaterBottle extends Arm<StatesArmWaterBottle> {
    private static final int MAX_WATER_BOTTLES = 2;

    private int amountLoadedWaterBottles;

    public ArmWaterBottle(RegulatedMotor motor) {
        super(motor, 300);
    }
    public void rotateTo(StatesArmWaterBottle state) {
        StatesArmWaterBottle moveTo = state;
        if (amountLoadedWaterBottles > 0 && state == StatesArmWaterBottle.UP_EMPTY) {
            moveTo = StatesArmWaterBottle.UP_FULL;
        }
        motor.rotateTo(moveTo.getDegrees());
    }

    public void addWaterBottles(int amount) {
        if (!(amount < 1 || amount + amountLoadedWaterBottles > MAX_WATER_BOTTLES)) {
            amountLoadedWaterBottles += amount;
        }
    }

    public void deliveredWaterBottle() {
        if (amountLoadedWaterBottles > 0) {
            amountLoadedWaterBottles--;
        }
    }

    public int getAmountLoadedWaterBottles() {
        return amountLoadedWaterBottles;
    }
}
