package itb.sdrank.client.notification;

import java.util.ArrayList;
import java.util.List;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.Session;
import javax.jms.Topic;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

public class NotificationChannel {

    private static Connection jmsConnection;
    private static String url = ActiveMQConnection.DEFAULT_BROKER_URL;
    private static ConnectionFactory connectionFactory;
    private static List<String> listeningRanking;

    static {
	connectionFactory = new ActiveMQConnectionFactory("admin", "admin", url);
	listeningRanking = new ArrayList<>();
    }
    
    public static void addListeningRanking(String rankingId) {
	if (!checkListeningRanking(rankingId) && !rankingId.equals(""))
	    listeningRanking.add(rankingId);
    }
    
    public static boolean checkListeningRanking(String rankingId) {
	for (String lr : listeningRanking) {
	    if (lr.equals(rankingId))
		return true;
	}
	
	return false;
    }

    public static void initialize()
	    throws NotificationException {
	try {
	    if (jmsConnection == null) {
		jmsConnection = connectionFactory.createConnection();
		jmsConnection.start();

		Session session = jmsConnection.createSession(false,
			Session.AUTO_ACKNOWLEDGE);
		Topic topic = session.createTopic("sdrank:ranking");

		MessageConsumer consumer = session.createConsumer(topic);
		MessageListener messageListener = new NotificationMessageListener();
		consumer.setMessageListener(messageListener);
	    }
	} catch (JMSException e) {
	    throw new NotificationException(e);
	}
    }

    public static void destroy() throws NotificationException {
	try {
	    if (jmsConnection != null) {
		jmsConnection.close();
		jmsConnection = null;
	    }
	    listeningRanking = new ArrayList<>();
	} catch (JMSException e) {
	    throw new NotificationException(e);
	}
    }
}
