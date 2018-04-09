package com.zhuoyou.plugin.running.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
import com.droi.btlib.service.BtDevice;
import com.droi.btlib.service.BtDevice.ConnectCallback;
import com.droi.btlib.service.BtManagerService;
import com.droi.btlib.service.BtManagerService.END_STATE;
import com.droi.btlib.service.BtManagerService.ScanCallback;
import com.droi.btlib.service.DeviceInfo;
import com.droi.sdk.analytics.DroiAnalytics;
import com.zhuoyou.plugin.running.C1680R;
import com.zhuoyou.plugin.running.base.BaseActivity;
import com.zhuoyou.plugin.running.tools.AnimUtils;
import com.zhuoyou.plugin.running.tools.Tools;
import com.zhuoyou.plugin.running.view.MyActionBar;
import com.zhuoyou.plugin.running.view.ProgressWheel;
import java.util.ArrayList;
import java.util.List;

public class DeviceAddActivity extends BaseActivity {
    static final /* synthetic */ boolean $assertionsDisabled = (!DeviceAddActivity.class.desiredAssertionStatus());
    private static final String eventReScan_conning = "event_rescan_conning";
    private static final String eventReScan_real = "event_rescan_real";
    private static final String eventReScan_scaning = "event_rescan_scaning";
    private static final String eventScan = "event_scan";
    private BaseAdapter adapter = new C17125();
    private ConnectCallback callback = new C17114();
    private List<DeviceInfo> dataList = new ArrayList();
    private boolean isConnecting;
    private ScanCallback mScanCallback = new C17103();
    private ProgressWheel pbScaning;
    private TextView tvCount;
    private ListView viewList;

    class C17081 implements OnItemClickListener {
        C17081() {
        }

