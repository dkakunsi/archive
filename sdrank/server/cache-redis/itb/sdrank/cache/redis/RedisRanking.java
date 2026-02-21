package itb.sdrank.cache.redis;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

import itb.sdrank.exception.RankingException;
import itb.sdrank.model.Ranking;

@RedisHash("Ranking")
public class RedisRanking extends Ranking {
	@Indexed
	private String query;

	public RedisRanking() {
		super();
	}

	public RedisRanking(Ranking ranking) {
		super(ranking);
		this.query = ranking.getCriteria().getQuery();
	}

	@Id
	public String getId() {
		return id;
	}

	public String getQuery() {
		return query;
	}

	public void setQuery(String query) {
		this.query = query;
	}

	@Override
	public void rank() throws RankingException {
		throw new RankingException("Not Supported");
	}
}
