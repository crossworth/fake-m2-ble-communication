package com.zhuoyou.plugin.ble;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothAdapter.LeScanCallback;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.fithealth.running.R;
import com.zhuoyou.plugin.bluetooth.data.Util;
import com.zhuoyou.plugin.running.RunningApp;
import com.zhuoyou.plugin.running.Tools;
import java.util.ArrayList;

@SuppressLint({"NewApi"})
public class BindBleDeviceActivity extends Activity implements OnClickListener {
    public static final String ACTION_BINDING_DEVICE_SUCCESS = "com.droi.binding.device.success";
    private static final int CONNECT_TIMEOUT = 50;
    private static final int GETTING_SERVICE_TIMEOUT = 60;
    private static final int REQUEST_ENABLE_BT = 1;
    private static final int SCAN_TIMEOUT = 10;
    private static BluetoothDevice connectDevice = null;
    public static Activity instance = null;
    private Animation BluetoothSinalAni;
    private String SearchName;
    private String TAG = "BindBleDeviceActivity";
    Runnable WaitGetServiceRunnable = new C11475();
    private Animation animation;
    private Thread checkServiceThread = null;
    private int clickItemCount = 0;
    private int clickposition = -1;
    private int connState = -1;
    private BleDeviceInfo curConnectingDevice;
    private String deviceName;
    private boolean isClickConn = false;
    private boolean isSupportBle = false;
    private boolean is_conneting = false;
    private int lastClickPosition = -1;
    private boolean mBTEnadble = false;
    private BroadcastReceiver mBindingDeviceReceiver = new C11499();
    private ProgressDialog mBindingDialog;
    private BleManagerService mBleDeviceManagerService = null;
    private BluetoothAdapter mBluetoothAdapter;
    private LinearLayout mBondHelp;
    private LinearLayout mBondScan = null;
    private final BroadcastReceiver mBroadcastReceiver = new C11464();
    private CustomTimerCallback mConnectCallback = new C18757();
    private CustomTimer mConnectTimer = null;
    private ImageView mConnectingImg;
    private ListView mDecList;
    private ImageView mDeviceAni = null;
    private String[] mDeviceFilter = new String[]{"Unik 1", "Unik 2", "LEO", "A7", "T-Band", "Rumor-1", "Rumor-2", "M2"};
    private LinearLayout mFreQuestion;
    private boolean mIsConnected = false;
    private LeDeviceListAdapter mLeDeviceListAdapter;
    private LeScanCallback mLeScanCallback;
    private LinearLayout mMoreEquip;
    private TextView mNumDevices = null;
    private int mNumDevs = 0;
    private CustomTimerCallback mScanCallback = new C18746();
    private CustomTimer mScanTimer = null;
    private boolean mScanning = false;
    private TextView mTitle;
    private ImageView mTitleBack;

    class C11431 implements LeScanCallback {
        C11431() {
        }

        public void onLeScan(final BluetoothDevice device, final int rssi, byte[] scanRecord) {
            BindBleDeviceActivity.this.runOnUiThread(new Runnable() {
                public void run() {
                    Log.d(BindBleDeviceActivity.this.TAG, "onLeScan Thread");
                    if (BindBleDeviceActivity.this.checkDeviceFilter(device)) {
                        if (BindBleDeviceActivity.this.deviceInfoExists(device.getAddress())) {
                            BindBleDeviceActivity.this.findDeviceInfo(device).updateRssi(rssi);
                        } else {
                            Log.d(BindBleDeviceActivity.this.TAG, "device.getAddress() :" + device.getAddress());
                            BindBleDeviceActivity.this.addDevice(BindBleDeviceActivity.this.createDeviceInfo(device, rssi));
                        }
                        BindBleDeviceActivity.this.mLeDeviceListAdapter.notifyDataSetChanged();
                    }
                }
            });
        }
    }

    class C11442 implements OnItemClickListener {
        C11442() {
        }

