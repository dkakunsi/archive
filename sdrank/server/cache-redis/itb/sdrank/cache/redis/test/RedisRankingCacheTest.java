package itb.sdrank.cache.redis.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;

import javax.annotation.Resource;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import itb.sdrank.cache.redis.RedisConfig;
import itb.sdrank.cache.redis.RedisRanking;
import itb.sdrank.cache.redis.RedisRankingCache;
import itb.sdrank.exception.RepositoryException;
import itb.sdrank.model.Criteria;
import itb.sdrank.model.Ranking;
import itb.sdrank.model.iot.Location;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { RedisConfig.class })
public class RedisRankingCacheTest {
	@Resource(name = "redisCache")
	private RedisRankingCache repository;

  @Test
  public void testAdd() throws RepositoryException {
    Ranking ranking = new RedisRanking();
    Location location = new Location(new Double(102.2), new Double(2.1));
    Criteria criteria = new Criteria("query", location, new ArrayList<>());
    ranking.setCriteria(criteria);
    repository.save(ranking);
  }

	@Test
	public void testGetString() throws RepositoryException {
		Ranking ranking = new RedisRanking();
    Location location = new Location(new Double(102.2), new Double(2.1));
    Criteria criteria = new Criteria("query", location, new ArrayList<>());
		ranking.setCriteria(criteria);
		repository.save(ranking);
		String id = ranking.getId();

		Ranking retrievedRank = repository.get(id);
		assertNotNull(retrievedRank);
		assertEquals(criteria.getQuery(), retrievedRank.getCriteria().getQuery());
	}

	@After
	public void destroy() throws RepositoryException {
		repository.clean();
	}

}
