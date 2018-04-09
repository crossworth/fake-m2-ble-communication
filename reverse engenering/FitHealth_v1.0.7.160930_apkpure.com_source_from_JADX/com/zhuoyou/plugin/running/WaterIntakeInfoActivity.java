package com.zhuoyou.plugin.running;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.fithealth.running.R;

public class WaterIntakeInfoActivity extends Activity {
    private RelativeLayout im_back;
    private TextView mTitle;

    class C14231 implements OnClickListener {
        C14231() {
        }

        public void onClick(View v) {
            WaterIntakeInfoActivity.this.finish();
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.water_intake_info_show);
        this.mTitle = (TextView) findViewById(R.id.title);
        this.mTitle.setText(R.string.drink_plan);
        this.im_back = (RelativeLayout) findViewById(R.id.back);
        this.im_back.setOnClickListener(new C14231());
    }
}
