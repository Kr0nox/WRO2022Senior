package team.brickfire.actions.dataTypes;

public enum BasketPosition {

    // TODO: figure these out
    CIRCUIT_SOUTH(-0.5),
    DELIVER_EAST(-1),
    DELIVER_CENTER(0),
    DELIVER_WEST(1),
    SCAN_EAST(-1, DELIVER_EAST),
    SCAN_CENTER(0, DELIVER_CENTER),
    SCAN_WEST(1, DELIVER_CENTER);

    private final double distance;
    private final BasketPosition dropOffPosition;

    private BasketPosition(double distance) {
        this.distance = distance;
        this.dropOffPosition = this;
    }

    private BasketPosition(double distance, BasketPosition dropOffPosition) {
        this.distance = distance;
        this.dropOffPosition = dropOffPosition;
    }

    public double getDistance() {
        return distance;
    }

    public BasketPosition getDropOff() {
        return dropOffPosition;
    }

}
