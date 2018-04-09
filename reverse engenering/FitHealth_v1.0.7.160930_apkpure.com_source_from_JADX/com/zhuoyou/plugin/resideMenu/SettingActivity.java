package com.zhuoyou.plugin.resideMenu;

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
import com.zhuoyou.plugin.bluetooth.attach.NoticationActivity;
import com.zhuoyou.plugin.bluetooth.data.PreferenceData;
import com.zhuoyou.plugin.bluetooth.service.BluetoothService;
import com.zhuoyou.plugin.running.RunningApp;

public class SettingActivity extends Activity implements OnClickListener {
    private LinearLayout always_forward;
    private CheckBox forward_enable_status;
    private TextView forward_enable_summary;
    private Context sContext = RunningApp.getInstance().getApplicationContext();
    private SharedPreferences sSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this.sContext);
    private LinearLayout show_accessibility_menu;
    private LinearLayout show_connection_status;
    private CheckBox show_enable_status;
    private TextView show_enable_summary;

    class C13441 implements OnClickListener {
        C13441() {
        }

        public void onClick(View v) {
            SettingActivity.this.finish();
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting_activity_layout);
        ((TextView) findViewById(R.id.title)).setText(R.string.setting);
        ((RelativeLayout) findViewById(R.id.back)).setOnClickListener(new C13441());
        this.show_accessibility_menu = (LinearLayout) findViewById(R.id.show_accessibility_menu);
        this.show_accessibility_menu.setOnClickListener(this);
        this.show_connection_status = (LinearLayout) findViewById(R.id.show_connection_status);
        this.show_connection_status.setOnClickListener(this);
        this.show_enable_summary = (TextView) findViewById(R.id.show_enable_summary);
        this.show_enable_status = (CheckBox) findViewById(R.id.show_enable_status);
        this.show_enable_status.setChecked(this.sSharedPreferences.getBoolean(PreferenceData.PREFERENCE_KEY_SHOW_CONNECTION_STATUS, true));
        if (this.show_enable_status.isChecked()) {
            this.show_enable_summary.setText(R.string.show_connect_status_preference_summary_yes);
        } else {
            this.show_enable_summary.setText(R.string.show_connect_status_preference_summary_no);
        }
        this.always_forward = (LinearLayout) findViewById(R.id.always_forward);
        this.always_forward.setOnClickListener(this);
        this.forward_enable_summary = (TextView) findViewById(R.id.forward_enable_summary);
        this.forward_enable_status = (CheckBox) findViewById(R.id.forward_enable_status);
        this.forward_enable_status.setChecked(this.sSharedPreferences.getBoolean(PreferenceData.PREFERENCE_KEY_ALWAYS_FORWARD, true));
        if (this.forward_enable_status.isChecked()) {
            this.forward_enable_summary.setText(R.string.always_forward_preference_summary);
        } else {
            this.forward_enable_summary.setText(R.string.lock_forward_preference_summary);
        }
    }

    public void onClick(View v) {
        Editor editor;
        switch (v.getId()) {
            case R.id.show_accessibility_menu:
                startActivity(NoticationActivity.ACCESSIBILITY_INTENT);
                return;
            case R.id.show_connection_status:
                BluetoothService service = BluetoothService.getInstance();
                if (service != null) {
                    editor = this.sSharedPreferences.edit();
                    if (this.show_enable_status.isChecked()) {
                        this.show_enable_status.setChecked(false);
                        editor.putBoolean(PreferenceData.PREFERENCE_KEY_SHOW_CONNECTION_STATUS, false);
                        this.show_enable_summary.setText(R.string.show_connect_status_preference_summary_no);
                    } else {
                        this.show_enable_status.setChecked(true);
                        editor.putBoolean(PreferenceData.PREFERENCE_KEY_SHOW_CONNECTION_STATUS, true);
                        this.show_enable_summary.setText(R.string.show_connect_status_preference_summary_yes);
                    }
                    editor.commit();
                    service.updateConnectionStatus(false, 0, 0, 0);
                    return;
                }
                return;
            case R.id.always_forward:
                editor = this.sSharedPreferences.edit();
                if (this.forward_enable_status.isChecked()) {
                    this.forward_enable_status.setChecked(false);
                    editor.putBoolean(PreferenceData.PREFERENCE_KEY_ALWAYS_FORWARD, false);
                    this.forward_enable_summary.setText(R.string.lock_forward_preference_summary);
                } else {
                    this.forward_enable_status.setChecked(true);
                    editor.putBoolean(PreferenceData.PREFERENCE_KEY_ALWAYS_FORWARD, true);
                    this.forward_enable_summary.setText(R.string.always_forward_preference_summary);
                }
                editor.commit();
                return;
            default:
                return;
        }
    }
}
