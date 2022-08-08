package team.brickfire.actions;

import team.brickfire.actions.circuit_drive.CircuitDrive;
import team.brickfire.actions.circuit_drive.CircuitOrientation;
import team.brickfire.actions.circuit_drive.CircuitPosition;

/**
 * <p>Action is used for testing</p>
 *
 * @version 1.0
 * @author Team BrickFire
 */
public class TestingAction extends BaseAction {

    /**
     * <p>Creates the testing action</p>
     */
    public TestingAction() {
        super();
    }

    /**
     * <p>Gets executed</p>
     */
    public void test() {
        CircuitDrive circuit = new CircuitDrive(this, CircuitPosition.SOUTH_EAST, CircuitOrientation.WEST);

        circuit.driveTo(CircuitPosition.WEST, CircuitOrientation.NORTH);
    }
}
