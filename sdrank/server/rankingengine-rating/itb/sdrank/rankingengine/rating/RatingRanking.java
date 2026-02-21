package itb.sdrank.rankingengine.rating;

import java.util.HashMap;
import java.util.Map;

import itb.sdrank.exception.AttributeNotFoundException;
import itb.sdrank.exception.RankingException;
import itb.sdrank.model.Candidate;
import itb.sdrank.model.Criteria;
import itb.sdrank.model.QualityField;
import itb.sdrank.model.Ranking;
import itb.sdrank.model.RankingItem;

public class RatingRanking extends Ranking {
  private Map<String, Float> weight;

  public RatingRanking() {
    super();
    this.method = RankingMethod.RATING;
  }

  public RatingRanking(Criteria criteria, Map<String, Candidate> candidates) {
    super(criteria, candidates);
    this.method = RankingMethod.RATING;
  }

  public RatingRanking(Ranking ranking) {
    super(ranking);
    this.weight = new HashMap<>();
  }

  @Override
  public void rank() throws RankingException {
    RatingCandidate candidate;
    RatingCounter rtRater = getRatingCounter(QualityField.RESPONSE_TIME);
    RatingCounter ctRater = getRatingCounter(QualityField.COST);
    RatingCounter pcRater = getRatingCounter(QualityField.PRECISION);
    getWeight();
    cleanItems();

    for (Map.Entry<String, Candidate> entry : candidates.entrySet()) {
      candidate = (RatingCandidate) entry.getValue();
      candidate.countQualityScore(rtRater, ctRater, pcRater, criteria.getLocation());
      items.add(scoreCandidate(candidate));
    }
  }

  private RankingItem scoreCandidate(RatingCandidate candidate) {
    float score = 0f;
    for (QualityField field : QualityField.values()) {
      score += weight.get(field.getValue()) * candidate.getQualityScores().get(field.getValue());
    }

    return new RankingItem(id, candidate.getId(), score, candidate.getEndpoint(),
        candidate.getAttribute());
  }

  private Map<String, Float> getWeight() {
    if (weight == null) {
      Map<String, Float> map = new HashMap<>();
      map.put(QualityField.RESPONSE_TIME.getValue(), criteria.getComparisonValue().get(0));
      map.put(QualityField.COVERAGE.getValue(), criteria.getComparisonValue().get(1));
      map.put(QualityField.PRECISION.getValue(), criteria.getComparisonValue().get(2));
      map.put(QualityField.COST.getValue(), criteria.getComparisonValue().get(3));

      weight = map;
    }

    return weight;
  }

  private RatingCounter getRatingCounter(QualityField qualityField) {
    final Float max = getMaxValue(qualityField);
    final Float min = getMinValue(qualityField);
    return new RatingCounter(max, min);
  }

  private Float getMaxValue(QualityField qualityField) {
    Float maxValue = null;
    Float candidateValue;
    Candidate candidate;

    for (Map.Entry<String, Candidate> entry : candidates.entrySet()) {
      candidate = entry.getValue();

      try {
        candidateValue = candidate.getQuality(qualityField.getValue());

        if (maxValue == null) {
          maxValue = candidateValue;
          continue;
        }

        if (candidateValue > maxValue) {
          maxValue = candidateValue;
        }
      } catch (AttributeNotFoundException e) {
        System.out.println("INFO >>>>> Max value is not found.");
      }
    }

    return maxValue;
  }

  private Float getMinValue(QualityField qualityField) {
    Float minValue = null;
    Float candidateValue;
    Candidate candidate;

    for (Map.Entry<String, Candidate> entry : candidates.entrySet()) {
      candidate = entry.getValue();

      try {
        candidateValue = candidate.getQuality(qualityField.getValue());

        if (minValue == null) {
          minValue = candidateValue;
          continue;
        }

        if (candidateValue < minValue) {
          minValue = candidateValue;
        }
      } catch (AttributeNotFoundException e) {
        System.out.println("INFO >>>>> Coordinate is out of range.");
      }
    }

    return minValue;
  }

}
