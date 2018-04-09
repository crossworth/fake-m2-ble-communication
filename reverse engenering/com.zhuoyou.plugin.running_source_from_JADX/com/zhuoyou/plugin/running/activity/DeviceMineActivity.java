package com.zhuoyou.plugin.running.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.droi.btlib.service.BtDevice;
import com.droi.btlib.service.BtDevice.ConnectCallback;
import com.droi.btlib.service.BtManagerService;
import com.droi.btlib.service.BtManagerService.CONNECT_STATE;
import com.zhuoyou.plugin.running.C1680R;
import com.zhuoyou.plugin.running.base.BaseActivity;
import com.zhuoyou.plugin.running.tools.AnimUtils;
import com.zhuoyou.plugin.running.tools.FirmwareUtils;
import com.zhuoyou.plugin.running.tools.SPUtils;
import com.zhuoyou.plugin.running.tools.Tools;
import com.zhuoyou.plugin.running.view.MyAlertDialog;
import com.zhuoyou.plugin.running.view.MyAlertDialog.OnClickListener;

public class DeviceMineActivity extends BaseActivity {
    public static final String KEY_HAS_UPDATE = "key_has_update";
    private static final String TAG = "DeviceMineActivity";
    private BtDevice btDevice;
    private TextView btnRebootDevice;
    private ConnectCallback callback = new C17141();
    private ImageView imgDeviceConnectState;
    private ImageView imgDeviceIcon;
    private ImageView imgUpdatePoint;
    private RelativeLayout rlFirmwareVersion;
    private RelativeLayout rlHardwareVersion;
    private TextView tvDeviceConnectState;
    private TextView tvDeviceName;
    private TextView tvFirmWareVersion;
    private TextView tvMacAddress;

    class C17141 implements ConnectCallback {
        C17141() {
        }

        public void success(BtDevice device) {
            DeviceMineActivity.this.btDevice = device;
            DeviceMineActivity.this.initData();
            FirmwareUtils.checkDeviceUpdate(DeviceMineActivity.this.btDevice, DeviceMineActivity.this, DeviceMineActivity.this.imgUpdatePoint, true, false);
        }

        public void fail(int state) {
            switch (state) {
                case 0:
                    DeviceMineActivity.this.initData();
                    return;
                case 2:
                    DeviceMineActivity.this.initData();
                    return;
                default:
                    return;
            }
        }

        public void disconnect(BtDevice device) {
            DeviceMineActivity.this.initData();
        }

        public void connecting(BtDevice device) {
        }

        public void battery(int battery) {
            if (battery == 101) {
                DeviceMineActivity.this.tvDeviceConnectState.setText(C1680R.string.battery_charging);
            } else if (battery == 102) {
                DeviceMineActivity.this.tvDeviceConnectState.setText(C1680R.string.battery_complete);
            } else {
                DeviceMineActivity.this.tvDeviceConnectState.setText(battery + "%");
            }
        }
    }

    class C17152 implements OnClickListener {
        C17152() {
        }

        public void onClick(int witch) {
            BtManagerService.deleteDevice();
            SPUtils.clear(SPUtils.getDeviceShared());
            DeviceMineActivity.this.finish();
        }
    }

    class C17163 implements OnClickListener {
        C17163() {
        }

