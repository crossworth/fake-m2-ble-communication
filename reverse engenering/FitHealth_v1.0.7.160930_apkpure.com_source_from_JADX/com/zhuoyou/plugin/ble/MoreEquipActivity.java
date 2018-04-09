package com.zhuoyou.plugin.ble;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.fithealth.running.R;

public class MoreEquipActivity extends Activity implements OnClickListener {
    private ImageView mBack;
    private Button mFindMore;
    private TextView mTitle;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.more_equip_activity);
        this.mFindMore = (Button) findViewById(R.id.find_more_equip);
        this.mFindMore.setOnClickListener(this);
        initView();
    }

    private void initView() {
        this.mTitle = (TextView) findViewById(R.id.title);
        this.mTitle.setText(R.string.more_equip);
        this.mBack = (ImageView) findViewById(R.id.back_m);
        this.mBack.setOnClickListener(this);
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_m:
                finish();
                return;
            case R.id.find_more_equip:
                if (BindBleDeviceActivity.instance != null) {
                    BindBleDeviceActivity.instance.finish();
                }
                startActivity(new Intent("android.settings.BLUETOOTH_SETTINGS"));
                finish();
                return;
            default:
                return;
        }
    }
}
