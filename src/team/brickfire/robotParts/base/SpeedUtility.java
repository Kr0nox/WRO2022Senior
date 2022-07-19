package team.brickfire.robotParts.base;

import lejos.hardware.motor.BaseRegulatedMotor;

/**
 * <p>Utility class for speed calculation and assignment</p>
 *
 * @version 1.0
 * @author Team BrickFire
 */
public final class SpeedUtility {

    private SpeedUtility() {
        throw new RuntimeException("Should not be instantiated");
    }

    /**
     * <p>Sets the speed and acceleration of the given motor</p>
     * @param motor Motor which gets its speed and acceleration set
     * @param speed Speed at which the motor rotates (0-100% of maximum capability).
     * @param acceleration Speed at which the motor accelerates (0-100% of max speed)
     */
    public static void setMotorSpeed(BaseRegulatedMotor motor, double speed, double acceleration) {
        speed = limitSpeed(speed / 100 * motor.getMaxSpeed(), 0, motor.getMaxSpeed());
        acceleration = limitSpeed(acceleration / 100 * motor.getMaxSpeed(), 0, Double.POSITIVE_INFINITY);
        motor.setSpeed((int) speed);
        motor.setAcceleration((int) acceleration);
    }

    /**
     * Limits the speed between the two given values
     * @param value Speed to limit
     * @param min Minimum value
     * @param max Maximum value
     * @return Limited value
     */
    public static double limitSpeed(double value, double min, double max) {
        if (value < min) {
            return min;
        }
        if (value > max) {
            return max;
        }
        return value;
    }
}
