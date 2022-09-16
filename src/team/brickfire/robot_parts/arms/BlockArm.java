package team.brickfire.robot_parts.arms;

import lejos.hardware.motor.BaseRegulatedMotor;
import team.brickfire.robot_parts.arms.adjusting.ArmMovementCollection;

/**
 * <p>Represent the Arm for lifting laundry blocks and balls on our WRO robot</p>
 *
 * @version 2.0
 * @author Team BrickFire
 */
public class BlockArm extends Arm implements ArmMovementCollection {

    public static final ArmMovement ZERO = new RotateToArmMovement(0);
    public static final ArmMovement LOWEST = new RotateToArmMovement(0, 100);
    public static final ArmMovement MIDDLE = new RotateToArmMovement(-95, 100);
    public static final ArmMovement HIGHEST = new RotateToArmMovement(-190, 70);
    public static final RotateDistanceArmMovement OPEN = new RotateDistanceArmMovement(80, 60);
    public static final RotateDistanceArmMovement CLOSE = new RotateDistanceArmMovement(-80, 60);
    public static final RotateDistanceArmMovement DROP_BALL = new RotateDistanceArmMovement(100, 50);
    public static final ArmMovement BASKET = new RotateToArmMovement(-100, 15);
    public static final ArmMovement NUDGE = new RotateDistanceArmMovement(-100, 60).chain(new RotateDistanceArmMovement(70, 100));

    private static final ArmMovement[] POSITIONS = new ArmMovement[]{OPEN, CLOSE, LOWEST, MIDDLE, HIGHEST};

    private static BlockArm instance;
    private int index;


    /**
     * <p>Creates an Arm object</p>
     *
     * @param motor              Motor of this arm
     * @param startPosition      Position it starts in
     * @param speed              Speed it should rotate in
     * @param accelerationFactor How many times the speed, is the acceleration
     */
    protected BlockArm(BaseRegulatedMotor motor, RotateToArmMovement startPosition, double speed, double accelerationFactor) {
        super(motor, startPosition, speed, accelerationFactor);
    }

    /**
     * <p>Returns the instance of this Arm<br>
     * <i>Singleton-Pattern</i></p>
     * @param motor              Motor of this arm
     * @param startPosition      Position it starts in
     * @param speed              Speed it should rotate in
     * @param accelerationFactor How many times the speed, is the acceleration
     * @return Instance of this arm
     */
    public static BlockArm create(BaseRegulatedMotor motor, RotateToArmMovement startPosition, double speed, double accelerationFactor) {
        if (instance == null) {
            instance = new BlockArm(motor, startPosition, speed, accelerationFactor);
        }
        return instance;
    }

    @Override
    public void next() {
        index = Math.min(index + 1, POSITIONS.length - 1);
        move(POSITIONS[index]);
    }

    @Override
    public void previous() {
        index = Math.max(index - 1, 0);
        move(POSITIONS[index]);
    }

    @Override
    public int positionCount() {
        return POSITIONS.length;
    }

    @Override
    public ArmMovement getZero() {
        return ZERO;
    }

    @Override
    public String getName() {
        return "Block";
    }
}
