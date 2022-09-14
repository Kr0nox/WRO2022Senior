package team.brickfire.robot_parts.base;

/**
 * <p>Collection of useful functions for driving and turning</p>
 *
 * @version 1.0
 * @author Team BrickFire
 */
public abstract class DrivingBase {

    /**
     * <p>The robot drives the given distance in a straight line</p>
     *
     * @param distance Distance the robot should travel in cm.
     * @param immediateReturn If true, immediately exists the method after starting the motors
     */
    public abstract void drive(double distance, boolean immediateReturn);

    /**
     * <p>The robot drives the given distance in a straight line</p>
     *
     * @param distance Distance the robot should travel in cm.
     * @param speed Speed at which the robot drives (0-100% of maximum capability).
     * @param immediateReturn If true, immediately exists the method after starting the motors
     */
    public void drive(double distance, double speed, boolean immediateReturn) {
        setDrivingSpeed(Math.abs(speed));
        drive(distance * Math.signum(speed), immediateReturn);
    }

    /**
     * <p>The robot drives the given distance in a straight line</p>
     *
     * @param distance Distance the robot should travel in cm.
     * @param speed Speed at which the robot drives (0-100% of maximum capability).
     */
    public void drive(double distance, double speed) {
        drive(distance, speed, false);
    }

    /**
     * <p>The robot drives the given distance in a straight line</p>
     *
     * @param distance Distance the robot should travel in cm.
     */
    public void drive(double distance) {
        drive(distance, false);
    }

    /**
     * <p>The robot turns the given number of degrees</p>
     *
     * @param angle Angle the robot should rotate in degrees.
     * @param immediateReturn If true, immediately exists the method after starting the motors.
     */
    public abstract void turn(double angle, boolean immediateReturn);

    /**
     * <p>The robot turns the given number of degrees</p>
     *
     * @param angle Angle the robot should rotate in degrees.
     * @param speed Speed at which the robot turns (0-100% of maximum capability).
     * @param immediateReturn If true, immediately exists the method after starting the motors.
     */
    public void turn(double angle, double speed, boolean immediateReturn) {
        setTurningSpeed(Math.abs(speed));
        turn(angle * Math.signum(speed), immediateReturn);
    }

    /**
     * <p>The robot turns the given number of degrees</p>
     *
     * @param angle Angle the robot should rotate in degrees.
     * @param speed Speed at which the robot turns (0-100% of maximum capability).
     */
    public void turn(double angle, double speed) {
        turn(angle, speed, false);
    }

    /**
     * <p>The robot turns the given number of degrees</p>
     *
     * @param angle Angle the robot should rotate in degrees.
     */
    public void turn(double angle) {
        turn(angle, false);
    }

    /**
     * <p>The robot turns the given number of degrees by only driving the left wheel and letting the right one
     * stand still</p>
     *
     * @param angle Angle the robot should rotate in degrees.
     * @param immediateReturn If true, immediately exists the method after starting the motors.
     */
    public abstract void turnLeftWheel(double angle, boolean immediateReturn);

    /**
     * <p>The robot turns the given number of degrees by only driving the left wheel and letting the right one
     * stand still</p>
     *
     * @param angle Angle the robot should rotate in degrees.
     * @param speed Speed at which the robot turns (0-100% of maximum capability).
     * @param immediateReturn If true, immediately exists the method after starting the motors.
     */
    public void turnLeftWheel(double angle, double speed, boolean immediateReturn) {
        setTurningSpeed(Math.abs(speed));
        turnLeftWheel(angle * Math.signum(speed), immediateReturn);
    }

    /**
     * <p>The robot turns the given number of degrees by only driving the left wheel and letting the right one
     * stand still</p>
     *
     * @param angle Angle the robot should rotate in degrees.
     * @param speed Speed at which the robot turn (0-100% of maximum capability).
     */
    public void turnLeftWheel(double angle, double speed) {
        turnLeftWheel(angle, speed, false);
    }

    /**
     * <p>The robot turns the given number of degrees by only driving the left wheel and letting the right one
     * stand still</p>
     *
     * @param angle Angle the robot should rotate in degrees.
     */
    public void turnLeftWheel(double angle) {
        turnLeftWheel(angle, false);
    }

    /**
     * <p>The robot turns the given number of degrees by only driving the right wheel and letting the left one
     * stand still</p>
     *
     * @param angle Angle the robot should rotate in degrees.
     * @param immediateReturn If true, immediately exists the method after starting the motors.
     */
    public abstract void turnRightWheel(double angle, boolean immediateReturn);

