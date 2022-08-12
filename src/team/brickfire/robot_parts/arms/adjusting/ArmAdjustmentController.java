package team.brickfire.robot_parts.arms.adjusting;

import lejos.hardware.Button;
import lejos.hardware.Key;
import lejos.hardware.lcd.LCD;
import team.brickfire.robot_parts.arms.Arm;
import team.brickfire.robot_parts.arms.adjusting.ArmMovementCollection;

/**
 * <p>Class that fulfills the moving tasks for manually adjusting the {@link Arm arms}</p>
 * @param <T> The type of arm to adjust
 *
 * @version 1.0
 * @author Team BrickFire
 */
public class ArmAdjustmentController<T extends Arm & ArmMovementCollection> {

    private static int row = 0;
    private final T arm;
    private final int nextButton;
    private final int previousButton;
    private final int resetButton;


    /**
     * <p>Creates an ArmAdjustmentController</p>
     * @param arm {@link Arm Arm} to adjust
     * @param nextButton {@link Key Button} for moving to the next position
     * @param previousButton {@link Key Button} for moving to the previous position
     * @param resetButton {@link Key Button} for moving to the start position
     */
    public ArmAdjustmentController(T arm, Key nextButton, Key previousButton, Key resetButton) {
        this.arm = arm;
        this.nextButton = nextButton.getId();
        this.previousButton = previousButton.getId();
        this.resetButton = resetButton.getId();
        LCD.drawString(arm.getName() + ": Up: " + nextButton.getName() + " Down: " + previousButton.getName(), 1, row);
        row++;
    }

    /**
     * <p>Executes the given command if the id is a correct button</p>
     * @param buttonID ID of pressed button
     */
    public void notify(int buttonID) {
        if (buttonID == nextButton) {
            arm.next();
        } else if (buttonID == previousButton) {
            arm.previous();
        } else if (buttonID == resetButton) {
            arm.move(arm.getZero());
        }
    }

    /**
     * <p>Listens to the Bricks button presses and executes the given command when the button was pressed</p>
     * <p>This function will occupy the thread it is executed on</p>
     * @param cancelButton When this button gets pressed the listening will stop and the thread can continue
     */
    public void run(Key cancelButton) {
        int id = -1;
        while (id != cancelButton.getId()) {
            id = Button.waitForAnyPress();
            notify(id);
        }
    }

    /**
     * <p>Moves the arm to its defined 0 position</p>
     */
    public void moveZero() {
        arm.move(arm.getZero());
    }
}
