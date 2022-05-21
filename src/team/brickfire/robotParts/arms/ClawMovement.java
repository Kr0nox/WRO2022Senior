package team.brickfire.robotParts.arms;

public class ClawMovement extends Movement {

    public ClawMovement(int goal) {
        super(goal);
    }

    @Override
    public void execute(Arm arm) {
        arm.closeClawTo(goal);
    }
}
