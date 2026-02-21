package itb.sdrank.rankingengine.ahp.test;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import itb.sdrank.RankingEngine;
import itb.sdrank.exception.RankingException;
import itb.sdrank.exception.SDRankException;
import itb.sdrank.model.Candidate;
import itb.sdrank.model.Criteria;
import itb.sdrank.model.QualityField;
import itb.sdrank.model.Ranking;
import itb.sdrank.model.RankingItem;
import itb.sdrank.model.iot.DeviceInfo;
import itb.sdrank.model.iot.Location;
import itb.sdrank.model.iot.Quality;
import itb.sdrank.model.iot.ResourceInfo;
import itb.sdrank.model.iot.ServiceInfo;
import itb.sdrank.rankingengine.ahp.AHPConfig;
import itb.sdrank.rankingengine.ahp.AHPMatrixBuilder;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { AHPConfig.class })
public class AHPRankingEngineTest {
  @Resource(name = "ahpRankingEngine")
  private RankingEngine rankingEngine;
  @Resource
  AHPMatrixBuilder matrixBuilder;
  private List<Candidate> candidates;
  private List<Float> qualityValues;
  Double latitude;
  Double longitude;
  Float range;

  @Before
  public void setUp() throws Exception {
    candidates = new ArrayList<>();
    qualityValues = new ArrayList<>();
    qualityValues.add(new Float(7)); // RT-CV
    qualityValues.add(new Float(3)); // RT - PC
    qualityValues.add(new Float(1)); // RT - CT
    qualityValues.add(new Float(1 / 7f)); // CV-PC
    qualityValues.add(new Float(1 / 5f)); // CV-CT
    qualityValues.add(new Float(1)); // PC-CT
    
    latitude = new Double(10);
    longitude = new Double(10);
    range = 10f;
  }

  @Test
  public void testRank() throws SDRankException {
    candidates.add(createCandidate(1, latitude, longitude, range));
    candidates.add(createCandidate(2, latitude, longitude, range));
    candidates.add(createCandidate(3, latitude, longitude, range));
    candidates.add(createCandidate(4, latitude, longitude, range));

    Location location = new Location(new Double(25), new Double(15));
    Criteria criteria = new Criteria("temperature", location, qualityValues);
    Ranking ranking = rankingEngine.rank(candidates, criteria);

    RankingItem item = ranking.getItems(1).iterator().next();
    assertEquals("C1", item.getCandidateId());

    RankingItem alternateItem = ranking.getItems(item.getCandidateId(), 1).iterator().next();
    assertEquals("C2", alternateItem.getCandidateId());
  }

  @Test
  public void testRankWithFreeResource() throws SDRankException {
    qualityValues = new ArrayList<>();
    qualityValues.add(new Float(1)); // RT-CV
    qualityValues.add(new Float(1)); // RT - PC
    qualityValues.add(new Float(1 / 7f)); // RT - CT
    qualityValues.add(new Float(1)); // CV-PC
    qualityValues.add(new Float(1 / 7f)); // CV-CT
    qualityValues.add(new Float(1 / 7f)); // PC-CT

    candidates.add(createCandidate(1, latitude, longitude, range));
    candidates.add(createCandidate(2, latitude, longitude, range));
    candidates.add(createFreeCandidate(3, latitude, longitude, range));
    candidates.add(createCandidate(4, latitude, longitude, range));

    Location location = new Location(new Double(25), new Double(15));
    Criteria criteria = new Criteria("temperature", location, qualityValues);
    Ranking ranking = rankingEngine.rank(candidates, criteria);

    RankingItem item = ranking.getItems(1).iterator().next();
    assertEquals("C3", item.getCandidateId());
  }

  @Test(expected = RankingException.class)
  public void testRankInconsistent() throws SDRankException {
    qualityValues = new ArrayList<>();
    qualityValues.add(new Float(5)); // RT-CV
    qualityValues.add(new Float(1)); // RT - PC
    qualityValues.add(new Float(1)); // RT - CT
    qualityValues.add(new Float(3)); // CV-PC
    qualityValues.add(new Float(4)); // CV-CT
    qualityValues.add(new Float(1)); // PC-CT

    candidates.add(createCandidate(1, latitude, longitude, range));
    candidates.add(createCandidate(2, latitude, longitude, range));
    candidates.add(createFreeCandidate(3, latitude, longitude, range));
    candidates.add(createCandidate(4, latitude, longitude, range));

    Location location = new Location(new Double(25), new Double(15));
    Criteria criteria = new Criteria("temperature", location, qualityValues);
    rankingEngine.rank(candidates, criteria);
  }

  public Candidate createCandidate(int num, Double latitude, Double longitude, Float range) {
    if (range == null) {
      latitude = new Double(10 * num);
      longitude = new Double(10 * num);
      range = 10f * num;
    }

    Location location = new Location(latitude, longitude);
    DeviceInfo deviceInfo = new DeviceInfo(String.format("C%d", num),
        String.format("Device %d", num), location);

    ResourceInfo resourceInfo = new ResourceInfo(range);
    List<Quality> qualities = new ArrayList<>();
    qualities.add(new Quality(QualityField.RESPONSE_TIME.getValue(), "RT", 10f * num));
    qualities.add(new Quality(QualityField.PRECISION.getValue(), "PR", 10f * num));
    qualities.add(new Quality(QualityField.COST.getValue(), "IDR", 100f * num));
    resourceInfo.setQualities(qualities);

    ServiceInfo serviceInfo = new ServiceInfo(String.format("http://candidate%d", num),
        "getTemperature", "Temperature");

    return new Candidate(deviceInfo, resourceInfo, serviceInfo);
  }

  public Candidate createFreeCandidate(int num, Double latitude, Double longitude, Float range) {
    if (range == null) {
      latitude = new Double(10 * num);
      longitude = new Double(10 * num);
      range = 10f * num;
    }

    Location location = new Location(latitude, longitude);
    DeviceInfo deviceInfo = new DeviceInfo(String.format("C%d", num),
        String.format("Device %d", num), location);

    ResourceInfo resourceInfo = new ResourceInfo(range);
    List<Quality> qualities = new ArrayList<>();
    qualities.add(new Quality(QualityField.RESPONSE_TIME.getValue(), "RT", 10f * num));
    qualities.add(new Quality(QualityField.PRECISION.getValue(), "PR", 10f * num));
    qualities.add(new Quality(QualityField.COST.getValue(), "IDR", 0f * num));
    resourceInfo.setQualities(qualities);

    ServiceInfo serviceInfo = new ServiceInfo(String.format("http://candidate%d", num),
        "getTemperature", "Temperature");

    return new Candidate(deviceInfo, resourceInfo, serviceInfo);
  }
}
