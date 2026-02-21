package itb.sdrank.model;

public class RankingItem implements Comparable<RankingItem> {
  private String rankingId;
	private String candidateId;
	private Float score;
	private String url;
	private String attribute;

	public RankingItem() {
		super();
	}

	public RankingItem(String rankingId, String candidateId, Float score, String url, String attribute) {
    super();
    this.rankingId = rankingId;
    this.candidateId = candidateId;
    this.score = score;
    this.url = url;
    this.attribute = attribute;
  }

  public String getRankingId() {
    return rankingId;
  }

  public void setRankingId(String rankingId) {
    this.rankingId = rankingId;
  }

  public String getCandidateId() {
		return candidateId;
	}

	public void setCandidateId(String candidateId) {
		this.candidateId = candidateId;
	}

	public Float getScore() {
		return score;
	}

	public void setScore(Float score) {
		this.score = score;
	}

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public String getAttribute() {
    return attribute;
  }

  public void setAttribute(String attribute) {
    this.attribute = attribute;
  }

  @Override
  public int compareTo(RankingItem o) {
    float result = getScore() - o.getScore();
    int value = 0;
    
    if (result > 0) {
      value = -1;
    } else if (result < 0){
      value = 1;
    } else if (!equals(o)){
      value = -1;
    }
    
    return value;
  }

  @Override
  public String toString() {
    return "RankingItem [rankingId=" + rankingId + ", candidateId=" + candidateId + ", score="
        + score + ", url=" + url + ", attribute=" + attribute + "]";
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((attribute == null) ? 0 : attribute.hashCode());
    result = prime * result + ((candidateId == null) ? 0 : candidateId.hashCode());
    result = prime * result + ((rankingId == null) ? 0 : rankingId.hashCode());
    result = prime * result + ((score == null) ? 0 : score.hashCode());
    result = prime * result + ((url == null) ? 0 : url.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    RankingItem other = (RankingItem) obj;
    if (attribute == null) {
      if (other.attribute != null)
        return false;
    } else if (!attribute.equals(other.attribute))
      return false;
    if (candidateId == null) {
      if (other.candidateId != null)
        return false;
    } else if (!candidateId.equals(other.candidateId))
      return false;
    if (rankingId == null) {
      if (other.rankingId != null)
        return false;
    } else if (!rankingId.equals(other.rankingId))
      return false;
    if (score == null) {
      if (other.score != null)
        return false;
    } else if (!score.equals(other.score))
      return false;
    if (url == null) {
      if (other.url != null)
        return false;
    } else if (!url.equals(other.url))
      return false;
    return true;
  }
}
