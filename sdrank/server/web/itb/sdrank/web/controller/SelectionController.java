package itb.sdrank.web.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import itb.sdrank.SelectionManager;
import itb.sdrank.exception.SDRankException;
import itb.sdrank.model.Criteria;
import itb.sdrank.model.RankingItem;
import itb.sdrank.model.iot.Location;
import itb.sdrank.web.RankingResponse;
import itb.sdrank.web.SimpleResponse;

@RestController
public class SelectionController {
  @Resource
  private SelectionManager selectionManager;

  @RequestMapping(method = RequestMethod.GET, path = "/{query}/location/{latitude},{longitude}/number/{number}")
  @ResponseStatus(code = HttpStatus.OK)
  public SimpleResponse getItems(@PathVariable String query, @PathVariable Double latitude,
      @PathVariable Double longitude, @RequestParam String comparisonValues,
      @PathVariable Integer number) {

    Location location = new Location(latitude, longitude);
    try {
      Criteria criteria = new Criteria(query, location, getPayload(comparisonValues));
      Set<RankingItem> items = selectionManager.getItems(criteria, number);
      return new RankingResponse("Berhasil", items);
    } catch (SDRankException e) {
      return new SimpleResponse(e.getMessage());
    }
  }

  @RequestMapping(method = RequestMethod.GET, path = "/ranking/{rankingId}/number/{number}")
  @ResponseStatus(code = HttpStatus.OK)
  public SimpleResponse getItems(@PathVariable String rankingId, @PathVariable Integer number) {
    try {
      return new RankingResponse("Berhasil", selectionManager.getItems(rankingId, number));
    } catch (SDRankException e) {
      return new SimpleResponse(e.getMessage());
    }
  }

  @RequestMapping(method = RequestMethod.GET, path = "/ranking/{rankingId}/candidate/{candidateId}/number/{number}")
  @ResponseStatus(code = HttpStatus.OK)
  public SimpleResponse getItems(@PathVariable String rankingId, @PathVariable String candidateId,
      @PathVariable Integer number) {
    try {
      return new RankingResponse("Berhasil", selectionManager.getItems(rankingId, candidateId, number));
    } catch (SDRankException e) {
      return new SimpleResponse(e.getMessage());
    }
  }

  private List<Float> getPayload(String payload) throws SDRankException {
    try {
      List<Float> list = new ArrayList<>();
      for (String str : payload.split(",")) {
        list.add(Float.valueOf(str));
      }
      return list;
    } catch (Exception e) {
      throw new SDRankException(e);
    }
  }
}
