package team.brickfire.actions;

public class TestingAction extends BaseAction {

    public TestingAction() {
        super();
    }

    public void test() {
        drive(10);
        turn(90);
        lineFollowing(100, 50);
    }
}
