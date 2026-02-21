package itb.sdrank.notificationengine.activemq;

import java.io.Serializable;
import java.util.Set;

import itb.sdrank.exception.RankingException;
import itb.sdrank.model.Ranking;
import itb.sdrank.model.RankingItem;

public class RankingMessage implements Serializable {
  private static final long serialVersionUID = -540905740769576464L;
  private String id;
  private Set<RankingItem> items;

  public RankingMessage() {
    super();
  }

  public RankingMessage(Ranking ranking) throws RankingException {
    this();
    this.id = ranking.getId();
    if (ranking.isUsed()) {
      int maxRequestedNumber = ranking.getMaxRequestedNumber();
      items = ranking.getItems(maxRequestedNumber);
    }
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public Set<RankingItem> getItems() {
    return items;
  }

  public void setItems(Set<RankingItem> services) {
    this.items = services;
  }
}
