package com.zhuoyou.plugin.action;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.Adapter;
import android.widget.ListView;
import com.fithealth.running.R;
import com.zhuoyou.plugin.action.ILoadingLayout.State;

public class PullToRefreshListView extends PullToRefreshBase<ListView> implements OnScrollListener {
    private ListView mListView;
    private LoadingLayout mLoadMoreFooterLayout;
    private OnScrollListener mScrollListener;

    public PullToRefreshListView(Context context) {
        this(context, null);
    }

    public PullToRefreshListView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PullToRefreshListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setPullLoadEnabled(false);
    }

    protected ListView createRefreshableView(Context context, AttributeSet attrs) {
        ListView listView = new ListView(context);
        this.mListView = listView;
        this.mListView.setVerticalScrollBarEnabled(false);
        this.mListView.setSelector(R.drawable.transparent);
        listView.setOnScrollListener(this);
        return listView;
    }

    public void setHasMoreData(boolean hasMoreData) {
        if (!hasMoreData) {
            if (this.mLoadMoreFooterLayout != null) {
                this.mLoadMoreFooterLayout.setState(State.NO_MORE_DATA);
            }
            LoadingLayout footerLoadingLayout = getFooterLoadingLayout();
            if (footerLoadingLayout != null) {
                footerLoadingLayout.setState(State.NO_MORE_DATA);
            }
        }
    }

    public void setOnScrollListener(OnScrollListener l) {
        this.mScrollListener = l;
    }

    protected boolean isReadyForPullUp() {
        return isLastItemVisible();
    }

    protected boolean isReadyForPullDown() {
        return isFirstItemVisible();
    }

    protected void startLoading() {
        super.startLoading();
        if (this.mLoadMoreFooterLayout != null) {
            this.mLoadMoreFooterLayout.setState(State.REFRESHING);
        }
    }

    public void onPullUpRefreshComplete() {
        super.onPullUpRefreshComplete();
        if (this.mLoadMoreFooterLayout != null) {
            this.mLoadMoreFooterLayout.setState(State.RESET);
        }
    }

    public void setScrollLoadEnabled(boolean scrollLoadEnabled) {
        super.setScrollLoadEnabled(scrollLoadEnabled);
        if (scrollLoadEnabled) {
            if (this.mLoadMoreFooterLayout == null) {
                this.mLoadMoreFooterLayout = new FooterLoadingLayout(getContext());
            }
            if (this.mLoadMoreFooterLayout.getParent() == null) {
                this.mListView.addFooterView(this.mLoadMoreFooterLayout, null, false);
            }
            this.mLoadMoreFooterLayout.show(true);
        } else if (this.mLoadMoreFooterLayout != null) {
            this.mLoadMoreFooterLayout.show(false);
        }
    }

    public LoadingLayout getFooterLoadingLayout() {
        if (isScrollLoadEnabled()) {
            return this.mLoadMoreFooterLayout;
        }
        return super.getFooterLoadingLayout();
    }

    public void onScrollStateChanged(AbsListView view, int scrollState) {
        if (isScrollLoadEnabled() && hasMoreData() && ((scrollState == 0 || scrollState == 2) && isReadyForPullUp())) {
            startLoading();
        }
        if (this.mScrollListener != null) {
            this.mScrollListener.onScrollStateChanged(view, scrollState);
        }
    }

    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        if (this.mScrollListener != null) {
            this.mScrollListener.onScroll(view, firstVisibleItem, visibleItemCount, totalItemCount);
        }
    }

    protected LoadingLayout createHeaderLoadingLayout(Context context, AttributeSet attrs) {
        return new RotateLoadingLayout(context);
    }

    private boolean hasMoreData() {
        if (this.mLoadMoreFooterLayout == null || this.mLoadMoreFooterLayout.getState() != State.NO_MORE_DATA) {
            return true;
        }
        return false;
    }

    private boolean isFirstItemVisible() {
        Adapter adapter = this.mListView.getAdapter();
        if (adapter == null || adapter.isEmpty()) {
            return true;
        }
        int mostTop;
        if (this.mListView.getChildCount() > 0) {
            mostTop = this.mListView.getChildAt(0).getTop();
        } else {
            mostTop = 0;
        }
        if (mostTop < 0) {
            return false;
        }
        return true;
    }

    private boolean isLastItemVisible() {
        Adapter adapter = this.mListView.getAdapter();
        if (adapter == null || adapter.isEmpty()) {
            return true;
        }
        int lastItemPosition = adapter.getCount() - 1;
        int lastVisiblePosition = this.mListView.getLastVisiblePosition();
        if (lastVisiblePosition >= lastItemPosition - 1) {
            View lastVisibleChild = this.mListView.getChildAt(Math.min(lastVisiblePosition - this.mListView.getFirstVisiblePosition(), this.mListView.getChildCount() - 1));
            if (lastVisibleChild != null) {
                if (lastVisibleChild.getBottom() > this.mListView.getBottom()) {
                    return false;
                }
                return true;
            }
        }
        return false;
    }
}
