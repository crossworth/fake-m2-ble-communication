package com.zhuoyou.plugin.add;

import android.app.Activity;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.fithealth.running.R;
import com.pixart.alg.PXIALGMOTION;
import com.zhuoyou.plugin.ble.BleManagerService;
import com.zhuoyou.plugin.bluetooth.data.Util;
import com.zhuoyou.plugin.bluetooth.service.BluetoothService;
import com.zhuoyou.plugin.database.DataBaseContants;
import com.zhuoyou.plugin.database.DataBaseUtil;
import com.zhuoyou.plugin.running.RunningApp;
import com.zhuoyou.plugin.running.RunningItem;
import com.zhuoyou.plugin.running.Tools;
import com.zhuoyou.plugin.view.HeartRateAnimationView;
import com.zhuoyou.plugin.view.ProgressButton;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class AddHeartRate extends Activity implements OnClickListener {
    public static final int HEART_RATE_LIST_NUM = 20;
    public static final int HEART_RATE_START = 291;
    public static final int HEART_RATE_STOP = 292;
    public static final int HEART_RATE_STOP_HAND = 293;
    private long clickStime = 0;
    private List<RunningItem> heartrateList = new ArrayList();
    private HeartRateAnimationView heartrateView;
    private TextView heartrate_showcount;
    private int hrData;
    private ArrayList<Integer> hrDataList = new ArrayList();
    private boolean isShare = false;
    private boolean isStart = false;
    private boolean isknow = false;
    private RelativeLayout linearlayout_showtime;
    private ImageView mBack;
    private BroadcastReceiver mGetDataReceiver = new C11032();
    private MyAdapter mMyAdapter;
    Handler mhandler = new C11043();
    private ListView my_listview;
    private int percent = 0;
    private ProgressButton progress_button;
    private RelativeLayout relativeLayout;
    private TextView reminder;
    private SharedPreferences sp;
    private TextView test_result;
    private Typeface tf;
    private ImageView title_share;

    class C11021 implements OnItemClickListener {
        C11021() {
        }

        public void onItemClick(AdapterView<?> adapterView, View view, int position, long arg3) {
            AddHeartRate.this.isStart = true;
            Intent intent = new Intent();
            RunningItem item = (RunningItem) AddHeartRate.this.heartrateList.get(position);
            intent.putExtra("heart_rate_id", item.getID());
            intent.putExtra(DataBaseContants.HEART_RATE_COUNT, item.getHeart_rate_count());
            intent.putExtra("heart_rate_date", item.getDate());
            intent.putExtra(DataBaseContants.HEART_RATE_TIME, item.getStartTime());
            intent.setClass(AddHeartRate.this, HeartRateDetailsActivity.class);
            AddHeartRate.this.startActivity(intent);
        }
    }

    class C11032 extends BroadcastReceiver {
        C11032() {
        }

        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals("com.zhuoyou.plugin.running.heart.data")) {
                PXIALGMOTION.Process(intent.getCharArrayExtra("heart_data"), intent.getFloatArrayExtra("gsensor_data"));
                AddHeartRate.this.hrData = PXIALGMOTION.GetHR();
                if (AddHeartRate.this.hrData > 0) {
                    AddHeartRate.this.hrDataList.add(Integer.valueOf(AddHeartRate.this.hrData));
                }
                Log.i("hph", "GetHR=" + PXIALGMOTION.GetHR());
            } else if (intent.getAction().equals("com.zhuoyou.plugin.running.m2.heart.data")) {
                AddHeartRate.this.hrData = intent.getIntExtra("m2_heart_data", 76);
                AddHeartRate.this.hrDataList.add(Integer.valueOf(AddHeartRate.this.hrData));
                Log.i("hph", "hrData=" + AddHeartRate.this.hrData);
            }
        }
    }

    class C11043 extends Handler {
        C11043() {
        }

        public void handleMessage(Message msg) {
            if (msg.what == 291) {
                AddHeartRate.this.progress_button.update(AddHeartRate.access$404(AddHeartRate.this));
                if (AddHeartRate.this.percent < 100) {
                    AddHeartRate.this.mhandler.sendEmptyMessageDelayed(291, 200);
                    return;
                }
                AddHeartRate.this.heartrateView.setVisibility(0);
                AddHeartRate.this.reminder.setVisibility(8);
                AddHeartRate.this.stopTestHeartRate(false);
            } else if (msg.what == 292) {
                int result = AddHeartRate.this.getAverage(AddHeartRate.this.hrDataList);
                AddHeartRate.this.hrDataList.clear();
                if (result <= 35) {
                    AddHeartRate.this.testResultShow(false);
                } else {
                    AddHeartRate.this.heartrate_showcount.setText(result + "");
                    AddHeartRate.this.insertDataBaseHeartRate(result + "");
                    AddHeartRate.this.testResultShow(true);
                }
                AddHeartRate.this.heartrateList = DataBaseUtil.getHeartRateList(AddHeartRate.this);
                if (AddHeartRate.this.heartrateList.size() > 20) {
                    AddHeartRate.this.deleteHeart();
                }
                AddHeartRate.this.GetHeartRateData();
                AddHeartRate.this.mMyAdapter.notifyDataSetChanged();
                Log.i("hph", "HEART_RATE_STOP");
            } else if (msg.what == AddHeartRate.HEART_RATE_STOP_HAND) {
                AddHeartRate.this.testResultShow(true);
                AddHeartRate.this.progress_button.setText(R.string.test_heart);
                List<RunningItem> runningItem = DataBaseUtil.getHeartRateList(AddHeartRate.this);
                if (runningItem.size() > 0) {
                    AddHeartRate.this.heartrate_showcount.setText(((RunningItem) runningItem.get(0)).getHeart_rate_count());
                } else {
                    AddHeartRate.this.heartrate_showcount.setText("0");
                }
                Log.i("hph", "HEART_RATE_STOP_HAND");
            }
        }
    }

    public class HeartRateDialog {
        private Dialog dialog;
        private TextView know;
        private Context mContext;
        private TextView no_reminder;

        class C11051 implements OnClickListener {
            C11051() {
            }

            public void onClick(View arg0) {
                if (AddHeartRate.this.progress_button.isDetecting()) {
                    AddHeartRate.this.stopTestHeartRate(true);
                } else {
                    AddHeartRate.this.startTestHeartRate();
                }
                HeartRateDialog.this.dialog.dismiss();
                AddHeartRate.this.isknow = true;
            }
        }

        class C11062 implements OnClickListener {
            C11062() {
            }

            public void onClick(View arg0) {
                if (AddHeartRate.this.progress_button.isDetecting()) {
                    AddHeartRate.this.stopTestHeartRate(true);
                } else {
                    AddHeartRate.this.startTestHeartRate();
                }
                Editor et = HeartRateDialog.this.mContext.getSharedPreferences("app_config", 0).edit();
                et.putBoolean("no_reminder", true);
                et.commit();
                HeartRateDialog.this.dialog.dismiss();
            }
        }

        public HeartRateDialog(Context context) {
            this.mContext = context;
            this.dialog = new Dialog(context, R.style.photo_dialogstyle);
            this.dialog.setContentView(R.layout.measurement_datails);
            this.dialog.setCanceledOnTouchOutside(false);
            Window window = this.dialog.getWindow();
            DisplayMetrics dm2 = context.getResources().getDisplayMetrics();
            LayoutParams lp = window.getAttributes();
            lp.width = dm2.widthPixels;
            lp.height = dm2.heightPixels;
            window.setAttributes(lp);
            iniView();
        }

        private void iniView() {
            this.know = (TextView) this.dialog.findViewById(R.id.know);
            this.no_reminder = (TextView) this.dialog.findViewById(R.id.no_reminder);
            this.know.setOnClickListener(new C11051());
            this.no_reminder.setOnClickListener(new C11062());
        }

        public void show() {
            this.dialog.show();
        }

        public void dismiss() {
            this.dialog.dismiss();
        }
    }

    class MyAdapter extends BaseAdapter {
        private LayoutInflater li;

        public MyAdapter() {
            this.li = (LayoutInflater) AddHeartRate.this.getSystemService("layout_inflater");
        }

        public int getCount() {
            return AddHeartRate.this.heartrateList.size();
        }

        public Object getItem(int position) {
            return Integer.valueOf(position);
        }

        public long getItemId(int position) {
            return (long) position;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                convertView = this.li.inflate(R.layout.listview_ringheart_item, null);
                holder = new ViewHolder();
                holder.test_date = (TextView) convertView.findViewById(R.id.text_date);
                holder.test_time = (TextView) convertView.findViewById(R.id.text_time);
                holder.test_count = (TextView) convertView.findViewById(R.id.text_number);
                holder.test_count.setTypeface(AddHeartRate.this.tf);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            RunningItem item = (RunningItem) AddHeartRate.this.heartrateList.get(position);
            holder.test_date.setText(item.getDate());
            holder.test_time.setText(item.getStartTime());
            String count = item.getHeart_rate_count();
            if (TextUtils.isEmpty(count) || count.equals("null")) {
                count = "0";
            }
            holder.test_count.setText(count);
            return convertView;
        }
    }

    private class ViewHolder {
        public TextView test_count;
        public TextView test_date;
        public TextView test_time;

        private ViewHolder() {
        }
    }

    static /* synthetic */ int access$404(AddHeartRate x0) {
        int i = x0.percent + 1;
        x0.percent = i;
        return i;
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.heartrate);
        this.tf = Typeface.createFromAsset(getAssets(), "font/cmtattoodragon.ttf");
        initView();
        registerBc();
        GetHeartRateData();
    }

    private void initView() {
        this.relativeLayout = (RelativeLayout) findViewById(R.id.back);
        this.relativeLayout.setOnClickListener(this);
        this.mBack = (ImageView) findViewById(R.id.back_m);
        this.mBack.setOnClickListener(this);
        this.title_share = (ImageView) findViewById(R.id.title_share);
        this.title_share.setOnClickListener(this);
        this.linearlayout_showtime = (RelativeLayout) findViewById(R.id.linearlayout_showtime);
        this.test_result = (TextView) findViewById(R.id.test_result);
        this.heartrate_showcount = (TextView) findViewById(R.id.heartrate_showcount);
        this.heartrate_showcount.setTypeface(this.tf);
        this.reminder = (TextView) findViewById(R.id.reminder);
        this.progress_button = (ProgressButton) findViewById(R.id.progress_button);
        this.progress_button.setOnClickListener(this);
        this.heartrateView = (HeartRateAnimationView) findViewById(R.id.heartrateView);
        this.my_listview = (ListView) findViewById(R.id.my_listview);
        this.mMyAdapter = new MyAdapter();
        this.my_listview.setAdapter(this.mMyAdapter);
        this.my_listview.setOnItemClickListener(new C11021());
    }

    private void GetHeartRateData() {
        this.heartrateList = DataBaseUtil.getHeartRateList(this);
        if (this.heartrateList.size() > 0) {
            this.title_share.setVisibility(0);
            this.heartrate_showcount.setText(((RunningItem) this.heartrateList.get(0)).getHeart_rate_count() + "");
        } else {
            this.heartrate_showcount.setText("0");
        }
        Log.i("lsj", "GetHeartRateData heartrateList.size = " + this.heartrateList.size());
    }

    private void deleteHeart() {
        RunningItem item = (RunningItem) this.heartrateList.get(19);
        Log.i("lsj", "deletecount =" + DataBaseUtil.deleteHeartDateByTime(item.getDate(), item.getStartTime(), this));
    }

    public void onClick(View v) {
        String name = Util.getDeviceName();
        switch (v.getId()) {
            case R.id.back:
                finish();
                PXIALGMOTION.Close();
                if (name.equals("Rumor-2")) {
                    sendBroadcast(new Intent(BleManagerService.ACTION_DISABLE_HEART_INFO));
                    return;
                }
                return;
            case R.id.back_m:
                finish();
                PXIALGMOTION.Close();
                if (name.equals("Rumor-2")) {
                    sendBroadcast(new Intent(BleManagerService.ACTION_DISABLE_HEART_INFO));
                    return;
                }
                return;
            case R.id.title_share:
                this.isShare = true;
                RunningItem item = (RunningItem) this.heartrateList.get(0);
                Intent intent = new Intent();
                intent.putExtra(DataBaseContants.HEART_RATE_COUNT, item.getHeart_rate_count());
                intent.putExtra("heart_rate_date", item.getDate());
                intent.putExtra(DataBaseContants.HEART_RATE_TIME, item.getStartTime());
                intent.setClass(this, HeartRateShareActivity.class);
                startActivity(intent);
                return;
            case R.id.progress_button:
                if (!BluetoothService.getInstance().isConnected() && (!RunningApp.isBLESupport || !BleManagerService.getInstance().GetBleConnectState())) {
                    Tools.makeToast(getResources().getString(R.string.alarm_toast));
                    return;
                } else if (Util.getDeviceName().equals("Rumor-2") || Util.getDeviceName().equals("M2")) {
                    this.sp = getSharedPreferences("app_config", 0);
                    if (!this.sp.getBoolean("no_reminder", false) && !this.isknow) {
                        new HeartRateDialog(this).show();
                        return;
                    } else if (this.progress_button.isDetecting()) {
                        stopTestHeartRate(true);
                        return;
                    } else {
                        startTestHeartRate();
                        return;
                    }
                } else {
                    Tools.makeToast(getResources().getString(R.string.no_support_heart_rate));
                    return;
                }
            default:
                return;
        }
    }

    private void startTestHeartRate() {
        if (BluetoothService.getInstance().isConnected() || (RunningApp.isBLESupport && BleManagerService.getInstance().GetBleConnectState())) {
            String name = Util.getDeviceName();
            if (name.equals("Rumor-2") || name.equals("M2")) {
                Log.i("lsj", "startTestHeartRate");
                this.linearlayout_showtime.setVisibility(4);
                this.test_result.setVisibility(8);
                this.reminder.setVisibility(8);
                this.heartrateView.setVisibility(0);
                this.progress_button.startDect();
                this.clickStime = System.currentTimeMillis();
                this.mhandler.sendEmptyMessage(291);
                this.mhandler.removeMessages(292);
                PXIALGMOTION.Close();
                if (name.equals("Rumor-2")) {
                    sendBroadcast(new Intent(BleManagerService.ACTION_HEART_DATA_READ));
                }
                if (name.equals("M2")) {
                    sendBroadcast(new Intent(BleManagerService.ACTION_M2_HEART_RATE));
                    return;
                }
                return;
            }
            Tools.makeToast(getResources().getString(R.string.no_support_heart_rate));
            return;
        }
        Tools.makeToast(getResources().getString(R.string.alarm_toast));
    }

    private void stopTestHeartRate(boolean fromHand) {
        String name = Util.getDeviceName();
        Log.i("lsj", "stopTestHeartRate");
        if (System.currentTimeMillis() - this.clickStime < 1000) {
            Tools.makeToast(getResources().getString(R.string.no_double_click));
            return;
        }
        this.percent = 0;
        this.mhandler.sendEmptyMessage(fromHand ? HEART_RATE_STOP_HAND : 292);
        this.mhandler.removeMessages(291);
        this.progress_button.stopDetect();
        PXIALGMOTION.Close();
        if (name.equals("Rumor-2")) {
            sendBroadcast(new Intent(BleManagerService.ACTION_DISABLE_HEART_INFO));
        }
    }

    private void registerBc() {
        IntentFilter intentData = new IntentFilter();
        intentData.addAction("com.zhuoyou.plugin.running.heart.data");
        intentData.addAction("com.zhuoyou.plugin.running.gsensor.data");
        intentData.addAction("com.zhuoyou.plugin.running.heart.gsensor.data");
        intentData.addAction("com.zhuoyou.plugin.running.m2.heart.data");
        registerReceiver(this.mGetDataReceiver, intentData);
    }

    private void unRegisterBc() {
        unregisterReceiver(this.mGetDataReceiver);
    }

    protected void onDestroy() {
        super.onDestroy();
        unRegisterBc();
    }

    protected void onResume() {
        if (this.heartrateList.size() > 0) {
            this.title_share.setVisibility(0);
        }
        if (this.heartrateList.size() > 20) {
            deleteHeart();
        }
        GetHeartRateData();
        this.mMyAdapter.notifyDataSetChanged();
        if (!this.progress_button.getText().equals(getResources().getString(R.string.stop_testheart))) {
            showView(1);
        } else if (this.isShare || this.isStart) {
            if (this.progress_button.isDetecting()) {
                showView(0);
            } else {
                showView(1);
            }
        }
        this.reminder.setVisibility(8);
        this.progress_button.setVisibility(0);
        this.progress_button.setText(R.string.test_heart);
        this.isShare = false;
        super.onResume();
    }

    private void showView(int type) {
        if (type == 0) {
            this.linearlayout_showtime.setVisibility(4);
            this.test_result.setVisibility(8);
            this.heartrateView.setVisibility(0);
            return;
        }
        this.linearlayout_showtime.setVisibility(0);
        this.test_result.setVisibility(0);
        this.heartrateView.setVisibility(8);
        if (this.heartrateList.size() > 0) {
            this.title_share.setVisibility(0);
            this.heartrate_showcount.setText(((RunningItem) this.heartrateList.get(0)).getHeart_rate_count() + "");
            return;
        }
        this.heartrate_showcount.setText("0");
    }

    private void testResultShow(boolean show) {
        if (show) {
            this.linearlayout_showtime.setVisibility(0);
            this.test_result.setVisibility(0);
            this.reminder.setVisibility(8);
            this.heartrateView.setVisibility(8);
            return;
        }
        this.linearlayout_showtime.setVisibility(4);
        this.test_result.setVisibility(8);
        this.heartrateView.setVisibility(8);
        this.reminder.setVisibility(0);
    }

    private int getAverage(ArrayList<Integer> list) {
        if (list.size() <= 0) {
            return 0;
        }
        int sum = 0;
        Iterator it = list.iterator();
        while (it.hasNext()) {
            sum += ((Integer) it.next()).intValue();
        }
        return sum / list.size();
    }

    private void insertDataBaseHeartRate(String count) {
        ContentValues runningItem = new ContentValues();
        runningItem.put("_id", Long.valueOf(Tools.getPKL()));
        runningItem.put("type", Integer.valueOf(7));
        runningItem.put(DataBaseContants.HEART_RATE_TIME, Long.valueOf(System.currentTimeMillis()));
        runningItem.put(DataBaseContants.HEART_RATE_COUNT, count);
        runningItem.put("date", getTimeBySystem());
        runningItem.put(DataBaseContants.STATISTICS, Integer.valueOf(0));
        runningItem.put(DataBaseContants.TIME_START, getStartTime());
        getContentResolver().insert(DataBaseContants.CONTENT_URI, runningItem);
    }

    private String getTimeBySystem() {
        return new SimpleDateFormat("yyyy-MM-dd").format(new Date(System.currentTimeMillis()));
    }

    private String getStartTime() {
        return DateFormat.format("HH:mm", System.currentTimeMillis()).toString();
    }

    public void onBackPressed() {
        PXIALGMOTION.Close();
        if (Util.getDeviceName().equals("Rumor-2")) {
            sendBroadcast(new Intent(BleManagerService.ACTION_DISABLE_HEART_INFO));
        }
        super.onBackPressed();
    }
}
