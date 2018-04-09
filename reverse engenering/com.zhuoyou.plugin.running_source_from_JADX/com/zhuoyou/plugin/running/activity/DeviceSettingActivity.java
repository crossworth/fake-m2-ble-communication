package com.zhuoyou.plugin.running.activity;

import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.droi.btlib.service.BtDevice;
import com.droi.btlib.service.BtDevice.ConnectCallback;
import com.droi.btlib.service.BtManagerService;
import com.zhuoyou.plugin.running.C1680R;
import com.zhuoyou.plugin.running.base.BaseActivity;
import com.zhuoyou.plugin.running.service.NotificationServiceNew;
import com.zhuoyou.plugin.running.tools.SPUtils;
import com.zhuoyou.plugin.running.tools.SPUtils.SharePrefrenceChange;
import com.zhuoyou.plugin.running.view.MyAlertDialog;
import com.zhuoyou.plugin.running.view.MyAlertDialog.OnClickListener;
import java.text.DecimalFormat;
import org.greenrobot.eventbus.Subscribe;

public class DeviceSettingActivity extends BaseActivity {
    private static final String TAG = "DeviceMineActivity";
    private BtDevice btDeivce;
    private ConnectCallback callback = new C17204();
    private CheckBox cbCallRemind;
    private CheckBox cbDisconnectRemind;
    private CheckBox cbSmsRemind;
    private CheckBox cbSportTarget;
    private CheckBox cbWristLift;
    private Runnable dialogDismiss = new C17193();
    private boolean isSportTarget;
    private boolean isWristLift;
    private LinearLayout llAlarmLayout;
    private LinearLayout llAntilostLayout;
    private LinearLayout llDeviceSetting;
    private LinearLayout llNoticeLayout;
    private MyAlertDialog mFindBraceletDialog;
    private Handler mHandler = new Handler();
    private RelativeLayout rlDisturbanceMode;
    private RelativeLayout rlFindBracelet;
    private RelativeLayout rlFindPhone;
    private RelativeLayout rlInCallRemind;
    private RelativeLayout rlPushMessage;
    private RelativeLayout rlSedentaryRemind;
    private RelativeLayout rlSmsRemind;
    private RelativeLayout rlSportTarget;
    private RelativeLayout rlWristLift;
    private TextView tvDisturbanceModeSwitch;
    private TextView tvLightSleepAlarm;
    private TextView tvPushMessageNum;
    private TextView tvSedentaryRemind;

    class C17171 implements OnClickListener {
        C17171() {
        }

        public void onClick(int witch) {
            DeviceSettingActivity.this.mFindBraceletDialog.dismiss();
        }
    }

    class C17182 implements OnDismissListener {
        C17182() {
        }

        public void onDismiss(DialogInterface dialog) {
            if (DeviceSettingActivity.this.btDeivce != null) {
                DeviceSettingActivity.this.btDeivce.endFindBracelet();
            }
        }
    }

    class C17193 implements Runnable {
        C17193() {
        }

        public void run() {
            DeviceSettingActivity.this.mFindBraceletDialog.dismiss();
        }
    }

    class C17204 implements ConnectCallback {
        C17204() {
        }

        public void success(BtDevice device) {
            DeviceSettingActivity.this.btDeivce = device;
            DeviceSettingActivity.this.initData();
        }

        public void fail(int state) {
            switch (state) {
                case 0:
                    DeviceSettingActivity.this.initData();
                    return;
                case 2:
                    DeviceSettingActivity.this.initData();
                    return;
                default:
                    return;
            }
        }

        public void disconnect(BtDevice device) {
            DeviceSettingActivity.this.finish();
        }

        public void connecting(BtDevice device) {
        }

        public void battery(int battery) {
        }
    }

    class C17215 implements OnClickListener {
        C17215() {
        }

