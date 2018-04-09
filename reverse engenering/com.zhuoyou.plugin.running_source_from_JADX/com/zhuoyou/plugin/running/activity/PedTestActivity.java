package com.zhuoyou.plugin.running.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ImageView;
import android.widget.TextView;
import com.zhuoyou.plugin.running.C1680R;
import com.zhuoyou.plugin.running.base.BaseActivity;
import com.zhuoyou.plugin.running.tools.StatusBarUtils;

public class PedTestActivity extends BaseActivity implements SensorEventListener {
    private ImageView imgTop;
    private boolean isScreenOff;
    private final BroadcastReceiver mBroadcastReceiver = new C17901();
    private SensorManager mSensorManager;
    private TextView tvHint;
    private TextView tvModel;

    class C17901 extends BroadcastReceiver {
        C17901() {
        }

        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if ("android.intent.action.SCREEN_ON".equals(action)) {
                PedTestActivity.this.isScreenOff = false;
            } else if ("android.intent.action.SCREEN_OFF".equals(action)) {
                PedTestActivity.this.isScreenOff = true;
                PedTestActivity.this.imgTop.setImageResource(C1680R.drawable.ped_test_not_ok);
                PedTestActivity.this.tvHint.setText(C1680R.string.app_setting_pedtest_not_ok);
                PedTestActivity.this.tvModel.setText(PedTestActivity.this.getString(C1680R.string.app_setting_model, new Object[]{Build.MODEL, VERSION.RELEASE}));
            }
        }
    }

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtils.StatusBarLightMode(this);
        setContentView((int) C1680R.layout.activity_ped_test);
        initView();
        this.mSensorManager = (SensorManager) getSystemService("sensor");
        this.mSensorManager.registerListener(this, this.mSensorManager.getDefaultSensor(1), 3);
        initReceiver();
    }

    private void initView() {
        this.tvHint = (TextView) findViewById(C1680R.id.tv_hint);
        this.tvModel = (TextView) findViewById(C1680R.id.tv_model);
        this.imgTop = (ImageView) findViewById(C1680R.id.img_top);
    }

    private void initReceiver() {
        IntentFilter filter = new IntentFilter();
        filter.addAction("android.intent.action.SCREEN_ON");
        filter.addAction("android.intent.action.SCREEN_OFF");
        registerReceiver(this.mBroadcastReceiver, filter);
    }

    public void onSensorChanged(SensorEvent event) {
        if (this.isScreenOff) {
            this.tvHint.setText(C1680R.string.app_setting_pedtest_ok);
            this.imgTop.setImageResource(C1680R.drawable.ped_test_ok);
        }
    }

    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }

    protected void onDestroy() {
        super.onDestroy();
        this.mSensorManager.unregisterListener(this);
        unregisterReceiver(this.mBroadcastReceiver);
    }
}
