package com.zhuoyou.plugin.resideMenu;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.fithealth.running.R;

public class SupportTypeActivity extends Activity {

    class C13451 implements OnClickListener {
        C13451() {
        }

        public void onClick(View v) {
            SupportTypeActivity.this.finish();
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.support_type);
        ((TextView) findViewById(R.id.title)).setText(R.string.supported_models);
        ((RelativeLayout) findViewById(R.id.back)).setOnClickListener(new C13451());
    }
}
