package com.zhuoyou.plugin.running;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;
import com.amap.api.maps.model.WeightedLatLng;
import com.amap.api.services.core.AMapException;
import com.fithealth.running.R;
import com.zhuoyou.plugin.bluetooth.data.Util;
import com.zhuoyou.plugin.custom.XListView;
import com.zhuoyou.plugin.custom.XListView.IXListViewListener;
import com.zhuoyou.plugin.custom.XListView.OnXListHeaderViewListener;
import com.zhuoyou.plugin.database.DataBaseUtil;
import com.zhuoyou.plugin.mainFrame.SleepDetailActivity;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SleepPageItemFragment extends Fragment implements OnItemClickListener, IXListViewListener, OnXListHeaderViewListener {
    public static final int SYNC_DEVICE_FAILED = 1;
    public static final int SYNC_DEVICE_SUCCESSED = 2;
    public static Map<String, Bitmap> gridviewBitmapCaches = new HashMap();
    public static Handler mHandler;
    private int drag_down_delta = 0;
    private View dummyHeader;
    private int headerHeight;
    private int headerTop;
    private int lastFirstVisibleItem;
    private int lastHeight;
    private int lastTop;
    private int lastheaderTop;
    private TextView mCaloriesTv;
    private String mDate;
    private ImageView mDayLeft;
    private ImageView mDayRight;
    private SleepPageListItemAdapter mListAdapter;
    private XListView mListView;
    private Typeface mNumberTP = RunningApp.getCustomNumberFont();
    private ImageView mPercentArc;
    private SleepPageListListener mSleeppageListListener;
    private TextView mStepsTv;
    private TextView mSummayTv;
    private TextView mSummayUnit;
    private List<SleepItem> mTodayLists = null;
    private View mroot;
    private View realHeader;
    private Context sContext = RunningApp.getInstance().getApplicationContext();
    private boolean waitingForExactHeaderHeight = true;

    class C14161 extends Handler {
        C14161() {
        }

        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    SleepPageItemFragment.this.mListView.stopRefresh();
                    return;
                case 2:
                    SleepPageItemFragment.this.mListView.stopRefresh();
                    SleepPageItemFragment.this.setLastUpdateTime();
                    return;
                default:
                    return;
            }
        }
    }

    class C14172 implements OnScrollListener {
        C14172() {
        }

        @SuppressLint({"NewApi"})
        public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
            View firstChild = view.getChildAt(0);
            if (firstChild != null) {
                int delta;
                int top = firstChild.getTop();
                int height = firstChild.getHeight();
                if (SleepPageItemFragment.this.lastFirstVisibleItem == firstVisibleItem) {
                    delta = SleepPageItemFragment.this.lastTop - top;
                } else if (firstVisibleItem > SleepPageItemFragment.this.lastFirstVisibleItem) {
                    delta = (((((firstVisibleItem - SleepPageItemFragment.this.lastFirstVisibleItem) - 1) * height) + SleepPageItemFragment.this.lastHeight) + SleepPageItemFragment.this.lastTop) - top;
                } else {
                    delta = (((-height) * ((SleepPageItemFragment.this.lastFirstVisibleItem - firstVisibleItem) - 1)) + SleepPageItemFragment.this.lastTop) - (height + top);
                }
                if (firstVisibleItem == 1) {
                    if (delta < 0) {
                        if (top + delta >= SleepPageItemFragment.this.headerHeight || SleepPageItemFragment.this.headerTop == 0) {
                            SleepPageItemFragment.this.headerTop = 0;
                        } else {
                            SleepPageItemFragment.this.headerTop = top;
                        }
                    } else if (delta > 0) {
                        if (top + delta < 0) {
                            SleepPageItemFragment.this.headerTop = top;
                        } else {
                            SleepPageItemFragment.this.headerTop = 0;
                        }
                    }
                } else if (firstVisibleItem == 0) {
                    Log.d("zzb", "drag_down delta in OnHeaderSyncShowDown = " + SleepPageItemFragment.this.drag_down_delta);
                    if (delta == 0 && top == 0) {
                        SleepPageItemFragment.this.headerTop = SleepPageItemFragment.this.drag_down_delta;
                    } else if (delta > 0) {
                        SleepPageItemFragment.this.headerTop = SleepPageItemFragment.this.drag_down_delta;
                    }
                } else {
                    Log.d("zzb", "lastFirstVisibleItem =" + SleepPageItemFragment.this.lastFirstVisibleItem + "firstVisibleItem =" + firstVisibleItem);
                    if (delta < 0) {
                        SleepPageItemFragment.this.headerTop = 0;
                    } else if (delta > 0) {
                        SleepPageItemFragment.this.headerTop = -SleepPageItemFragment.this.headerHeight;
                    }
                }
                if (SleepPageItemFragment.this.lastheaderTop != SleepPageItemFragment.this.headerTop) {
                    SleepPageItemFragment.this.lastheaderTop = SleepPageItemFragment.this.headerTop;
                    Log.d("zzb", "topMargin=" + SleepPageItemFragment.this.headerTop);
                    SleepPageItemFragment.this.realHeader.setTranslationY((float) SleepPageItemFragment.this.headerTop);
                }
                SleepPageItemFragment.this.lastFirstVisibleItem = firstVisibleItem;
                SleepPageItemFragment.this.lastTop = top;
                SleepPageItemFragment.this.lastHeight = firstChild.getHeight();
                SleepPageItemFragment.this.recycleBitmapCaches(0, firstVisibleItem);
                SleepPageItemFragment.this.recycleBitmapCaches((firstVisibleItem + visibleItemCount) - 2, totalItemCount);
                if (SleepPageItemFragment.this.mSleeppageListListener != null) {
                    SleepPageItemFragment.this.mSleeppageListListener.onScrollListDispatch(view, firstVisibleItem, visibleItemCount, totalItemCount, delta);
                }
            }
        }

        public void onScrollStateChanged(AbsListView view, int scrollState) {
        }
    }

    class C14183 implements OnGlobalLayoutListener {
        C14183() {
        }

        public void onGlobalLayout() {
            Log.d("zzb", "onGlobalLayout");
            if (SleepPageItemFragment.this.waitingForExactHeaderHeight && SleepPageItemFragment.this.dummyHeader.getHeight() > 0) {
                SleepPageItemFragment.this.headerHeight = SleepPageItemFragment.this.dummyHeader.getHeight();
                SleepPageItemFragment.this.waitingForExactHeaderHeight = false;
                LayoutParams params = SleepPageItemFragment.this.dummyHeader.getLayoutParams();
                params.height = SleepPageItemFragment.this.headerHeight;
                SleepPageItemFragment.this.dummyHeader.setLayoutParams(params);
                SleepPageItemFragment.this.mListView.getViewTreeObserver().removeGlobalOnLayoutListener(this);
            }
        }
    }

    interface SleepPageListListener {
        void onScrollListDispatch(AbsListView absListView, int i, int i2, int i3, int i4);

        void onScrollStateChange(int i);
    }

    public static SleepPageItemFragment newInstance(String bean) {
        SleepPageItemFragment fragment = new SleepPageItemFragment();
        fragment.mDate = bean;
        return fragment;
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mHandler = new C14161();
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.mroot = inflater.inflate(R.layout.sleep_page_item, container, false);
        this.realHeader = this.mroot.findViewById(R.id.header);
        this.mListView = (XListView) this.mroot.findViewById(R.id.listview_sleep);
        this.mListView.setOnItemClickListener(this);
        this.mListView.setXListViewListener(this);
        this.mListView.setSleepHeaderBackground();
        return this.mroot;
    }

    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        this.realHeader.measure(0, 0);
        this.headerHeight = this.realHeader.getMeasuredHeight();
        this.mListView.setOnScrollListener(new C14172());
        this.mListView.getViewTreeObserver().addOnGlobalLayoutListener(new C14183());
        this.mListView.SetXlistHeaderLisetener(this);
        this.dummyHeader = new View(getActivity());
        this.dummyHeader.setLayoutParams(new AbsListView.LayoutParams(-1, this.headerHeight));
        this.mListView.addHeaderView(this.dummyHeader);
    }

    private void recycleBitmapCaches(int fromPosition, int toPosition) {
        for (int del = fromPosition; del < toPosition - 2; del++) {
            String url = ((SleepItem) this.mTodayLists.get(del)).getmImgUri();
            if (!(url == null || url.equals(""))) {
                String path = Tools.getSDPath() + "/Running/.thumbnailnew/" + url.substring(url.lastIndexOf("/") + 1, url.lastIndexOf("."));
                if (gridviewBitmapCaches != null && gridviewBitmapCaches.size() > 0) {
                    Bitmap delBitmap = (Bitmap) gridviewBitmapCaches.get(path + ((SleepItem) this.mTodayLists.get(del)).getDate() + del);
                    if (delBitmap != null) {
                        gridviewBitmapCaches.remove(path + ((SleepItem) this.mTodayLists.get(del)).getDate() + del);
                        delBitmap.recycle();
                        System.gc();
                    }
                }
            }
        }
    }

    public void hideImageAnimated(final ImageView iv) {
        Animation alpha = new AlphaAnimation(1.0f, 0.0f);
        alpha.setDuration(2000);
        alpha.setAnimationListener(new AnimationListener() {
            public void onAnimationEnd(Animation arg0) {
                iv.setVisibility(8);
            }

            public void onAnimationRepeat(Animation arg0) {
            }

            public void onAnimationStart(Animation arg0) {
            }
        });
        iv.startAnimation(alpha);
    }

    public void onResume() {
        super.onResume();
        Log.i("gchk", "item onResume");
        this.mPercentArc = (ImageView) this.mroot.findViewById(R.id.percent_arc_iv);
        this.mSummayTv = (TextView) this.mroot.findViewById(R.id.summay_tv);
        this.mSummayUnit = (TextView) this.mroot.findViewById(R.id.summay_unit);
        this.mSummayUnit.setText("%");
        this.mStepsTv = (TextView) this.mroot.findViewById(R.id.steps_tv);
        this.mCaloriesTv = (TextView) this.mroot.findViewById(R.id.calories_tv);
        this.mSummayTv.setTypeface(this.mNumberTP);
        this.mSummayUnit.setTypeface(this.mNumberTP);
        this.mDayLeft = (ImageView) this.mroot.findViewById(R.id.day_btn_left);
        this.mDayRight = (ImageView) this.mroot.findViewById(R.id.day_btn_right);
        this.mSummayTv.setText("0");
        this.mStepsTv.setText(Tools.getTimer(0));
        this.mCaloriesTv.setText(Tools.getTimer(0));
        updateNotificationRemoteViews(this.sContext, this.mPercentArc, 0.0f);
        hideImageAnimated(this.mDayLeft);
        hideImageAnimated(this.mDayRight);
        initListData();
    }

    public void onPause() {
        super.onPause();
        Log.i("gchk", "item onPause");
    }

    public void onDetach() {
        super.onDetach();
        Log.i("SleepPageItemFragment", "item onDetach");
    }

    private void initListData() {
        Log.i("SleepPageItemFragment", "mDateBean.getDate():" + this.mDate);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        try {
            date = sdf.parse(this.mDate);
        } catch (ParseException e) {
        }
        long start = ((((long) (date.getYear() + AMapException.CODE_AMAP_CLIENT_UNKNOWN_ERROR)) * 10000000000L) + (((long) (date.getMonth() + 1)) * 100000000)) + (((long) date.getDate()) * 1000000);
        long end = ((((long) (date.getYear() + AMapException.CODE_AMAP_CLIENT_UNKNOWN_ERROR)) * 10000000000L) + (((long) (date.getMonth() + 1)) * 100000000)) + (((long) (date.getDate() + 1)) * 1000000);
        Log.i("SleepPageItemFragment", "start:" + start);
        Log.i("SleepPageItemFragment", "end:" + end);
        this.mTodayLists = DataBaseUtil.getSleepItem(this.sContext, start, end);
        if (this.mTodayLists != null && this.mTodayLists.size() == 0) {
            this.mTodayLists = DataBaseUtil.getClassicSleepItem(this.sContext, this.mDate);
        }
        this.mListAdapter = new SleepPageListItemAdapter(getActivity(), this.mTodayLists);
        this.mListView.setAdapter((ListAdapter) this.mListAdapter);
        int Sleep = 0;
        int DeepSleep = 0;
        int LightSleep = 0;
        if (this.mTodayLists != null && this.mTodayLists.size() > 0) {
            for (int i = 0; i < this.mTodayLists.size(); i++) {
                SleepItem item = (SleepItem) this.mTodayLists.get(i);
                int sleep = item.getmSleepT();
                Sleep += sleep;
                int deepSleep = item.getmDSleepT();
                DeepSleep += deepSleep;
                LightSleep += sleep - deepSleep;
                if (Sleep != 0 && Sleep > DeepSleep) {
                    int rate = (DeepSleep * 100) / Sleep;
                    this.mSummayTv.setText(rate + "");
                    this.mStepsTv.setText(Tools.getTimer(Sleep));
                    this.mCaloriesTv.setText(Tools.getTimer(DeepSleep));
                    updateNotificationRemoteViews(this.sContext, this.mPercentArc, ((float) rate) / 100.0f);
                }
            }
        }
    }

    private void setLastUpdateTime() {
        String text = formatDateTime(System.currentTimeMillis());
        Editor editor = PreferenceManager.getDefaultSharedPreferences(this.sContext).edit();
        editor.putString("sync_device_time", text);
        editor.commit();
    }

    private String formatDateTime(long time) {
        SimpleDateFormat mDateFormat = new SimpleDateFormat("MM-dd HH:mm");
        if (0 == time) {
            return "";
        }
        return mDateFormat.format(new Date(time));
    }

    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
        int index = position - 2;
        if (index >= 0) {
            SleepItem item = (SleepItem) this.mTodayLists.get(index);
            Log.i("2222", "position=" + position);
            Log.i("2222", "item=" + item);
            Intent intent = new Intent(getActivity(), SleepDetailActivity.class);
            intent.putExtra("item", item);
            startActivity(intent);
        }
    }

    public void updateNotificationRemoteViews(Context paramContext, ImageView mPerArc, float num) {
        double d = (double) num;
        if (num > 0.0f) {
            if (((double) num) < 0.01d) {
                d = 0.01d;
            }
            if (((double) num) >= WeightedLatLng.DEFAULT_INTENSITY) {
                d = WeightedLatLng.DEFAULT_INTENSITY;
            }
        }
        NumberFormat.getPercentInstance().setMinimumFractionDigits(0);
        Bitmap localBitmap = Bitmap.createBitmap(Util.dip2pixel(paramContext, 73.0f), Util.dip2pixel(paramContext, 73.0f), Config.ARGB_8888);
        Canvas localCanvas = new Canvas(localBitmap);
        Paint localPaint = new Paint();
        localPaint.setAntiAlias(true);
        localPaint.setColor(-1);
        localPaint.setStyle(Style.STROKE);
        localPaint.setStrokeWidth((float) Util.dip2pixel(paramContext, 3.0f));
        localCanvas.drawArc(new RectF((float) Util.dip2pixel(paramContext, 1.8f), (float) Util.dip2pixel(paramContext, 1.8f), (float) Util.dip2pixel(paramContext, 71.0f), (float) Util.dip2pixel(paramContext, 71.0f)), -90.0f, (float) ((int) (360.0d * d)), false, localPaint);
        mPerArc.setImageBitmap(localBitmap);
    }

    public void onRefresh() {
        if (MainService.getInstance() != null) {
            MainService.getInstance().syncWithDevice();
        }
    }

    public void OnHeaderSyncShowDown(int delta) {
        Log.d("zzb", "11OnHeaderSyncShowDown:" + this.drag_down_delta);
        this.drag_down_delta = delta;
    }

    public void OnHeaderSyncScrollBack(int position, int time) {
        this.drag_down_delta = position;
        this.headerTop = this.drag_down_delta;
    }
}
