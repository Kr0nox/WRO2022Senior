package team.brickfire.robotParts;

public enum WaterBottleArmStates {
    // TODO: Figure out degrees
    START (0),
    TABLE (0),
    UP_FULL (0),
    UP_EMPTY (0);

    private int degrees;

    WaterBottleArmStates(int degrees) {
        this.degrees = degrees;
    }

    public int getDegrees() {
        return degrees;
    }

}
