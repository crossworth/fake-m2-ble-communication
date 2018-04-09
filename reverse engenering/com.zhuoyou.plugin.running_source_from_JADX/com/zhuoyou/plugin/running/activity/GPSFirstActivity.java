package com.zhuoyou.plugin.running.activity;

import android.annotation.TargetApi;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.mapapi.model.LatLng;
import com.umeng.socialize.common.SocializeConstants;
import com.zhuoyou.plugin.running.C1680R;
import com.zhuoyou.plugin.running.base.BaseActivity;
import com.zhuoyou.plugin.running.service.GPSRunService;
import com.zhuoyou.plugin.running.service.GPSRunService.State;
import com.zhuoyou.plugin.running.tools.AnimUtils;
import com.zhuoyou.plugin.running.tools.BMapUtils;
import com.zhuoyou.plugin.running.tools.GpsUtils;
import com.zhuoyou.plugin.running.tools.SPUtils;
import com.zhuoyou.plugin.running.tools.Tools;
import com.zhuoyou.plugin.running.view.MyAlertDialog;
import com.zhuoyou.plugin.running.view.MyAlertDialog.OnClickListener;
import org.greenrobot.eventbus.Subscribe;

public class GPSFirstActivity extends BaseActivity implements BDLocationListener {
    private BroadcastReceiver GPSReceiver = new C17581();
    private MyAlertDialog dialog;
    private ImageView imgGpsStatus;
    private ImageView imgStartBack;
    private int[] imgStatus = new int[]{C1680R.drawable.gps_signal_0, C1680R.drawable.gps_signal_1, C1680R.drawable.gps_signal_2, C1680R.drawable.gps_signal_3};
    private boolean isGpsEnabled;
    private LocationClient mLocationClient = null;
    private TextView tvGpsStatus;
    private int[] tvStatus = new int[]{C1680R.string.gps_is_none, C1680R.string.gps_is_weak, C1680R.string.gps_is_soso, C1680R.string.gps_is_good};

    class C17581 extends BroadcastReceiver {
        C17581() {
        }

        public void onReceive(Context context, Intent intent) {
            GPSFirstActivity.this.checkGps();
        }
    }

    class C17592 implements OnClickListener {
        C17592() {
        }

        public void onClick(int witch) {
            GPSFirstActivity.this.enableLocationSettings();
        }
    }

    public static class EventFinish {
    }

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) C1680R.layout.activity_gps_first);
        initView();
        initLocation();
        initGPSReceiver();
        checkGps();
        if (GPSRunService.state == State.Running) {
            startActivity(new Intent(this, GPSRunActivity.class));
        }
    }

    private void initView() {
        this.tvGpsStatus = (TextView) findViewById(C1680R.id.tv_gps_status);
        this.imgGpsStatus = (ImageView) findViewById(C1680R.id.img_gps_status);
        this.imgStartBack = (ImageView) findViewById(C1680R.id.img_start_back);
        this.imgStartBack.setAnimation(AnimationUtils.loadAnimation(this, C1680R.anim.gps_start_back));
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case C1680R.id.btn_start:
                if (this.isGpsEnabled) {
                    startActivity(new Intent(this, GPSRunActivity.class));
                    return;
                } else {
                    Tools.makeToast((int) C1680R.string.gps_not_enable);
                    return;
                }
            case C1680R.id.btn_history:
                startActivity(new Intent(this, GPSHistoryActivity.class));
                return;
            default:
                return;
        }
    }

    protected void onResume() {
        super.onResume();
        this.mLocationClient.start();
    }

    protected void onPause() {
        super.onPause();
        this.mLocationClient.stop();
    }

    @TargetApi(19)
    private void initGPSReceiver() {
        IntentFilter filter = new IntentFilter();
        filter.addAction("android.location.PROVIDERS_CHANGED");
        registerReceiver(this.GPSReceiver, filter);
    }

    private void initLocation() {
        this.mLocationClient = BMapUtils.getDefLocationClient(this, this.mLocationClient);
        this.mLocationClient.registerLocationListener(this);
    }

    public void checkGps() {
        this.isGpsEnabled = ((LocationManager) getSystemService(SocializeConstants.KEY_LOCATION)).isProviderEnabled("gps");
        if (this.isGpsEnabled) {
            hideGpsClosedDialog();
            this.tvGpsStatus.setText(C1680R.string.gps_is_searching);
            this.imgGpsStatus.setImageResource(C1680R.drawable.gps_searching);
            AnimUtils.playAnimList(this.imgGpsStatus.getDrawable());
            return;
        }
        showGpsClosedDialog();
        this.tvGpsStatus.setText(C1680R.string.gps_is_closed);
        this.imgGpsStatus.setImageResource(C1680R.drawable.gps_signal_0);
    }

    private void showGpsClosedDialog() {
        if (this.dialog == null) {
            this.dialog = new MyAlertDialog(this);
        }
        if (!this.dialog.isShowing()) {
            this.dialog.setTitle((int) C1680R.string.gps_is_closed);
            this.dialog.setMessage((int) C1680R.string.gps_dialog_needGPS);
            this.dialog.setLeftButton((int) C1680R.string.string_cancel, null);
            this.dialog.setRightButton((int) C1680R.string.gps_dialog_openGPS, new C17592());
            this.dialog.show();
        }
    }

    private void hideGpsClosedDialog() {
        if (this.dialog != null && this.dialog.isShowing()) {
            this.dialog.dismiss();
        }
    }

    private void enableLocationSettings() {
        startActivity(new Intent("android.settings.LOCATION_SOURCE_SETTINGS"));
    }

    public void onReceiveLocation(BDLocation bdLocation) {
        if (BMapUtils.isLocationSuccess(bdLocation)) {
            SPUtils.setLastLatLng(new LatLng(bdLocation.getLatitude(), bdLocation.getLongitude()));
        }
        if (bdLocation != null && this.isGpsEnabled) {
            int id = GpsUtils.getGpsStatus(bdLocation.getSatelliteNumber());
            this.tvGpsStatus.setText(this.tvStatus[id]);
            this.imgGpsStatus.setImageResource(this.imgStatus[id]);
        }
    }

    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(this.GPSReceiver);
        this.mLocationClient.unRegisterLocationListener(this);
    }

    @Subscribe
    public void onEventMainThread(EventFinish event) {
        finish();
    }
}
