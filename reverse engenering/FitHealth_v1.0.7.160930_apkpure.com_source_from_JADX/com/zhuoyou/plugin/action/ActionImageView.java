package com.zhuoyou.plugin.action;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

public class ActionImageView extends ImageView {
    public ActionImageView(Context context) {
        super(context);
    }

    public ActionImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ActionImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (getDrawable() != null) {
            int width = getDrawable().getIntrinsicWidth();
            int height = getDrawable().getIntrinsicHeight();
            int currentWidth = getMeasuredWidth();
            int currentHeight = getMeasuredHeight();
            setMeasuredDimension(currentWidth, (int) (((float) height) * (((float) currentWidth) / ((float) width))));
        }
    }
}
