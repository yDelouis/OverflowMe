package fr.ydelouis.overflowme.api.rest;

import org.springframework.http.converter.json.GsonHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import com.googlecode.androidannotations.annotations.rest.Get;
import com.googlecode.androidannotations.annotations.rest.Rest;

import fr.ydelouis.overflowme.api.Api;
import fr.ydelouis.overflowme.api.Filter;
import fr.ydelouis.overflowme.api.entity.InboxItem.InboxItemRequest;
import fr.ydelouis.overflowme.api.entity.Notification.NotificationRequest;

@Rest(rootUrl = Api.ROOT_URL, converters = GsonHttpMessageConverter.class)
public interface NotifRest
{
	public RestTemplate getRestTemplate();
	
	@Get("/inbox?pagesize={pageSize}&filter="+Filter.NOTIF_INBOXITEM)
	public InboxItemRequest getInbox(int pageSize);
	
	@Get("/inbox/unread?filter="+Filter.NOTIF_INBOXITEM)
	public InboxItemRequest getInboxUnread();
	
	@Get("/notifications?pagesize={pageSize}&filter="+Filter.NOTIF_NOTIFICATION)
	public NotificationRequest getNotifications(int pageSize);
	
	@Get("/notifications/unread?filter="+Filter.NOTIF_NOTIFICATION)
	public NotificationRequest getUnreadNotifications();
}
