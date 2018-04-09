package com.zhuoyou.plugin.view;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ScrollView;
import java.lang.ref.WeakReference;

public class VerScrollView extends ScrollView {
    private UpdateHandler handler = new UpdateHandler(this);
    private int lastScrollY;
    private OnScrollListener onScrollListener;

    public interface OnScrollListener {
        void onScroll(int i);
    }

    public static class UpdateHandler extends Handler {
        WeakReference<VerScrollView> mMyFragment;

        public UpdateHandler(VerScrollView f) {
            this.mMyFragment = new WeakReference(f);
        }

        public void handleMessage(Message msg) {
            if (this.mMyFragment != null) {
                VerScrollView home = (VerScrollView) this.mMyFragment.get();
                if (home != null) {
                    int scrollY = home.getScrollY();
                    if (home.lastScrollY != scrollY) {
                        home.lastScrollY = scrollY;
                        home.handler.sendMessageDelayed(home.handler.obtainMessage(), 5);
                    }
                    if (home.onScrollListener != null) {
                        home.onScrollListener.onScroll(scrollY);
                    }
                }
            }
        }
    }

    public VerScrollView(Context context) {
        super(context);
        init();
    }

    public VerScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public VerScrollView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
    }

    public boolean onTouchEvent(MotionEvent ev) {
        if (this.onScrollListener != null) {
            OnScrollListener onScrollListener = this.onScrollListener;
            int scrollY = getScrollY();
            this.lastScrollY = scrollY;
            onScrollListener.onScroll(scrollY);
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
