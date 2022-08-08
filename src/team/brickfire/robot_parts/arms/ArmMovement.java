package team.brickfire.robot_parts.arms;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * <p>The positions {@link Arm arm} can be in</p>
 *
 * @version 2.2
 * @author upoon
 */
public abstract class ArmMovement {

    protected int distance;
    protected double speed;

    private final List<ArmMovement> chain;


    /**
     * <p>Moves the {@link Arm arm} with the desired type<br>
     * Speed will be the arms default speed</p>
     *
     * @param distance Distance to move
     */
    protected ArmMovement(int distance) {
        this.distance = distance;
        this.speed = -1;
        this.chain = new ArrayList<>();
    }


    /**
     * <p>Moves the {@link Arm arm} with the desired type</p>
     *
     * @param distance Distance to move
     * @param speed Speed the arm should rotate at. Ranges from -100 - 100
     *
     */
    protected ArmMovement(int distance, double speed) {
        this.distance = distance * (speed > 0 ? 1 : -1);
        this.speed = Math.abs(speed);
        this.chain = new ArrayList<>();
    }

    /**
     * <p>Rotates the given {@link Arm arm} according to the movement</p>
     * <p><i>Package-private</i></p>
     *
     * @param arm Arm to rotate
     * @param immediateReturn whether the method should immediately return after starting the motor
     */
    void execute(Arm arm, boolean immediateReturn) {
        if (speed > 0) {
            arm.setSpeed(speed);
        } else {
            arm.setSpeed(arm.getStandardSpeed());
        }

        move(arm, immediateReturn);
        if (!immediateReturn) {
            for (ArmMovement armMovement : chain) {
                armMovement.execute(arm);
            }
        }
    }

    /**
     * <p>Rotates the given {@link Arm arm} according to the movement</p>
     * <p><i>Package-private</i></p>
     *
     * @param arm Arm to rotate
     */
    void execute(Arm arm) {
        execute(arm, false);
    }

    /**
     * <p>Executes these movements after this one was executed. <br>
     * Only does so if the movement is with immediateReturn = false</p>
     * <p>The difference to the add(RotateDistanceArmMovement... movements)-function: <br>
     * This stops the motor before starting the next one. This enables it to move the arm in one direction first and then
     * a different one at the next execution</p>
     * @param movements Movements to add
     * @return The ArmMovement with the other ones chained. This is a new object and not linked to the one the function was called on
     */
    public ArmMovement chain(ArmMovement... movements) {
        ArmMovement newMovement = this.copy();
        newMovement.chain.addAll(Arrays.asList(movements));
        return newMovement;
    }

    /**
     * <p>Adds a RotateDistanceArmMovement to this rotateTo movement<br>
     * The speed will the the slowest out of all Movements including this one</p>
     * <p>The difference to the chain(ArmMovement... movements)-function:<br>
     * This executes both movements without pausing in between, but if one movement is a positive distance
     * and the other is negative it wont move the longer one the whole distance</p>
     * @param movements {@link RotateDistanceArmMovement Movement} to add to this
     * @return The result of adding. This is a new object and not linked to the one the function was called on
     */
    public ArmMovement add(RotateDistanceArmMovement... movements) {
        ArmMovement newMovement = this.copy();
        for (RotateDistanceArmMovement movement : movements) {
            newMovement.distance += movement.distance;
            newMovement.speed = newMovement.speed > 0
                    ? (movement.speed > 0 ? Math.min(newMovement.speed, movement.speed) : newMovement.speed) : movement.speed;
        }
        return newMovement;
    }

    /**
     * <p>Moves the {@link Arm arm} as the class specified type</p>
     * @param arm Arm to move
     * @param immediateReturn If true the robot exits the method immediately after starting the arm
     */
    protected abstract void move(Arm arm, boolean immediateReturn);

    /**
     * <p>Creates a copy of this movement</p>
     * @return A copy unliked to this object or any of its contents
     */
    public abstract ArmMovement copy();

    @Override
    public String toString() {
        return getClass().getName() + " Distance: " + distance + " Speed: " + speed;
    }
}
