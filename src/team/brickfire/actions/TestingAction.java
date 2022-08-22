package team.brickfire.actions;

import lejos.hardware.Button;
import team.brickfire.actions.circuit_drive.CircuitDrive;
import team.brickfire.actions.circuit_drive.CircuitOrientation;
import team.brickfire.actions.circuit_drive.CircuitPosition;
import team.brickfire.robot_parts.arms.ArmMovement;
import team.brickfire.robot_parts.arms.BlockArm;
import team.brickfire.robot_parts.arms.WaterBottleArm;

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
        waterBottleArm.move(WaterBottleArm.PICKUP);
        Button.waitForAnyPress();
        waterBottleArm.move(WaterBottleArm.OVER_TABLE);

        new Side(true).doSide();

        Button.waitForAnyPress();
        blockArm.move(BlockArm.HIGHEST);
        waterBottleArm.move(WaterBottleArm.START);
    }
}
