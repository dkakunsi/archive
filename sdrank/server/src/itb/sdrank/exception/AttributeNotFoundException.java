package itb.sdrank.exception;

public class AttributeNotFoundException extends SDRankException {

  private static final long serialVersionUID = 1L;

  public AttributeNotFoundException(String message) {
    super(message);
  }

  public AttributeNotFoundException(Throwable cause) {
    super(cause);
  }
}
