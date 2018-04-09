package com.zhuoyou.plugin.gps;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.os.Build;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.fithealth.running.R;
import java.util.List;

public class ResultGpsActivity extends Activity {
    TextView andrText;
    TextView errorText;
    private String from;
    private boolean isHaveGps;
    Button mButton;
    ImageView mImage;
    TextView mTextView;
    TextView modelText;
    private boolean step_sensor;

    class C12791 implements OnClickListener {
        C12791() {
        }

        public void onClick(View v) {
            if (ResultGpsActivity.this.from.equals("Main")) {
                ResultGpsActivity.this.startActivity(new Intent(ResultGpsActivity.this, GaoDeMapActivity.class));
                ResultGpsActivity.this.finish();
                return;
            }
            ResultGpsActivity.this.finish();
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gps_result);
        Intent mItent = getIntent();
        this.step_sensor = mItent.getBooleanExtra("sensor_isuse", false);
        this.from = mItent.getStringExtra("from");
        this.isHaveGps = hasGPSDevice(this);
        this.mButton = (Button) findViewById(R.id.use_begin);
        this.mTextView = (TextView) findViewById(R.id.result_test);
        this.modelText = (TextView) findViewById(R.id.mob_model);
        this.andrText = (TextView) findViewById(R.id.andr_version);
        this.errorText = (TextView) findViewById(R.id.error_reason);
        this.mImage = (ImageView) findViewById(R.id.gps_result);
        this.modelText.setText(getResources().getString(R.string.mobe_model) + Build.MODEL);
        this.andrText.setText(getResources().getString(R.string.android_version) + VERSION.RELEASE);
        if (this.step_sensor && this.isHaveGps) {
            Log.i("zhaojunhui", "1 step_sensor=" + this.step_sensor + ";isHaveGps=" + this.isHaveGps);
            this.mTextView.setText(getResources().getString(R.string.result_ok));
            this.mImage.setImageDrawable(getResources().getDrawable(R.drawable.gps_support));
            this.errorText.setVisibility(8);
        } else if (!this.step_sensor && this.isHaveGps) {
            Log.i("zhaojunhui", "2 step_sensor=" + this.step_sensor + ";isHaveGps=" + this.isHaveGps);
            this.mTextView.setText(getResources().getString(R.string.result_some));
            this.mImage.setImageDrawable(getResources().getDrawable(R.drawable.gps_no_support));
            this.errorText.setVisibility(0);
            this.errorText.setText(getResources().getString(R.string.error_steps));
        } else if (!this.isHaveGps && this.step_sensor) {
            Log.i("zhaojunhui", "3 step_sensor=" + this.step_sensor + ";isHaveGps=" + this.isHaveGps);
            this.mTextView.setText(getResources().getString(R.string.result_some));
            this.mImage.setImageDrawable(getResources().getDrawable(R.drawable.gps_no_support));
            this.errorText.setVisibility(0);
            this.errorText.setText(getResources().getString(R.string.error_gps));
        } else if (!(this.isHaveGps || this.step_sensor)) {
            Log.i("zhaojunhui", "4 step_sensor=" + this.step_sensor + ";isHaveGps=" + this.isHaveGps);
            this.mTextView.setText(getResources().getString(R.string.result_none));
            this.mImage.setImageDrawable(getResources().getDrawable(R.drawable.gps_no_support));
            this.errorText.setVisibility(0);
            this.errorText.setText(getResources().getString(R.string.error_all));
        }
        this.mButton.setOnClickListener(new C12791());
    }

    public boolean hasGPSDevice(Context context) {
        LocationManager mgr = (LocationManager) context.getSystemService("location");
        if (mgr == null) {
            return false;
        }
        List<String> providers = mgr.getAllProviders();
        if (providers != null) {
            return providers.contains("gps");
        }
        return false;
    }

    protected void onResume() {
        super.onResume();
    }

    protected void onDestroy() {
        super.onDestroy();
    }
}
