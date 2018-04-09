package com.zhuoyou.plugin.view;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.HorizontalScrollView;
import java.lang.ref.WeakReference;

public class HorScrollView extends HorizontalScrollView {
    private UpdateHandler handler = new UpdateHandler(this);
    private int lastScrollX;
    private OnScrollListener onScrollListener;

    public interface OnScrollListener {
        void onScroll(int i);
    }

    public static class UpdateHandler extends Handler {
        WeakReference<HorScrollView> mMyFragment;

        public UpdateHandler(HorScrollView f) {
            this.mMyFragment = new WeakReference(f);
        }

        public void handleMessage(Message msg) {
            if (this.mMyFragment != null) {
                HorScrollView home = (HorScrollView) this.mMyFragment.get();
                if (home != null) {
                    int scrollX = home.getScrollX();
                    if (home.lastScrollX != scrollX) {
                        home.lastScrollX = scrollX;
                        home.handler.sendMessageDelayed(home.handler.obtainMessage(), 5);
                    }
                    if (home.onScrollListener != null) {
                        home.onScrollListener.onScroll(scrollX);
                    }
                }
            }
        }
    }

    public HorScrollView(Context context) {
        super(context);
        init();
    }

    public HorScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public HorScrollView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
    }

    public boolean onTouchEvent(MotionEvent ev) {
        if (this.onScrollListener != null) {
            OnScrollListener onScrollListener = this.onScrollListener;
            int scrollX = getScrollX();
            this.lastScrollX = scrollX;
            onScrollListener.onScroll(scrollX);
        }
        switch (ev.getAction()) {
            case 1:
                this.handler.sendMessageDelayed(this.handler.obtainMessage(), 5);
                break;
        }
        return super.onTouchEvent(ev);
    }

    public void setOnScrollListener(OnScrollListener onScrollListener) {
        this.onScrollListener = onScrollListener;
    }
}
