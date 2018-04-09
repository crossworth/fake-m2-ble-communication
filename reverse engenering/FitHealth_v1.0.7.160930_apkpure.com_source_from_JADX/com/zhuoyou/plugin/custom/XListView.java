package com.zhuoyou.plugin.custom;

import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.animation.DecelerateInterpolator;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Scroller;
import android.widget.Toast;
import com.amap.api.maps.model.GroundOverlayOptions;
import com.fithealth.running.R;
import com.zhuoyou.plugin.ble.BleManagerService;
import com.zhuoyou.plugin.bluetooth.data.Util;
import com.zhuoyou.plugin.bluetooth.service.BluetoothService;
import com.zhuoyou.plugin.cloud.CloudSync;
import com.zhuoyou.plugin.running.MainService;
import com.zhuoyou.plugin.running.RunningApp;
import java.util.List;

public class XListView extends ListView implements OnScrollListener {
    private static final float OFFSET_RADIO = 2.5f;
    private static final int SCROLLBACK_HEADER = 0;
    private static final int SCROLL_DURATION = 400;
    private BroadcastReceiver mBTConnectReceiver = new C12302();
    private Context mContext;
    private boolean mEnablePullRefresh = true;
    private XListViewHeader mHeaderView;
    private RelativeLayout mHeaderViewContent;
    private int mHeaderViewHeight;
    private float mLastY = GroundOverlayOptions.NO_DIMENSION;
    private IXListViewListener mListViewListener;
    private boolean mPullRefreshing = false;
    private int mScrollBack;
    private OnScrollListener mScrollListener;
    private Scroller mScroller;
    private LinearLayout mSleepHeader;
    private OnXListHeaderViewListener mXlistHearListener;

    class C12291 implements OnGlobalLayoutListener {
        C12291() {
        }

        public void onGlobalLayout() {
            XListView.this.mHeaderViewHeight = XListView.this.mHeaderViewContent.getHeight();
            XListView.this.getViewTreeObserver().removeGlobalOnLayoutListener(this);
        }
    }

