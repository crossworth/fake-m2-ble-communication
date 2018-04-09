package com.zhuoyou.plugin.running;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.ContentObserver;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.os.FileObserver;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.amap.api.services.core.AMapException;
import com.fithealth.running.R;
import com.mcube.lib.ped.PedometerService;
import com.umeng.analytics.MobclickAgent;
import com.zhuoyou.plugin.ble.BleManagerService;
import com.zhuoyou.plugin.bluetooth.data.MessageObj;
import com.zhuoyou.plugin.bluetooth.data.Util;
import com.zhuoyou.plugin.bluetooth.service.BluetoothService;
import com.zhuoyou.plugin.cloud.CloudSync;
import com.zhuoyou.plugin.database.DataBaseContants;
import com.zhuoyou.plugin.gps.ilistener.IStepListener;
import com.zhuoyou.plugin.weather.WeatherTools;
import java.lang.ref.WeakReference;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import p031u.aly.C1502d;

@SuppressLint({"SdCardPath"})
public class HomePageFragment extends Fragment implements OnPageChangeListener {
    private static final int NOTIFY_DATASET_CHANGED = 5;
    private static int PHONE_STEP = 0;
    public static final int SYNC_DEVICE_FAILED = 3;
    public static final int SYNC_DEVICE_PROGRESS = 4;
    public static final int SYNC_DEVICE_START = 1;
    public static final int SYNC_DEVICE_SUCCESSED = 2;
    private static final String TAG = "HomePageFragment";
    public static Handler mHandler;
    public static HomePageFragment mInstance;
    public static List<RunningItem> mRunningDays;
    public static Map<String, Integer> steps;
    private static Calendar today_date;
    public static Map<String, String> weight;
    OnClickListener btnSleep = new C13635();
    OnClickListener calOnClick = new C13646();
    private CalendarView calPopupWindow;
    private RelativeLayout cal_lay;
    private int caloriesAddSport;
    private String currDate = null;
    private List<String> date_list = new ArrayList();
    public String firstDay = "";
    private RunningItem item;
    private ContentObserver mContentObserver = new ContentObserver(this.mUpdateHandler) {
        public void onChange(boolean selfChange) {
            Log.i("gchk", "database changed!");
            if (HomePageFragment.this.mUpdateHandler != null) {
                Log.i("gchk", "send to target");
                HomePageFragment.this.mUpdateHandler.removeMessages(AMapException.CODE_AMAP_INVALID_USER_SCODE);
                Message msg = new Message();
                msg.what = AMapException.CODE_AMAP_INVALID_USER_SCODE;
                if (Tools.dbStateChange) {
                    HomePageFragment.this.mUpdateHandler.sendMessageDelayed(msg, 1000);
                } else {
                    HomePageFragment.this.mUpdateHandler.sendMessageDelayed(msg, 50);
                }
            }
        }
    };
    private Context mCtx = RunningApp.getInstance();
    private DateChangeReceiver mDateChangeReceiver = new DateChangeReceiver();
    private View mFooter;
    private TextView mHeaderTimeView;
    private TextView mHeaderTimeViewTitle;
    private TextView mHintTextView;
    private HomePageAdapter mHomeAdapter = null;
    private RelativeLayout mListViewRel;
    private TextView mProgress;
    private View mRootView;
    private Button mSleep;
    private UpdateHandler mUpdateHandler = new UpdateHandler(this);
    private ViewPager mViewPager;
    private WeatherObserver mWeatherObserver = new WeatherObserver(C1502d.f3811a + this.mCtx.getPackageName() + "/shared_prefs/", 2);
    private TextView title_bar_text;
    private ImageView title_cloud;
    private ImageView title_share;

