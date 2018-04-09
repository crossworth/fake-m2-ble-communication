package com.zhuoyou.plugin.running.service;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.utils.DistanceUtil;
import com.droi.greendao.bean.GpsPointBean;
import com.droi.greendao.bean.GpsSportBean;
import com.droi.sdk.core.DroiUser;
import com.zhuoyou.plugin.running.database.GpsPointHelper;
import com.zhuoyou.plugin.running.database.GpsSportHelper;
import com.zhuoyou.plugin.running.database.WeightHelper;
import com.zhuoyou.plugin.running.tools.BMapUtils;
import com.zhuoyou.plugin.running.tools.GpsUtils;
import com.zhuoyou.plugin.running.tools.SPUtils;
import com.zhuoyou.plugin.running.tools.Tools;
import java.util.LinkedList;
import java.util.List;

public class GPSRunService extends Service implements BDLocationListener {
    private static final int MAX_ACCURACY = 30;
    private static final int MIN_DISTANCE = 3;
    private static final int MSG_UPDATE_TIME = 257;
    public static final String TAG = "zhuqichao";
    public static BDLocation bdLocation;
    public static float cal;
    public static float distance;
    public static int duration;
    public static int heart;
    public static LinkedList<LatLng> latlngList = new LinkedList();
    private static List<LocationCallBack> listeners = new LinkedList();
    private static Handler mHandler = new C19141();
    public static float speed;
    public static String sportId = "";
    public static State state = State.Stop;
    private double la = 31.162798d;
    private double lo = 121.40418d;
    private LocationClient mLocationClient = null;

    public interface LocationCallBack {
        void onDistanceChanged(float f, float f2);

        void onDurationChanged(int i);

        void onHeartChanged(int i);

        void onLocationChanged(BDLocation bDLocation);

        void onSpeedChanged(float f);
    }

    static class C19141 extends Handler {
        C19141() {
        }

        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 257:
                    GPSRunService.duration++;
                    GPSRunService.doOnDurationChanged(GPSRunService.duration);
                    GPSRunService.mHandler.sendEmptyMessageDelayed(257, 1000);
                    return;
                default:
                    return;
            }
        }
    }

    public enum State {
        Running,
        Stop
    }

    public void onCreate() {
        super.onCreate();
        initLocation();
    }

    public static void startSport() {
        state = State.Running;
        resetData();
        sportId = Tools.formatTime(System.currentTimeMillis());
        latlngList.clear();
        updateGpsSportInfo(false);
        mHandler.sendEmptyMessageDelayed(257, 1000);
    }

    public static void stopSport() {
        state = State.Stop;
        mHandler.removeMessages(257);
        updateGpsSportInfo(true);
        resetData();
    }

    public static void addCallBack(LocationCallBack mCallBack) {
        listeners.add(mCallBack);
    }

    public static boolean removeCallBack(LocationCallBack mCallBack) {
        return listeners.remove(mCallBack);
    }

    private void initLocation() {
        this.mLocationClient = BMapUtils.getDefLocationClient(this, this.mLocationClient);
        this.mLocationClient.registerLocationListener(this);
        bdLocation = this.mLocationClient.getLastKnownLocation();
        this.mLocationClient.start();
    }

    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i("zhuqichao", "GPSRunService.onStartCommand");
        return 2;
    }

    public void onTaskRemoved(Intent rootIntent) {
        relase();
        super.onTaskRemoved(rootIntent);
        Log.i("zhuqichao", "GPSRunService.onTaskRemoved");
    }

    public void onDestroy() {
        relase();
        super.onDestroy();
        Log.i("zhuqichao", "GPSRunService.onDestroy");
    }

    @Nullable
    public IBinder onBind(Intent intent) {
        return null;
    }

    private void relase() {
        if (this.mLocationClient != null) {
            this.mLocationClient.unRegisterLocationListener(this);
            this.mLocationClient.stop();
        }
        resetData();
        sportId = "";
        state = State.Stop;
        latlngList.clear();
        listeners.clear();
    }

    private static void resetData() {
        distance = 0.0f;
        duration = 0;
        cal = 0.0f;
        heart = 0;
    }

    public void onReceiveLocation(BDLocation location) {
        if (BMapUtils.isLocationSuccess(location)) {
            SPUtils.setLastLatLng(new LatLng(location.getLatitude(), location.getLongitude()));
            bdLocation = location;
            speed = (float) (((double) location.getSpeed()) * 3.6d);
            doOnSpeedChanged(speed);
            if (state == State.Running && savePoint(location)) {
                updateGpsSportInfo(false);
                updateDistance(location);
            }
            doOnLocationChanged(location);
        }
    }

    private void updateDistance(BDLocation location) {
        distance = (float) (((double) distance) + getPointDistance(location));
        cal = GpsUtils.getCalories(distance, WeightHelper.getNewestWeight().getWeight());
        latlngList.add(new LatLng(location.getLatitude(), location.getLongitude()));
        doOnDistanceChanged(distance, cal);
    }

    private static void updateGpsSportInfo(boolean stop) {
        int i = 0;
        GpsSportBean value = new GpsSportBean(sportId);
        value.setAccount(DroiUser.getCurrentUser().getUserId());
        value.setStartTime(sportId);
        value.setStopTime(Tools.formatTime(System.currentTimeMillis()));
        value.setDistance(distance);
        value.setDuration(duration);
        value.setStep(0);
        value.setCal(cal);
        value.setHeart(heart);
        if (stop) {
            i = 1;
        }
        value.setStop(i);
        GpsSportHelper.getBeanDao().insertOrReplace(value);
    }

    private boolean savePoint(BDLocation location) {
        if (latlngList.size() > 0 || location.getRadius() > 30.0f) {
            return location.getRadius() <= 30.0f && getPointDistance(location) >= 3.0d && insertPoint(location);
        } else {
            return insertPoint(location);
        }
    }

    private boolean insertPoint(BDLocation location) {
        GpsPointBean point = new GpsPointBean();
        point.setSportId(sportId);
        point.setAccount(DroiUser.getCurrentUser().getUserId());
        point.setTime(Tools.formatTime(System.currentTimeMillis()));
        point.setLatitude(location.getLatitude());
        point.setLongitude(location.getLongitude());
        point.setHeart(heart);
        point.setAddress(location.getLocationDescribe());
        point.setCadence(0);
        if (GpsPointHelper.getBeanDao().insert(point) > 0) {
            return true;
        }
        return false;
    }

    private double getPointDistance(BDLocation location) {
        if (latlngList.size() <= 0) {
            return 0.0d;
        }
        return DistanceUtil.getDistance((LatLng) latlngList.getLast(), new LatLng(location.getLatitude(), location.getLongitude()));
    }

    private static void doOnDurationChanged(int tiem) {
        for (LocationCallBack item : listeners) {
            if (item != null) {
                item.onDurationChanged(tiem);
            }
        }
    }

    private void doOnSpeedChanged(float speed) {
        for (LocationCallBack item : listeners) {
            if (item != null) {
                item.onSpeedChanged(speed);
            }
        }
    }

    private void doOnDistanceChanged(float distance, float cal) {
        for (LocationCallBack item : listeners) {
            if (item != null) {
                item.onDistanceChanged(distance, cal);
            }
        }
    }

    private void doOnLocationChanged(BDLocation location) {
        for (LocationCallBack item : listeners) {
            if (item != null) {
                item.onLocationChanged(location);
            }
        }
    }

    private void doOnHeartChanged(int heart) {
        for (LocationCallBack item : listeners) {
            if (item != null) {
                item.onHeartChanged(heart);
            }
        }
    }
}
