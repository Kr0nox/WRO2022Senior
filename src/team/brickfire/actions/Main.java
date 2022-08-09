package team.brickfire.actions;

import team.brickfire.actions.circuit_drive.CircuitDrive;
import team.brickfire.actions.circuit_drive.CircuitOrientation;
import team.brickfire.actions.circuit_drive.CircuitPosition;

/**
 * <p>Main Action</p>
 *
 * @version 2.0
 * @author Team Brickfire
 */
public class Main extends BaseAction {

    private static Main instance;

    /**
     * <p>Creates the main Action event</p>
     */
    protected Main() {
        super();
    }

    /**
     * <p>Returns the instance of the main Action</p>
     * <p><i>Singleton-Pattern</i></p>
     * @return The instance of the main Action
     */
    public static Main getInstance() {
        if (instance == null) {
            instance = new Main();
        }
        return instance;
    }

    /**
     * <p>Executes the main action</p>
     */
    public void execute() {
        // TODO: drive to water bottles and pick them up
        WaterBottleAction.getInstance();

        // TODO: drive to circuit starting position
        CircuitDrive circuit = new CircuitDrive(this, CircuitPosition.NORTH, CircuitOrientation.EAST);
        circuit.driveTo(CircuitPosition.GREEN_CLOSE, CircuitOrientation.SOUTH);

        new Side().doSide();

        circuit.setPosition(CircuitPosition.EAST_ROOMS_CLOSE, CircuitOrientation.SOUTH);
        circuit.driveTo(CircuitPosition.YELLOW_CLOSE, CircuitOrientation.NORTH);

        new Side().doSide();

        circuit.setPosition(CircuitPosition.WEST_ROOMS_CLOSE, CircuitOrientation.NORTH);
        circuit.driveTo(CircuitPosition.SOUTH_WEST, CircuitOrientation.SOUTH);

        int basket = LaundryAction.getInstance().deliverBlocks();

        // TODO: drive to base
    }

}
