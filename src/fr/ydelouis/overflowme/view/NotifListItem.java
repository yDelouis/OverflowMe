package fr.ydelouis.overflowme.view;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.googlecode.androidannotations.annotations.Bean;
import com.googlecode.androidannotations.annotations.EViewGroup;
import com.googlecode.androidannotations.annotations.ViewById;

import fr.ydelouis.overflowme.R;
import fr.ydelouis.overflowme.entity.Notif;
import fr.ydelouis.overflowme.model.MeStore;
import fr.ydelouis.overflowme.util.DateUtil;

@EViewGroup(R.layout.listitem_notif)
public class NotifListItem extends LinearLayout
{
	@Bean
	protected MeStore	meStore;

	@ViewById(R.id.notifItem_unread)
	protected ImageView	unread;
	@ViewById(R.id.notifItem_type)
	protected TextView	type;
	@ViewById(R.id.notifItem_date)
	protected TextView	date;
	@ViewById(R.id.notifItem_text)
	protected TextView	text;

	public NotifListItem(Context context) {
		super(context);
	}

	public void bind(Notif notif) {
		unread.setVisibility(notif.isUnread(meStore.getLastSeenDate()) ? View.VISIBLE : View.INVISIBLE);
		type.setText(notif.getType());
		text.setText(notif.getText());
		date.setText(DateUtil.toShortString(notif.getCreationDate()));
	}
}
