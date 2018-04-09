package com.zhuoyou.plugin.resideMenu;

import android.app.Activity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.fithealth.running.R;

public class BluetoothWatchActivity extends Activity {

    class C13291 implements OnClickListener {
        C13291() {
        }

        public void onClick(View v) {
            BluetoothWatchActivity.this.finish();
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bluetooth_watch);
        ((TextView) findViewById(R.id.title)).setText(R.string.bluetooth_watch);
        ((RelativeLayout) findViewById(R.id.back)).setOnClickListener(new C13291());
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
