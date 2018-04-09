package com.zhuoyou.plugin.running.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import com.zhuoyou.plugin.running.C1680R;
import com.zhuoyou.plugin.running.base.BaseActivity;
import com.zhuoyou.plugin.running.tools.SPUtils;
import com.zhuoyou.plugin.running.view.SegmentedGroup;

public class AppSettingActivity extends BaseActivity {
    static final /* synthetic */ boolean $assertionsDisabled = (!AppSettingActivity.class.desiredAssertionStatus());
    private CheckBox cbNotify;
    private CheckBox cbPhonePed;
    private CheckBox cbWifiOnly;
    private OnCheckedChangeListener onCheckedChangeListener = new C16941();

    class C16941 implements OnCheckedChangeListener {
        C16941() {
        }

        public void onCheckedChanged(RadioGroup group, int checkedId) {
            switch (checkedId) {
                case C1680R.id.radio_dis1:
                    SPUtils.setUnitDis(0);
                    return;
                case C1680R.id.radio_dis2:
                    SPUtils.setUnitDis(1);
                    return;
                case C1680R.id.radio_wei1:
                    SPUtils.setUnitWei(0);
                    return;
                case C1680R.id.radio_wei2:
                    SPUtils.setUnitWei(1);
                    return;
                default:
                    return;
            }
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) C1680R.layout.activity_app_setting);
        initView();
    }

    private void initView() {
        SegmentedGroup sgDistance = (SegmentedGroup) findViewById(C1680R.id.segmented_dis_unit);
        SegmentedGroup sgWeight = (SegmentedGroup) findViewById(C1680R.id.segmented_wei_unit);
        this.cbPhonePed = (CheckBox) findViewById(C1680R.id.switch_pedometer);
        this.cbNotify = (CheckBox) findViewById(C1680R.id.switch_enable_notify);
        this.cbWifiOnly = (CheckBox) findViewById(C1680R.id.switch_only_wifi);
        if ($assertionsDisabled || sgDistance != null) {
            sgDistance.setOnCheckedChangeListener(this.onCheckedChangeListener);
            if ($assertionsDisabled || sgWeight != null) {
                sgWeight.setOnCheckedChangeListener(this.onCheckedChangeListener);
                sgDistance.check(SPUtils.getUnitDis() == 0 ? C1680R.id.radio_dis1 : C1680R.id.radio_dis2);
                sgWeight.check(SPUtils.getUnitWei() == 0 ? C1680R.id.radio_wei1 : C1680R.id.radio_wei2);
                this.cbPhonePed.setChecked(SPUtils.isPhonePed());
                this.cbNotify.setChecked(SPUtils.isShowNotify());
                this.cbWifiOnly.setChecked(SPUtils.isWifiOnly());
                return;
            }
            throw new AssertionError();
        }
        throw new AssertionError();
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case C1680R.id.switch_pedometer:
                SPUtils.setPhonePed(this.cbPhonePed.isChecked());
                return;
            case C1680R.id.tv_test_stepcounter:
                startActivity(new Intent(this, PedTestActivity.class));
                return;
            case C1680R.id.btn_accessibility:
                startActivity(new Intent("android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS"));
                return;
            case C1680R.id.switch_enable_notify:
                SPUtils.setShowNotify(this.cbNotify.isChecked());
                return;
            case C1680R.id.switch_only_wifi:
                SPUtils.setWifiOnly(this.cbWifiOnly.isChecked());
                return;
            default:
                return;
        }
    }
}
