package team.brickfire.robotParts.arms;

import lejos.robotics.RegulatedMotor;

public class Arm {

    private final RegulatedMotor armMotor;
    private final RegulatedMotor clawMotor;

    public Arm(RegulatedMotor armMotor, RegulatedMotor clawMotor) {
        this.armMotor = armMotor;
        this.clawMotor = clawMotor;
    }

    public void move(ArmPosition goal) {
        move(goal.getMovements());
    }

    public void move(Movement movement) {
        movement.execute(this);
    }

    public void move(Movement[] movements) {
        for (Movement m : movements) {
            move(m);
        }
    }

    public void moveArmTo(int position) {
        armMotor.rotateTo(position);
    }

    public void closeClawTo(int position) {
        clawMotor.rotateTo(position);
    }

}
