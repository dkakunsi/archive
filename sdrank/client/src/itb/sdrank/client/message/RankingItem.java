package itb.sdrank.client.message;

public class RankingItem {
    private String rankingId;
    private String candidateId;
    private Float score;
    private String url;
    private String attribute;

    public RankingItem() {
	super();
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
    public String toString() {
	return "RankingItem [candidateId=" + candidateId + ", score=" + score + ", url=" + url + ", attribute="
		+ attribute + "]";
    }
}
