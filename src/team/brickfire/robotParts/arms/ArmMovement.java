package team.brickfire.robotParts.arms;

import lejos.hardware.motor.BaseRegulatedMotor;

public enum ArmMovement {

    NONE(0, ArmMovementType.ROTATE);

    private final int distance;
    private final ArmMovementType type;

    ArmMovement(int distance, ArmMovementType type) {
        this.distance = distance;
        this.type = type;
    }

    void execute(Arm arm, boolean immediateReturn) {
        type.execute(arm.getMotor(), distance - arm.getStartPosition().distance, immediateReturn);
    }

    enum ArmMovementType {

        ROTATE {
            @Override
            public void execute(BaseRegulatedMotor motor, int distance, boolean immediateReturn) {
                motor.rotate(distance, immediateReturn);
            }
        },

        ROTATE_TO{
            @Override
            public void execute(BaseRegulatedMotor motor, int distance, boolean immediateReturn) {
                motor.rotateTo(distance, immediateReturn);
            }
        };

        abstract void execute(BaseRegulatedMotor motor, int distance, boolean immediateReturn);

    }


}
