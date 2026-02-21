package itb.sdrank.model;

import java.util.Date;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import itb.sdrank.exception.RankingException;

public abstract class Ranking extends Entity {
  protected Criteria criteria;
  protected Map<String, Candidate> candidates;
  protected SortedSet<RankingItem> items;
  protected Integer maxRequestedNumber;
  protected RankingMethod method;

  public enum RankingMethod {
    RATING, AHP
  }

  protected Ranking() {
    super();
    this.id = String.valueOf(new Date().getTime());
    this.items = new TreeSet<>();
    this.maxRequestedNumber = 0;
  }

  protected Ranking(Ranking ranking) {
    super();
    this.id = ranking.getId();
    this.criteria = ranking.getCriteria();
    this.candidates = ranking.getCandidates();
    this.items = new TreeSet<>();
    this.maxRequestedNumber = ranking.getMaxRequestedNumber();
    this.method = ranking.getMethod();
  }

  protected Ranking(Criteria criteria, Map<String, Candidate> candidates) {
    super();
    this.id = String.valueOf(new Date().getTime());
    this.criteria = criteria;
    this.candidates = candidates;
    this.items = new TreeSet<>();
    this.maxRequestedNumber = 0;
  }

  public abstract void rank() throws RankingException;

  public Criteria getCriteria() {
    return criteria;
  }

  public void setCriteria(Criteria criteria) {
    this.criteria = criteria;
  }

  public Map<String, Candidate> getCandidates() {
    return candidates;
  }

  public void setCandidates(Map<String, Candidate> candidates) {
    this.candidates = candidates;
  }

  public void addCandidate(Candidate candidate) {
    if (candidates.containsKey(candidate.getId()))
      candidates.remove(candidate.getId());
    candidates.put(candidate.getId(), candidate);
  }

  public void removeCandidate(Candidate candidate) {
    candidates.remove(candidate.getId());
  }

  public void removeCandidate(String id) {
    candidates.remove(id);
    removeItem(id);
  }

  public Set<RankingItem> getItems() {
    return items;
  }

  public Set<RankingItem> getItems(Integer number) throws RankingException {
    if (items.size() < number)
      throw new RankingException("No available item");
    
    if (checkMaxRequestedNumber(number))
      maxRequestedNumber = number;
    
    Set<RankingItem> returnedItems = new TreeSet<>();
    for (RankingItem item : items) {
      returnedItems.add(item);
      
      if (returnedItems.size() >= number)
        break;
    }
    
    return returnedItems;
  }

  public Set<RankingItem> getItems(String candidateId, Integer number) throws RankingException {
    removeCandidate(candidateId);
    return getItems(number);
  }

  public void setItems(SortedSet<RankingItem> items) {
    this.items = items;
  }

  public void removeItem(String id) {
    for (RankingItem item : items)
      if (item.getCandidateId().equals(id)) {
        items.remove(item);
        break;
      }
  }
  
  protected void cleanItems() {
    items.clear();
  }

  public Integer getMaxRequestedNumber() {
    return maxRequestedNumber;
  }

  public void setMaxRequestedNumber(Integer maxRequestedNumber) {
    this.maxRequestedNumber = maxRequestedNumber;
  }

  public boolean checkMaxRequestedNumber(Integer number) {
    return number > this.maxRequestedNumber;
  }

  public RankingMethod getMethod() {
    return method;
  }

  public void setMethod(RankingMethod method) {
    this.method = method;
  }

  public boolean isAffected(DeviceDescription deviceDescription) {
    boolean affected = false;
    for (Candidate candidate : deviceDescription.getCandidates(criteria.getQuery())) {
      affected = inRange(candidate);
    }
    return affected;
  }

  public boolean inRange(Candidate candidate) {
    return candidate.inRange(criteria.getLocation());
  }

  /**
   * The shortest range candidate still covers the required location of criteria.
   * 
   * @param criteria
   * @return whether in range or not.
   */
  public boolean inRange(Criteria criteria) {
    return this.criteria.getLocation().inRange(criteria.getLocation(), getShortestRange());
  }

  private Float getShortestRange() {
    Float shortestRange = Float.MAX_VALUE;
    Candidate candidate;
    for (Map.Entry<String, Candidate> entry : candidates.entrySet()) {
      candidate = entry.getValue();
      if (candidate.getRange() < shortestRange)
        shortestRange = candidate.getRange();
    }
    return shortestRange;
  }

  public boolean exist(final Candidate candidate) {
    return candidates.get(candidate.getId()) != null;
  }

  public boolean isRanked() {
    return items.size() > 0;
  }

  public boolean isUsed() {
    return (isRanked() && getMaxRequestedNumber() > 0);
  }

  @Override
  public String toString() {
    return "Ranking [criteria=" + criteria + ", candidates=" + candidates + ", items=" + items
        + ", maxRequestedNumber=" + maxRequestedNumber + ", method=" + method + ", id=" + id + "]";
  }
}
