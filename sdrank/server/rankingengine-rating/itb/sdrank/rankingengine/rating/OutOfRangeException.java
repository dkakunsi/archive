package itb.sdrank.rankingengine.rating;

import itb.sdrank.exception.RankingException;

public class OutOfRangeException extends RankingException {
  private static final long serialVersionUID = 1L;

  public OutOfRangeException(String message) {
    super(message);
  }
}
