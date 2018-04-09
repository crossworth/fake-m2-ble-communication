package com.zhuoyou.plugin.action;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.fithealth.running.R;
import com.zhuoyou.plugin.action.ILoadingLayout.State;

public class HeaderLoadingLayout extends LoadingLayout {
    private static final int ROTATE_ANIM_DURATION = 150;
    private ImageView mArrowImageView;
    private RelativeLayout mHeaderContainer;
    private TextView mHeaderTimeView;
    private TextView mHeaderTimeViewTitle;
    private TextView mHintTextView;
    private ProgressBar mProgressBar;
    private Animation mRotateDownAnim;
    private Animation mRotateUpAnim;

    public HeaderLoadingLayout(Context context) {
        super(context);
        init(context);
    }

    public HeaderLoadingLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        this.mHeaderContainer = (RelativeLayout) findViewById(R.id.pull_to_refresh_header_content);
        this.mArrowImageView = (ImageView) findViewById(R.id.pull_to_refresh_header_arrow);
        this.mHintTextView = (TextView) findViewById(R.id.pull_to_refresh_header_hint_textview);
        this.mProgressBar = (ProgressBar) findViewById(R.id.pull_to_refresh_header_progressbar);
        this.mHeaderTimeView = (TextView) findViewById(R.id.pull_to_refresh_header_time);
        this.mHeaderTimeViewTitle = (TextView) findViewById(R.id.pull_to_refresh_last_update_time_text);
        this.mRotateUpAnim = new RotateAnimation(0.0f, -180.0f, 1, 0.5f, 1, 0.5f);
        this.mRotateUpAnim.setDuration(150);
        this.mRotateUpAnim.setFillAfter(true);
        this.mRotateDownAnim = new RotateAnimation(-180.0f, 0.0f, 1, 0.5f, 1, 0.5f);
        this.mRotateDownAnim.setDuration(150);
        this.mRotateDownAnim.setFillAfter(true);
    }

    public void setLastUpdatedLabel(CharSequence label) {
        this.mHeaderTimeViewTitle.setVisibility(TextUtils.isEmpty(label) ? 4 : 0);
        this.mHeaderTimeView.setText(label);
    }

    public int getContentSize() {
        if (this.mHeaderContainer != null) {
            return this.mHeaderContainer.getHeight();
        }
        return (int) (getResources().getDisplayMetrics().density * BitmapDescriptorFactory.HUE_YELLOW);
    }

    protected View createLoadingView(Context context, AttributeSet attrs) {
        return LayoutInflater.from(context).inflate(R.layout.pull_to_refresh_header, null);
    }

    protected void onStateChanged(State curState, State oldState) {
        this.mArrowImageView.setVisibility(0);
        this.mProgressBar.setVisibility(4);
        super.onStateChanged(curState, oldState);
    }

    protected void onReset() {
        this.mArrowImageView.clearAnimation();
        this.mHintTextView.setText(R.string.pull_to_refresh_header_hint_normal);
    }

    protected void onPullToRefresh() {
        if (State.RELEASE_TO_REFRESH == getPreState()) {
            this.mArrowImageView.clearAnimation();
            this.mArrowImageView.startAnimation(this.mRotateDownAnim);
        }
        this.mHintTextView.setText(R.string.pull_to_refresh_header_hint_normal);
    }

    protected void onReleaseToRefresh() {
        this.mArrowImageView.clearAnimation();
        this.mArrowImageView.startAnimation(this.mRotateUpAnim);
        this.mHintTextView.setText(R.string.pull_to_refresh_header_hint_ready);
    }

    protected void onRefreshing() {
        this.mArrowImageView.clearAnimation();
        this.mArrowImageView.setVisibility(4);
        this.mProgressBar.setVisibility(0);
        this.mHintTextView.setText(R.string.pull_to_refresh_header_hint_loading);
    }
}
