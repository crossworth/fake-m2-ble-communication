package com.zhuoyou.plugin.running.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Rect;
import android.os.Build.VERSION;
import android.util.AttributeSet;
import android.util.Log;
import android.view.WindowInsets;
import android.widget.RelativeLayout;

public class AdjustRelativeLayout extends RelativeLayout {
    private int[] mInsets;

    public AdjustRelativeLayout(Context context) {
        super(context);
        this.mInsets = new int[3];
    }

    public AdjustRelativeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mInsets = new int[3];
    }

    public AdjustRelativeLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mInsets = new int[3];
    }

    @TargetApi(21)
    public AdjustRelativeLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        this.mInsets = new int[3];
    }

    protected final boolean fitSystemWindows(Rect insets) {
        if (VERSION.SDK_INT >= 19) {
            this.mInsets[0] = insets.left;
            this.mInsets[1] = insets.top;
            this.mInsets[2] = insets.right;
            insets.left = 0;
            insets.top = 0;
            insets.right = 0;
        }
        return super.fitSystemWindows(insets);
    }

    public final WindowInsets onApplyWindowInsets(WindowInsets insets) {
        if (VERSION.SDK_INT < 20) {
            return insets;
        }
        this.mInsets[0] = insets.getSystemWindowInsetLeft();
        Log.i("chenxin", "mInsets[0]" + this.mInsets[0]);
        this.mInsets[1] = insets.getSystemWindowInsetTop();
        Log.i("chenxin", "mInsets[1]" + this.mInsets[1]);
        this.mInsets[2] = insets.getSystemWindowInsetRight();
        Log.i("chenxin", "mInsets[2]" + this.mInsets[2]);
        return super.onApplyWindowInsets(insets.replaceSystemWindowInsets(0, 0, 0, insets.getSystemWindowInsetBottom()));
    }
}
