package com.zhuoyou.plugin.info;

import android.app.Activity;
import android.content.Context;
import android.support.v4.view.ViewCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.fithealth.running.R;
import com.zhuoyou.plugin.add.TosGallery.LayoutParams;
import com.zhuoyou.plugin.running.Tools;

public class WheelTextAdapter extends BaseAdapter {
    String UNIT;
    String[] mData;
    int mHeight;
    Context mcontext;
    int selection;
    int size;
    int target;

    public WheelTextAdapter(Context context, String[] String, int sizes) {
        this.mData = new String[0];
        this.mHeight = 38;
        this.selection = 0;
        this.UNIT = "";
        this.target = 1;
        this.mcontext = context;
        this.mData = String;
        this.mHeight = Tools.dip2px(this.mcontext, (float) this.mHeight);
        this.size = sizes;
    }

    public WheelTextAdapter(Context context, String[] String, String munit, int sizes) {
        this.mData = new String[0];
        this.mHeight = 38;
        this.selection = 0;
        this.UNIT = "";
        this.target = 1;
        this.mcontext = context;
        this.mData = String;
        this.mHeight = Tools.dip2px(this.mcontext, (float) this.mHeight);
        this.UNIT = munit;
        this.size = sizes;
    }

    public int getCount() {
        return this.mData != null ? this.mData.length : 0;
    }

    public Object getItem(int arg0) {
        return this.mData[arg0];
    }

    public long getItemId(int position) {
        return (long) position;
    }

    public void SetSelecttion(int position) {
        this.selection = position;
    }

    public void setTarget(int targets) {
        this.target = targets;
    }

    public void setData(String[] data) {
        this.mData = data;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        TextView txtView = null;
        if (convertView == null) {
            LayoutInflater mInflator = ((Activity) this.mcontext).getLayoutInflater();
            convertView = new TextView(this.mcontext);
            convertView.setLayoutParams(new LayoutParams(-1, this.mHeight));
            txtView = (TextView) convertView;
            txtView.setTextSize(1, 23.0f);
            txtView.setTextColor(ViewCompat.MEASURED_STATE_MASK);
            txtView.setGravity(17);
        }
        if (txtView == null) {
            txtView = (TextView) convertView;
        }
        String text = this.mData[position];
        if (text.length() > 5) {
            text = text.substring(0, 5);
        }
        txtView.setText(text);
        if (position == this.selection) {
            txtView.setTextSize(1, (float) this.size);
        } else if (position == this.selection - 1 || position == this.selection + 1) {
            txtView.setTextSize(1, (float) (this.size - 2));
            txtView.setTextColor(this.mcontext.getResources().getColor(R.color.list_view_item1));
        } else if (position == this.selection - 2 || position == this.selection + 2) {
            txtView.setTextSize(1, (float) (this.size - 4));
            txtView.setTextColor(this.mcontext.getResources().getColor(R.color.list_view_item2));
        } else if (position == this.selection - 3 || position == this.selection + 3) {
            txtView.setTextSize(1, (float) (this.size - 6));
            txtView.setTextColor(this.mcontext.getResources().getColor(R.color.list_view_item3));
        } else {
            txtView.setTextColor(-5263441);
        }
        return convertView;
    }

    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }
}
