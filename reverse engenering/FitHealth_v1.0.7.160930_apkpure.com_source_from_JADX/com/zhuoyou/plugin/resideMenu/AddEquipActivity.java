package com.zhuoyou.plugin.resideMenu;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.fithealth.running.R;
import com.zhuoyou.plugin.ble.BindBleDeviceActivity;
import com.zhuoyou.plugin.running.RunningApp;
import java.util.ArrayList;
import java.util.List;

public class AddEquipActivity extends Activity implements OnClickListener {
    private int[] deviceBle = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 1, 0, 0, 1, 1, 1, 1, 0, 1};
    private int[] deviceDescription = new int[]{R.string.device_1, R.string.device_2, R.string.device_2, R.string.device_2, R.string.device_2, R.string.device_2, R.string.device_2, R.string.device_3, R.string.device_4, R.string.device_2, R.string.device_1, R.string.device_3, R.string.device_1, R.string.device_3, R.string.device_1, R.string.device_2, R.string.device_3, R.string.device_3, R.string.device_1, R.string.device_1, R.string.device_3, R.string.device_2};
    private int[] deviceIcon = new int[]{R.drawable.p1_connect, R.drawable.p3_connect, R.drawable.mars5_connect, R.drawable.mars5_connect, R.drawable.p3_connect, R.drawable.p1_connect, R.drawable.p3_connect, R.drawable.t1_connect, R.drawable.a1_connect, R.drawable.m2_connect, R.drawable.luna1_connect, R.drawable.leo_connect, R.drawable.luna3_connect, R.drawable.leo_connect, R.drawable.luna3_connect, R.drawable.s3_connect, R.drawable.a7_connect, R.drawable.luna5_connect, R.drawable.rumor1_connect, R.drawable.luna1_connect, R.drawable.luna3_connect, R.drawable.mi2_connect};
    private String[] deviceName = new String[]{"EAMEY P1", "EAMEY P3", "Primo 5", "Primo 5C", "ABT-100", "Primo 1", "Primo 3", "TJ01", "Meegoo A10", "Megoo2", "Unik 1", "Unik 2", "Unik 3", "LEO", "LUNA3", "S3", "A7", "T-Band", "Rumor-1", "Rumor-2", "UNIK 3SE", "M2"};
    OnItemClickListener listViewItemClick = new C13251();
    private DeviceAdapter mAdapter = null;
    private List<DeviceItem> mDeviceItems = new ArrayList();
    private ListView mListView;

    class C13251 implements OnItemClickListener {
        C13251() {
        }

        public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
            DeviceItem item = (DeviceItem) AddEquipActivity.this.mDeviceItems.get(position);
            if (item.getBle() != 1) {
                AddEquipActivity.this.startActivity(new Intent("android.settings.BLUETOOTH_SETTINGS"));
            } else if (RunningApp.isBLESupport) {
                Intent intent = new Intent(AddEquipActivity.this, BindBleDeviceActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("BLE_DEVICE_NAME", item.getName());
                intent.putExtras(bundle);
                AddEquipActivity.this.startActivity(intent);
            } else {
                Toast.makeText(AddEquipActivity.this.getApplicationContext(), R.string.not_support_ble, 0).show();
            }
        }
    }

    private class DeviceAdapter extends BaseAdapter {
        private Context mContext;
        private List<DeviceItem> mDeviceList = new ArrayList();

        private class ViewCache {
            private TextView device_des;
            private ImageView device_icon;
            private TextView device_name;

            private ViewCache() {
            }
        }

        public DeviceAdapter(Context context, List<DeviceItem> list) {
            this.mContext = context;
            this.mDeviceList = list;
        }

        public int getCount() {
            return this.mDeviceList.size();
        }

        public Object getItem(int position) {
            return this.mDeviceList.get(position);
        }

        public long getItemId(int position) {
            return (long) position;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            ViewCache holder;
            if (convertView == null) {
                convertView = LayoutInflater.from(this.mContext).inflate(R.layout.add_device_item, null);
                holder = new ViewCache();
                holder.device_icon = (ImageView) convertView.findViewById(R.id.device_type);
                holder.device_name = (TextView) convertView.findViewById(R.id.device_name);
                holder.device_des = (TextView) convertView.findViewById(R.id.connect_state);
                convertView.setTag(holder);
            } else {
                holder = (ViewCache) convertView.getTag();
            }
            DeviceItem item = (DeviceItem) this.mDeviceList.get(position);
            holder.device_icon.setImageResource(item.getIcon());
            holder.device_name.setText(item.getName());
            holder.device_des.setText(item.getDes());
            return (item.getBle() == 1 && AddEquipActivity.this.getPackageManager().hasSystemFeature("android.hardware.bluetooth_le")) ? convertView : convertView;
        }
    }

    private class DeviceItem {
        private int ble;
        private String description;
        private int icon;
        private String name;

        private DeviceItem() {
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getName() {
            return this.name;
        }

        public void setIcon(int icon) {
            this.icon = icon;
        }

        public int getIcon() {
            return this.icon;
        }

        public void setDes(String description) {
            this.description = description;
        }

        public String getDes() {
            return this.description;
        }

        public void setBle(int ble) {
            this.ble = ble;
        }

        public int getBle() {
            return this.ble;
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_equip_layout);
        initView();
    }

    private void initView() {
        for (int i = 0; i < this.deviceName.length; i++) {
            DeviceItem item = new DeviceItem();
            item.setName(this.deviceName[i]);
            item.setIcon(this.deviceIcon[i]);
            item.setDes(getResources().getString(this.deviceDescription[i]));
            item.setBle(this.deviceBle[i]);
            this.mDeviceItems.add(item);
        }
        this.mListView = (ListView) findViewById(R.id.device_list);
        this.mAdapter = new DeviceAdapter(this, this.mDeviceItems);
        this.mListView.setAdapter(this.mAdapter);
        this.mListView.setOnItemClickListener(this.listViewItemClick);
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                finish();
                return;
            default:
                return;
        }
    }
}
