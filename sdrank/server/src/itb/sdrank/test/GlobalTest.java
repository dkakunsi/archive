package itb.sdrank.test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import itb.sdrank.cache.redis.test.RedisRankingCacheTest;
import itb.sdrank.discoveryengine.lucene.test.LuceneDiscoveryEngineTest;
import itb.sdrank.notificationengine.activemq.test.MessagePublisherTest;
import itb.sdrank.rankingengine.ahp.test.AllAHPTests;
import itb.sdrank.rankingengine.rating.test.AllRatingTests;
import itb.sdrank.storage.filesystem.test.FSStorageTest;
import itb.sdrank.web.test.AllWebTests;

@RunWith(Suite.class)
@SuiteClasses({ AllTests.class, AllAHPTests.class,
    LuceneDiscoveryEngineTest.class, FSStorageTest.class, RedisRankingCacheTest.class,
    MessagePublisherTest.class, AllWebTests.class, AllRatingTests.class })
public class GlobalTest {

}
