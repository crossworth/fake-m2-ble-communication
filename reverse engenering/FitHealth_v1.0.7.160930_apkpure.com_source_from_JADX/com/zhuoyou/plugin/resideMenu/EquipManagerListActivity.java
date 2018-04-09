package com.zhuoyou.plugin.resideMenu;

import android.app.Activity;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.facebook.login.widget.ToolTipPopup;
import com.fithealth.running.R;
import com.zhuoyou.plugin.ble.BindBleDeviceActivity;
import com.zhuoyou.plugin.ble.BleManagerService;
import com.zhuoyou.plugin.bluetooth.attach.BTPluginActivity;
import com.zhuoyou.plugin.bluetooth.attach.PluginManager;
import com.zhuoyou.plugin.bluetooth.data.Util;
import com.zhuoyou.plugin.bluetooth.product.ProductManager;
import com.zhuoyou.plugin.bluetooth.service.BluetoothService;
import com.zhuoyou.plugin.custom.CustomAlertDialog.Builder;
import com.zhuoyou.plugin.database.DataBaseContants;
import com.zhuoyou.plugin.running.RunningApp;
import com.zhuoyou.plugin.running.Tools;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class EquipManagerListActivity extends Activity implements OnClickListener {
    public static final String ACTION_UNBINDING_DEVICE_SUCCESS = "com.droi.unbinding.device.success";
    public static final int BATTERY = 3;
    public static final int ENTER_PLUGIN_DETAIL = 1;
    public static final int REMOVE_DEVICE_ITEM = 2;
    public static final int UPDATE_BATTERY = 4;
    private static List<Map<String, Object>> bondedDevices;
    private static BluetoothAdapter btAdapt;
    private static BluetoothDevice connectDevice = null;
    private static TextView device_battery;
    private static BluetoothDevice gattconnectDevice = null;
    public static boolean isEditMode = false;
    private static TextView mBondedTv;
    private static TextView mConntedTv;
    private static DeviceListAdapter mDeviceListAdapter = null;
    private static Button mEdit;
    public static Handler mHandler;
    private static EquipManagerListActivity this_ = null;
    private String BLEDeviceFilter = "Unik 1|Unik 2|LEO|Unik 3|LUNA 3|UNIK 3SE|A7|T-Band|Rumor-1|Rumor-2|M2";
    private int BLE_battery = 0;
    private String battery;
    private int batteryNumber = 0;
    private int battery_num = 0;
    private HashMap<String, String> bleBondMap;
    private List<BluetoothDevice> bondDevices = null;
    private String connectProductName;
    private RelativeLayout details_layout;
    private ImageView device_battery_addImageView;
    private RelativeLayout device_layout;
    private ImageView device_logo;
    private TextView device_name;
    OnItemClickListener listViewItemClick = new C13353();
    private BroadcastReceiver mBTConnectReceiver = new C13332();
    public ListView mDeviceList;
    private RelativeLayout mSearch;
    private PluginManager manager;
    private RelativeLayout remove_layout;
    private int status;
    private int totalNumber = 0;
    private ProgressDialog unbundingDialog;

    class C13321 extends Handler {
        C13321() {
        }

        public void handleMessage(Message msg) {
            Map<String, Object> map;
            switch (msg.what) {
                case 1:
                    map = msg.obj;
                    PluginManager manager = PluginManager.getInstance();
                    String productName = Util.getProductName((String) map.get("name"));
                    String nickname = ProductManager.getInstance().getProductCategory(productName);
                    manager.processPlugList(productName);
                    if (manager.getPlugBeans().size() > 0) {
                        Intent intent = new Intent(EquipManagerListActivity.this_, BTPluginActivity.class);
                        intent.putExtra("nick_name", nickname);
                        intent.putExtra("remote_name", productName);
                        intent.putExtra("enable_state", false);
                        EquipManagerListActivity.this_.startActivity(intent);
                        return;
                    }
                    return;
                case 2:
                    map = (Map) msg.obj;
                    String devicename = (String) map.get("name");
                    String address = (String) map.get("address");
                    Util.removeBond(EquipManagerListActivity.btAdapt.getRemoteDevice(address));
                    EquipManagerListActivity.bondedDevices.remove(map);
                    Tools.removeBleBindInfo(EquipManagerListActivity.this, devicename, address);
                    if (address.equals(Util.getLatestConnectDeviceAddress(EquipManagerListActivity.this))) {
                        Util.updateLatestConnectDeviceAddress(EquipManagerListActivity.this, "");
                    }
                    EquipManagerListActivity.mDeviceListAdapter.notifyDataSetChanged();
                    if (EquipManagerListActivity.bondedDevices.size() == 0) {
                        EquipManagerListActivity.mBondedTv.setVisibility(8);
                    }
                    if (EquipManagerListActivity.connectDevice == null && EquipManagerListActivity.bondedDevices.size() <= 0) {
                        EquipManagerListActivity.isEditMode = false;
                        EquipManagerListActivity.mEdit.setText(R.string.bt_edit);
                        return;
                    }
                    return;
                case 3:
                    EquipManagerListActivity.this.status = msg.arg1;
                    EquipManagerListActivity.this.battery_num = msg.arg2 - 32;
                    Bundle bundle = msg.getData();
                    EquipManagerListActivity.this.totalNumber = bundle.getInt("total_number");
                    EquipManagerListActivity.this.batteryNumber = bundle.getInt("battery_number");
                    Log.i("peter", "status111=" + EquipManagerListActivity.this.status);
                    Log.i("peter", "battery_num111=" + EquipManagerListActivity.this.battery_num);
                    if (EquipManagerListActivity.this.batteryNumber == 0 || EquipManagerListActivity.this.battery_num == 255) {
                        EquipManagerListActivity.device_battery.setVisibility(8);
                        EquipManagerListActivity.this.device_battery_addImageView.setVisibility(0);
                        EquipManagerListActivity.this.device_battery_addImageView.setImageResource(R.drawable.battery_0);
                    } else if (EquipManagerListActivity.this.status == 1) {
                        EquipManagerListActivity.this.device_battery_addImageView.setVisibility(0);
                        EquipManagerListActivity.device_battery.setVisibility(8);
                        EquipManagerListActivity.this.device_battery_addImageView.setImageResource(R.drawable.battery_connecting);
                    } else if (EquipManagerListActivity.this.status == 2) {
                        EquipManagerListActivity.this.device_battery_addImageView.setVisibility(0);
                        EquipManagerListActivity.device_battery.setVisibility(8);
                        EquipManagerListActivity.this.device_battery_addImageView.setImageResource(R.drawable.battery_completed);
                    } else if (EquipManagerListActivity.this.totalNumber == 3) {
                        if (EquipManagerListActivity.this.batteryNumber == 1) {
                            EquipManagerListActivity.device_battery.setTextColor(-6709081);
                            EquipManagerListActivity.device_battery.setVisibility(8);
                            EquipManagerListActivity.this.device_battery_addImageView.setVisibility(0);
                            EquipManagerListActivity.this.device_battery_addImageView.setImageResource(R.drawable.battery_one);
                        } else if (EquipManagerListActivity.this.batteryNumber == 2) {
                            EquipManagerListActivity.device_battery.setTextColor(-6709081);
                            EquipManagerListActivity.device_battery.setVisibility(8);
                            EquipManagerListActivity.this.device_battery_addImageView.setVisibility(0);
                            EquipManagerListActivity.this.device_battery_addImageView.setImageResource(R.drawable.battery_two);
                        } else if (EquipManagerListActivity.this.batteryNumber == 3) {
                            EquipManagerListActivity.device_battery.setTextColor(-6709081);
                            EquipManagerListActivity.device_battery.setVisibility(8);
                            EquipManagerListActivity.this.device_battery_addImageView.setVisibility(0);
                            EquipManagerListActivity.this.device_battery_addImageView.setImageResource(R.drawable.battery_4);
                        }
                    } else if (EquipManagerListActivity.this.battery_num > 0 && EquipManagerListActivity.this.battery_num <= 25) {
                        EquipManagerListActivity.device_battery.setTextColor(-6709081);
                        EquipManagerListActivity.device_battery.setVisibility(8);
                        EquipManagerListActivity.this.device_battery_addImageView.setVisibility(0);
                        EquipManagerListActivity.this.device_battery_addImageView.setImageResource(R.drawable.battery_1);
                        Log.i("hph", "battery_num==0...25");
                    } else if (25 < EquipManagerListActivity.this.battery_num && EquipManagerListActivity.this.battery_num <= 50) {
                        EquipManagerListActivity.device_battery.setTextColor(-6709081);
                        EquipManagerListActivity.device_battery.setVisibility(8);
                        EquipManagerListActivity.this.device_battery_addImageView.setVisibility(0);
                        EquipManagerListActivity.this.device_battery_addImageView.setImageResource(R.drawable.battery_2);
                    } else if (50 < EquipManagerListActivity.this.battery_num && EquipManagerListActivity.this.battery_num <= 75) {
                        EquipManagerListActivity.device_battery.setTextColor(-6709081);
                        EquipManagerListActivity.device_battery.setVisibility(8);
                        EquipManagerListActivity.this.device_battery_addImageView.setVisibility(0);
                        EquipManagerListActivity.this.device_battery_addImageView.setImageResource(R.drawable.battery_3);
                    } else if (75 >= EquipManagerListActivity.this.battery_num || EquipManagerListActivity.this.battery_num > 100) {
                        EquipManagerListActivity.device_battery.setTextColor(-65494);
                    } else {
                        EquipManagerListActivity.device_battery.setTextColor(-6709081);
                        EquipManagerListActivity.device_battery.setVisibility(8);
                        EquipManagerListActivity.this.device_battery_addImageView.setVisibility(0);
                        EquipManagerListActivity.this.device_battery_addImageView.setImageResource(R.drawable.battery_4);
                        Log.i("hph", "75...100 Classic");
                    }
                    EquipManagerListActivity.device_battery.setText(EquipManagerListActivity.this.battery);
                    return;
                case 4:
                    EquipManagerListActivity.this.BLE_battery = msg.arg1;
                    EquipManagerListActivity.this.BLE_battery = Tools.getBatteryLevel();
                    Log.i("hph", "BLE_battery111===" + EquipManagerListActivity.this.BLE_battery);
                    return;
                default:
                    return;
            }
        }
    }

    class C13332 extends BroadcastReceiver {
        C13332() {
        }

        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals("com.zhuoyou.running.connect.success")) {
                EquipManagerListActivity.mEdit.setText(R.string.bt_edit);
                EquipManagerListActivity.isEditMode = false;
                EquipManagerListActivity.this.updateProductView();
            } else if (action.equals("com.zhuoyou.running.connect.failed")) {
                EquipManagerListActivity.this.updateProductView();
            } else if (action.equals(EquipManagerListActivity.ACTION_UNBINDING_DEVICE_SUCCESS) && EquipManagerListActivity.this.unbundingDialog != null) {
                EquipManagerListActivity.this.unbundingDialog.dismiss();
                Tools.setDeviceConnectState(1);
                Tools.setClickConnectDeviceName("");
                EquipManagerListActivity.this.removeDevice();
                EquipManagerListActivity.this.finish();
            }
        }
    }

    class C13353 implements OnItemClickListener {
        C13353() {
        }

        public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
            Map<String, Object> map = (Map) EquipManagerListActivity.bondedDevices.get(position);
            if (!((Boolean) map.get(DataBaseContants.MSG_STATE)).booleanValue() && !EquipManagerListActivity.isEditMode) {
                String deviceName = (String) map.get("name");
                String deviceAddress = (String) map.get("address");
                if (!Util.isBleDevice(deviceName)) {
                    final BluetoothDevice device = EquipManagerListActivity.btAdapt.getRemoteDevice((String) map.get("address"));
                    if (EquipManagerListActivity.connectDevice == null) {
                        Util.connect(device);
                        Map<String, Object> newmap = new HashMap();
                        newmap.put("icon", map.get("icon"));
                        newmap.put("name", map.get("name"));
                        newmap.put("address", map.get("address"));
                        newmap.put(DataBaseContants.MSG_STATE, Boolean.valueOf(false));
                        newmap.put("connect", EquipManagerListActivity.this.getResources().getString(R.string.connecting));
                        EquipManagerListActivity.bondedDevices.set(position, newmap);
                        EquipManagerListActivity.mDeviceListAdapter.notifyDataSetChanged();
                        return;
                    }
                    Util.connect(device);
                    new Thread() {
                        public void run() {
                            try {
                                Thread.sleep(3000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            Util.connect(device);
                        }
                    }.start();
                } else if (deviceName != null) {
                    Log.i("hph", "deviceName != null111");
                    EquipManagerListActivity.device_battery.setVisibility(0);
                    EquipManagerListActivity.device_battery.setText(R.string.connecting);
                    Tools.setConnectNotVibtation(false);
                    Intent intent = new Intent(BleManagerService.ACTION_CONNECT_BINDED_DEVICE);
                    intent.putExtra("deviceName", deviceName);
                    intent.putExtra("deviceAddress", deviceAddress);
                    EquipManagerListActivity.this.sendBroadcast(intent);
                }
            }
        }
    }

    class C13364 implements DialogInterface.OnClickListener {
        C13364() {
        }

        public void onClick(DialogInterface dialog, int which) {
            dialog.dismiss();
        }
    }

    class C13395 implements DialogInterface.OnClickListener {

        class C13381 implements Runnable {

            class C13371 implements Runnable {
                C13371() {
                }

                public void run() {
                    EquipManagerListActivity.mEdit.setText(R.string.bt_edit);
                    EquipManagerListActivity.this.details_layout.setVisibility(0);
                    EquipManagerListActivity.this.remove_layout.setVisibility(8);
                }
            }

            C13381() {
            }

            public void run() {
                try {
                    Thread.sleep(ToolTipPopup.DEFAULT_POPUP_DISPLAY_TIME);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                EquipManagerListActivity.this.unbundingDialog.dismiss();
                EquipManagerListActivity.this.runOnUiThread(new C13371());
            }
        }

        C13395() {
        }

        public void onClick(DialogInterface dialog, int which) {
            EquipManagerListActivity.this.unbundingDialog = ProgressDialog.show(EquipManagerListActivity.this_, "解绑设备", "解绑中...", true, false);
            new Thread(new C13381()).start();
            dialog.dismiss();
        }
    }

    class C13406 implements DialogInterface.OnClickListener {
        C13406() {
        }

        public void onClick(DialogInterface dialog, int which) {
            EquipManagerListActivity.mEdit.setText(R.string.bt_edit);
            EquipManagerListActivity.this.details_layout.setVisibility(0);
            EquipManagerListActivity.this.remove_layout.setVisibility(8);
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.equip_manage_dialog_layout);
        btAdapt = BluetoothAdapter.getDefaultAdapter();
        isEditMode = false;
        this_ = this;
        this.manager = PluginManager.getInstance();
        if (getIntent() != null) {
            this.battery = getIntent().getStringExtra("battery");
        }
        initView();
        mHandler = new C13321();
        IntentFilter intent = new IntentFilter();
        intent.addAction("com.zhuoyou.running.connect.success");
        intent.addAction("com.zhuoyou.running.connect.failed");
        intent.addAction(ACTION_UNBINDING_DEVICE_SUCCESS);
        registerReceiver(this.mBTConnectReceiver, intent);
    }

    private void getBLEBattery() {
        this.BLE_battery = Tools.getBatteryLevel();
        Log.i("hph", "BLE_battery===" + this.BLE_battery);
        if (Util.getDeviceName().equals("M2")) {
            updateM2Battery();
        } else if (this.BLE_battery > 0 && this.BLE_battery <= 25) {
            device_battery.setTextColor(-6709081);
            this.device_battery_addImageView.setVisibility(0);
            device_battery.setVisibility(8);
            this.device_battery_addImageView.setImageResource(R.drawable.battery_1);
        } else if (25 < this.BLE_battery && this.BLE_battery <= 50) {
            device_battery.setTextColor(-6709081);
            this.device_battery_addImageView.setVisibility(0);
            device_battery.setVisibility(8);
            this.device_battery_addImageView.setImageResource(R.drawable.battery_2);
        } else if (50 < this.BLE_battery && this.BLE_battery <= 75) {
            device_battery.setTextColor(-6709081);
            this.device_battery_addImageView.setVisibility(0);
            device_battery.setVisibility(8);
            this.device_battery_addImageView.setImageResource(R.drawable.battery_3);
        } else if (75 < this.BLE_battery && this.BLE_battery <= 100) {
            device_battery.setTextColor(-6709081);
            this.device_battery_addImageView.setVisibility(0);
            device_battery.setVisibility(8);
            this.device_battery_addImageView.setImageResource(R.drawable.battery_4);
            Log.i("hph", "75...100 BLE");
        } else if (this.BLE_battery == 101) {
            this.device_battery_addImageView.setVisibility(0);
            device_battery.setVisibility(8);
            this.device_battery_addImageView.setImageResource(R.drawable.battery_connecting);
        } else if (this.BLE_battery == 102) {
            this.device_battery_addImageView.setVisibility(0);
            device_battery.setVisibility(8);
            this.device_battery_addImageView.setImageResource(R.drawable.battery_completed);
        } else {
            device_battery.setTextColor(-65494);
        }
    }

    private void updateM2Battery() {
        if (this.BLE_battery > 0 && this.BLE_battery <= 10) {
            device_battery.setTextColor(-6709081);
            this.device_battery_addImageView.setVisibility(0);
            device_battery.setVisibility(8);
            this.device_battery_addImageView.setImageResource(R.drawable.battery_0);
        } else if (10 < this.BLE_battery && this.BLE_battery <= 30) {
            device_battery.setTextColor(-6709081);
            this.device_battery_addImageView.setVisibility(0);
            device_battery.setVisibility(8);
            this.device_battery_addImageView.setImageResource(R.drawable.battery_1);
        } else if (30 < this.BLE_battery && this.BLE_battery <= 60) {
            device_battery.setTextColor(-6709081);
            this.device_battery_addImageView.setVisibility(0);
            device_battery.setVisibility(8);
            this.device_battery_addImageView.setImageResource(R.drawable.battery_2);
        } else if (60 < this.BLE_battery && this.BLE_battery <= 90) {
            device_battery.setTextColor(-6709081);
            this.device_battery_addImageView.setVisibility(0);
            device_battery.setVisibility(8);
            this.device_battery_addImageView.setImageResource(R.drawable.battery_3);
            Log.i("hph", "75...100 BLE");
        } else if (90 < this.BLE_battery && this.BLE_battery <= 100) {
            device_battery.setTextColor(-6709081);
            this.device_battery_addImageView.setVisibility(0);
            device_battery.setVisibility(8);
            this.device_battery_addImageView.setImageResource(R.drawable.battery_4);
            Log.i("hph", "75...100 BLE");
        } else if (this.BLE_battery == 101) {
            this.device_battery_addImageView.setVisibility(0);
            device_battery.setVisibility(8);
            this.device_battery_addImageView.setImageResource(R.drawable.battery_connecting);
        } else if (this.BLE_battery == 102) {
            this.device_battery_addImageView.setVisibility(0);
            device_battery.setVisibility(8);
            this.device_battery_addImageView.setImageResource(R.drawable.battery_completed);
        } else {
            device_battery.setTextColor(-65494);
        }
    }

    protected void onResume() {
        super.onResume();
        Log.i("hph", "Equ onResume");
        updateProductView();
        if (RunningApp.isBLESupport && BleManagerService.getInstance().GetBleConnectState()) {
            getBLEBattery();
        }
    }

    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(this.mBTConnectReceiver);
    }

    private void initView() {
        mEdit = (Button) findViewById(R.id.edit);
        this.mSearch = (RelativeLayout) findViewById(R.id.searching);
        this.mSearch.setOnClickListener(this_);
        mConntedTv = (TextView) findViewById(R.id.connted);
        this.device_layout = (RelativeLayout) findViewById(R.id.device_layout);
        this.device_logo = (ImageView) findViewById(R.id.device_logo);
        this.device_name = (TextView) findViewById(R.id.device_name);
        device_battery = (TextView) findViewById(R.id.device_battery);
        this.device_battery_addImageView = (ImageView) findViewById(R.id.device_battery_img_add);
        this.details_layout = (RelativeLayout) findViewById(R.id.details_layout);
        this.details_layout.setOnClickListener(this_);
        this.remove_layout = (RelativeLayout) findViewById(R.id.remove_layout);
        this.remove_layout.setOnClickListener(this_);
        mBondedTv = (TextView) findViewById(R.id.bonded);
        this.mDeviceList = (ListView) findViewById(R.id.device_list);
    }

    private void updateProductView() {
        int i;
        bondedDevices = new ArrayList();
        if (BluetoothService.getInstance() != null) {
            connectDevice = Util.getConnectDevice();
            this.bleBondMap = Tools.getBleBindDevice(this);
            if (connectDevice != null) {
                String name = connectDevice.getName();
                this.connectProductName = Util.getProductName(name);
                this.manager.processPlugList(this.connectProductName);
                mConntedTv.setVisibility(0);
                this.device_layout.setVisibility(0);
                this.device_logo.setImageResource(Util.getIconByDeviceName(name, true));
                this.device_name.setText(connectDevice.getName());
                if (this.manager.getPlugBeans().size() == 0) {
                    this.details_layout.setVisibility(8);
                }
                if (this.battery.equals(getResources().getString(R.string.getting_electricity)) || this.battery.equals(getResources().getString(R.string.not_connected))) {
                    Intent intent1 = new Intent("com.tyd.plugin.receiver.sendmsg");
                    intent1.putExtra("plugin_cmd", 3);
                    intent1.putExtra("plugin_content", "");
                    sendBroadcast(intent1);
                    device_battery.setText(R.string.getting_electricity);
                    Log.i("hph", "getting_electricity333");
                } else {
                    device_battery.setText(this.battery);
                }
            }
            this.bondDevices = Util.getBondDevice();
            i = 0;
            while (i < this.bondDevices.size()) {
                if (connectDevice != null && connectDevice.equals(this.bondDevices.get(i))) {
                    this.bondDevices.remove(i);
                }
                i++;
            }
            if (this.bondDevices.size() > 0) {
                for (i = 0; i < this.bondDevices.size(); i++) {
                    Map<String, Object> map = new HashMap();
                    map.put("icon", Integer.valueOf(Util.getIconByDeviceName(((BluetoothDevice) this.bondDevices.get(i)).getName(), false)));
                    map.put("name", ((BluetoothDevice) this.bondDevices.get(i)).getName());
                    map.put("address", ((BluetoothDevice) this.bondDevices.get(i)).getAddress());
                    map.put(DataBaseContants.MSG_STATE, Boolean.valueOf(false));
                    map.put("connect", getResources().getString(R.string.not_connected));
                    bondedDevices.add(map);
                }
            }
        }
        if (RunningApp.isBLESupport) {
            List<BluetoothDevice> gattConnectedDeviceList = BleManagerService.getInstance().getGattCurrentDevice();
            if (gattConnectedDeviceList == null) {
                gattconnectDevice = null;
            } else if (!this.bleBondMap.isEmpty()) {
                for (i = 0; i < gattConnectedDeviceList.size(); i++) {
                    if (this.bleBondMap.containsValue(((BluetoothDevice) gattConnectedDeviceList.get(i)).getAddress())) {
                        gattconnectDevice = (BluetoothDevice) gattConnectedDeviceList.get(i);
                    }
                }
                if (gattconnectDevice != null) {
                    Log.d("yangyang", "gattconnectDevice" + gattconnectDevice);
                    name = Tools.keyString(this.bleBondMap, gattconnectDevice.getAddress());
                    this.connectProductName = Util.getProductName(name);
                    this.manager.processPlugList(this.connectProductName);
                    mConntedTv.setVisibility(0);
                    this.device_layout.setVisibility(0);
                    this.device_logo.setImageResource(Util.getIconByDeviceName(name, true));
                    this.device_name.setText(this.connectProductName);
                    if (this.manager.getPlugBeans().size() == 0) {
                        this.details_layout.setVisibility(8);
                    }
                    if (this.battery.equals(getResources().getString(R.string.getting_electricity)) || this.battery.equals(getResources().getString(R.string.not_connected))) {
                        intent1 = new Intent("com.tyd.plugin.receiver.sendmsg");
                        intent1.putExtra("plugin_cmd", 3);
                        intent1.putExtra("plugin_content", "");
                        sendBroadcast(intent1);
                        device_battery.setVisibility(0);
                        device_battery.setText(R.string.getting_electricity);
                        getBLEBattery();
                        Log.i("hph", "getting_electricity444");
                    } else {
                        device_battery.setText(this.battery);
                    }
                    this.bleBondMap.remove(name);
                }
            }
            if (!this.bleBondMap.isEmpty()) {
                for (Entry entry : this.bleBondMap.entrySet()) {
                    String deviceName = (String) entry.getKey();
                    HashMap map2 = new HashMap();
                    map2.put("icon", Integer.valueOf(Util.getIconByDeviceName(deviceName, false)));
                    map2.put("name", entry.getKey());
                    map2.put("address", entry.getValue());
                    map2.put(DataBaseContants.MSG_STATE, Boolean.valueOf(false));
                    map2.put("connect", getResources().getString(R.string.not_connected));
                    bondedDevices.add(map2);
                }
            }
        }
        if (connectDevice == null && gattconnectDevice == null) {
            mConntedTv.setVisibility(8);
            this.device_layout.setVisibility(8);
        }
        mDeviceListAdapter = new DeviceListAdapter(this, bondedDevices);
        this.mDeviceList.setAdapter(mDeviceListAdapter);
        this.mDeviceList.setOnItemClickListener(this.listViewItemClick);
        mBondedTv.setVisibility(0);
        if (bondedDevices.size() == 0) {
            mBondedTv.setVisibility(8);
        }
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                finish();
                return;
            case R.id.edit:
                if (isEditMode) {
                    isEditMode = false;
                    mEdit.setText(R.string.bt_edit);
                    if (connectDevice != null) {
                        this.manager.processPlugList(this.connectProductName);
                        if (this.manager.getPlugBeans().size() > 0) {
                            this.details_layout.setVisibility(0);
                            this.remove_layout.setVisibility(8);
                        }
                    } else if (Util.getLatestDeviceType(this)) {
                        this.manager.processPlugList(Util.getProductName(Tools.keyString(this.bleBondMap, Util.getLatestConnectDeviceAddress(this_))));
                        this.details_layout.setVisibility(0);
                        this.remove_layout.setVisibility(8);
                    }
                } else {
                    if (bondedDevices.size() > 0 || connectDevice != null) {
                        isEditMode = true;
                        mEdit.setText(R.string.it_is_ok);
                        if (connectDevice != null) {
                            this.details_layout.setVisibility(8);
                            this.remove_layout.setVisibility(0);
                        }
                    } else if (Util.getLatestDeviceType(this_)) {
                        isEditMode = true;
                        mEdit.setText(R.string.it_is_ok);
                        this.details_layout.setVisibility(8);
                        this.remove_layout.setVisibility(0);
                    }
                    if (mDeviceListAdapter != null) {
                        mDeviceListAdapter.notifyDataSetChanged();
                    }
                }
                if (mDeviceListAdapter != null) {
                    mDeviceListAdapter.notifyDataSetChanged();
                    return;
                }
                return;
            case R.id.searching:
                if (BluetoothService.getInstance().isConnected() || (RunningApp.isBLESupport && BleManagerService.getInstance().GetBleConnectState())) {
                    Builder builder = new Builder(this);
                    builder.setTitle((int) R.string.search_tip);
                    builder.setMessage((int) R.string.search_message);
                    builder.setPositiveButton((int) R.string.know, new C13364());
                    builder.create().show();
                } else {
                    startActivity(new Intent(this_, BindBleDeviceActivity.class));
                }
                this.details_layout.setVisibility(0);
                this.remove_layout.setVisibility(8);
                return;
            case R.id.details_layout:
                if (!isEditMode) {
                    String nickname = ProductManager.getInstance().getProductCategory(this.connectProductName);
                    this.manager.processPlugList(this.connectProductName);
                    if (this.manager.getPlugBeans().size() > 0) {
                        Intent intents = new Intent(this_, BTPluginActivity.class);
                        intents.putExtra("nick_name", nickname);
                        intents.putExtra("remote_name", this.connectProductName);
                        intents.putExtra("enable_state", true);
                        this_.startActivity(intents);
                        return;
                    }
                    return;
                }
                return;
            case R.id.remove_layout:
                if (!isEditMode) {
                    return;
                }
                if (Util.isBleDevice(this.connectProductName) || this.connectProductName == "" || this.connectProductName == null) {
                    String address = Util.getLatestConnectDeviceAddress(this);
                    Tools.removeBleBindInfo(this, this.connectProductName, address);
                    if (address.equals(Util.getLatestConnectDeviceAddress(this))) {
                        Util.updateLatestConnectDeviceAddress(this, "");
                    }
                    Intent unBindIntent = new Intent(BleManagerService.ACTION_DISCONNECT_BINDED_DEVICE);
                    unBindIntent.putExtra("BINDED_DEVICE_ADDRESS", address);
                    sendBroadcast(unBindIntent);
                    mConntedTv.setVisibility(8);
                    this.device_layout.setVisibility(8);
                    isEditMode = false;
                    mEdit.setText(R.string.bt_edit);
                    Tools.setIsSendBindingDevice(true);
                    Log.i("hph111", "remove_layout connectProductName==null");
                    return;
                }
                Log.i("hph111", "connectDevice getName=" + connectDevice.getName());
                if (connectDevice.getName().equals("M2")) {
                    setDeviceUnboundState();
                    Log.i("hph111", "setDeviceUnboundState()");
                    return;
                }
                Util.removeBond(btAdapt.getRemoteDevice(connectDevice.getAddress()));
                connectDevice = null;
                mConntedTv.setVisibility(8);
                this.device_layout.setVisibility(8);
                if (connectDevice == null && bondedDevices.size() <= 0) {
                    isEditMode = false;
                    mEdit.setText(R.string.bt_edit);
                    return;
                }
                return;
            default:
                return;
        }
    }

    private void setDeviceUnboundState() {
        Builder builder = new Builder(this);
        builder.setTitle((int) R.string.alert_title);
        builder.setMessage(getResources().getString(R.string.open_pedometer_lock_screen));
        builder.setPositiveButton((int) R.string.ok, new C13395());
        builder.setNegativeButton((int) R.string.cancle, new C13406());
        builder.create().show();
    }

    private void removeDevice() {
        if (isEditMode) {
            if (Util.isBleDevice(this.connectProductName) || this.connectProductName == "" || this.connectProductName == null) {
                String address = Util.getLatestConnectDeviceAddress(this);
                Tools.removeBleBindInfo(this, this.connectProductName, address);
                if (address.equals(Util.getLatestConnectDeviceAddress(this))) {
                    Util.updateLatestConnectDeviceAddress(this, "");
                }
                Intent unBindIntent = new Intent(BleManagerService.ACTION_DISCONNECT_BINDED_DEVICE);
                unBindIntent.putExtra("BINDED_DEVICE_ADDRESS", address);
                sendBroadcast(unBindIntent);
                mConntedTv.setVisibility(8);
                this.device_layout.setVisibility(8);
                isEditMode = false;
                mEdit.setText(R.string.bt_edit);
                Log.i("hph111", "isEditMode=isBleDevice1111");
            } else {
                Util.removeBond(btAdapt.getRemoteDevice(connectDevice.getAddress()));
                connectDevice = null;
                mConntedTv.setVisibility(8);
                this.device_layout.setVisibility(8);
                if (connectDevice == null && bondedDevices.size() <= 0) {
                    isEditMode = false;
                    mEdit.setText(R.string.bt_edit);
                }
            }
            Log.i("hph111", "EquipManagerListActivity ACTION_REMOVE_DEVICE");
        }
    }
}
