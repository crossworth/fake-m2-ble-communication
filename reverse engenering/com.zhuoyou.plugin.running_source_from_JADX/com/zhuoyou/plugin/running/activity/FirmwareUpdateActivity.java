package com.zhuoyou.plugin.running.activity;

import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import com.droi.btlib.service.BtManagerService;
import com.droi.btlib.service.BtManagerService.updateBleCallback;
import com.droi.sdk.selfupdate.DroiInappDownloadListener;
import com.droi.sdk.selfupdate.DroiInappUpdateListener;
import com.droi.sdk.selfupdate.DroiInappUpdateResponse;
import com.droi.sdk.selfupdate.DroiUpdate;
import com.zhuoyou.plugin.running.C1680R;
import com.zhuoyou.plugin.running.base.BaseActivity;
import com.zhuoyou.plugin.running.tools.Tools;
import java.io.File;

public class FirmwareUpdateActivity extends BaseActivity {
    private static final int BIN_WITH_DAT = 2;
    public static final String EXTRA_KEY_DEVICE_NAME = "update_device_name";
    public static final String EXTRA_KEY_RESPONSE = "update_response";
    public static final String EXTRA_KEY_UPDATE_TYLE = "update_update_type";
    public static final String EXTRA_KEY_VERSION = "update_version";
    private static final int MSG_BLE_UPDATE_FAIL = 4101;
    private static final int MSG_BLE_UPDATE_SUCCESS = 4100;
    private static final int MSG_DOWNLOAD_FAIL = 4099;
    private static final int MSG_DOWNLOAD_PROGRESS = 4098;
    private static final int MSG_DOWNLOAD_SUCCESS_BLE = 4097;
    private static final int MSG_DOWNLOAD_SUCCESS_CLASSIC = 4102;
    private static final int ONLY_BIN_BLE = 1;
    private static final int ONLY_BIN_CLASSIC = 0;
    private static final int STEP_BLE_DOWNLOAD_SUCCESS = 8193;
    private static final int STEP_CLASSIC_DOWNLOAD_SUCCESS = 8194;
    private static final int STEP_CLASSIC_SEND_END = 8195;
    private static final int STEP_NO = 8192;
    private static final String TAG = "FirmwareUpdateActivity";
    private String binFilePath;
    private ImageView btClose;
    private TextView btSend;
    private String datFilePath;
    private String deviceName;
    private Handler handler = new C17221();
    private ImageView imgBack;
    private DroiInappUpdateResponse response;
    private int step = 8192;
    private TextView tvHint;
    private TextView tvProgress;
    private TextView tvStatus;
    private int updateType;
    private int version;

