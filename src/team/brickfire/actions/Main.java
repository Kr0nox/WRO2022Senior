package team.brickfire.actions;

import lejos.hardware.Button;
import team.brickfire.actions.circuit_drive.CircuitDrive;
import team.brickfire.actions.circuit_drive.CircuitOrientation;
import team.brickfire.actions.circuit_drive.CircuitPosition;
import team.brickfire.robot_parts.arms.BlockArm;
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
        // collect bottles
        waterBottleArm.move(WaterBottleArm.PICKUP,true);
        setDrivingSpeed(50,100);
        setTurningSpeed(50,100);
        drive(-6.5);
        turn(-42);
        drive(-26.5);
        waterBottleArm.move(WaterBottleArm.OVER_TABLE);

        // drive to side
        turnRightWheel(-63);
        setDrivingSpeed(100, 250);
        drive(87);
        turnLeftWheel(-65);


        new Side(true).doSide();


        // Switch Sides
        alignTrigonometry(20);
        turnRightWheel(88);
        setDrivingSpeed(100, 250);
        drive(17);
        alignTrigonometry(20);
        turn(5);
        setDrivingSpeed(100, 200);
        drive(105);
        alignTrigonometry(20);
        setDrivingSpeed(80, 150);
        drive(9);
        turnLeftWheel(-87);


        new Side(false).doSide();

        // drive to laundry baskets
        alignTrigonometry(30);
        setDrivingSpeed(100,200);
        turn(88,100);
        drive(20);
        alignTrigonometry(20);
        setDrivingSpeed(100,200);
        drive(33);
        turn(90);
        drive(27);
        alignTrigonometry(20);
        setDrivingSpeed(50, 120);
        drive(-2.5);
        turn(-88,30);
        drive(-4);

        LaundryAction.getInstance().deliverBlocks();

        // drive to base
        turnLeftWheel(122);
        drive(24.5, 100);

        waterBottleArm.move(WaterBottleArm.START,true);
        blockArm.move(BlockArm.HIGHEST);
    }

}
