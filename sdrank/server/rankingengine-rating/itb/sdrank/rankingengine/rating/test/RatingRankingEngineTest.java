package itb.sdrank.rankingengine.rating.test;

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
import itb.sdrank.rankingengine.rating.RatingConfig;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { RatingConfig.class })
public class RatingRankingEngineTest {
  @Resource(name = "ratingRankingEngine")
  private RankingEngine rankingEngine;
  private List<Candidate> candidates;
  private List<Float> qualityValues;
  private Double latitude;
  private Double longitude;
  private Float range;

  @Before
  public void setUp() throws Exception {
    candidates = new ArrayList<>();
    qualityValues = new ArrayList<>();
    latitude = new Double(10);
    longitude = new Double(10);
    range = 10f;
  }

  @Test
  public void testRank() throws SDRankException {
    qualityValues.add(new Float(4)); // RT
    qualityValues.add(new Float(2)); // CV
    qualityValues.add(new Float(1)); // PC
    qualityValues.add(new Float(1)); // CT

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
    qualityValues.add(new Float(1)); // RT
    qualityValues.add(new Float(1)); // CV
    qualityValues.add(new Float(1)); // PC
    qualityValues.add(new Float(8)); // CT

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

  public Candidate createCandidate(int num, Double latitude, Double longitude, Float range) {
    if (range == null) {
      latitude = new Double(10 * num);
      longitude = new Double(10 * num);
      range = 10f * num;
    }

    // -6.878291,107.596233
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

    // -6.878291,107.596233
    Location location = new Location(latitude, longitude);
    DeviceInfo deviceInfo = new DeviceInfo(String.format("C%d", num),
        String.format("Device %d", num), location);

    ResourceInfo resourceInfo = new ResourceInfo(range);
    List<Quality> qualities = new ArrayList<>();
    qualities.add(new Quality(QualityField.RESPONSE_TIME.getValue(), "RT", 10f * num));
    qualities.add(new Quality(QualityField.PRECISION.getValue(), "PR", 10f * num));
    qualities.add(new Quality(QualityField.COST.getValue(), "IDR", 1f * num));
    resourceInfo.setQualities(qualities);

    ServiceInfo serviceInfo = new ServiceInfo(String.format("http://candidate%d", num),
        "getTemperature", "Temperature");

    return new Candidate(deviceInfo, resourceInfo, serviceInfo);
  }
}