    class C17221 extends Handler {
        C17221() {
        }

        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 4097:
                    FirmwareUpdateActivity.this.tvStatus.setText(C1680R.string.firmware_download_success);
                    FirmwareUpdateActivity.this.tvProgress.setText("100%");
                    FirmwareUpdateActivity.this.step = 8193;
                    FirmwareUpdateActivity.this.btSend.setText(C1680R.string.firmware_ble_send);
                    FirmwareUpdateActivity.this.btSend.setVisibility(0);
                    return;
                case 4098:
                    FirmwareUpdateActivity.this.tvProgress.setText(msg.arg1 + "%");
                    return;
                case 4100:
                    FirmwareUpdateActivity.this.finish();
                    Tools.makeToast((int) C1680R.string.firmwear_update_finish);
                    return;
                case 4101:
                    FirmwareUpdateActivity.this.finish();
                    Tools.makeToast((int) C1680R.string.firmwear_update_fail);
                    return;
                case 4102:
                    FirmwareUpdateActivity.this.tvStatus.setText(C1680R.string.firmware_download_success);
                    FirmwareUpdateActivity.this.tvHint.setText(C1680R.string.firmwear_des_1);
                    FirmwareUpdateActivity.this.tvProgress.setText("100%");
                    FirmwareUpdateActivity.this.step = 8194;
                    FirmwareUpdateActivity.this.btSend.setText(C1680R.string.firmware_classic_send);
                    FirmwareUpdateActivity.this.btSend.setVisibility(0);
                    return;
                default:
                    return;
            }
        }
    }

    class C17263 implements DroiInappDownloadListener {
        C17263() {
        }

        public void onStart(long l) {
        }

        public void onProgress(float v) {
            Log.i(FirmwareUpdateActivity.TAG, "classic onProgress:" + v);
            Message msg = new Message();
            msg.what = 4098;
            msg.arg1 = (int) (100.0f * v);
            FirmwareUpdateActivity.this.handler.sendMessage(msg);
        }

        public void onFinished(String s) {
            Log.i(FirmwareUpdateActivity.TAG, "下载成功");
            FirmwareUpdateActivity.this.handler.sendEmptyMessage(4102);
        }

        public void onFailed(int i) {
            Log.i(FirmwareUpdateActivity.TAG, "classic bin 下载失败:" + i);
            FirmwareUpdateActivity.this.handler.sendEmptyMessage(4099);
        }
    }

    class C17274 implements updateBleCallback {
        C17274() {
        }

        public void success() {
            Log.i(FirmwareUpdateActivity.TAG, "固件升级成功");
            FirmwareUpdateActivity.this.handler.sendEmptyMessage(4100);
        }

        public void progress(int progress) {
            Log.i(FirmwareUpdateActivity.TAG, "固件升级中:" + progress);
            Message msg = new Message();
            msg.what = 4098;
            msg.arg1 = progress;
            FirmwareUpdateActivity.this.handler.sendMessage(msg);
        }

        public void fail(int state) {
            Log.i(FirmwareUpdateActivity.TAG, "固件升级失败:" + state);
            FirmwareUpdateActivity.this.handler.sendEmptyMessage(4101);
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) C1680R.layout.activity_firmware_update);
        this.response = (DroiInappUpdateResponse) getIntent().getSerializableExtra(EXTRA_KEY_RESPONSE);
        this.deviceName = getIntent().getStringExtra(EXTRA_KEY_DEVICE_NAME);
        this.version = getIntent().getIntExtra(EXTRA_KEY_VERSION, -1);
        this.updateType = getIntent().getIntExtra(EXTRA_KEY_UPDATE_TYLE, -1);
        initView();
        initData();
    }

    private void initView() {
        this.imgBack = (ImageView) findViewById(C1680R.id.img_back);
        this.tvHint = (TextView) findViewById(C1680R.id.tv_hint);
        this.tvProgress = (TextView) findViewById(C1680R.id.tv_progress);
        this.tvStatus = (TextView) findViewById(C1680R.id.tv_status);
        this.btSend = (TextView) findViewById(C1680R.id.bt_send);
        this.btClose = (ImageView) findViewById(C1680R.id.btn_close);
        this.imgBack.setAnimation(AnimationUtils.loadAnimation(this, C1680R.anim.firware_update_back));
    }

    private void initData() {
        this.binFilePath = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + this.deviceName + this.version + ".bin";
        this.datFilePath = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + this.deviceName + this.version + ".dat";
        File checkBin = new File(this.binFilePath);
        File checkDat = new File(this.datFilePath);
        Log.i(TAG, "verType:0 binFilePath:" + this.binFilePath + "\ndatFilePath:" + this.datFilePath + "\n" + checkBin.exists() + " " + (checkBin.length() + "").equals(this.response.getFileSize()));
        switch (this.updateType) {
            case 0:
                if (checkBin.exists() && (checkBin.length() + "").equals(this.response.getFileSize())) {
                    this.handler.sendEmptyMessage(4102);
                    return;
                }
                if (checkBin.exists()) {
                    checkBin.delete();
                }
                downLoadClassicBin(this.binFilePath);
                return;
            case 2:
                if (checkBin.exists() && (checkBin.length() + "").equals(this.response.getFileSize()) && checkDat.exists()) {
                    Log.i(TAG, "already have files");
                    this.handler.sendEmptyMessage(4097);
                    return;
                }
                if (checkBin.exists()) {
                    checkBin.delete();
                }
                if (checkDat.exists()) {
                    checkDat.delete();
                }
                downloadBinAndDat(this.binFilePath, this.datFilePath);
                return;
            default:
                return;
        }
    }

    private void downloadBinAndDat(final String binPath, final String datPath) {
        Log.i(TAG, "downloadBinAndDat:" + binPath + " " + datPath);
        DroiUpdate.inappUpdate(this, this.deviceName + ".dat", this.version, new DroiInappUpdateListener() {

            class C17241 implements DroiInappDownloadListener {

                class C17231 implements DroiInappDownloadListener {
                    C17231() {
                    }

                    public void onStart(long startPos) {
                    }

                    public void onProgress(float progress) {
                        Log.i(FirmwareUpdateActivity.TAG, "bin onProgress:" + ((int) (((progress * 100.0f) / 2.0f) + 50.0f)));
                        Message msg = new Message();
                        msg.what = 4098;
                        msg.arg1 = (int) (((progress * 100.0f) / 2.0f) + 50.0f);
                        FirmwareUpdateActivity.this.handler.sendMessage(msg);
                    }

                    public void onFinished(String s) {
                        Log.i(FirmwareUpdateActivity.TAG, "下载成功");
                        FirmwareUpdateActivity.this.handler.sendEmptyMessage(4097);
                    }

                    public void onFailed(int failCode) {
                        Log.i(FirmwareUpdateActivity.TAG, "bin 下载失败:" + failCode);
                        FirmwareUpdateActivity.this.handler.sendEmptyMessage(4099);
                    }
                }

                C17241() {
                }

                public void onStart(long l) {
                }

                public void onProgress(float v) {
                    Log.i(FirmwareUpdateActivity.TAG, "dat onProgress:" + ((int) ((v * 100.0f) / 2.0f)) + "%");
                    Message msg = new Message();
                    msg.what = 4098;
                    msg.arg1 = (int) ((v * 100.0f) / 2.0f);
                    FirmwareUpdateActivity.this.handler.sendMessage(msg);
                }

                public void onFinished(String s) {
                    DroiUpdate.downloadInappUpdateFile(FirmwareUpdateActivity.this.response, binPath, new C17231());
                }

                public void onFailed(int i) {
                    Log.i(FirmwareUpdateActivity.TAG, "dat 下载失败:" + i);
                    FirmwareUpdateActivity.this.handler.sendEmptyMessage(4099);
                }
            }

            public void onUpdateReturned(int i, DroiInappUpdateResponse droiInappUpdateResponse) {
                switch (i) {
                    case 0:
                    case 2:
                    case 3:
                        Log.i(FirmwareUpdateActivity.TAG, "dat 没有更新");
                        FirmwareUpdateActivity.this.handler.sendEmptyMessage(4099);
                        return;
                    case 1:
                        Log.i(FirmwareUpdateActivity.TAG, "dat 发现更新:" + droiInappUpdateResponse.getFileUrl() + " " + datPath);
                        DroiUpdate.downloadInappUpdateFile(droiInappUpdateResponse, datPath, new C17241());
                        return;
                    default:
                        return;
                }
            }
        });
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case C1680R.id.btn_close:
                finish();
                return;
            case C1680R.id.bt_send:
                switch (this.step) {
                    case 8193:
                        this.btSend.setVisibility(8);
                        this.tvHint.setText(C1680R.string.firmwear_des_3);
                        this.tvProgress.setText("0%");
                        this.btClose.setVisibility(4);
                        this.tvStatus.setText(C1680R.string.firmware_updating);
                        if (this.updateType == 2) {
                            updateByBle(this.binFilePath, this.datFilePath);
                            return;
                        }
                        return;
                    case 8194:
                        BtManagerService.sendFileByBluetooth(this, this.binFilePath);
                        return;
                    default:
                        return;
                }
            default:
                return;
        }
    }

    private void downLoadClassicBin(String filePath) {
        DroiUpdate.downloadInappUpdateFile(this.response, filePath, new C17263());
    }

    private void updateByBle(String binPath, String datPath) {
        BtManagerService.updateBleDevice(binPath, datPath, new C17274());
    }

    public void onBackPressed() {
    }
}
