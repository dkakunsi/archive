package itb.sdrank.notificationengine.activemq;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.connection.CachingConnectionFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.converter.MessageType;

@Configuration
@ComponentScan("itb.sdrank.notificationengine.activemq")
public class ActiveMQConfig {

  @Bean
  public MessageConverter jacksonJmsMessageConverter() {
    MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
    converter.setTargetType(MessageType.TEXT);
    converter.setTypeIdPropertyName("_type");
    return converter;
  }

  @Bean
  public JmsTemplate jmsTemplate() {
    ActiveMQConnectionFactory amqpConnectionFactory = new ActiveMQConnectionFactory("admin", "admin", "tcp://localhost:61616");
    CachingConnectionFactory connectionFactory = new CachingConnectionFactory(amqpConnectionFactory);
    ActiveMQTopic defaultDestination = new ActiveMQTopic("sdrank:ranking");
    
    JmsTemplate template = new JmsTemplate(connectionFactory);
    template.setDefaultDestination(defaultDestination);
    return template;
  }

}
