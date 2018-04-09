package com.zhuoyou.plugin.firmware;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.NotificationManager;
import android.bluetooth.BluetoothAdapter;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.drawable.AnimationDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.fithealth.running.R;
import com.zhuoyou.plugin.ble.BleManagerService;
import com.zhuoyou.plugin.ble.BluetoothLeService;
import com.zhuoyou.plugin.ble.DfuService;
import com.zhuoyou.plugin.bluetooth.data.MessageObj;
import com.zhuoyou.plugin.bluetooth.data.Util;
import com.zhuoyou.plugin.custom.CustomAlertDialog.Builder;
import com.zhuoyou.plugin.running.Tools;
import com.zhuoyou.plugin.view.HoloCircularProgressBar;
import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import no.nordicsemi.android.dfu.DfuBaseService;
import org.apache.http.cookie.ClientCookie;

public class FwUpdateActivity extends Activity implements OnClickListener {
    public static final String ACTION_DOWNLOADED = "com.zhuoyou.running.action.downloaded";
    public static final String ACTION_DOWNLOADING = "com.zhuoyou.running.action.downloading";
    public static final String ACTION_DOWNLOAD_FAILED = "com.zhuoyou.running.action.download.failed";
    public static final String ACTION_DOWNLOAD_PAUSED = "com.zhuoyou.running.action.download.paused";
    private AnimationDrawable animationDrawable = null;
    private String device_name = "";
    private String fileDatPath;
    private String filePath;
    private boolean isBleDevice;
    private boolean isUpdateReady = false;
    private IntentFilter mBlueFilter;
    private final BroadcastReceiver mBlueToothRecivier = new C12447();
    private LinearLayout mButton_layout;
    private final BroadcastReceiver mDfuUpdateReceiver = new C12498();
    private Button mExit;
    private Handler mHandler;
    private ImageView mImageView1;
    private ImageView mImageView2;
    private ImageView mImageView3;
    private TextView mIntroduction;
    private FrameLayout mLayout;
    private Button mNext;
    private Button mPre;
    private HoloCircularProgressBar mProgressBar;
    private LinearLayout mStep2_layout;
    private LinearLayout mStep3_layout;
    private LinearLayout mStep4_firmLayot;
    private TextView mTVrate;
    private TextView mTVstate;
    private LinearLayout mText_layout;
    private TextView mToast;
    private final BroadcastReceiver mUpdateUIRecivier = new C12436();
    private IntentFilter mViweIntent;
    private SharedPreferences prefs;
    private int rate = 0;
    private int step = 1;
    private final String tag = "FwUpdateActivity";
    private int testNum = 0;

    class C12381 extends Handler {
        C12381() {
        }

        public void handleMessage(Message msg) {
            if (msg.what == 200) {
                FwUpdateActivity.this.startDFU();
            }
        }
    }

    class C12392 implements Runnable {
        C12392() {
        }

        public void run() {
            FwUpdateActivity.this.step = 3;
            FwUpdateActivity.this.mExit.setVisibility(8);
            FwUpdateActivity.this.mStep2_layout.setVisibility(8);
            FwUpdateActivity.this.mStep3_layout.setVisibility(0);
            FwUpdateActivity.this.mPre.setVisibility(0);
            FwUpdateActivity.this.mNext.setText(R.string.it_is_ok);
            if (FwUpdateActivity.this.device_name.startsWith("EAMEY_P3") || FwUpdateActivity.this.device_name.startsWith("EAMEY_P2") || FwUpdateActivity.this.device_name.startsWith("Primo_3") || FwUpdateActivity.this.device_name.startsWith("ABT-100")) {
                FwUpdateActivity.this.mToast.setVisibility(0);
            }
        }
    }

    class C12403 implements DialogInterface.OnClickListener {
        C12403() {
        }

        public void onClick(DialogInterface dialog, int which) {
            dialog.dismiss();
            Tools.setFirmwear(false);
            FwUpdateActivity.this.finish();
        }
    }

    class C12414 implements DialogInterface.OnClickListener {
        C12414() {
        }

