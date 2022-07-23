package team.brickfire.actions;

import team.brickfire.actions.circuit_drive.CircuitDrive;
import team.brickfire.actions.circuit_drive.CircuitOrientation;
import team.brickfire.actions.circuit_drive.CircuitPosition;

public class TestingAction extends BaseAction {

    public TestingAction() {
        super();
    }

    public void test() {
        CircuitDrive circuit = new CircuitDrive(CircuitPosition.SOUTH_EAST, CircuitOrientation.WEST);

        circuit.driveTo(CircuitPosition.WEST, CircuitOrientation.NORTH);
    }
}
