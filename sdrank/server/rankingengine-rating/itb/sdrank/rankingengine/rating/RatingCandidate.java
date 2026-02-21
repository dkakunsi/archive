package itb.sdrank.rankingengine.rating;

import java.util.HashMap;
import java.util.Map;

import itb.sdrank.exception.AttributeNotFoundException;
import itb.sdrank.exception.RankingException;
import itb.sdrank.model.Candidate;
import itb.sdrank.model.QualityField;
import itb.sdrank.model.iot.DeviceInfo;
import itb.sdrank.model.iot.Location;
import itb.sdrank.model.iot.ResourceInfo;
import itb.sdrank.model.iot.ServiceInfo;

public class RatingCandidate extends Candidate {
  private Map<String, Float> qualityScores;

  public RatingCandidate() {
    super();
  }

  public RatingCandidate(Candidate candidate) {
    this(candidate.getDeviceInfo(), candidate.getResourceInfo(), candidate.getServiceInfo());
  }

  public RatingCandidate(DeviceInfo deviceInfo, ResourceInfo resourceInfo,
      ServiceInfo serviceInfo) {
    super(deviceInfo, resourceInfo, serviceInfo);
    this.qualityScores = new HashMap<>();
  }

  public Map<String, Float> getQualityScores() {
    return qualityScores;
  }

  public void setQualityScores(Map<String, Float> qualityScores) {
    this.qualityScores = qualityScores;
  }
  
  public void countQualityScore(RatingCounter mmTime, RatingCounter mmCost, RatingCounter mmPrecision,
      Location location) throws RankingException {
    Float responseTimeScore = countScore(QualityField.RESPONSE_TIME, mmTime);
    qualityScores.put(QualityField.RESPONSE_TIME.getValue(), responseTimeScore);

    Float precisionScore = countScore(QualityField.PRECISION, mmPrecision);
    qualityScores.put(QualityField.PRECISION.getValue(), precisionScore);

    Float costScore = countScore(QualityField.COST, mmCost);
    qualityScores.put(QualityField.COST.getValue(), costScore);

    Float coverageScore = countCoverageScore(location);
    qualityScores.put(QualityField.COVERAGE.getValue(), coverageScore);
  }

  public Float countScore(QualityField field, RatingCounter ratingCounter) throws OutOfRangeException {
    try {
      float value = getQuality(field.getValue());
      return ratingCounter.countRate(value, field.getDirection());
    } catch (AttributeNotFoundException e) {
      return 0f;
    }
  }
}
