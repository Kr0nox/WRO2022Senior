package team.brickfire.actions;

import lejos.hardware.Button;
import lejos.hardware.lcd.LCD;
import team.brickfire.robot_parts.arms.adjusting.ArmAdjustmentController;
import team.brickfire.robot_parts.arms.BlockArm;
import team.brickfire.robot_parts.arms.WaterBottleArm;

/**
 * <p>Can be used before the actual program starts to bring the arms into the desired position</p>
 *
 * @version 1.0
 * @author Team BrickFire
 */
public class AdjustArmAction extends BaseAction {

    private final ArmAdjustmentController<WaterBottleArm> waterAdjust;
    private final ArmAdjustmentController<BlockArm> blockAdjust;

    /**
     * <p>Creates an AdjustArmAction</p>
     */
    public AdjustArmAction() {
        super();

        blockAdjust = new ArmAdjustmentController<>(blockArm, Button.UP, Button.LEFT, Button.ESCAPE);
        waterAdjust = new ArmAdjustmentController<>(waterBottleArm, Button.RIGHT, Button.LEFT, Button.ESCAPE);
    }

    /**
     * <p>Executes arm adjustment for both the robots arms until the center button gets pressed</p>
     */
    public void run() {
        int id = -1;
        while (id != Button.ENTER.getId()) {
            Button.waitForAnyPress();
            waterAdjust.notify(id);
            blockAdjust.notify(id);
        }

        LCD.clear();
    }

    /**
     * <p>Moves both arms to the position they were in when the program was started</p>
     */
    public void moveZero() {
        blockArm.move(BlockArm.ZERO);
        waterBottleArm.move(WaterBottleArm.ZERO);
    }

    /**
     * <p>Waits for the any button to be pressed</p>
     * <p>Should be used on competition day</p>
     */
    public void skip() {
        Button.waitForAnyPress();
    }

}
