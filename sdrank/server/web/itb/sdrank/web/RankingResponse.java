package itb.sdrank.web;

import java.util.Set;

import itb.sdrank.model.RankingItem;

public class RankingResponse extends SimpleResponse {
	private Set<RankingItem> items;

	public RankingResponse() {
		super();
	}
	
	public RankingResponse(String message) {
		super(message);
	}

	public RankingResponse(String message, Set<RankingItem> items) {
		super(message);
		this.items = items;
	}

	public Set<RankingItem> getItems() {
		return items;
	}
}
