package team.brickfire.robotParts.arms;

public abstract class Movement {

    protected final int goal;

    protected Movement(int goal) {
        this.goal = goal;
    }

    public abstract void execute(Arm arm);

}
