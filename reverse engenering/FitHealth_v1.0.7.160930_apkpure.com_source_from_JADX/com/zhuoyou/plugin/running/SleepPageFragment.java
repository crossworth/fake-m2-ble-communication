package com.zhuoyou.plugin.running;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.ContentObserver;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import com.amap.api.services.core.AMapException;
import com.fithealth.running.R;
import com.umeng.analytics.MobclickAgent;
import com.umeng.socialize.common.SocializeConstants;
import com.zhuoyou.plugin.bluetooth.data.MessageObj;
import com.zhuoyou.plugin.database.DBOpenHelper;
import com.zhuoyou.plugin.database.DataBaseContants;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

@SuppressLint({"SdCardPath"})
public class SleepPageFragment extends Fragment implements OnPageChangeListener {
    private static final int NOTIFY_DATASET_CHANGED = 5;
    private static final String TAG = "SleepPageFragment";
    public static Handler mHandler;
    public static SleepPageFragment mInstance;
    OnClickListener btnSleep = new C14153();
    private List<String> date_list = new ArrayList();
    public String firstDay = "";
    private ContentObserver mContentObserver = new ContentObserver(this.mUpdateHandler) {
        public void onChange(boolean selfChange) {
            Log.i("gchk", "database changed!");
            if (SleepPageFragment.this.mUpdateHandler != null) {
                Log.i("gchk", "send to target");
                SleepPageFragment.this.mUpdateHandler.removeMessages(AMapException.CODE_AMAP_INVALID_USER_SCODE);
                Message msg = new Message();
                msg.what = AMapException.CODE_AMAP_INVALID_USER_SCODE;
                SleepPageFragment.this.mUpdateHandler.sendMessageDelayed(msg, 50);
            }
        }
    };
    private Context mCtx = RunningApp.getInstance();
    private DateChangeReceiver mDateChangeReceiver = new DateChangeReceiver();
    private View mRootView;
    private Button mSleep;
    private SleepPageAdapter mSleepAdapter = null;
    private UpdateHandler mUpdateHandler = new UpdateHandler(this);
    private ViewPager mViewPager;
    private TextView title_bar_text;