        public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
            if (DeviceAddActivity.this.isConnecting) {
                Tools.makeToast((int) C1680R.string.bt_device_connecting);
                return;
            }
            BtManagerService.stopScan();
            ((DeviceInfo) DeviceAddActivity.this.dataList.get(position)).connectDevice(DeviceAddActivity.this.callback);
            ImageView state = (ImageView) view.findViewById(C1680R.id.device_state);
            state.setImageResource(C1680R.drawable.bt_connecting);
            AnimUtils.playAnimList(state.getDrawable());
            DeviceAddActivity.this.isConnecting = true;
        }
    }

    class C17092 implements OnClickListener {
        C17092() {
        }

        public void onClick(View v) {
            if (BtManagerService.isScanning()) {
                DroiAnalytics.onEvent(DeviceAddActivity.this, DeviceAddActivity.eventReScan_scaning);
                Tools.makeToast((int) C1680R.string.bt_device_scaning);
            } else if (DeviceAddActivity.this.isConnecting) {
                DroiAnalytics.onEvent(DeviceAddActivity.this, DeviceAddActivity.eventReScan_conning);
                Tools.makeToast((int) C1680R.string.bt_device_connecting);
            } else {
                DroiAnalytics.onEvent(DeviceAddActivity.this, DeviceAddActivity.eventReScan_real);
                DeviceAddActivity.this.pbScaning.setVisibility(0);
                BtManagerService.scanDevice(60000, DeviceAddActivity.this.mScanCallback);
            }
        }
    }

    class C17103 implements ScanCallback {
        C17103() {
        }

        public void scan(ArrayList<DeviceInfo> list) {
            DeviceAddActivity.this.dataList = list;
            Log.i("chenxiny", "size:" + DeviceAddActivity.this.dataList.size());
            DeviceAddActivity.this.tvCount.setText(DeviceAddActivity.this.getString(C1680R.string.bt_device_count, new Object[]{Integer.valueOf(DeviceAddActivity.this.dataList.size())}));
            DeviceAddActivity.this.adapter.notifyDataSetInvalidated();
            Log.i("chenxiny", "adapter size:" + DeviceAddActivity.this.adapter.getCount());
        }

        public void end(END_STATE state) {
            DeviceAddActivity.this.pbScaning.setVisibility(8);
            switch (state) {
                case BT_UNSUPPORT:
                    Tools.makeToast((int) C1680R.string.bt_bluetooth_isenable);
                    return;
                default:
                    return;
            }
        }
    }

    class C17114 implements ConnectCallback {
        C17114() {
        }

        public void success(BtDevice device) {
            DeviceAddActivity.this.setResult(-1);
            DeviceAddActivity.this.finish();
            DeviceAddActivity.this.isConnecting = false;
        }

        public void fail(int state) {
            DeviceAddActivity.this.adapter.notifyDataSetChanged();
            DeviceAddActivity.this.isConnecting = false;
            switch (state) {
                case 0:
                    Tools.makeToast((int) C1680R.string.bt_device_connect_timeout);
                    return;
                case 1:
                    Tools.makeToast((int) C1680R.string.bt_device_connecting);
                    return;
                default:
                    return;
            }
        }

        public void disconnect(BtDevice device) {
        }

        public void connecting(BtDevice device) {
        }

        public void battery(int battery) {
        }
    }

    class C17125 extends BaseAdapter {

        class ViewHolder {
            ImageView btState;
            ImageView imgLogo;
            TextView tvDetial;
            TextView tvName;

            ViewHolder() {
            }
        }

        C17125() {
        }

        public int getCount() {
            return DeviceAddActivity.this.dataList.size();
        }

        public Object getItem(int position) {
            return DeviceAddActivity.this.dataList.get(position);
        }

        public long getItemId(int position) {
            return (long) position;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                holder = new ViewHolder();
                convertView = LayoutInflater.from(DeviceAddActivity.this).inflate(C1680R.layout.item_device_list, parent, false);
                holder.imgLogo = (ImageView) convertView.findViewById(C1680R.id.device_logo);
                holder.tvName = (TextView) convertView.findViewById(C1680R.id.device_name);
                holder.tvDetial = (TextView) convertView.findViewById(C1680R.id.device_detail);
                holder.btState = (ImageView) convertView.findViewById(C1680R.id.device_state);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            DeviceInfo item = (DeviceInfo) DeviceAddActivity.this.dataList.get(position);
            holder.tvName.setText(item.getName());
            holder.tvDetial.setText(item.getMacAddress());
            holder.btState.setImageResource(DeviceAddActivity.this.getRssiImage(item.getRssi()));
            holder.tvDetial.setText(item.getMacAddress());
            return convertView;
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) C1680R.layout.activity_add_device);
        initView();
        BtManagerService.scanDevice(20000, this.mScanCallback);
        DroiAnalytics.onEvent(this, eventScan);
    }

    private void initView() {
        this.tvCount = (TextView) findViewById(C1680R.id.tv_device_count);
        this.tvCount.setText(getString(C1680R.string.bt_device_count, new Object[]{Integer.valueOf(this.dataList.size())}));
        this.viewList = (ListView) findViewById(C1680R.id.lv_devices);
        this.pbScaning = (ProgressWheel) findViewById(C1680R.id.pb_scanning);
        this.viewList.addFooterView(getLayoutInflater().inflate(C1680R.layout.layout_add_device_footer, null), null, false);
        this.viewList.setAdapter(this.adapter);
        this.viewList.setOnItemClickListener(new C17081());
        MyActionBar actionBar = (MyActionBar) findViewById(C1680R.id.actionbar);
        if ($assertionsDisabled || actionBar != null) {
            actionBar.setOnRightTitleClickListener(new C17092());
            return;
        }
        throw new AssertionError();
    }

    public void onClick(View v) {
        Intent intent = new Intent();
        switch (v.getId()) {
            case C1680R.id.layout_more_device:
                intent.setClass(this, DeviceMoreActivity.class);
                break;
            case C1680R.id.layout_help:
                intent.setClass(this, HelpActivity.class);
                break;
        }
        startActivity(intent);
    }

    public void onBackPressed() {
        if (this.isConnecting) {
            Tools.makeToast((int) C1680R.string.bt_device_connecting);
        } else {
            super.onBackPressed();
        }
    }

    protected void onDestroy() {
        super.onDestroy();
        BtManagerService.removeConnectCallback(this.callback);
        BtManagerService.stopScan();
    }

    private int getRssiImage(int rssi) {
        if (rssi > -70 && rssi < -55) {
            return C1680R.drawable.bt_rssi_batter;
        }
        if (rssi > -55) {
            return C1680R.drawable.bt_rssi_good;
        }
        return C1680R.drawable.bt_rssi_bad;
    }
}
