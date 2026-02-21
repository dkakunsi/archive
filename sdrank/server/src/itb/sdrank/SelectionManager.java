package itb.sdrank;

import java.util.List;
import java.util.Set;

import itb.sdrank.exception.SDRankException;
import itb.sdrank.model.DeviceDescription;
import itb.sdrank.model.RankingItem;
import itb.sdrank.model.Criteria;
import itb.sdrank.model.iot.Quality;

public interface SelectionManager {

  Set<RankingItem> getItems(Criteria criteria, Integer number) throws SDRankException;

  Set<RankingItem> getItems(String rankingId, Integer number) throws SDRankException;

  Set<RankingItem> getItems(String rankingId, String candidateId, Integer numOfCandidate)
      throws SDRankException;

  void add(DeviceDescription deviceDescription) throws SDRankException;

  /**
   * Used only for initialization of system. Usually in testing process.
   * 
   * @param deviceDescriptions
   * @throws SDRankException
   */
  void add(List<DeviceDescription> deviceDescriptions) throws SDRankException;

  void updateQuality(String deviceId, String resourceName, Quality quality) throws SDRankException;

  void updateLocation(String deviceId, Double latitude, Double longitude) throws SDRankException;

  void updateOperationMode(String deviceId, Boolean active, String uri) throws SDRankException;
}
