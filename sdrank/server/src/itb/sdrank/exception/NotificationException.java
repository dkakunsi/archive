package itb.sdrank.exception;

public class NotificationException extends SDRankException {

  private static final long serialVersionUID = 1L;

  public NotificationException(String message) {
    super(message);
  }

  public NotificationException(Throwable cause) {
    super(cause);
  }

}
