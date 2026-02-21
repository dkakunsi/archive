package itb.sdrank.exception;

public class RankingException extends SDRankException {
	private static final long serialVersionUID = 1L;

	public RankingException(Throwable cause) {
		super(cause);
	}

	public RankingException(String message) {
		super(message);
	}
}
