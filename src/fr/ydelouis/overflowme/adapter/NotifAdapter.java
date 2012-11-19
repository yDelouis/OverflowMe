package fr.ydelouis.overflowme.adapter;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import fr.ydelouis.overflowme.R;
import fr.ydelouis.overflowme.entity.Notif;
import fr.ydelouis.overflowme.view.NotifListItem;
import fr.ydelouis.overflowme.view.NotifListItem_;

public class NotifAdapter extends ArrayAdapter<Notif>
{
	private static final int LAYOUT = R.layout.listitem_notif;
	
	public NotifAdapter(Context context, List<Notif> items) {
		super(context, LAYOUT, items);
	}

	@Override
	public View getView(int position, View view, ViewGroup parent) {
		NotifListItem itemView;
        if (view == null)
            itemView = NotifListItem_.build(getContext());
        else
            itemView = (NotifListItem) view;
        
        itemView.bind(getItem(position));

        return itemView;
	}
}
