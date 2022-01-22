package team.brickfire.robotParts.arms;

import lejos.robotics.RegulatedMotor;
import team.brickfire.challengeParts.LaundryBlock;
import team.brickfire.robotParts.arms.dataTypes.StatesArmLaundryBlock;

import java.util.ArrayDeque;
import java.util.Deque;

public class ArmLaundryBlock extends Arm<StatesArmLaundryBlock> {

    private static final int MAX_LOADED_BLOCKS = 2;

    private final Deque<LaundryBlock> loadedBlocks;

    public ArmLaundryBlock(RegulatedMotor motor) {
        super(motor, 300);
        loadedBlocks = new ArrayDeque<>();
    }

    @Override
    public void rotateTo(StatesArmLaundryBlock state) {
        motor.rotateTo(state.getDegrees());
    }

    public void addLaundryBlock(LaundryBlock laundryBlock) {
        if (loadedBlocks.size() < MAX_LOADED_BLOCKS) {
            loadedBlocks.addFirst(laundryBlock);
        }
    }

    public LaundryBlock getLastLoadedBlock() {
        return loadedBlocks.peekFirst();
    }

    public void removeLaundryBlock() {
        loadedBlocks.removeFirst();
    }

    public int getLaundryBlockNumber() {
        return loadedBlocks.size();
    }
}
