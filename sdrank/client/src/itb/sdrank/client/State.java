package itb.sdrank.client;

public enum State {
    WAITING, REQUESTING, ACCESSING, ERROR, EXIT;

    private static State currentState;

    public static State getState() {
	return currentState;
    }

    public static void setState(State state) {
	State.currentState = state;
    }

    public static void print() {
	System.out.println(currentState);
    }

}
