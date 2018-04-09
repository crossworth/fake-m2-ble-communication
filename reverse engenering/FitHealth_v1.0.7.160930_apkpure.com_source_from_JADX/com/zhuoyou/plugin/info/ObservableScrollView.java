package com.zhuoyou.plugin.info;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ScrollView;

public class ObservableScrollView extends ScrollView {
    private Callbacks mCallbacks;

    public interface Callbacks {
        void onDownMotionEvent();

        void onScrollChanged(int i);

        void onUpOrCancelMotionEvent();
    }

    public ObservableScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        if (this.mCallbacks != null) {
            this.mCallbacks.onScrollChanged(t);
        }
    }

    public boolean onTouchEvent(MotionEvent ev) {
        if (this.mCallbacks != null) {
            switch (ev.getActionMasked()) {
                case 0:
                    this.mCallbacks.onDownMotionEvent();
                    break;
                case 1:
                case 3:
                    this.mCallbacks.onUpOrCancelMotionEvent();
                    break;
            }
        }
        return super.onTouchEvent(ev);
    }

    public int computeVerticalScrollRange() {
        return super.computeVerticalScrollRange();
    }

    public void setCallbacks(Callbacks listener) {
        this.mCallbacks = listener;
    }
}
