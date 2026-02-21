package itb.sdrank.notificationengine.activemq.test;

import static org.junit.Assert.assertTrue;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import itb.sdrank.exception.NotificationException;
import itb.sdrank.exception.RankingException;
import itb.sdrank.model.Ranking;
import itb.sdrank.notificationengine.activemq.ActiveMQConfig;
import itb.sdrank.notificationengine.activemq.MessagePublisher;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { ActiveMQConfig.class })
public class MessagePublisherTest {
  @Resource
  private MessagePublisher messagePublisher;

  @Test
  public void testNotifyRanking() throws NotificationException, RankingException {
    Ranking ranking = new Ranking() {
      
      @Override
      public void rank() throws RankingException {
        this.id = "R02";
      }
    };
    ranking.rank();
    
    assertTrue(ranking.isUsed());
    
    messagePublisher.notify(ranking);
  }

}
