package team.brickfire.actions;

import team.brickfire.robotParts.Robot;

public class ActionsRoom extends BaseAction {

    private boolean isGameRoom;

    /**
     * Creates an ActionsRoom Object
     * @param robot The robot
     */
    public ActionsRoom(Robot robot) {
        super(robot);
    }

    public void doRoom(boolean forward) {

        scanRoomBlock();

        ActionsLaundry laundry = new ActionsLaundry(robot);
        laundry.collectBlock(forward);
        ActionsGame game = new ActionsGame(robot);
        ActionsWater water = new ActionsWater(robot);

        if (isGameRoom) {
            game.play(forward);
            water.skip(forward);
        } else {
            game.skip(forward);
            water.deliver(forward);
        }

        //TODO: drive to corner
    }

    public void scanRoomBlock() {

    }
}
