package itb.sdrank.cache.redis;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository("redisRankingRepository")
public interface RedisRankingRepository extends CrudRepository<RedisRanking, String> {

	List<RedisRanking> findByQuery(String query);

}
