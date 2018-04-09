package com.zhuoyou.plugin.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ScrollView;

public class MofeiScrollView extends ScrollView {
    private View view;

    public MofeiScrollView(Context context) {
        super(context);
    }

    public MofeiScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MofeiScrollView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (this.view == null || !checkArea(this.view, ev)) {
            return super.onInterceptTouchEvent(ev);
        }
        return false;
    }

    private boolean checkArea(View v, MotionEvent event) {
        float x = event.getRawX();
        float y = event.getRawY();
        int[] locate = new int[2];
        if (v != null) {
            v.getLocationOnScreen(locate);
            int l = locate[0];
            int r = l + v.getWidth();
            int t = locate[1];
            int b = t + v.getHeight();
            if (((float) l) < x && x < ((float) r) && ((float) t) < y && y < ((float) b)) {
                return true;
            }
        }
        return false;
    }

    public View getView() {
        return this.view;
    }

    public void setView(View view) {
        this.view = view;
    }
}
