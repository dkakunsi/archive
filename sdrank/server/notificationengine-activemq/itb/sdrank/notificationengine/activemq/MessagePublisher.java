package itb.sdrank.notificationengine.activemq;

import javax.annotation.Resource;

import org.springframework.jms.JmsException;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import itb.sdrank.NotificationManager;
import itb.sdrank.exception.NotificationException;
import itb.sdrank.exception.RankingException;
import itb.sdrank.model.Ranking;

@Service
public class MessagePublisher implements NotificationManager {
  @Resource
  private JmsTemplate jmsTemplate;

  public MessagePublisher() {
  }

  @Override
  public void notify(Ranking ranking) throws NotificationException {
    try {
      RankingMessage message = new RankingMessage(ranking);

      ObjectMapper mapper = new ObjectMapper();
      String json = mapper.writeValueAsString(message);
      jmsTemplate.convertAndSend(json);
    } catch (JmsException | RankingException | JsonProcessingException e) {
      throw new NotificationException(e);
    }
  }

}
