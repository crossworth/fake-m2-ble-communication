package com.zhuoyou.plugin.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ScrollView;
import java.util.ArrayList;
import java.util.List;

public class NewScrollView extends ScrollView {
    public static final int DOWN = 2;
    public static final int STOP = 3;
    public static final int UP = 1;
    private View inner;
    private List<ScrollListener> mListeners = new ArrayList();
    private int state = 0;

    public interface ScrollListener {
        void onScrollChanged(int i, int i2);
    }

    public NewScrollView(Context context) {
        super(context);
    }

    public NewScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public NewScrollView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void addListener(ScrollListener scrolllistener) {
        if (this.mListeners == null) {
            this.mListeners = new ArrayList();
        }
        this.mListeners.add(scrolllistener);
    }

    protected void onFinishInflate() {
        if (getChildCount() > 0) {
            this.inner = getChildAt(0);
        }
    }

    protected void onScrollChanged(int x, int y, int oldx, int oldy) {
        super.onScrollChanged(x, y, oldx, oldy);
        if (this.inner.getMeasuredHeight() >= getScrollY() + getHeight()) {
            if (y > oldy) {
                this.state = 1;
            } else if (y < oldy) {
                this.state = 2;
            } else {
                this.state = 3;
            }
            sendScroll(this.state, y);
        }
    }

    public void sendScroll(int paramInt1, int paramInt2) {
        for (ScrollListener onScrollChanged : this.mListeners) {
            onScrollChanged.onScrollChanged(paramInt1, paramInt2);
        }
    }
}
