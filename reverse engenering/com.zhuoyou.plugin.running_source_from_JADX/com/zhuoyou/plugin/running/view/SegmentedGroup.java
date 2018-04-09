package com.zhuoyou.plugin.running.view;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Build.VERSION;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.RadioGroup.LayoutParams;
import com.zhuoyou.plugin.running.C1680R;

public class SegmentedGroup extends RadioGroup {
    private int mCheckedTextColor;
    private Float mCornerRadius;
    private LayoutSelector mLayoutSelector;
    private int mMarginDp;
    private int mTintColor;
    private int mUnCheckedTextColor;

    private class LayoutSelector {
        private final int SELECTED_LAYOUT = C1680R.drawable.radio_checked;
        private final int UNSELECTED_LAYOUT = C1680R.drawable.radio_unchecked;
        private int child = -1;
        private int children = -1;
        private float f5010r;
        private final float r1 = TypedValue.applyDimension(1, 0.1f, SegmentedGroup.this.getResources().getDisplayMetrics());
        private final float[] rBot;
        private final float[] rDefault;
        private final float[] rLeft;
        private final float[] rMiddle;
        private final float[] rRight;
        private final float[] rTop;
        private float[] radii;

        public LayoutSelector(float cornerRadius) {
            this.f5010r = cornerRadius;
            this.rLeft = new float[]{this.f5010r, this.f5010r, this.r1, this.r1, this.r1, this.r1, this.f5010r, this.f5010r};
            this.rRight = new float[]{this.r1, this.r1, this.f5010r, this.f5010r, this.f5010r, this.f5010r, this.r1, this.r1};
            this.rMiddle = new float[]{this.r1, this.r1, this.r1, this.r1, this.r1, this.r1, this.r1, this.r1};
            this.rDefault = new float[]{this.f5010r, this.f5010r, this.f5010r, this.f5010r, this.f5010r, this.f5010r, this.f5010r, this.f5010r};
            this.rTop = new float[]{this.f5010r, this.f5010r, this.f5010r, this.f5010r, this.r1, this.r1, this.r1, this.r1};
            this.rBot = new float[]{this.r1, this.r1, this.r1, this.r1, this.f5010r, this.f5010r, this.f5010r, this.f5010r};
        }

        private int getChildren() {
            return SegmentedGroup.this.getChildCount();
        }

        private int getChildIndex(View view) {
            return SegmentedGroup.this.indexOfChild(view);
        }

        private void setChildRadii(int newChildren, int newChild) {
            if (this.children != newChildren || this.child != newChild) {
                this.children = newChildren;
                this.child = newChild;
                if (this.children == 1) {
                    this.radii = this.rDefault;
                } else if (this.child == 0) {
                    this.radii = SegmentedGroup.this.getOrientation() == 0 ? this.rLeft : this.rTop;
                } else if (this.child == this.children - 1) {
                    this.radii = SegmentedGroup.this.getOrientation() == 0 ? this.rRight : this.rBot;
                } else {
                    this.radii = this.rMiddle;
                }
            }
        }

        public int getSelected() {
            return C1680R.drawable.radio_checked;
        }

        public int getUnselected() {
            return C1680R.drawable.radio_unchecked;
        }

        public float[] getChildRadii(View view) {
            setChildRadii(getChildren(), getChildIndex(view));
            return this.radii;
        }
    }

    public SegmentedGroup(Context context) {
        super(context);
        this.mMarginDp = 1;
        this.mCheckedTextColor = -1;
        this.mUnCheckedTextColor = -16777216;
        this.mTintColor = getResources().getColor(C1680R.color.radio_button_selected_color);
        this.mMarginDp = (int) getResources().getDimension(C1680R.dimen.radio_button_stroke_border);
        this.mCornerRadius = Float.valueOf(getResources().getDimension(C1680R.dimen.radio_button_conner_radius));
        this.mLayoutSelector = new LayoutSelector(this.mCornerRadius.floatValue());
    }

    private void initAttrs(AttributeSet attrs) {
        TypedArray typedArray = getContext().getTheme().obtainStyledAttributes(attrs, C1680R.styleable.SegmentedGroup, 0, 0);
        try {
            this.mMarginDp = (int) typedArray.getDimension(1, getResources().getDimension(C1680R.dimen.radio_button_stroke_border));
            this.mCornerRadius = Float.valueOf(typedArray.getDimension(0, getResources().getDimension(C1680R.dimen.radio_button_conner_radius)));
            this.mTintColor = typedArray.getColor(2, getResources().getColor(C1680R.color.radio_button_selected_color));
            this.mCheckedTextColor = typedArray.getColor(3, getResources().getColor(17170443));
            this.mUnCheckedTextColor = typedArray.getColor(4, getResources().getColor(17170444));
        } finally {
            typedArray.recycle();
        }
    }

    public SegmentedGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mMarginDp = 1;
        this.mCheckedTextColor = -1;
        this.mUnCheckedTextColor = -16777216;
        initAttrs(attrs);
        this.mLayoutSelector = new LayoutSelector(this.mCornerRadius.floatValue());
    }

    protected void onFinishInflate() {
        super.onFinishInflate();
        updateBackground();
    }

    public void setTintColor(int tintColor) {
        this.mTintColor = tintColor;
        updateBackground();
    }

    public void setTintColor(int tintColor, int checkedTextColor) {
        this.mTintColor = tintColor;
        this.mCheckedTextColor = checkedTextColor;
        updateBackground();
    }

    public void updateBackground() {
        int count = super.getChildCount();
        int i = 0;
        while (i < count) {
            View child = getChildAt(i);
            updateBackground(child);
            if (i != count - 1) {
                LayoutParams initParams = (LayoutParams) child.getLayoutParams();
                LayoutParams params = new LayoutParams(initParams.width, initParams.height, initParams.weight);
                if (getOrientation() == 0) {
                    params.setMargins(0, 0, -this.mMarginDp, 0);
                } else {
                    params.setMargins(0, 0, 0, -this.mMarginDp);
                }
                child.setLayoutParams(params);
                i++;
            } else {
                return;
            }
        }
    }

    private void updateBackground(View view) {
        int checked = this.mLayoutSelector.getSelected();
        int unchecked = this.mLayoutSelector.getUnselected();
        r6 = new int[2][];
        r6[0] = new int[]{-16842912};
        r6[1] = new int[]{16842912};
        ((Button) view).setTextColor(new ColorStateList(r6, new int[]{this.mUnCheckedTextColor, this.mCheckedTextColor}));
        Drawable checkedDrawable = getResources().getDrawable(checked).mutate();
        Drawable uncheckedDrawable = getResources().getDrawable(unchecked).mutate();
        ((GradientDrawable) checkedDrawable).setColor(this.mTintColor);
        ((GradientDrawable) checkedDrawable).setStroke(this.mMarginDp, this.mTintColor);
        ((GradientDrawable) uncheckedDrawable).setStroke(this.mMarginDp, this.mTintColor);
        ((GradientDrawable) checkedDrawable).setCornerRadii(this.mLayoutSelector.getChildRadii(view));
        ((GradientDrawable) uncheckedDrawable).setCornerRadii(this.mLayoutSelector.getChildRadii(view));
        StateListDrawable stateListDrawable = new StateListDrawable();
        stateListDrawable.addState(new int[]{-16842912}, uncheckedDrawable);
        stateListDrawable.addState(new int[]{16842912}, checkedDrawable);
        if (VERSION.SDK_INT >= 16) {
            view.setBackground(stateListDrawable);
        } else {
            view.setBackgroundDrawable(stateListDrawable);
        }
    }
}