    class C14142 extends Handler {
        C14142() {
        }

        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 5:
                    SleepPageFragment.this.mSleepAdapter.notifyDataSetChanged(SleepPageFragment.this.date_list);
                    return;
                default:
                    return;
            }
        }
    }

    class C14153 implements OnClickListener {
        C14153() {
        }

        public void onClick(View v) {
            Main.vFragment = true;
            if (Main.mHandler != null) {
                Main.mHandler.sendEmptyMessage(6);
            }
        }
    }

    public class DateChangeReceiver extends BroadcastReceiver {
        private static final String ACTION_DATE_CHANGED = "android.intent.action.DATE_CHANGED";

        public void onReceive(Context arg0, Intent arg1) {
            if (ACTION_DATE_CHANGED.equals(arg1.getAction())) {
                Log.d("gchk", "---DATE_CHANGED!---");
                SleepPageFragment.this.mUpdateHandler.removeMessages(AMapException.CODE_AMAP_INVALID_USER_SCODE);
                Message msg = new Message();
                msg.what = AMapException.CODE_AMAP_INVALID_USER_SCODE;
                SleepPageFragment.this.mUpdateHandler.sendMessageDelayed(msg, 50);
            }
        }
    }

    public static class UpdateHandler extends Handler {
        WeakReference<SleepPageFragment> mMyFragment;

        public UpdateHandler(SleepPageFragment f) {
            this.mMyFragment = new WeakReference(f);
        }

        public void handleMessage(Message msg) {
            Log.i("gchk", "UpdateHandler receiver msg");
            if (this.mMyFragment != null) {
                SleepPageFragment home = (SleepPageFragment) this.mMyFragment.get();
                if (home != null && home.mSleepAdapter != null) {
                    Log.i("gchk", MessageObj.ACTION_UPDATE);
                    home.initData();
                }
            }
        }
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d("gchk", "homepagefragment onCreateView");
        this.mRootView = inflater.inflate(R.layout.sleep_page, container, false);
        this.mRootView.setBackgroundColor(Color.parseColor("#fcfcfc"));
        mInstance = this;
        initView();
        this.mSleepAdapter = new SleepPageAdapter(getChildFragmentManager(), this.date_list);
        this.mViewPager.setAdapter(this.mSleepAdapter);
        return this.mRootView;
    }

    public void onActivityCreated(Bundle paramBundle) {
        super.onActivityCreated(paramBundle);
        mHandler = new C14142();
    }

    private String formatRemoteDate(String old_date) {
        return (((old_date.substring(0, 4) + SocializeConstants.OP_DIVIDER_MINUS) + old_date.substring(4, 6)) + SocializeConstants.OP_DIVIDER_MINUS) + old_date.substring(6, 8);
    }

    private void initData() {
        this.date_list.clear();
        this.firstDay = "";
        long start = System.currentTimeMillis();
        String today = Tools.getDate(0);
        String enter_day = today;
        SQLiteDatabase sqlDB = new DBOpenHelper(this.mCtx).getWritableDatabase();
        Cursor mCursor = sqlDB.query(DataBaseContants.TABLE_SLEEP, new String[]{"_id", "start_time"}, null, null, null, null, "start_time ASC");
        mCursor.moveToFirst();
        if (mCursor.getCount() > 0) {
            this.firstDay = formatRemoteDate(mCursor.getString(mCursor.getColumnIndex("start_time")));
        }
        if (this.firstDay.equals("")) {
            mCursor = sqlDB.query(DataBaseContants.TABLE_SLEEP_2, new String[]{"_id", "date"}, null, null, null, null, "date ASC");
            mCursor.moveToFirst();
            if (mCursor.getCount() > 0) {
                this.firstDay = mCursor.getString(mCursor.getColumnIndex("date"));
            }
        }
        sqlDB.close();
        mCursor.close();
        if (!this.firstDay.equals("")) {
            enter_day = this.firstDay;
        }
        int count3 = Tools.getDayCount(enter_day, today, "yyyy-MM-dd");
        for (int i = 0; i < count3; i++) {
            this.date_list.add(Tools.getDate(enter_day, 0 - i));
        }
        this.mSleepAdapter.notifyDataSetChanged(this.date_list);
        this.mViewPager.setCurrentItem(this.date_list.size() - 1, false);
        Log.i("gchk", "getAllDate耗时" + (System.currentTimeMillis() - start));
    }

    public void onResume() {
        super.onResume();
        Log.i("gchk", "home onResume");
        initData();
        this.mCtx.getContentResolver().registerContentObserver(DataBaseContants.CONTENT_URI, true, this.mContentObserver);
        this.mCtx.registerReceiver(this.mDateChangeReceiver, new IntentFilter("android.intent.action.DATE_CHANGED"));
        mHandler.sendEmptyMessageDelayed(5, 50);
        int index = Tools.getSleepCurrPageIndex();
        if (index == 0) {
            this.mViewPager.setCurrentItem(this.date_list.size() - 1, false);
            if (this.date_list.size() == 1) {
                setTitle(Tools.getDate(0));
            }
        } else {
            this.mViewPager.setCurrentItem(index, false);
        }
        MobclickAgent.onPageStart(TAG);
    }

    public void onPause() {
        super.onPause();
        Log.i("gchk", "home onPause");
        this.mCtx.getContentResolver().unregisterContentObserver(this.mContentObserver);
        this.mCtx.unregisterReceiver(this.mDateChangeReceiver);
        Tools.setSleepCurrPageIndex(this.mViewPager.getCurrentItem());
        MobclickAgent.onPageEnd(TAG);
    }

    public void onDestroy() {
        super.onDestroy();
    }

    public void onPageScrollStateChanged(int arg0) {
    }

    public void onPageScrolled(int arg0, float arg1, int arg2) {
    }

    public void onPageSelected(int arg0) {
        setTitle((String) this.date_list.get(arg0));
    }

    private void setTitle(String title) {
        if (title.equals(Tools.getDate(0))) {
            this.title_bar_text.setText(R.string.today);
        } else {
            this.title_bar_text.setText(title.substring(5));
        }
    }

    private void initView() {
        this.mViewPager = (ViewPager) this.mRootView.findViewById(R.id.main_viewpager);
        this.mViewPager.setOnPageChangeListener(this);
        this.title_bar_text = (TextView) this.mRootView.findViewById(R.id.title_bar_text);
        this.title_bar_text.setText(R.string.today);
        this.mSleep = (Button) this.mRootView.findViewById(R.id.sleep_btn);
        this.mSleep.setOnClickListener(this.btnSleep);
    }
}
