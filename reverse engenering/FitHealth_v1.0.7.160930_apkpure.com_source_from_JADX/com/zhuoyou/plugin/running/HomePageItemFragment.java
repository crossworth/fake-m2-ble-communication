package com.zhuoyou.plugin.running;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences.Editor;
import android.database.Cursor;
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
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.FrameLayout.LayoutParams;
import android.widget.ImageView;
import android.widget.TextView;
import com.amap.api.maps.model.WeightedLatLng;
import com.fithealth.running.R;
import com.zhuoyou.plugin.add.AddSports;
import com.zhuoyou.plugin.add.AddWeight;
import com.zhuoyou.plugin.add.HeartRateDetailsActivity;
import com.zhuoyou.plugin.bluetooth.data.Util;
import com.zhuoyou.plugin.custom.XListView;
import com.zhuoyou.plugin.custom.XListView.IXListViewListener;
import com.zhuoyou.plugin.custom.XListView.OnXListHeaderViewListener;
import com.zhuoyou.plugin.database.DataBaseContants;
import com.zhuoyou.plugin.database.DataBaseUtil;
import com.zhuoyou.plugin.gps.GpsSportInfo;
import com.zhuoyou.plugin.mainFrame.QuickReturnHeaderHelper;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HomePageItemFragment extends Fragment implements OnItemClickListener, IXListViewListener, OnXListHeaderViewListener {
    public static final int SYNC_DEVICE_FAILED = 1;
    public static final int SYNC_DEVICE_SUCCESSED = 2;
    public static Map<String, Bitmap> gridviewBitmapCaches = new HashMap();
    public static Handler mHandler;
    private int drag_down_delta = 0;
    private View dummyHeader;
    private int headerHeight;
    private int headerTop;
    private List<RunningItem> heartList;
    private QuickReturnHeaderHelper helper;
    private int lastFirstVisibleItem;
    private int lastHeight;
    private int lastTop;
    private int lastheaderTop;
    private TextView mCaloriesTv;
    private RunningItem mDateBean;
    private ImageView mDayLeft;
    private ImageView mDayRight;
    private HomepageListListener mHomepageListListener;
    private TextView mKilometreTv;
    private HomePageListItemAdapter mListAdapter;
    private XListView mListView;
    private Typeface mNumberTP = RunningApp.getCustomNumberFont();
    private ImageView mPercentArc;
    private int[] mSteps = null;
    private TextView mStepsTv;
    private TextView mSummayTv;
    private TextView mSummayUnit;
    private List<RunningItem> mTodayLists = new ArrayList();
    private TextView mWeatherTv;
    private View mroot;
    private float num = 0.0f;
    private PersonalGoal personal;
    private View realHeader;
    private LayoutParams realHeaderLayoutParams;
    private Context sContext = RunningApp.getInstance().getApplicationContext();
    private int scrollPosition;
    private Map<String, Integer> steps = null;
    private boolean waitingForExactHeaderHeight = true;
    private Map<String, String> weight = null;
    private ArrayList<Double> weightList = null;

    class C13671 extends Handler {
        C13671() {
        }

        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    HomePageItemFragment.this.mListView.stopRefresh();
                    return;
                case 2:
                    HomePageItemFragment.this.mListView.stopRefresh();
                    HomePageItemFragment.this.setLastUpdateTime();
                    return;
                default:
                    return;
            }
        }
    }

    class C13682 implements OnScrollListener {
        C13682() {
        }

        @SuppressLint({"NewApi"})
        public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
            View firstChild = view.getChildAt(0);
            if (firstChild != null) {
                int delta;
                int top = firstChild.getTop();
                int height = firstChild.getHeight();
                if (HomePageItemFragment.this.lastFirstVisibleItem == firstVisibleItem) {
                    delta = HomePageItemFragment.this.lastTop - top;
                } else if (firstVisibleItem > HomePageItemFragment.this.lastFirstVisibleItem) {
                    delta = (((((firstVisibleItem - HomePageItemFragment.this.lastFirstVisibleItem) - 1) * height) + HomePageItemFragment.this.lastHeight) + HomePageItemFragment.this.lastTop) - top;
                } else {
                    delta = (((-height) * ((HomePageItemFragment.this.lastFirstVisibleItem - firstVisibleItem) - 1)) + HomePageItemFragment.this.lastTop) - (height + top);
                }
                if (firstVisibleItem == 1) {
                    if (delta < 0) {
                        if (top + delta >= HomePageItemFragment.this.headerHeight || HomePageItemFragment.this.headerTop == 0) {
                            HomePageItemFragment.this.headerTop = 0;
                        } else {
                            HomePageItemFragment.this.headerTop = top;
                        }
                    } else if (delta > 0) {
                        if (top + delta < 0) {
                            HomePageItemFragment.this.headerTop = top;
                        } else {
                            HomePageItemFragment.this.headerTop = 0;
                        }
                    }
                } else if (firstVisibleItem == 0) {
                    Log.d("zzb", "drag_down delta in OnHeaderSyncShowDown = " + HomePageItemFragment.this.drag_down_delta);
                    if (delta == 0 && top == 0) {
                        HomePageItemFragment.this.headerTop = HomePageItemFragment.this.drag_down_delta;
                    } else if (delta > 0) {
                        HomePageItemFragment.this.headerTop = HomePageItemFragment.this.drag_down_delta;
                    }
                } else {
                    Log.d("zzb", "lastFirstVisibleItem =" + HomePageItemFragment.this.lastFirstVisibleItem + "firstVisibleItem =" + firstVisibleItem);
                    if (delta < 0) {
                        HomePageItemFragment.this.headerTop = 0;
                    } else if (delta > 0) {
                        HomePageItemFragment.this.headerTop = -HomePageItemFragment.this.headerHeight;
                    }
                }
                if (HomePageItemFragment.this.lastheaderTop != HomePageItemFragment.this.headerTop) {
                    HomePageItemFragment.this.lastheaderTop = HomePageItemFragment.this.headerTop;
                    Log.d("zzb", "topMargin=" + HomePageItemFragment.this.headerTop);
                    HomePageItemFragment.this.realHeader.setTranslationY((float) HomePageItemFragment.this.headerTop);
                }
                HomePageItemFragment.this.lastFirstVisibleItem = firstVisibleItem;
                HomePageItemFragment.this.lastTop = top;
                HomePageItemFragment.this.lastHeight = firstChild.getHeight();
                HomePageItemFragment.this.recycleBitmapCaches(0, firstVisibleItem);
                HomePageItemFragment.this.recycleBitmapCaches((firstVisibleItem + visibleItemCount) - 2, totalItemCount);
                if (HomePageItemFragment.this.mHomepageListListener != null) {
                    HomePageItemFragment.this.mHomepageListListener.onScrollListDispatch(view, firstVisibleItem, visibleItemCount, totalItemCount, delta);
                }
            }
        }

        public void onScrollStateChanged(AbsListView view, int scrollState) {
        }
    }

    class C13693 implements OnGlobalLayoutListener {
        C13693() {
        }

        public void onGlobalLayout() {
            Log.d("zzb", "onGlobalLayout");
            if (HomePageItemFragment.this.waitingForExactHeaderHeight && HomePageItemFragment.this.dummyHeader.getHeight() > 0) {
                HomePageItemFragment.this.headerHeight = HomePageItemFragment.this.dummyHeader.getHeight();
                HomePageItemFragment.this.waitingForExactHeaderHeight = false;
                ViewGroup.LayoutParams params = HomePageItemFragment.this.dummyHeader.getLayoutParams();
                params.height = HomePageItemFragment.this.headerHeight;
                HomePageItemFragment.this.dummyHeader.setLayoutParams(params);
                HomePageItemFragment.this.mListView.getViewTreeObserver().removeGlobalOnLayoutListener(this);
            }
        }
    }

    interface HomepageListListener {
        void onScrollListDispatch(AbsListView absListView, int i, int i2, int i3, int i4);

        void onScrollStateChange(int i);
    }

    public static HomePageItemFragment newInstance(RunningItem bean, Map<String, String> wei, Map<String, Integer> step) {
        HomePageItemFragment fragment = new HomePageItemFragment();
        fragment.mDateBean = bean;
        fragment.weight = wei;
        fragment.steps = step;
        fragment.personal = Tools.getPersonalGoal();
        return fragment;
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mHandler = new C13671();
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.mroot = inflater.inflate(R.layout.home_page_item, container, false);
        this.realHeader = this.mroot.findViewById(R.id.fg_header);
        this.mListView = (XListView) this.mroot.findViewById(R.id.listview_activity);
        this.mListView.setOnItemClickListener(this);
        this.mListView.setXListViewListener(this);
        return this.mroot;
    }

    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        this.realHeader.measure(0, 0);
        this.headerHeight = this.realHeader.getMeasuredHeight();
        this.mListView.setOnScrollListener(new C13682());
        this.mListView.getViewTreeObserver().addOnGlobalLayoutListener(new C13693());
        this.mListView.SetXlistHeaderLisetener(this);
        this.dummyHeader = new View(getActivity());
        this.dummyHeader.setLayoutParams(new AbsListView.LayoutParams(-1, this.headerHeight));
        this.mListView.addHeaderView(this.dummyHeader);
        this.mListAdapter = new HomePageListItemAdapter(getActivity(), this.mTodayLists, this.mDateBean.getDate(), this.weightList, this.num);
        this.mListView.setAdapter(this.mListAdapter);
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

    public void SetListener(HomepageListListener mlistener) {
        this.mHomepageListListener = mlistener;
    }

    public void onResume() {
        super.onResume();
        Log.i("gchk", "item onResume");
        this.mListView.setTag("listview");
        int step = this.mDateBean.getSteps();
        int z = this.mDateBean.getmBmi() == null ? 0 : Integer.getInteger(this.mDateBean.getmBmi()).intValue();
        this.num = 0.0f;
        if (step != 0) {
            if (z != 0) {
                this.num = ((float) step) / ((float) z);
            } else {
                this.num = ((float) step) / ((float) this.personal.mGoalSteps);
            }
        }
        int pm = this.mDateBean.getPm25();
        this.mWeatherTv = (TextView) this.mroot.findViewById(R.id.weather_tv);
        if (pm < 0) {
            this.mWeatherTv.setText(R.string.day_pm_0);
        } else if (pm >= 0 && pm <= 50) {
            this.mWeatherTv.setText(R.string.day_pm_1);
        } else if (51 <= pm && pm <= 100) {
            this.mWeatherTv.setText(R.string.day_pm_2);
        } else if (101 <= pm && pm <= 150) {
            this.mWeatherTv.setText(R.string.day_pm_3);
        } else if (151 <= pm && pm <= 200) {
            this.mWeatherTv.setText(R.string.day_pm_4);
        } else if (201 <= pm && pm <= 300) {
            this.mWeatherTv.setText(R.string.day_pm_5);
        } else if (pm > 300) {
            this.mWeatherTv.setText(R.string.day_pm_6);
        }
        this.mPercentArc = (ImageView) this.mroot.findViewById(R.id.percent_arc_iv);
        updateNotificationRemoteViews(this.sContext, this.mPercentArc, this.num);
        this.mSummayTv = (TextView) this.mroot.findViewById(R.id.summay_tv);
        this.mSummayTv.setText(((int) ((this.num * 100.0f) / 1.0f)) + "");
        this.mSummayUnit = (TextView) this.mroot.findViewById(R.id.summay_unit);
        this.mSummayUnit.setText("%");
        this.mStepsTv = (TextView) this.mroot.findViewById(R.id.steps_tv);
        this.mStepsTv.setText(step + "");
        this.mKilometreTv = (TextView) this.mroot.findViewById(R.id.kilometre_tv);
        this.mKilometreTv.setText(this.mDateBean.getKilometer() + "");
        this.mCaloriesTv = (TextView) this.mroot.findViewById(R.id.calories_tv);
        this.mCaloriesTv.setText(this.mDateBean.getCalories() + "");
        this.mSummayTv.setTypeface(this.mNumberTP);
        this.mSummayUnit.setTypeface(this.mNumberTP);
        this.mStepsTv.setTypeface(this.mNumberTP);
        this.mCaloriesTv.setTypeface(this.mNumberTP);
        this.mDayLeft = (ImageView) this.mroot.findViewById(R.id.day_btn_left);
        this.mDayRight = (ImageView) this.mroot.findViewById(R.id.day_btn_right);
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
        Log.i("gchk", "item onDetach");
    }

    private void initListData() {
        List<Object> list;
        int index;
        int y;
        Log.i("lsj", "initListData");
        this.mTodayLists.clear();
        this.mTodayLists = new ArrayList();
        long start = System.currentTimeMillis();
        ContentResolver cr = getActivity().getContentResolver();
        String day = this.mDateBean.getDate();
        this.mTodayLists.add(0, this.mDateBean);
        Cursor c = cr.query(DataBaseContants.CONTENT_URI, new String[]{"_id", "date", DataBaseContants.TIME_DURATION, DataBaseContants.TIME_START, DataBaseContants.TIME_END, "steps", DataBaseContants.KILOMETER, DataBaseContants.CALORIES, DataBaseContants.CONF_WEIGHT, DataBaseContants.BMI, DataBaseContants.IMG_URI, DataBaseContants.EXPLAIN, DataBaseContants.SPORTS_TYPE, "type", DataBaseContants.HEART_RATE_TIME, DataBaseContants.HEART_RATE_COUNT}, "date  = ? AND statistics = ? ", new String[]{day, "0"}, "_id DESC");
        SortCursor sortCursor = new SortCursor(c, DataBaseContants.TIME_START);
        sortCursor.moveToFirst();
        int count = sortCursor.getCount();
        if (count > 0) {
            for (int i = 0; i < count; i++) {
                long id = c.getLong(c.getColumnIndex("_id"));
                String start_time = c.getString(sortCursor.getColumnIndex(DataBaseContants.TIME_START));
                int sport_type = c.getInt(sortCursor.getColumnIndex(DataBaseContants.SPORTS_TYPE));
                int type = c.getInt(sortCursor.getColumnIndex("type"));
                int size = this.mTodayLists.size();
                if (size > 0 && type == 2 && sport_type == 0 && ((RunningItem) this.mTodayLists.get(size - 1)).getmType() == 2 && ((RunningItem) this.mTodayLists.get(size - 1)).getSportsType() == 0 && ((RunningItem) this.mTodayLists.get(size - 1)).getStartTime().equals(start_time)) {
                    cr.delete(DataBaseContants.CONTENT_URI, "_id = ?", new String[]{String.valueOf(id)});
                    ContentValues values = new ContentValues();
                    values.put(DataBaseContants.DELETE_VALUE, Long.valueOf(id));
                    cr.insert(DataBaseContants.CONTENT_DELETE_URI, values);
                    break;
                }
                if (!(type == 4 || type == 3 || type == 7)) {
                    RunningItem item = new RunningItem();
                    item.setID(id);
                    item.setDate(c.getString(sortCursor.getColumnIndex("date")));
                    item.setDuration(c.getString(sortCursor.getColumnIndex(DataBaseContants.TIME_DURATION)));
                    item.setStartTime(start_time);
                    item.setEndTime(c.getString(sortCursor.getColumnIndex(DataBaseContants.TIME_END)));
                    item.setCalories(c.getInt(sortCursor.getColumnIndex(DataBaseContants.CALORIES)));
                    item.setSteps(c.getInt(sortCursor.getColumnIndex("steps")));
                    item.setKilometer(c.getInt(sortCursor.getColumnIndex(DataBaseContants.KILOMETER)));
                    item.setmWeight(c.getString(sortCursor.getColumnIndex(DataBaseContants.CONF_WEIGHT)));
                    item.setmBmi(c.getString(sortCursor.getColumnIndex(DataBaseContants.BMI)));
                    item.setmImgUri(c.getString(sortCursor.getColumnIndex(DataBaseContants.IMG_URI)));
                    item.setmExplain(c.getString(sortCursor.getColumnIndex(DataBaseContants.EXPLAIN)));
                    item.setSportsType(sport_type);
                    item.setmType(type);
                    item.setisStatistics(0);
                    this.mTodayLists.add(item);
                }
                sortCursor.moveToNext();
            }
        }
        c.close();
        sortCursor.close();
        if (this.mDateBean.getSteps() > 0 && this.steps != null && this.steps.size() > 0) {
            list = Arrays.asList(this.steps.keySet().toArray());
            index = list.indexOf(day);
            this.mSteps = new int[(index + 1)];
            for (y = 0; y <= index; y++) {
                this.mSteps[y] = ((Integer) this.steps.get(list.get(y))).intValue();
            }
            Arrays.sort(this.mSteps);
            if (this.mSteps[this.mSteps.length - 1] == this.mDateBean.getSteps()) {
                this.num = 10000.0f;
            }
        }
        this.weightList = new ArrayList();
        if (this.weight != null && this.weight.size() > 0) {
            list = Arrays.asList(this.weight.keySet().toArray());
            index = list.indexOf(day);
            y = index + -6 < 0 ? 0 : index - 6;
            while (y <= index) {
                this.weightList.add(Double.valueOf((String) this.weight.get(list.get(y))));
                y++;
            }
        }
        this.heartList = DataBaseUtil.getHeartRateByDate(getActivity(), this.mDateBean.getDate());
        if (this.heartList.size() > 0) {
            this.mTodayLists.add(this.heartList.get(0));
        }
        this.mListAdapter.UpdateDate(getActivity(), this.mTodayLists, day, this.weightList, this.num);
        this.mListAdapter.notifyDataSetChanged();
        Log.i("gchk", "initListData耗时" + (System.currentTimeMillis() - start));
    }

    private void recycleBitmapCaches(int fromPosition, int toPosition) {
        for (int del = fromPosition; del < toPosition - 2; del++) {
            String url = ((RunningItem) this.mTodayLists.get(del)).getmImgUri();
            if (!(url == null || url.equals(""))) {
                String path = Tools.getSDPath() + "/Running/.thumbnailnew/" + url.substring(url.lastIndexOf("/") + 1, url.lastIndexOf("."));
                if (gridviewBitmapCaches != null && gridviewBitmapCaches.size() > 0) {
                    Bitmap delBitmap = (Bitmap) gridviewBitmapCaches.get(path + ((RunningItem) this.mTodayLists.get(del)).getDate() + del);
                    if (delBitmap != null) {
                        gridviewBitmapCaches.remove(path + ((RunningItem) this.mTodayLists.get(del)).getDate() + del);
                        delBitmap.recycle();
                        System.gc();
                    }
                }
            }
        }
    }

    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
        int index = position - 2;
        if (index >= 0 && this.mListAdapter != null) {
            Intent intent;
            switch (this.mListAdapter.getItemViewType(index)) {
                case 1:
                    intent = new Intent();
                    intent.setClass(this.sContext, AddWeight.class);
                    intent.addFlags(268435456);
                    intent.putExtra("id", ((RunningItem) this.mTodayLists.get(index)).getID());
                    intent.putExtra("date", ((RunningItem) this.mTodayLists.get(index)).getDate());
                    intent.putExtra("weightCount", ((RunningItem) this.mTodayLists.get(index)).getmWeight());
                    intent.putExtra("startTime", ((RunningItem) this.mTodayLists.get(index)).getStartTime());
                    this.sContext.startActivity(intent);
                    return;
                case 3:
                    if (((RunningItem) this.mTodayLists.get(index)).getSportsType() != 0) {
                        intent = new Intent();
                        intent.setClass(this.sContext, AddSports.class);
                        intent.addFlags(268435456);
                        intent.putExtra("id", ((RunningItem) this.mTodayLists.get(index)).getID());
                        intent.putExtra("sportType", ((RunningItem) this.mTodayLists.get(index)).getSportsType());
                        intent.putExtra("sportStartTime", ((RunningItem) this.mTodayLists.get(index)).getStartTime());
                        intent.putExtra("date", ((RunningItem) this.mTodayLists.get(index)).getDate());
                        intent.putExtra("sportDuration", ((RunningItem) this.mTodayLists.get(index)).getDuration() + "");
                        intent.putExtra("wasteCalories", ((RunningItem) this.mTodayLists.get(index)).getCalories() + "");
                        this.sContext.startActivity(intent);
                        return;
                    }
                    return;
                case 4:
                    return;
                case 5:
                    String gpsId = ((RunningItem) this.mTodayLists.get(index)).getmExplain();
                    Log.i("lsj", "gpsId =" + gpsId);
                    intent = new Intent();
                    intent.setClass(this.sContext, GpsSportInfo.class);
                    intent.addFlags(268435456);
                    intent.putExtra("gpsid", gpsId);
                    this.sContext.startActivity(intent);
                    return;
                case 7:
                    intent = new Intent();
                    RunningItem item = (RunningItem) this.heartList.get(0);
                    intent.setClass(this.sContext, HeartRateDetailsActivity.class);
                    intent.addFlags(268435456);
                    intent.putExtra("heart_rate_id", item.getID());
                    intent.putExtra(DataBaseContants.HEART_RATE_COUNT, item.getHeart_rate_count());
                    intent.putExtra("heart_rate_date", item.getDate());
                    intent.putExtra(DataBaseContants.HEART_RATE_TIME, item.getStartTime());
                    this.sContext.startActivity(intent);
                    return;
                default:
                    return;
            }
        }
    }

    public void onRefresh() {
        if (MainService.getInstance() != null) {
            MainService.getInstance().syncWithDevice();
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

    public void reSetHeadMargin() {
        if (this.headerTop != 0) {
            this.headerTop = 0;
            this.lastheaderTop = 0;
            this.realHeader.setTranslationY((float) this.headerTop);
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
        Bitmap localBitmap = Bitmap.createBitmap(Util.dip2pixel(paramContext, 47.0f), Util.dip2pixel(paramContext, 47.0f), Config.ARGB_8888);
        Canvas localCanvas = new Canvas(localBitmap);
        Paint localPaint = new Paint();
        localPaint.setAntiAlias(true);
        localPaint.setColor(-1);
        localPaint.setStyle(Style.STROKE);
        localPaint.setStrokeWidth((float) Util.dip2pixel(paramContext, 1.0f));
        localCanvas.drawArc(new RectF((float) Util.dip2pixel(paramContext, 2.0f), (float) Util.dip2pixel(paramContext, 2.0f), (float) Util.dip2pixel(paramContext, 44.0f), (float) Util.dip2pixel(paramContext, 44.0f)), -90.0f, (float) ((int) (360.0d * d)), false, localPaint);
        mPerArc.setImageBitmap(localBitmap);
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
