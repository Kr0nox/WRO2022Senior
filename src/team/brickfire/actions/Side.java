package team.brickfire.actions;

import team.brickfire.data.color.AdvancedColor;
import team.brickfire.data.color.Color;
import team.brickfire.data.color.LaundryBlockColorMap;
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
    private final boolean east;

    /**
     * <p>Creates a new Side object</p>
     *
     * @param east Whether the side is on the east side of the playing field
     */
    public Side(boolean east) {
        this.east = east;
        this.laundryAction = LaundryAction.getInstance();
        this.waterBottleAction = WaterBottleAction.getInstance();
    }

    /**
     * <p>Performs the side action</p>
     */
    public void doSide() {
        new Room(true).doRoom();
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
         * End position is on the crossing of the central east-west-line and the room-block-line, the back
         * facing the room<br>
         * End position is on the crossing of the central east-west-line and the room-block-line</p>
         */
        private void doRoom() {
            alignTrigonometry(20);
            setDrivingSpeed(100, 150);
            if (east) {
                if (thingsOnLeft) {
                    // green
                    drive(12.8);
                } else {
                    // red
                    drive(11.5);
                }
            } else {
                if (thingsOnLeft) {
                    // yellow
                    drive(12);
                } else {
                    // blue
                    drive(12.8);
                }
            }

            this.roomColor = colorSensorBlocks.getColor(new RoomBlockColorMap(), 10);

            System.out.println("Room color: " + roomColor);
            blockArm.move(BlockArm.LOWEST.add(BlockArm.OPEN));


            if (roomColor == Color.WHITE) {
                waterBottleAction.deliverBottle(thingsOnLeft);
            } else {
                drive(5.5, 60);
                blockArm.move(BlockArm.NUDGE);
                setDrivingSpeed(80, 150);
                drive(11.5);
                AdvancedColor c = new AdvancedColor(colorSensorBlocks, new LaundryBlockColorMap());
                blockArm.move(BlockArm.HIGHEST);
                laundryAction.enterScan(c);
                playGame();
            }
        }

        /**
         * <p>Plays the game</p>
         */
        private void playGame() {
            setDrivingSpeed(100, 200);
            // Collect Ball
            blockArm.move(BlockArm.LOWEST.add(BlockArm.OPEN));
            drive(9);
            blockArm.move(BlockArm.BASKET);
            // Drop ball off
            if (thingsOnLeft) {
                turnLeftWheel(80);
            } else {
                turnRightWheel(85);
            }
            drive(15);
            blockArm.move(BlockArm.DROP_BALL);
            blockArm.move(BlockArm.MIDDLE, true);
            //drive back
            drive(-10);
            setTurningSpeed(100, 150);
            turn (thingsOnLeft ? -110 : 110);
            setDrivingSpeed(100, 200);
            drive(41);
            turn(thingsOnLeft ? 20 : -18);
        }
    }
}
