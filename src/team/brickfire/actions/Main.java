package team.brickfire.actions;

import lejos.hardware.Button;
import team.brickfire.actions.circuit_drive.CircuitDrive;
import team.brickfire.actions.circuit_drive.CircuitOrientation;
import team.brickfire.actions.circuit_drive.CircuitPosition;
import team.brickfire.robot_parts.arms.WaterBottleArm;

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
        /*// drive to water bottles and pick them up
        waterBottleArm.move(WaterBottleArm.PICKUP,true);
        drive(4,100);
        turn(-44);
        setDrivingSpeed(60,100);
        drive(38);
        setDrivingSpeed(100);
        WaterBottleAction.getInstance();
        waterBottleArm.move(WaterBottleArm.OVER_TABLE);

        // drive to circuit starting position
        turnLeftWheel(-76);
        drive(2);

        CircuitDrive circuit = new CircuitDrive(this, CircuitPosition.NORTH, CircuitOrientation.EAST);
        circuit.driveTo(CircuitPosition.GREEN_CLOSE, CircuitOrientation.SOUTH);*/

        new Side(true).doSide();

        /*circuit.setPosition(CircuitPosition.EAST_ROOMS_CLOSE, CircuitOrientation.SOUTH);
        circuit.driveTo(CircuitPosition.YELLOW_CLOSE, CircuitOrientation.NORTH);

        new Side(false).doSide();

        circuit.setPosition(CircuitPosition.WEST_ROOMS_CLOSE, CircuitOrientation.NORTH);
        circuit.driveTo(CircuitPosition.SOUTH_WEST, CircuitOrientation.SOUTH);

        int basket = LaundryAction.getInstance().deliverBlocks();

        // TODO: drive to base*/

        Button.waitForAnyPress();
        waterBottleArm.move(WaterBottleArm.START);
    }

}
