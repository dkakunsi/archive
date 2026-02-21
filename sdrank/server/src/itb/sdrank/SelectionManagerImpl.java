package itb.sdrank;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import itb.sdrank.exception.EntityNotFoundException;
import itb.sdrank.exception.NotificationException;
import itb.sdrank.exception.RankingException;
import itb.sdrank.exception.RepositoryException;
import itb.sdrank.exception.SDRankException;
import itb.sdrank.model.Candidate;
import itb.sdrank.model.DeviceDescription;
import itb.sdrank.model.Ranking;
import itb.sdrank.model.Ranking.RankingMethod;
import itb.sdrank.model.RankingItem;
import itb.sdrank.model.Criteria;
import itb.sdrank.model.iot.Quality;
import itb.sdrank.rankingengine.ahp.AHPRanking;
import itb.sdrank.rankingengine.rating.RatingRanking;

@Service
public class SelectionManagerImpl implements SelectionManager {
  @Resource
  private RankingEngine ahpRankingEngine;
  @Resource
  private RankingEngine ratingRankingEngine;
  @Resource
  private DiscoveryEngine discoveryEngine;
  @Resource(name = "redisCache")
  private RankingCache rankingCache;
  @Resource(name = "fsStorage")
  private DescriptionStorage descriptionStorage;
  @Resource
  private NotificationManager notificationManager;

  @Override
  public Set<RankingItem> getItems(Criteria criteria, Integer number) throws SDRankException {
    discoveryEngine.setRepository(descriptionStorage);
    Ranking ranking = null;

    try {
      ranking = chooseRanking(criteria, rankingCache.get(criteria));
    } catch (EntityNotFoundException e) {
      ranking = createRanking(criteria, number);
    }

    if (ranking == null || !ranking.isRanked())
      throw new RankingException("No ranking available");
    
    if (ranking.checkMaxRequestedNumber(number)) {
      ranking.setMaxRequestedNumber(number);
      rankingCache.save(ranking);
    }

    return ranking.getItems(number);
  }

  @Override
  public Set<RankingItem> getItems(String rankingId, Integer number) throws SDRankException {
    return rankingCache.get(rankingId).getItems(number);
  }

  @Override
  public Set<RankingItem> getItems(String rankingId, String candidateId, Integer numOfCandidate)
      throws SDRankException {
    Ranking ranking = rankingCache.get(rankingId);
    Set<RankingItem> items = ranking.getItems(candidateId, numOfCandidate);
    rankingCache.save(ranking);
    
    updateOperationMode(candidateId, false, null);

    return items;
  }

  @Override
  public void add(DeviceDescription deviceDescription) throws SDRankException {
    descriptionStorage.save(deviceDescription);
    discoveryEngine.setRepository(descriptionStorage);
    discoveryEngine.add(deviceDescription);
    updateRanking(deviceDescription);
  }

  @Override
  public void add(List<DeviceDescription> deviceDescriptions) throws SDRankException {
    descriptionStorage.save(deviceDescriptions);
    discoveryEngine.setRepository(descriptionStorage);
    discoveryEngine.add(deviceDescriptions);
  }

  @Override
  public void updateQuality(String deviceId, String resourceName, Quality quality)
      throws SDRankException {
    discoveryEngine.setRepository(descriptionStorage);
    DeviceDescription deviceDescription = descriptionStorage.get(deviceId);
    deviceDescription.setQuality(resourceName, quality);
    deviceDescription = deviceDescription.save();

    updateRanking(deviceDescription);
  }

  @Override
  public void updateLocation(String deviceId, Double latitude, Double longitude)
      throws SDRankException {
    discoveryEngine.setRepository(descriptionStorage);
    DeviceDescription deviceDescription = descriptionStorage.get(deviceId);
    deviceDescription.setLocation(latitude, longitude);
    deviceDescription = deviceDescription.save();

    updateRanking(deviceDescription);
  }

