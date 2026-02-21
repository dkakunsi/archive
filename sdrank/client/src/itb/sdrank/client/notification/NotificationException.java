package itb.sdrank.client.notification;

public class NotificationException extends Exception {

    private static final long serialVersionUID = 1L;

    public NotificationException() {
	super();
    }

    public NotificationException(String message) {
	super(message);
    }

    public NotificationException(Throwable cause) {
	super(cause);
    }
}
