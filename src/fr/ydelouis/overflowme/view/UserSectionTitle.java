package fr.ydelouis.overflowme.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.googlecode.androidannotations.annotations.AfterViews;
import com.googlecode.androidannotations.annotations.EViewGroup;
import com.googlecode.androidannotations.annotations.ViewById;

import fr.ydelouis.overflowme.R;

@EViewGroup(R.layout.view_user_sectiontitle)
public class UserSectionTitle extends LinearLayout
{
	@ViewById(R.id.user_sectionTitle_count)
	protected TextView countText;
	@ViewById(R.id.user_sectionTitle_title)
	protected TextView titleText;

	private String title;
	
	public UserSectionTitle(Context context) {
		super(context);
	}
	
	public UserSectionTitle(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}
	
	public UserSectionTitle(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		
		TypedArray tAttrs = context.obtainStyledAttributes(attrs, R.styleable.UserSectionTitle, defStyle, 0);
		title = tAttrs.getString(R.styleable.UserSectionTitle_title);
		tAttrs.recycle();
	}
	
	@AfterViews
	protected void init() {
		setTitle(title);
	}
	
	public void setCount(int count) {
		setCount(String.valueOf(count));
	}
	
	public void setCount(String count) {
		if(countText != null)
			countText.setText(count);
	}
	
	public void setTitle(int titleResId) {
		setTitle(getResources().getString(titleResId));
	}
	
	public void setTitle(String title) {
		if(titleText != null)
			titleText.setText(title);
	}
	
	public int getCountWidth() {
		if(countText == null)
			return 0;
		if(countText.getMeasuredWidth() == 0)
			countText.measure(0, 0);
		return countText.getMeasuredWidth();
	}
	
	public void setCountWidth(int width) {
		if(countText != null)
			countText.setWidth(width);
	}
}
