package com.zhuoyou.plugin.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import com.fithealth.running.R;
import com.zhuoyou.plugin.add.TosGallery;
import com.zhuoyou.plugin.running.C1400R;

public class WheelView extends TosGallery {
    private boolean attr1;
    private Rect mSelectorBound = new Rect();
    private Drawable mSelectorDrawable = null;

    public WheelView(Context context) {
        super(context);
        initialize(context);
    }

    public WheelView(Context context, AttributeSet attrs) {
        super(context, attrs);
        getAttrs(context, attrs);
        initialize(context);
    }

    public WheelView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs);
        getAttrs(context, attrs);
        initialize(context);
    }

    private void getAttrs(Context context, AttributeSet attrs) {
        TypedArray ta = context.obtainStyledAttributes(attrs, C1400R.styleable.myWheelView);
        this.attr1 = ta.getBoolean(0, true);
        ta.recycle();
    }

    private void initialize(Context context) {
        setVerticalScrollBarEnabled(false);
        setSlotInCenter(true);
        setOrientation(2);
        setGravity(1);
        setUnselectedAlpha(1.0f);
        setWillNotDraw(false);
        if (this.attr1) {
            this.mSelectorDrawable = getContext().getResources().getDrawable(R.drawable.wheel_val);
        }
        this.mSelectorDrawable = getContext().getResources().getDrawable(R.drawable.wheel_select_info);
        setSoundEffectsEnabled(false);
    }

    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        drawCenterRect(canvas);
    }

    public void setOrientation(int orientation) {
        if (1 == orientation) {
            throw new IllegalArgumentException("The orientation must be VERTICAL");
        }
        super.setOrientation(orientation);
    }

    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        int galleryCenter = getCenterOfGallery();
        View v = getChildAt(0);
        int height = (int) ((50.0f * getContext().getResources().getDisplayMetrics().density) + 0.5f);
        int top = galleryCenter - (height / 2);
        this.mSelectorBound.set(getPaddingLeft(), top, getWidth() - getPaddingRight(), top + height);
    }

    protected void selectionChanged() {
        super.selectionChanged();
        playSoundEffect(0);
    }

    private void drawCenterRect(Canvas canvas) {
        if (this.mSelectorDrawable != null) {
            this.mSelectorDrawable.setBounds(this.mSelectorBound);
            this.mSelectorDrawable.draw(canvas);
        }
    }
}
