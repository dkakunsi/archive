package itb.sdrank.client.notification;

import java.io.IOException;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import com.fasterxml.jackson.databind.ObjectMapper;

import itb.sdrank.client.command.CommandException;
import itb.sdrank.client.command.DeviceConnectionCommand;
import itb.sdrank.client.message.RankingItem;

public class NotificationMessageListener implements MessageListener {
    public void onMessage(Message message) {
	try {
	    TextMessage textMessage = (TextMessage) message;
	    String text = textMessage.getText();

	    ObjectMapper mapper = new ObjectMapper();
	    NotificationMessage ranking = mapper.readValue(text, NotificationMessage.class);

	    if (NotificationChannel.checkListeningRanking(ranking.getId())) {
		System.out.println(String.format("MESSAGE: Ranking updated (ID: %s)", ranking.getId()));
		
		for (RankingItem item : ranking.getItems()) {
		    DeviceConnectionCommand devCommand = new DeviceConnectionCommand(item);
		    devCommand.execute();
		}
		
		System.out.print("SDRank: client > ");
	    }
	} catch (JMSException | IOException | CommandException e) {
	    e.printStackTrace();
	}
    }
}