    /**
     * <p>The robot turns the given number of degrees by only driving the right wheel and letting the left one
     * stand still</p>
     *
     * @param angle Angle the robot should rotate in degrees.
     * @param speed Speed at which the robot turns (0-100% of maximum capability).
     * @param immediateReturn If true, immediately exists the method after starting the motors.
     */
    public void turnRightWheel(double angle, double speed, boolean immediateReturn) {
        setTurningSpeed(Math.abs(speed));
        turnRightWheel(angle * Math.signum(speed), immediateReturn);
    }

    /**
     * <p>The robot turns the given number of degrees by only driving the right wheel and letting the left one
     * stand still</p>
     *
     * @param angle Angle the robot should rotate in degrees.
     * @param speed Speed at which the robot turns (0-100% of maximum capability).
     */
    public void turnRightWheel(double angle, double speed) {
        turnRightWheel(angle, speed, false);
    }

    /**
     * <p>The robot turns the given number of degrees by only driving the right wheel and letting the left one
     * stand still</p>
     *
     * @param angle Angle the robot should rotate in degrees.
     */
    public void turnRightWheel(double angle) {
        turnRightWheel(angle, false);
    }

    /**
     * <p>Lets the robot drive forward until stopped</p>
     */
    public abstract void driveForward();

    /**
     * <p>Lets the robot drive forward until stopped</p>
     *
     * @param speed Speed at which the robot drives (0-100% of maximum capability).
     */
    public void driveForward(double speed) {
        setDrivingSpeed(Math.abs(speed));
        driveForward();
    }

    /**
     * <p>Lets the robot drive backward until stopped</p>
     */
    public abstract void driveBackward();

    /**
     * <p>Lets the robot drive backward until stopped</p>
     *
     * @param speed Speed at which the robot drives (0-100% of maximum capability).
     */
    public void driveBackward(double speed) {
        setDrivingSpeed(Math.abs(speed));
        driveBackward();
    }

    /**
     * <p>Stops the robot</p>
     *
     * @param immediateReturn If true, immediately exists the method after stopping the motors.
     */
    public abstract void stop(boolean immediateReturn);

    /**
     * <p>Stops the robot</p>
     */
    public void stop() {
        stop(false);
    }

    /**
     * <p>Sets speed and acceleration of the robot for driving <br>
     * Speed is limited between 0%-100% of the max Speed <br>
     * Acceleration is relative to the max Speed.</p>
     *
     * @param speed Speed at which the robot drives (0-100% of maximum capability).
     * @param acceleration Acceleration of the robot when driving. (0-100% of max speed)
     */
    public abstract void setDrivingSpeed(double speed, double acceleration);

    /**
     * <p>Sets speed and acceleration of the robot for driving <br>
     * Speed is limited between 0%-100% of the max Speed</p>
     *
     * @param speed Speed at which the robot drives (0-infinite% of maximum capability).
     */
    public void setDrivingSpeed(double speed) {
        setDrivingSpeed(speed, speed * 3);
    }

    /**
     * <p>Sets speed and acceleration of the robot for turning <br>
     * Speed is limited between 0%-100% of the max Speed <br>
     * Acceleration is relative to the max Speed</p>
     *
     * @param speed Speed at which the robot turns (0-100% of maximum capability).
     * @param acceleration Acceleration of the robot when turning. (0-infinite% of max speed)
     */
    public abstract void setTurningSpeed(double speed, double acceleration);

    /**
     * <p>Sets speed and acceleration of the robot for turning <br>
     * Speed is limited between 0%-100% of the max Speed</p>

     * @param speed Speed at which the robot turns (0-100% of maximum capability).
     */
    public void setTurningSpeed(double speed) {
        setTurningSpeed(speed, speed * 4);
    }

    /**
     * <p>The distance the left wheel has traveled in cm</p>
     * @return The distance the left wheel has traveled in cm
     */
    public abstract double getLeftWheelDistance();

    /**
     * <p>The distance the left wheel has traveled in cm</p>
     * @return The distance the left wheel has traveled in cm
     */
    public abstract double getRightWheelDistance();

    /**
     * <p>The distance the robot has traveled in cm</p>
     * @return The distance the robot has traveled in cm
     */
    public double getDistance() {
        return (getLeftWheelDistance() + getRightWheelDistance()) / 2.0;
    }

    /**
     * <p>Resets the counter for traveled distance</p>
     */
    public abstract void resetDistance();

    /**
     * <p>Checks whether the robot is currently moving</p>
     * @return True if the robot is moving, false otherwise
     */
    public abstract boolean isMoving();
}