  @Override
  public void updateOperationMode(String deviceId, Boolean active, String uri) throws SDRankException {
    discoveryEngine.setRepository(descriptionStorage);
    DeviceDescription deviceDescription = descriptionStorage.get(deviceId);
    deviceDescription.setOperationMode(active);
    
    if (uri != null)
      deviceDescription.setUri(uri);
    
    deviceDescription = deviceDescription.save();

    if (active) {
      updateRanking(deviceDescription);
    } else {
      removeAndUpdateRanking(deviceDescription);
    }
  }
  
  private Ranking createRanking(Criteria criteria, Integer number) throws SDRankException {
    List<Candidate> candidates = discoveryEngine.discover(criteria);
    if (candidates.size() < number)
      throw new SDRankException("No candidates available for your request");

    if (criteria.getRankingMethod().equals(RankingMethod.AHP)) {
      return ahpRankingEngine.rank(candidates, criteria);
    } else {
      return ratingRankingEngine.rank(candidates, criteria);
    }
  }

  private Ranking chooseRanking(Criteria criteria, List<Ranking> ranks)
      throws EntityNotFoundException {
    Ranking ranking = null;
    Double rankingDistanceToCriteria = new Double(0);
    for (Ranking r : ranks) {
      if (r.getCriteria().equals(criteria)) {
        if (ranking == null) {
          ranking = r;
          rankingDistanceToCriteria = r.getCriteria().getLocation()
              .getDistance(criteria.getLocation());
          continue;
        }

        Double rDistanceToCriteria = r.getCriteria().getLocation()
            .getDistance(criteria.getLocation());
        if (rankingDistanceToCriteria > rDistanceToCriteria) {
          ranking = r;
          rankingDistanceToCriteria = rDistanceToCriteria;
        }
      }
    }

    if (ranking == null)
      throw new EntityNotFoundException("No equal ranking found");
    return ranking;
  }

  private void updateRanking(DeviceDescription deviceDescription)
      throws RepositoryException, NotificationException {
    boolean reRank = false;

    for (String attribute : deviceDescription.getAttributes()) {
      try {
        for (Ranking ranking : rankingCache.getByAttribute(attribute)) {
          reRank = false;

          for (Candidate candidate : deviceDescription.getCandidates(attribute)) {
            if (ranking.exist(candidate)) {
              ranking.removeCandidate(candidate);
              if (ranking.inRange(candidate)) {
                ranking.addCandidate(candidate);
              }

              reRank = true;
            } else {
              if (ranking.inRange(candidate)) {
                ranking.addCandidate(candidate);
              }

              reRank = true;
            }
          }

          if (reRank) {
            updateRanking(ranking);
          }
        }
      } catch (RepositoryException e) {
        if (!e.getMessage().equals("Ranking is unavailable")) {
          throw e;
        }
      }
    }
  }

  private void removeAndUpdateRanking(DeviceDescription deviceDescription)
      throws RepositoryException, NotificationException {
    List<Candidate> candidates = null;
    List<Ranking> rankings = new ArrayList<>();
    boolean reRank = false;

    for (String attribute : deviceDescription.getAttributes()) {
      try {
        rankings = rankingCache.getByAttribute(attribute);

        for (Ranking ranking : rankings) {
          reRank = false;
          candidates = deviceDescription.getCandidates(attribute);

          for (Candidate candidate : candidates) {
            if (ranking.exist(candidate)) {
              ranking.removeCandidate(candidate);
              reRank = true;
            }
          }

          if (reRank) {
            updateRanking(ranking);
          }
        }
      } catch (RepositoryException e) {
        if (!e.getMessage().equals("Ranking is unavailable")) {
          throw e;
        }
      }
    }
  }

  private void updateRanking(Ranking ranking) throws RepositoryException, NotificationException {
    ranking = getRankingObject(ranking);

    try {
      ranking.rank();
      rankingCache.save(ranking);
      notificationManager.notify(ranking);
    } catch (RankingException e) {
    }
  }

  private Ranking getRankingObject(Ranking ranking) {
    if (ranking.getMethod().equals(RankingMethod.AHP) && !(ranking instanceof AHPRanking)) {
      ranking = new AHPRanking(ranking);
    } else if (ranking.getMethod().equals(RankingMethod.RATING)
        && !(ranking instanceof RatingRanking)) {
      ranking = new RatingRanking(ranking);
    }

    return ranking;
  }
}
