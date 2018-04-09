package com.zhuoyou.plugin.gps;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.Resources;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import com.fithealth.running.R;
import com.tencent.open.yyb.TitleBar;
import com.zhuoyou.plugin.custom.CustomAlertDialog.Builder;

public class FirstGpsActivity extends Activity {
    private float currentDistance;
    private Editor editor;
    String from = "";
    Button ignoreBu;
    private float lastX = 0.0f;
    private float lastY = 0.0f;
    private float lastZ = 0.0f;
    Sensor mAccel = null;
    private final BroadcastReceiver mBroadcastReceiver = new C12563();
    SensorManager mSensorManager = null;
    Resources res;
    private boolean screen_off = false;
    SensorEventListener sel = null;
    private boolean sensor_isuse = false;
    private SharedPreferences sharepreference;
    private float totalShake;

    class C12541 implements OnClickListener {

        class C12521 implements DialogInterface.OnClickListener {
            C12521() {
            }

            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                FirstGpsActivity.this.editor = FirstGpsActivity.this.sharepreference.edit();
                FirstGpsActivity.this.editor.putBoolean("is_first_gps", false);
                FirstGpsActivity.this.editor.commit();
                if (FirstGpsActivity.this.from.equals("Main")) {
                    FirstGpsActivity.this.startActivity(new Intent(FirstGpsActivity.this, GaoDeMapActivity.class));
                    FirstGpsActivity.this.finish();
                } else if (FirstGpsActivity.this.from.equals("DayPedometerActivity")) {
                    FirstGpsActivity.this.finish();
                }
                FirstGpsActivity.this.from = "";
            }
        }

        class C12532 implements DialogInterface.OnClickListener {
            C12532() {
            }

            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        }

        C12541() {
        }

        public void onClick(View v) {
            Builder builder = new Builder(FirstGpsActivity.this);
            builder.setTitle((int) R.string.test_ensure);
            builder.setMessage((int) R.string.test_message);
            builder.setPositiveButton((int) R.string.test_postive, new C12521());
            builder.setNegativeButton((int) R.string.test_cancel, new C12532());
            builder.setCancelable(Boolean.valueOf(true));
            builder.create().show();
        }
    }

    class C12552 implements SensorEventListener {
        C12552() {
        }

        public void onSensorChanged(SensorEvent sensorEvent) {
            if (FirstGpsActivity.this.screen_off) {
                FirstGpsActivity.this.currentDistance = (Math.abs(sensorEvent.values[0] - FirstGpsActivity.this.lastX) + Math.abs(sensorEvent.values[1] - FirstGpsActivity.this.lastY)) + Math.abs(sensorEvent.values[2] - FirstGpsActivity.this.lastZ);
                Log.i("zhaojunhui", "distance is =" + FirstGpsActivity.this.currentDistance);
                if (FirstGpsActivity.this.currentDistance >= TitleBar.BACKBTN_LEFT_MARGIN) {
                    FirstGpsActivity.this.sensor_isuse = true;
                }
            }
        }

        public void onAccuracyChanged(Sensor arg0, int arg1) {
        }
    }

    class C12563 extends BroadcastReceiver {
        C12563() {
        }

        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if ("android.intent.action.SCREEN_ON".equals(action)) {
                FirstGpsActivity.this.screen_off = false;
                FirstGpsActivity.this.editor = FirstGpsActivity.this.sharepreference.edit();
                FirstGpsActivity.this.editor.putBoolean("is_first_gps", false);
                FirstGpsActivity.this.editor.commit();
                Intent mIntent = new Intent(FirstGpsActivity.this, ResultGpsActivity.class);
                mIntent.putExtra("sensor_isuse", FirstGpsActivity.this.sensor_isuse);
                mIntent.putExtra("from", FirstGpsActivity.this.from);
                FirstGpsActivity.this.startActivity(mIntent);
                FirstGpsActivity.this.finish();
            } else if ("android.intent.action.SCREEN_OFF".equals(action)) {
                FirstGpsActivity.this.screen_off = true;
            }
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.first_gps);
        this.res = getResources();
        initFilter();
        this.from = getIntent().getStringExtra("from");
        this.ignoreBu = (Button) findViewById(R.id.test_ignore);
        this.ignoreBu.setClickable(true);
        this.mSensorManager = (SensorManager) getSystemService("sensor");
        this.mAccel = this.mSensorManager.getDefaultSensor(1);
        this.sharepreference = getSharedPreferences("gaode_location_info", 0);
        this.ignoreBu.setOnClickListener(new C12541());
        this.sel = new C12552();
        this.mSensorManager.registerListener(this.sel, this.mAccel, 3);
    }

    private void initFilter() {
        IntentFilter mGATTFilter = new IntentFilter();
        mGATTFilter.addAction("android.intent.action.SCREEN_ON");
        mGATTFilter.addAction("android.intent.action.SCREEN_OFF");
        registerReceiver(this.mBroadcastReceiver, mGATTFilter);
    }

    protected void onResume() {
        super.onResume();
    }

    protected void onDestroy() {
        super.onDestroy();
        this.mSensorManager.unregisterListener(this.sel);
        unregisterReceiver(this.mBroadcastReceiver);
    }
}
