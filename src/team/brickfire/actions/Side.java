package team.brickfire.actions;

import team.brickfire.data.color.Color;
import team.brickfire.data.color.RoomBlockColorMap;
import team.brickfire.robot_parts.arms.BlockArm;

/**
 * <p>Action for doing a side of the playing field</p>
 *
 * @version 2.0
 * @author Team BrickFire
 */
public class Side extends BaseAction {

    private final LaundryAction laundryAction;
    private final WaterBottleAction waterBottleAction;

    /**
     * <p>Creates a new Side object</p>
     */
    public Side() {
        this.laundryAction = LaundryAction.getInstance();
        this.waterBottleAction = WaterBottleAction.getInstance();
    }

    /**
     * <p>Performs the side action</p>
     */
    public void doSide() {
        new Room(true).doRoom();
        //new Room(false).doRoom();
    }

    /**
     * <p>Class representing a room</p>
     *
     * @version 2.0
     * @author Team BrickFire
     */
    private final class Room {

        private Color roomColor;
        private final boolean thingsOnLeft;

        /**
         * <p>Creates a new Room object</p>
         * @param thingsOnLeft Side the table is on
         */
        private Room(boolean thingsOnLeft) {
            this.thingsOnLeft = thingsOnLeft;
        }

        /**
         * <p>Performs the room action <br>
         * End position is on the crossing of the central east-west-line and the room-block-line, the back
         * facing the room<br>
         * End position is on the crossing of the central east-west-line and the room-block-line</p>
         */
        private void doRoom() {
            drive(-2.5, 100);

            this.roomColor = colorSensorBlocks.getMappedColor(new RoomBlockColorMap(), 10);

            System.out.println(roomColor);
            blockArm.move(BlockArm.LOWEST.add(BlockArm.OPEN));


            if (roomColor == Color.WHITE) {
                waterBottleAction.deliverBottle(thingsOnLeft);
            } else {
                drive(-10, 60, true);
                while (getDistance() < 6);
                blockArm.move(BlockArm.NUDGE);
                setDrivingSpeed(60, 100);
                drive(-6);
                laundryAction.scanBlock();
                blockArm.move(BlockArm.HIGHEST);
                playGame();
            }
        }

        /**
         * <p>Plays the game</p>
         */
        private void playGame() {
            // Collect Ball
            blockArm.move(BlockArm.LOWEST.add(BlockArm.OPEN));
            drive(-9.5, 75);
            blockArm.move(BlockArm.BASKET);
            // Drop ball off
            turn(thingsOnLeft ? -68 : 68);
            drive(-18, 100);
            blockArm.move(BlockArm.DROP_BALL);
            // TODO: Leave room
            drive(19, true);
            blockArm.move(BlockArm.MIDDLE, true);
            while (isMoving());
            turn(thingsOnLeft ? -115 : 115);
        }

    }

}
