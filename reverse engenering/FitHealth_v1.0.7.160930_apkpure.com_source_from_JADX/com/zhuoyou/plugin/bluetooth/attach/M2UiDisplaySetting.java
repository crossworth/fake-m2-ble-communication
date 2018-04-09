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
import com.zhuoyou.plugin.ble.BleManagerService;
import com.zhuoyou.plugin.running.Tools;

public class M2UiDisplaySetting extends Activity implements OnClickListener {
    private boolean isCaloriesOpen;
    private boolean isElectricityOpen;
    private boolean isMileageOpen;
    private String m2UiDisplayInfo = "";
    private RelativeLayout mActionBarBackRl;
    private TextView mActionBarLeftTitleTv;
    private ImageView mCaloriesImg;
    private ImageView mElectricityImg;
    private ImageView mMileageImg;

    class C11721 implements OnClickListener {
        C11721() {
        }

        public void onClick(View v) {
            M2UiDisplaySetting.this.broadM2UiDisplayInfo(M2UiDisplaySetting.this.m2UiDisplayInfo);
            M2UiDisplaySetting.this.finish();
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_m2_ui_display_setting);
        initView();
        initData();
    }

    private void initView() {
        this.mActionBarLeftTitleTv = (TextView) findViewById(R.id.title);
        this.mActionBarBackRl = (RelativeLayout) findViewById(R.id.back);
        this.mMileageImg = (ImageView) findViewById(R.id.mileage_img);
        this.mCaloriesImg = (ImageView) findViewById(R.id.calories_img);
        this.mElectricityImg = (ImageView) findViewById(R.id.electricity_img);
        this.mMileageImg.setOnClickListener(this);
        this.mCaloriesImg.setOnClickListener(this);
        this.mElectricityImg.setOnClickListener(this);
    }

    private void initData() {
        int i;
        int i2 = 1;
        this.isMileageOpen = Tools.getM2MileageSwitch();
        this.isCaloriesOpen = Tools.getM2CaloriesSwitch();
        this.isElectricityOpen = Tools.getM2ElectricitySwitch();
        if (this.isMileageOpen) {
            this.mMileageImg.setImageResource(R.drawable.warn_on);
        } else {
            this.mMileageImg.setImageResource(R.drawable.warn_off);
        }
        if (this.isCaloriesOpen) {
            this.mCaloriesImg.setImageResource(R.drawable.warn_on);
        } else {
            this.mCaloriesImg.setImageResource(R.drawable.warn_off);
        }
        if (this.isElectricityOpen) {
            this.mElectricityImg.setImageResource(R.drawable.warn_on);
        } else {
            this.mElectricityImg.setImageResource(R.drawable.warn_off);
        }
        this.mActionBarLeftTitleTv.setText(R.string.m2_ui_display);
        this.mActionBarBackRl.setOnClickListener(new C11721());
        StringBuilder append = new StringBuilder().append("4|");
        if (Tools.getM2MileageSwitch()) {
            i = 1;
        } else {
            i = 0;
        }
        StringBuilder append2 = append.append(i).append("|").append(Tools.getM2CaloriesSwitch() ? 1 : 0).append("|");
        if (!Tools.getM2ElectricitySwitch()) {
            i2 = 0;
        }
        this.m2UiDisplayInfo = append2.append(i2).append("|").append("|||||||").toString();
    }

    public void onClick(View v) {
        int i = 1;
        StringBuilder append;
        int i2;
        StringBuilder append2;
        switch (v.getId()) {
            case R.id.mileage_img:
                if (this.isMileageOpen) {
                    this.mMileageImg.setImageResource(R.drawable.warn_on);
                    this.isMileageOpen = false;
                    Tools.setM2MileageSwitch(true);
                } else {
                    this.mMileageImg.setImageResource(R.drawable.warn_off);
                    this.isMileageOpen = true;
                    Tools.setM2MileageSwitch(false);
                }
                append = new StringBuilder().append("4|");
                if (Tools.getM2MileageSwitch()) {
                    i2 = 1;
                } else {
                    i2 = 0;
                }
                append2 = append.append(i2).append("|").append(Tools.getM2CaloriesSwitch() ? 1 : 0).append("|");
                if (!Tools.getM2ElectricitySwitch()) {
                    i = 0;
                }
                this.m2UiDisplayInfo = append2.append(i).append("|").append("|||||||").toString();
                return;
            case R.id.calories_img:
                if (this.isCaloriesOpen) {
                    this.mCaloriesImg.setImageResource(R.drawable.warn_on);
                    this.isCaloriesOpen = false;
                    Tools.setM2CaloriesSwitch(true);
                } else {
                    this.mCaloriesImg.setImageResource(R.drawable.warn_off);
                    this.isCaloriesOpen = true;
                    Tools.setM2CaloriesSwitch(false);
                }
                append = new StringBuilder().append("4|");
                if (Tools.getM2MileageSwitch()) {
                    i2 = 1;
                } else {
                    i2 = 0;
                }
                append2 = append.append(i2).append("|").append(Tools.getM2CaloriesSwitch() ? 1 : 0).append("|");
                if (!Tools.getM2ElectricitySwitch()) {
                    i = 0;
                }
                this.m2UiDisplayInfo = append2.append(i).append("|").append("|||||||").toString();
                return;
            case R.id.electricity_img:
                if (this.isElectricityOpen) {
                    this.mElectricityImg.setImageResource(R.drawable.warn_on);
                    this.isElectricityOpen = false;
                    Tools.setM2ElectricitySwitch(true);
                } else {
                    this.mElectricityImg.setImageResource(R.drawable.warn_off);
                    this.isElectricityOpen = true;
                    Tools.setM2ElectricitySwitch(false);
                }
                append = new StringBuilder().append("4|");
                if (Tools.getM2MileageSwitch()) {
                    i2 = 1;
                } else {
                    i2 = 0;
                }
                append2 = append.append(i2).append("|").append(Tools.getM2CaloriesSwitch() ? 1 : 0).append("|");
                if (!Tools.getM2ElectricitySwitch()) {
                    i = 0;
                }
                this.m2UiDisplayInfo = append2.append(i).append("|").append("|||||||").toString();
                return;
            default:
                return;
        }
    }

    private void broadM2UiDisplayInfo(String info) {
        Intent intent = new Intent(BleManagerService.ACTION_UPDATE_SEDENTARY_INFO);
        intent.putExtra("sedentary_info", info);
        sendBroadcast(intent);
    }

    public void onBackPressed() {
        super.onBackPressed();
        Log.i("hph", "onBackPressed m2UiDisplayInfo=" + this.m2UiDisplayInfo);
        broadM2UiDisplayInfo(this.m2UiDisplayInfo);
    }
}
