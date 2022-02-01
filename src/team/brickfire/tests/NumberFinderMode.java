package team.brickfire.tests;

public enum NumberFinderMode {

    VALUE {
        @Override
        public NumberFinderMode change(int index, int max) {
            if (index < 0) {
                return CHANGE_AMOUNT;
            }
            if (index > max) {
                return START;
            }
            return this;
        }
    },
    START {
        @Override
        public NumberFinderMode change(int index, int max) {
            if (index > 0) {
                return CHANGE_AMOUNT;
            }
            if (index < 0) {
                return VALUE;
            }
            return this;
        }
    },
    CHANGE_AMOUNT {
        @Override
        public NumberFinderMode change(int index, int max) {
            if (index < 0) {
                return START;
            }
            if (index > max) {
                return VALUE;
            }
            return this;
        }
    };

    public abstract NumberFinderMode change(int index, int max);

}
