package fr.ydelouis.overflowme.loader;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import android.content.Context;

import com.googlecode.androidannotations.annotations.EBean;
import com.googlecode.androidannotations.annotations.OrmLiteDao;
import com.googlecode.androidannotations.annotations.RootContext;
import com.googlecode.androidannotations.annotations.rest.RestService;

import fr.ydelouis.overflowme.api.Api;
import fr.ydelouis.overflowme.api.entity.InboxItem;
import fr.ydelouis.overflowme.api.entity.Notification;
import fr.ydelouis.overflowme.api.rest.NotifRest;
import fr.ydelouis.overflowme.entity.Notif;
import fr.ydelouis.overflowme.model.DatabaseHelper;
import fr.ydelouis.overflowme.model.NotifDao;

@EBean
public class NotifLoader
{
	private static final int NB_NOTIF = 8;
	
	@RootContext
	protected Context context;
	@RestService
	protected NotifRest notifRest;
	@OrmLiteDao(helper = DatabaseHelper.class, model = Notif.class)
	protected NotifDao notifDao;
	
	public void load() {
		Api.prepare(context, notifRest.getRestTemplate(), false);
		List<InboxItem> inboxItems = notifRest.getInboxUnread().getItems();
		List<Notification> notifications = notifRest.getUnreadNotifications().getItems();
		List<Notif> notifs = merge(inboxItems, notifications);
		if(notifs.size() < NB_NOTIF) {
			inboxItems = notifRest.getInbox(NB_NOTIF).getItems();
			notifications = notifRest.getNotifications(NB_NOTIF).getItems();
			notifs = merge(inboxItems, notifications);
		}
		if(notifs.size() > NB_NOTIF)
			notifs = notifs.subList(0, NB_NOTIF);
		
		try {
			notifDao.clear();
			notifDao.createAll(notifs);
		} catch (SQLException e) {}
	}
	
	private ArrayList<Notif> merge(List<InboxItem> inboxItems, List<Notification> notifications) {
		ArrayList<Notif> notifs = new ArrayList<Notif>();
		for(InboxItem inboxItem : inboxItems) {
			if(inboxItem.getSite().getSiteUrl().equals(Api.SITE_URL))
				notifs.add(new Notif(inboxItem));
		}
		for(Notification notification : notifications) {
			if(notification.getSite().getSiteUrl().equals(Api.SITE_URL))
				notifs.add(new Notif(notification));
		}
		Collections.sort(notifs);
		return notifs;
	}
	
	public void load(NotifLoaderListener notifLoaderListener) {
		load();
		if(notifLoaderListener != null)
			notifLoaderListener.onNotifsLoaded();
	}
	
	public interface NotifLoaderListener {
		public void onNotifsLoaded();
	}
}
