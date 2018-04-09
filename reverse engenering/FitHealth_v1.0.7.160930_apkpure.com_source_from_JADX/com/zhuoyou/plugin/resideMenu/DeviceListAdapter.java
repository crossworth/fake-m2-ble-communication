package com.zhuoyou.plugin.resideMenu;

import android.content.Context;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.fithealth.running.R;
import com.zhuoyou.plugin.bluetooth.attach.PluginManager;
import com.zhuoyou.plugin.bluetooth.data.Util;
import com.zhuoyou.plugin.running.Tools;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DeviceListAdapter extends BaseAdapter {
    private Context mContext;
    private List<Map<String, Object>> mDeviceList = new ArrayList();

    private class ViewCache {
        public TextView connect_state;
        public RelativeLayout details_lay;
        public TextView device_name;
        public ImageView device_type;
        public RelativeLayout remove_lay;

        private ViewCache() {
        }
    }

    public DeviceListAdapter(Context context, List<Map<String, Object>> list) {
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
            convertView = LayoutInflater.from(this.mContext).inflate(R.layout.device_list_item, null);
            holder = new ViewCache();
            holder.device_type = (ImageView) convertView.findViewById(R.id.device_type);
            holder.device_name = (TextView) convertView.findViewById(R.id.device_name);
            holder.connect_state = (TextView) convertView.findViewById(R.id.connect_state);
            holder.remove_lay = (RelativeLayout) convertView.findViewById(R.id.remove_lay);
            holder.details_lay = (RelativeLayout) convertView.findViewById(R.id.details_lay);
            convertView.setTag(holder);
        } else {
            holder = (ViewCache) convertView.getTag();
        }
        final Map<String, Object> item = (Map) this.mDeviceList.get(position);
        final int mPosition = position;
        holder.device_type.setImageResource(((Integer) item.get("icon")).intValue());
        holder.device_name.setText(item.get("name").toString());
        holder.connect_state.setText(item.get("connect").toString());
        PluginManager manager = PluginManager.getInstance();
        manager.processPlugList(Util.getProductName(item.get("name").toString()));
        if (EquipManagerListActivity.isEditMode) {
            holder.details_lay.setVisibility(8);
            holder.remove_lay.setVisibility(0);
            holder.remove_lay.setOnClickListener(new OnClickListener() {
                public void onClick(View v) {
                    Message msg = new Message();
                    msg.what = 2;
                    msg.arg1 = mPosition;
                    msg.obj = item;
                    EquipManagerListActivity.mHandler.sendMessage(msg);
                    Tools.setIsSendBindingDevice(true);
                    Log.i("hph111", "DeviceListAdapter REMOVE_DEVICE_ITEM");
                }
            });
        } else {
            if (manager.getPlugBeans().size() > 0) {
                holder.details_lay.setVisibility(0);
            }
            holder.remove_lay.setVisibility(8);
            holder.details_lay.setOnClickListener(new OnClickListener() {
                public void onClick(View v) {
                    Message msg = new Message();
                    msg.what = 1;
                    msg.arg1 = mPosition;
                    msg.obj = item;
                    EquipManagerListActivity.mHandler.sendMessage(msg);
                }
            });
        }
        return convertView;
    }
}
