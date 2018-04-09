package com.zhuoyou.plugin.running.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.TextView;
import com.zhuoyou.plugin.running.C1680R;
import com.zhuoyou.plugin.running.base.BaseActivity;
import com.zhuoyou.plugin.running.tools.SPUtils;
import com.zhuoyou.plugin.running.tools.VCDetectThread;
import com.zhuoyou.plugin.running.view.MyAlertDialog;
import com.zhuoyou.plugin.running.view.MyAlertDialog.OnClickListener;
import com.zhuoyou.plugin.running.view.VitalCapView;

public class VCTestActivity extends BaseActivity {
    private VCDetectThread detectThread;
    private VitalCapView mCircularView;
    private TextView mDescText;
    private Handler mHandler = new C18411();
    private TextView mPlayAgagin;
    private int vital_value;

    class C18411 extends Handler {
        C18411() {
        }

        public void handleMessage(Message msg) {
            if (msg.what == 0) {
                VCTestActivity.this.vital_value = (int) ((((float) SPUtils.getVitalCapacity()) / 15.0f) * 34.0f);
                if (VCTestActivity.this.vital_value > 8000) {
                    VCTestActivity.this.vital_value = 8000;
                } else if (VCTestActivity.this.vital_value < 0) {
                    VCTestActivity.this.vital_value = 0;
                }
                SPUtils.setVitalValue(VCTestActivity.this.vital_value);
                VCTestActivity.this.mCircularView.setValue(SPUtils.getVitalValue());
            } else if (msg.what == 1) {
                if (VCTestActivity.this.vital_value < 1700) {
                    VCTestActivity.this.mDescText.setText(C1680R.string.health_center_vital_capacity_result1);
                } else if (VCTestActivity.this.vital_value < 3400) {
                    VCTestActivity.this.mDescText.setText(C1680R.string.health_center_vital_capacity_result2);
                } else if (VCTestActivity.this.vital_value < 5100) {
                    VCTestActivity.this.mDescText.setText(C1680R.string.health_center_vital_capacity_result3);
                } else {
                    VCTestActivity.this.mDescText.setText(C1680R.string.health_center_vital_capacity_result4);
                }
                VCTestActivity.this.mPlayAgagin.setEnabled(true);
            } else if (msg.what == 2) {
                VCTestActivity.this.detectThread.getNoiseLevel();
            } else if (msg.what == 3) {
                VCTestActivity.this.mCircularView.setValue(0);
                VCTestActivity.this.mDescText.setText(C1680R.string.health_center_vital_capacity_result1);
            } else if (msg.what == 4) {
                VCTestActivity.this.showFailedDialog();
            }
        }
    }

    class C18422 implements OnClickListener {
        C18422() {
        }

        public void onClick(int witch) {
            VCTestActivity.this.finish();
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(128, 128);
        setContentView((int) C1680R.layout.activity_vc_test);
        this.mCircularView = (VitalCapView) findViewById(C1680R.id.vital_capacity);
        this.mDescText = (TextView) findViewById(C1680R.id.vital_capacity_desc_text);
        this.detectThread = new VCDetectThread(this.mHandler);
        this.mPlayAgagin = (TextView) findViewById(C1680R.id.play_again);
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case C1680R.id.play_again:
                this.mCircularView.setValue(0);
                this.mDescText.setText(C1680R.string.health_center_blowing_microphone);
                this.mPlayAgagin.setText(C1680R.string.health_center_vital_capacity_tryagain);
                this.mPlayAgagin.setEnabled(false);
                this.detectThread = new VCDetectThread(this.mHandler);
                this.mHandler.sendEmptyMessageDelayed(2, 500);
                return;
            default:
                return;
        }
    }

    private void showFailedDialog() {
        MyAlertDialog dialog = new MyAlertDialog(this);
        dialog.setTitle((int) C1680R.string.string_reminder);
        dialog.setMessage((int) C1680R.string.health_center_vital_open_mic_fialed);
        dialog.setLeftButton((int) C1680R.string.string_ok, new C18422());
        dialog.show();
    }

    public void onBackPressed() {
        super.onBackPressed();
        this.detectThread.stopThread();
    }

    protected void onPause() {
        super.onPause();
        this.detectThread.stopThread();
    }

    protected void onStop() {
        super.onStop();
        this.detectThread.stopThread();
    }
}