    class C12302 extends BroadcastReceiver {
        C12302() {
        }

        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals("com.zhuoyou.running.connect.success")) {
                XListView.this.mHeaderView.setState(2);
                if (XListView.this.mListViewListener != null) {
                    XListView.this.mListViewListener.onRefresh();
                }
            } else if (action.equals("com.zhuoyou.running.connect.failed")) {
                XListView.this.mPullRefreshing = false;
                XListView.this.resetHeaderHeight();
                Toast.makeText(XListView.this.mContext, R.string.xlistview_header_hint_connect_fail, 0).show();
            }
            XListView.this.mContext.unregisterReceiver(XListView.this.mBTConnectReceiver);
        }
    }

    public interface IXListViewListener {
        void onRefresh();
    }

    public interface OnXListHeaderViewListener {
        void OnHeaderSyncScrollBack(int i, int i2);

        void OnHeaderSyncShowDown(int i);
    }

    public interface OnXScrollListener extends OnScrollListener {
        void onXScrolling(View view);
    }

    public XListView(Context context) {
        super(context);
        initWithContext(context);
    }

    public XListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initWithContext(context);
    }

    public XListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initWithContext(context);
    }

    private void initWithContext(Context context) {
        this.mContext = context;
        this.mScroller = new Scroller(context, new DecelerateInterpolator());
        super.setOnScrollListener(this);
        this.mHeaderView = new XListViewHeader(context);
        this.mHeaderViewContent = (RelativeLayout) this.mHeaderView.findViewById(R.id.xlistview_header_content);
        this.mSleepHeader = (LinearLayout) this.mHeaderView.findViewById(R.id.sleep_header);
        addHeaderView(this.mHeaderView);
        this.mHeaderView.getViewTreeObserver().addOnGlobalLayoutListener(new C12291());
    }

    public void setAdapter(ListAdapter adapter) {
        super.setAdapter(adapter);
    }

    public void setPullRefreshEnable(boolean enable) {
        this.mEnablePullRefresh = enable;
        if (this.mEnablePullRefresh) {
            this.mHeaderViewContent.setVisibility(0);
        } else {
            this.mHeaderViewContent.setVisibility(4);
        }
    }

    public void stopRefresh() {
        Log.i("2222", "mPullRefreshing = " + this.mPullRefreshing);
        if (this.mPullRefreshing) {
            this.mPullRefreshing = false;
            resetHeaderHeight();
        }
    }

    private void invokeOnScrolling() {
        if (this.mScrollListener instanceof OnXScrollListener) {
            this.mScrollListener.onXScrolling(this);
        }
    }

    private void updateHeaderHeight(float delta) {
        this.mHeaderView.setVisiableHeight(((int) delta) + this.mHeaderView.getVisiableHeight());
        if (this.mXlistHearListener != null) {
            this.mXlistHearListener.OnHeaderSyncShowDown((((int) delta) + this.mHeaderView.getVisiableHeight()) + (getScrollY() >= 0 ? 0 : Math.abs(getScrollY())));
        }
        if (this.mEnablePullRefresh && !this.mPullRefreshing) {
            if (this.mHeaderView.getVisiableHeight() > this.mHeaderViewHeight) {
                this.mHeaderView.setState(1);
            } else {
                this.mHeaderView.setState(0);
            }
        }
        setSelection(0);
    }

    private void resetHeaderHeight() {
        int height = this.mHeaderView.getVisiableHeight();
        if (height != 0) {
            if (!this.mPullRefreshing || height > this.mHeaderViewHeight) {
                int finalHeight = 0;
                if (this.mPullRefreshing && height > this.mHeaderViewHeight) {
                    finalHeight = this.mHeaderViewHeight;
                }
                this.mScrollBack = 0;
                this.mScroller.startScroll(0, height, 0, finalHeight - height, 400);
                if (this.mXlistHearListener != null) {
                    this.mXlistHearListener.OnHeaderSyncScrollBack(finalHeight, 400);
                }
                invalidate();
            }
        }
    }

    private void startRefreshing() {
        if (!this.mPullRefreshing) {
            if (CloudSync.isSync) {
                resetHeaderHeight();
                Toast.makeText(this.mContext, R.string.ps_cloud_sync_wait, 0).show();
            } else if (BluetoothService.getInstance().isConnected() || (RunningApp.isBLESupport && BleManagerService.getInstance().GetBleConnectState())) {
                this.mPullRefreshing = true;
                this.mHeaderView.setState(2);
                if (this.mListViewListener != null) {
                    this.mListViewListener.onRefresh();
                }
            } else {
                List<BluetoothDevice> bondList = Util.getBondDevice();
                if (bondList == null || bondList.size() <= 0) {
                    resetHeaderHeight();
                    Toast.makeText(this.mContext, R.string.ps_connect_device, 0).show();
                    return;
                }
                this.mPullRefreshing = true;
                this.mHeaderView.setState(3);
                IntentFilter intent = new IntentFilter();
                intent.addAction("com.zhuoyou.running.connect.success");
                intent.addAction("com.zhuoyou.running.connect.failed");
                this.mContext.registerReceiver(this.mBTConnectReceiver, intent);
                Util.connect((BluetoothDevice) bondList.get(0));
            }
        }
    }

    public boolean onTouchEvent(MotionEvent ev) {
        if (this.mLastY == GroundOverlayOptions.NO_DIMENSION) {
            this.mLastY = ev.getRawY();
        }
        switch (ev.getAction()) {
            case 0:
                this.mLastY = ev.getRawY();
                break;
            case 1:
            case 3:
                this.mLastY = GroundOverlayOptions.NO_DIMENSION;
                if (getFirstVisiblePosition() == 0 && !MainService.syncnow.booleanValue()) {
                    if (this.mEnablePullRefresh && this.mHeaderView.getVisiableHeight() > this.mHeaderViewHeight) {
                        startRefreshing();
                    }
                    resetHeaderHeight();
                    break;
                }
            case 2:
                float deltaY = ev.getRawY() - this.mLastY;
                this.mLastY = ev.getRawY();
                if (getFirstVisiblePosition() == 0 && ((this.mHeaderView.getVisiableHeight() > 0 || deltaY > 0.0f) && !MainService.syncnow.booleanValue())) {
                    updateHeaderHeight(deltaY / OFFSET_RADIO);
                    invokeOnScrolling();
                    break;
                }
        }
        return super.onTouchEvent(ev);
    }

    public void computeScroll() {
        if (this.mScroller.computeScrollOffset()) {
            if (this.mScrollBack == 0) {
                this.mHeaderView.setVisiableHeight(this.mScroller.getCurrY());
            }
            postInvalidate();
            invokeOnScrolling();
        }
        super.computeScroll();
    }

    public void setOnScrollListener(OnScrollListener l) {
        this.mScrollListener = l;
    }

    public void onScrollStateChanged(AbsListView view, int scrollState) {
        if (this.mScrollListener != null) {
            this.mScrollListener.onScrollStateChanged(view, scrollState);
        }
    }

    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        if (this.mScrollListener != null) {
            this.mScrollListener.onScroll(view, firstVisibleItem, visibleItemCount, totalItemCount);
        }
    }

    public void setXListViewListener(IXListViewListener l) {
        this.mListViewListener = l;
    }

    public void SetXlistHeaderLisetener(OnXListHeaderViewListener mlistener) {
        this.mXlistHearListener = mlistener;
    }

    public void setSleepHeaderBackground() {
        this.mSleepHeader.setBackgroundColor(Color.rgb(65, 54, an.f2210case));
    }
}
