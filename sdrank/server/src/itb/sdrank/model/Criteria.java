package itb.sdrank.model;

import java.util.List;

import itb.sdrank.model.Ranking.RankingMethod;
import itb.sdrank.model.iot.Location;

public class Criteria {
	private String query;
  private Location location;
  private List<Float> comparisonValue;

	public Criteria() {
		super();
	}
	
	public Criteria(String query) {
	  super();
	  this.query = query;
	}

	public Criteria(String query, Location location, List<Float> comparisonValue) {
    super();
    this.query = query;
    this.location = location;
    this.comparisonValue = comparisonValue;
  }

  public String getQuery() {
		return query;
	}

  public Location getLocation() {
    return location;
  }

  public List<Float> getComparisonValue() {
    return comparisonValue;
  }
  
  public RankingMethod getRankingMethod() {
    if (comparisonValue.size() == 4)
      return RankingMethod.RATING;
    return RankingMethod.AHP;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    Criteria other = (Criteria) obj;
    if (comparisonValue == null)
      return false;
    if (comparisonValue.size() != other.getComparisonValue().size())
      return false;
    
    int idx;
    for (idx = 0; idx < comparisonValue.size(); idx++) {
      if (!comparisonValue.get(idx).equals(other.getComparisonValue().get(idx)))
        return false;
    }
    return true;
  }
}
