package com.zhuoyou.plugin.add;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.zhuoyou.plugin.add.TosGallery.LayoutParams;
import com.zhuoyou.plugin.running.Tools;

public class MyWheelView extends BaseAdapter {
    private Context context;
    private String[] data;
    private int mHeight = 50;
    private int selectPos;

    public MyWheelView(String[] data, Context context) {
        this.data = data;
        this.context = context;
        this.mHeight = Tools.dip2px(context, (float) this.mHeight);
    }

    public void setSelectPos(int pos) {
        this.selectPos = pos;
    }

    public void setData(String[] data) {
        this.data = data;
    }

    public int getCount() {
        return this.data != null ? this.data.length : 0;
    }

    public Object getItem(int position) {
        return this.data[position];
    }

    public long getItemId(int position) {
        return (long) position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        TextView txtView = null;
        if (convertView == null) {
            convertView = new TextView(this.context);
            convertView.setLayoutParams(new LayoutParams(-1, this.mHeight));
            txtView = (TextView) convertView;
            txtView.setGravity(17);
        }
        if (txtView == null) {
            txtView = (TextView) convertView;
        }
        if (position == this.selectPos) {
            txtView.setTextSize(1, 28.0f);
            txtView.setTextColor(-13948117);
        } else if (position == this.selectPos - 1 || position == this.selectPos + 1) {
            txtView.setTextSize(1, 26.0f);
            txtView.setTextColor(-3750202);
        } else if (position == this.selectPos - 2 || position == this.selectPos + 2) {
            txtView.setTextSize(1, 24.0f);
            txtView.setTextColor(-3815995);
        } else {
            txtView.setTextSize(1, 22.0f);
            txtView.setTextColor(-1052689);
        }
        txtView.setText(this.data[position]);
        return convertView;
    }
}
