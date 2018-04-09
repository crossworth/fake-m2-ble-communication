package com.umeng.socialize.shareboard;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import com.umeng.socialize.Config;
import com.umeng.socialize.shareboard.wigets.ActionFrameAdapter;
import com.umeng.socialize.shareboard.wigets.UMActionFrame;
import com.umeng.socialize.utils.Log;

public class UMActionBoard extends RelativeLayout {
    private static final int ANIM_IN_TIME = 150;
    private UMActionFrame mActionFrame;
    private Context mContext;
    private Animation mFrameAnim;
    private View mTouchDisView;

    public UMActionBoard(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public UMActionBoard(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public UMActionBoard(Context context) {
        super(context);
        this.mContext = context;
        init();
    }

    private void init() {
        int i;
        int i2 = 12;
        this.mActionFrame = new UMActionFrame(this.mContext);
        LayoutParams layoutParams = new LayoutParams(-1, -1);
        if (Config.showShareBoardOnTop) {
            i = 10;
        } else {
            i = 12;
        }
        layoutParams.addRule(i);
        this.mActionFrame.setLayoutParams(layoutParams);
        this.mFrameAnim = new TranslateAnimation(0.0f, 0.0f, 80.0f, 0.0f);
        this.mFrameAnim.setDuration(150);
        this.mTouchDisView = new View(this.mContext);
        LayoutParams tParams = new LayoutParams(-1, -1);
        if (!Config.showShareBoardOnTop) {
            i2 = 10;
        }
        tParams.addRule(i2);
        this.mTouchDisView.setLayoutParams(tParams);
        this.mTouchDisView.setBackgroundColor(Color.argb(50, 0, 0, 0));
        AlphaAnimation alphaAnimation = new AlphaAnimation(0.0f, 1.0f);
        alphaAnimation.setDuration(1500);
        this.mTouchDisView.setAnimation(alphaAnimation);
        addView(this.mTouchDisView);
        addView(this.mActionFrame);
    }

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        Log.m4546d("onMeasure", "ActionBoard, width = " + width + ", height = " + height);
        ViewGroup.LayoutParams layoutParams = this.mActionFrame.getLayoutParams();
        layoutParams.height = this.mActionFrame.getAdapterHeight(width);
        layoutParams.width = width;
        ViewGroup.LayoutParams tParams = this.mTouchDisView.getLayoutParams();
        tParams.height = height - layoutParams.height;
        tParams.width = width;
    }

    public void activateFrame(ActionFrameAdapter adapter) {
        this.mActionFrame.setAdapter(adapter);
        this.mActionFrame.setBackgroundColor(-16777216);
    }

    public void setFrameOutsideListener(OnClickListener listener) {
        this.mTouchDisView.setOnClickListener(listener);
    }

    public void animation() {
        this.mActionFrame.startAnimation(this.mFrameAnim);
    }
}
