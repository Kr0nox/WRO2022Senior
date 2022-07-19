package team.brickfire.robot_parts.arms;

import lejos.hardware.motor.BaseRegulatedMotor;

public class WaterBottleArm extends Arm {

    /**
     * <p>Creates an WaterBottleArm object</p>
     *
     * @param motor              Motor of this arm
     * @param startPosition      Position it starts in
     * @param speed              Speed it should rotate in
     * @param accelerationFactor How many times the speed, is the acceleration
     */
    public WaterBottleArm(BaseRegulatedMotor motor, ArmMovement startPosition, double speed, double accelerationFactor) {
        super(motor, startPosition, speed, accelerationFactor);
    }
}
