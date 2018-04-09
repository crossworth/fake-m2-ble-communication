package com.zhuoyou.plugin.running.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import com.droi.greendao.bean.SleepBean;
import com.lemon.cx.histogra.MiHistogramSleepData;
import com.lemon.cx.histogra.MiHistogramSleepView;
import com.lemon.cx.histogra.OnHistogramClickListener;
import com.umeng.socialize.UMShareAPI;
import com.zhuoyou.plugin.running.C1680R;
import com.zhuoyou.plugin.running.base.BaseActivity;
import com.zhuoyou.plugin.running.database.SleepHelper;
import com.zhuoyou.plugin.running.tools.GpsUtils;
import com.zhuoyou.plugin.running.tools.Tools;
import com.zhuoyou.plugin.running.view.FullShareDialog;
import com.zhuoyou.plugin.running.view.MyActionBar;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class SleepOneActivity extends BaseActivity {
    static final /* synthetic */ boolean $assertionsDisabled = (!SleepOneActivity.class.desiredAssertionStatus());
    public static final String KEY_TARGET_DATA = "key_target_data";
    private Calendar mCalendar = Calendar.getInstance();
    private MiHistogramSleepView mSleepView;
    private TextView tvDeepSleepTime;
    private TextView tvEnd;
    private TextView tvLightSleepTime;
    private TextView tvSleepTime;
    private TextView tvStart;
    private TextView tvSubsleep;
    private TextView tvTotalSleepTime;
    private TextView tvWakeupTime;

    class C18101 implements OnClickListener {
        C18101() {
        }

        public void onClick(View v) {
            SleepOneActivity.this.showShareDialog();
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) C1680R.layout.activity_sleep_one);
        initView();
        initData();
    }

    private void initView() {
        this.mSleepView = (MiHistogramSleepView) findViewById(C1680R.id.sleep_view);
        this.tvTotalSleepTime = (TextView) findViewById(C1680R.id.tv_total_sleep_time);
        this.tvDeepSleepTime = (TextView) findViewById(C1680R.id.tv_deep_sleep_time);
        this.tvLightSleepTime = (TextView) findViewById(C1680R.id.tv_light_sleep_time);
        this.tvSleepTime = (TextView) findViewById(C1680R.id.tv_sleep_time);
        this.tvWakeupTime = (TextView) findViewById(C1680R.id.tv_wakeup_time);
        this.tvStart = (TextView) findViewById(C1680R.id.tv_start);
        this.tvEnd = (TextView) findViewById(C1680R.id.tv_end);
        this.tvSubsleep = (TextView) findViewById(C1680R.id.tv_subsleep);
        MyActionBar actionBar = (MyActionBar) findViewById(C1680R.id.action_bar);
        if ($assertionsDisabled || actionBar != null) {
            actionBar.setOnRightButtonClickListener(new C18101());
            return;
        }
        throw new AssertionError();
    }

    private void initData() {
        final List src = SleepHelper.getTodaySleepList();
        if (src.size() > 0) {
            ArrayList<MiHistogramSleepData> list = SleepHelper.getSleepData(src);
            this.mSleepView.setSleepData(list);
            String timeFormat = "HH:mm";
            String start = Tools.formatDate(((SleepBean) src.get(0)).getStartTime(), Tools.DEFAULT_FORMAT_TIME, timeFormat);
            String end = Tools.formatDate(((SleepBean) src.get(src.size() - 1)).getEndTime(), Tools.DEFAULT_FORMAT_TIME, timeFormat);
            this.tvSleepTime.setText(start);
            this.tvWakeupTime.setText(end);
            this.tvStart.setText(start);
            this.tvEnd.setText(end);
            int[] time = SleepHelper.getDeepTime(list);
            this.tvTotalSleepTime.setText(GpsUtils.getSleepTime(time[0] + time[1]));
            this.tvDeepSleepTime.setText(GpsUtils.getSleepTime(time[0]));
            this.tvLightSleepTime.setText(GpsUtils.getSleepTime(time[1]));
            this.mSleepView.setSleepListener(new OnHistogramClickListener<MiHistogramSleepData>() {
                public void getData(MiHistogramSleepData data) {
                    String start = ((SleepBean) src.get(0)).getStartTime();
                    SleepOneActivity.this.tvSubsleep.setText(data.isDeep() ? C1680R.string.sleep_type_deep : C1680R.string.sleep_type_light);
                    SleepOneActivity.this.tvSubsleep.append(SleepOneActivity.this.getTime(start, data.getStartTime()) + "~" + SleepOneActivity.this.getTime(start, data.getEndTime()));
                    SleepOneActivity.this.tvSubsleep.setVisibility(0);
                }

                public void onTouchUp() {
                    SleepOneActivity.this.tvSubsleep.setVisibility(8);
                }
            });
        }
    }

    private String getTime(String start, int minute) {
        Calendar today = Tools.parseDefDate(start);
        today.add(12, minute);
        return Tools.formatTime(today.getTimeInMillis(), "HH:mm");
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case C1680R.id.btn_history:
                startActivity(new Intent(this, SleepHistoryActivity.class));
                return;
            default:
                return;
        }
    }

    private void showShareDialog() {
        FullShareDialog dialog = new FullShareDialog(this);
        View convertView = getLayoutInflater().inflate(C1680R.layout.layout_share_sleep, null);
        TextView hour = (TextView) convertView.findViewById(C1680R.id.tv_sleep_hour);
        TextView min = (TextView) convertView.findViewById(C1680R.id.tv_sleep_min);
        TextView date = (TextView) convertView.findViewById(C1680R.id.tv_date);
        String[] time = this.tvTotalSleepTime.getText().toString().split(":");
        hour.setText(time[0]);
        min.setText(time[1]);
        date.setText(Tools.formatDate(this.mCalendar, "yyyy.MM.dd"));
        dialog.setContentView(convertView);
        dialog.setMessage(getString(C1680R.string.share_sleep_message, new Object[]{time[0], time[1]}) + getString(C1680R.string.share_suffix));
        dialog.show();
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }
}
