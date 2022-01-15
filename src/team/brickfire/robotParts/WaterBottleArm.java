package team.brickfire.robotParts;

import lejos.robotics.RegulatedMotor;

public class WaterBottleArm extends Arm<WaterBottleArmStates> {
    private static final int MAX_WATER_BOTTLES = 2;

    private int amountLoadedWaterBottles;

    public WaterBottleArm(RegulatedMotor motor) {
        super(motor);
    }
    public void rotateTo(WaterBottleArmStates state) {
        WaterBottleArmStates moveTo = state;
        if (amountLoadedWaterBottles > 0 && state == WaterBottleArmStates.UP_EMPTY) {
            moveTo = WaterBottleArmStates.UP_FULL;
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
}
