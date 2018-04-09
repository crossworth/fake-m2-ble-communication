package com.zhuoyou.plugin.view;

import android.app.Dialog;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.fithealth.running.R;
import com.zhuoyou.plugin.bluetooth.data.Util;
import java.util.ArrayList;
import java.util.List;

public class SearchListDialog extends Dialog implements OnClickListener {
    public ArrayAdapter<String> adtDevices;
    private TextView bTSettings;
    private Context context;
    private LeaveMyDialogListener listener;
    private List<String> mDeviceNames;
    private List<BluetoothDevice> mList = new ArrayList();
    public ListView mListView = null;
    public RelativeLayout search_lay;

    class C14411 implements OnItemClickListener {
        C14411() {
        }

        public void onItemClick(AdapterView<?> adapterView, View arg1, int arg2, long arg3) {
            Util.bondDevice((BluetoothDevice) SearchListDialog.this.mList.get(arg2));
            SearchListDialog.this.dismiss();
        }
    }

    public interface LeaveMyDialogListener {
        void onClick(View view);
    }

    public List<BluetoothDevice> getmList() {
        return this.mList;
    }

    public void setmList(List<BluetoothDevice> mList) {
        this.mList = mList;
    }

    public SearchListDialog(Context context) {
        super(context);
        this.context = context;
    }

    public SearchListDialog(Context context, int theme) {
        super(context, theme);
        this.context = context;
    }

    public SearchListDialog(Context context, int theme, List<BluetoothDevice> list, LeaveMyDialogListener listener) {
        super(context, theme);
        this.context = context;
        this.mList = list;
        this.listener = listener;
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View root = View.inflate(this.context, R.layout.devices_dialog_layout, null);
        setContentView(root);
        this.search_lay = (RelativeLayout) root.findViewById(R.id.search_lay);
        this.bTSettings = (TextView) root.findViewById(R.id.bluetooth_settings);
        this.bTSettings.setOnClickListener(this);
        this.mListView = (ListView) root.findViewById(R.id.device_list);
        updateProductView(this.context, this.mList);
        this.mListView.setOnItemClickListener(new C14411());
    }

    public void updateProductView(Context con, List<BluetoothDevice> list) {
        this.mDeviceNames = new ArrayList();
        if (list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                StringBuilder sb = new StringBuilder();
                String name = ((BluetoothDevice) list.get(i)).getName();
                String address = ((BluetoothDevice) list.get(i)).getAddress();
                sb.append(name);
                sb.append("  ");
                sb.append(address);
                this.mDeviceNames.add(sb.toString());
            }
        }
        if (this.mListView != null) {
            this.adtDevices = new ArrayAdapter(con, R.layout.simple_list_item, this.mDeviceNames);
            this.mListView.setAdapter(this.adtDevices);
        }
    }

    public void onClick(View v) {
        this.listener.onClick(v);
    }

    public void dismiss() {
        super.dismiss();
    }
}