    class C13602 extends Handler {
        C13602() {
        }

        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    HomePageFragment.this.refreshView(Boolean.valueOf(true), msg.arg1);
                    return;
                case 2:
                    int type = msg.arg1;
                    HomePageFragment.this.refreshView(Boolean.valueOf(false), type);
                    HomePageFragment.this.setLastUpdateTime(type);
                    return;
                case 3:
                    HomePageFragment.this.refreshView(Boolean.valueOf(false), msg.arg1);
                    return;
                case 4:
                    String pro = msg.obj;
                    if (HomePageFragment.this.mProgress != null) {
                        HomePageFragment.this.mProgress.setText(pro);
                        return;
                    }
                    return;
                case 5:
                    Log.i(PedometerService.TAG, "NOTIFY_DATASET_CHANGED");
                    HomePageFragment.this.mHomeAdapter.notifyDataSetChanged(HomePageFragment.mRunningDays, HomePageFragment.weight, HomePageFragment.steps);
                    return;
                default:
                    return;
            }
        }
    }

    class C13613 implements OnClickListener {
        C13613() {
        }

        public void onClick(View v) {
            HomePageFragment.this.toShareActivity();
        }
    }

    class C13624 implements OnClickListener {
        C13624() {
        }

        public void onClick(View v) {
            CloudSync.prepareSync();
        }
    }

    class C13635 implements OnClickListener {
        C13635() {
        }

        public void onClick(View v) {
            Main.vFragment = false;
            if (Main.mHandler != null) {
                Main.mHandler.sendEmptyMessage(6);
            }
        }
    }

    class C13646 implements OnClickListener {
        C13646() {
        }

        public void onClick(View v) {
            HomePageFragment.this.initmPopupWindowView();
            HomePageFragment.this.calPopupWindow.showAsDropDown(v, 0, 0);
        }
    }

    class C13657 implements OnDismissListener {
        C13657() {
        }

        public void onDismiss() {
            HomePageFragment.this.calPopupWindow = null;
        }
    }

    public class DateChangeReceiver extends BroadcastReceiver {
        private static final String ACTION_DATE_CHANGED = "android.intent.action.DATE_CHANGED";

        public void onReceive(Context arg0, Intent arg1) {
            if (ACTION_DATE_CHANGED.equals(arg1.getAction())) {
                Log.d("gchk", "---DATE_CHANGED!---");
                HomePageFragment.this.mUpdateHandler.removeMessages(AMapException.CODE_AMAP_INVALID_USER_SCODE);
                Message msg = new Message();
                msg.what = AMapException.CODE_AMAP_INVALID_USER_SCODE;
                HomePageFragment.this.mUpdateHandler.sendMessageDelayed(msg, 50);
                if (Tools.getPm25(Tools.getDate(0)) == 0) {
                    WeatherTools.newInstance().getCurrWeather();
                }
                BleManagerService.getInstance().setTimeToRemote(0);
                Message message = new Message();
                message.what = 1;
                message.arg1 = 1;
                HomePageFragment.mHandler.sendMessageDelayed(message, 500);
            }
        }
    }

    public static class UpdateHandler extends Handler {
        WeakReference<HomePageFragment> mMyFragment;

        public UpdateHandler(HomePageFragment f) {
            this.mMyFragment = new WeakReference(f);
        }

        public void handleMessage(Message msg) {
            Log.i("gchk", "UpdateHandler receiver msg");
            if (this.mMyFragment != null) {
                HomePageFragment home = (HomePageFragment) this.mMyFragment.get();
                if (home != null && home.mHomeAdapter != null) {
                    Log.i("gchk", MessageObj.ACTION_UPDATE);
                    home.initData();
                }
            }
        }
    }

    public class WeatherObserver extends FileObserver {

        class C13661 implements Runnable {
            C13661() {
            }

            public void run() {
                HomePageFragment.this.mHomeAdapter.notifyDataSetChanged(HomePageFragment.mRunningDays, HomePageFragment.weight, HomePageFragment.steps);
            }
        }

        public WeatherObserver(String path, int mask) {
            super(path, mask);
            Log.i("gchk", "WeatherObserver = " + path);
        }

        public void onEvent(int event, String path) {
            switch (event) {
                case 2:
                    if (path.startsWith("weather")) {
                        Log.i("gchk", "MODIFY = " + path);
                        if (HomePageFragment.mRunningDays != null && HomePageFragment.mRunningDays.size() > 0) {
                            RunningItem item = (RunningItem) HomePageFragment.mRunningDays.get(HomePageFragment.mRunningDays.size() - 1);
                            item.setPm25(Tools.getPm25(item.getDate()));
                            if (HomePageFragment.this.mHomeAdapter != null) {
                                HomePageFragment.this.getActivity().runOnUiThread(new C13661());
                                return;
                            }
                            return;
                        }
                        return;
                    }
                    return;
                default:
                    return;
            }
        }
    }

    class StepObserver implements IStepListener {
        StepObserver() {
        }

        public void onStepCount(int stepCount) {
        }

        public void onStateChanged(int newState) {
        }

        public void onHadRunStep(int hadRunStep) {
        }
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d("gchk", "homepagefragment onCreateView");
        today_date = Calendar.getInstance();
        this.mRootView = inflater.inflate(R.layout.home_page, container, false);
        this.mRootView.setBackgroundColor(Color.parseColor("#fcfcfc"));
        mInstance = this;
        this.date_list = Tools.getDateFromDb(this.mCtx);
        initView();
        this.mHomeAdapter = new HomePageAdapter(getChildFragmentManager(), mRunningDays, weight, steps);
        this.mViewPager.setAdapter(this.mHomeAdapter);
        return this.mRootView;
    }

    public void onActivityCreated(Bundle paramBundle) {
        super.onActivityCreated(paramBundle);
        mHandler = new C13602();
    }

    void initAddSport(String day) {
        this.caloriesAddSport = 0;
        int calories = 0;
        Cursor cAddSport = this.mCtx.getContentResolver().query(DataBaseContants.CONTENT_URI, new String[]{"_id", "date", DataBaseContants.CALORIES, DataBaseContants.CONF_WEIGHT, DataBaseContants.SPORTS_TYPE, "type"}, "date  = ? AND statistics = ?", new String[]{day, "0"}, null);
        cAddSport.moveToFirst();
        if (cAddSport.getCount() > 0) {
            for (int y = 0; y < cAddSport.getCount(); y++) {
                if (cAddSport.getInt(cAddSport.getColumnIndex("type")) == 2) {
                    if (cAddSport.getInt(cAddSport.getColumnIndex(DataBaseContants.SPORTS_TYPE)) != 0) {
                        calories += cAddSport.getInt(cAddSport.getColumnIndex(DataBaseContants.CALORIES));
                        this.caloriesAddSport = calories;
                    }
                } else if (cAddSport.getInt(cAddSport.getColumnIndex("type")) == 1 && weight.get(cAddSport.getString(cAddSport.getColumnIndex("date"))) == null) {
                    weight.put(cAddSport.getString(cAddSport.getColumnIndex("date")), cAddSport.getString(cAddSport.getColumnIndex(DataBaseContants.CONF_WEIGHT)));
                }
                cAddSport.moveToNext();
            }
        }
        cAddSport.close();
    }

    private void readPhoneStep(String day) {
        Cursor c = this.mCtx.getContentResolver().query(DataBaseContants.CONTENT_URI, new String[]{"_id", "steps"}, "date  = ? AND data_from  = ? AND statistics = ?", new String[]{day, "phone", "2"}, null);
        c.moveToFirst();
        if (c.getCount() > 0) {
            for (int y = 0; y < c.getCount(); y++) {
                PHONE_STEP = c.getInt(c.getColumnIndex("steps"));
                c.moveToNext();
            }
        }
        c.close();
    }

    private void initData() {
        if (mRunningDays == null) {
            mRunningDays = new ArrayList();
        }
        mRunningDays.clear();
        Tools.loginStateChange = false;
        this.firstDay = "";
        weight = new LinkedHashMap();
        steps = new LinkedHashMap();
        long start = System.currentTimeMillis();
        String today = Tools.getDate(0);
        String str = today;
        this.date_list = Tools.getDateFromDb(this.mCtx);
        if (this.date_list != null && this.date_list.size() > 0) {
            str = (String) this.date_list.get(0);
        }
        if (str.equals(today)) {
            this.date_list = Tools.getDateFromDb(this.mCtx);
            if (this.date_list != null && this.date_list.size() > 0) {
                str = (String) this.date_list.get(0);
            }
        }
        int count = Tools.getDayCount(str, today, "yyyy-MM-dd");
        ContentResolver cr = this.mCtx.getContentResolver();
        for (int i = 0; i < count; i++) {
            RunningItem runningdate = new RunningItem();
            String day = Tools.getDate(str, 0 - i);
            if (this.date_list == null || this.date_list.size() <= 0 || this.date_list.indexOf(day) == -1) {
                runningdate.setDate(day);
                runningdate.setCalories(0);
                runningdate.setSteps(0);
                runningdate.setKilometer(0);
                runningdate.setStartTime("");
                runningdate.setEndTime("");
                runningdate.setDuration("");
                runningdate.setPm25(Tools.getPm25(day));
            } else {
                initAddSport(day);
                Cursor c = cr.query(DataBaseContants.CONTENT_URI, new String[]{"_id", "date", "steps", DataBaseContants.KILOMETER, DataBaseContants.CALORIES}, "date  = ? AND statistics = ? ", new String[]{day, "1"}, "steps DESC");
                c.moveToFirst();
                if (c.getCount() > 0) {
                    if (this.firstDay.equals("")) {
                        this.firstDay = day;
                    }
                    for (int j = 0; j < c.getCount(); j++) {
                        long id = c.getLong(c.getColumnIndex("_id"));
                        if (j == 0) {
                            runningdate.setDate(c.getString(c.getColumnIndex("date")));
                            runningdate.setCalories(c.getInt(c.getColumnIndex(DataBaseContants.CALORIES)) + this.caloriesAddSport);
                            runningdate.setSteps(c.getInt(c.getColumnIndex("steps")));
                            runningdate.setKilometer(c.getInt(c.getColumnIndex(DataBaseContants.KILOMETER)));
                            runningdate.setStartTime("");
                            runningdate.setEndTime("");
                            runningdate.setDuration("");
                            runningdate.setPm25(Tools.getPm25(day));
                            steps.put(c.getString(c.getColumnIndex("date")), Integer.valueOf(c.getInt(c.getColumnIndex("steps"))));
                        } else {
                            cr.delete(DataBaseContants.CONTENT_URI, "_id = ?", new String[]{String.valueOf(id)});
                            ContentValues values = new ContentValues();
                            values.put(DataBaseContants.DELETE_VALUE, Long.valueOf(id));
                            cr.insert(DataBaseContants.CONTENT_DELETE_URI, values);
                        }
                        c.moveToNext();
                    }
                } else {
                    runningdate.setDate(day);
                    runningdate.setCalories(this.caloriesAddSport + 0);
                    runningdate.setSteps(0);
                    runningdate.setKilometer(0);
                    runningdate.setStartTime("");
                    runningdate.setEndTime("");
                    runningdate.setDuration("");
                    runningdate.setPm25(Tools.getPm25(day));
                }
                c.close();
            }
            mRunningDays.add(runningdate);
        }
        this.mHomeAdapter.notifyDataSetChanged(mRunningDays, weight, steps);
        this.mViewPager.setCurrentItem(mRunningDays.size() - 1, false);
        Log.i("gchk", "getAllDate耗时" + (System.currentTimeMillis() - start));
        BluetoothService service = BluetoothService.getInstance();
        if (service != null) {
            int targetStep = Tools.getPersonalGoal().getStep();
            service.updateConnectionStatus(false, ((RunningItem) mRunningDays.get(mRunningDays.size() - 1)).getSteps(), ((RunningItem) mRunningDays.get(mRunningDays.size() - 1)).getCalories(), targetStep);
        }
    }

    public void onResume() {
        super.onResume();
        Log.i("gchk", "home onResume");
        Calendar check_date = Calendar.getInstance();
        boolean check;
        if (today_date.get(1) == check_date.get(1) && today_date.get(2) == check_date.get(2) && today_date.get(5) == check_date.get(5)) {
            check = true;
        } else {
            check = false;
        }
        if (mRunningDays == null || mRunningDays.size() == 0 || Tools.loginStateChange) {
            today_date = Calendar.getInstance();
            initData();
        } else if (!(mRunningDays == null || weight == null || steps == null)) {
            this.mHomeAdapter.notifyDataSetChanged(mRunningDays, weight, steps);
        }
        if (Boolean.valueOf(Welcome.isentry).booleanValue()) {
            if (mRunningDays.size() > 0) {
                this.mViewPager.setCurrentItem(mRunningDays.size() - 1, false);
            }
            if (mRunningDays.size() == 1) {
                setTitle(Tools.getDate(0));
            }
            Util.autoConnect(this.mCtx);
            Welcome.isentry = false;
        } else {
            int index = Tools.getCurrPageIndex();
            if (index == 0) {
                this.mViewPager.setCurrentItem(mRunningDays.size() - 1, false);
                if (mRunningDays.size() == 1) {
                    setTitle(Tools.getDate(0));
                }
            } else {
                this.mViewPager.setCurrentItem(index, false);
            }
        }
        this.mWeatherObserver.startWatching();
        this.mCtx.getContentResolver().registerContentObserver(DataBaseContants.CONTENT_URI, true, this.mContentObserver);
        this.mCtx.registerReceiver(this.mDateChangeReceiver, new IntentFilter("android.intent.action.DATE_CHANGED"));
        mHandler.sendEmptyMessageDelayed(5, 50);
        this.mUpdateHandler.sendEmptyMessage(AMapException.CODE_AMAP_INVALID_USER_SCODE);
        MobclickAgent.onPageStart(TAG);
    }

    public void onPause() {
        super.onPause();
        Log.i("gchk", "home onPause");
        this.mWeatherObserver.stopWatching();
        this.mCtx.getContentResolver().unregisterContentObserver(this.mContentObserver);
        this.mCtx.unregisterReceiver(this.mDateChangeReceiver);
        Tools.setCurrPageIndex(this.mViewPager.getCurrentItem());
        if (this.calPopupWindow != null && this.calPopupWindow.isShowing()) {
            this.calPopupWindow.dismiss();
        }
        MobclickAgent.onPageEnd(TAG);
    }

    public void onDestroy() {
        super.onDestroy();
    }

    public void onPageScrollStateChanged(int arg0) {
    }

    public void onPageScrolled(int arg0, float arg1, int arg2) {
        if (!(this.mFooter == null || this.mFooter.getVisibility() == 0)) {
            this.mFooter.setVisibility(0);
        }
        HomePageItemFragment cur_fragment = (HomePageItemFragment) this.mHomeAdapter.registeredFragments.get(this.mViewPager.getCurrentItem());
        if (cur_fragment != null) {
            cur_fragment.reSetHeadMargin();
        }
    }

    public void onPageSelected(int arg0) {
        setTitle(((RunningItem) mRunningDays.get(arg0)).getDate());
    }

    private void setTitle(String title) {
        this.currDate = title;
        if (title.equals(Tools.getDate(0))) {
            this.title_bar_text.setText(R.string.today);
        } else {
            this.title_bar_text.setText(title.substring(5));
        }
    }

    public List<String> getDateList() {
        return this.date_list;
    }

    public void onTapLeft() {
        int index = this.mViewPager.getCurrentItem();
        if (index == 0) {
            Toast.makeText(this.mCtx, R.string.left_error_tip, 0).show();
        } else {
            this.mViewPager.setCurrentItem(index - 1, true);
        }
    }

    public void onTapRight() {
        int index = this.mViewPager.getCurrentItem();
        if (index == mRunningDays.size() - 1) {
            Toast.makeText(this.mCtx, R.string.right_error_tip, 0).show();
        } else {
            this.mViewPager.setCurrentItem(index + 1, true);
        }
    }

    public void onViewPagerIndex(int index) {
        this.mViewPager.setCurrentItem(index, true);
    }

    public void onViewPagerCurrent() {
        this.mViewPager.setCurrentItem(mRunningDays.size() - 1, true);
    }

    public RunningItem getCurrPageData() {
        if (mRunningDays != null) {
            return (RunningItem) mRunningDays.get(this.mViewPager.getCurrentItem());
        }
        return null;
    }

    private void toShareActivity() {
        RunningItem data = getCurrPageData();
        Intent intent = new Intent(getActivity(), ShareActivity.class);
        intent.putExtra("steps", data.getSteps());
        intent.putExtra("cals", data.getCalories());
        intent.putExtra("km", data.getKilometer());
        intent.putExtra("date", data.getDate());
        startActivity(intent);
        getActivity().overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
    }

    private void initView() {
        this.mViewPager = (ViewPager) this.mRootView.findViewById(R.id.main_viewpager);
        this.mViewPager.setOnPageChangeListener(this);
        this.title_bar_text = (TextView) this.mRootView.findViewById(R.id.title_bar_text);
        this.title_bar_text.setText(R.string.today);
        this.cal_lay = (RelativeLayout) this.mRootView.findViewById(R.id.cal_lay);
        this.cal_lay.setOnClickListener(this.calOnClick);
        this.title_share = (ImageView) this.mRootView.findViewById(R.id.title_share);
        this.title_cloud = (ImageView) this.mRootView.findViewById(R.id.title_cloud);
        this.title_share.setOnClickListener(new C13613());
        this.title_cloud.setOnClickListener(new C13624());
        this.mHintTextView = (TextView) this.mRootView.findViewById(R.id.xlistview_header_hint_textview);
        this.mHeaderTimeViewTitle = (TextView) this.mRootView.findViewById(R.id.xlistview_header_time_text);
        this.mHeaderTimeView = (TextView) this.mRootView.findViewById(R.id.xlistview_header_time);
        this.mListViewRel = (RelativeLayout) this.mRootView.findViewById(R.id.xlistview_header_content);
        this.mProgress = (TextView) this.mRootView.findViewById(R.id.progress);
        this.mProgress.setVisibility(8);
        this.mFooter = getActivity().findViewById(R.id.foot_id);
        this.mSleep = (Button) this.mRootView.findViewById(R.id.sleep_btn);
        this.mSleep.setOnClickListener(this.btnSleep);
    }

    public void initmPopupWindowView() {
        this.calPopupWindow = new CalendarView(getActivity(), this.currDate);
        this.calPopupWindow.setAnimationStyle(R.style.AnimationFade);
        this.calPopupWindow.setOnDismissListener(new C13657());
    }

    public void refreshView(Boolean state, int type) {
        if (state.booleanValue()) {
            SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this.mCtx);
            String label = "";
            if (type == 1) {
                label = sp.getString("sync_device_time", "");
                this.mHintTextView.setText(R.string.xlistview_header_hint_loading);
            } else if (type == 2) {
                this.mProgress.setVisibility(0);
                this.mProgress.setText("0%");
                label = sp.getString("sync_cloud_time", "");
                this.mHintTextView.setText(R.string.progressbar_dialog_sync);
            }
            if (TextUtils.isEmpty(label)) {
                this.mHeaderTimeViewTitle.setVisibility(8);
                this.mHeaderTimeView.setVisibility(8);
            } else {
                this.mHeaderTimeViewTitle.setVisibility(0);
                this.mHeaderTimeView.setVisibility(0);
                this.mHeaderTimeView.setText(label);
            }
            this.mListViewRel.setVisibility(0);
            return;
        }
        this.mListViewRel.setVisibility(8);
    }

    private void setLastUpdateTime(int type) {
        String text = formatDateTime(System.currentTimeMillis());
        Editor editor = PreferenceManager.getDefaultSharedPreferences(this.mCtx).edit();
        if (type == 1) {
            editor.putString("sync_device_time", text);
        } else if (type == 2) {
            editor.putString("sync_cloud_time", text);
        }
        editor.commit();
    }

    private String formatDateTime(long time) {
        SimpleDateFormat mDateFormat = new SimpleDateFormat("MM-dd HH:mm");
        if (0 == time) {
            return "";
        }
        return mDateFormat.format(new Date(time));
    }
}
