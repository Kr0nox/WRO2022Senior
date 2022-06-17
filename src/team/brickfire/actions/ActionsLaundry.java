package team.brickfire.actions;

import lejos.robotics.Color;
import team.brickfire.actions.dataTypes.BasketPosition;
import team.brickfire.challengeParts.LaundryBasket;
import team.brickfire.robotParts.Robot;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

// package-private
public class ActionsLaundry extends BaseAction {

    private final Map<LaundryBasket, BasketPosition> laundryBaskets;
    private static final LaundryBasket[] POSSIBLE_BASKETS = new LaundryBasket[]{new LaundryBasket(Color.BLACK),
            new LaundryBasket(Color.YELLOW), new LaundryBasket(Color.RED)};
    private BasketPosition position;

    //private Stack<Integer>

    /**
     * Creates an ActionsLaundry Object
     *
     * @param robot The robot
     */
    public ActionsLaundry(Robot robot) {
        super(robot);
        laundryBaskets = new HashMap<>();
    }

    public void collectBlock(boolean forward) {
        if (forward == true) {
            robot.arm().moveArmTo(0);
            robot.travel(-7);
            robot.arm().moveArmTo(90);
            robot.scanner().getColorID();
            robot.arm().closeClawTo(100);
            robot.travel(2);
            robot.arm().moveArmTo(160);
            robot.travel(-4.15);
            robot.arm().moveArmTo(-110);
            robot.travel(-10);
        } else {
            robot.arm().moveArmTo(0);
            robot.travel(-2.8);
            robot.arm().closeClawTo(105);
            robot.arm().moveArmTo(-90);
            robot.travel(-4.6);
            robot.scanner().getColorID();
            robot.travel(-12.15);
        }
    }

    public void deliverLaundry() {
        robot.arm().closeClawTo(140);
        robot.travel(-13.5);
        robot.turn(90);
        robot.travel(-36);
        robot.turn(90);

        robot.arm().moveArmTo(-150);
        robot.travel(8);
        robot.arm().moveArmTo(-110);
        robot.arm().moveArmTo(0);
        robot.arm().closeClawTo(53);
        robot.travel(-4);
        robot.arm().moveArmTo(300);
        robot.travel(-10);
        robot.arm().closeClawTo(120);
        robot.travel(5.5);
        robot.arm().moveArmTo(-150);
        robot.travel(6);
        robot.arm().moveArmTo(0);
        robot.arm().closeClawTo(53);
        robot.travel(-2);
        robot.arm().moveArmTo(-300);
        robot.travel(-10);
        robot.arm().moveArmTo(-190);
        robot.arm().closeClawTo(120);
        robot.travel(-2,150);
        robot.travel(-7,100);

        robot.travel(16);
        robot.arm().moveArmTo(-90);
        robot.arm().closeClawTo(53);
        robot.motorRight().rotate(-115);
        robot.travel(-11);
        robot.arm().closeClawTo(120);
    }
}