        public void onClick(int witch) {
            DeviceMineActivity.this.btDevice.rebootDevice();
            Tools.makeToast((int) C1680R.string.device_reboot_success);
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) C1680R.layout.activity_mine_device);
        this.btDevice = BtManagerService.getConnectDevice(this.callback);
        initView();
        initData();
    }

    private void initView() {
        this.imgDeviceIcon = (ImageView) findViewById(C1680R.id.img_device_icon);
        this.tvDeviceName = (TextView) findViewById(C1680R.id.tv_device_name);
        this.tvDeviceConnectState = (TextView) findViewById(C1680R.id.tv_device_connect_state);
        this.imgDeviceConnectState = (ImageView) findViewById(C1680R.id.img_device_connect_state);
        this.tvFirmWareVersion = (TextView) findViewById(C1680R.id.tv_firmware_version);
        this.tvMacAddress = (TextView) findViewById(C1680R.id.tv_mac_address);
        this.rlFirmwareVersion = (RelativeLayout) findViewById(C1680R.id.rl_firmware_update);
        this.rlHardwareVersion = (RelativeLayout) findViewById(C1680R.id.rl_firmware_version);
        this.imgUpdatePoint = (ImageView) findViewById(C1680R.id.img_update_point);
        this.btnRebootDevice = (TextView) findViewById(C1680R.id.btn_reboot_device);
    }

    private void initData() {
        int i = 0;
        if (this.btDevice != null) {
            Log.i(TAG, "initData:");
            this.tvDeviceName.setText(this.btDevice.getName());
            if (TextUtils.isEmpty(this.btDevice.getHardWare())) {
                this.tvFirmWareVersion.setVisibility(8);
            } else {
                this.tvFirmWareVersion.setText(this.btDevice.getHardWare());
            }
            this.tvMacAddress.setText(this.btDevice.getMacAddress());
            if (this.btDevice.getConnectState() == CONNECT_STATE.CONNECTED) {
                if (this.btDevice.getBattery() == 101) {
                    this.tvDeviceConnectState.setText(C1680R.string.battery_charging);
                } else if (this.btDevice.getBattery() == 102) {
                    this.tvDeviceConnectState.setText(C1680R.string.battery_complete);
                } else {
                    this.tvDeviceConnectState.setText(this.btDevice.getBattery() + "%");
                }
                this.imgDeviceConnectState.setImageResource(C1680R.drawable.bt_state_connect);
            } else if (this.btDevice.getConnectState() == CONNECT_STATE.CONNECTING) {
                this.tvDeviceConnectState.setText(C1680R.string.connect_state_connecting);
                this.imgDeviceConnectState.setImageResource(C1680R.drawable.bt_connecting);
                AnimUtils.playAnimList(this.imgDeviceConnectState.getDrawable());
            } else {
                this.tvDeviceConnectState.setText(C1680R.string.connect_state_disconnect);
                this.imgDeviceConnectState.setImageResource(C1680R.drawable.bt_state_disconnect);
            }
            if (!this.btDevice.supportFirmwareVersion()) {
                this.rlFirmwareVersion.setVisibility(8);
                this.rlHardwareVersion.setVisibility(8);
            }
            if (!this.btDevice.supportReboot()) {
                this.btnRebootDevice.setVisibility(8);
            }
            ImageView imageView = this.imgUpdatePoint;
            if (!getIntent().getBooleanExtra(KEY_HAS_UPDATE, false)) {
                i = 8;
            }
            imageView.setVisibility(i);
        }
    }

    protected void onDestroy() {
        super.onDestroy();
        BtManagerService.removeConnectCallback(this.callback);
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case C1680R.id.rl_device_layout:
                if (!BtManagerService.isBluetoothOpen()) {
                    Tools.makeToast((int) C1680R.string.bt_tip_bt_not_open);
                    return;
                } else if (this.btDevice.getConnectState() == CONNECT_STATE.DISCONNECTED) {
                    this.btDevice.connect(this.callback);
                    this.tvDeviceConnectState.setText(C1680R.string.connect_state_connecting);
                    this.imgDeviceConnectState.setImageResource(C1680R.drawable.bt_connecting);
                    AnimUtils.playAnimList(this.imgDeviceConnectState.getDrawable());
                    return;
                } else {
                    return;
                }
            case C1680R.id.rl_firmware_update:
                if (this.btDevice == null) {
                    return;
                }
                if (this.btDevice.getConnectState() != CONNECT_STATE.CONNECTED) {
                    Tools.makeToast((int) C1680R.string.heart_device_not_connect);
                    return;
                } else {
                    FirmwareUtils.checkDeviceUpdate(this.btDevice, this, this.imgUpdatePoint, true, true);
                    return;
                }
            case C1680R.id.btn_reboot_device:
                if (this.btDevice == null) {
                    return;
                }
                if (this.btDevice.getConnectState() != CONNECT_STATE.CONNECTED) {
                    Tools.makeToast((int) C1680R.string.heart_device_not_connect);
                    return;
                } else {
                    showRebootDeviceDialog();
                    return;
                }
            case C1680R.id.btn_delete_device:
                showDeleteHintDialog();
                return;
            default:
                return;
        }
    }

    private void showDeleteHintDialog() {
        MyAlertDialog dialog = new MyAlertDialog(this);
        dialog.setTitle((int) C1680R.string.bt_device_delete);
        dialog.setMessage((int) C1680R.string.bt_device_delete_hint);
        dialog.setLeftButton((int) C1680R.string.string_cancel, null);
        dialog.setRightButton((int) C1680R.string.string_ok, new C17152());
        dialog.show();
    }

    private void showRebootDeviceDialog() {
        MyAlertDialog dialog = new MyAlertDialog(this);
        dialog.setTitle((int) C1680R.string.device_reboot_device);
        dialog.setMessage((int) C1680R.string.device_reboot_sure);
        dialog.setLeftButton((int) C1680R.string.string_cancel, null);
        dialog.setRightButton((int) C1680R.string.string_ok, new C17163());
        dialog.show();
    }

    protected void onResume() {
        super.onResume();
        FirmwareUtils.checkDeviceUpdate(this.btDevice, this, this.imgUpdatePoint, false, false);
        initData();
    }
}
