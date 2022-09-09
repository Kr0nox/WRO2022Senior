package team.brickfire.actions;

import lejos.hardware.Button;
import lejos.utility.Delay;
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
    private final boolean east;

    /**
     * <p>Creates a new Side object</p>
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
            alignTrigonometry(20);
            setDrivingSpeed(100, 100);
            drive(thingsOnLeft && east || !thingsOnLeft && !east ? 12.5 : 12, 100);

            this.roomColor = colorSensorBlocks.getMappedColor(new RoomBlockColorMap(), 10);

            System.out.println(roomColor);
            blockArm.move(BlockArm.LOWEST.add(BlockArm.OPEN));


            if (roomColor == Color.WHITE) {
                waterBottleAction.deliverBottle(thingsOnLeft);
            } else {
                drive(10, 60, true);
                while (getDistance() < 6);
                blockArm.move(BlockArm.NUDGE);
                setDrivingSpeed(100, 200);
                drive(6.5);
                blockArm.move(BlockArm.CLOSE);
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
            drive(9.25, 100);
            blockArm.move(BlockArm.BASKET);
            // Drop ball off
            if (thingsOnLeft) {
                turnLeftWheel(80);
            } else {
                turnRightWheel(92);
            }
            drive(14, 100);
            blockArm.move(BlockArm.DROP_BALL);
            blockArm.move(BlockArm.MIDDLE, true);
            drive(-30);
            System.out.println(thingsOnLeft);
            if (thingsOnLeft) {
                turnLeftWheel(88);
            } else {
                turnRightWheel(90);
            }
            setDrivingSpeed(100,100);
            drive(29);
        }
    }
}
