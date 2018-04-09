package com.zhuoyou.plugin.running;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.fithealth.running.R;
import java.util.List;

public class SleepPageListItemAdapter extends BaseAdapter {
    private LayoutInflater inflater;
    private Context mContext;
    private List<SleepItem> mTodayLists;

    class ViewCache {
        TextView mDSleepT;
        TextView mSleepT;
        TextView mTime;
        TextView mWSleepT;
        RelativeLayout sleepLay;

        ViewCache() {
        }
    }

    public SleepPageListItemAdapter(Context ctx, List<SleepItem> list) {
        this.mContext = ctx;
        this.mTodayLists = list;
    }

    public void UpdateDate(Context ctx, List<SleepItem> list) {
        this.mContext = ctx;
        this.mTodayLists = list;
    }

    public int getCount() {
        return this.mTodayLists.size();
    }

    public SleepItem getItem(int position) {
        return (SleepItem) this.mTodayLists.get(position);
    }

    public long getItemId(int position) {
        return (long) position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewCache holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(this.mContext).inflate(R.layout.listitem_sleep, parent, false);
            holder = new ViewCache();
            holder.mTime = (TextView) convertView.findViewById(R.id.time);
            holder.mSleepT = (TextView) convertView.findViewById(R.id.sleepTimeV);
            holder.mDSleepT = (TextView) convertView.findViewById(R.id.dSleepTimeV);
            holder.mWSleepT = (TextView) convertView.findViewById(R.id.sSleepTimeV);
            holder.sleepLay = (RelativeLayout) convertView.findViewById(R.id.sleep_lay);
            convertView.setTag(holder);
        } else {
            holder = (ViewCache) convertView.getTag();
        }
        holder.mTime.setText(((SleepItem) this.mTodayLists.get(position)).getmStartT() + " -" + ((SleepItem) this.mTodayLists.get(position)).getmEndT());
        holder.mSleepT.setText(Tools.getTimer(((SleepItem) this.mTodayLists.get(position)).getmSleepT()));
        holder.mDSleepT.setText(Tools.getTimer(((SleepItem) this.mTodayLists.get(position)).getmDSleepT()));
        holder.mWSleepT.setText(Tools.getTimer(((SleepItem) this.mTodayLists.get(position)).getmWSleepT()));
        if (position == 0) {
            if (this.mTodayLists.size() - 1 == position) {
                holder.sleepLay.setBackgroundResource(R.drawable.listitem_sleep_bg_1);
            } else {
                holder.sleepLay.setBackgroundResource(R.drawable.listitem_sleep_bg);
            }
        } else if (this.mTodayLists.size() - 1 == position) {
            holder.sleepLay.setBackgroundResource(R.drawable.listitem_sleep_bg_3);
        } else {
            holder.sleepLay.setBackgroundResource(R.drawable.listitem_sleep_bg_2);
        }
        return convertView;
    }
}
