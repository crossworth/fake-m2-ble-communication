package com.zhuoyou.plugin.running.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import com.droi.btlib.service.BtDevice;
import com.droi.btlib.service.BtManagerService;
import com.zhuoyou.plugin.running.C1680R;
import com.zhuoyou.plugin.running.base.BaseActivity;
import com.zhuoyou.plugin.running.tools.SPUtils;

public class M2UiDisplaySetting extends BaseActivity implements OnClickListener {
    private CheckBox cbCalories;
    private CheckBox cbElectricity;
    private CheckBox cbMileage;
    private boolean isCaloriesOpen;
    private boolean isElectricityOpen;
    private boolean isMileageOpen;
    private RelativeLayout layoutBattery;
    private String m2UiDisplayInfo = "";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) C1680R.layout.activity_m2_ui_display_setting);
        initView();
        initData();
    }

    private void initView() {
        this.cbMileage = (CheckBox) findViewById(C1680R.id.cb_mileage);
        this.cbCalories = (CheckBox) findViewById(C1680R.id.cb_calories);
        this.cbElectricity = (CheckBox) findViewById(C1680R.id.cb_electricity);
        this.layoutBattery = (RelativeLayout) findViewById(C1680R.id.layout_battery);
        BtDevice device = BtManagerService.getConnectDevice(null);
        if (device != null && device.getName().contains("U3")) {
            this.layoutBattery.setVisibility(8);
        }
    }

    private void initData() {
        int i;
        int i2 = 1;
        this.isMileageOpen = SPUtils.getM2MileageSwitch();
        this.isCaloriesOpen = SPUtils.getM2CaloriesSwitch();
        this.isElectricityOpen = SPUtils.getM2ElectricitySwitch();
        this.cbMileage.setChecked(this.isMileageOpen);
        this.cbCalories.setChecked(this.isCaloriesOpen);
        this.cbElectricity.setChecked(this.isElectricityOpen);
        StringBuilder append = new StringBuilder().append("4|");
        if (SPUtils.getM2MileageSwitch()) {
            i = 1;
        } else {
            i = 0;
        }
        StringBuilder append2 = append.append(i).append("|").append(SPUtils.getM2CaloriesSwitch() ? 1 : 0).append("|");
        if (!SPUtils.getM2ElectricitySwitch()) {
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
            case C1680R.id.cb_mileage:
                this.isMileageOpen = this.cbMileage.isChecked();
                if (this.isMileageOpen) {
                    SPUtils.setM2MileageSwitch(true);
                } else {
                    SPUtils.setM2MileageSwitch(false);
                }
                append = new StringBuilder().append("4|");
                if (SPUtils.getM2MileageSwitch()) {
                    i2 = 1;
                } else {
                    i2 = 0;
                }
                append2 = append.append(i2).append("|").append(SPUtils.getM2CaloriesSwitch() ? 1 : 0).append("|");
                if (!SPUtils.getM2ElectricitySwitch()) {
                    i = 0;
                }
                this.m2UiDisplayInfo = append2.append(i).append("|").append("|||||||").toString();
                return;
            case C1680R.id.cb_calories:
                this.isCaloriesOpen = this.cbCalories.isChecked();
                if (this.isCaloriesOpen) {
                    SPUtils.setM2CaloriesSwitch(true);
                } else {
                    SPUtils.setM2CaloriesSwitch(false);
                }
                append = new StringBuilder().append("4|");
                if (SPUtils.getM2MileageSwitch()) {
                    i2 = 1;
                } else {
                    i2 = 0;
                }
                append2 = append.append(i2).append("|").append(SPUtils.getM2CaloriesSwitch() ? 1 : 0).append("|");
                if (!SPUtils.getM2ElectricitySwitch()) {
                    i = 0;
                }
                this.m2UiDisplayInfo = append2.append(i).append("|").append("|||||||").toString();
                return;
            case C1680R.id.cb_electricity:
                this.isElectricityOpen = this.cbElectricity.isChecked();
                if (this.isElectricityOpen) {
                    SPUtils.setM2ElectricitySwitch(true);
                } else {
                    SPUtils.setM2ElectricitySwitch(false);
                }
                append = new StringBuilder().append("4|");
                if (SPUtils.getM2MileageSwitch()) {
                    i2 = 1;
                } else {
                    i2 = 0;
                }
                append2 = append.append(i2).append("|").append(SPUtils.getM2CaloriesSwitch() ? 1 : 0).append("|");
                if (!SPUtils.getM2ElectricitySwitch()) {
                    i = 0;
                }
                this.m2UiDisplayInfo = append2.append(i).append("|").append("|||||||").toString();
                return;
            default:
                return;
        }
    }

    public void onBackPressed() {
        super.onBackPressed();
        Log.i("hph", "onBackPressed m2UiDisplayInfo=" + this.m2UiDisplayInfo);
        BtDevice device = BtManagerService.getConnectDevice(null);
        if (device != null) {
            device.setSedentary(this.m2UiDisplayInfo);
        }
    }
}
