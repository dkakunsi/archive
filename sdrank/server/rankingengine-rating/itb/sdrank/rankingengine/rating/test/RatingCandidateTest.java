package itb.sdrank.rankingengine.rating.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import itb.sdrank.exception.RankingException;
import itb.sdrank.model.QualityField;
import itb.sdrank.model.iot.DeviceInfo;
import itb.sdrank.model.iot.Location;
import itb.sdrank.model.iot.Quality;
import itb.sdrank.model.iot.ResourceInfo;
import itb.sdrank.model.iot.ServiceInfo;
import itb.sdrank.rankingengine.rating.OutOfRangeException;
import itb.sdrank.rankingengine.rating.RatingCandidate;
import itb.sdrank.rankingengine.rating.RatingCounter;

public class RatingCandidateTest {
  private RatingCandidate candidate;
  private RatingCounter mmTime;
  private RatingCounter mmCost;
  private RatingCounter mmPrecision;
  private Location location;

  @Before
  public void init() {
    // -6.878291,107.596233
    // -6.878208,107.597784
    location = new Location(new Double(-6.878291), new Double(107.596233));
    DeviceInfo deviceInfo = new DeviceInfo("DEV1", "Device 1", location);
    
    ResourceInfo resourceInfo = new ResourceInfo(50f);
    List<Quality> qualities = new ArrayList<>();
    qualities.add(new Quality(QualityField.RESPONSE_TIME.getValue(), "RT", 50f));
    qualities.add(new Quality(QualityField.PRECISION.getValue(), "PR", 10f));
    qualities.add(new Quality(QualityField.COST.getValue(), "IDR", 400000f));
    resourceInfo.setQualities(qualities);
    
    ServiceInfo serviceInfo = new ServiceInfo("http://10.10.10.1/temperature", "getTemperature", "Temperature");

    candidate = new RatingCandidate(deviceInfo, resourceInfo, serviceInfo);

    mmTime = new RatingCounter(70f, 20f);
    mmCost = new RatingCounter(1000000f, 100000f);
    mmPrecision = new RatingCounter(50f, 5f);
  }

  @Test
  public void testSetQualityScoresFloatFloatFloatFloatDoubleDouble() throws RankingException {
    candidate.countQualityScore(mmTime, mmCost, mmPrecision, location);
    Map<String, Float> qualityScores = candidate.getQualityScores();

    assertEquals(new Float(2f/5f), qualityScores.get(QualityField.RESPONSE_TIME.getValue()));
    assertEquals(new Float(1), qualityScores.get(QualityField.COVERAGE.getValue()));
    assertEquals(new Float(5f/45f), qualityScores.get(QualityField.PRECISION.getValue()));
    assertEquals(new Float(6f/9f), qualityScores.get(QualityField.COST.getValue()));
  }

  @Test
  public void testGetResponseTimeScore() throws OutOfRangeException {
    Float score = candidate.countScore(QualityField.RESPONSE_TIME, mmTime);
    assertNotNull(score);
    assertEquals(new Float(2f/5f), score);
  }

  @Test
  public void testGetCoverageScore() {
    // -6.878208,107.597784
    Float score = candidate.countCoverageScore(location);
    assertNotNull(score);
    assertEquals(new Float(1), score);
  }

  @Test
  public void testGetPrecisionScore() throws OutOfRangeException {
    Float score = candidate.countScore(QualityField.PRECISION, mmPrecision);
    assertNotNull(score);
    assertEquals(new Float(5f/45f), score);
  }

  @Test
  public void testGetCostScore() throws OutOfRangeException {
    Float score = candidate.countScore(QualityField.COST, mmCost);
    assertNotNull(score);
    assertEquals(new Float(6f/9f), score);
  }
}
