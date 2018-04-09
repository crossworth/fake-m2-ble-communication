package com.zhuoyou.plugin.sedentaryremind;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager.LayoutParams;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.fithealth.running.R;
import com.zhuoyou.plugin.add.TosGallery;
import com.zhuoyou.plugin.add.TosGallery.OnEndFlingListener;
import com.zhuoyou.plugin.ble.BleManagerService;
import com.zhuoyou.plugin.info.WheelTextAdapter;
import com.zhuoyou.plugin.running.Tools;
import com.zhuoyou.plugin.view.WheelView;
import java.text.DecimalFormat;

public class SedentaryTimeSetActivity extends Activity implements OnClickListener, OnEndFlingListener {
    private ImageView HindPeriod;
    private WheelView PeriodWheelView;
    private WheelTextAdapter endHourAdapter;
    private WheelTextAdapter endMinAdapter;
    private ImageView endTimeSedentaryImageView;
    private WheelView end_wv_hour;
    private WheelView end_wv_min;
    private DecimalFormat intFormat = new DecimalFormat("#00");
    private Boolean isOpenRemind = Boolean.valueOf(false);
    private ImageView isOpenRemindImg;
    private TextView mEndTimeTextView;
    private String[] mPeriodTimeArray = new String[]{"30分钟", "60分钟", "90分钟", "120分钟"};
    private TextView mPeriodTimeTextView;
    private SedentaryBean mSedentaryBean;
    private TextView mStartTimeTextView;
    private ImageView periodSedentaryImageView;
    private int selectPostionEH;
    private int selectPostionEM;
    private int selectPostionSH;
    private int selectPostionSM;
    private WheelTextAdapter startHourAdapter;
    private WheelTextAdapter startMinAdapter;
    private ImageView startTimeSedentaryImageView;
    private WheelView start_wv_hour;
    private WheelView start_wv_min;

    class C14271 implements OnClickListener {
        C14271() {
        }

        public void onClick(View v) {
            if (SedentaryTimeSetActivity.this.isOpenRemind.booleanValue()) {
                SedentaryTimeSetActivity.this.isOpenRemindImg.setBackgroundResource(R.drawable.alarm_button_openon);
                SedentaryTimeSetActivity.this.mSedentaryBean.setIsOpen(Boolean.valueOf(true));
                SedentaryTimeSetActivity.this.isOpenRemind = Boolean.valueOf(false);
            } else if (!SedentaryTimeSetActivity.this.isOpenRemind.booleanValue()) {
                SedentaryTimeSetActivity.this.isOpenRemindImg.setBackgroundResource(R.drawable.alarm_button_closeoff);
                SedentaryTimeSetActivity.this.mSedentaryBean.setIsOpen(Boolean.valueOf(false));
                SedentaryTimeSetActivity.this.isOpenRemind = Boolean.valueOf(true);
            }
        }
    }

    private class SedentaryPeriodTimeDialog {
        private Dialog dialog;
        private OnClickListener mOnClickListener = new C14281();

        class C14281 implements OnClickListener {
            C14281() {
            }

            public void onClick(View v) {
                v.getId();
                SedentaryTimeSetActivity.this.mPeriodTimeTextView.setText(((Button) v).getText());
                SedentaryPeriodTimeDialog.this.dialog.dismiss();
            }
        }

        public SedentaryPeriodTimeDialog() {
            this.dialog = new Dialog(SedentaryTimeSetActivity.this, R.style.photo_dialogstyle);
            this.dialog.setCanceledOnTouchOutside(true);
            Window window = this.dialog.getWindow();
            window.setGravity(80);
            Display display = SedentaryTimeSetActivity.this.getWindowManager().getDefaultDisplay();
            LayoutParams lp = window.getAttributes();
            lp.width = display.getWidth();
            window.setAttributes(lp);
            initView();
        }

        private void initView() {
        }

        public void showDialog() {
            this.dialog.show();
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sedentary_time_set_layout);
        this.mSedentaryBean = new SedentaryBean();
        initView();
        getData();
    }

    private void initView() {
        this.isOpenRemindImg = (ImageView) findViewById(R.id.is_enable);
        this.isOpenRemindImg.setOnClickListener(new C14271());
        this.mSedentaryBean.setIsOpen(Boolean.valueOf(false));
        this.mSedentaryBean.setPeriodTime(60);
    }

    private void getData() {
        boolean z = false;
        String sedentaryStr = Tools.getSedentaryRemind();
        Log.i("hph", "sedentaryStr=" + sedentaryStr);
        Log.i("hph", "setIsOpen=" + this.mSedentaryBean.getIsOpen());
        Log.i("hph", "setPeriodTime=" + this.mSedentaryBean.getPeriodTime());
        if (sedentaryStr.length() == 5) {
            SedentaryBean sedentaryBean = this.mSedentaryBean;
            if (sedentaryStr.substring(0, 1).equals("1")) {
                z = true;
            }
            sedentaryBean.setIsOpen(Boolean.valueOf(z));
            this.mSedentaryBean.setPeriodTime(Integer.valueOf(sedentaryStr.substring(2, 4)).intValue());
            if (this.mSedentaryBean.getIsOpen().booleanValue()) {
                this.isOpenRemindImg.setBackgroundResource(R.drawable.alarm_button_openon);
                Log.i("hph", "mSedentaryBean.isOpen==true");
            } else if (!this.mSedentaryBean.getIsOpen().booleanValue()) {
                this.isOpenRemindImg.setBackgroundResource(R.drawable.alarm_button_closeoff);
                Log.i("hph", "mSedentaryBean.isOpen==false");
            }
        }
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_m:
                finishActivity();
                return;
            default:
                return;
        }
    }

    public void onEndFling(TosGallery v) {
    }

    private String[] getData(int length) {
        String[] temp = new String[length];
        for (int i = 0; i < length; i++) {
            temp[i] = Integer.toString(i);
        }
        return temp;
    }

    private String[] getHourArray() {
        return getResources().getStringArray(R.array.hour);
    }

    private String[] getMinArray() {
        return getResources().getStringArray(R.array.minute);
    }

    public void onBackPressed() {
        super.onBackPressed();
        finishActivity();
    }

    private void finishActivity() {
        Log.i("hph", "finishActivity");
        Tools.saveSedentaryRemind(this.mSedentaryBean.saveSedentaryShareP());
        broadAlarmInfo(this.mSedentaryBean.toString());
        finish();
    }

    private void broadAlarmInfo(String info) {
        Intent bluetoothBroadIntent = new Intent(BleManagerService.ACTION_UPDATE_SEDENTARY_INFO);
        bluetoothBroadIntent.putExtra("sedentaryInfo", info);
        sendBroadcast(bluetoothBroadIntent);
        Log.i("hph", "sedentaryInfo=" + info);
    }
}
