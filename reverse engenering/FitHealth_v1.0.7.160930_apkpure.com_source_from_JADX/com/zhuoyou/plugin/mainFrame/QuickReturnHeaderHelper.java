package com.zhuoyou.plugin.mainFrame;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.animation.Animation;
import android.widget.AbsListView;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import com.fithealth.running.R;
import com.zhuoyou.plugin.custom.XListView;
import com.zhuoyou.plugin.custom.XListView.OnXListHeaderViewListener;
import com.zhuoyou.plugin.mainFrame.ListViewScrollObserver.OnListViewScrollListener;

public class QuickReturnHeaderHelper implements OnGlobalLayoutListener, OnXListHeaderViewListener {
    private static final long ANIMATION_DURATION = 400;
    protected static final String TAG = "QuickReturnHeaderHelper";
    private static int scroll_state = 0;
    private Animation animation;
    private View content;
    private int contentResId;
    private Context context;
    private int drag_down_delta = 0;
    private View dummyHeader;
    private Animation head_scroll_up_animation;
    private int headerHeight;
    private int headerResId;
    private int headerTop;
    private LayoutInflater inflater;
    private int lastFirstVisibleItem;
    private int lastHeight;
    private int lastTop;
    private ViewGroup listParentContainer;
    private XListView listView;
    private OnListScrollForFragment mFragment_listener;
    private OnSnappedChangeListener onSnappedChangeListener;
    private View realHeader;
    private LayoutParams realHeaderLayoutParams;
    private ViewGroup root;
    private int scrollPosition;
    private boolean scrollingUp;
    private boolean snapped = true;
    private boolean waitingForExactHeaderHeight = true;

    public interface OnListScrollForFragment {
        void onScrollListDispatch(AbsListView absListView, int i, int i2, int i3, int i4);

        void onScrollStateChange(int i);
    }

    public interface OnSnappedChangeListener {
        void onSnappedChange(boolean z);
    }

    class C18911 implements OnListViewScrollListener {
        C18911() {
        }

        public void onScrollStateChange(int state) {
            QuickReturnHeaderHelper.scroll_state = state;
            if (QuickReturnHeaderHelper.this.mFragment_listener != null) {
                QuickReturnHeaderHelper.this.mFragment_listener.onScrollStateChange(state);
            }
        }

        public void onScrollListDispatch(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
            View firstChild = view.getChildAt(0);
            if (firstChild != null) {
                int delta;
                int top = firstChild.getTop();
                int height = firstChild.getHeight();
                if (QuickReturnHeaderHelper.this.lastFirstVisibleItem == firstVisibleItem) {
                    delta = QuickReturnHeaderHelper.this.lastTop - top;
                } else if (firstVisibleItem > QuickReturnHeaderHelper.this.lastFirstVisibleItem) {
                    delta = (((((firstVisibleItem - QuickReturnHeaderHelper.this.lastFirstVisibleItem) - 1) * height) + QuickReturnHeaderHelper.this.lastHeight) + QuickReturnHeaderHelper.this.lastTop) - top;
                } else {
                    delta = (((-height) * ((QuickReturnHeaderHelper.this.lastFirstVisibleItem - firstVisibleItem) - 1)) + QuickReturnHeaderHelper.this.lastTop) - (height + top);
                }
                if (QuickReturnHeaderHelper.this.mFragment_listener != null) {
                    QuickReturnHeaderHelper.this.mFragment_listener.onScrollListDispatch(view, firstVisibleItem, visibleItemCount, totalItemCount, delta);
                }
                if (firstVisibleItem == 1) {
                    if (delta < 0) {
                        if (top + delta >= QuickReturnHeaderHelper.this.headerHeight || QuickReturnHeaderHelper.this.headerTop == 0) {
                            QuickReturnHeaderHelper.this.headerTop = 0;
                        } else {
                            QuickReturnHeaderHelper.this.headerTop = top;
                        }
                    } else if (delta > 0) {
                        if (top + delta < 0) {
                            QuickReturnHeaderHelper.this.headerTop = top;
                        } else {
                            QuickReturnHeaderHelper.this.headerTop = 0;
                        }
                    }
                } else if (firstVisibleItem == 0) {
                    if (delta == 0 && top == 0) {
                        QuickReturnHeaderHelper.this.headerTop = QuickReturnHeaderHelper.this.drag_down_delta;
                    } else if (delta > 0) {
                        QuickReturnHeaderHelper.this.headerTop = QuickReturnHeaderHelper.this.drag_down_delta;
                    }
                } else if (delta < 0) {
                    QuickReturnHeaderHelper.this.headerTop = 0;
                } else if (delta > 0) {
                    QuickReturnHeaderHelper.this.headerTop = -QuickReturnHeaderHelper.this.headerHeight;
                }
                if (QuickReturnHeaderHelper.this.realHeaderLayoutParams.topMargin != QuickReturnHeaderHelper.this.headerTop) {
                    QuickReturnHeaderHelper.this.realHeaderLayoutParams.topMargin = QuickReturnHeaderHelper.this.headerTop;
                    Log.v(QuickReturnHeaderHelper.TAG, "topMargin=" + QuickReturnHeaderHelper.this.headerTop);
                    QuickReturnHeaderHelper.this.realHeader.setLayoutParams(QuickReturnHeaderHelper.this.realHeaderLayoutParams);
                }
                QuickReturnHeaderHelper.this.lastFirstVisibleItem = firstVisibleItem;
                QuickReturnHeaderHelper.this.lastTop = top;
                QuickReturnHeaderHelper.this.lastHeight = firstChild.getHeight();
            }
        }
    }

