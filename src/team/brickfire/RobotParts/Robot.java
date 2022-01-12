package team.brickfire.RobotParts;

import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.*;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.robotics.Color;
import lejos.robotics.chassis.Wheel;
import lejos.robotics.chassis.WheeledChassis;
import lejos.robotics.navigation.MovePilot;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public class Robot {

    private final Wheel wheelLeft;
    private final Wheel wheelRight;
    private final MovePilot pilot;

    private final EV3ColorSensor colorSensorLeft;
    private final EV3ColorSensor colorSensorRight;
    // Add extra two sensors (maybe classes, depending on whether they need to save data)

    // Add extra Motors (probably as own objects)

    public Robot(double diameter, double offset, Port motorLeft, Port motorRight, Port Arm1,
                 Port Arm2, Port sensorLeft, Port sensorRight, Port s1, Port s2) {
        wheelLeft = WheeledChassis.modelWheel(new EV3LargeRegulatedMotor((TachoMotorPort) motorLeft), diameter).offset(-offset);
        wheelRight = WheeledChassis.modelWheel(new EV3LargeRegulatedMotor((TachoMotorPort) motorRight), diameter).offset(offset);
        pilot = new MovePilot(new WheeledChassis(new Wheel[] {wheelRight, wheelLeft}, WheeledChassis.TYPE_DIFFERENTIAL));

        colorSensorLeft = new EV3ColorSensor((UARTPort) sensorLeft);
        colorSensorRight = new EV3ColorSensor((UARTPort) sensorRight);
        // Initialize extra sensors
        // Create objects for the two "arms"
    }

    /*
     * Drives the given Distance with optional stop
     * @param distance The distance in cm
     * @param speed The speed the robot should drive with in cm/s
     * @param stop Whether the robot should stop at the end or keep on turning
     */
    public void travel(double distance) {
        pilot.travel(distance);
    }
    public void travel(double distance, int speed) {
        pilot.setLinearSpeed(speed);
        travel(distance);
    }
    public void travel(double distance, int speed, boolean stop) {
        travel(distance,speed);
        if (stop)
            pilot.stop();
    }

    /*
     * The Robot turns counterclockwise
     * @param angle The angle the robot should rotate
     * @param speed The speed at which the Robot rotates
     */
    public void turn(int angle) {
        pilot.rotate(angle);
    }
    public void turn(int angle, int speed) {
        pilot.setAngularSpeed(speed);
        turn(angle);
    }

    /*
     * The Robot squares up with a line it drives towards
     * @param forward The direction in which the Robot drives
     * @param speed The speed at which the Robot moves
     */
    public void alignLine(boolean forward, int speed) {
        char finishedFirst = driveTillLine(forward, speed);
        wheelRight.getMotor().resetTachoCount();
        wheelLeft.getMotor().resetTachoCount();
        wheelRight.getMotor().setSpeed(speed * 2 / 3);
        wheelLeft.getMotor().setSpeed(speed * 2 / 3);
        boolean finishedAll = false;
        while (!finishedAll) {
            if (finishedFirst == 'r' && colorSensorLeft.getColorID() == Color.BLACK) {
                finishedAll = true;
            } else if (finishedFirst == 'l' && colorSensorRight.getColorID() == Color.BLACK) {
                finishedAll = true;
            }
        }
        // Why?
        double distCm = wheelLeft.getMotor().getTachoCount() * 0.0488 / 3.7;
        double degrees = Math.toDegrees(Math.atan(distCm) * (finishedFirst == 'r' ? 1 : -1));
        turn((int)degrees, speed / 3);

        squareWithLine(speed / 3);
    }
    public void alignLine(boolean forward) {
        alignLine(forward, 300);
    }
    public void alignLine() {
        alignLine(true);
    }

    public char driveTillLine(boolean forward, int speed) {
        pilot.setLinearSpeed(speed);
        if(forward)
            pilot.forward();
        else
            pilot.backward();
        while (colorSensorRight.getColorID() != Color.BLACK || colorSensorLeft.getColorID() != Color.BLACK);
        if (colorSensorRight.getColorID() == Color.BLACK)
            return 'r';
        else
            return  'l';
    }

    private static final float FINAL_ADJUSTMENT = 0.1f;
    private void squareWithLine(int speed) {
        pilot.setLinearSpeed(speed);
        float[] vl = {1};
        float[] vr = {1};
        do {
            colorSensorLeft.getRedMode().fetchSample(vl, 0);
            colorSensorRight.getRedMode().fetchSample(vr, 0);
            if(vl[0] > vr[0]) {
                wheelLeft.getMotor().forward();
                wheelRight.getMotor().backward();
            } else if (vl[0] < vr[0]) {
                wheelLeft.getMotor().backward();
                wheelRight.getMotor().forward();
            }
        } while (Math.abs(vl[0]-vr[0]) < FINAL_ADJUSTMENT);
    }

    // TODO: Wall alignment
    /*
     * The robot drives backwards against a wall to stand straight
     * @param speed The speed at which the Robot moves
     */
    public void alignWall(int speed) {
        throw new NotImplementedException();
    }

    // TODO: Line following
    /*
     * The Robot follows a black line
     * @param distance The distance it should drive along the line in cm
     * @param forward Direction the Robot should drive
     */
    public void lineFollowing(double distance, boolean forward) {
        // Robot should recognize which sensor is on the line
        throw new NotImplementedException();
    }
}
