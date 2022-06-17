package team.brickfire.actions;

import team.brickfire.actions.dataTypes.CircuitPosition;
import team.brickfire.actions.dataTypes.Orientation;
import team.brickfire.robotParts.Robot;

public class Circuit extends BaseAction {

    private static final double ROTATION_SPEED = 200;

    private CircuitPosition currentPosition;
    private Orientation currentOrientation;

    private static final int SPEED = 320;

    private boolean onLine = true;

    /**
     * Creates an Circuit Object
     * @param robot The robot
     */
    public Circuit(Robot robot, CircuitPosition startPosition, Orientation startOrientation) {
        super(robot);
        currentPosition = startPosition;
        currentOrientation = startOrientation;
    }

    public void driveTo(CircuitPosition goal, boolean startOnLine) {
        if (currentPosition == goal) {
            if (!onLine) {
                getOnLine();
            }
            return;
        }
        if (currentPosition.getX() == goal.getX()) {
            turnTo(Orientation.getOrientation(0, Integer.compare(goal.getY(), currentPosition.getY())));
        } else if (currentPosition.getY() == goal.getY()) {
            turnTo(Orientation.getOrientation(Integer.compare(goal.getX(), currentPosition.getX()), 0));
        } else {
            if (goal.getX() == 0) {
                turnTo(Orientation.getOrientation(0, Integer.compare(goal.getY(), currentPosition.getY())));
            } else {
                turnTo(Orientation.getOrientation(Integer.compare(goal.getX(), currentPosition.getX()), 0));
            }
        }
        driveOneForward();
        driveTo(goal, onLine);
    }

    public void driveTo(CircuitPosition goal) {
        driveTo(goal, true);
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
        if (!onLine) {
            getOnLine();
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
        System.out.println(currentPosition.name());
        onLine = false;

    }

        public void setPosition(CircuitPosition P) {
            currentPosition = P;
        }

        public void setOrientation(Orientation O) {
            currentOrientation = O;
        }

        private void getOnLine() {
            robot.travel(-5.5);
            onLine = true;
        }
}
