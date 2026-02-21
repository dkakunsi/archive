package itb.sdrank.client.message;

import java.util.List;

public class RankingMessage extends SimpleMessage {

    private static final long serialVersionUID = 7439585017368520076L;
    protected List<RankingItem> items;

    public List<RankingItem> getItems() {
	return items;
    }

    public void setItems(List<RankingItem> items) {
	this.items = items;
    }

    @Override
    public String toString() {
	return "RankingMessage [items=" + items + ", message=" + message + "]";
    }

    public String getRankingId() {
	if (items != null && items.size() > 0)
	    return items.get(0).getRankingId();
	return "";
    }

    public boolean checkItems() {
	return items != null && items.size() > 0;
    }
}
