package com.zhuoyou.plugin.bluetooth.attach;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.fithealth.running.R;
import com.zhuoyou.plugin.bluetooth.data.PreferenceData;
import com.zhuoyou.plugin.bluetooth.service.BluetoothService;
import com.zhuoyou.plugin.running.RunningApp;

public class CallServiceActivity extends Activity implements OnClickListener {
    private static final Context sContext = RunningApp.getInstance().getApplicationContext();
    private static final SharedPreferences sSharedPreferences = PreferenceManager.getDefaultSharedPreferences(sContext);
    private LinearLayout enable_call_service;
    private CheckBox enable_status;
    private TextView enable_summary;

    class C11711 implements OnClickListener {
        C11711() {
        }

        public void onClick(View v) {
            CallServiceActivity.this.finish();
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.call_activity_layout);
        ((TextView) findViewById(R.id.title)).setText(R.string.call_preference_title);
        ((RelativeLayout) findViewById(R.id.back)).setOnClickListener(new C11711());
        this.enable_call_service = (LinearLayout) findViewById(R.id.enable_call_service);
        this.enable_call_service.setOnClickListener(this);
        this.enable_summary = (TextView) findViewById(R.id.enable_summary);
        this.enable_status = (CheckBox) findViewById(R.id.enable_status);
        this.enable_status.setChecked(sSharedPreferences.getBoolean(PreferenceData.PREFERENCE_KEY_CALL, true));
        if (this.enable_status.isChecked()) {
            this.enable_summary.setText(R.string.call_preference_summary_yes);
        } else {
            this.enable_summary.setText(R.string.call_preference_summary_no);
        }
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.enable_call_service:
                BluetoothService service = BluetoothService.getInstance();
                if (service != null) {
                    Editor editor = sSharedPreferences.edit();
                    if (this.enable_status.isChecked()) {
                        this.enable_status.setChecked(false);
                        editor.putBoolean(PreferenceData.PREFERENCE_KEY_CALL, false);
                        this.enable_summary.setText(R.string.call_preference_summary_no);
                        service.stopCallService();
                    } else {
                        this.enable_status.setChecked(true);
                        editor.putBoolean(PreferenceData.PREFERENCE_KEY_CALL, true);
                        this.enable_summary.setText(R.string.call_preference_summary_yes);
                        service.startCallService();
                    }
                    editor.commit();
                    return;
                }
                return;
            default:
                return;
        }
    }
}
