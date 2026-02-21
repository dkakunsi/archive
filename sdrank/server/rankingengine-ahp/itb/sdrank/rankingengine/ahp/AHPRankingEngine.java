package itb.sdrank.rankingengine.ahp;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import itb.sdrank.RankingEngine;
import itb.sdrank.exception.RankingException;
import itb.sdrank.model.Candidate;
import itb.sdrank.model.Ranking;
import itb.sdrank.model.Criteria;

@Component(value = "ahpRankingEngine")
public class AHPRankingEngine implements RankingEngine {
  @Resource
  private AHPMatrixBuilder matrixBuilder;

	@Override
	public Ranking rank(List<Candidate> candidates, Criteria criteria) throws RankingException {
	  Map<String, Candidate> mapOfCandidates = new HashMap<>();
	  for (Candidate candidate : candidates) {
	    mapOfCandidates.put(candidate.getId(), candidate);
	  }
	  
	  Ranking ranking = new AHPRanking(criteria, mapOfCandidates, matrixBuilder);
	  ranking.rank();
		return ranking;
	}
}
