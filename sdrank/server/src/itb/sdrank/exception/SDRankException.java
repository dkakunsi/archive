package itb.sdrank.exception;

public class SDRankException extends Exception {
	private static final long serialVersionUID = 1L;

	public SDRankException(String message) {
		super(message);
	}

	public SDRankException(Throwable cause) {
		super(cause);
	}
}
