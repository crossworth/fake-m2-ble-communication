package com.zhuoyou.plugin.gps;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.Resources;
import android.location.GpsSatellite;
import android.location.GpsStatus;
import android.location.GpsStatus.Listener;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.location.LocationManagerProxy;
import com.amap.api.location.LocationProviderProxy;
import com.amap.api.maps.AMap;
import com.amap.api.maps.AMapUtils;
import com.amap.api.maps.LocationSource;
import com.amap.api.maps.LocationSource.OnLocationChangedListener;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.LatLng;
import com.fithealth.running.R;
import com.mcube.lib.ped.PedometerService;
import com.tencent.open.yyb.TitleBar;
import com.zhuoyou.plugin.database.DataBaseContants;
import com.zhuoyou.plugin.database.DataBaseUtil;
import com.zhuoyou.plugin.download.Util_update.TimeManager;
import com.zhuoyou.plugin.gps.ilistener.IStepListener;
import com.zhuoyou.plugin.gps.ilistener.MonitorWatcher;
import com.zhuoyou.plugin.gps.ilistener.SignalWatcher;
import com.zhuoyou.plugin.gps.ilistener.StepWatcher;
import com.zhuoyou.plugin.running.RunningItem;
import com.zhuoyou.plugin.running.Tools;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class GaodeService extends Service implements LocationSource, AMapLocationListener {
    private static final int DELAY_NO_GPSSINGAL = 1000;
    private static final int FIRST_GPS_ADDRESS = 1003;
    private static final int GPS_STRENGTH_JUDGE = 1001;
    private static final int SAVE_GPS_INFO = 1002;
    public static double baseDistance = 0.0d;
    public static int baseStep = 0;
    public static String endAddress = "";
    public static int gpsSignal_state = 0;
    public static GpsSportDataModel gpsSportInfo = new GpsSportDataModel();
    public static int hadRunStep = 0;
    public static List<GuidePointModel> handlerList = new ArrayList();
    public static boolean is_running = false;
    public static double mSumDis = 0.0d;
    public static double mdistace = 0.0d;
    public static int point_state = 0;
    public static String startAddress = "";
    public static final String tag = "GaodeService";
    public static TempDataModel tempDataModel = new TempDataModel();
    private AMap aMap;
    private Handler adresHandler = new C12682();
    private Editor editor;
    private Handler firstHandler = new C12693();
    private GuidePointModel firstPoint;
    private SimpleDateFormat formatter;
    private long gpsSingalTime = 0;
    private long gpsSingal_is_change = 0;
    private int initRunStep;
    private boolean is_first_point;
    private LocationManager locationManager;
    private long locationTime = 0;
    private long location_is_change = 0;
    private LocationManagerProxy mAMapLocationManager;
    private final BroadcastReceiver mBroadcastReceiver = new C12715();
    private DataBaseUtil mDataBaseUtil;
    private Handler mHandler = new C12704();
    private OnLocationChangedListener mListener;
    private int mRunStep;
    public StepObserver mStepObserver;
    private MapView mapView;
    private String no_address;
    private List<GpsSatellite> numSatelliteList = new ArrayList();
    private Resources res;
    private int satelliteNum = 0;
    private SharedPreferences sharepreference;
    private final Listener statusListener = new C12671();
    private double sumRunDis;
    private GuidePointModel upPoint;
    private int usedcount = 0;

    class C12671 implements Listener {
        C12671() {
        }

        public void onGpsStatusChanged(int event) {
            GaodeService.this.updateGpsStatus(event, GaodeService.this.locationManager.getGpsStatus(null));
        }
    }

    class C12682 extends Handler {
        C12682() {
        }

        public void handleMessage(Message msg) {
            if (msg.what == 0) {
                GuidePointModel mPoint = msg.obj;
                if (mPoint != null) {
                    String addres = mPoint.getAddress();
                    if (!TextUtils.isEmpty(addres) && !GaodeService.endAddress.equals(addres)) {
                        GaodeService.endAddress = addres;
                    }
                }
            }
        }
    }

    class C12693 extends Handler {
        C12693() {
        }

        public void handleMessage(Message msg) {
            if (msg.what == 0) {
                GuidePointModel mPoint = msg.obj;
                if (mPoint != null) {
                    String addres = mPoint.getAddress();
                    if (TextUtils.isEmpty(addres)) {
                        GaodeService.this.mHandler.sendEmptyMessageDelayed(1003, 30000);
                        return;
                    }
                    GaodeService.startAddress = addres;
                    Editor edit = GaodeService.this.sharepreference.edit();
                    edit.putString("is_have_beginaddr", GaodeService.startAddress);
                    edit.commit();
                }
            }
        }
    }

    class C12704 extends Handler {
        C12704() {
        }

        public void handleMessage(Message msg) {
            Editor edit;
            switch (msg.what) {
                case 1000:
                    if (!(GaodeService.this.locationTime == 0 || GaodeService.this.location_is_change == GaodeService.this.locationTime || System.currentTimeMillis() - GaodeService.this.locationTime <= TimeManager.UNIT_MINUTE)) {
                        GaodeService.gpsSignal_state = 2;
                        edit = GaodeService.this.sharepreference.edit();
                        edit.putInt("gps_singal_state", 2);
                        edit.commit();
                        SignalWatcher.getInstance().notifyWatchers(GaodeService.gpsSignal_state);
                        GaodeService.this.location_is_change = GaodeService.this.locationTime;
                        GaodeService.point_state = 6;
                        OperationTimeModel mOperation = new OperationTimeModel();
                        mOperation.setOperatId(Tools.getPKL());
                        mOperation.setOperationtime(GaodeService.conversTime());
                        mOperation.setOperationSystime(System.currentTimeMillis());
                        mOperation.setOperationState(6);
                        mOperation.setSyncState(0);
                        GaodeService.this.mDataBaseUtil.insertOperation(mOperation);
                    }
                    GaodeService.this.mHandler.sendEmptyMessageDelayed(1000, 10000);
                    break;
                case 1001:
                    if (!(GaodeService.this.gpsSingalTime == 0 || GaodeService.this.gpsSingal_is_change == GaodeService.this.gpsSingalTime || System.currentTimeMillis() - GaodeService.this.gpsSingalTime <= TimeManager.UNIT_MINUTE)) {
                        GaodeService.gpsSignal_state = 2;
                        edit = GaodeService.this.sharepreference.edit();
                        edit.putInt("gps_singal_state", 2);
                        edit.commit();
                        SignalWatcher instance = SignalWatcher.getInstance();
                        GaodeService.gpsSignal_state = 2;
                        instance.notifyWatchers(2);
                        GaodeService.this.gpsSingal_is_change = GaodeService.this.gpsSingalTime;
                    }
                    GaodeService.this.mHandler.sendEmptyMessageDelayed(1001, 10000);
                    break;
                case 1002:
                    GaodeService.this.compeleGpsSport();
                    GaodeService.this.sendBroadcast(new Intent("ACTION_SAVE_GPSSPORT"));
                    GaodeService.this.insertTempData();
                    GaodeService.this.stopService(new Intent(GaodeService.this.getApplicationContext(), PedometerService.class));
                    GaodeService.this.stopSelf();
                    break;
                case 1003:
                    GpsUtil.getAdresByNet(GaodeService.this, GaodeService.this.firstPoint, GaodeService.this.firstHandler, 0);
                    break;
            }
            super.handleMessage(msg);
        }
    }

    class C12715 extends BroadcastReceiver {
        C12715() {
        }

        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if ("ACTION_STOP_GPSSPORT".equals(action)) {
                GaodeService.this.mHandler.removeMessages(1000);
                GaodeService.this.mHandler.sendEmptyMessageDelayed(1002, 898000);
            } else if ("ACTION_BEGIN_GPSSPORT".equals(action)) {
                initAllData();
                GaodeService.this.locationTime = System.currentTimeMillis();
                GaodeService.this.mHandler.sendEmptyMessageDelayed(1000, 10000);
            } else if ("ACTION_CONTINUE_GPSSPORT".equals(action)) {
                GaodeService.this.locationTime = System.currentTimeMillis();
                GaodeService.this.mHandler.removeMessages(1002);
                GaodeService.this.mHandler.sendEmptyMessageDelayed(1000, 10000);
            } else if ("ACTION_COMPELE_GPSSPORT".equals(action)) {
                initAllData();
                GaodeService.this.mHandler.removeMessages(1002);
                if (GaodeService.handlerList.size() > 0) {
                    if (GaodeService.handlerList.size() > 5) {
                        GaodeService.this.mDataBaseUtil.inserPoint(GaodeService.this.filterPoint(GaodeService.handlerList));
                    } else {
                        GaodeService.this.mDataBaseUtil.inserPoint(GaodeService.handlerList);
                    }
                    GaodeService.this.mDataBaseUtil.deleteTempPoint(0, GaodeService.conversTime());
                    GaodeService.handlerList.clear();
                }
            } else if ("com.zhuoyou.gaode.service.hello".equals(action)) {
                GaodeService.this.sendBroadcast(new Intent("com.zhuoyou.gaode.activity.hi"));
            }
        }

        private void initAllData() {
            GaodeService.mSumDis = 0.0d;
            GaodeService.hadRunStep = 0;
            GaodeService.this.mRunStep = 0;
            GaodeService.this.initRunStep = -1;
            GaodeService.this.sumRunDis = 0.0d;
            GaodeService.this.upPoint = null;
        }
    }

    class StepObserver implements IStepListener {
        StepObserver() {
        }

        public void onStepCount(int stepCount) {
            if (GaodeService.this.initRunStep <= 0 && stepCount >= GaodeService.this.mRunStep) {
                GaodeService.this.initRunStep = stepCount - GaodeService.this.mRunStep;
            }
            if (GaodeService.is_running) {
                GaodeService.this.mRunStep = stepCount - GaodeService.this.initRunStep;
                GaodeService.hadRunStep = GaodeService.this.mRunStep + GaodeService.baseStep;
                GaodeService.this.notifyStep();
                return;
            }
            GaodeService.this.initRunStep = stepCount - GaodeService.this.mRunStep;
        }

        public void onStateChanged(int newState) {
        }

        public void onHadRunStep(int hadRunStep) {
            long gpsid = GaodeService.this.sharepreference.getLong("gps_sport_id", -1);
            if (gpsid != -1) {
                GpsUtil.updateSport(GaodeService.this, Long.valueOf(gpsid), GaodeService.startAddress, GaodeService.endAddress, null, null, null, Integer.valueOf(hadRunStep), null);
            }
        }
    }

    public IBinder onBind(Intent intent) {
        return null;
    }

    private void updateGpsStatus(int event, GpsStatus status) {
        int maxSatellites = status.getMaxSatellites();
        Iterator<GpsSatellite> it = status.getSatellites().iterator();
        this.numSatelliteList.clear();
        while (it.hasNext() && this.satelliteNum <= maxSatellites) {
            GpsSatellite s = (GpsSatellite) it.next();
            this.numSatelliteList.add(s);
            if (s.usedInFix()) {
                this.usedcount++;
            }
            this.satelliteNum++;
        }
        String gps_state_show = "";
        switch (event) {
            case 1:
                gps_state_show = "STARTED";
                break;
            case 2:
                gps_state_show = "STOPPED";
                break;
            case 3:
                gps_state_show = "FIRST_FIX";
                break;
            case 4:
                gps_state_show = "SATELLITE_STATUS";
                break;
        }
        String gps_info = gps_state_show + this.usedcount + "/" + this.satelliteNum;
    }

    public void onCreate() {
        super.onCreate();
        this.res = getResources();
        this.sumRunDis = 0.0d;
        this.upPoint = null;
        this.is_first_point = true;
        this.mapView = new MapView(getApplicationContext());
        if (this.aMap == null) {
            this.aMap = this.mapView.getMap();
        }
        this.no_address = this.res.getString(R.string.gps_addressunknow);
        startAddress = this.no_address;
        endAddress = this.no_address;
        this.mDataBaseUtil = new DataBaseUtil(getApplicationContext());
        this.gpsSingalTime = System.currentTimeMillis();
        this.locationManager = (LocationManager) getSystemService("location");
        this.locationManager.addGpsStatusListener(this.statusListener);
        this.sharepreference = getSharedPreferences("gaode_location_info", 0);
        this.mHandler.sendEmptyMessageDelayed(1001, 10000);
        initFilter();
        setUpMap();
        this.mStepObserver = new StepObserver();
        StepWatcher.getInstance().addWatcher(this.mStepObserver);
    }

    public int onStartCommand(Intent intent, int flags, int startId) {
        List pointList = this.mDataBaseUtil.selectTempPoint(10);
        if (pointList.size() > 0) {
            if (pointList.size() > 5) {
                this.mDataBaseUtil.inserPoint(filterPoint(pointList));
            } else {
                this.mDataBaseUtil.inserPoint(pointList);
            }
            this.mDataBaseUtil.deleteTempPoint(0, conversTime());
            pointList.clear();
        }
        int operation_state = this.sharepreference.getInt("map_activity_state", 0);
        if (operation_state == 2 || operation_state == 4) {
            GaoDeMapActivity.is_line = true;
            is_running = true;
        } else {
            GaoDeMapActivity.is_line = true;
            is_running = false;
        }
        return super.onStartCommand(intent, flags, startId);
    }

    public void onDestroy() {
        super.onDestroy();
        if (handlerList.size() > 0) {
            if (handlerList.size() > 5) {
                this.mDataBaseUtil.inserPoint(filterPoint(handlerList));
            } else {
                this.mDataBaseUtil.inserPoint(handlerList);
            }
            this.mDataBaseUtil.deleteTempPoint(0, conversTime());
            handlerList.clear();
        }
        this.locationManager.removeGpsStatusListener(this.statusListener);
        deactivate();
        this.mapView.onDestroy();
        unregisterReceiver(this.mBroadcastReceiver);
        stopService(new Intent(getApplicationContext(), PedometerService.class));
        StepWatcher.getInstance().removeWatcher(this.mStepObserver);
        this.locationManager.removeUpdates(this);
    }

    public void onLocationChanged(AMapLocation amapLocation) {
        if (this.mListener != null && amapLocation != null && amapLocation != null && amapLocation.getAMapException().getErrorCode() == 0) {
            if ((amapLocation.getProvider() == "gps" || amapLocation.getProvider().equals("gps")) && gpsSignal_state != 1) {
                gpsSignal_state = 1;
                Editor edit = this.sharepreference.edit();
                edit.putInt("gps_singal_state", 1);
                edit.commit();
                this.gpsSingalTime = System.currentTimeMillis();
                SignalWatcher.getInstance().notifyWatchers(gpsSignal_state);
            }
            if (is_running && (amapLocation.getProvider() == "gps" || amapLocation.getProvider().equals("gps"))) {
                GuidePointModel tydguidePoint = initGuidePoint(amapLocation);
                if (this.is_first_point) {
                    boolean is_have = this.sharepreference.contains("is_have_beginaddr");
                    startAddress = this.sharepreference.getString("is_have_beginaddr", this.no_address);
                    this.firstPoint = tydguidePoint;
                    this.is_first_point = false;
                    if (!is_have) {
                        GpsUtil.getAdresByNet(this, this.firstPoint, this.firstHandler, 0);
                    }
                }
                GpsUtil.getAdresByNet(this, tydguidePoint, this.adresHandler, 0);
                this.locationTime = System.currentTimeMillis();
                if (tydguidePoint.getPointState() != 0) {
                    calSumDistance(tydguidePoint);
                    MonitorWatcher.getInstance().notifyWatchers(tydguidePoint);
                    if (handlerList.size() != 0) {
                        if (handlerList.size() < 5) {
                            this.mDataBaseUtil.inserPoint(handlerList);
                        } else {
                            this.mDataBaseUtil.inserPoint(filterPoint(handlerList));
                        }
                        this.mDataBaseUtil.deleteTempPoint(0, conversTime());
                    }
                    handlerList.clear();
                    this.mDataBaseUtil.inserPoint(tydguidePoint);
                } else if (tydguidePoint.getAccuracy() < 15.0f && calSumDistance(tydguidePoint) > 2.0d) {
                    MonitorWatcher.getInstance().notifyWatchers(tydguidePoint);
                    this.mDataBaseUtil.inserTempPoint(tydguidePoint);
                    handlerList.add(tydguidePoint);
                    if (handlerList.size() == 10) {
                        this.mDataBaseUtil.inserPoint(filterPoint(handlerList));
                        this.mDataBaseUtil.deleteTempPoint(0, conversTime());
                        handlerList.clear();
                    }
                }
            }
            if (this.sharepreference.getBoolean("is_begin_point", true)) {
                MonitorWatcher.getInstance().notifyWatchers(initGuidePoint(amapLocation));
            }
        }
    }

    private List<GuidePointModel> filterPoint(List<GuidePointModel> listPoint) {
        int i;
        double filterSpeed;
        List<GuidePointModel> filterList = new ArrayList();
        double lastLatitude = ((((GuidePointModel) listPoint.get(listPoint.size() - 3)).getLatitude() + ((GuidePointModel) listPoint.get(listPoint.size() - 2)).getLatitude()) + ((GuidePointModel) listPoint.get(listPoint.size() - 1)).getLatitude()) / 3.0d;
        double lastLongitude = ((((GuidePointModel) listPoint.get(listPoint.size() - 3)).getLongitude() + ((GuidePointModel) listPoint.get(listPoint.size() - 2)).getLongitude()) + ((GuidePointModel) listPoint.get(listPoint.size() - 1)).getLongitude()) / 3.0d;
        GuidePointModel firstPoint = new GuidePointModel(((((GuidePointModel) listPoint.get(0)).getLatitude() + ((GuidePointModel) listPoint.get(1)).getLatitude()) + ((GuidePointModel) listPoint.get(2)).getLatitude()) / 3.0d, ((((GuidePointModel) listPoint.get(0)).getLongitude() + ((GuidePointModel) listPoint.get(1)).getLongitude()) + ((GuidePointModel) listPoint.get(2)).getLongitude()) / 3.0d);
        GuidePointModel lastPoint = new GuidePointModel(lastLatitude, lastLongitude);
        List<Double> listSpeed = new ArrayList();
        for (i = 0; i < listPoint.size(); i++) {
            if (i == 0) {
                listSpeed.add(Double.valueOf((1000.0d * pointDistance(firstPoint, (GuidePointModel) listPoint.get(0), (GuidePointModel) listPoint.get(1))) / ((double) (((((GuidePointModel) listPoint.get(2)).getSysTime() - ((GuidePointModel) listPoint.get(0)).getSysTime()) / 2) + (((GuidePointModel) listPoint.get(1)).getSysTime() - ((GuidePointModel) listPoint.get(0)).getSysTime())))));
            } else if (i == listPoint.size() - 1) {
                listSpeed.add(Double.valueOf((1000.0d * pointDistance((GuidePointModel) listPoint.get(listPoint.size() - 2), (GuidePointModel) listPoint.get(listPoint.size() - 1), lastPoint)) / ((double) (((((GuidePointModel) listPoint.get(listPoint.size() - 1)).getSysTime() - ((GuidePointModel) listPoint.get(listPoint.size() - 3)).getSysTime()) / 2) + (((GuidePointModel) listPoint.get(listPoint.size() - 2)).getSysTime() - ((GuidePointModel) listPoint.get(listPoint.size() - 1)).getSysTime())))));
            } else {
                listSpeed.add(Double.valueOf((1000.0d * pointDistance((GuidePointModel) listPoint.get(i - 1), (GuidePointModel) listPoint.get(i), (GuidePointModel) listPoint.get(i + 1))) / ((double) (((GuidePointModel) listPoint.get(i + 1)).getSysTime() - ((GuidePointModel) listPoint.get(i - 1)).getSysTime()))));
            }
        }
        Collections.sort(listSpeed);
        if (listSpeed.size() < 6) {
            filterSpeed = ((Double) listSpeed.get(listSpeed.size() - 2)).doubleValue();
        } else if (listSpeed.size() <= 5 || listSpeed.size() >= 11) {
            filterSpeed = ((Double) listSpeed.get(listSpeed.size() - 6)).doubleValue();
        } else {
            filterSpeed = ((Double) listSpeed.get(listSpeed.size() - 3)).doubleValue();
        }
        for (i = 0; i < listPoint.size(); i++) {
            if (i == 0) {
                if ((1000.0d * pointDistance(firstPoint, (GuidePointModel) listPoint.get(0), (GuidePointModel) listPoint.get(1))) / ((double) (((((GuidePointModel) listPoint.get(2)).getSysTime() - ((GuidePointModel) listPoint.get(0)).getSysTime()) / 2) + (((GuidePointModel) listPoint.get(1)).getSysTime() - ((GuidePointModel) listPoint.get(0)).getSysTime()))) < filterSpeed) {
                    filterList.add(listPoint.get(i));
                }
            } else if (i == listPoint.size() - 1) {
                if ((1000.0d * pointDistance((GuidePointModel) listPoint.get(listPoint.size() - 2), (GuidePointModel) listPoint.get(listPoint.size() - 1), lastPoint)) / ((double) (((((GuidePointModel) listPoint.get(listPoint.size() - 1)).getSysTime() - ((GuidePointModel) listPoint.get(listPoint.size() - 3)).getSysTime()) / 2) + (((GuidePointModel) listPoint.get(listPoint.size() - 2)).getSysTime() - ((GuidePointModel) listPoint.get(listPoint.size() - 1)).getSysTime()))) < filterSpeed) {
                    filterList.add(listPoint.get(i));
                }
            } else {
                if ((1000.0d * pointDistance((GuidePointModel) listPoint.get(i - 1), (GuidePointModel) listPoint.get(i), (GuidePointModel) listPoint.get(i + 1))) / ((double) (((GuidePointModel) listPoint.get(i + 1)).getSysTime() - ((GuidePointModel) listPoint.get(i - 1)).getSysTime())) < filterSpeed) {
                    filterList.add(listPoint.get(i));
                }
            }
        }
        return filterList;
    }

    private double pointDistance(GuidePointModel guide1, GuidePointModel guide2, GuidePointModel guide3) {
        LatLng point1 = new LatLng(guide1.getLatitude(), guide1.getLongitude());
        LatLng point2 = new LatLng(guide2.getLatitude(), guide2.getLongitude());
        return ((double) AMapUtils.calculateLineDistance(point1, point2)) + ((double) AMapUtils.calculateLineDistance(point2, new LatLng(guide3.getLatitude(), guide3.getLongitude())));
    }

    public GuidePointModel initGuidePoint(AMapLocation amapLocation) {
        long pointTime = conversTime();
        GuidePointModel mguidePoint = new GuidePointModel();
        mguidePoint.setGuideId(Tools.getPKL());
        mguidePoint.setLatitude(amapLocation.getLatitude());
        mguidePoint.setLongitude(amapLocation.getLongitude());
        mguidePoint.setAddress(amapLocation.getAddress());
        mguidePoint.setAccuracy(amapLocation.getAccuracy());
        mguidePoint.setAltitude(amapLocation.getAltitude());
        mguidePoint.setProvider(amapLocation.getProvider());
        mguidePoint.setSpeed((float) this.usedcount);
        mguidePoint.setTime(pointTime);
        mguidePoint.setSysTime(System.currentTimeMillis());
        mguidePoint.setSyncState(0);
        mguidePoint.setGpsStatus(this.satelliteNum);
        if (point_state == 0) {
            mguidePoint.setPointState(0);
        } else {
            mguidePoint.setPointState(point_state);
            if (amapLocation.getProvider() == "gps" || amapLocation.getProvider().equals("gps")) {
                point_state = 0;
            }
        }
        return mguidePoint;
    }

    public static long conversTime() {
        Calendar mCal = Calendar.getInstance();
        int year = mCal.get(1);
        int month = mCal.get(2) + 1;
        int days = mCal.get(5);
        int hour = mCal.get(11);
        return ((((((long) mCal.get(13)) + (((long) mCal.get(12)) * 100)) + (((long) hour) * 10000)) + (((long) days) * 1000000)) + (((long) month) * 100000000)) + (((long) year) * 10000000000L);
    }

    public void activate(OnLocationChangedListener listener) {
        this.mListener = listener;
        if (this.mAMapLocationManager == null) {
            this.mAMapLocationManager = LocationManagerProxy.getInstance((Context) this);
            this.mAMapLocationManager.requestLocationData(LocationProviderProxy.AMapNetwork, 2000, TitleBar.SHAREBTN_RIGHT_MARGIN, this);
        }
    }

    public void deactivate() {
        this.mListener = null;
        if (this.mAMapLocationManager != null) {
            this.mAMapLocationManager.removeUpdates((AMapLocationListener) this);
            this.mAMapLocationManager.destroy();
        }
        this.mAMapLocationManager = null;
    }

    public void onLocationChanged(Location location) {
    }

    public void onStatusChanged(String provider, int status, Bundle extras) {
    }

    public void onProviderEnabled(String provider) {
    }

    public void onProviderDisabled(String provider) {
    }

    private void initFilter() {
        IntentFilter mGATTFilter = new IntentFilter();
        mGATTFilter.addAction("ACTION_STOP_GPSSPORT");
        mGATTFilter.addAction("ACTION_BEGIN_GPSSPORT");
        mGATTFilter.addAction("ACTION_CONTINUE_GPSSPORT");
        mGATTFilter.addAction("ACTION_COMPELE_GPSSPORT");
        mGATTFilter.addAction("com.zhuoyou.gaode.service.hello");
        registerReceiver(this.mBroadcastReceiver, mGATTFilter);
    }

    private void insertTempData() {
        Log.i("lsj", "GaodeService.gpsSportInfo.getGpsId() =" + gpsSportInfo.getGpsId());
        if (gpsSportInfo.getGpsId() != 0) {
            this.mDataBaseUtil.updateGpsInfo(gpsSportInfo);
        }
        if (tempDataModel.getTempId() != 0) {
            RunningItem runningItem = new RunningItem();
            runningItem.setDate(tempDataModel.getTempDate());
            runningItem.setStartTime(tempDataModel.getTempStaTime());
            runningItem.setDuration(tempDataModel.getTempDuration());
            runningItem.setEndTime(tempDataModel.getTempEndTime());
            runningItem.setCalories(tempDataModel.getTempCalories());
            runningItem.setSteps(tempDataModel.getTempStep());
            runningItem.setmType(tempDataModel.getTempType());
            runningItem.setisStatistics(tempDataModel.getTempStatistics());
            runningItem.setmWeight(tempDataModel.getTempStaAddress());
            runningItem.setmBmi(tempDataModel.getTempEndAddress());
            runningItem.setmImgUri(tempDataModel.getTempImageUrl());
            runningItem.setmExplain(Long.toString(tempDataModel.getTempGpsId()));
            runningItem.setHeart_rate_count(tempDataModel.getTempHeartRate());
            Intent gpsIntent = new Intent("ACTION_GPS_INFO");
            gpsIntent.putExtra("gps_info", runningItem);
            sendBroadcast(gpsIntent);
        }
    }

    private void compeleGpsSport() {
        this.editor = this.sharepreference.edit();
        this.editor.putInt("map_activity_state", 0);
        this.editor.putBoolean("is_begin_point", true);
        this.editor.putInt("gps_singal_state", 0);
        if (this.sharepreference.contains("is_have_beginaddr")) {
            this.editor.remove("is_have_beginaddr");
        }
        if (this.sharepreference.contains("gps_sport_id")) {
            this.editor.remove("gps_sport_id");
        }
        this.editor.commit();
        point_state = 0;
        OperationTimeModel mOperation2 = new OperationTimeModel();
        mOperation2.setOperatId(Tools.getPKL());
        mOperation2.setOperationtime(conversTime());
        mOperation2.setOperationSystime(System.currentTimeMillis());
        mOperation2.setOperationState(4);
        mOperation2.setSyncState(0);
        this.mDataBaseUtil.insertOperation(mOperation2);
        GaoDeMapActivity.is_line = false;
        is_running = false;
    }

    private void deletefromDatabase(GpsSportDataModel mGps) {
        int i;
        List<Integer> listGpsId = this.mDataBaseUtil.selectPointID(mGps.getStarttime(), conversTime(), 0);
        List<Integer> listOperationId = this.mDataBaseUtil.selectOperationId(mGps.getStarttime(), conversTime(), 0);
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
        this.mDataBaseUtil.deleteOperation(mGps.getStarttime(), conversTime());
        this.mDataBaseUtil.deletePoint(mGps.getStarttime(), conversTime());
    }

    private void setUpMap() {
        this.aMap.setLocationSource(this);
        this.aMap.getUiSettings().setMyLocationButtonEnabled(true);
        this.aMap.setMyLocationEnabled(true);
        this.aMap.setMyLocationType(1);
    }

    private double calSumDistance(GuidePointModel point) {
        if (this.upPoint == null) {
            this.upPoint = point;
            return 0.0d;
        }
        double pieRunDis = (double) AMapUtils.calculateLineDistance(new LatLng(this.upPoint.getLatitude(), this.upPoint.getLongitude()), new LatLng(point.getLatitude(), point.getLongitude()));
        if (pieRunDis > 2.0d) {
            this.sumRunDis += pieRunDis;
            this.upPoint = point;
            mSumDis = this.sumRunDis + baseDistance;
            notifyDistance();
        }
        long gpsid = this.sharepreference.getLong("gps_sport_id", -1);
        if (gpsid != -1) {
            GpsUtil.updateSport(this, Long.valueOf(gpsid), startAddress, endAddress, null, null, Float.valueOf((float) mSumDis), null, null);
        }
        return pieRunDis;
    }

    private void notifyStep() {
        long gpsid = this.sharepreference.getLong("gps_sport_id", -1);
        if (gpsid != -1) {
            GpsSportDataModel mGpsInfo = this.mDataBaseUtil.selectGpsInfoForID(gpsid);
            if (hadRunStep < mGpsInfo.getSteps()) {
                hadRunStep = mGpsInfo.getSteps();
                baseStep = hadRunStep - this.mRunStep;
            }
        }
        StepWatcher.getInstance().notifyHadRunStep(hadRunStep);
    }

    private void notifyDistance() {
        long gpsid = this.sharepreference.getLong("gps_sport_id", -1);
        if (gpsid != -1) {
            GpsSportDataModel mGpsInfo = this.mDataBaseUtil.selectGpsInfoForID(gpsid);
            if (mSumDis < ((double) mGpsInfo.getSteps())) {
                mSumDis = (double) mGpsInfo.getSteps();
                baseDistance = mSumDis - this.sumRunDis;
            }
        }
        MonitorWatcher.getInstance().notifySumDistance(mSumDis);
    }
}
