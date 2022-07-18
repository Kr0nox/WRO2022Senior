package team.brickfire.robotParts.arms;

import lejos.hardware.motor.BaseRegulatedMotor;

/**
 * <p>The positions arms can be in</p>
 *
 * @version 2.0
 * @author upoon
 */
abstract class ArmMovement {

    private final int distance;
    private final ArmMovementType type;

    /**
     * <p>Moves the arm with the desired type</p>
     *
     * @param distance Distance to move
     * @param type Type of Movement
     */
    protected ArmMovement(int distance, ArmMovementType type) {
        this.distance = distance;
        this.type = type;
    }

    /**
     * <p>Moves the arm to the given position</p>
     *
     * @param distance Position to move to
     */
    protected ArmMovement(int distance) {
        this.distance = distance;
        this.type = ArmMovementType.ROTATE_TO;
    }

    /**
     * <p>Rotates the given arm according to the movement</p>
     * <p><i>Package-private</i></p>
     *
     * @param arm Arm to rotate
     * @param immediateReturn whether the method should immediately return after starting the motor
     */
    void execute(Arm arm, boolean immediateReturn) {
        type.execute(arm.getMotor(), distance - arm.getStartPosition().distance, immediateReturn);
    }

    protected enum ArmMovementType {

        ROTATE {
            @Override
            public void execute(BaseRegulatedMotor motor, int distance, boolean immediateReturn) {
                motor.rotate(distance, immediateReturn);
            }
        },

        ROTATE_TO {
            @Override
            public void execute(BaseRegulatedMotor motor, int distance, boolean immediateReturn) {
                motor.rotateTo(distance, immediateReturn);
            }
        };

        abstract void execute(BaseRegulatedMotor motor, int distance, boolean immediateReturn);

    }


}
