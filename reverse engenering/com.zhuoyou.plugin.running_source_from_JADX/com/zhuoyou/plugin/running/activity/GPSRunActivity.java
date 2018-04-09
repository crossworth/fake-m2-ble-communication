package com.zhuoyou.plugin.running.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.View.OnTouchListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.OvershootInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.baidu.location.BDLocation;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BaiduMap.OnMapLoadedCallback;
import com.baidu.mapapi.map.BaiduMap.SnapshotReadyCallback;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationConfiguration.LocationMode;
import com.baidu.mapapi.map.MyLocationData.Builder;
import com.baidu.mapapi.map.Polyline;
import com.baidu.mapapi.map.UiSettings;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.model.LatLngBounds;
import com.zhuoyou.plugin.running.C1680R;
import com.zhuoyou.plugin.running.activity.GPSFirstActivity.EventFinish;
import com.zhuoyou.plugin.running.base.BaseActivity;
import com.zhuoyou.plugin.running.database.GpsPointHelper;
import com.zhuoyou.plugin.running.database.GpsSportHelper;
import com.zhuoyou.plugin.running.service.GPSRunService;
import com.zhuoyou.plugin.running.service.GPSRunService.LocationCallBack;
import com.zhuoyou.plugin.running.service.GPSRunService.State;
import com.zhuoyou.plugin.running.tools.BMapUtils;
import com.zhuoyou.plugin.running.tools.BitmapUtils;
import com.zhuoyou.plugin.running.tools.GpsUtils;
import com.zhuoyou.plugin.running.tools.SPUtils;
import com.zhuoyou.plugin.running.tools.Tools;
import com.zhuoyou.plugin.running.view.MyActionBar;
import com.zhuoyou.plugin.running.view.MyAlertDialog;
import org.greenrobot.eventbus.EventBus;

public class GPSRunActivity extends BaseActivity implements OnClickListener {
    static final /* synthetic */ boolean $assertionsDisabled = (!GPSRunActivity.class.desiredAssertionStatus());
    public static final String TAG = "zhuqichao";
    private MyActionBar actionBar;
    private BaiduMap bMap = null;
    private LinearLayout bootomLayout;
    private int[] colors = new int[]{C1680R.color.gps_status_color_none, C1680R.color.gps_status_color_wake, C1680R.color.gps_status_color_soso, C1680R.color.gps_status_color_good};
    private ImageView imgGpsStatus;
    private ImageView imgMapGpsStatus;
    private ImageView imgMapZoom;
    private int[] imgStatus = new int[]{C1680R.drawable.gps_signal_0, C1680R.drawable.gps_signal_1, C1680R.drawable.gps_signal_2, C1680R.drawable.gps_signal_3};
    private int[] imgStatusMap = new int[]{C1680R.drawable.gps_map_signal_0, C1680R.drawable.gps_map_signal_1, C1680R.drawable.gps_map_signal_2, C1680R.drawable.gps_map_signal_3};
    private ImageView imgStopBack;
    private boolean isShowSimple = true;
    private boolean isShowStop = true;
    private View layoutSimple;
    private LocationCallBack mCallBack = new C17654();
    private Handler mHandler = new C17676();
    private MapView mMapView = null;
    private OnMapLoadedCallback onMapLoadedCallback = new C17665();
    private ProgressDialog pdialog;
    private Marker sMarker;
    private Intent service;
    private Polyline sportLine;
    private TextView tvCal;
    private TextView tvDistance;
    private TextView tvGpsStatus;
    private TextView tvHeart;
    private TextView tvMapDistance;
    private TextView tvMapGpsStatus;
    private TextView tvMapTime;
    private TextView tvSpeed;
    private int[] tvStatus = new int[]{C1680R.string.gps_is_none, C1680R.string.gps_is_weak, C1680R.string.gps_is_soso, C1680R.string.gps_is_good};
    private TextView tvTime;

    class C17621 implements OnLongClickListener {
        C17621() {
        }

        public boolean onLongClick(View v) {
            GPSRunActivity.this.onStopClick();
            return true;
        }
    }

    class C17632 implements OnTouchListener {
        C17632() {
        }

