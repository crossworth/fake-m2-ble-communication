package com.zhuoyou.plugin.resideMenu;

import android.app.Activity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.fithealth.running.R;

public class BluetoothHeadset extends Activity {

    class C13271 implements OnClickListener {
        C13271() {
        }

        public void onClick(View v) {
            BluetoothHeadset.this.finish();
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bluetooth_headset2);
        ((TextView) findViewById(R.id.title)).setText(R.string.bluetooth_headset);
        ((RelativeLayout) findViewById(R.id.back)).setOnClickListener(new C13271());
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() != 16908332) {
            return super.onOptionsItemSelected(item);
        }
        finish();
        overridePendingTransition(R.anim.in_from_left, R.anim.out_to_right);
        return true;
    }
}
