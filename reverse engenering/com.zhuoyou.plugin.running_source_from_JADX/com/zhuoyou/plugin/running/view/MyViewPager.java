package com.zhuoyou.plugin.running.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

public class MyViewPager extends ViewPager {
    private boolean scorllable = true;

    public MyViewPager(Context context) {
        super(context);
    }

    public MyViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public boolean isScorllable() {
        return this.scorllable;
    }

    public void setScorllable(boolean scorllable) {
        this.scorllable = scorllable;
    }

    public void setCurrentItem(int item) {
        super.setCurrentItem(item, false);
    }

    public boolean onTouchEvent(MotionEvent ev) {
        return this.scorllable && super.onTouchEvent(ev);
    }

    public boolean onInterceptHoverEvent(MotionEvent event) {
        return this.scorllable && super.onInterceptHoverEvent(event);
    }
}
