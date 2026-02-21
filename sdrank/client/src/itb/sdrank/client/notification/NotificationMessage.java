package itb.sdrank.client.notification;

import itb.sdrank.client.message.RankingMessage;

public class NotificationMessage extends RankingMessage {

    private static final long serialVersionUID = 4780787349057296006L;
    private String id;

    public String getId() {
	return id;
    }

    public void setId(String id) {
	this.id = id;
    }

    @Override
    public String toString() {
	return "NotificationMessage [id=" + id + ", items=" + items
		+ ", message=" + message + "]";
    }
}