        public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
            Tools.setConnectNotVibtation(false);
            BindBleDeviceActivity.this.connectDeviceByListPosition(position);
            BindBleDeviceActivity.this.isClickConn = true;
            Log.d(BindBleDeviceActivity.this.TAG, "sendBroadcast ACTION_DISCONNECT_BINDED_DEVICE");
        }
    }

    class C11453 implements Runnable {
        C11453() {
        }

        public void run() {
            BindBleDeviceActivity.this.stopScan();
            BindBleDeviceActivity.this.autoChoiceByScanResult();
        }
    }

    class C11464 extends BroadcastReceiver {
        C11464() {
        }

        public void onReceive(Context context, Intent intent) {
            BindBleDeviceActivity.this.connState = -1;
            String action = intent.getAction();
            if ("android.bluetooth.adapter.action.STATE_CHANGED".equals(action)) {
                int status = intent.getIntExtra("android.bluetooth.adapter.extra.STATE", 10);
                Log.d(BindBleDeviceActivity.this.TAG, "ACTION_STATE_CHANGED :" + status);
                if (status == 10) {
                    BindBleDeviceActivity.this.mNumDevs = 0;
                    BindBleDeviceActivity.this.setScanButtonClickable(false);
                    refresh();
                } else if (status == 12) {
                    BindBleDeviceActivity.this.setScanButtonClickable(true);
                    refresh();
                }
            }
            if (BluetoothLeService.ACTION_GATT_CONNECTED.equals(action)) {
                BindBleDeviceActivity.this.mIsConnected = true;
                BindBleDeviceActivity.this.stopTimers();
                BindBleDeviceActivity.this.stopPBAnimator();
                BindBleDeviceActivity.this.checkServiceThread = new Thread(BindBleDeviceActivity.this.WaitGetServiceRunnable);
                BindBleDeviceActivity.this.checkServiceThread.start();
                BindBleDeviceActivity.this.is_conneting = false;
            }
            if (BluetoothLeService.ACTION_GATT_DISCONNECTED.equals(action) && BindBleDeviceActivity.this.isClickConn) {
                BindBleDeviceActivity.this.mIsConnected = false;
                Log.d(BindBleDeviceActivity.this.TAG, "ACTION_GATT_DISCONNECTED");
                BindBleDeviceActivity.this.setScanButtonClickable(true);
                BindBleDeviceActivity.this.stopTimers();
                BindBleDeviceActivity.this.stopPBAnimator();
                BindBleDeviceActivity.this.connState = 3;
                BindBleDeviceActivity.this.mLeDeviceListAdapter.notifyDataSetChanged();
                BindBleDeviceActivity.this.is_conneting = false;
                BindBleDeviceActivity.this.clickItemCount = 0;
                if (BindBleDeviceActivity.this.checkServiceThread != null) {
                    BindBleDeviceActivity.this.checkServiceThread.interrupt();
                    BindBleDeviceActivity.this.checkServiceThread = null;
                }
            }
        }

        private void refresh() {
            BindBleDeviceActivity.this.finish();
            BindBleDeviceActivity.this.startActivity(new Intent(BindBleDeviceActivity.this, BindBleDeviceActivity.class));
            BindBleDeviceActivity.this.onCreate(null);
        }
    }

    class C11475 implements Runnable {
        C11475() {
        }

        public void run() {
            int i = 60;
            if (BindBleDeviceActivity.this.mBleDeviceManagerService != null) {
                while (BindBleDeviceActivity.this.mBleDeviceManagerService.IsSupportedServicesListEmpty() && i > 0) {
                    i--;
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                if (BindBleDeviceActivity.this.mBleDeviceManagerService.IsSupportedServicesListEmpty()) {
                    Log.d(BindBleDeviceActivity.this.TAG, "Get Service TimeOut,disconnect the devices");
                    BindBleDeviceActivity.this.disconnectCurrentDevice();
                    BindBleDeviceActivity.this.stopPBAnimator();
                    BindBleDeviceActivity.this.is_conneting = false;
                    return;
                }
                Log.d(BindBleDeviceActivity.this.TAG, "Get Service OK");
                BindBleDeviceActivity.this.stopPBAnimator();
                BindBleDeviceActivity.this.setResult(-1);
                BindBleDeviceActivity.this.finish();
                Log.i("hph", "WaitGetServiceRunnable finish");
            }
        }
    }

    class C11488 implements Runnable {
        C11488() {
        }

        public void run() {
            if (BindBleDeviceActivity.this.animation.hasStarted()) {
                BindBleDeviceActivity.this.animation.cancel();
                BindBleDeviceActivity.this.mDeviceAni.clearAnimation();
            }
        }
    }

    class C11499 extends BroadcastReceiver {
        C11499() {
        }

        /* JADX WARNING: inconsistent code. */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public void onReceive(android.content.Context r4, android.content.Intent r5) {
            /*
            r3 = this;
            r2 = -1;
            r0 = r5.getAction();
            r1 = r0.hashCode();
            switch(r1) {
                case 1391736269: goto L_0x0011;
                default: goto L_0x000c;
            };
        L_0x000c:
            r1 = r2;
        L_0x000d:
            switch(r1) {
                case 0: goto L_0x001b;
                default: goto L_0x0010;
            };
        L_0x0010:
            return;
        L_0x0011:
            r1 = "com.droi.binding.device.success";
            r1 = r0.equals(r1);
            if (r1 == 0) goto L_0x000c;
        L_0x0019:
            r1 = 0;
            goto L_0x000d;
        L_0x001b:
            r1 = com.zhuoyou.plugin.ble.BindBleDeviceActivity.this;
            r1 = r1.mBindingDialog;
            r1.dismiss();
            r1 = com.zhuoyou.plugin.ble.BindBleDeviceActivity.this;
            r1.stopPBAnimator();
            r1 = com.zhuoyou.plugin.ble.BindBleDeviceActivity.this;
            r1.setResult(r2);
            r1 = com.zhuoyou.plugin.ble.BindBleDeviceActivity.this;
            r1.finish();
            r1 = "hph";
            r2 = "mBindingDeviceReceiver ACTION_BINDING_DEVICE_SUCCESS";
            android.util.Log.i(r1, r2);
            goto L_0x0010;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.zhuoyou.plugin.ble.BindBleDeviceActivity.9.onReceive(android.content.Context, android.content.Intent):void");
        }
    }

    private class LeDeviceListAdapter extends BaseAdapter {
        private LayoutInflater mInflator;
        private ArrayList<BleDeviceInfo> mLeDevices = new ArrayList();

        public LeDeviceListAdapter() {
            this.mInflator = BindBleDeviceActivity.this.getLayoutInflater();
        }

        public void addDevice(BleDeviceInfo device) {
            if (!this.mLeDevices.contains(device)) {
                BindBleDeviceActivity.this.mNumDevs = BindBleDeviceActivity.this.mNumDevs + 1;
                this.mLeDevices.add(device);
            }
        }

        public BleDeviceInfo getDevice(int position) {
            return (BleDeviceInfo) this.mLeDevices.get(position);
        }

        public void clear() {
            this.mLeDevices.clear();
        }

        public int getCount() {
            return this.mLeDevices.size();
        }

        public Object getItem(int i) {
            return this.mLeDevices.get(i);
        }

        public long getItemId(int i) {
            return (long) i;
        }

        @SuppressLint({"InflateParams"})
        public View getView(int i, View view, ViewGroup viewGroup) {
            ViewHolder viewHolder;
            if (view == null) {
                view = this.mInflator.inflate(R.layout.listitem_device, null);
                viewHolder = new ViewHolder();
                viewHolder.deviceVenus = (ImageView) view.findViewById(R.id.device_venus_little);
                viewHolder.deviceAddress = (TextView) view.findViewById(R.id.device_address);
                viewHolder.deviceName = (TextView) view.findViewById(R.id.device_name);
                viewHolder.deviceRssi = (ImageView) view.findViewById(R.id.device_rssi);
                view.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) view.getTag();
            }
            BleDeviceInfo device = (BleDeviceInfo) this.mLeDevices.get(i);
            String deviceName = device.getBluetoothDevice().getName();
            if (deviceName == null || deviceName.length() <= 0) {
                viewHolder.deviceName.setText(R.string.unknown_device);
            } else {
                viewHolder.deviceName.setText(deviceName);
            }
            viewHolder.deviceVenus.setBackgroundResource(Util.getProductIcon(deviceName, true));
            viewHolder.deviceAddress.setText(device.getBluetoothDevice().getAddress());
            if (i != BindBleDeviceActivity.this.clickposition) {
                viewHolder.deviceRssi.setBackgroundResource(getRssiImage(((BleDeviceInfo) this.mLeDevices.get(i)).getRssi()));
                view.findViewById(R.id.bt_disconnected).setVisibility(8);
            } else if (BindBleDeviceActivity.this.connState == 3) {
                viewHolder.deviceRssi.setBackgroundResource(R.drawable.equip_disconnected);
                view.findViewById(R.id.bt_disconnected).setVisibility(0);
            } else {
                viewHolder.deviceRssi.setBackgroundResource(R.drawable.bluetooth_singal);
                AnimationDrawable animationdrawable = (AnimationDrawable) viewHolder.deviceRssi.getBackground();
                animationdrawable.setOneShot(false);
                animationdrawable.start();
                view.findViewById(R.id.bt_disconnected).setVisibility(8);
            }
            return view;
        }

        private int getRssiImage(int rssi) {
            if (rssi > -70 && rssi < -55) {
                return R.drawable.rssi_batter;
            }
            if (rssi > -55) {
                return R.drawable.rssi_good;
            }
            return R.drawable.rssi_bad;
        }
    }

    static class ViewHolder {
        TextView deviceAddress;
        TextView deviceName;
        ImageView deviceRssi;
        ImageView deviceVenus;

        ViewHolder() {
        }
    }

    class C18746 extends CustomTimerCallback {
        C18746() {
        }

        public void onTimeout() {
            Log.d(BindBleDeviceActivity.this.TAG, "onTimeout");
            BindBleDeviceActivity.this.onScanTimeout();
        }

        public void onTick(int i) {
        }
    }

    class C18757 extends CustomTimerCallback {
        C18757() {
        }

        public void onTimeout() {
            Log.d(BindBleDeviceActivity.this.TAG, "Connecting TimeOut,disconnect the devices");
            BindBleDeviceActivity.this.stopPBAnimator();
            BindBleDeviceActivity.this.disconnectCurrentDevice();
        }

        public void onTick(int i) {
        }
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_bind_venus);
        instance = this;
        this.mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        this.mDecList = (ListView) findViewById(R.id.devices_listview);
        LinearLayout footer = new LinearLayout(this);
        this.mDecList.addFooterView((LinearLayout) getLayoutInflater().inflate(R.layout.equipment_footer, null), null, false);
        initView();
        IntentFilter mFilter = new IntentFilter(BluetoothLeService.ACTION_GATT_CONNECTED);
        mFilter.addAction(BluetoothLeService.ACTION_GATT_DISCONNECTED);
        mFilter.addAction("android.bluetooth.adapter.action.STATE_CHANGED");
        registerReceiver(this.mBroadcastReceiver, mFilter);
        IntentFilter bindingFilter = new IntentFilter();
        bindingFilter.addAction(ACTION_BINDING_DEVICE_SUCCESS);
        registerReceiver(this.mBindingDeviceReceiver, bindingFilter);
        initBluetoothView();
    }

    private void initCallback() {
        this.mLeScanCallback = new C11431();
    }

    private void initNotSupportView() {
        this.mDecList.setAdapter(null);
    }

    private void initBluetooth() {
        this.mBleDeviceManagerService = BleManagerService.getInstance();
        this.mNumDevices = (TextView) findViewById(R.id.num_devices);
        this.mNumDevices.setVisibility(0);
        this.mLeDeviceListAdapter = new LeDeviceListAdapter();
        this.mDecList.setAdapter(this.mLeDeviceListAdapter);
        this.mDecList.setOnItemClickListener(new C11442());
        this.mDeviceAni = (ImageView) findViewById(R.id.ani_device);
        this.mDeviceAni.setVisibility(0);
        this.mDeviceAni.setBackgroundResource(R.drawable.refresh_devices);
        this.animation = AnimationUtils.loadAnimation(this, R.anim.search_devices_ani);
        this.mDeviceAni.setAnimation(this.animation);
        this.animation.startNow();
    }

    private void initBluetoothView() {
        if (!this.mBluetoothAdapter.isEnabled()) {
            if (this.mDeviceAni != null) {
                this.mDeviceAni.setVisibility(8);
            }
            if (this.mNumDevices != null) {
                if (this.animation.hasStarted()) {
                    this.animation.cancel();
                    this.mDeviceAni.clearAnimation();
                }
                this.mNumDevices.setVisibility(8);
            }
            Toast.makeText(getApplicationContext(), R.string.ensure_bluetooth_isenable, 0).show();
            this.mDecList.setAdapter(null);
        } else if (RunningApp.isBLESupport) {
            this.isSupportBle = true;
            initCallback();
            initBluetooth();
            startScan();
            updateBtStatusAndUI();
        } else {
            initNotSupportView();
            this.isSupportBle = false;
            Toast.makeText(getApplicationContext(), R.string.not_support_ble_tip, 0).show();
        }
    }

    private void initView() {
        this.mTitleBack = (ImageView) findViewById(R.id.bt_back);
        this.mTitleBack.setOnClickListener(this);
        this.mTitle = (TextView) findViewById(R.id.bt_title);
        this.mTitle.setText(R.string.nearby_equip);
        this.mMoreEquip = (LinearLayout) findViewById(R.id.more_equip);
        this.mMoreEquip.setOnClickListener(this);
    }

    protected void onResume() {
        super.onResume();
        Tools.setOpenDialogState(false);
    }

    protected void onDestroy() {
        super.onDestroy();
        if (this.isSupportBle) {
            if (this.mScanning) {
                stopScan();
                this.mScanning = false;
            }
            stopTimers();
        }
        unregisterReceiver(this.mBroadcastReceiver);
        unregisterReceiver(this.mBindingDeviceReceiver);
    }

    protected void onPause() {
        super.onPause();
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.more_equip:
                startActivity(new Intent(this, MoreEquipActivity.class));
                return;
            case R.id.bt_back:
                finish();
                return;
            case R.id.ani_device:
                if (this.animation.hasEnded()) {
                    startScan();
                    return;
                }
                stopTimers();
                stopScan();
                return;
            default:
                return;
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1 && resultCode == 0) {
            finish();
            Log.i("hph", "onActivityResult finish");
            return;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void startScan() {
        this.isClickConn = false;
        this.mNumDevs = 0;
        this.mLeDeviceListAdapter.clear();
        scanLeDevice(true);
        if (this.mScanning) {
            startPBAnimator();
            this.mScanTimer = new CustomTimer(10, this.mScanCallback);
            return;
        }
        Log.d(this.TAG, "Device discovery start failed");
    }

    private void stopScan() {
        this.mScanning = false;
        scanLeDevice(false);
        stopPBAnimator();
    }

    private boolean scanLeDevice(boolean enable) {
        if (enable) {
            this.mScanning = this.mBluetoothAdapter.startLeScan(this.mLeScanCallback);
        } else {
            this.mScanning = false;
            this.mBluetoothAdapter.stopLeScan(this.mLeScanCallback);
        }
        return this.mScanning;
    }

    public void onScanTimeout() {
        runOnUiThread(new C11453());
    }

    private BleDeviceInfo createDeviceInfo(BluetoothDevice device, int rssi) {
        return new BleDeviceInfo(device, rssi);
    }

    private boolean checkDeviceFilter(BluetoothDevice device) {
        int n = 0;
        if (this.mDeviceFilter != null) {
            n = this.mDeviceFilter.length;
        }
        if (n <= 0) {
            return true;
        }
        boolean found = false;
        for (int i = 0; i < n && !found && !TextUtils.isEmpty(device.getName()); i++) {
            found = device.getName().equals(this.mDeviceFilter[i]);
            Log.i(this.TAG, "deviceName=" + device.getName());
        }
        Log.d(this.TAG, "found = " + found);
        return found;
    }

    private boolean deviceInfoExists(String address) {
        for (int i = 0; i < this.mLeDeviceListAdapter.getCount(); i++) {
            if (this.mLeDeviceListAdapter.getDevice(i).getBluetoothDevice().getAddress().equals(address)) {
                return true;
            }
        }
        return false;
    }

    private BleDeviceInfo findDeviceInfo(BluetoothDevice device) {
        for (int i = 0; i < this.mLeDeviceListAdapter.getCount(); i++) {
            if (this.mLeDeviceListAdapter.getDevice(i).getBluetoothDevice().getAddress().equals(device.getAddress())) {
                return this.mLeDeviceListAdapter.getDevice(i);
            }
        }
        return null;
    }

    private void addDevice(BleDeviceInfo device) {
        this.mLeDeviceListAdapter.addDevice(device);
        if (this.mNumDevs > 1 || this.mNumDevs == 1) {
            this.mNumDevices.setText(this.mNumDevs + getResources().getString(R.string.bind_venus_devices));
        }
    }

    private void stopTimers() {
        if (this.mScanTimer != null) {
            Log.d(this.TAG, "stopScanTimers");
            this.mScanTimer.stop();
            this.mScanTimer = null;
        }
        if (this.mConnectTimer != null) {
            Log.d(this.TAG, "stopConnectTimers");
            this.mConnectTimer.stop();
            this.mConnectTimer = null;
        }
    }

    private void connectDeviceByListPosition(int position) {
        String BleAddress = Util.getLatestConnectDeviceAddress(this);
        Intent DisconnectDevicesIntent = new Intent(BleManagerService.ACTION_DISCONNECT_BINDED_DEVICE);
        DisconnectDevicesIntent.putExtra("BINDED_DEVICE_ADDRESS", BleAddress);
        sendBroadcast(DisconnectDevicesIntent);
        connectDevice = Util.getConnectDevice();
        if (connectDevice != null) {
            Util.removeBond(this.mBluetoothAdapter.getRemoteDevice(connectDevice.getAddress()));
        }
        this.connState = -1;
        this.clickposition = position;
        this.mLeDeviceListAdapter.notifyDataSetChanged();
        if (!this.is_conneting) {
            this.is_conneting = true;
            BleDeviceInfo device = this.mLeDeviceListAdapter.getDevice(position);
            Log.d(this.TAG, "device" + device);
            Log.d(this.TAG, "mBleDeviceManagerService" + this.mBleDeviceManagerService);
            if (device != null && this.mBleDeviceManagerService != null) {
                Log.d(this.TAG, "onClick:Connect device :" + device.getBluetoothDevice().getName() + ";address =" + device.getBluetoothDevice().getAddress());
                boolean result = false;
                if (device.getBluetoothDevice().getAddress() != null) {
                    setScanButtonClickable(false);
                    this.mConnectTimer = new CustomTimer(50, this.mConnectCallback);
                    this.deviceName = device.getBluetoothDevice().getName();
                    result = this.mBleDeviceManagerService.ConnectToDevice(device);
                    Log.i("hph", "mBleDeviceManagerService result=" + result);
                }
                if (result) {
                    Log.d(this.TAG, "connect sussec");
                    this.curConnectingDevice = device;
                    setResult(-1);
                    return;
                }
                Log.d(this.TAG, "connect failed");
                setResult(0);
            }
        }
    }

    private void disconnectCurrentDevice() {
        setScanButtonClickable(true);
        if (this.mIsConnected && this.curConnectingDevice != null && this.curConnectingDevice.getBluetoothDevice().getAddress() != null) {
            this.mBleDeviceManagerService.disConnectDevice(this.curConnectingDevice);
        }
    }

    private void autoChoiceByScanResult() {
        if (this.mNumDevs == 1) {
            connectDeviceByListPosition(0);
        }
    }

    private void setScanButtonClickable(boolean enable) {
        if (this.mDeviceAni != null) {
            this.mDeviceAni.setClickable(enable);
        }
    }

    private void updateBtStatusAndUI() {
        if (BluetoothAdapter.getDefaultAdapter().isEnabled()) {
            this.mBTEnadble = true;
        } else {
            this.mBTEnadble = false;
        }
        if (this.mBTEnadble) {
            setScanButtonClickable(true);
        } else {
            setScanButtonClickable(false);
        }
    }

    private void startPBAnimator() {
        Log.i("hello", "start startPBAnimator");
        this.animation.reset();
        this.mDeviceAni.startAnimation(this.animation);
        this.animation.start();
    }

    private void stopPBAnimator() {
        Log.i("hello", "stop stopPBAnimator");
        runOnUiThread(new C11488());
    }
}
