package team.brickfire.robot_parts.arms;

import lejos.hardware.motor.BaseRegulatedMotor;
import team.brickfire.robot_parts.arms.adjusting.ArmMovementCollection;

/**
 * <p>Represent the Arm for lifting and carrying water bottles on our WRO robot</p>
 *
 * @version 2.0
 * @author Team BrickFire
 */
public class WaterBottleArm extends Arm implements ArmMovementCollection {


    public static final ArmMovement ZERO = new RotateDistanceArmMovement(0);
    /* TODO: Figure values out */
    public static final ArmMovement HIGHEST = new RotateToArmMovement(-1);
    public static final ArmMovement LOWEST = new RotateToArmMovement(-1);
    public static final ArmMovement TABLE = new RotateToArmMovement(-1);
    public static final ArmMovement OVER_TABLE = new RotateToArmMovement(-1);
    public static final ArmMovement OPEN = new RotateDistanceArmMovement(-1);
    public static final ArmMovement CLOSE = new RotateDistanceArmMovement(-1);
    public static final ArmMovement DROP_BLOCK = new RotateDistanceArmMovement(-1);

    private static final ArmMovement[] POSITIONS = new ArmMovement[]{LOWEST, TABLE, OVER_TABLE, HIGHEST};
    private static WaterBottleArm instance;
    private int index;


    /**
     * <p>Creates an Arm object</p>
     *
     * @param motor              Motor of this arm
     * @param startPosition      Position it starts in
     * @param speed              Speed it should rotate in
     * @param accelerationFactor How many times the speed, is the acceleration
     */
    protected WaterBottleArm(BaseRegulatedMotor motor, RotateToArmMovement startPosition, double speed, double accelerationFactor) {
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
    public static WaterBottleArm create(BaseRegulatedMotor motor, RotateToArmMovement startPosition, double speed, double accelerationFactor) {
        if (instance == null) {
            instance = new WaterBottleArm(motor, startPosition, speed, accelerationFactor);
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
}