        public void onClick(DialogInterface dialog, int which) {
            dialog.dismiss();
        }
    }

    class C12425 implements Runnable {
        C12425() {
        }

        public void run() {
            FwUpdateActivity.this.mButton_layout.setVisibility(8);
            FwUpdateActivity.this.findViewById(R.id.toast_firmware).setVisibility(0);
        }
    }

    class C12436 extends BroadcastReceiver {
        C12436() {
        }

        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            Log.i("FwUpdateActivity", action);
            if (action.equals(FwUpdateActivity.ACTION_DOWNLOADING)) {
                if (!FwUpdateActivity.this.animationDrawable.isRunning()) {
                    FwUpdateActivity.this.animationDrawable.start();
                }
                FwUpdateActivity.this.mImageView2.setVisibility(0);
                FwUpdateActivity.this.mImageView3.setVisibility(8);
                FwUpdateActivity.this.mTVstate.setText(R.string.firmware_downloading);
                int temp = intent.getIntExtra("rate", 0);
                if (temp > FwUpdateActivity.this.rate) {
                    FwUpdateActivity.this.rate = temp;
                    float frate = ((float) FwUpdateActivity.this.rate) / 100.0f;
                    FwUpdateActivity.this.mTVrate.setText(FwUpdateActivity.this.rate + "%");
                    FwUpdateActivity.this.mProgressBar.setProgress(frate);
                }
            } else if (action.equals(FwUpdateActivity.ACTION_DOWNLOADED)) {
                if (FwUpdateActivity.this.step != 1) {
                    return;
                }
                if (FwUpdateActivity.this.isBleDevice) {
                    FwUpdateActivity.this.step = 4;
                    FwUpdateActivity.this.mIntroduction.setVisibility(8);
                    FwUpdateActivity.this.mLayout.setVisibility(8);
                    FwUpdateActivity.this.mText_layout.setVisibility(8);
                    FwUpdateActivity.this.mStep4_firmLayot.setVisibility(0);
                    FwUpdateActivity.this.mButton_layout.setVisibility(0);
                    FwUpdateActivity.this.mNext.setText(R.string.ok);
                    return;
                }
                FwUpdateActivity.this.step = 2;
                FwUpdateActivity.this.mIntroduction.setVisibility(8);
                FwUpdateActivity.this.mLayout.setVisibility(8);
                FwUpdateActivity.this.mText_layout.setVisibility(8);
                FwUpdateActivity.this.mStep2_layout.setVisibility(0);
                FwUpdateActivity.this.mButton_layout.setVisibility(0);
            } else if (action.equals(FwUpdateActivity.ACTION_DOWNLOAD_PAUSED)) {
                if (FwUpdateActivity.this.animationDrawable.isRunning()) {
                    FwUpdateActivity.this.animationDrawable.stop();
                }
                FwUpdateActivity.this.mImageView2.setVisibility(8);
                FwUpdateActivity.this.mImageView3.setVisibility(0);
                FwUpdateActivity.this.mImageView3.setImageResource(R.drawable.update_again);
                FwUpdateActivity.this.mTVstate.setText(R.string.firmwear_net_error);
                FwUpdateActivity.this.mTVrate.setText(null);
            }
        }
    }

    class C12447 extends BroadcastReceiver {
        C12447() {
        }

        public void onReceive(Context context, Intent intent) {
            if (BluetoothLeService.ACTION_GATT_DISCONNECTED.equals(intent.getAction())) {
                Log.i("FwUpdateActivity", "Firmware ACTION_GATT_DISCONNECTED");
                if (FwUpdateActivity.this.isUpdateReady) {
                    FwUpdateActivity.this.mHandler.sendEmptyMessageDelayed(200, 100);
                }
            }
        }
    }

    class C12498 extends BroadcastReceiver {

        class C12451 implements Runnable {
            C12451() {
            }

            public void run() {
                Log.i("chenxin", "ble enadble");
                BluetoothAdapter.getDefaultAdapter().enable();
            }
        }

        class C12462 implements Runnable {
            C12462() {
            }

            public void run() {
                BluetoothAdapter adapter = BluetoothAdapter.getDefaultAdapter();
                if (adapter.isEnabled()) {
                    Log.i("chenxin", "ble startDiscovery");
                    adapter.startDiscovery();
                }
            }
        }

        class C12473 implements Runnable {
            C12473() {
            }

            public void run() {
                BluetoothAdapter adapter = BluetoothAdapter.getDefaultAdapter();
                Log.i("chenxin", "ble cancelDiscovery");
                adapter.cancelDiscovery();
            }
        }

        C12498() {
        }

        public void onReceive(Context context, final Intent intent) {
            String action = intent.getAction();
            Log.i("FwUpdateActivity", "action:" + action);
            if (DfuBaseService.BROADCAST_PROGRESS.equals(action)) {
                if (intent.getIntExtra(DfuBaseService.EXTRA_DATA, 0) == -6) {
                    Toast.makeText(FwUpdateActivity.this.getApplicationContext(), R.string.firmwear_update_finish, 1).show();
                    Tools.setFirmwear(false);
                    FwUpdateActivity.this.finish();
                }
            } else if (!DfuBaseService.BROADCAST_ERROR.equals(action)) {
            } else {
                if (FwUpdateActivity.this.testNum < 3) {
                    FwUpdateActivity.this.stopService(new Intent(FwUpdateActivity.this, DfuService.class));
                    FwUpdateActivity.this.mHandler.sendEmptyMessageDelayed(200, 5000);
                } else if (FwUpdateActivity.this.testNum == 3) {
                    BluetoothAdapter.getDefaultAdapter().disable();
                    Log.i("chenxin", "testNum == 3");
                    FwUpdateActivity.this.mHandler.postDelayed(new C12451(), 2000);
                    FwUpdateActivity.this.mHandler.postDelayed(new C12462(), 5000);
                    FwUpdateActivity.this.mHandler.postDelayed(new C12473(), 13000);
                    FwUpdateActivity.this.stopService(new Intent(FwUpdateActivity.this, DfuService.class));
                    FwUpdateActivity.this.mHandler.sendEmptyMessageDelayed(200, 15000);
                } else {
                    new Handler().postDelayed(new Runnable() {
                        public void run() {
                            ((NotificationManager) FwUpdateActivity.this.getSystemService(MessageObj.CATEGORY_NOTI)).cancel(DfuBaseService.NOTIFICATION_ID);
                            Toast.makeText(FwUpdateActivity.this.getApplicationContext(), "固件更新失败", 1).show();
                            Log.i("chenxin", "error code:" + intent.getIntExtra(DfuBaseService.EXTRA_DATA, -999));
                            FwUpdateActivity.this.finish();
                        }
                    }, 200);
                }
            }
        }
    }

    @SuppressLint({"HandlerLeak"})
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_firmware_upgrade);
        this.device_name = getIntent().getStringExtra("device_name");
        this.isBleDevice = getIntent().getBooleanExtra("isBleDevice", false);
        this.mHandler = new C12381();
        this.prefs = PreferenceManager.getDefaultSharedPreferences(this);
        this.mExit = (Button) findViewById(R.id.exit);
        this.mExit.setOnClickListener(this);
        this.mIntroduction = (TextView) findViewById(R.id.introduction);
        this.mLayout = (FrameLayout) findViewById(R.id.layout);
        this.mProgressBar = (HoloCircularProgressBar) findViewById(R.id.holoCircularProgressBar);
        this.mImageView1 = (ImageView) findViewById(R.id.imageview1);
        this.mImageView1.setEnabled(false);
        this.mImageView2 = (ImageView) findViewById(R.id.imageview2);
        this.animationDrawable = (AnimationDrawable) this.mImageView2.getBackground();
        if (!this.animationDrawable.isRunning()) {
            this.animationDrawable.start();
        }
        this.mImageView3 = (ImageView) findViewById(R.id.imageview3);
        this.mTVstate = (TextView) findViewById(R.id.fw_update_state);
        this.mTVrate = (TextView) findViewById(R.id.fw_update_rate);
        this.mToast = (TextView) findViewById(R.id.toast);
        this.mText_layout = (LinearLayout) findViewById(R.id.text_layout);
        this.mStep2_layout = (LinearLayout) findViewById(R.id.step2_layout);
        this.mStep3_layout = (LinearLayout) findViewById(R.id.step3_layout);
        this.mStep4_firmLayot = (LinearLayout) findViewById(R.id.step4_firmware_layout);
        this.mButton_layout = (LinearLayout) findViewById(R.id.button_layout);
        this.mNext = (Button) findViewById(R.id.next);
        this.mNext.setOnClickListener(this);
        this.mPre = (Button) findViewById(R.id.pre);
        this.mPre.setOnClickListener(this);
        this.mViweIntent = new IntentFilter();
        this.mViweIntent.addAction(ACTION_DOWNLOADING);
        this.mViweIntent.addAction(ACTION_DOWNLOADED);
        this.mViweIntent.addAction(ACTION_DOWNLOAD_PAUSED);
        this.mViweIntent.addAction(ACTION_DOWNLOAD_FAILED);
        registerReceiver(this.mUpdateUIRecivier, this.mViweIntent);
        this.mBlueFilter = new IntentFilter();
        this.mBlueFilter.addAction(BluetoothLeService.ACTION_GATT_CONNECTED);
        this.mBlueFilter.addAction(BluetoothLeService.ACTION_GATT_DISCONNECTED);
        this.mBlueFilter.addAction(DfuBaseService.BROADCAST_PROGRESS);
        registerReceiver(this.mBlueToothRecivier, this.mBlueFilter);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(DfuBaseService.BROADCAST_PROGRESS);
        intentFilter.addAction(DfuBaseService.BROADCAST_ERROR);
        LocalBroadcastManager.getInstance(this).registerReceiver(this.mDfuUpdateReceiver, intentFilter);
    }

    public void onDestroy() {
        super.onDestroy();
        unregisterReceiver(this.mUpdateUIRecivier);
        unregisterReceiver(this.mBlueToothRecivier);
        LocalBroadcastManager.getInstance(this).unregisterReceiver(this.mDfuUpdateReceiver);
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.exit:
                if (this.step == 1) {
                    sendBroadcast(new Intent("com.zhuoyou.running.cancel.firmwear"));
                    finish();
                    return;
                } else if (this.step == 2) {
                    finish();
                    return;
                } else {
                    return;
                }
            case R.id.next:
                if (this.step == 2) {
                    this.filePath = this.prefs.getString(ClientCookie.PATH_ATTR, "");
                    this.fileDatPath = this.prefs.getString("path_dat", "");
                    Log.i("caixinxin", "filePath = " + this.filePath);
                    Log.i("caixinxin", "fileDatPath = " + this.fileDatPath);
                    sendFileByBluetooth(this.filePath);
                    this.mHandler.postDelayed(new C12392(), 500);
                    return;
                } else if (this.step == 3) {
                    Builder builder = new Builder(this);
                    builder.setTitle((int) R.string.alert_title);
                    builder.setMessage((int) R.string.firmwear_des_4);
                    builder.setPositiveButton((int) R.string.ok, new C12403());
                    builder.setNegativeButton((int) R.string.cancle, new C12414());
                    builder.setCancelable(Boolean.valueOf(false));
                    builder.create().show();
                    return;
                } else if (this.step == 4) {
                    this.filePath = this.prefs.getString(ClientCookie.PATH_ATTR, "");
                    this.fileDatPath = this.prefs.getString("path_dat", "");
                    this.isUpdateReady = true;
                    sendBroadcast(new Intent(BleManagerService.ACTION_READY_FIRMWARE_UPDATE));
                    this.mHandler.postDelayed(new C12425(), 500);
                    return;
                } else {
                    return;
                }
            case R.id.pre:
                if (this.step == 3) {
                    this.step = 2;
                    this.mExit.setVisibility(0);
                    this.mStep2_layout.setVisibility(0);
                    this.mStep3_layout.setVisibility(8);
                    this.mPre.setVisibility(8);
                    this.mNext.setText(R.string.next);
                    if (this.device_name.startsWith("EAMEY_P3") || this.device_name.startsWith("EAMEY_P2") || this.device_name.startsWith("Primo_3") || this.device_name.startsWith("ABT-100")) {
                        this.mToast.setVisibility(8);
                        return;
                    }
                    return;
                }
                return;
            default:
                return;
        }
    }

    private void sendFileByBluetooth(String path) {
        if (path != null) {
            if (!path.equals("") && BluetoothAdapter.getDefaultAdapter() != null) {
                PackageManager localPackageManager = getPackageManager();
                Intent localIntent = null;
                HashMap<String, ActivityInfo> localHashMap = null;
                try {
                    Intent localIntent2 = new Intent();
                    try {
                        localIntent2.setAction("android.intent.action.SEND");
                        localIntent2.putExtra("android.intent.extra.STREAM", Uri.fromFile(new File(path)));
                        localIntent2.setType("*/*");
                        List<ResolveInfo> localList = localPackageManager.queryIntentActivities(localIntent2, 0);
                        HashMap<String, ActivityInfo> localHashMap2 = new HashMap();
                        try {
                            for (ResolveInfo resolveInfo : localList) {
                                ActivityInfo localActivityInfo2 = resolveInfo.activityInfo;
                                String str = localActivityInfo2.applicationInfo.processName;
                                if (str.contains("bluetooth")) {
                                    localHashMap2.put(str, localActivityInfo2);
                                }
                            }
                            localHashMap = localHashMap2;
                            localIntent = localIntent2;
                        } catch (Exception e) {
                            localHashMap = localHashMap2;
                            localIntent = localIntent2;
                        }
                    } catch (Exception e2) {
                        localIntent = localIntent2;
                    }
                } catch (Exception e3) {
                }
                ActivityInfo activityInfo = (ActivityInfo) localHashMap.get("com.android.bluetooth");
                if (activityInfo == null) {
                    activityInfo = (ActivityInfo) localHashMap.get("com.mediatek.bluetooth");
                }
                if (activityInfo == null) {
                    Iterator<ActivityInfo> localIterator2 = localHashMap.values().iterator();
                    if (localIterator2.hasNext()) {
                        activityInfo = (ActivityInfo) localIterator2.next();
                    }
                }
                if (activityInfo != null) {
                    localIntent.setComponent(new ComponentName(activityInfo.packageName, activityInfo.name));
                    startActivity(localIntent);
                }
            }
        }
    }

    private void startDFU() {
        Log.i("FwUpdateActivity", "startDFU");
        this.testNum++;
        String address = Util.getLatestConnectDeviceAddress(this);
        this.filePath = this.prefs.getString(ClientCookie.PATH_ATTR, "");
        this.fileDatPath = this.prefs.getString("path_dat", "");
        File file = new File(this.filePath);
        Log.i("FwUpdateActivity", "filePath" + this.filePath + ",+fileDatPath==" + this.fileDatPath);
        Log.i("FwUpdateActivity", "file.exists()==" + file.exists());
        if (file.exists()) {
            Log.i("FwUpdateActivity", "start Update:" + address);
            Intent service = new Intent(this, DfuService.class);
            service.putExtra(DfuBaseService.EXTRA_DEVICE_ADDRESS, address);
            service.putExtra(DfuBaseService.EXTRA_FILE_MIME_TYPE, "application/octet-stream");
            service.putExtra(DfuBaseService.EXTRA_FILE_TYPE, 4);
            service.putExtra(DfuBaseService.EXTRA_FILE_PATH, this.filePath);
            if (!TextUtils.isEmpty(this.fileDatPath)) {
                service.putExtra(DfuBaseService.EXTRA_INIT_FILE_PATH, this.fileDatPath);
            }
            startService(service);
        }
    }

    public void onBackPressed() {
    }
}
