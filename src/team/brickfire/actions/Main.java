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
        //collect bottles
        /*waterBottleArm.move(WaterBottleArm.PICKUP,true);
        setDrivingSpeed(50,80);
        setTurningSpeed(50,100);
        drive(-6.5);
        turn(-42);
        drive(-26.5);
        waterBottleArm.move(WaterBottleArm.OVER_TABLE);

        turnRightWheel(-62);
        drive(87,100);
        turnLeftWheel(-70);


        new Side(true).doSide();


        // Switch Sides
        alignTrigonometry(20);
        turnRightWheel(92.5);
        drive(128, 100);
        alignTrigonometry(20);
        drive(9,80);
        turnLeftWheel(-87);

        new Side(false).doSide();*/


        alignTrigonometry(20);
        setDrivingSpeed(100,200);
        turn(88,100);
        drive(65);
        turn(90);
        drive(27);
        alignTrigonometry(20);
        drive(-6,50);
        turn(-88,30);
        drive(-4.5);

        // TODO: drive to base

        Button.waitForAnyPress();
        waterBottleArm.move(WaterBottleArm.START);
        blockArm.move(BlockArm.HIGHEST);
    }

}
