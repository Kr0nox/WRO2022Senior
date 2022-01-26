package team.brickfire.robotParts.arms.dataTypes;

public enum StatesArmWaterBottle {

    START (0),
    TABLE (0),
    UP_FULL (0),
    UP_EMPTY (0);

    private final int degrees;

    StatesArmWaterBottle(int degrees) {
        this.degrees = degrees;
    }

    public int getDegrees() {
        return degrees;
    }

}
