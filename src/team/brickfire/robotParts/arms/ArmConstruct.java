package team.brickfire.robotParts.arms;

public class ArmConstruct extends ArmLift {

    private final ArmBlockCarrier armBlockCarrier;

    public ArmConstruct() {
        this.armBlockCarrier = new ArmBlockCarrier();
    }

    public void pickUp() {

    }

    public void drop() {
        armBlockCarrier.drop();
    }
}
