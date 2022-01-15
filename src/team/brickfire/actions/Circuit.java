package team.brickfire.actions;

import team.brickfire.robotParts.Robot;

public class Circuit extends BaseAction {

    private static final double ROTATION_SPEED = 500;

    private CircuitPosition currentPosition;
    private Orientation currentOrientation;

    /**
     * Creates an Circuit Object
     * @param robot The robot
     */
    public Circuit(Robot robot) {
        super(robot);
        currentPosition = CircuitPosition.NONE;
        currentOrientation = Orientation.NONE;
    }

    private int getPositionIndex(CircuitPosition position) {
        for (int i = 0; i < CircuitPosition.POSITION_ORDER.length; i++) {
            if (CircuitPosition.POSITION_ORDER[i] == position) {
                return i;
            }
        }
        return -1;
    }


    public void driveTo(CircuitPosition destination) {
        if (destination == currentPosition) {
            return;
        }
        int currentIndex = getPositionIndex(currentPosition);
        int direction = CircuitPosition.POSITION_ORDER[
                (currentIndex + currentPosition.distance(destination) % CircuitPosition.POSITION_ORDER.length)]
                == destination ? 1 : -1;
        driveToAdjacent(CircuitPosition.POSITION_ORDER[(currentIndex + direction)
                % CircuitPosition.POSITION_ORDER.length]);
        driveTo(destination);
    }

    public void turnTo(Orientation goalOrientation) {
        if (currentOrientation == goalOrientation) {
            return;
        }
        if (currentOrientation.getX() == goalOrientation.getX()
                && currentOrientation.getY() == currentOrientation.getY()) {
            robot.turn(180, ROTATION_SPEED);
        } else {
            int direction = currentOrientation.getX() * -goalOrientation.getY()
                    + currentPosition.getY() * goalOrientation.getX();
            robot.turn(direction * 90, ROTATION_SPEED);
        }
        currentOrientation = goalOrientation;
    }

    public void driveToAdjacent(CircuitPosition destination) {
        if (!destination.isAdjacent(currentPosition)) {
            return;
        }
        turnTo(Orientation.getOrientation(destination.getX() - currentPosition.getX(),
                destination.getY() - currentPosition.getY()));
        // TODO: drivng
        currentPosition = destination;
    }

    public CircuitPosition getCurrentPosition() {
        return currentPosition;
    }

    public void setCurrentPosition(CircuitPosition currentPosition) {
        this.currentPosition = currentPosition;
    }

    public Orientation getCurrentOrientation() {
        return currentOrientation;
    }

    public void setCurrentOrientation(Orientation currentOrientation) {
        this.currentOrientation = currentOrientation;
    }

}
