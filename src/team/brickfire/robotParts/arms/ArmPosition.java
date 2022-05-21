package team.brickfire.robotParts.arms;

import com.sun.xml.internal.bind.v2.runtime.unmarshaller.XsiNilLoader;

import java.lang.reflect.Array;

public enum ArmPosition {

    MAIN(new Movement[]{
            new ArmMovement(0),
            new ClawMovement(-80)
    }),

    CLAW_OPEN(new ClawMovement(-165)),

    CLAW_CLOSED(new ClawMovement(0)),

    DISCARD_EXTRA_BOTTLE(new Movement[]{
            new ArmMovement(-70),
            new ClawMovement(0),
            new ArmMovement(70),
    });

    private final Movement[] movements;

    private ArmPosition(Movement[] movements) {
        this.movements = movements;

    }

    private ArmPosition(Movement movement) {
        this.movements = new Movement[]{movement};
    }

    public Movement[] getMovements() {
        return movements;
    }
}
