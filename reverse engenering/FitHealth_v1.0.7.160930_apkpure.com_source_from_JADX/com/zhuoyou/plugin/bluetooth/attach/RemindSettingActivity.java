package com.zhuoyou.plugin.bluetooth.attach;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.fithealth.running.R;
import com.zhuoyou.plugin.running.Tools;

public class RemindSettingActivity extends Activity implements OnClickListener {
    private boolean isElectricityReimnd;
    private boolean isMileageReimnd;
    private boolean isSaveElectricity = false;
    private boolean isWholePointReind;
    private ImageView mElectricityRemindImg;
    private ImageView mMileageRemindImg;
    private ImageView mSaveElectrictyImg;
    private ImageView mWholePointImg;

    class C11781 implements OnClickListener {
        C11781() {
        }

        public void onClick(View v) {
            int i;
            int i2 = 1;
            RemindSettingActivity remindSettingActivity = RemindSettingActivity.this;
            StringBuilder stringBuilder = new StringBuilder();
            if (Tools.getElectricityRemind()) {
                i = 1;
            } else {
                i = 0;
            }
            stringBuilder = stringBuilder.append(i).append("|").append(Tools.getWholePointRemind() ? 1 : 0).append("|");
            if (Tools.getMileageRemind()) {
                i = 1;
            } else {
                i = 0;
            }
            StringBuilder append = stringBuilder.append(i).append("|");
            if (!Tools.getSaveElectricity()) {
                i2 = 0;
            }
            remindSettingActivity.broadAlarmInfo(append.append(i2).append("|").toString());
            RemindSettingActivity.this.finish();
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.remind_setting_layout);
        initView();
        initData();
    }

    private void initView() {
        ((TextView) findViewById(R.id.title)).setText(R.string.remind_setting_title);
        ((RelativeLayout) findViewById(R.id.back)).setOnClickListener(new C11781());
        this.mElectricityRemindImg = (ImageView) findViewById(R.id.electricity_remind_img);
        this.mWholePointImg = (ImageView) findViewById(R.id.whole_point_remind_img);
        this.mMileageRemindImg = (ImageView) findViewById(R.id.mileage_remind_img);
        this.mSaveElectrictyImg = (ImageView) findViewById(R.id.save_electricity_mode_img);
        this.mElectricityRemindImg.setOnClickListener(this);
        this.mWholePointImg.setOnClickListener(this);
        this.mMileageRemindImg.setOnClickListener(this);
        this.mSaveElectrictyImg.setOnClickListener(this);
    }

    private void initData() {
        if (Tools.getElectricityRemind()) {
            this.mElectricityRemindImg.setImageResource(R.drawable.alarm_button_openon);
        } else {
            this.mElectricityRemindImg.setImageResource(R.drawable.alarm_button_closeoff);
        }
        if (Tools.getWholePointRemind()) {
            this.mWholePointImg.setImageResource(R.drawable.alarm_button_openon);
        } else {
            this.mWholePointImg.setImageResource(R.drawable.alarm_button_closeoff);
        }
        if (Tools.getMileageRemind()) {
            this.mMileageRemindImg.setImageResource(R.drawable.alarm_button_openon);
        } else {
            this.mMileageRemindImg.setImageResource(R.drawable.alarm_button_closeoff);
        }
        if (Tools.getSaveElectricity()) {
            this.mSaveElectrictyImg.setImageResource(R.drawable.alarm_button_openon);
        } else {
            this.mSaveElectrictyImg.setImageResource(R.drawable.alarm_button_closeoff);
        }
    }

