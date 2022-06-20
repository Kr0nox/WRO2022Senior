package team.brickfire.actions;

import lejos.robotics.Color;
import team.brickfire.robotParts.Robot;

// package-private
class ActionsSide extends BaseAction {

    private final ActionsLaundry laundry;
    private final ActionsWater water;

    public ActionsSide(Robot robot, ActionsLaundry laundry, ActionsWater water) {
        super(robot);
        this.laundry = laundry;
        this.water = water;
    }

    public void doSide() {
        // TODO: Drive to room starting point
        robot.alignLine(true, 25);
        robot.setLinearSpeed(110);
        robot.travel(-9);
        robot.turn(-90);

        robot.travel(4.5);
        robot.alignLine(false, 30);

        new ActionsRoom(robot, laundry, water).doRoom(true);

        robot.alignLine(false,35);

        new ActionsRoom(robot, laundry, water).doRoom(false);

        robot.travel(-11.5);
        robot.turn(-90);
    }


    /*private class ActionsRoom {

        private static final int GAME_ROOM_COLOR = Color.GREEN;
        private boolean isGameRoom;
        private final boolean isForward;

        private ActionsRoom(boolean forward) {
            this.isForward = forward;
        }

        private void doRoom() {

            scanRoomBlock();

            //laundry.collectBlock(isForward);
            ActionsGame game = new ActionsGame();
            ActionsWater water = new ActionsWater(robot);

            if (isGameRoom) {
                game.play();
            } else {
                game.skip();
                water.deliverWater(isForward);
            }

            //TODO: drive to corner
        }

        private void scanRoomBlock() {
            // TODO: Drive to block
            // robot.travel();
            // move arm
           // isGameRoom = robot.blockColorSensor().getColorID() == GAME_ROOM_COLOR;
        }


        private class ActionsGame {

            public void play() {

            }

            public void skip() {

            }
        }
    }*/
}
