package team.brickfire.actions.circuit_drive;

import team.brickfire.actions.BaseAction;

import java.util.List;

public class CircuitDrive extends BaseAction {

    private static final double DRIVING_SPEED = 100;
    private static final double TURNING_SPEED = 100;

    private final CircuitGraph circuit;
    private CircuitOrientation facing;
    private CircuitPosition position;

    public CircuitDrive(CircuitPosition startingPosition, CircuitOrientation startingOrientation) {
        super();
        this.facing = startingOrientation;
        this.position = startingPosition;
        this.circuit = new CircuitGraph();
    }

    CircuitDrive() {
        super(new Object());
        circuit = new CircuitGraph();
    }

    public void driveTo(CircuitPosition goalPosition) {
        for(GraphNode node : circuit.shortestPath(circuit.getNodeFromIdentifier(position),
                circuit.getNodeFromIdentifier(goalPosition))) {
            driveStraightDistance(node.getPosition());
        }
    }

    public void driveTo(CircuitPosition goalPosition, CircuitOrientation goalOrientation) {
        driveTo(goalPosition);
        turnTo(goalOrientation);
    }

    public void turnTo(CircuitOrientation goalOrientation) {
        if (facing == goalOrientation) {
            return;
        }
        if (goalOrientation.getX() == facing.getX() || goalOrientation.getY() == facing.getY()) {
            turn(180, TURNING_SPEED);
        }

        int direction = facing.getX() * -goalOrientation.getY()
                + facing.getY() * goalOrientation.getX();
        turn(direction * -90, TURNING_SPEED);

        facing = goalOrientation;
    }

    private void driveStraightDistance(CircuitPosition goalPosition) {
        turnTo(CircuitOrientation.get(goalPosition.getX() - position.getX(),goalPosition.getY() - position.getY()));

        lineFollowing(goalPosition.getDistance(position), DRIVING_SPEED);
        position = goalPosition;
    }

    public CircuitPosition[] simplifyPath(List<GraphNode> path) {
        for (int i = 0; i < path.size() - 2; i++) {
            GraphNode one = path.get(i);
            GraphNode two = path.get(i + 1);
            GraphNode three = path.get(i + 2);

            int otx = (int)Math.signum(two.getPosition().getX() - one.getPosition().getX());
            int oty = (int)Math.signum(two.getPosition().getY() - one.getPosition().getY());
            int ttx = (int)Math.signum(three.getPosition().getX() - two.getPosition().getX());
            int tty = (int)Math.signum(three.getPosition().getY() - two.getPosition().getY());

            if (otx == ttx && oty == tty) {
                path.remove(two);
                i--;
            }
        }
        CircuitPosition[] a = new CircuitPosition[path.size() - 1];

        for (int i = 0; i < a.length; i++) {
            a[i] = path.get(i + 1).getPosition();
        }

        return a;
    }

}