    public QuickReturnHeaderHelper(Context context, int contentResId, int headerResId) {
        this.context = context;
        this.contentResId = contentResId;
        this.headerResId = headerResId;
    }

    public QuickReturnHeaderHelper(Context context, int contentResId, int headerResId, ViewGroup listparent) {
        this.context = context;
        this.contentResId = contentResId;
        this.headerResId = headerResId;
        this.listParentContainer = listparent;
    }

    public View createView() {
        this.inflater = LayoutInflater.from(this.context);
        this.content = this.inflater.inflate(this.contentResId, this.listParentContainer, false);
        this.realHeader = this.inflater.inflate(this.headerResId, this.listParentContainer, false);
        this.realHeaderLayoutParams = new LayoutParams(-1, -2);
        this.realHeaderLayoutParams.gravity = 48;
        this.realHeader.measure(0, 0);
        this.headerHeight = this.realHeader.getMeasuredHeight();
        this.listView = (XListView) this.content.findViewById(R.id.listview_activity);
        if (this.listView != null) {
            createListView();
        }
        this.listView.SetXlistHeaderLisetener(this);
        return this.root;
    }

    public void setOnSnappedChangeListener(OnSnappedChangeListener onSnapListener) {
        this.onSnappedChangeListener = onSnapListener;
    }

    public void setOnListScrollForFragmentListener(OnListScrollForFragment fragmentlistlistener) {
        this.mFragment_listener = fragmentlistlistener;
    }

    private void createListView() {
        this.root = (FrameLayout) this.inflater.inflate(R.layout.qrh_listview_container, this.listParentContainer, false);
        this.root.addView(this.content);
        this.listView.getViewTreeObserver().addOnGlobalLayoutListener(this);
        new ListViewScrollObserver(this.listView).setOnScrollUpAndDownListener(new C18911());
        this.root.addView(this.realHeader, this.realHeaderLayoutParams);
        this.dummyHeader = new View(this.context);
        this.dummyHeader.setLayoutParams(new AbsListView.LayoutParams(-1, this.headerHeight));
        this.listView.addHeaderView(this.dummyHeader);
    }

    private void snap(boolean newValue) {
        if (this.snapped != newValue) {
            this.snapped = newValue;
            if (this.onSnappedChangeListener != null) {
                this.onSnappedChangeListener.onSnappedChange(this.snapped);
            }
            Log.v(TAG, "snapped=" + this.snapped);
        }
    }

    public void onGlobalLayout() {
        if (this.waitingForExactHeaderHeight && this.dummyHeader.getHeight() > 0) {
            this.headerHeight = this.dummyHeader.getHeight();
            this.waitingForExactHeaderHeight = false;
            ViewGroup.LayoutParams params = this.dummyHeader.getLayoutParams();
            params.height = this.headerHeight;
            this.dummyHeader.setLayoutParams(params);
        }
    }

    public void OnHeaderSyncShowDown(int delta) {
        this.drag_down_delta = delta;
    }

    public void OnHeaderSyncScrollBack(int position, int time) {
        this.drag_down_delta = position;
        this.headerTop = this.drag_down_delta;
    }

    public void reSetHeaderMargin() {
        if (this.headerTop != 0) {
            this.headerTop = 0;
            this.realHeaderLayoutParams.topMargin = this.headerTop;
            this.realHeader.setLayoutParams(this.realHeaderLayoutParams);
        }
    }
}
