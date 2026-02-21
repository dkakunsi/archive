package itb.sdrank.client.command;

import itb.sdrank.client.State;
import itb.sdrank.client.message.RankingItem;
import itb.sdrank.client.message.SimpleMessage;
import itb.sdrank.client.notification.NotificationChannel;

public class DeviceConnectionCommand extends AbstractCommand {
    private RankingItem item;

    public DeviceConnectionCommand(RankingItem item) {
	super();
	this.item = item;
    }
    
    @Override
    public SimpleMessage execute() throws CommandException {
	NotificationChannel.addListeningRanking(item.getRankingId());
	State.setState(State.ACCESSING);

	System.out.println(String.format("INFO: Ranking (ID: %s), Candidate (ID: %s, SCORE: %.2f)", item.getRankingId(), item.getCandidateId(), item.getScore()));
	System.out.println(String.format("Requesting: url: %s", item.getUrl()));
	
	return new SimpleMessage();
    }

    @Override
    public void readValue(String[] inputs) throws CommandException {
	// TODO Auto-generated method stub
    }
}
