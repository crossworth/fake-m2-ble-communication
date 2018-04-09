package com.zhuoyou.plugin.gps;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v4.internal.view.SupportMenu;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.amap.api.maps.AMap;
import com.amap.api.maps.AMap.OnCameraChangeListener;
import com.amap.api.maps.AMap.OnMapScreenShotListener;
import com.amap.api.maps.AMap.OnMapTouchListener;
import com.amap.api.maps.AMap.OnMarkerClickListener;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.UiSettings;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.CameraPosition;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.LatLngBounds;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.Polyline;
import com.amap.api.maps.model.PolylineOptions;
import com.fithealth.running.R;
import com.mcube.lib.ped.PedometerService;
import com.pixart.alg.PXIALGMOTION;
import com.umeng.socialize.common.SocializeConstants;
import com.zhuoyou.plugin.ble.BleManagerService;
import com.zhuoyou.plugin.bluetooth.data.Util;
import com.zhuoyou.plugin.database.DataBaseContants;
import com.zhuoyou.plugin.database.DataBaseUtil;
import com.zhuoyou.plugin.gps.ilistener.GPSSignalListener;
import com.zhuoyou.plugin.gps.ilistener.IGPSPointListener;
import com.zhuoyou.plugin.gps.ilistener.IStepListener;
import com.zhuoyou.plugin.gps.ilistener.MonitorWatcher;
import com.zhuoyou.plugin.gps.ilistener.SignalWatcher;
import com.zhuoyou.plugin.gps.ilistener.StepWatcher;
import com.zhuoyou.plugin.running.PersonalConfig;
import com.zhuoyou.plugin.running.RunningItem;
import com.zhuoyou.plugin.running.Tools;
import java.io.File;
import java.text.DecimalFormat;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class GaoDeMapActivity extends Activity implements OnMarkerClickListener, OnCameraChangeListener, SensorEventListener, OnMapTouchListener, OnClickListener, OnMapScreenShotListener {
    public static final int BEGIN_MARK = 1;
    public static final int CANCEL_MARK = 3;
    public static final int CURRENT_MARK = 4;
    public static final int END_MARK = 2;
    private static final int GET_DATA_SCREEN = 2001;
    private static final int GET_MAP_SCREEN = 2000;
    private static final int LOCK_GPS_OPERATION = 5;
    public static final int MSG_LOCK_SUCESS = 291;
    public static final int MSG_UPDATE_LOCK_VIEW = 292;
    public static final String NEVER_SHOW_LOG = "never_show_exitDialog";
    private static Format decFormat = new DecimalFormat("#0.00");
    private static Format intformat = new DecimalFormat("00");
    public static boolean isStartHeart = false;
    public static boolean is_line = false;
    public static GuidePointModel staticGuide = null;
    private static final String tag = "GaoDeMapActivity";
    private String ACTION_POINT_LIST = "ACTION_POINT_LIST";
    private String ACTION_TIME_MANAGE = "ACTION_TIME_MANAGE";
    private int Countdown = 900;
    private AMap aMap;
    private double aveSpeed;
    private int begin_point_state = 0;
    private Marker beiginMark;
    private Button btnUnlock;
    private int calories;
    private Dialog cancelmapDataDialog;
    private PersonalConfig config;
    private ArrayList<GuidePointModel> currentList;
    private String current_address;
    private float current_angle = 0.0f;
    private long current_direction = 0;
    private Display display;
    private int durationTime;
    private Editor editor;
    private Marker endMark;
    private String end_address;
    private String fileName;
    private boolean first_zoom;
    private SimpleDateFormat formatter;
    private Sensor gyroSensor = null;
    private Dialog hanldmapDataDialog;
    private LinearLayout heart_layout_distance;
    private TextView heart_tvde_distance;
    public boolean isCompleteGps = false;
    public boolean isStartService = false;
    private boolean isViewOnTop;
    private boolean isZoomall = true;
    private boolean is_china = true;
    private boolean is_first_dire = false;
    private Dialog loadDialog;
    private AlarmManager mAlarm;
    private Handler mAlivegpsHandler = new C12571();
    private final BroadcastReceiver mBroadcastReceiver = new C12615();
    private Button mBtnFinish;
    private Button mBtnStart;
    private Button mBtnSupStart;
    private ImageView mBtnZoom;
    private DataBaseUtil mDataBaseUtil;
    private TextView mGPSSignalLevel;
    private BroadcastReceiver mGetDataReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals("com.zhuoyou.plugin.running.heart.data")) {
                PXIALGMOTION.Process(intent.getCharArrayExtra("heart_data"), intent.getFloatArrayExtra("gsensor_data"));
                if (PXIALGMOTION.GetHR() != 0) {
                    GaoDeMapActivity.this.heart_tvde_distance.setText(PXIALGMOTION.GetHR() + "");
                } else {
                    GaoDeMapActivity.this.heart_tvde_distance.setText("0");
                }
            } else if (intent.getAction().equals("com.zhuoyou.plugin.running.m2.heart.data")) {
                GaoDeMapActivity.this.heart_tvde_distance.setText(intent.getIntExtra("m2_heart_data", 76) + "");
            }
            Log.i("hph", "GetHR=" + PXIALGMOTION.GetHR());
        }
    };
    private TextView mGpsSignal;
    private GuidePointModel mGuidePoint;
    private Handler mHandler = new C12626();
    private LinearLayout mLayoutDetailData;
    private RelativeLayout mLayoutOpeartion;
    private LinearLayout mLayoutShortData;
    private LinearLayout mLayoutShutdown;
    private LinearLayout mLayoutSupStart;
    private Marker mMark;
    private PointObserver mPointOberver;
    public ServiceUtil mServiceUtil;
    private GPSSignalObserver mSignalObserver;
    private StepObserver mStepObserver;
    private TextView mTVCountdown;
    private TextView mTVDCalories;
    private TextView mTVDDisSpeed;
    private TextView mTVDDistance;
    private TextView mTVDStep;
    private TextView mTVDTimer;
    private TextView mTVSDistance;
    private TextView mTVSStep;
    private TextView mTVSTimer;
    private int mTaskState = 0;
    private UiSettings mUiSettings;
    private MapView mapView;
    private String no_address;
    private PendingIntent pendingIntent;
    private Polyline polyline;
    private Resources res;
    private SensorManager sensorManager = null;
    private SharedPreferences sharepreference;
    private long startTime;
    private String start_address;
    private double sumRunDis;
    private int sumStep;
    private Typeface tf;
    private Thread timerThread = new Thread(new C12582());
    private Handler unLockHandler = new C12604();
    private boolean unLocked = true;
    private View unlockView;
    private GuidePointModel upPoint;
    private WindowManager windowManager;

    class C12571 extends Handler {
        C12571() {
        }

        public void handleMessage(Message msg) {
            if (msg.what == 1) {
                Log.i("GaoDeMapActivit1", "msg.arg1" + msg.arg1);
                GaoDeMapActivity.this.mTVDTimer.setText(GaoDeMapActivity.this.formatTimer((long) msg.arg1));
                GaoDeMapActivity.this.mTVSTimer.setText(GaoDeMapActivity.this.formatTimer((long) msg.arg1));
            }
        }
    }

    class C12582 implements Runnable {
        C12582() {
        }

        public void run() {
            while (true) {
                Log.i("GaoDeMapActivit1", "isViewOnTop:" + GaoDeMapActivity.this.isViewOnTop);
                Log.i("GaoDeMapActivit1", "is_running:" + GaodeService.is_running);
                if (GaoDeMapActivity.this.isViewOnTop && GaodeService.is_running) {
                    Message msg = GaoDeMapActivity.this.mAlivegpsHandler.obtainMessage();
                    msg.what = 1;
                    msg.arg1 = GaoDeMapActivity.access$404(GaoDeMapActivity.this);
                    GaoDeMapActivity.this.mAlivegpsHandler.sendMessage(msg);
                    if (!GaoDeMapActivity.this.unLockHandler.hasMessages(5)) {
                        Message lockMsg = GaoDeMapActivity.this.unLockHandler.obtainMessage();
                        lockMsg.what = 5;
                        GaoDeMapActivity.this.unLockHandler.sendMessageDelayed(lockMsg, 3000);
                    }
                }
                if (GaoDeMapActivity.this.sharepreference.getInt("map_activity_state", 0) == 3) {
                    msg = GaoDeMapActivity.this.unLockHandler.obtainMessage();
                    msg.what = 10;
                    msg.arg1 = GaoDeMapActivity.this.Countdown = GaoDeMapActivity.this.Countdown - 1;
                    GaoDeMapActivity.this.unLockHandler.sendMessage(msg);
                } else if (GaoDeMapActivity.this.Countdown != 900) {
                    GaoDeMapActivity.this.Countdown = 900;
                    msg = GaoDeMapActivity.this.unLockHandler.obtainMessage();
                    msg.what = 9;
                    GaoDeMapActivity.this.unLockHandler.sendMessage(msg);
                }
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    class C12593 implements OnTouchListener {
        C12593() {
        }

        public boolean onTouch(View v, MotionEvent event) {
            switch (event.getAction()) {
                case 0:
                    if (!GaoDeMapActivity.this.unLocked) {
                        GaoDeMapActivity.this.unLockHandler.sendEmptyMessageDelayed(292, 10);
                        break;
                    }
                    break;
                case 1:
                    if (!GaoDeMapActivity.this.unLocked) {
                        GaoDeMapActivity.this.unLockHandler.sendEmptyMessage(5);
                    }
                    GaoDeMapActivity.this.unLockHandler.removeMessages(292);
                    break;
            }
            return true;
        }
    }

    class C12604 extends Handler {
        C12604() {
        }

        public void handleMessage(Message msg) {
            if (msg.what == 292) {
                GaoDeMapActivity.this.unLocked = false;
                GaoDeMapActivity.this.updateUnLockView(msg.arg1);
                Message msg1 = new Message();
                int i = msg.arg1 + 1;
                msg.arg1 = i;
                msg1.arg1 = i;
                if (msg1.arg1 >= 100) {
                    msg1.what = 291;
                } else {
                    msg1.what = 292;
                }
                GaoDeMapActivity.this.unLockHandler.sendMessageDelayed(msg1, 10);
            } else if (msg.what == 291) {
                GaoDeMapActivity.this.unLocked = true;
                GaoDeMapActivity.this.unLockHandler.removeMessages(5);
                GaoDeMapActivity.this.btnUnlock.setVisibility(8);
                GaoDeMapActivity.this.mBtnStart.setVisibility(8);
                GaoDeMapActivity.this.updateUnLockView(0);
                GaoDeMapActivity.this.mLayoutSupStart.setVisibility(0);
            } else if (msg.what == 5) {
                GaoDeMapActivity.this.unLocked = false;
                GaoDeMapActivity.this.updateUnLockView(0);
                if (GaoDeMapActivity.this.btnUnlock.getVisibility() == 8 && GaodeService.is_running) {
                    GaoDeMapActivity.this.btnUnlock.setVisibility(0);
                    GaoDeMapActivity.this.mBtnStart.setVisibility(8);
                    GaoDeMapActivity.this.mLayoutSupStart.setVisibility(8);
                }
            } else if (msg.what == 9) {
                GaoDeMapActivity.this.mLayoutShutdown.setVisibility(8);
            } else if (msg.what == 10) {
                if (GaoDeMapActivity.this.mLayoutShutdown.getVisibility() != 0) {
                    GaoDeMapActivity.this.mLayoutShutdown.setVisibility(0);
                }
                int time = msg.arg1;
                GaoDeMapActivity.this.mTVCountdown.setText(GaoDeMapActivity.intformat.format(Integer.valueOf(time / 60)) + GaoDeMapActivity.this.res.getString(R.string.gps_minute) + GaoDeMapActivity.intformat.format(Integer.valueOf(time % 60)) + GaoDeMapActivity.this.res.getString(R.string.gps_second));
                if (msg.arg1 <= 0) {
                    GaoDeMapActivity.this.mLayoutShutdown.setVisibility(8);
                }
            }
        }
    }

    class C12615 extends BroadcastReceiver {
        C12615() {
        }

        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (!GaoDeMapActivity.this.ACTION_POINT_LIST.equals(action)) {
                if (GaoDeMapActivity.this.ACTION_TIME_MANAGE.equals(action)) {
                    if (GaoDeMapActivity.staticGuide != null) {
                        GaoDeMapActivity.this.aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(GaoDeMapActivity.staticGuide.getLatitude(), GaoDeMapActivity.staticGuide.getLongitude()), GaoDeMapActivity.this.sharepreference.getFloat("current_amp_zoom", 14.0f)));
                    }
                } else if ("ACTION_SAVE_GPSSPORT".equals(action)) {
                    GaoDeMapActivity.this.initRunData();
                    GaoDeMapActivity.this.mServiceUtil.isServiceRunning();
                }
            }
        }
    }

    class C12626 extends Handler {
        C12626() {
        }

        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 2000:
                    GaoDeMapActivity.this.getMapScreenShot();
                    break;
                case 2001:
                    GaoDeMapActivity.this.LoadedMaptoview();
                    break;
            }
            super.handleMessage(msg);
        }
    }

    class C12637 implements DialogInterface.OnClickListener {
        C12637() {
        }

        public void onClick(DialogInterface dialog, int which) {
            Editor edit = GaoDeMapActivity.this.sharepreference.edit();
            edit.putBoolean(GaoDeMapActivity.NEVER_SHOW_LOG, true);
            edit.commit();
            dialog.dismiss();
            GaoDeMapActivity.isStartHeart = true;
            GaoDeMapActivity.this.isStartService = true;
            GaoDeMapActivity.this.finish();
        }
    }

    class C12648 implements DialogInterface.OnClickListener {
        C12648() {
        }

        public void onClick(DialogInterface dialog, int which) {
            dialog.dismiss();
            GaoDeMapActivity.isStartHeart = true;
            GaoDeMapActivity.this.isStartService = true;
            GaoDeMapActivity.this.finish();
        }
    }

    class C12659 implements DialogInterface.OnClickListener {
        C12659() {
        }

        public void onClick(DialogInterface dialog, int which) {
            dialog.dismiss();
        }
    }

    public class GPSShowDialog {
        private int count = 3;
        private Dialog dialog;
        Handler handler = new C12661();
        private Context mContext;
        private TextView text_count;

        class C12661 extends Handler {
            C12661() {
            }

            public void handleMessage(Message msg) {
                if (msg.what == 0) {
                    Log.i("luosaijun", "count = " + GPSShowDialog.this.count);
                    GPSShowDialog.this.text_count.startAnimation(AnimationUtils.loadAnimation(GaoDeMapActivity.this, R.anim.count_down_exit));
                    GPSShowDialog.this.count = GPSShowDialog.this.count - 1;
                    if (GPSShowDialog.this.count <= 0) {
                        GPSShowDialog.this.dialog.dismiss();
                        String name = Util.getDeviceName();
                        if (name.equals("Rumor-2") || name.equals("M2")) {
                            GaoDeMapActivity.this.openHeartRate();
                            return;
                        } else {
                            Tools.makeToast(GaoDeMapActivity.this.res.getString(R.string.no_support_heart_rate));
                            return;
                        }
                    }
                    GPSShowDialog.this.text_count.setText(GPSShowDialog.this.count + "");
                    GPSShowDialog.this.handler.sendEmptyMessageDelayed(0, 1000);
                }
            }
        }

        public GPSShowDialog(Context context) {
            this.mContext = context;
            this.dialog = new Dialog(context, R.style.photo_dialogstyle);
            this.dialog.setContentView(R.layout.my_countdown);
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
            this.text_count = (TextView) this.dialog.findViewById(R.id.text_count);
            this.text_count.setTypeface(GaoDeMapActivity.this.tf);
        }

        public void show() {
            this.dialog.show();
            this.handler.sendEmptyMessageDelayed(0, 1000);
        }

        public void dismiss() {
            this.dialog.dismiss();
        }
    }

    private class InitDataTask extends AsyncTask<String, Integer, String> {
        int operation_state;

        private InitDataTask() {
            this.operation_state = GaoDeMapActivity.this.sharepreference.getInt("map_activity_state", 0);
        }

        protected void onPreExecute() {
            super.onPreExecute();
            if (GaoDeMapActivity.this.mTaskState == 1) {
                if (this.operation_state > 1) {
                    GaoDeMapActivity.this.loadDialog.show();
                }
            } else if (GaoDeMapActivity.this.mTaskState != 2 && GaoDeMapActivity.this.mTaskState == 3) {
            }
        }

        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            if (GaoDeMapActivity.this.mTaskState == 1) {
                if (this.operation_state > 1) {
                    GaoDeMapActivity.this.loadDialog.dismiss();
                }
            } else if (GaoDeMapActivity.this.mTaskState != 2 && GaoDeMapActivity.this.mTaskState == 3) {
            }
        }

        protected String doInBackground(String... params) {
            if (GaoDeMapActivity.this.mTaskState == 1) {
                if (this.operation_state > 1) {
                    GaoDeMapActivity.this.initDatabaseline();
                }
            } else if (GaoDeMapActivity.this.mTaskState == 2) {
                GaoDeMapActivity.this.handlerMap();
            } else if (GaoDeMapActivity.this.mTaskState == 3) {
                GaoDeMapActivity.this.compeleGpsSport();
            }
            return null;
        }
    }

    class GPSSignalObserver implements GPSSignalListener {
        GPSSignalObserver() {
        }

        public void update(int gpsState) {
            GaoDeMapActivity.this.initGpsSignal(gpsState);
        }
    }

    class PointObserver implements IGPSPointListener {
        Format mFormat = new DecimalFormat("#0.00");

        PointObserver() {
        }

        public void update(GuidePointModel point) {
            if (point != null) {
                GaoDeMapActivity.staticGuide = point;
            }
            if (GaoDeMapActivity.this.first_zoom) {
                point.setPointState(20);
                GaoDeMapActivity.this.mDataBaseUtil.inserTempPoint(point);
                GaoDeMapActivity.this.aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(point.getLatitude(), point.getLongitude()), GaoDeMapActivity.this.sharepreference.getFloat("current_amp_zoom", 14.0f)));
                GaoDeMapActivity.this.hanlderMarker(point, 4);
                Editor edit = GaoDeMapActivity.this.sharepreference.edit();
                edit.putBoolean("is_begin_point", false);
                edit.commit();
                GaoDeMapActivity.this.first_zoom = false;
            }
            if (point.getProvider().equals("gps")) {
                if (!GaoDeMapActivity.this.isCompleteGps) {
                    GaoDeMapActivity.this.hanlderMarker(point, 4);
                }
                GaoDeMapActivity.this.calGPSLocationPoint(point);
            }
            if (GaoDeMapActivity.this.upPoint == null) {
                GaoDeMapActivity.this.upPoint = point;
            }
            if (GaoDeMapActivity.this.upPoint.getTime() != point.getTime() && point.getProvider().equals("gps") && point.getAccuracy() < 15.0f && GaodeService.is_running) {
                LatLng upLatLng = new LatLng(GaoDeMapActivity.this.upPoint.getLatitude(), GaoDeMapActivity.this.upPoint.getLongitude());
                LatLng nowLatLng = new LatLng(point.getLatitude(), point.getLongitude());
                if (GaoDeMapActivity.this.durationTime != 0) {
                    GaoDeMapActivity.this.aveSpeed = GaoDeMapActivity.this.sumRunDis / ((double) GaoDeMapActivity.this.durationTime);
                } else {
                    GaoDeMapActivity.this.aveSpeed = 0.0d;
                }
                GaoDeMapActivity.this.upPoint = point;
                long gpsid = GaoDeMapActivity.this.sharepreference.getLong("gps_sport_id", -1);
                if (gpsid != -1) {
                    GpsSportDataModel mGpsInfo = new GpsSportDataModel();
                    mGpsInfo.setGpsId(gpsid);
                    mGpsInfo.setAvespeed(GaoDeMapActivity.this.aveSpeed);
                    mGpsInfo.setCalorie((double) GaoDeMapActivity.this.calories);
                    mGpsInfo.setDurationtime((long) GaoDeMapActivity.this.durationTime);
                    mGpsInfo.setEndAddress(GaoDeMapActivity.this.end_address);
                    mGpsInfo.setStartAddress(GaoDeMapActivity.this.start_address);
                    mGpsInfo.setSteps(GaoDeMapActivity.this.sumStep);
                    mGpsInfo.setTotalDistance(GaoDeMapActivity.this.sumRunDis);
                    GaoDeMapActivity.this.mDataBaseUtil.updateGpsInfo(mGpsInfo);
                }
                GaoDeMapActivity.this.mTVDDistance.setText(this.mFormat.format(Double.valueOf(GaoDeMapActivity.this.sumRunDis / 1000.0d)));
                GaoDeMapActivity.this.mTVSDistance.setText(this.mFormat.format(Double.valueOf(GaoDeMapActivity.this.sumRunDis / 1000.0d)));
                GaoDeMapActivity.this.mTVDDisSpeed.setText(this.mFormat.format(Double.valueOf(GaoDeMapActivity.this.aveSpeed * 3.6d)));
            }
        }

        public void sumDisChanged(double distance) {
            GaoDeMapActivity.this.sumRunDis = distance;
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
            GaoDeMapActivity.this.sumStep = hadRunStep;
            GaoDeMapActivity.this.calories = Tools.calcCalories(Tools.calcDistance(GaoDeMapActivity.this.sumStep, GaoDeMapActivity.this.config.getHeight()), GaoDeMapActivity.this.config.getWeightNum());
            GaoDeMapActivity.this.mTVDStep.setText("" + GaoDeMapActivity.this.sumStep);
            GaoDeMapActivity.this.mTVSStep.setText("" + GaoDeMapActivity.this.sumStep);
            GaoDeMapActivity.this.mTVDCalories.setText("" + GaoDeMapActivity.this.calories);
        }
    }

    static /* synthetic */ int access$404(GaoDeMapActivity x0) {
        int i = x0.durationTime + 1;
        x0.durationTime = i;
        return i;
    }

    public String formatTimer(long timer) {
        timer -= 3;
        String hour = intformat.format(Long.valueOf((timer % 86400) / 3600));
        String minute = intformat.format(Long.valueOf((timer % 3600) / 60));
        String second = intformat.format(Long.valueOf(timer % 60));
        if (timer % 60 < 0) {
            second = "00";
        }
        return hour + ":" + minute + ":" + second;
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map_gaode);
        this.res = getResources();
        this.mapView = (MapView) findViewById(R.id.gaode_map);
        this.mapView.onCreate(savedInstanceState);
        this.mDataBaseUtil = new DataBaseUtil(getApplicationContext());
        this.mServiceUtil = new ServiceUtil(this);
        this.sharepreference = getSharedPreferences("gaode_location_info", 0);
        this.no_address = this.res.getString(R.string.gps_addressunknow);
        this.start_address = this.res.getString(R.string.gps_startAddress);
        this.end_address = this.res.getString(R.string.gps_endAddress);
        this.current_address = this.res.getString(R.string.gps_currentAddress);
        this.windowManager = getWindowManager();
        this.display = this.windowManager.getDefaultDisplay();
        this.mAlarm = (AlarmManager) getSystemService("alarm");
        this.currentList = new ArrayList();
        this.loadDialog = new Builder(this).setTitle(this.res.getString(R.string.gps_loadingmMap)).setMessage(this.res.getString(R.string.gps_isLoadingMap)).create();
        this.hanldmapDataDialog = new Builder(this).setTitle(this.res.getString(R.string.gps_fixMapData)).setMessage(this.res.getString(R.string.gps_isLoadingMap)).create();
        this.cancelmapDataDialog = new Builder(this).setTitle(this.res.getString(R.string.gps_cancelMapData)).setMessage(this.res.getString(R.string.gps_ishanldingMap)).create();
        this.tf = Typeface.createFromAsset(getAssets(), "font/cmtattoodragon.ttf");
        initView();
        getLanguageEnv();
        initFilter();
        initMapView();
        openGPSSettings();
        isNetuseful();
        initGuideline();
        registerBc();
        this.mPointOberver = new PointObserver();
        MonitorWatcher.getInstance().addWatcher(this.mPointOberver);
        this.mSignalObserver = new GPSSignalObserver();
        SignalWatcher.getInstance().addWatcher(this.mSignalObserver);
        this.mStepObserver = new StepObserver();
        StepWatcher.getInstance().addWatcher(this.mStepObserver);
        this.timerThread.start();
    }

    private void initGuideline() {
        List<GuidePointModel> mylist3 = this.mDataBaseUtil.selectTempPoint();
        if (this.sharepreference.getInt("map_activity_state", 0) > 1) {
            this.startTime = this.mDataBaseUtil.selectLastOperation(1);
            this.mTaskState = 1;
            new InitDataTask().execute(new String[0]);
            List<GuidePointModel> mylist = this.mDataBaseUtil.selectPoint(this.startTime, conversTime());
            List<GuidePointModel> mylist_new = new ArrayList();
            if (mylist.size() > 0) {
                mylist_new.add(mylist.get(mylist.size() - 1));
            }
            if (GaodeService.handlerList.size() > 0) {
                staticGuide = (GuidePointModel) GaodeService.handlerList.get(GaodeService.handlerList.size() - 1);
                mylist_new.addAll(GaodeService.handlerList);
            } else {
                List<GuidePointModel> mylist2 = this.mDataBaseUtil.selectTempPoint(10);
                if (mylist3.size() > 0) {
                    staticGuide = (GuidePointModel) mylist3.get(mylist3.size() - 1);
                }
                if (mylist2.size() > 0) {
                    mylist_new.addAll(mylist2);
                }
            }
            if (mylist_new.size() > 0) {
                initLine(mylist_new, false);
                this.currentList.add(mylist_new.get(mylist_new.size() - 1));
            } else if (mylist.size() > 0) {
                this.currentList.add(mylist.get(mylist.size() - 1));
            }
            if (staticGuide == null && mylist.size() > 0) {
                staticGuide = (GuidePointModel) mylist.get(mylist.size() - 1);
            }
        } else if (mylist3.size() > 0) {
            staticGuide = (GuidePointModel) mylist3.get(mylist3.size() - 1);
        }
        if (staticGuide != null) {
            this.aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(staticGuide.getLatitude(), staticGuide.getLongitude()), this.sharepreference.getFloat("current_amp_zoom", 14.0f)));
            hanlderMarker(staticGuide, 4);
            Editor edit = this.sharepreference.edit();
            edit.putBoolean("is_begin_point", false);
            edit.commit();
            this.first_zoom = false;
            return;
        }
        this.begin_point_state = 1;
        this.first_zoom = true;
        edit = this.sharepreference.edit();
        edit.putBoolean("is_begin_point", true);
        edit.commit();
    }

    private void initMapView() {
        if (this.aMap == null) {
            this.aMap = this.mapView.getMap();
            this.mUiSettings = this.aMap.getUiSettings();
            this.mUiSettings.setZoomControlsEnabled(false);
            this.aMap.setOnMarkerClickListener(this);
            this.aMap.setOnCameraChangeListener(this);
            this.aMap.setOnMapTouchListener(this);
        }
    }

    private void initView() {
        this.sensorManager = (SensorManager) getSystemService("sensor");
        this.gyroSensor = this.sensorManager.getDefaultSensor(3);
        this.mLayoutDetailData = (LinearLayout) findViewById(R.id.gps_layout_detaildata);
        this.mLayoutOpeartion = (RelativeLayout) findViewById(R.id.gps_layout_operation);
        this.mTVDStep = (TextView) findViewById(R.id.gps_tvde_step);
        this.mTVDStep.setTypeface(this.tf);
        this.mTVDTimer = (TextView) findViewById(R.id.gps_tvde_timer);
        this.mTVDTimer.setTypeface(this.tf);
        this.mTVDDistance = (TextView) findViewById(R.id.gps_tvde_distance);
        this.mTVDDistance.setTypeface(this.tf);
        this.mTVDDisSpeed = (TextView) findViewById(R.id.gps_tvde_dipspeed);
        this.mTVDDisSpeed.setTypeface(this.tf);
        this.mTVDCalories = (TextView) findViewById(R.id.gps_tvde_calorie );
        this.mTVDCalories.setTypeface(this.tf);
        this.mLayoutShortData = (LinearLayout) findViewById(R.id.gps_layout_shortdata);
        this.mTVSStep = (TextView) findViewById(R.id.gps_tvsh_step);
        this.mTVSTimer = (TextView) findViewById(R.id.gps_tvsh_timer);
        this.mTVSDistance = (TextView) findViewById(R.id.gps_tvsh_travel);
        this.mGpsSignal = (TextView) findViewById(R.id.gps_signal);
        this.mGPSSignalLevel = (TextView) findViewById(R.id.gps_signal_level);
        this.mLayoutSupStart = (LinearLayout) findViewById(R.id.gps_layout_restart);
        this.mBtnZoom = (ImageView) findViewById(R.id.gps_igbtn_zoom);
        this.mTVCountdown = (TextView) findViewById(R.id.gps_countdown);
        this.mLayoutShutdown = (LinearLayout) findViewById(R.id.gps_layout_shutdown_notify);
        this.heart_layout_distance = (LinearLayout) findViewById(R.id.heart_layout_distance);
        this.heart_tvde_distance = (TextView) findViewById(R.id.heart_tvde_distance);
        this.heart_tvde_distance.setTypeface(this.tf);
        this.mBtnStart = (Button) findViewById(R.id.gps_btn_start);
        this.mBtnSupStart = (Button) findViewById(R.id.gps_btn_supstart);
        this.mBtnFinish = (Button) findViewById(R.id.gps_btn_finish);
        this.btnUnlock = (Button) findViewById(R.id.btn_unlock);
        this.unlockView = findViewById(R.id.slider_icon);
        this.btnUnlock.setOnTouchListener(new C12593());
        initGpsSignal(this.sharepreference.getInt("gps_singal_state", 0));
        findViewById(R.id.back).setOnClickListener(this);
        this.mBtnStart.setOnClickListener(this);
        this.mBtnSupStart.setOnClickListener(this);
        this.mBtnFinish.setOnClickListener(this);
        this.mBtnZoom.setOnClickListener(this);
        this.config = Tools.getPersonalConfig();
    }

    private void updateUnLockView(int percent) {
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) this.unlockView.getLayoutParams();
        params.width = (this.btnUnlock.getLayoutParams().width * percent) / 100;
        this.unlockView.setLayoutParams(params);
    }

    public boolean onTouchEvent(MotionEvent event) {
        if (this.unLockHandler.hasMessages(5)) {
            this.unLockHandler.removeMessages(5);
        }
        return super.onTouchEvent(event);
    }

    private void initGpsSignal(int gpsState) {
        switch (gpsState) {
            case 0:
                this.mGpsSignal.setText(this.res.getString(R.string.gps_signal_state0));
                this.mGPSSignalLevel.setVisibility(8);
                Tools.makeToast(this.res.getString(R.string.gpsSignal_search));
                return;
            case 1:
                this.mGpsSignal.setText(this.res.getString(R.string.gps_signal_state1));
                this.mGPSSignalLevel.setVisibility(0);
                this.mGPSSignalLevel.setTextColor(-16711936);
                this.mGPSSignalLevel.setText(this.res.getString(R.string.gps_signal_state2));
                return;
            case 2:
                this.mGpsSignal.setText(this.res.getString(R.string.gps_signal_state1));
                this.mGPSSignalLevel.setVisibility(0);
                this.mGPSSignalLevel.setTextColor(getResources().getColor(R.color.colorRed));
                this.mGPSSignalLevel.setText(this.res.getString(R.string.gps_signal_state3));
                Tools.makeToast(this.res.getString(R.string.gpsSignal_weak));
                return;
            default:
                return;
        }
    }

    private boolean isNetuseful() {
        boolean bisConnFlag = false;
        ConnectivityManager conManager = (ConnectivityManager) getSystemService("connectivity");
        if (conManager.getActiveNetworkInfo() != null) {
            bisConnFlag = conManager.getActiveNetworkInfo().isAvailable();
        }
        if (!bisConnFlag) {
            Toast.makeText(this, this.res.getString(R.string.open_net), 1).show();
        }
        return bisConnFlag;
    }

    private void initFilter() {
        IntentFilter mGATTFilter = new IntentFilter();
        mGATTFilter.addAction("ACTION_POINT_LIST");
        mGATTFilter.addAction("ACTION_TIME_MANAGE");
        mGATTFilter.addAction("ACTION_SAVE_GPSSPORT");
        registerReceiver(this.mBroadcastReceiver, mGATTFilter);
    }

    private void calGPSLocationPoint(GuidePointModel point) {
        this.mGuidePoint = point;
        if (is_line) {
            this.currentList.add(point);
            if (this.begin_point_state == 1) {
                hanlderMarker((GuidePointModel) this.currentList.get(0), 1);
            }
            if (this.currentList.size() > 1) {
                initLine(this.currentList, false);
                this.currentList.clear();
                this.currentList.add(point);
            }
        }
    }

    public void timeManage() {
        this.pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 800, new Intent("ACTION_TIME_MANAGE"), 134217728);
        this.mAlarm.setRepeating(1, System.currentTimeMillis() + 3000, 3000, this.pendingIntent);
    }

    public void hanlderMarker(GuidePointModel mGuidePoint, int marker) {
        LatLng latlng = new LatLng(mGuidePoint.getLatitude(), mGuidePoint.getLongitude());
        switch (marker) {
            case 1:
                MarkerOptions markerOption1;
                if (this.is_china) {
                    markerOption1 = new MarkerOptions().anchor(0.5f, 0.5f).position(latlng).title(this.start_address).snippet(GaodeService.startAddress).icon(BitmapDescriptorFactory.fromResource(R.drawable.point_begin)).perspective(true).draggable(true);
                } else {
                    markerOption1 = new MarkerOptions().anchor(0.5f, 0.5f).position(latlng).title(this.start_address).snippet(GaodeService.startAddress).icon(BitmapDescriptorFactory.fromResource(R.drawable.point_begin_en)).perspective(true).draggable(true);
                }
                this.beiginMark = this.aMap.addMarker(markerOption1);
                this.begin_point_state = 2;
                return;
            case 2:
                MarkerOptions markerOption2;
                String maddress2 = GaodeService.endAddress;
                if (this.is_china) {
                    markerOption2 = new MarkerOptions().anchor(0.5f, 0.5f).position(latlng).title(this.end_address).snippet(maddress2).icon(BitmapDescriptorFactory.fromResource(R.drawable.point_complete)).perspective(true).draggable(true);
                } else {
                    markerOption2 = new MarkerOptions().anchor(0.5f, 0.5f).position(latlng).title(this.end_address).snippet(maddress2).icon(BitmapDescriptorFactory.fromResource(R.drawable.point_complete_en)).perspective(true).draggable(true);
                }
                this.endMark = this.aMap.addMarker(markerOption2);
                return;
            case 3:
                Marker cancelMark = this.aMap.addMarker(new MarkerOptions().anchor(0.5f, 0.5f).position(latlng).title(this.current_address).icon(BitmapDescriptorFactory.fromResource(R.drawable.point_cancel)).perspective(true).draggable(true));
                return;
            case 4:
                if (this.mMark != null) {
                    this.mMark.remove();
                }
                this.mMark = this.aMap.addMarker(new MarkerOptions().anchor(0.5f, 0.5f).position(latlng).title(this.current_address).icon(BitmapDescriptorFactory.fromResource(R.drawable.location_marker)).perspective(true).draggable(true));
                this.mMark.setRotateAngle(360.0f - this.current_angle);
                return;
            default:
                return;
        }
    }

    public void initLine(List<GuidePointModel> listPoint, boolean isVirtual) {
        int i;
        List<LatLng> initList = new ArrayList();
        List<LatLng> tempList = new ArrayList();
        for (i = 0; i < listPoint.size(); i++) {
            initList.add(new LatLng(((GuidePointModel) listPoint.get(i)).getLatitude(), ((GuidePointModel) listPoint.get(i)).getLongitude()));
        }
        if (initList.size() > 1 && initList.size() < 10000) {
            this.polyline = this.aMap.addPolyline(new PolylineOptions().addAll(initList).width(14.0f).setDottedLine(isVirtual).color(SupportMenu.CATEGORY_MASK));
        } else if (initList.size() > 10000) {
            int mp = initList.size() / 10000;
            int mod = initList.size() % 10000;
            int j = 0;
            for (i = 0; i < initList.size(); i++) {
                tempList.add(initList.get(i));
                if (j < mp) {
                    if (tempList.size() == 10000) {
                        this.polyline = this.aMap.addPolyline(new PolylineOptions().addAll(tempList).width(14.0f).setDottedLine(isVirtual).color(SupportMenu.CATEGORY_MASK));
                        tempList.clear();
                        tempList.add(initList.get(i));
                        j++;
                    }
                } else if (mod + mp < 10000) {
                    if (initList.size() == mod + mp) {
                        this.polyline = this.aMap.addPolyline(new PolylineOptions().addAll(tempList).width(14.0f).setDottedLine(isVirtual).color(SupportMenu.CATEGORY_MASK));
                        initList.clear();
                    }
                } else if (tempList.size() == 10000) {
                    this.polyline = this.aMap.addPolyline(new PolylineOptions().addAll(tempList).width(14.0f).setDottedLine(isVirtual).color(SupportMenu.CATEGORY_MASK));
                    tempList.clear();
                }
            }
        }
    }

    public boolean onMarkerClick(Marker marker) {
        if (marker.getTitle().trim().equals(this.current_address.trim())) {
            return true;
        }
        if (marker.isInfoWindowShown()) {
            marker.hideInfoWindow();
        } else {
            marker.showInfoWindow();
        }
        return false;
    }

    public void onCameraChange(CameraPosition arg0) {
    }

    public void onCameraChangeFinish(CameraPosition arg0) {
        float mzoom = this.aMap.getCameraPosition().zoom;
        this.editor = this.sharepreference.edit();
        this.editor.putFloat("current_amp_zoom", mzoom);
        this.editor.commit();
    }

    public void onSensorChanged(SensorEvent event) {
        if (this.is_first_dire) {
            this.current_direction = System.currentTimeMillis();
            this.is_first_dire = false;
        } else if (System.currentTimeMillis() - this.current_direction > 2000) {
            this.current_direction = System.currentTimeMillis();
            this.current_angle = event.values[0];
            if (this.mMark != null) {
                this.mMark.setRotateAngle(360.0f - event.values[0]);
            }
        }
    }

    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }

    protected void onResume() {
        super.onResume();
        Log.i(tag, "onResume");
        this.isViewOnTop = true;
        this.mapView.onResume();
        this.sensorManager.registerListener(this, this.gyroSensor, 3);
        this.is_first_dire = true;
        calRunData();
        this.mServiceUtil.isServiceRunning();
        int operation_state = this.sharepreference.getInt("map_activity_state", 0);
        if (operation_state == 2 || operation_state == 4) {
            GaodeService.is_running = true;
            is_line = true;
        } else {
            GaodeService.is_running = false;
            is_line = true;
        }
        Log.i(tag, "operation_state:" + operation_state);
        if (operation_state == 0) {
            initRunData();
        } else if (operation_state == 2) {
            beginViewState();
        } else if (operation_state == 3) {
            stopViewState();
        } else if (operation_state == 4) {
            continueViewState();
        }
    }

    private void initRunData() {
        this.sumStep = 0;
        this.durationTime = 0;
        this.sumRunDis = 0.0d;
        this.aveSpeed = 0.0d;
        this.mTVDStep.setText("0");
        this.mTVDTimer.setText("00:00:00");
        this.mTVDDistance.setText("0.00");
        this.mTVDDisSpeed.setText("0.00");
        this.mTVDCalories.setText("0");
        this.mTVSStep.setText("0");
        this.mTVSTimer.setText("00:00:00");
        this.mTVSDistance.setText("0.00");
        this.heart_tvde_distance.setText("0");
        this.mBtnStart.setVisibility(0);
        this.mLayoutSupStart.setVisibility(8);
        this.btnUnlock.setVisibility(8);
    }

    private void calRunData() {
        int hadTime = this.sharepreference.getInt("durationTime", 0);
        long startT = this.sharepreference.getLong("onPause_time", System.currentTimeMillis());
        int dru = (int) (((System.currentTimeMillis() - startT) - this.sharepreference.getLong("change_time", 0)) / 1000);
        int operation_state = this.sharepreference.getInt("map_activity_state", 0);
        this.durationTime = hadTime + dru;
        if (operation_state != 0) {
            long gpsid = this.sharepreference.getLong("gps_sport_id", -1);
            if (gpsid != -1) {
                GpsSportDataModel mGpsInfo = this.mDataBaseUtil.selectGpsInfoForID(gpsid);
                if (this.sumRunDis < mGpsInfo.getTotalDistance()) {
                    this.sumRunDis = mGpsInfo.getTotalDistance();
                }
                if (this.sumStep < mGpsInfo.getSteps()) {
                    this.sumStep = mGpsInfo.getSteps();
                }
            }
        }
        this.calories = Tools.calcCalories(Tools.calcDistance(this.sumStep, this.config.getHeight()), this.config.getWeightNum());
        if (operation_state == 3) {
            this.Countdown = this.sharepreference.getInt("countdown", 900) - ((int) ((System.currentTimeMillis() - this.sharepreference.getLong("countdown_init", System.currentTimeMillis())) / 1000));
            if (this.Countdown <= 0) {
                this.Countdown = 900;
            }
            this.mTVCountdown.setText(intformat.format(Integer.valueOf(this.Countdown / 60)) + this.res.getString(R.string.gps_minute) + intformat.format(Integer.valueOf(this.Countdown % 60)) + this.res.getString(R.string.gps_second));
        }
        if (this.durationTime != 0) {
            this.aveSpeed = this.sumRunDis / ((double) this.durationTime);
        } else {
            this.aveSpeed = 0.0d;
        }
        this.mTVDDisSpeed.setText(decFormat.format(Double.valueOf(this.aveSpeed * 3.6d)));
        this.mTVDStep.setText("" + this.sumStep);
        this.mTVSStep.setText("" + this.sumStep);
        this.mTVDTimer.setText(formatTimer((long) this.durationTime));
        this.mTVSTimer.setText(formatTimer((long) this.durationTime));
        this.mTVDDistance.setText(decFormat.format(Double.valueOf(this.sumRunDis / 1000.0d)));
        this.mTVSDistance.setText(decFormat.format(Double.valueOf(this.sumRunDis / 1000.0d)));
        this.mTVDCalories.setText("" + this.calories);
    }

    public void initDatabaseline() {
        List<GuidePointModel> smallistPoint = new ArrayList();
        List<GuidePointModel> smallistPoint2 = new ArrayList();
        if (this.startTime != 0) {
            GuidePointModel firstPoint = this.mDataBaseUtil.selectFirstPoint(this.startTime, conversTime());
            if (firstPoint.getLongitude() != 0.0d) {
                List<GuidePointModel> sublistPoint = this.mDataBaseUtil.selectPoint(firstPoint.getTime(), conversTime(), 0);
                if (sublistPoint.size() <= 0) {
                    return;
                }
                List<GuidePointModel> handlistPoint;
                GuidePointModel beginPoint;
                if (sublistPoint.size() == 1) {
                    smallistPoint = this.mDataBaseUtil.selectPoint(((GuidePointModel) sublistPoint.get(0)).getTime(), conversTime());
                    handlistPoint = GpsUtil.handlePoint(smallistPoint);
                    handlistPoint.add(smallistPoint.get(smallistPoint.size() - 1));
                    beginPoint = (GuidePointModel) handlistPoint.get(0);
                    beginPoint.setTime(((GuidePointModel) smallistPoint.get(0)).getTime());
                    hanlderMarker(beginPoint, 1);
                    initLine(handlistPoint, false);
                    return;
                }
                int i = 0;
                while (i < sublistPoint.size()) {
                    if (i > 0 && (((GuidePointModel) sublistPoint.get(i)).getPointState() == 3 || ((GuidePointModel) sublistPoint.get(i)).getPointState() == 5 || ((GuidePointModel) sublistPoint.get(i)).getPointState() == 6)) {
                        smallistPoint = this.mDataBaseUtil.selectPoint(((GuidePointModel) sublistPoint.get(i - 1)).getTime(), ((GuidePointModel) sublistPoint.get(i)).getTime());
                        if (smallistPoint.size() == 2) {
                            initLine(smallistPoint, false);
                        } else if (smallistPoint.size() > 2) {
                            handlistPoint = GpsUtil.handlePoint(this.mDataBaseUtil.selectPoint(((GuidePointModel) sublistPoint.get(i - 1)).getTime(), ((GuidePointModel) sublistPoint.get(i)).getTime() - 1));
                            initLine(handlistPoint, false);
                            if (i == sublistPoint.size() - 1) {
                                smallistPoint2 = this.mDataBaseUtil.selectPoint(((GuidePointModel) sublistPoint.get(i)).getTime(), conversTime());
                            } else {
                                smallistPoint2 = this.mDataBaseUtil.selectPoint(((GuidePointModel) sublistPoint.get(i)).getTime(), ((GuidePointModel) sublistPoint.get(i + 1)).getTime() - 1);
                            }
                            List<GuidePointModel> currentlistPoint = new ArrayList();
                            currentlistPoint.add(handlistPoint.get(handlistPoint.size() - 1));
                            currentlistPoint.add(GpsUtil.handlePoint(smallistPoint2).get(0));
                            initLine(currentlistPoint, false);
                        }
                    }
                    if (i == 1) {
                        smallistPoint = this.mDataBaseUtil.selectPoint(((GuidePointModel) sublistPoint.get(0)).getTime(), ((GuidePointModel) sublistPoint.get(1)).getTime());
                        beginPoint = (GuidePointModel) GpsUtil.handlePoint(smallistPoint).get(0);
                        beginPoint.setTime(((GuidePointModel) smallistPoint.get(0)).getTime());
                        hanlderMarker(beginPoint, 1);
                    }
                    if (i == sublistPoint.size() - 1) {
                        smallistPoint = this.mDataBaseUtil.selectPoint(((GuidePointModel) sublistPoint.get(i)).getTime(), conversTime());
                        handlistPoint = GpsUtil.handlePoint(smallistPoint);
                        handlistPoint.add(smallistPoint.get(smallistPoint.size() - 1));
                        if (handlistPoint.size() > 1) {
                            initLine(handlistPoint, false);
                        }
                    }
                    i++;
                }
            }
        }
    }

    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        this.mapView.onSaveInstanceState(outState);
        Log.i(tag, "onSaveInstanceState");
    }

    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Log.i(tag, "onRestoreInstanceState");
    }

    protected void onPause() {
        super.onPause();
        this.mapView.onPause();
        this.sensorManager.unregisterListener(this);
        if (this.pendingIntent != null) {
            this.mAlarm.cancel(this.pendingIntent);
        }
        saveRunData();
        if (isFinishing()) {
            MonitorWatcher.getInstance().removeWatcher(this.mPointOberver);
            SignalWatcher.getInstance().removeWatcher(this.mSignalObserver);
            StepWatcher.getInstance().removeWatcher(this.mStepObserver);
            unregisterReceiver(this.mBroadcastReceiver);
        }
    }

    private void saveRunData() {
        this.isViewOnTop = false;
        Editor edit = this.sharepreference.edit();
        int operation_state = this.sharepreference.getInt("map_activity_state", 0);
        if (operation_state != 0) {
            edit.putInt("durationTime", this.durationTime);
            if (GaodeService.is_running) {
                edit.putLong("onPause_time", System.currentTimeMillis());
                edit.putLong("change_time", 0);
            } else {
                edit.remove("onPause_time");
            }
        } else {
            edit.putInt("durationTime", this.durationTime);
            edit.remove("onPause_time");
        }
        if (operation_state == 3) {
            edit.putInt("countdown", this.Countdown);
            edit.putLong("countdown_init", System.currentTimeMillis());
        } else {
            edit.remove("countdown");
            edit.remove("countdown_init");
        }
        edit.commit();
    }

    protected void onDestroy() {
        super.onDestroy();
        if (this.sharepreference.getInt("map_activity_state", 0) < 2 && !Tools.getPhonePedState()) {
            stopService(new Intent(getApplicationContext(), PedometerService.class));
        }
        this.mServiceUtil.uninitFilter();
        this.mapView.onDestroy();
        if (!this.isStartService) {
            this.mServiceUtil.closeGaodeService();
        }
        unRegisterBc();
    }

    private void openGPSSettings() {
        if (!((LocationManager) getSystemService("location")).isProviderEnabled("gps")) {
            dialogShow(1);
        }
    }

    public boolean hasGPSDevice(Context context) {
        LocationManager mgr = (LocationManager) context.getSystemService("location");
        if (mgr == null) {
            return false;
        }
        List<String> providers = mgr.getAllProviders();
        if (providers != null) {
            return providers.contains("gps");
        }
        return false;
    }

    private boolean isGpsuseful() {
        return ((LocationManager) getSystemService("location")).isProviderEnabled("gps");
    }

    public void onTouch(MotionEvent event) {
        switch (event.getAction()) {
            case 0:
                if (this.pendingIntent != null) {
                    this.mAlarm.cancel(this.pendingIntent);
                    return;
                }
                return;
            case 1:
                timeManage();
                return;
            default:
                return;
        }
    }

    public void onClick(View v) {
        if (this.unLockHandler.hasMessages(5)) {
            this.unLockHandler.removeMessages(5);
        }
        switch (v.getId()) {
            case R.id.back:
                onBackPressed();
                return;
            case R.id.gps_igbtn_zoom:
                zoomSwitch();
                return;
            case R.id.gps_btn_start:
                new GPSShowDialog(this).show();
                timeManage();
                startRun();
                return;
            case R.id.gps_btn_finish:
                if (this.pendingIntent != null) {
                    this.mAlarm.cancel(this.pendingIntent);
                }
                finishRun();
                return;
            case R.id.gps_btn_supstart:
                int operation_state = this.sharepreference.getInt("map_activity_state", 0);
                if (GaodeService.is_running) {
                    if (this.pendingIntent != null) {
                        this.mAlarm.cancel(this.pendingIntent);
                    }
                    stopRun();
                    return;
                }
                timeManage();
                reStartRun();
                return;
            default:
                return;
        }
    }

    private void removeData() {
        Editor edit = this.sharepreference.edit();
        edit.putInt("save_service_step", 0);
        edit.putFloat("save_service_distance", 0.0f);
        edit.commit();
    }

    private void compeleGpsSport() {
        this.isCompleteGps = true;
        GaodeService.point_state = 0;
        is_line = false;
        GaodeService.is_running = false;
        removeData();
        if (!TextUtils.isEmpty(GaodeService.tempDataModel.getTempImageUrl())) {
            File mFile = new File(GaodeService.tempDataModel.getTempImageUrl());
            if (mFile.exists()) {
                mFile.delete();
            }
        }
        sendBroadcast(new Intent("ACTION_COMPELE_GPSSPORT"));
        handlerMap();
        OperationTimeModel mOperation2 = new OperationTimeModel();
        mOperation2.setOperatId(Tools.getPKL());
        mOperation2.setOperationtime(conversTime());
        mOperation2.setOperationSystime(System.currentTimeMillis());
        mOperation2.setOperationState(4);
        mOperation2.setSyncState(0);
        this.mDataBaseUtil.insertOperation(mOperation2);
    }

    private void handlerMap() {
        if (this.mGuidePoint != null) {
            hanlderMarker(this.mGuidePoint, 2);
        }
        if (this.mMark != null) {
            this.mMark.remove();
        }
        this.mHandler.sendEmptyMessageDelayed(2001, 600);
    }

    public void getMapScreenShot() {
        this.aMap.getMapScreenShot(this);
    }

    public void onMapScreenShot(Bitmap bitmap) {
        this.fileName = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + ".jpg";
        savegpsInfo();
        if (bitmap != null) {
            Tools.saveGpsBitmapToFile(bitmap, this.fileName);
            bitmap.recycle();
        }
    }

    public void onMapScreenShot(Bitmap bitmap, int i) {
    }

    public void LoadedMaptoview() {
        double[] points = this.mDataBaseUtil.findGpsBound(this.startTime, conversTime());
        if (points != null) {
            LatLng startGuide = new LatLng(points[0], points[1]);
            this.aMap.moveCamera(CameraUpdateFactory.newLatLngBounds(new LatLngBounds.Builder().include(startGuide).include(new LatLng(points[2], points[3])).build(), 50));
        }
        this.mHandler.sendEmptyMessageDelayed(2000, 1000);
    }

    private void delteGpsSport() {
        deletefromDatabase();
        this.editor = this.sharepreference.edit();
        this.editor.putInt("map_activity_state", 0);
        this.editor.putBoolean("is_begin_point", true);
        if (this.sharepreference.contains("is_have_beginaddr")) {
            this.editor.remove("is_have_beginaddr");
        }
        if (this.sharepreference.contains("gps_sport_id")) {
            this.editor.remove("gps_sport_id");
        }
        this.editor.commit();
        is_line = false;
        GaodeService.is_running = false;
        initViewState();
        this.aMap.clear();
        removeData();
    }

    private void deletefromDatabase() {
        int i;
        List<Integer> listGpsId = this.mDataBaseUtil.selectPointID(this.startTime, conversTime(), 0);
        List<Integer> listOperationId = this.mDataBaseUtil.selectOperationId(this.startTime, conversTime(), 0);
        if (listGpsId.size() > 0) {
            for (i = 0; i < listGpsId.size(); i++) {
                ContentValues runningItem = new ContentValues();
                runningItem.put(DataBaseContants.GPS_TABLE, DataBaseContants.TABLE_POINT_NAME);
                runningItem.put(DataBaseContants.GPS_DELETE, (Integer) listGpsId.get(i));
                getContentResolver().insert(DataBaseContants.CONTENT_URI_GPSSYNC, runningItem);
            }
        }
        if (listOperationId.size() > 0) {
            for (i = 0; i < listOperationId.size(); i++) {
                runningItem = new ContentValues();
                runningItem.put(DataBaseContants.GPS_TABLE, DataBaseContants.TABLE_OPERATION_NAME);
                runningItem.put(DataBaseContants.GPS_DELETE, (Integer) listOperationId.get(i));
                getContentResolver().insert(DataBaseContants.CONTENT_URI_GPSSYNC, runningItem);
            }
        }
        if (this.sharepreference.contains("gps_sport_id")) {
            this.mDataBaseUtil.deleteGpsFromID(this.sharepreference.getLong("gps_sport_id", -1));
        }
        this.mDataBaseUtil.deleteOperation(this.startTime, conversTime());
        this.mDataBaseUtil.deletePoint(this.startTime, conversTime());
    }

    private void savegpsInfo() {
        long startSysTime = this.mDataBaseUtil.selectOperSysTime(1);
        GaodeService.gpsSportInfo.setGpsId(this.sharepreference.getLong("gps_sport_id", 0));
        GaodeService.gpsSportInfo.setStarttime(this.startTime);
        GaodeService.gpsSportInfo.setEndtime(conversTime());
        GaodeService.gpsSportInfo.setCalorie((double) this.calories);
        GaodeService.gpsSportInfo.setStarSysttime(startSysTime);
        GaodeService.gpsSportInfo.setEndSystime(System.currentTimeMillis());
        GaodeService.gpsSportInfo.setDurationtime((long) this.durationTime);
        GaodeService.gpsSportInfo.setTotalDistance(this.sumRunDis);
        GaodeService.gpsSportInfo.setAvespeed(this.aveSpeed);
        GaodeService.gpsSportInfo.setSteps(this.sumStep);
        GaodeService.gpsSportInfo.setHeartCount(this.heart_tvde_distance.getText().toString());
        Log.i("lsj", this.heart_tvde_distance.getText().toString());
        String maddress = this.sharepreference.getString("is_have_beginaddr", this.no_address);
        if (TextUtils.isEmpty(maddress)) {
            GaodeService.gpsSportInfo.setStartAddress(this.no_address);
        } else {
            GaodeService.gpsSportInfo.setStartAddress(maddress);
        }
        if (TextUtils.isEmpty(GaodeService.endAddress)) {
            GaodeService.gpsSportInfo.setEndAddress(this.no_address);
        } else {
            GaodeService.gpsSportInfo.setEndAddress(GaodeService.endAddress);
        }
        GaodeService.gpsSportInfo.setSyncState(0);
        if (this.isCompleteGps) {
            this.editor = this.sharepreference.edit();
            this.editor.putInt("map_activity_state", 0);
            this.editor.putInt("gps_singal_state", 0);
            this.editor.putBoolean("is_begin_point", true);
            if (this.sharepreference.contains("is_have_beginaddr")) {
                this.editor.remove("is_have_beginaddr");
            }
            if (this.sharepreference.contains("gps_sport_id")) {
                this.editor.remove("gps_sport_id");
            }
            this.editor.commit();
            this.mDataBaseUtil.updateGpsInfo(GaodeService.gpsSportInfo);
        }
        insertDataBaseSportType(GaodeService.gpsSportInfo);
        closeHeartRate();
    }

    public String getSDPath() {
        if (Environment.getExternalStorageState().equals("mounted")) {
            return Environment.getExternalStorageDirectory().toString();
        }
        return null;
    }

    private void insertDataBaseSportType(GpsSportDataModel gpsSportInfo) {
        String beginAddress;
        String endAddress;
        if (TextUtils.isEmpty(gpsSportInfo.getStartAddress())) {
            beginAddress = this.no_address;
        } else {
            beginAddress = gpsSportInfo.getStartAddress();
        }
        if (TextUtils.isEmpty(GaodeService.endAddress)) {
            endAddress = this.no_address;
        } else {
            endAddress = GaodeService.endAddress;
        }
        String gpsfilePath = getSDPath() + "/Running/gps/" + this.fileName;
        String mTime = Long.toString(gpsSportInfo.getStarttime());
        String curr_date = mTime.substring(0, 4) + SocializeConstants.OP_DIVIDER_MINUS + mTime.substring(4, 6) + SocializeConstants.OP_DIVIDER_MINUS + mTime.substring(6, 8);
        String startTime = mTime.substring(8, 10) + ":" + mTime.substring(10, 12);
        String mduration = Double.toString(Math.rint((double) (this.durationTime / 60)));
        String endTime = hanlderEndTime(gpsSportInfo);
        String heart_data = this.heart_tvde_distance.getText().toString();
        if (this.isCompleteGps) {
            RunningItem runningItem = new RunningItem();
            runningItem.setDate(curr_date);
            runningItem.setStartTime(startTime);
            runningItem.setDuration(mduration);
            runningItem.setEndTime(endTime);
            runningItem.setCalories(this.calories);
            runningItem.setSteps(this.sumStep);
            runningItem.setKilometer((int) this.sumRunDis);
            runningItem.setmType(5);
            runningItem.setmWeight(beginAddress);
            runningItem.setmBmi(endAddress);
            runningItem.setmImgUri(gpsfilePath);
            runningItem.setmExplain(Long.toString(gpsSportInfo.getGpsId()));
            runningItem.setHeart_rate_count(heart_data);
            Intent gpsIntent = new Intent("ACTION_GPS_INFO");
            gpsIntent.putExtra("gps_info", runningItem);
            sendBroadcast(gpsIntent);
            finish();
            return;
        }
        if (this.endMark != null) {
            this.endMark.remove();
        }
        GaodeService.tempDataModel.setTempId(Tools.getPKL());
        GaodeService.tempDataModel.setTempDate(curr_date);
        GaodeService.tempDataModel.setTempStaTime(startTime);
        GaodeService.tempDataModel.setTempDuration(mduration);
        GaodeService.tempDataModel.setTempEndTime(endTime);
        GaodeService.tempDataModel.setTempCalories(this.calories);
        GaodeService.tempDataModel.setTempStep(this.sumStep);
        GaodeService.tempDataModel.setTempDistance(this.sumRunDis);
        GaodeService.tempDataModel.setTempType(5);
        GaodeService.tempDataModel.setTempStatistics(0);
        GaodeService.tempDataModel.setTempState(0);
        GaodeService.tempDataModel.setTempStaAddress(beginAddress);
        GaodeService.tempDataModel.setTempEndAddress(endAddress);
        GaodeService.tempDataModel.setTempImageUrl(gpsfilePath);
        GaodeService.tempDataModel.setTempGpsId(gpsSportInfo.getGpsId());
        GaodeService.tempDataModel.setTempHeartRate(heart_data);
    }

    private String hanlderEndTime(GpsSportDataModel gpsSportInfo) {
        long durSys = (gpsSportInfo.getEndSystime() / 1000) - (gpsSportInfo.getStarSysttime() / 1000);
        String mTime = Long.toString(gpsSportInfo.getStarttime());
        String mTime2 = Long.toString(gpsSportInfo.getEndtime());
        long disMin = 86400 - ((long) (((Integer.parseInt(mTime.substring(8, 10)) * 3600) + (Integer.parseInt(mTime.substring(10, 12)) * 60)) + Integer.parseInt(mTime.substring(12, 14))));
        if (durSys < disMin) {
            return mTime2.substring(8, 10) + ":" + mTime2.substring(10, 12);
        }
        return String.valueOf(((long) Integer.parseInt(mTime2.substring(8, 10))) + (24 * (((durSys - disMin) / 86400) + 1))) + ":" + mTime2.substring(10, 12);
    }

    private void getLanguageEnv() {
        if ("zh".equals(Locale.getDefault().getLanguage())) {
            this.is_china = true;
        } else {
            this.is_china = false;
        }
    }

    public static long conversTime() {
        Calendar mCal = Calendar.getInstance();
        int year = mCal.get(1);
        int month = mCal.get(2) + 1;
        int days = mCal.get(5);
        int hour = mCal.get(11);
        return ((((((long) mCal.get(13)) + (((long) mCal.get(12)) * 100)) + (((long) hour) * 10000)) + (((long) days) * 1000000)) + (((long) month) * 100000000)) + (((long) year) * 10000000000L);
    }

    private void initViewState() {
        this.mBtnStart.setVisibility(0);
        this.mLayoutSupStart.setVisibility(8);
    }

    private void beginViewState() {
        this.mBtnStart.setVisibility(8);
        this.mLayoutSupStart.setVisibility(0);
        this.mBtnSupStart.setText(this.res.getString(R.string.gps_stop));
    }

    private void continueViewState() {
        this.mBtnStart.setVisibility(8);
        this.mLayoutSupStart.setVisibility(0);
        this.mBtnSupStart.setText(this.res.getString(R.string.gps_stop));
    }

    private void stopViewState() {
        this.mBtnStart.setVisibility(8);
        this.mLayoutSupStart.setVisibility(0);
        this.mBtnSupStart.setText(this.res.getString(R.string.gps_continue));
    }

    private void startRun() {
        Log.i(tag, "startRun");
        initRunData();
        if (!(isNetuseful() || isGpsuseful())) {
            Toast.makeText(this, this.res.getString(R.string.gps_GPSisSearching), 0).show();
        }
        GaodeService.gpsSportInfo.clearData();
        GaodeService.tempDataModel.clearData();
        GaodeService.baseStep = 0;
        GaodeService.baseDistance = 0.0d;
        this.startTime = conversTime();
        beginViewState();
        is_line = true;
        GaodeService.is_running = true;
        this.begin_point_state = 1;
        this.editor = this.sharepreference.edit();
        this.editor.putInt("map_activity_state", 2);
        this.editor.commit();
        sendBroadcast(new Intent("ACTION_BEGIN_GPSSPORT"));
        GaodeService.point_state = 1;
        OperationTimeModel mOperation = new OperationTimeModel();
        mOperation.setOperatId(Tools.getPKL());
        mOperation.setOperationtime(conversTime());
        mOperation.setOperationSystime(System.currentTimeMillis());
        mOperation.setOperationState(1);
        mOperation.setSyncState(0);
        this.mDataBaseUtil.insertOperation(mOperation);
        GpsSportDataModel mSportModel = new GpsSportDataModel();
        long sportId = Tools.getPKL();
        this.editor = this.sharepreference.edit();
        this.editor.putLong("gps_sport_id", sportId);
        this.editor.commit();
        mSportModel.setGpsId(sportId);
        mSportModel.setStarSysttime(System.currentTimeMillis());
        mSportModel.setStarttime(conversTime());
        this.mDataBaseUtil.insertGpsInfo(mSportModel);
    }

    private void stopRun() {
        GaodeService.mdistace = this.sumRunDis;
        GaodeService.is_running = false;
        is_line = false;
        this.editor = this.sharepreference.edit();
        this.editor.putInt("map_activity_state", 3);
        this.editor.commit();
        sendBroadcast(new Intent("ACTION_STOP_GPSSPORT"));
        stopViewState();
        GaodeService.point_state = 2;
        this.mTaskState = 2;
        new InitDataTask().execute(new String[0]);
        OperationTimeModel mOperation = new OperationTimeModel();
        mOperation.setOperatId(Tools.getPKL());
        mOperation.setOperationtime(conversTime());
        mOperation.setOperationSystime(System.currentTimeMillis());
        mOperation.setOperationState(2);
        mOperation.setSyncState(0);
        this.mDataBaseUtil.insertOperation(mOperation);
        closeHeartRate();
    }

    private void reStartRun() {
        is_line = true;
        GaodeService.is_running = true;
        continueViewState();
        if (!TextUtils.isEmpty(GaodeService.tempDataModel.getTempImageUrl())) {
            File mFile = new File(GaodeService.tempDataModel.getTempImageUrl());
            if (mFile.exists()) {
                mFile.delete();
            }
        }
        GaodeService.gpsSportInfo.clearData();
        GaodeService.tempDataModel.clearData();
        this.editor = this.sharepreference.edit();
        this.editor.putInt("map_activity_state", 4);
        this.editor.commit();
        sendBroadcast(new Intent("ACTION_CONTINUE_GPSSPORT"));
        GaodeService.point_state = 3;
        OperationTimeModel mOperation2 = new OperationTimeModel();
        mOperation2.setOperatId(Tools.getPKL());
        mOperation2.setOperationtime(conversTime());
        mOperation2.setOperationSystime(System.currentTimeMillis());
        mOperation2.setOperationState(3);
        mOperation2.setSyncState(0);
        this.mDataBaseUtil.insertOperation(mOperation2);
        openHeartRate();
    }

    private void finishRun() {
        if (this.sumRunDis < 100.0d) {
            dialogShow(3);
            return;
        }
        this.mTaskState = 3;
        new InitDataTask().execute(new String[0]);
    }

    private void zoomSwitch() {
        if (this.isZoomall) {
            this.mLayoutDetailData.setVisibility(8);
            this.mLayoutShortData.setVisibility(0);
            this.mLayoutOpeartion.setVisibility(8);
            this.mBtnZoom.setImageResource(R.drawable.gps_zoomin);
            this.isZoomall = false;
            return;
        }
        this.mLayoutDetailData.setVisibility(0);
        this.mLayoutShortData.setVisibility(8);
        this.mLayoutOpeartion.setVisibility(0);
        this.mBtnZoom.setImageResource(R.drawable.gps_zoomout);
        this.isZoomall = true;
    }

    private void dialogShow(int policy) {
        int res_title = 0;
        int res_msg = 0;
        int btn_pos = 0;
        int btn_neg = 0;
        if (policy == 0) {
            res_title = R.string.gps_notify;
            res_msg = R.string.gps_activityToBack;
            btn_pos = R.string.gps_noNotify;
            btn_neg = R.string.gps_yes;
        } else if (policy == 1) {
            res_title = R.string.gps_notify;
            res_msg = R.string.gps_dialog_needGPS;
            btn_pos = R.string.gps_no;
            btn_neg = R.string.gps_dialog_openGPS;
        } else if (policy == 2) {
            res_title = R.string.gps_notify;
            res_msg = R.string.gps_dis_short;
            btn_pos = R.string.gps_no;
            btn_neg = R.string.gps_exit;
        } else if (policy == 3) {
            res_title = R.string.gps_notify;
            res_msg = R.string.gps_dis_short2;
            btn_pos = R.string.gps_no1;
            btn_neg = R.string.gps_exit;
        } else if (policy == 4) {
            res_title = R.string.gps_notify;
            res_msg = R.string.gps_guide_remind;
            btn_pos = R.string.gps_yes;
            btn_neg = R.string.gps_no;
        }
        Builder builder = new Builder(this);
        builder.setCancelable(false);
        builder.setTitle(res_title);
        builder.setMessage(res_msg);
        if (policy == 0) {
            builder.setPositiveButton(btn_pos, new C12637());
            builder.setNegativeButton(btn_neg, new C12648());
        } else if (policy == 1) {
            builder.setPositiveButton(btn_pos, new C12659());
            builder.setNegativeButton(btn_neg, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    Toast.makeText(GaoDeMapActivity.this, GaoDeMapActivity.this.res.getString(R.string.gps_openGPS), 0).show();
                    GaoDeMapActivity.this.startActivityForResult(new Intent("android.settings.LOCATION_SOURCE_SETTINGS"), 0);
                    dialog.dismiss();
                }
            });
        } else if (policy == 2) {
            builder.setPositiveButton(btn_pos, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            builder.setNegativeButton(btn_neg, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    GaoDeMapActivity.this.delteGpsSport();
                    dialog.dismiss();
                    GaoDeMapActivity.this.initRunData();
                    GaoDeMapActivity.this.closeHeartRate();
                }
            });
        } else if (policy == 3) {
            builder.setPositiveButton(btn_pos, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    GaoDeMapActivity.this.mTaskState = 3;
                    new InitDataTask().execute(new String[0]);
                    dialog.dismiss();
                    GaoDeMapActivity.isStartHeart = false;
                    GaoDeMapActivity.this.isStartService = false;
                }
            });
            builder.setNegativeButton(btn_neg, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    GaoDeMapActivity.this.delteGpsSport();
                    dialog.dismiss();
                    GaoDeMapActivity.this.initRunData();
                    GaoDeMapActivity.this.closeHeartRate();
                    GaoDeMapActivity.this.unLocked = true;
                    GaoDeMapActivity.this.unLockHandler.removeMessages(5);
                    GaoDeMapActivity.this.unLockHandler.removeMessages(292);
                    GaoDeMapActivity.this.isStartService = false;
                }
            });
        } else if (policy == 4) {
            builder.setPositiveButton(btn_pos, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    if (GaoDeMapActivity.this.sharepreference.getBoolean(GaoDeMapActivity.NEVER_SHOW_LOG, false) || !GaodeService.is_running) {
                        GaoDeMapActivity.this.finish();
                    } else {
                        GaoDeMapActivity.this.dialogShow(0);
                    }
                    GaoDeMapActivity.this.closeHeartRate();
                }
            });
            builder.setNegativeButton(btn_neg, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
        }
        builder.create().show();
    }

    public void onBackPressed() {
        if (!this.unLocked) {
            return;
        }
        if (this.sharepreference.getBoolean(NEVER_SHOW_LOG, false) || !GaodeService.is_running) {
            super.onBackPressed();
        } else {
            dialogShow(0);
        }
    }

    private void openHeartRate() {
        String name = Util.getDeviceName();
        if (name.equals("Rumor-2")) {
            PXIALGMOTION.Close();
            sendBroadcast(new Intent(BleManagerService.ACTION_HEART_DATA_READ));
        } else if (name.equals("M2")) {
            Tools.makeToast("该设备不支持动态心率");
            this.heart_tvde_distance.setText("-- ");
            Log.i("lsj", "start");
        }
    }

    private void closeHeartRate() {
        if (Util.getDeviceName().equals("Rumor-2")) {
            PXIALGMOTION.Close();
            sendBroadcast(new Intent(BleManagerService.ACTION_DISABLE_HEART_INFO));
        } else {
            Log.i("lsj", "close");
        }
        isStartHeart = false;
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
}
