package com.zhuoyou.plugin.bluetooth.attach;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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
import com.zhuoyou.plugin.custom.CustomAlertDialog.Builder;
import com.zhuoyou.plugin.running.RunningApp;

public class NoticationActivity extends Activity implements OnClickListener {
    public static final Intent ACCESSIBILITY_INTENT = new Intent("android.settings.ACCESSIBILITY_SETTINGS");
    private static final Context sContext = RunningApp.getInstance().getApplicationContext();
    private static final SharedPreferences sSharedPreferences = PreferenceManager.getDefaultSharedPreferences(sContext);
    private LinearLayout enable_notifi_service;
    private CheckBox enable_status;
    private TextView enable_summary;
    private LinearLayout select_notifi;
    private LinearLayout show_accessibility_menu;

    class C11731 implements OnClickListener {
        C11731() {
        }

        public void onClick(View v) {
            NoticationActivity.this.finish();
        }
    }

    class C11742 implements DialogInterface.OnClickListener {
        C11742() {
        }

        public void onClick(DialogInterface dialog, int which) {
            dialog.dismiss();
        }
    }

    class C11753 implements DialogInterface.OnClickListener {
        C11753() {
        }

        public void onClick(DialogInterface dialog, int which) {
            dialog.dismiss();
            NoticationActivity.this.startActivity(NoticationActivity.ACCESSIBILITY_INTENT);
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notication_activity_layout);
        ((TextView) findViewById(R.id.title)).setText(R.string.notification_preference_title);
        ((RelativeLayout) findViewById(R.id.back)).setOnClickListener(new C11731());
        this.show_accessibility_menu = (LinearLayout) findViewById(R.id.show_accessibility_menu);
        this.show_accessibility_menu.setOnClickListener(this);
        this.enable_notifi_service = (LinearLayout) findViewById(R.id.enable_notifi_service);
        this.enable_notifi_service.setOnClickListener(this);
        this.select_notifi = (LinearLayout) findViewById(R.id.select_notifi);
        this.select_notifi.setOnClickListener(this);
        this.enable_summary = (TextView) findViewById(R.id.enable_summary);
        this.enable_status = (CheckBox) findViewById(R.id.enable_status);
        this.enable_status.setChecked(sSharedPreferences.getBoolean(PreferenceData.PREFERENCE_KEY_NOTIFI, true));
        if (this.enable_status.isChecked()) {
            this.enable_summary.setText(R.string.notification_preference_summary_yes);
        } else {
            this.enable_summary.setText(R.string.notification_preference_summary_no);
        }
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.show_accessibility_menu:
                startActivity(ACCESSIBILITY_INTENT);
                return;
            case R.id.enable_notifi_service:
                BluetoothService service = BluetoothService.getInstance();
                if (service != null) {
                    Editor editor = sSharedPreferences.edit();
                    if (this.enable_status.isChecked()) {
                        this.enable_status.setChecked(false);
                        editor.putBoolean(PreferenceData.PREFERENCE_KEY_NOTIFI, false);
                        this.enable_summary.setText(R.string.notification_preference_summary_no);
                        service.stopNotificationService();
                    } else {
                        this.enable_status.setChecked(true);
                        editor.putBoolean(PreferenceData.PREFERENCE_KEY_NOTIFI, true);
                        this.enable_summary.setText(R.string.notification_preference_summary_yes);
                        service.startNotificationService();
                        if (!BluetoothService.isNotificationServiceActived()) {
                            showAccessibilityPrompt();
                        }
                    }
                    editor.commit();
                    return;
                }
                return;
            case R.id.select_notifi:
                startActivity(new Intent(this, SelectNotifiActivity.class));
                return;
            default:
                return;
        }
    }

    private void showAccessibilityPrompt() {
        Builder builder = new Builder(this);
        builder.setTitle((int) R.string.accessibility_prompt_title);
        builder.setMessage((int) R.string.accessibility_prompt_content);
        builder.setNegativeButton((int) R.string.cancle, new C11742());
        builder.setPositiveButton((int) R.string.ok, new C11753());
        builder.create().show();
    }
}