        public boolean onTouch(View v, MotionEvent event) {
            return true;
        }
    }

    class C17643 implements OnTouchListener {
        C17643() {
        }

        public boolean onTouch(View v, MotionEvent event) {
            switch (event.getAction()) {
                case 0:
                    Animation anim = AnimationUtils.loadAnimation(GPSRunActivity.this, C1680R.anim.gps_stop_back);
                    GPSRunActivity.this.imgStopBack.setVisibility(0);
                    GPSRunActivity.this.imgStopBack.setAnimation(anim);
                    GPSRunActivity.this.imgStopBack.setVisibility(8);
                    break;
            }
            return false;
        }
    }

    class C17654 implements LocationCallBack {
        C17654() {
        }

        public void onLocationChanged(BDLocation location) {
            if (BMapUtils.isLocationSuccess(location)) {
                GPSRunActivity.this.bMap.setMyLocationData(new Builder().accuracy(location.getRadius()).direction(location.getDirection()).latitude(location.getLatitude()).longitude(location.getLongitude()).build());
                int id = GpsUtils.getGpsStatus(location.getSatelliteNumber());
                GPSRunActivity.this.tvGpsStatus.setText(GPSRunActivity.this.tvStatus[id]);
                GPSRunActivity.this.tvMapGpsStatus.setText(GPSRunActivity.this.tvStatus[id]);
                GPSRunActivity.this.tvMapGpsStatus.setTextColor(GPSRunActivity.this.getResources().getColor(GPSRunActivity.this.colors[id]));
                GPSRunActivity.this.imgGpsStatus.setImageResource(GPSRunActivity.this.imgStatus[id]);
                GPSRunActivity.this.imgMapGpsStatus.setImageResource(GPSRunActivity.this.imgStatusMap[id]);
            }
        }

        public void onDistanceChanged(float distance, float cal) {
            String disStr = GpsUtils.getDisStrNoUnit(distance);
            GPSRunActivity.this.tvDistance.setText(disStr);
            GPSRunActivity.this.tvMapDistance.setText(disStr);
            if (GPSRunActivity.this.sMarker == null && GPSRunService.latlngList.size() > 0) {
                GPSRunActivity.this.sMarker = BMapUtils.addMakerToMap(GPSRunActivity.this.bMap, C1680R.drawable.point_begin, (LatLng) GPSRunService.latlngList.getFirst());
            }
            if (GPSRunActivity.this.sportLine != null) {
                GPSRunActivity.this.sportLine.setPoints(GPSRunService.latlngList);
            } else {
                GPSRunActivity.this.sportLine = BMapUtils.addLineToMap(GPSRunActivity.this.bMap, GPSRunService.latlngList, false);
            }
            GPSRunActivity.this.tvCal.setText(String.valueOf(GpsUtils.getCalStrNoUnit(cal)));
        }

        public void onSpeedChanged(float speed) {
            GPSRunActivity.this.tvSpeed.setText(GpsUtils.getSpeed(speed));
        }

        public void onDurationChanged(int time) {
            String timeStr = GpsUtils.getDuration(time);
            GPSRunActivity.this.tvTime.setText(timeStr);
            GPSRunActivity.this.tvMapTime.setText(timeStr);
        }

        public void onHeartChanged(int heart) {
            GPSRunActivity.this.tvHeart.setText(String.valueOf(heart));
        }
    }

    class C17665 implements OnMapLoadedCallback {
        C17665() {
        }

        public void onMapLoaded() {
            Log.i("zhuqichao", "onMapLoaded");
            LatLng latLng = SPUtils.getLastLatLng();
            Log.i("zhuqichao", "lastLocation=" + latLng.latitude + ", " + latLng.longitude);
            if (latLng.latitude == -1.0d || latLng.longitude == -1.0d) {
                GPSRunActivity.this.changeCamera(MapStatusUpdateFactory.zoomTo(19.0f), false);
            } else {
                GPSRunActivity.this.changeCamera(MapStatusUpdateFactory.newLatLngZoom(latLng, 19.0f), false);
            }
        }
    }

    class C17676 extends Handler {
        C17676() {
        }

        public void handleMessage(Message msg) {
        }
    }

