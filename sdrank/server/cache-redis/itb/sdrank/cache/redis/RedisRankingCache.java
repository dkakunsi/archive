package itb.sdrank.cache.redis;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import itb.sdrank.RankingCache;
import itb.sdrank.exception.EntityNotFoundException;
import itb.sdrank.exception.RepositoryException;
import itb.sdrank.model.Ranking;
import itb.sdrank.model.Criteria;

@Component("redisCache")
public class RedisRankingCache implements RankingCache {
	@Resource
	private RedisRankingRepository repository;

  @Override
  public List<Ranking> get(Criteria criteria) throws EntityNotFoundException, RepositoryException {
    List<Ranking> ranks = new ArrayList<>();
    for (Ranking ranking : getByAttribute(criteria.getQuery())) {
      if (ranking.inRange(criteria)) {
        ranks.add(ranking);
      }
    }
    return ranks;
  }

  @Override
  public List<Ranking> getByAttribute(String attribute) throws EntityNotFoundException, RepositoryException {
    List<RedisRanking> hitRanks= repository.findByQuery(attribute);
    if (hitRanks.isEmpty())
      throw new EntityNotFoundException("Ranking is unavailable");
    return new ArrayList<Ranking>(hitRanks);
  }

	@Override
	public Ranking get(String id) throws RepositoryException {
		Optional<RedisRanking> optional = repository.findById(id);
		if (!optional.isPresent())
      throw new EntityNotFoundException("Ranking is unavailable");
		return optional.get();
	}

	@Override
	public void save(Ranking ranking) throws RepositoryException {
	  repository.deleteById(ranking.getId());
		repository.save(new RedisRanking(ranking));
	}

	@Override
	public void save(List<Ranking> entities) throws RepositoryException {
		for (Ranking ranking : entities) {
			save(ranking);
		}
	}

	@Override
	public void clean() throws RepositoryException {
		repository.deleteAll();
	}
}
