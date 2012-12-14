package fr.ydelouis.overflowme.common;

import android.content.Context;
import android.text.TextUtils.TruncateAt;
import android.text.method.SingleLineTransformationMethod;
import android.util.AttributeSet;
import android.widget.TextView;

public class ScrollTextView extends TextView
{
	public ScrollTextView(Context context) {
		super(context);
		init();
	}
	
	public ScrollTextView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}
	
	public ScrollTextView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}
	
	private void init() {
		setSelected(true);
		setTransformationMethod(new SingleLineTransformationMethod());
		setEllipsize(TruncateAt.MARQUEE);
		setMarqueeRepeatLimit(-1);
		setHorizontallyScrolling(true);
	}
}
