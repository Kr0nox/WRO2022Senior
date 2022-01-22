package team.brickfire.robotParts;

public enum StatesArmLaundryBlock {
    START (0),
    TABLE (0),
    UP_BALL (0),
    DOWN_BALL (0),
    UP_BLOCK (0),
    DOWN_BLOCK (0);

    private int degrees;

    StatesArmLaundryBlock(int degrees) {
        this.degrees = degrees;
    }

    public int getDegrees() {
        return degrees;
    }
}
