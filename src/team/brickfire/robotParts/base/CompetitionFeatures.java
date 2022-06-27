package team.brickfire.robotParts.base;

import team.brickfire.data.color.Color;

/**
 * <p>Collection of useful features for competitions like line following and aligning</p>
 *
 * @version 1.0
 * @author Team BrickFire
 */
public interface CompetitionFeatures {

    /**
     * <p>Calculates the angle the robot needs to rotate to stand straight through a right triangle </p>
     * <p>First the robot positions the sensor that is closer to the line on top of it. <br>
     * The robot then measures the adjacent leg of the triangle through driving straight until the other sensor is
     * on top of the line. <br>
     * Then through the atan-function the angle gets calculated by dividing the driven distance through the distance
     * between the two wheels. <br>
     * At last, he turns to stand straight. </p>
     *
     * @param speed Speed at which the robot executes the alignment (0-100% of maximum capability).
     *              Negative speed makes the robot drive backwards.
     */
    void alignTrigonometry(double speed);

    /**
     * <p>Aligns the robot through driving to the black and white line repeatedly</p>
     * <p>The robot drives to the black line until one sensor is on top of it. The corresponding motor then stops. <br>
     * He then positions the other sensor on top of the line by driving with the remaining motor. </br>
     * This procedure gets repeated with the white line and again the black line for n-times.</p>
     *
     * @param speed Speed at which the robot executes the alignment (0-100% of maximum capability).
     *                    Negative speed makes the robot drive backwards at the first repetition.
     * @param repetitions How many times the aligning gets repeated. The first repetition aligns it with the black line,
     *                    the second with the white and so forth.
     */
    void alignColor(double speed, int repetitions);

    /**
     * <p>The robot turns until booth sensors see the same reflected light value</p>
     *
     * @param speed Speed at which the robot executes the alignment (0-100% of maximum capability).
     */
    void alignLightLevel(double speed);

    /**
     * <p>The robot drives the wheel that has traveled less distance forward until they both have traveled the </p>
     * same distance. <br>
     *
     * The speed is the last driven speed.
     */
    void alignMotorRotations();

    /**
     * <p>The robot follows a line straight along with a PID line follower</p>
     *
     * @param distance The distance the robot should travel along the line.
     * @param speed Speed at which the robot drives (0-100% of maximum capability).
     */
    void lineFollowing(double distance, double speed);

    /**
     * <p>The robot drives until it has reached the desired color </p>
     *
     * @param speed Speed at which the robot drives (0-100% of maximum capability).
     * @param color Color the robot stops at
     */
    void driveTillColor(double speed, Color color);
}
