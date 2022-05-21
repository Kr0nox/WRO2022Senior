package team.brickfire.robotParts.arms;

public class ArmMovement extends Movement {

    public ArmMovement(int goal) {
        super(goal);
    }

    @Override
    public void execute(Arm arm) {
        arm.moveArmTo(goal);
    }
}
