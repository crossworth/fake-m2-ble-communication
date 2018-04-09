package com.zhuoyou.plugin.resideMenu;

import android.app.Activity;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask.Status;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.fithealth.running.R;
import com.zhuoyou.plugin.cloud.NetUtils;
import com.zhuoyou.plugin.running.Tools;
import com.zhuoyou.plugin.selfupdate.Constants;
import com.zhuoyou.plugin.selfupdate.MyHandler;
import com.zhuoyou.plugin.selfupdate.RequestAsyncTask;
import com.zhuoyou.plugin.selfupdate.SelfUpdateMain;

public class HelpActivity extends Activity implements OnClickListener {
    private final int APPLICATION_INTRODUING = 1;
    private BluetoothDevice currentDevice;
    private String deviceVersionStr;
    private String hardwareVersionStr;
    private View hardwareView1;
    private View hardwareView2;
    private Context mContext;
    private RelativeLayout mHardwareVersionRL;
    private TextView mHardwareVersionTV;
    private RelativeLayout mSoftwareVersionRL;
    private TextView mSoftwareaVersionTV;
    private TextView mVersion;
    private RequestAsyncTask tast;
    private RelativeLayout tvApplicationIntroducing;
    private RelativeLayout tvApplicationUpdate;
    private RelativeLayout tvFrequentlyQuestions;
    private RelativeLayout tvSupportType;
    private String version;

    class C13421 implements OnClickListener {
        C13421() {
        }

        public void onClick(View v) {
            HelpActivity.this.finish();
        }
    }

    class C13432 extends Handler {
        C13432() {
        }

        public void handleMessage(Message msg) {
            HelpActivity.this.tvApplicationUpdate.setEnabled(true);
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.help);
        this.mContext = this;
        this.version = Tools.setAppVersionInfo(this);
        this.hardwareVersionStr = Tools.getHardwareVersion();
        this.deviceVersionStr = Tools.getDeviceVersion();
        initView();
        if (this.deviceVersionStr == null || this.deviceVersionStr.equals("")) {
            this.mHardwareVersionRL.setVisibility(8);
            this.hardwareView1.setVisibility(8);
            this.hardwareView2.setVisibility(8);
        }
    }

    private void initView() {
        ((TextView) findViewById(R.id.title)).setText(R.string.help);
        ((RelativeLayout) findViewById(R.id.back)).setOnClickListener(new C13421());
        this.tvSupportType = (RelativeLayout) findViewById(R.id.tv_support_type);
        this.tvFrequentlyQuestions = (RelativeLayout) findViewById(R.id.tv_frequently_questions);
        this.tvApplicationUpdate = (RelativeLayout) findViewById(R.id.tv_application_update);
        this.tvApplicationIntroducing = (RelativeLayout) findViewById(R.id.tv_application_introducing);
        this.mHardwareVersionRL = (RelativeLayout) findViewById(R.id.hardware_version_rl);
        this.mSoftwareVersionRL = (RelativeLayout) findViewById(R.id.software_version_rl);
        this.mHardwareVersionTV = (TextView) findViewById(R.id.hardware_version);
        this.mSoftwareaVersionTV = (TextView) findViewById(R.id.software_version);
        this.mVersion = (TextView) findViewById(R.id.tv_version);
        this.hardwareView1 = findViewById(R.id.hardware_version_view1);
        this.hardwareView2 = findViewById(R.id.hardware_version_view2);
        this.tvSupportType.setOnClickListener(this);
        this.tvFrequentlyQuestions.setOnClickListener(this);
        this.tvApplicationUpdate.setOnClickListener(this);
        this.tvApplicationIntroducing.setOnClickListener(this);
        if (!this.version.equals("")) {
            this.mSoftwareaVersionTV.setText(this.version);
        }
        if (!this.hardwareVersionStr.equals("")) {
            this.mHardwareVersionRL.setVisibility(0);
            this.hardwareView1.setVisibility(0);
            this.hardwareView2.setVisibility(0);
            this.mHardwareVersionTV.setText(this.hardwareVersionStr);
        } else if (!this.deviceVersionStr.equals("")) {
            this.mHardwareVersionRL.setVisibility(0);
            this.hardwareView1.setVisibility(0);
            this.hardwareView2.setVisibility(0);
            this.mHardwareVersionTV.setText(this.deviceVersionStr);
        }
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_support_type:
                startActivity(new Intent(this, SupportTypeActivity.class));
                return;
            case R.id.tv_frequently_questions:
                startActivity(new Intent(this, FrequentlyQuestionsActivity.class));
                return;
            case R.id.tv_application_introducing:
                startActivityForResult(new Intent(this, ApplicationIntroduingActivity.class), 1);
                return;
            case R.id.tv_application_update:
                if (NetUtils.getAPNType(this.mContext) == -1) {
                    Toast.makeText(this.mContext, R.string.check_network, 0).show();
                    return;
                } else if (!SelfUpdateMain.isDownloading) {
                    Toast.makeText(this.mContext, getResources().getString(R.string.isgoing_check_update), 0).show();
                    this.tvApplicationUpdate.setEnabled(false);
                    new C13432().sendEmptyMessageDelayed(1, 600);
                    MyHandler h = new MyHandler(this, true);
                    if (this.tast == null || this.tast.getStatus() == Status.FINISHED) {
                        this.tast = new RequestAsyncTask(this, h, 1001, Constants.APPID, Constants.CHNID);
                        this.tast.startRun();
                        return;
                    }
                    return;
                } else {
                    return;
                }
            default:
                return;
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case 1:
                if (resultCode == -1) {
                    finish();
                    return;
                }
                return;
            default:
                return;
        }
    }
}
