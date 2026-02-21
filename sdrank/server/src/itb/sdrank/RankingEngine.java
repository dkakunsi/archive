package itb.sdrank;

import java.util.List;

import itb.sdrank.exception.RankingException;
import itb.sdrank.model.Candidate;
import itb.sdrank.model.Ranking;
import itb.sdrank.model.Criteria;

public interface RankingEngine {
  Ranking rank(List<Candidate> candidates, Criteria criteria) throws RankingException;
}