    public void onClick(View v) {
        int i = 1;
        StringBuilder stringBuilder;
        int i2;
        StringBuilder append;
        switch (v.getId()) {
            case R.id.electricity_remind_img:
                if (this.isElectricityReimnd) {
                    this.mElectricityRemindImg.setImageResource(R.drawable.alarm_button_openon);
                    this.isElectricityReimnd = false;
                    Tools.setElectricityRemind(true);
                } else {
                    this.mElectricityRemindImg.setImageResource(R.drawable.alarm_button_closeoff);
                    this.isElectricityReimnd = true;
                    Tools.setElectricityRemind(false);
                }
                stringBuilder = new StringBuilder();
                if (Tools.getElectricityRemind()) {
                    i2 = 1;
                } else {
                    i2 = 0;
                }
                stringBuilder = stringBuilder.append(i2).append("|").append(Tools.getWholePointRemind() ? 1 : 0).append("|");
                if (Tools.getMileageRemind()) {
                    i2 = 1;
                } else {
                    i2 = 0;
                }
                append = stringBuilder.append(i2).append("|");
                if (!Tools.getSaveElectricity()) {
                    i = 0;
                }
                broadAlarmInfo(append.append(i).append("|").toString());
                return;
            case R.id.whole_point_remind_img:
                if (this.isWholePointReind) {
                    this.mWholePointImg.setImageResource(R.drawable.alarm_button_openon);
                    this.isWholePointReind = false;
                    Tools.setWholePointRemind(true);
                } else {
                    this.mWholePointImg.setImageResource(R.drawable.alarm_button_closeoff);
                    this.isWholePointReind = true;
                    Tools.setWholePointRemind(false);
                }
                stringBuilder = new StringBuilder();
                if (Tools.getElectricityRemind()) {
                    i2 = 1;
                } else {
                    i2 = 0;
                }
                stringBuilder = stringBuilder.append(i2).append("|").append(Tools.getWholePointRemind() ? 1 : 0).append("|");
                if (Tools.getMileageRemind()) {
                    i2 = 1;
                } else {
                    i2 = 0;
                }
                append = stringBuilder.append(i2).append("|");
                if (!Tools.getSaveElectricity()) {
                    i = 0;
                }
                broadAlarmInfo(append.append(i).append("|").toString());
                return;
            case R.id.mileage_remind_img:
                if (this.isMileageReimnd) {
                    this.mMileageRemindImg.setImageResource(R.drawable.alarm_button_openon);
                    this.isMileageReimnd = false;
                    Tools.setMileageRemind(true);
                } else {
                    this.mMileageRemindImg.setImageResource(R.drawable.alarm_button_closeoff);
                    this.isMileageReimnd = true;
                    Tools.setMileageRemind(false);
                }
                stringBuilder = new StringBuilder();
                if (Tools.getElectricityRemind()) {
                    i2 = 1;
                } else {
                    i2 = 0;
                }
                stringBuilder = stringBuilder.append(i2).append("|").append(Tools.getWholePointRemind() ? 1 : 0).append("|");
                if (Tools.getMileageRemind()) {
                    i2 = 1;
                } else {
                    i2 = 0;
                }
                append = stringBuilder.append(i2).append("|");
                if (!Tools.getSaveElectricity()) {
                    i = 0;
                }
                broadAlarmInfo(append.append(i).append("|").toString());
                return;
            case R.id.save_electricity_mode_img:
                if (this.isSaveElectricity) {
                    this.mSaveElectrictyImg.setImageResource(R.drawable.alarm_button_openon);
                    this.isSaveElectricity = false;
                    Tools.setSaveElectricity(true);
                } else {
                    this.mSaveElectrictyImg.setImageResource(R.drawable.alarm_button_closeoff);
                    this.isSaveElectricity = true;
                    Tools.setSaveElectricity(false);
                }
                stringBuilder = new StringBuilder();
                if (Tools.getElectricityRemind()) {
                    i2 = 1;
                } else {
                    i2 = 0;
                }
                stringBuilder = stringBuilder.append(i2).append("|").append(Tools.getWholePointRemind() ? 1 : 0).append("|");
                if (Tools.getMileageRemind()) {
                    i2 = 1;
                } else {
                    i2 = 0;
                }
                append = stringBuilder.append(i2).append("|");
                if (!Tools.getSaveElectricity()) {
                    i = 0;
                }
                broadAlarmInfo(append.append(i).append("|").toString());
                return;
            default:
                return;
        }
    }

    private void broadAlarmInfo(String info) {
        Intent intent = new Intent("com.tyd.plugin.receiver.sendmsg");
        intent.putExtra("plugin_cmd", 96);
        intent.putExtra("plugin_content", info);
        sendBroadcast(intent);
        Log.i("hph123", "plugin_content=" + info);
    }

    public void onBackPressed() {
        int i;
        int i2 = 1;
        super.onBackPressed();
        StringBuilder stringBuilder = new StringBuilder();
        if (Tools.getElectricityRemind()) {
            i = 1;
        } else {
            i = 0;
        }
        stringBuilder = stringBuilder.append(i).append("|").append(Tools.getWholePointRemind() ? 1 : 0).append("|");
        if (Tools.getMileageRemind()) {
            i = 1;
        } else {
            i = 0;
        }
        StringBuilder append = stringBuilder.append(i).append("|");
        if (!Tools.getSaveElectricity()) {
            i2 = 0;
        }
        broadAlarmInfo(append.append(i2).append("|").toString());
    }
}
