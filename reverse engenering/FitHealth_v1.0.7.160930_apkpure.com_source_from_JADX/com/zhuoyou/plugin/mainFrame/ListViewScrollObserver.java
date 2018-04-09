package com.zhuoyou.plugin.mainFrame;

import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ListView;

public class ListViewScrollObserver implements OnScrollListener {
    private OnListViewScrollListener listener;

    public interface OnListViewScrollListener {
        void onScrollListDispatch(AbsListView absListView, int i, int i2, int i3);

        void onScrollStateChange(int i);
    }

    public ListViewScrollObserver(ListView listView) {
        listView.setOnScrollListener(this);
    }

    public void setOnScrollUpAndDownListener(OnListViewScrollListener listener) {
        this.listener = listener;
    }

    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        if (this.listener != null) {
            this.listener.onScrollListDispatch(view, firstVisibleItem, visibleItemCount, totalItemCount);
        }
    }

    public void onScrollStateChanged(AbsListView view, int scrollState) {
        if (this.listener != null) {
            this.listener.onScrollStateChange(scrollState);
        }
    }
}
