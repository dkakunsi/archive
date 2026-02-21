package itb.sdrank.rankingengine.ahp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import itb.sdrank.exception.AttributeNotFoundException;
import itb.sdrank.exception.RankingException;
import itb.sdrank.model.Candidate;
import itb.sdrank.model.Criteria;
import itb.sdrank.model.QualityField;
import itb.sdrank.model.QualityField.Direction;
import itb.sdrank.model.Ranking;
import itb.sdrank.model.RankingItem;
import itb.sdrank.model.iot.Location;

public class AHPRanking extends Ranking {
  private AHPMatrixBuilder matrixBuilder;
  private AHP attributesAHP;
  private Map<QualityField, AHP> ahps;

  public AHPRanking(Criteria criteria, Map<String, Candidate> candidates, AHPMatrixBuilder matrixBuilder) {
    super(criteria, candidates);
    this.matrixBuilder = matrixBuilder;
    this.ahps = new HashMap<>();
    this.method = RankingMethod.AHP;
  }

  public AHPRanking(Ranking ranking) {
    super(ranking);
    this.matrixBuilder = new AHPMatrixBuilder();
    this.ahps = new HashMap<>();
  }

  @Override
  public void rank() throws RankingException {
    attributesAHP = calculate();
    if (!attributesAHP.isFine())
      throw new RankingException("The given comparison is inconsistent.");

    fillQualityAhp();
    cleanItems();

    Candidate candidate;
    for (Map.Entry<String, Candidate> entry : candidates.entrySet()) {
      candidate = entry.getValue();
      items.add(scoreCandidate(candidate));
    }
  }

  private void fillQualityAhp() {
    for (QualityField field : QualityField.values()) {
      try {
        AHP ahp = calculate(field);
        ahps.put(field, ahp);
      } catch (AttributeNotFoundException e) {
        e.printStackTrace();
      }
    }
  }

  private RankingItem scoreCandidate(Candidate candidate) {
    int indexOfCandidateInMatrix, indexOfQualityInMatrix;
    float score = 0, candidateQualityScore, qualityComparisonScore;

    for (QualityField field : QualityField.values()) {
      AHP ahp = ahps.get(field);

      indexOfCandidateInMatrix = ahp.getIndex(candidate.getId());
      candidateQualityScore = ahp.getPriorities().get(indexOfCandidateInMatrix);

      indexOfQualityInMatrix = attributesAHP.getIndex(field.getValue());
      qualityComparisonScore = attributesAHP.getPriorities().get(indexOfQualityInMatrix);

      score += candidateQualityScore * qualityComparisonScore;
    }

    return new RankingItem(id, candidate.getId(), score, candidate.getEndpoint(),
        candidate.getAttribute());
  }

  public AHP calculate(QualityField attribute) throws AttributeNotFoundException {
    List<String> candidateIds = new ArrayList<>(candidates.keySet());
    AHPMatrix matrix = matrixBuilder.build(candidateIds, getValues(attribute, candidateIds));
    return new AHP(matrix);
  }

  public List<Float> getValues(QualityField attribute, List<String> candidateIds)
      throws AttributeNotFoundException {
    List<Float> values = new ArrayList<>();
    Candidate current, comparing;

    int i, j;
    for (i = 0; i < candidateIds.size() - 1; i++) {
      current = candidates.get(candidateIds.get(i));
      for (j = i + 1; j < candidateIds.size(); j++) {
        comparing = candidates.get(candidateIds.get(j));
        values.add(getQualityValue(attribute, current, comparing));
      }
    }

    return values;
  }

  private Float getQualityValue(QualityField attribute, Candidate current, Candidate comparing)
      throws AttributeNotFoundException {
    if (attribute.equals(QualityField.COVERAGE)) {
      return getCoverageValue(current, comparing);
    }

    String key = attribute.getValue();
    float currentScore = current.getQuality(key);
    float comparingScore = comparing.getQuality(key);

    if (currentScore == 0)
      currentScore = 1;

    if (comparingScore == 0)
      comparingScore =1;

    if (attribute.getDirection().equals(Direction.DECREASING))
      return comparingScore / currentScore;
    return currentScore / comparingScore;
  }

  private Float getCoverageValue(Candidate current, Candidate comparing) {
    Location location = criteria.getLocation();
    float currentScore = current.countCoverageScore(location);
    float comparingScore = comparing.countCoverageScore(location);

    return currentScore / comparingScore;
  }

  public AHP calculate() throws RankingException {
    if (criteria.getComparisonValue().size() != 6)
      throw new RankingException("Requires 6 comparison.");

    AHPMatrix matrix = matrixBuilder.build(QualityField.getQualities(),
        criteria.getComparisonValue());
    return new AHP(matrix);
  }
}
