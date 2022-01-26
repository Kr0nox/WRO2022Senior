package team.brickfire.actions;

import lejos.robotics.Color;
import team.brickfire.actions.dataTypes.BasketPosition;
import team.brickfire.challengeParts.LaundryBasket;
import team.brickfire.robotParts.Robot;

import java.util.HashMap;
import java.util.Map;

// package-private
class ActionsLaundry extends BaseAction {

    private final Map<LaundryBasket, BasketPosition> laundryBaskets;
    private static final LaundryBasket[] POSSIBLE_BASKETS = new LaundryBasket[]{new LaundryBasket(Color.BLACK),
            new LaundryBasket(Color.YELLOW), new LaundryBasket(Color.RED)};
    private BasketPosition position;

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
        // drive to block
        /* if (there is a block) {
            robot.armLaundryBlock().addLaundryBlock(new LaundryBlock(robot.blockColorSensor().getColorID()));
            // Pick up block
            if (robot.armLaundryBlock().getLaundryBlockNumber() >= 1) {
            } else {
            }
        } */
    }

    public void dropOff() {
        position = BasketPosition.CIRCUIT_SOUTH;
        if (laundryBaskets.isEmpty()) {
            scanBaskets();
        }
        // Deliver them
        while (robot.armLaundryBlock().getLaundryBlockNumber() > 0) {
            driveToBasket(getNextBasket());
            // arm movement
            robot.armLaundryBlock().removeLaundryBlock();
        }
        driveToBasket(BasketPosition.CIRCUIT_SOUTH);
    }

    private void driveToBasket(BasketPosition destination) {
        robot.travel(destination.getDistance() - position.getDistance());
        position = destination;
    }

    private BasketPosition getNextBasket() {
        LaundryBasket correspondingBasket = new LaundryBasket(robot.armLaundryBlock().getLastLoadedBlock().getColor());
        if (laundryBaskets.containsKey(correspondingBasket)) {
            return laundryBaskets.get(correspondingBasket);
        }

        return position.getDropOff();
    }

    // TODO: finish this
    private void scanBaskets() {
        for (BasketPosition b : new BasketPosition[]{BasketPosition.SCAN_CENTER, BasketPosition.SCAN_EAST}) {
            driveToBasket(b);
            laundryBaskets.put(new LaundryBasket(robot.laundryBasketColorSensor().getColorID()), b.getDropOff());
        }

        // Calculate last Color
        for (LaundryBasket b : POSSIBLE_BASKETS) {
            if (!laundryBaskets.containsKey(b)) {
                laundryBaskets.put(b, BasketPosition.SCAN_WEST.getDropOff());
                break;
            }
        }
    }
}
