package com.zhuoyou.plugin.view;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.ViewParent;
import android.widget.RelativeLayout;
import com.zhuoyou.plugin.view.NewScrollView.ScrollListener;
import java.lang.ref.WeakReference;

public class NewRelativeLayout extends RelativeLayout implements ScrollListener {
    private static final int ANIMATE = 1;
    private static final int SCROLL = 2;
    public boolean canAnimate = false;
    private int locHeight;
    private Handler mHandler = new WRHandler(this);
    private Operator mOperator;
    private int mState = 0;
    public ViewParent parent = getParent();

    public interface Operator {
        int getWindowHeight();

        void refreshLayout();
    }

    private static class WRHandler extends Handler {
        WeakReference<NewRelativeLayout> mNewRelativeLayout;

        public WRHandler(NewRelativeLayout NRL) {
            this.mNewRelativeLayout = new WeakReference(NRL);
        }

        public void handleMessage(Message msg) {
            if (this.mNewRelativeLayout != null) {
                NewRelativeLayout newRelativeLayout = (NewRelativeLayout) this.mNewRelativeLayout.get();
                if (newRelativeLayout != null) {
                    switch (msg.what) {
                        case 1:
                            newRelativeLayout.canAnimate = true;
                            newRelativeLayout.mOperator.refreshLayout();
                            return;
                        case 2:
                            newRelativeLayout.doScroll(msg.arg1, msg.arg2);
                            return;
                        default:
                            return;
                    }
                }
            }
        }
    }

    public NewRelativeLayout(Context context) {
        super(context);
    }

    public NewRelativeLayout(Context context, AttributeSet attributeset) {
        super(context, attributeset);
    }

    public NewRelativeLayout(Context context, AttributeSet attributeset, int i) {
        super(context, attributeset, i);
    }

    private boolean doAnimate(int i) {
        if (isShown() && this.mOperator.getWindowHeight() + i > this.locHeight + 20 && this.mState == 1) {
            return true;
        }
        return false;
    }

    private void doScroll(int state, int y) {
        if (this.mState != state || !this.canAnimate) {
            this.mState = state;
            if (doAnimate(y)) {
                this.mHandler.sendEmptyMessage(1);
            }
        }
    }

    public void setLocHeight(int i) {
        this.locHeight = i;
    }

    public void setOperator(Operator operatorP) {
        this.mOperator = operatorP;
    }

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    public void onScrollChanged(int i, int j) {
        Message message = this.mHandler.obtainMessage();
        message.what = 2;
        message.arg1 = i;
        message.arg2 = j;
        this.mHandler.sendMessage(message);
    }
}
