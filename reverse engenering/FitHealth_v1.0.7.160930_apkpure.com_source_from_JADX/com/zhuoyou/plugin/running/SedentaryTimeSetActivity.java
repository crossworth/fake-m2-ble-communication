package com.zhuoyou.plugin.running;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.ViewCompat;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.fithealth.running.R;
import com.zhuoyou.plugin.add.TosGallery;
import com.zhuoyou.plugin.add.TosGallery.LayoutParams;
import com.zhuoyou.plugin.add.TosGallery.OnEndFlingListener;
import com.zhuoyou.plugin.view.WheelView;

public class SedentaryTimeSetActivity extends Activity implements OnClickListener, OnEndFlingListener {
    private WheelView EndWheelViewHour;
    private WheelView EndWheelViewMinute;
    private ImageView HindPeriod;
    private WheelView PeriodWheelView;
    private WheelView StartWheelViewHour;
    private WheelView StartWheelViewMinute;
    private ImageView im_back;
    private ImageView isEnableImageView;
    private boolean is_edit = false;
    private SedentaryDeviceItem mDevice;
    private TextView mEndTimeTextView;
    private String[] mPeriodTimeArray = new String[]{"30分钟", "60分钟", "90分钟", "120分钟"};
    private TextView mPeriodTimeTextView;
    private TextView mStartTimeTextView;
    private TextView tv_title;

    class C14061 implements OnClickListener {
        C14061() {
        }

        public void onClick(View v) {
            boolean z = true;
            SedentaryTimeSetActivity.this.is_edit = true;
            SedentaryDeviceItem access$100 = SedentaryTimeSetActivity.this.mDevice;
            if (SedentaryTimeSetActivity.this.mDevice.getState().booleanValue()) {
                z = false;
            }
            access$100.setState(Boolean.valueOf(z));
            SedentaryTimeSetActivity.this.isEnableImageView.setBackgroundResource(SedentaryTimeSetActivity.this.mDevice.getState().booleanValue() ? R.drawable.warn_on : R.drawable.warn_off);
        }
    }

    private class WheelTextAdapter extends BaseAdapter {
        String[] mData = new String[0];
        int mHeight = 50;

        public WheelTextAdapter(String[] String) {
            this.mData = String;
            this.mHeight = Tools.dip2px(SedentaryTimeSetActivity.this, (float) this.mHeight);
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

        public View getView(int position, View convertView, ViewGroup parent) {
            TextView txtView = null;
            if (convertView == null) {
                convertView = new TextView(SedentaryTimeSetActivity.this);
                convertView.setLayoutParams(new LayoutParams(-1, this.mHeight));
                txtView = (TextView) convertView;
                txtView.setTextSize(1, 25.0f);
                txtView.setTextColor(ViewCompat.MEASURED_STATE_MASK);
                txtView.setGravity(17);
            }
            if (txtView == null) {
                txtView = (TextView) convertView;
            }
            txtView.setText(this.mData[position]);
            return convertView;
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sedentary_time_set_layout);
        this.mDevice = (SedentaryDeviceItem) getIntent().getSerializableExtra(Tools.DEVICE_INFO);
        if (this.mDevice != null) {
            Toast.makeText(this, "name=" + this.mDevice.getDeviceName(), 1).show();
        }
        initView();
    }

    private void initView() {
        this.tv_title.setText(R.string.sedentary_remind);
        this.im_back.setOnClickListener(this);
        this.isEnableImageView = (ImageView) findViewById(R.id.is_enable);
        this.isEnableImageView.setOnClickListener(new C14061());
        this.mPeriodTimeTextView.setOnClickListener(this);
        this.isEnableImageView.setBackgroundResource(this.mDevice.getState().booleanValue() ? R.drawable.warn_on : R.drawable.warn_off);
        this.mPeriodTimeTextView.setText((this.mDevice.getTimeLag() * 30) + "分钟");
        this.mStartTimeTextView.setText(this.mDevice.getStartTime());
        this.mEndTimeTextView.setText(this.mDevice.getEndTime());
        this.PeriodWheelView.setAdapter(new WheelTextAdapter(this.mPeriodTimeArray));
        this.PeriodWheelView.setOnEndFlingListener(this);
        this.StartWheelViewHour.setAdapter(new WheelTextAdapter(getData(24)));
        this.StartWheelViewHour.setOnEndFlingListener(this);
        this.StartWheelViewMinute.setAdapter(new WheelTextAdapter(getData(60)));
        this.StartWheelViewMinute.setOnEndFlingListener(this);
        this.EndWheelViewHour.setAdapter(new WheelTextAdapter(getData(24)));
        this.EndWheelViewHour.setOnEndFlingListener(this);
        this.EndWheelViewMinute.setAdapter(new WheelTextAdapter(getData(60)));
        this.EndWheelViewMinute.setOnEndFlingListener(this);
    }

    public void onClick(View v) {
        v.getId();
    }

    public void onEndFling(TosGallery v) {
        this.is_edit = true;
    }

    private String[] getData(int length) {
        String[] temp = new String[length];
        for (int i = 0; i < length; i++) {
            temp[i] = Integer.toString(i);
        }
        return temp;
    }
}
