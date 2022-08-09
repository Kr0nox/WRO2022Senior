package team.brickfire.actions;

import team.brickfire.data.color.Color;
import team.brickfire.data.color.RoomBlockColorMap;

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
        // TODO: drive to other room
        new Room(false).doRoom();
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
         * Starting position is scanning the room block <br>
         * End position is on the crossing of the central east-west-line and the room-block-line</p>
         */
        private void doRoom() {
            this.roomColor = colorSensorBlocks.getMappedColor(new RoomBlockColorMap(), 10);

            // TODO: Drive to laundry block
            laundryAction.scanBlock();

            if (roomColor == Color.WHITE) {
                waterBottleAction.deliverBottle(thingsOnLeft);
            } else {
                playGame();
            }
        }

        /**
         * <p>Plays the game</p>
         */
        private void playGame() {
            // TODO: Collect Ball
            // TODO: Drop ball off
            // TODO: Leave room
        }

    }

}
