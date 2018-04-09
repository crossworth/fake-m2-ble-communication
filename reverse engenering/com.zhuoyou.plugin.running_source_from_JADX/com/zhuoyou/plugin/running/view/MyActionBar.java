package com.zhuoyou.plugin.running.view;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.zhuoyou.plugin.running.C1680R;

public class MyActionBar extends RelativeLayout implements OnClickListener {
    private AttributeSet attrs;
    private ImageView btnRight;
    private RelativeLayout layout;
    private View rootView;
    private TextView tvBackTitle;
    private TextView tvLeftTitle;
    private TextView tvRightTitle;
    private TextView tvTitle;

    public MyActionBar(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.attrs = attrs;
        init();
    }

    public MyActionBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.attrs = attrs;
        init();
    }

    public MyActionBar(Context context) {
        super(context);
        init();
    }

    private void init() {
        this.rootView = LayoutInflater.from(getContext()).inflate(C1680R.layout.layout_actionbar, this, true);
        this.layout = (RelativeLayout) this.rootView.findViewById(C1680R.id.actionbar_root);
        this.btnRight = (ImageView) this.rootView.findViewById(C1680R.id.actionbar_right_button);
        this.tvBackTitle = (TextView) this.rootView.findViewById(C1680R.id.actionbar_back_title);
        this.tvLeftTitle = (TextView) this.rootView.findViewById(C1680R.id.actionbar_left_title);
        this.tvTitle = (TextView) this.rootView.findViewById(C1680R.id.actionbar_title);
        this.tvBackTitle.setOnClickListener(this);
        this.tvRightTitle = (TextView) this.rootView.findViewById(C1680R.id.actionbar_right_title);
        if (this.attrs != null) {
            TypedArray mTypedArray = getContext().obtainStyledAttributes(this.attrs, C1680R.styleable.MyActionBar);
            boolean backShow = mTypedArray.getBoolean(9, true);
            int rightButtonImgId = mTypedArray.getResourceId(1, -1);
            String backtitle = mTypedArray.getString(2);
            String lefttitle = mTypedArray.getString(3);
            String title = mTypedArray.getString(4);
            String righttitle = mTypedArray.getString(5);
            int titleColor = mTypedArray.getColor(6, -1);
            int btitleColor = mTypedArray.getColor(7, -1);
            int rtitleColor = mTypedArray.getColor(8, -1);
            mTypedArray.recycle();
            this.tvTitle.setText(title);
            this.tvTitle.setTextColor(titleColor);
            this.tvLeftTitle.setText(lefttitle);
            this.tvLeftTitle.setTextColor(btitleColor);
            this.tvBackTitle.setText(backtitle);
            this.tvBackTitle.setTextColor(btitleColor);
            this.tvBackTitle.setVisibility(backShow ? 0 : 8);
            this.tvRightTitle.setText(righttitle);
            this.tvRightTitle.setTextColor(rtitleColor);
            if (rightButtonImgId == -1) {
                this.btnRight.setVisibility(8);
                return;
            }
            this.btnRight.setVisibility(0);
            this.btnRight.setImageResource(rightButtonImgId);
        }
    }

    public void setOnBackTitleClickListener(OnClickListener listener) {
        this.tvBackTitle.setOnClickListener(listener);
    }

    public void setOnLeftTitleClickListener(OnClickListener listener) {
        this.tvLeftTitle.setOnClickListener(listener);
    }

    public void setOnRightButtonClickListener(OnClickListener listener) {
        this.btnRight.setOnClickListener(listener);
    }

    public void setOnRightTitleClickListener(OnClickListener listener) {
        this.tvRightTitle.setOnClickListener(listener);
    }

    public void setBackButtonVisible(boolean visible) {
        this.tvBackTitle.setVisibility(visible ? 0 : 8);
    }

    public void setRightButtonVisible(boolean visible) {
        this.btnRight.setVisibility(visible ? 0 : 8);
    }

    public void setRightTitleVisible(boolean visible) {
        this.tvRightTitle.setVisibility(visible ? 0 : 8);
    }

    public void setBackTitle(String title) {
        this.tvBackTitle.setText(title);
    }

    public void setBackTitle(int resid) {
        this.tvBackTitle.setText(resid);
    }

    public void setTitle(String title) {
        this.tvTitle.setText(title);
    }

    public void setTitle(int resid) {
        this.tvTitle.setText(resid);
    }

    public void setRightButtonImage(int drawableId) {
        this.btnRight.setImageResource(drawableId);
        this.btnRight.setVisibility(0);
    }

    public void setRightButtonImageDrawable(Drawable drawable) {
        if (drawable == null) {
            this.btnRight.setVisibility(8);
            return;
        }
        this.btnRight.setVisibility(0);
        this.btnRight.setImageDrawable(drawable);
    }

    public void setRightTitle(String rightTitle) {
        this.tvRightTitle.setText(rightTitle);
    }

    public void setRightTitle(int resid) {
        this.tvRightTitle.setText(resid);
    }

    public View getRootView() {
        return this.rootView;
    }

    public RelativeLayout getLayout() {
        return this.layout;
    }

    public TextView getBackTitle() {
        return this.tvBackTitle;
    }

    public TextView getTitle() {
        return this.tvTitle;
    }

    public ImageView getRightButton() {
        return this.btnRight;
    }

    public TextView getRightTitle() {
        return this.tvRightTitle;
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case C1680R.id.actionbar_back_title:
                ((Activity) getContext()).onBackPressed();
                return;
            default:
                return;
        }
    }
}
