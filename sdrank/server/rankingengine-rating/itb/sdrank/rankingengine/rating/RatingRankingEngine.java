package itb.sdrank.rankingengine.rating;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import itb.sdrank.RankingEngine;
import itb.sdrank.exception.RankingException;
import itb.sdrank.model.Candidate;
import itb.sdrank.model.Criteria;
import itb.sdrank.model.Ranking;

@Component(value = "ratingRankingEngine")
public class RatingRankingEngine implements RankingEngine {

  @Override
  public Ranking rank(List<Candidate> candidates, Criteria criteria) throws RankingException {
    Map<String, Candidate> mapOfCandidates = new HashMap<>();
    for (Candidate candidate : candidates) {
      mapOfCandidates.put(candidate.getId(), new RatingCandidate(candidate));
    }
    
    Ranking ranking = new RatingRanking(criteria, mapOfCandidates);
    ranking.rank();
    return ranking;
  }

}
