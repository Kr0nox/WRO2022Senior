package team.brickfire.robot_parts.arms;

/**
 * <p>The positions {@link Arm arm} can be in</p>
 *
 * @version 2.1
 * @author upoon
 */
class ArmMovement {

    private final int distance;
    private final ArmMovementType type;

    private final double speed;

    /**
     * <p>Moves the {@link Arm arm} with the desired type<br>
     * Speed will be the arms default speed</p>
     *
     * @param distance Distance to move
     * @param type Type of Movement
     */
    protected ArmMovement(int distance, ArmMovementType type) {
        this.distance = distance;
        this.type = type;
        this.speed = -1;
    }

    /**
     * <p>Moves the {@link Arm arm} to the given position<br>
     * Speed will be the arms default speed</p>
     *
     * @param distance Position to move to
     */
    protected ArmMovement(int distance) {
        this(distance, ArmMovementType.ROTATE_TO);
    }

    /**
     * <p>Moves the {@link Arm arm} with the desired type</p>
     *
     * @param distance Distance to move
     * @param speed Speed the arm should rotate at. Ranges from -100 - 100
     * @param type Type of Movement
     *
     */
    protected ArmMovement(int distance, double speed, ArmMovementType type) {
        this.distance = distance * (speed > 0 ? 1 : -1);
        this.type = type;
        this.speed = Math.abs(speed);
    }

    /**
     * <p>Moves the {@link Arm arm} to the given position</p>
     *
     * @param distance Position to move to
     * @param speed Speed the arm should rotate at. Ranges from -100 - 100
     */
    protected ArmMovement(int distance, double speed) {
        this(distance, speed, ArmMovementType.ROTATE_TO);
    }

    /**
     * <p>Rotates the given {@link Arm arm} according to the movement</p>
     * <p><i>Package-private</i></p>
     *
     * @param arm Arm to rotate
     * @param immediateReturn whether the method should immediately return after starting the motor
     */
    void execute(Arm arm, boolean immediateReturn) {
        if (speed > 0) {
            arm.setSpeed(speed);
        } else {
            arm.setSpeed(arm.getStandardSpeed());
        }

        type.execute(arm, distance, immediateReturn);
    }

    protected enum ArmMovementType {

        ROTATE {
            @Override
            public void execute(Arm arm, int distance, boolean immediateReturn) {
                arm.getMotor().rotate(distance, immediateReturn);
            }
        },

        ROTATE_TO {
            @Override
            public void execute(Arm arm, int distance, boolean immediateReturn) {
                arm.getMotor().rotateTo(distance - arm.getStartPosition().distance, immediateReturn);
            }
        };

        /**
         * <p>Executes this movement type</p>
         *
         * @param arm Motor to move
         * @param distance Distance for moving
         * @param immediateReturn Whether the method should immediately return
         */
        abstract void execute(Arm arm, int distance, boolean immediateReturn);

    }


}
