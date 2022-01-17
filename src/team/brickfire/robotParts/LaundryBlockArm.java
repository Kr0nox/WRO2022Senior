package team.brickfire.robotParts;

import lejos.robotics.RegulatedMotor;
import team.brickfire.challengeParts.LaundryBlock;

public class LaundryBlockArm extends Arm<LaundryBlockArmStates> {

    private static final int MAX_LOADED_BLOCKS = 2;

    private final LaundryBlock[] loadedBlocks;

    public LaundryBlockArm(RegulatedMotor motor) {
        super(motor, 300);
        loadedBlocks = new LaundryBlock[MAX_LOADED_BLOCKS];
    }

    @Override
    public void rotateTo(LaundryBlockArmStates state) {
        LaundryBlockArmStates moveTo = state;
        motor.rotateTo(moveTo.getDegrees());
    }

    public void addLaundryBlock(LaundryBlock laundryBlock) {
        for (int i = 0; i < loadedBlocks.length; i++) {
            if (loadedBlocks[i] == null) {
                loadedBlocks[i] = laundryBlock;
                break;
            }
        }
    }

    public LaundryBlock getLastLoadedBlock() {
        for (int i = loadedBlocks.length - 1; i <= 0 ; i--) {
            if (loadedBlocks[i] != null) {
                return loadedBlocks[i];
            }
        }
        return null;
    }

    public void removeLaundryBlock() {
        for(int i = loadedBlocks.length - 1; i <= 0; i-- ) {
            if(loadedBlocks[i] != null) {
                loadedBlocks[i] = null;
                break;
            }
        }
    }

    public int getLaundryBlockNumber() {
        for (int i = 0; i < loadedBlocks.length; i++) {
            if (loadedBlocks[i] == null) {
                return i;
            }
        }
        return loadedBlocks.length;
    }
}
