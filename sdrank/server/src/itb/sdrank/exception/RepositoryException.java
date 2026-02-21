package itb.sdrank.exception;

public class RepositoryException extends SDRankException {
	private static final long serialVersionUID = 1L;

	public RepositoryException(Throwable cause) {
		super(cause);
	}

	public RepositoryException(String message) {
		super(message);
	}
}
