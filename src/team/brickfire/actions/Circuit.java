package team.brickfire.actions;

import team.brickfire.actions.dataTypes.CircuitPosition;
import team.brickfire.actions.dataTypes.Orientation;
import team.brickfire.robotParts.Robot;

public class Circuit extends BaseAction {

    private static final double ROTATION_SPEED = 200;

    private CircuitPosition currentPosition;
    private Orientation currentOrientation;

    private static final int SPEED = 200;

    /**
     * Creates an Circuit Object
     * @param robot The robot
     */
    public Circuit(Robot robot, CircuitPosition startPosition, Orientation startOrientation) {
        super(robot);
        currentPosition = startPosition;
        currentOrientation = startOrientation;
    }

    public void driveTo(CircuitPosition goal) {
        if (currentPosition == goal) {
            return;
        }
        if (currentPosition.getX() == goal.getX()) {
            turnTo(Orientation.getOrientation(0, Integer.compare(currentPosition.getY(), goal.getY())));
        } else if (currentPosition.getY() == goal.getY()) {
            turnTo(Orientation.getOrientation(Integer.compare(currentPosition.getX(), goal.getX()), 0));
        } else {
            if (goal.getX() == 0) {
                turnTo(Orientation.getOrientation(0, Integer.compare(currentPosition.getY(), goal.getY())));
            } else {
                turnTo(Orientation.getOrientation(Integer.compare(currentPosition.getX(), goal.getX()), 0));
            }
        }
        driveOneForward();
        driveTo(goal);
    }

    public void driveTo(CircuitPosition goalPosition, Orientation goalOrientation) {
        driveTo(currentOrientation, goalPosition, goalOrientation);

    }

    public void driveTo(Orientation startOrientation, CircuitPosition goalPosition) {
        currentOrientation = startOrientation;
        driveTo(goalPosition);
    }

    public void driveTo(Orientation startOrientation, CircuitPosition goalPosition, Orientation goalOrientation) {
        driveTo(startOrientation, goalPosition);
        turnTo(goalOrientation);
    }

    public void turnTo(Orientation goalOrientation) {
        if (currentOrientation == goalOrientation) {
            return;
        }
        if (currentOrientation.getX() == goalOrientation.getX()
                || currentOrientation.getY() == goalOrientation.getY()) {
            robot.turn(180, ROTATION_SPEED);
        } else {
            int direction = currentOrientation.getX() * -goalOrientation.getY()
                    + currentPosition.getY() * goalOrientation.getX();
            robot.turn(direction * -90, ROTATION_SPEED);
        }
        currentOrientation = goalOrientation;
    }

    private void driveOneForward() {
        // Driving
        robot.lineFollowing(SPEED);
        currentPosition = currentPosition.getAdjacent(currentOrientation);

        // Adjust
        if (currentPosition.isCorner()) {

        } else {

        }
    }

        public void setPosition(CircuitPosition P) {
            currentPosition = P;
        }

        public void setOrientation(Orientation O) {
            currentOrientation = O;
        }
}
