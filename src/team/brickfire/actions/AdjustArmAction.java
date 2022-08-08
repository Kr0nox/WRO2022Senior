package team.brickfire.actions;

import lejos.hardware.Button;
import lejos.hardware.Key;
import lejos.hardware.lcd.LCD;
import team.brickfire.robot_parts.arms.adjusting.ArmAdjustmentController;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>Can be used before the actual program starts to bring the {@link team.brickfire.robot_parts.arms.Arm arms}
 * into the desired position</p>
 *
 * @version 1.0
 * @author Team BrickFire
 */
public class AdjustArmAction extends BaseAction {
    private final List<ArmAdjustmentController> armControllers;
    private final int stopButton;

    /**
     * <p>Creates an AdjustArmAction</p>
     *
     * @param stopButton Button that stops the run() function
     */
    public AdjustArmAction(Key stopButton) {
        super();
        this.stopButton = stopButton.getId();
        this.armControllers = new ArrayList<>();
        addArmAdjustController(new ArmAdjustmentController<>(blockArm, Button.UP, Button.LEFT, Button.ESCAPE));
        addArmAdjustController(new ArmAdjustmentController<>(waterBottleArm, Button.RIGHT, Button.LEFT, Button.ESCAPE));
    }

    /**
     * <p>Adds an {@link ArmAdjustmentController ArmAdjustController}</p>
     *
     * @param a ArmAdjustController to add
     * @return True if it was added successfully, false otherwise
     */
    public boolean addArmAdjustController(ArmAdjustmentController a) {
        return armControllers.add(a);
    }

    /**
     * <p>Executes arm adjustment for both the robots arms until the stop button gets pressed</p>
     */
    public void run() {
        int id = -1;
        while (id != stopButton) {
            Button.waitForAnyPress();
            for (ArmAdjustmentController armController : armControllers) {
                armController.notify(id);
            }
        }

        LCD.clear();
    }

    /**
     * <p>Moves both arms to the position they were in when the program was started</p>
     */
    public void moveZero() {
        for (ArmAdjustmentController armController : armControllers) {
            armController.moveZero();
        }
    }

    /**
     * <p>Waits for the any button to be pressed</p>
     * <p>Should be used on competition day</p>
     */
    public void skip() {
        Button.waitForAnyPress();
    }

}