        public void onClick(int witch) {
            DeviceSettingActivity.this.startActivity(new Intent("android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS"));
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) C1680R.layout.activity_device_setting);
        this.btDeivce = BtManagerService.getConnectDevice(this.callback);
        initView();
        initData();
    }

    private void initView() {
        this.llNoticeLayout = (LinearLayout) findViewById(C1680R.id.ll_notice_layout);
        this.llAlarmLayout = (LinearLayout) findViewById(C1680R.id.ll_alarm_layout);
        this.llAntilostLayout = (LinearLayout) findViewById(C1680R.id.ll_antilost_layout);
        this.tvLightSleepAlarm = (TextView) findViewById(C1680R.id.tv_light_sleep_alarm);
        this.cbCallRemind = (CheckBox) findViewById(C1680R.id.cb_in_call);
        this.cbSmsRemind = (CheckBox) findViewById(C1680R.id.cb_sms);
        this.cbDisconnectRemind = (CheckBox) findViewById(C1680R.id.cb_antilost);
        this.rlInCallRemind = (RelativeLayout) findViewById(C1680R.id.rl_in_call_remind);
        this.rlSmsRemind = (RelativeLayout) findViewById(C1680R.id.rl_sms_remind);
        this.rlPushMessage = (RelativeLayout) findViewById(C1680R.id.rl_push_message);
        this.cbSportTarget = (CheckBox) findViewById(C1680R.id.cb_sport_target);
        this.rlDisturbanceMode = (RelativeLayout) findViewById(C1680R.id.rl_disturbance_mode);
        this.cbWristLift = (CheckBox) findViewById(C1680R.id.cb_wrist_lift);
        this.rlSedentaryRemind = (RelativeLayout) findViewById(C1680R.id.rl_sedentary_notice);
        this.tvSedentaryRemind = (TextView) findViewById(C1680R.id.tv_sedentary_remind);
        this.tvPushMessageNum = (TextView) findViewById(C1680R.id.tv_push_message_num);
        this.rlSportTarget = (RelativeLayout) findViewById(C1680R.id.rl_sport_target);
        this.llDeviceSetting = (LinearLayout) findViewById(C1680R.id.ll_device_display_layout);
        this.rlWristLift = (RelativeLayout) findViewById(C1680R.id.rl_wrist_lift);
        this.tvDisturbanceModeSwitch = (TextView) findViewById(C1680R.id.tv_distarbance_mode_switch);
        this.rlFindBracelet = (RelativeLayout) findViewById(C1680R.id.rl_find_bracelet);
        this.rlFindPhone = (RelativeLayout) findViewById(C1680R.id.rl_find_phone);
        this.mFindBraceletDialog = new MyAlertDialog(this);
        this.mFindBraceletDialog.setMessage((int) C1680R.string.find_bracelet_message);
        this.mFindBraceletDialog.setTitle((int) C1680R.string.find_bracelet);
        this.mFindBraceletDialog.setLeftButton((int) C1680R.string.find_bracelet_confirm, new C17171());
        this.mFindBraceletDialog.setDismissListener(new C17182());
    }

    private void initData() {
        if (this.btDeivce != null) {
            int openAlarm = 0;
            for (int i = 0; i < 3; i++) {
                if (SPUtils.getAlarm(i).isOpen()) {
                    openAlarm++;
                }
            }
            String alarmOpen = getResources().getString(C1680R.string.alarm_open_num);
            if (openAlarm == 0) {
                this.tvLightSleepAlarm.setText(C1680R.string.not_open);
            } else {
                this.tvLightSleepAlarm.setText(String.format(alarmOpen, new Object[]{Integer.valueOf(openAlarm)}));
            }
            this.isSportTarget = SPUtils.getSportTargetReminderSwitch();
            this.isWristLift = SPUtils.getM2WristBrightScreenSwitch();
            this.cbCallRemind.setChecked(BtManagerService.getCallRemind());
            this.cbSmsRemind.setChecked(BtManagerService.getSMSRemind());
            this.cbDisconnectRemind.setChecked(BtManagerService.getDisconnectRemind());
            this.cbSportTarget.setChecked(this.isSportTarget);
            this.cbWristLift.setChecked(this.isWristLift);
            if (!(this.btDeivce.supportCallRemind() || this.btDeivce.supportSmsRemind() || this.btDeivce.supportPushRemind())) {
                this.llNoticeLayout.setVisibility(8);
            }
            if (!this.btDeivce.supportCallRemind()) {
                this.rlInCallRemind.setVisibility(8);
            }
            if (!this.btDeivce.supportSmsRemind()) {
                this.rlSmsRemind.setVisibility(8);
            }
            if (!this.btDeivce.supportPushRemind()) {
                this.rlPushMessage.setVisibility(8);
            }
            if (!this.btDeivce.supportAlarm()) {
                this.llAlarmLayout.setVisibility(8);
            }
            if (!(this.btDeivce.supportAntilost() || this.btDeivce.supportFindBracelet())) {
                this.llAntilostLayout.setVisibility(8);
            }
            if (!this.btDeivce.supportSportTarget()) {
                this.rlSportTarget.setVisibility(8);
            }
            if (!this.btDeivce.supportDisturbanceMode()) {
                this.rlDisturbanceMode.setVisibility(8);
            }
            Log.i("hph", "supportSedentaryRemind=" + this.btDeivce.supportSedentaryRemind());
            if (!this.btDeivce.supportSedentaryRemind()) {
                this.rlSedentaryRemind.setVisibility(8);
            }
            if (!this.btDeivce.supportDisplaySetting()) {
                this.llDeviceSetting.setVisibility(8);
            }
            if (!this.btDeivce.supportWristLift()) {
                this.rlWristLift.setVisibility(8);
            }
            if (!this.btDeivce.supportFindBracelet()) {
                this.rlFindBracelet.setVisibility(8);
            }
            if (!this.btDeivce.supportAntilost()) {
                this.rlFindPhone.setVisibility(8);
            }
            if (NotificationServiceNew.isAccessibilitySettingsOn(this)) {
                int size = SPUtils.getList("droi_share_push_list").size();
                Log.i("yuanzz", "device get size:" + size);
                if (size > 0) {
                    this.tvPushMessageNum.setText(String.format(getString(C1680R.string.alarm_open_num), new Object[]{Integer.valueOf(size)}));
                } else {
                    this.tvPushMessageNum.setText(C1680R.string.not_open);
                }
            } else {
                this.tvPushMessageNum.setText(C1680R.string.not_open);
            }
            if (SPUtils.getDisturbanceModleSwitch()) {
                this.tvDisturbanceModeSwitch.setText(C1680R.string.open_mode);
            } else {
                this.tvDisturbanceModeSwitch.setText(C1680R.string.not_open);
            }
            if (SPUtils.getSedentaryRemindSwitch()) {
                this.tvSedentaryRemind.setText(C1680R.string.open_mode);
            } else {
                this.tvSedentaryRemind.setText(C1680R.string.not_open);
            }
        }
    }

    protected void onDestroy() {
        super.onDestroy();
        BtManagerService.removeConnectCallback(this.callback);
    }

    public void onClick(View v) {
        int i = 1;
        Intent it = new Intent();
        StringBuilder append;
        switch (v.getId()) {
            case C1680R.id.rl_push_message:
                if (NotificationServiceNew.isAccessibilitySettingsOn(this)) {
                    startActivity(new Intent(this, PushMessageActivity.class));
                    return;
                }
                MyAlertDialog dialog = new MyAlertDialog(this);
                dialog.setLeftButton((int) C1680R.string.string_ok, new C17215());
                dialog.setRightButton((int) C1680R.string.string_cancel, null);
                dialog.setMessage((int) C1680R.string.accessibility_preference_summary);
                dialog.setTitle((int) C1680R.string.accessibility_check_permission);
                dialog.show();
                return;
            case C1680R.id.cb_sport_target:
                this.isSportTarget = this.cbSportTarget.isChecked();
                if (this.isSportTarget) {
                    SPUtils.setSportTargetReminderSwitch(true);
                } else {
                    SPUtils.setSportTargetReminderSwitch(false);
                }
                append = new StringBuilder().append("2|");
                if (!SPUtils.getSportTargetReminderSwitch()) {
                    i = 0;
                }
                String sportTargetRemindInfo = append.append(i).append("|").append(decimalFormatTarget(SPUtils.getTargetStep())).append("||||||").toString();
                if (this.btDeivce != null) {
                    this.btDeivce.setSedentary(sportTargetRemindInfo);
                    return;
                }
                return;
            case C1680R.id.cb_in_call:
                BtManagerService.setCallRemind(this.cbCallRemind.isChecked());
                return;
            case C1680R.id.cb_sms:
                BtManagerService.setSMSRemind(this.cbSmsRemind.isChecked());
                return;
            case C1680R.id.rl_disturbance_mode:
                startActivity(new Intent(this, AaronLiModelActivity.class));
                return;
            case C1680R.id.cb_wrist_lift:
                this.isWristLift = this.cbWristLift.isChecked();
                if (this.isWristLift) {
                    SPUtils.setM2WristBrightScreenSwitch(true);
                } else {
                    SPUtils.setM2WristBrightScreenSwitch(false);
                }
                append = new StringBuilder().append("5|");
                if (!SPUtils.getM2WristBrightScreenSwitch()) {
                    i = 0;
                }
                String wristBrightScreenInfo = append.append(i).append("||||||||||||").toString();
                if (this.btDeivce != null) {
                    this.btDeivce.setSedentary(wristBrightScreenInfo);
                    return;
                }
                return;
            case C1680R.id.rl_display_setting:
                startActivity(new Intent(this, M2UiDisplaySetting.class));
                return;
            case C1680R.id.rl_light_sleep_alarm:
                it.setClass(this, AlarmActivity.class);
                startActivity(it);
                return;
            case C1680R.id.rl_sedentary_notice:
                startActivity(new Intent(this, SedentaryReminderM2Activity.class));
                return;
            case C1680R.id.cb_antilost:
                BtManagerService.setDisconnectRemind(this.cbDisconnectRemind.isChecked());
                return;
            case C1680R.id.rl_find_bracelet:
                if (!this.mFindBraceletDialog.isShowing()) {
                    this.mFindBraceletDialog.show();
                    this.mHandler.postDelayed(this.dialogDismiss, StatisticConfig.MIN_UPLOAD_INTERVAL);
                    if (this.btDeivce != null) {
                        this.btDeivce.startFindBracelet();
                        return;
                    }
                    return;
                }
                return;
            default:
                return;
        }
    }

    protected void onResume() {
        super.onResume();
        initData();
    }

    @Subscribe
    public void onEventMainThread(SharePrefrenceChange event) {
        Log.i("yuanzz", "onEventMainThread");
        initData();
    }

    public void onBackPressed() {
        super.onBackPressed();
    }

    private String decimalFormatTarget(int step) {
        return new DecimalFormat("#00000").format((long) step);
    }
}
