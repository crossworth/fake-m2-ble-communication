package com.zhuoyou.plugin.running;

import android.app.Activity;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.fithealth.running.R;
import com.mcube.lib.ped.PedometerService;
import com.zhuoyou.plugin.custom.CustomAlertDialog.Builder;
import com.zhuoyou.plugin.gps.FirstGpsActivity;

public class DayPedometerActivity extends Activity {
    public static Boolean isOpen = Boolean.valueOf(false);
    private BluetoothDevice currentBleDevice = null;
    private BluetoothDevice currentClassicDevice = null;
    private RelativeLayout im_back;
    private Context mContext;
    private ImageView mEnable;
    private LinearLayout mTestPed;
    private TextView mTitle;
    private String preDeviceAddress;

    class C13551 implements OnClickListener {
        C13551() {
        }

        public void onClick(View v) {
            Intent intent = new Intent(DayPedometerActivity.this, FirstGpsActivity.class);
            intent.putExtra("from", "DayPedometerActivity");
            DayPedometerActivity.this.startActivity(intent);
        }
    }

    class C13562 implements OnClickListener {
        C13562() {
        }

        public void onClick(View v) {
            DayPedometerActivity.this.finish();
        }
    }

    class C13573 implements OnClickListener {
        C13573() {
        }

        public void onClick(View v) {
            DayPedometerActivity.isOpen = Boolean.valueOf(!DayPedometerActivity.isOpen.booleanValue());
            DayPedometerActivity.this.setImageSource(DayPedometerActivity.isOpen);
            DayPedometerActivity.this.phoneStepsServiceSwitch();
        }
    }

    class C13584 implements DialogInterface.OnClickListener {
        C13584() {
        }

        public void onClick(DialogInterface dialog, int which) {
            dialog.dismiss();
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.day_pedometer_layout);
        this.mTestPed = (LinearLayout) findViewById(R.id.test_ped);
        this.mTestPed.setOnClickListener(new C13551());
        this.mEnable = (ImageView) findViewById(R.id.pedometer_enable);
        isOpen = Boolean.valueOf(Tools.getPhonePedState());
        initPhoneStepState();
        this.mTitle = (TextView) findViewById(R.id.title);
        this.mTitle.setText(R.string.day_pedometer);
        this.im_back = (RelativeLayout) findViewById(R.id.back);
        this.im_back.setOnClickListener(new C13562());
    }

    private void initPhoneStepState() {
        this.mEnable.setImageResource(isOpen.booleanValue() ? R.drawable.warn_on : R.drawable.warn_off);
    }

    protected void onResume() {
        super.onResume();
        this.mEnable.setOnClickListener(new C13573());
    }

    private void setImageSource(Boolean isOpen) {
        if (isOpen.booleanValue()) {
            this.mEnable.setImageResource(R.drawable.warn_on);
            startService(new Intent(getApplicationContext(), PedometerService.class));
            isOpen = Boolean.valueOf(true);
            Builder builder = new Builder(this);
            builder.setTitle((int) R.string.alert_title);
            builder.setMessage(getResources().getString(R.string.open_pedometer_lock_screen));
            builder.setPositiveButton((int) R.string.ok, new C13584());
            builder.create().show();
        } else {
            this.mEnable.setImageResource(R.drawable.warn_off);
            stopService(new Intent(getApplicationContext(), PedometerService.class));
            isOpen = Boolean.valueOf(false);
        }
        Tools.setPhonePedState(isOpen.booleanValue());
    }

    private void phoneStepsServiceSwitch() {
        if (isOpen.booleanValue()) {
            startService(new Intent(getApplicationContext(), PedometerService.class));
        }
    }
}
