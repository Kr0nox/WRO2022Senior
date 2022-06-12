package team.brickfire.robotParts.arms;

import lejos.robotics.RegulatedMotor;

public class Arm {

    private int baseSpeed = 150;

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

    public void setClawSpeed(int speed) {
        baseSpeed = clawMotor.getSpeed();
        clawMotor.setSpeed(speed);
    }

    public void resetClawSpeed() {
        clawMotor.setSpeed(baseSpeed);
    }

    public void setArmSpeed(int speed) {
        baseSpeed = armMotor.getSpeed();
        armMotor.setSpeed(speed);
    }

    public void resetArmSpeed() {
        armMotor.setSpeed(baseSpeed);
    }
}
