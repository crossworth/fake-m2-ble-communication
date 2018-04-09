package com.zhuoyou.plugin.info;

import android.content.Context;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import com.fithealth.running.R;
import com.zhuoyou.plugin.running.Tools;

public class HeadAdapter extends BaseAdapter {
    private int[] headIcon = Tools.headIcon;
    private boolean isDefaults = false;
    private Context mContext;
    private GridView mGridView;
    private ImageView myHead;
    private int selectIndex = -1;

    private class ViewCache {
        ImageView mIcon;
        ImageView mSelect;

        private ViewCache() {
        }
    }

    public HeadAdapter(Context context, GridView view) {
        this.mContext = context;
        this.mGridView = view;
    }

    public int getCount() {
        return this.headIcon.length;
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return (long) position;
    }

    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
        this.isDefaults = true;
    }

    public static int headBySelect(int selectIndex) {
        int length1 = Tools.headIcon1.length;
        int length2 = Tools.headIcon2.length;
        int length3 = Tools.headIcon3.length;
        int length4 = Tools.headIcon4.length;
        switch (selectIndex / 100) {
            case 0:
                if (selectIndex < length1) {
                    return selectIndex;
                }
                return -1;
            case 1:
                return (selectIndex % 100) + length1;
            case 2:
                return ((selectIndex % 100) + length1) + length2;
            case 3:
                return (((selectIndex % 100) + length1) + length2) + length3;
            case 4:
                return ((((selectIndex % 100) + length1) + length2) + length3) + length4;
            default:
                return -1;
        }
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewCache holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(this.mContext).inflate(R.layout.choose_head_item, parent, false);
            holder = new ViewCache();
            holder.mIcon = (ImageView) convertView.findViewById(R.id.head_icon);
            holder.mSelect = (ImageView) convertView.findViewById(R.id.select);
            convertView.setTag(holder);
        } else {
            holder = (ViewCache) convertView.getTag();
        }
        final int mPosition = position;
        holder.mIcon.setImageResource(this.headIcon[position]);
        if (headBySelect(Tools.getHead(this.mContext)) != position || this.isDefaults) {
            holder.mSelect.setVisibility(8);
        } else {
            holder.mSelect.setVisibility(0);
            this.myHead = holder.mSelect;
        }
        holder.mSelect.setTag(Integer.valueOf(position));
        holder.mIcon.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                ImageView preselectByTag;
                HeadAdapter.this.isDefaults = false;
                if (HeadAdapter.this.selectIndex == -1 || HeadAdapter.this.selectIndex == mPosition) {
                    preselectByTag = HeadAdapter.this.myHead;
                } else {
                    preselectByTag = (ImageView) HeadAdapter.this.mGridView.findViewWithTag(Integer.valueOf(HeadAdapter.this.selectIndex));
                }
                ImageView selectByTag = (ImageView) HeadAdapter.this.mGridView.findViewWithTag(Integer.valueOf(mPosition));
                if (preselectByTag != null) {
                    preselectByTag.setVisibility(8);
                }
                if (selectByTag != null) {
                    selectByTag.setVisibility(0);
                }
                HeadAdapter.this.selectIndex = mPosition;
                if (ChooseHeadActivity.mHandler != null) {
                    Message msg = new Message();
                    msg.what = 1;
                    msg.arg1 = HeadAdapter.this.selectIndex;
                    ChooseHeadActivity.mHandler.sendMessage(msg);
                }
            }
        });
        return convertView;
    }
}
