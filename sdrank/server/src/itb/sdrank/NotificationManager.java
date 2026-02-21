package itb.sdrank;

import itb.sdrank.exception.NotificationException;
import itb.sdrank.model.Ranking;

public interface NotificationManager {
	void notify(Ranking ranking) throws NotificationException;
}
