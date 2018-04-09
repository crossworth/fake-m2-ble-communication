package com.zhuoyou.plugin.running;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.fithealth.running.R;
import com.mcube.lib.ped.PedometerService;
import com.umeng.socialize.common.SocializeConstants;
import com.zhuoyou.plugin.bluetooth.connection.BtProfileReceiver;
import com.zhuoyou.plugin.bluetooth.data.Util;
import com.zhuoyou.plugin.gps.ilistener.IStepListener;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class SedentaryRemindActivity extends Activity {
    private static final int REQUEST = 1;
    private BluetoothAdapter bluetoothAdapt;
    private List<BluetoothDevice> bondList;
    BluetoothDevice currentDevice;
    private RelativeLayout im_back;
    private SedentaryListAdapter mAdapter;
    private String[] mDeviceFilter = new String[]{"Unik 3", "Unik 2", "A7", "T-Band", "Rumor-1", "Rumor-2", "M2"};
    private ListView mListView;
    private int mNumDevs = 0;
    String preDeviceAddress;
    private TextView tv_title;

    class C14031 implements OnClickListener {
        C14031() {
        }

        public void onClick(View v) {
            SedentaryRemindActivity.this.finish();
        }
    }

    class C14042 implements OnItemClickListener {
        C14042() {
        }

        public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
            new Bundle().putSerializable(Tools.DEVICE_INFO, (Serializable) SedentaryRemindActivity.this.mAdapter.Devices.get(position));
        }
    }

    public class SedentaryListAdapter extends BaseAdapter {
        private ArrayList<SedentaryDeviceItem> Devices = new ArrayList();
        private LayoutInflater mInflator;

        public SedentaryListAdapter() {
            this.mInflator = SedentaryRemindActivity.this.getLayoutInflater();
        }

        public void addDevice(SedentaryDeviceItem device) {
            if (!this.Devices.contains(device)) {
                SedentaryRemindActivity.this.mNumDevs = SedentaryRemindActivity.this.mNumDevs + 1;
                this.Devices.add(device);
            }
        }

        public int getCount() {
            return this.Devices.size();
        }

        public Object getItem(int position) {
            return this.Devices.get(position);
        }

        public long getItemId(int position) {
            return (long) position;
        }

        public View getView(final int position, View convertView, ViewGroup parent) {
            ViewHolder viewholeder;
            if (convertView == null) {
                convertView = this.mInflator.inflate(R.layout.sedentary_remind_item, null);
                viewholeder = new ViewHolder();
                viewholeder.DeviceImg = (ImageView) convertView.findViewById(R.id.divice_img);
                viewholeder.DeviceNameTextView = (TextView) convertView.findViewById(R.id.sedentary_device_name);
                viewholeder.DeviceTimeSet = (TextView) convertView.findViewById(R.id.device_time_set);
                viewholeder.enable = (ImageView) convertView.findViewById(R.id.divice_enable_img);
                convertView.setTag(viewholeder);
            } else {
                viewholeder = (ViewHolder) convertView.getTag();
            }
            if (position == 0) {
                viewholeder.DeviceImg.setBackgroundResource(R.drawable.leo_connect);
            } else {
                viewholeder.DeviceImg.setBackgroundResource(Util.getProductIcon(((SedentaryDeviceItem) this.Devices.get(position)).getDeviceName(), true));
            }
            viewholeder.DeviceNameTextView.setText(((SedentaryDeviceItem) this.Devices.get(position)).getDeviceName());
            viewholeder.DeviceTimeSet.setText((((SedentaryDeviceItem) this.Devices.get(position)).getTimeLag() * 30) + "分钟/" + ((SedentaryDeviceItem) this.Devices.get(position)).getStartTime() + SocializeConstants.OP_DIVIDER_MINUS + ((SedentaryDeviceItem) this.Devices.get(position)).getEndTime());
            viewholeder.enable.setBackgroundResource(((SedentaryDeviceItem) this.Devices.get(position)).getState().booleanValue() ? R.drawable.warn_on : R.drawable.warn_off);
            viewholeder.enable.setOnClickListener(new OnClickListener() {
                public void onClick(View v) {
                    boolean item_state = ((SedentaryDeviceItem) SedentaryListAdapter.this.Devices.get(position)).getState().booleanValue();
                    ((SedentaryDeviceItem) SedentaryListAdapter.this.Devices.get(position)).setState(Boolean.valueOf(!item_state));
                    SedentaryRemindActivity.this.mAdapter.notifyDataSetChanged();
                    if (position != 0) {
                        return;
                    }
                    if (!Tools.getPhonePedState() && !item_state) {
                        SedentaryRemindActivity.this.startService(new Intent(SedentaryRemindActivity.this.getApplicationContext(), PedometerService.class));
                        Tools.setPhoneSedentaryState(true);
                        Toast.makeText(SedentaryRemindActivity.this, "open", 1).show();
                    } else if (!Tools.getPhonePedState() && item_state) {
                        SedentaryRemindActivity.this.stopService(new Intent(SedentaryRemindActivity.this.getApplicationContext(), PedometerService.class));
                        Tools.setPhoneSedentaryState(false);
                        Toast.makeText(SedentaryRemindActivity.this, "close", 1).show();
                    }
                }
            });
            return convertView;
        }
    }

    public class ViewHolder {
        ImageView DeviceImg;
        TextView DeviceNameTextView;
        TextView DeviceTimeSet;
        ImageView enable;
    }

    class StepObserver implements IStepListener {
        StepObserver() {
        }

        public void onStepCount(int stepCount) {
        }

        public void onStateChanged(int newState) {
        }

        public void onHadRunStep(int hadRunStep) {
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sedentary_remind_activity);
        this.tv_title = (TextView) findViewById(R.id.title);
        this.tv_title.setText(R.string.sedentary_remind);
        this.im_back = (RelativeLayout) findViewById(R.id.back);
        this.im_back.setOnClickListener(new C14031());
        this.mAdapter = new SedentaryListAdapter();
        initData();
        this.mListView = (ListView) findViewById(R.id.sedentary_device_list);
        this.mListView.setOnItemClickListener(new C14042());
        this.mListView.setAdapter(this.mAdapter);
    }

    private void initData() {
        this.mAdapter.addDevice(new SedentaryDeviceItem("Phone", "08:00", "22:00", 3, Boolean.valueOf(Tools.getPhoneSedentaryState())));
        this.bluetoothAdapt = BluetoothAdapter.getDefaultAdapter();
        this.preDeviceAddress = Util.getLatestConnectDeviceAddress(getApplicationContext());
        this.bondList = Util.getBondDevice();
        this.currentDevice = BtProfileReceiver.getRemoteDevice();
        if (this.currentDevice == null && this.bondList != null && this.bondList.size() > 0) {
            if (this.preDeviceAddress.equals("")) {
                this.currentDevice = (BluetoothDevice) this.bondList.get(0);
            } else {
                this.currentDevice = this.bluetoothAdapt.getRemoteDevice(this.preDeviceAddress);
            }
        }
        if (this.currentDevice != null) {
            addDevice(this.currentDevice);
            Toast.makeText(this, Util.getProductName(this.currentDevice.getName()), 1).show();
            return;
        }
        Toast.makeText(this, "null", 1).show();
    }

    public void addDevice(BluetoothDevice device) {
        boolean found = false;
        for (int i = 0; i < this.mDeviceFilter.length && !found && !TextUtils.isEmpty(device.getName()); i++) {
            found = this.currentDevice.getName().equals(this.mDeviceFilter[i]);
        }
        if (found) {
            SedentaryDeviceItem deviceItem = new SedentaryDeviceItem();
            deviceItem.setDeviceName(Util.getProductName(device.getName()));
            deviceItem.setEndTime("22:00");
            deviceItem.setStartTime("8:00");
            deviceItem.setState(Boolean.valueOf(false));
            deviceItem.setTimeLag(1);
            this.mAdapter.addDevice(deviceItem);
            this.mAdapter.notifyDataSetChanged();
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode != 1) {
        }
    }
}