    class C17687 implements SnapshotReadyCallback {
        C17687() {
        }

        public void onSnapshotReady(Bitmap bitmap) {
            BitmapUtils.saveBitmapToFile(GpsUtils.getGpsLocusPath(Tools.FILE_PATH) + GPSRunService.sportId, bitmap);
            Tools.hideProgressDialog(GPSRunActivity.this.pdialog);
            GPSRunActivity.this.startActivity(new Intent(GPSRunActivity.this, GPSDetailActivity.class).putExtra("key_sport_bean_id", GPSRunService.sportId));
            GPSRunActivity.this.finish();
        }
    }

    class C17698 implements Runnable {
        C17698() {
        }

        public void run() {
            GPSRunActivity.this.getMapScreenShot();
        }
    }

    class C17709 implements MyAlertDialog.OnClickListener {
        C17709() {
        }

        public void onClick(int witch) {
            GPSRunActivity.this.deleteAndFinish();
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) C1680R.layout.activity_gps_run);
        this.service = new Intent(this, GPSRunService.class);
        this.pdialog = Tools.getProgressDialog(this);
        setUpMap();
        initView();
        startSport();
    }

    private void initView() {
        this.actionBar = (MyActionBar) findViewById(C1680R.id.actionbar);
        FrameLayout btnStop = (FrameLayout) findViewById(C1680R.id.btn_stop);
        this.imgStopBack = (ImageView) findViewById(C1680R.id.img_stop_back);
        this.imgMapZoom = (ImageView) findViewById(C1680R.id.btn_map_zoom);
        this.bootomLayout = (LinearLayout) findViewById(C1680R.id.layout_bootom);
        this.tvGpsStatus = (TextView) findViewById(C1680R.id.tv_gps_status);
        this.tvMapGpsStatus = (TextView) findViewById(C1680R.id.tv_gps_map_status);
        this.imgGpsStatus = (ImageView) findViewById(C1680R.id.img_gps_status);
        this.imgMapGpsStatus = (ImageView) findViewById(C1680R.id.img_gps_map_status);
        this.tvDistance = (TextView) findViewById(C1680R.id.tv_distance);
        this.tvMapDistance = (TextView) findViewById(C1680R.id.tv_map_distance);
        this.tvTime = (TextView) findViewById(C1680R.id.tv_duration);
        this.tvMapTime = (TextView) findViewById(C1680R.id.tv_map_duration);
        this.tvSpeed = (TextView) findViewById(C1680R.id.tv_speed);
        this.tvCal = (TextView) findViewById(C1680R.id.tv_cal);
        this.tvHeart = (TextView) findViewById(C1680R.id.tv_heart);
        this.layoutSimple = findViewById(C1680R.id.layout_simple_mode);
        this.actionBar.setOnRightButtonClickListener(this);
        this.actionBar.setOnRightTitleClickListener(this);
        if ($assertionsDisabled || btnStop != null) {
            btnStop.setOnLongClickListener(new C17621());
            this.layoutSimple.setOnTouchListener(new C17632());
            btnStop.setOnTouchListener(new C17643());
            return;
        }
        throw new AssertionError();
    }

    private void setUpMap() {
        this.mMapView = (MapView) findViewById(C1680R.id.map);
        if ($assertionsDisabled || this.mMapView != null) {
            this.bMap = this.mMapView.getMap();
            this.bMap.setMyLocationEnabled(true);
            this.bMap.setMyLocationConfigeration(getMyLocationConfig());
            this.bMap.setOnMapLoadedCallback(this.onMapLoadedCallback);
            this.mMapView.showZoomControls(false);
            UiSettings uiSettings = this.bMap.getUiSettings();
            uiSettings.setCompassEnabled(false);
            uiSettings.setOverlookingGesturesEnabled(false);
            return;
        }
        throw new AssertionError();
    }

    private void startSport() {
        startService(this.service);
        if (GPSRunService.state != State.Running) {
            GPSRunService.startSport();
        }
        this.bMap.clear();
        initSportData();
        GPSRunService.addCallBack(this.mCallBack);
    }

    private void initSportData() {
        this.mCallBack.onSpeedChanged(GPSRunService.speed);
        this.mCallBack.onDurationChanged(GPSRunService.duration);
        this.mCallBack.onDistanceChanged(GPSRunService.distance, GPSRunService.cal);
    }

    private void showSportLine() {
        LatLng[] bounds = GpsPointHelper.getBoundPoints(GPSRunService.sportId);
        if (bounds != null) {
            LatLngBounds.Builder latLngBounds = new LatLngBounds.Builder();
            latLngBounds.include(bounds[0]).include(bounds[1]).include(bounds[2]).include(bounds[3]);
            changeCamera(MapStatusUpdateFactory.newLatLngBounds(latLngBounds.build(), this.mMapView.getWidth() - 50, this.mMapView.getHeight() - 50), false);
            this.bMap.clear();
            BMapUtils.addMakerToMap(this.bMap, C1680R.drawable.point_begin, (LatLng) GPSRunService.latlngList.getFirst());
            BMapUtils.addLineToMap(this.bMap, GPSRunService.latlngList, false);
            BMapUtils.addMakerToMap(this.bMap, C1680R.drawable.point_complete, (LatLng) GPSRunService.latlngList.getLast());
        }
    }

    private void showSimple(boolean show) {
        this.isShowSimple = show;
        if (show) {
            if (!this.isShowStop) {
                showStopButton(true);
            }
            Animation anim = AnimationUtils.loadAnimation(this, C1680R.anim.gps_layout_show);
            anim.setInterpolator(new OvershootInterpolator(1.2f));
            this.layoutSimple.setAnimation(anim);
            this.layoutSimple.setVisibility(0);
            this.actionBar.setRightTitle((int) C1680R.string.gps_mode_map);
            this.actionBar.setRightButtonImage(C1680R.drawable.gps_map);
            this.actionBar.setBackgroundResource(C1680R.color.gps_normal_mode_title_color);
            return;
        }
        anim = AnimationUtils.loadAnimation(this, C1680R.anim.gps_layout_dismiss);
        anim.setInterpolator(new OvershootInterpolator(1.2f));
        this.layoutSimple.setAnimation(anim);
        this.layoutSimple.setVisibility(8);
        this.actionBar.setRightTitle((int) C1680R.string.gps_mode_simple);
        this.actionBar.setRightButtonImage(C1680R.drawable.gps_normal);
        this.actionBar.setBackgroundResource(C1680R.color.gps_map_mode_title_color);
    }

    private void showStopButton(boolean show) {
        this.isShowStop = show;
        if (show) {
            Animation anim = AnimationUtils.loadAnimation(this, C1680R.anim.gps_layout_show);
            anim.setInterpolator(new OvershootInterpolator(1.2f));
            this.bootomLayout.setAnimation(anim);
            this.imgMapZoom.setImageResource(C1680R.drawable.gps_zoomout);
            this.bootomLayout.setVisibility(0);
            return;
        }
        anim = AnimationUtils.loadAnimation(this, C1680R.anim.gps_layout_dismiss);
        anim.setInterpolator(new OvershootInterpolator(1.2f));
        this.bootomLayout.setAnimation(anim);
        this.imgMapZoom.setImageResource(C1680R.drawable.gps_zoomin);
        this.bootomLayout.setVisibility(8);
    }

    private void changeCamera(MapStatusUpdate update, boolean animated) {
        if (animated) {
            this.bMap.animateMapStatus(update, 1000);
        } else {
            this.bMap.setMapStatus(update);
        }
    }

    private MyLocationConfiguration getMyLocationConfig() {
        return new MyLocationConfiguration(LocationMode.FOLLOWING, true, null, Color.parseColor("#2a0ea3fe"), Color.argb(0, 0, 0, 0));
    }

    private void getMapScreenShot() {
        this.bMap.snapshot(new C17687());
    }

    public void onClick(View v) {
        boolean z = true;
        switch (v.getId()) {
            case C1680R.id.actionbar_right_title:
            case C1680R.id.actionbar_right_button:
                if (this.isShowSimple) {
                    z = false;
                }
                this.isShowSimple = z;
                showSimple(this.isShowSimple);
                return;
            case C1680R.id.btn_map_zoom:
                if (this.isShowStop) {
                    z = false;
                }
                this.isShowStop = z;
                showStopButton(this.isShowStop);
                return;
            default:
                return;
        }
    }

    protected void onDestroy() {
        GPSRunService.removeCallBack(this.mCallBack);
        super.onDestroy();
        this.mMapView.onDestroy();
        if (GPSRunService.state != State.Running) {
            stopService(this.service);
        }
    }

    private void onStopClick() {
        if (GPSRunService.state != State.Stop) {
            int size = GPSRunService.latlngList.size();
            if (size <= 1) {
                showNoDataDialog();
            } else if (size < 20) {
                showLessDataDialog();
            } else {
                showStopHintDialog();
            }
        }
    }

    private void stopAndSaveSportData() {
        Tools.showProgressDialog(this.pdialog, getString(C1680R.string.gps_data_saving));
        GPSRunService.stopSport();
        if (this.isShowSimple) {
            showSimple(false);
        }
        showSportLine();
        this.mHandler.postDelayed(new C17698(), 500);
    }

    private void deleteAndFinish() {
        GPSRunService.stopSport();
        GpsPointHelper.deleteBySportId(GPSRunService.sportId);
        GpsSportHelper.getBeanDao().deleteByKey(GPSRunService.sportId);
        finish();
    }

    private void showNoDataDialog() {
        MyAlertDialog dialog = new MyAlertDialog(this);
        dialog.setTitle((int) C1680R.string.string_reminder);
        dialog.setMessage((int) C1680R.string.gps_dis_short);
        dialog.setLeftButton((int) C1680R.string.string_cancel, null);
        dialog.setRightButton((int) C1680R.string.string_ok, new C17709());
        dialog.show();
    }

    private void showStopHintDialog() {
        MyAlertDialog dialog = new MyAlertDialog(this);
        dialog.setTitle((int) C1680R.string.gps_sport_compelte);
        dialog.setMessage((int) C1680R.string.gps_data_save);
        dialog.setLeftButton((int) C1680R.string.string_cancel, null);
        dialog.setRightButton((int) C1680R.string.string_ok, new MyAlertDialog.OnClickListener() {
            public void onClick(int witch) {
                GPSRunActivity.this.stopAndSaveSportData();
            }
        });
        dialog.show();
    }

    private void showLessDataDialog() {
        MyAlertDialog dialog = new MyAlertDialog(this);
        dialog.setTitle((int) C1680R.string.gps_sport_compelte);
        dialog.setMessage((int) C1680R.string.gps_dis_short2);
        dialog.setLeftButton((int) C1680R.string.string_donnot_save, new MyAlertDialog.OnClickListener() {
            public void onClick(int witch) {
                GPSRunActivity.this.deleteAndFinish();
            }
        });
        dialog.setRightButton((int) C1680R.string.string_save, new MyAlertDialog.OnClickListener() {
            public void onClick(int witch) {
                GPSRunActivity.this.stopAndSaveSportData();
            }
        });
        dialog.show();
    }

    private void showRunningHintDialog(int msgid) {
        MyAlertDialog dialog = new MyAlertDialog(this);
        dialog.setTitle((int) C1680R.string.gps_guide_exit);
        dialog.setMessage(msgid);
        dialog.setLeftButton((int) C1680R.string.string_cancel, null);
        dialog.setRightButton((int) C1680R.string.string_ok, new MyAlertDialog.OnClickListener() {
            public void onClick(int witch) {
                EventBus.getDefault().post(new EventFinish());
                GPSRunActivity.this.finish();
            }
        });
        dialog.show();
    }

    public void onBackPressed() {
        if (GPSRunService.state != State.Running) {
            super.onBackPressed();
        } else if (GPSRunService.latlngList.size() < 20) {
            showRunningHintDialog(C1680R.string.gps_guide_remind);
        } else {
            showRunningHintDialog(C1680R.string.gps_guide_remind2);
        }
    }

    protected void onPause() {
        super.onPause();
        this.mMapView.onPause();
    }

    protected void onResume() {
        super.onResume();
        this.mMapView.onResume();
    }

    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        this.mMapView.onSaveInstanceState(outState);
    }
}
