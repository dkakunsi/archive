package itb.sdrank.client.command;

public class CommandException extends Exception {

    private static final long serialVersionUID = 1L;

    public CommandException() {
	super();
    }

    public CommandException(String message) {
	super(message);
    }

    public CommandException(Throwable e) {
	super(e);
    }

}
