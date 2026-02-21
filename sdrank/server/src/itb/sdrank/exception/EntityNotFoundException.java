package itb.sdrank.exception;

public class EntityNotFoundException extends RepositoryException {

  private static final long serialVersionUID = 1L;

  public EntityNotFoundException(Throwable cause) {
    super(cause);
  }

  public EntityNotFoundException(String message) {
    super(message);
  }

}
