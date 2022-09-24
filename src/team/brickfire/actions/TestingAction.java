package team.brickfire.actions;

import lejos.hardware.Button;
import lejos.utility.Delay;
import team.brickfire.data.color.AdvancedColor;
import team.brickfire.data.color.LaundryBlockColorMap;

import java.util.Arrays;
import java.util.concurrent.Delayed;

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
        new Side(true).doSide();
        /*int id = -1;
        do {
            AdvancedColor c = new AdvancedColor(colorSensorBlocks, new LaundryBlockColorMap());
            Button.waitForAnyPress();
            c.getErrorValues(colorSensorBlocks, new LaundryBlockColorMap());
            System.out.println(c.error());
            id = Button.waitForAnyPress();
        } while (id != Button.ID_ESCAPE);
*/
    }
}
