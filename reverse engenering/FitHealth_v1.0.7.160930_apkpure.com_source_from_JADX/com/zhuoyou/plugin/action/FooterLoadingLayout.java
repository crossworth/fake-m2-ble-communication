package com.zhuoyou.plugin.action;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.fithealth.running.R;
import com.zhuoyou.plugin.action.ILoadingLayout.State;

public class FooterLoadingLayout extends LoadingLayout {
    private TextView mHintView;
    private ProgressBar mProgressBar;

    public FooterLoadingLayout(Context context) {
        super(context);
        init(context);
    }

    public FooterLoadingLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        this.mProgressBar = (ProgressBar) findViewById(R.id.pull_to_load_footer_progressbar);
        this.mHintView = (TextView) findViewById(R.id.pull_to_load_footer_hint_textview);
        setState(State.RESET);
    }

    protected View createLoadingView(Context context, AttributeSet attrs) {
        return LayoutInflater.from(context).inflate(R.layout.pull_to_load_footer, null);
    }

    public void setLastUpdatedLabel(CharSequence label) {
    }

    public int getContentSize() {
        View view = findViewById(R.id.pull_to_load_footer_content);
        if (view != null) {
            return view.getHeight();
        }
        return (int) (getResources().getDisplayMetrics().density * 40.0f);
    }

    protected void onStateChanged(State curState, State oldState) {
        this.mProgressBar.setVisibility(8);
        this.mHintView.setVisibility(4);
        super.onStateChanged(curState, oldState);
    }

    protected void onReset() {
        this.mHintView.setText(R.string.pull_to_refresh_header_hint_loading);
    }

    protected void onPullToRefresh() {
        this.mHintView.setVisibility(0);
        this.mHintView.setText(R.string.pull_to_refresh_header_hint_normal2);
    }

    protected void onReleaseToRefresh() {
        this.mHintView.setVisibility(0);
        this.mHintView.setText(R.string.pull_to_refresh_header_hint_ready);
    }

    protected void onRefreshing() {
        this.mProgressBar.setVisibility(0);
        this.mHintView.setVisibility(0);
        this.mHintView.setText(R.string.pull_to_refresh_header_hint_loading);
    }

    protected void onNoMoreData() {
        this.mHintView.setVisibility(0);
        this.mHintView.setText(R.string.pushmsg_center_no_more_msg);
    }
}
