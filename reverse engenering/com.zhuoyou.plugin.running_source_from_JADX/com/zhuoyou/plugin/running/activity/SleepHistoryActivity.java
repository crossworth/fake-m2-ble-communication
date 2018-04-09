package com.zhuoyou.plugin.running.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.lemon.cx.micolumnar.MiColumnarSleepData;
import com.lemon.cx.micolumnar.MiColumnarSleepView;
import com.lemon.cx.micolumnar.MiColumnarView.OnFlingListener;
import com.lemon.cx.micolumnar.OnColumnarChangeListener;
import com.zhuoyou.plugin.running.C1680R;
import com.zhuoyou.plugin.running.base.BaseActivity;
import com.zhuoyou.plugin.running.database.SleepHelper;
import com.zhuoyou.plugin.running.tools.GpsUtils;
import java.util.ArrayList;
import java.util.Calendar;

public class SleepHistoryActivity extends BaseActivity {
    private TextView btnDay;
    private Button btnDetail;
    private TextView btnMonth;
    private TextView btnWeek;
    private MiColumnarSleepView sleepView;
    private TextView tvDeepSleepTime;
    private TextView tvLightSleepTime;
    private TextView tvTotalSleepTime;

    class C18081 implements OnFlingListener {
        C18081() {
        }

        public void onFling(boolean isFling) {
            SleepHistoryActivity.this.btnDetail.setEnabled(!isFling);
        }
    }

    class C18092 implements OnColumnarChangeListener<MiColumnarSleepData> {
        C18092() {
        }

        public void onChange(MiColumnarSleepData data) {
            Log.i("zhuqichao", "onChange");
            SleepHistoryActivity.this.tvTotalSleepTime.setText(GpsUtils.getSleepTime(data.getdSleepTime() + data.getlSleepTime()));
            SleepHistoryActivity.this.tvDeepSleepTime.setText(GpsUtils.getSleepTime(data.getdSleepTime()));
            SleepHistoryActivity.this.tvLightSleepTime.setText(GpsUtils.getSleepTime(data.getlSleepTime()));
            SleepHistoryActivity.this.btnDetail.setEnabled(SleepHelper.getSleepList(data.getDate()).size() > 0);
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) C1680R.layout.activity_sleep_history);
        initView();
        long time = System.currentTimeMillis();
        initData();
        Log.i("zhuqichao", "initData用时：" + (System.currentTimeMillis() - time) + "毫秒");
    }

    private void initView() {
        this.sleepView = (MiColumnarSleepView) findViewById(C1680R.id.sleep_view);
        this.btnDay = (TextView) findViewById(C1680R.id.btn_day);
        this.btnWeek = (TextView) findViewById(C1680R.id.btn_week);
        this.btnMonth = (TextView) findViewById(C1680R.id.btn_month);
        this.tvTotalSleepTime = (TextView) findViewById(C1680R.id.tv_total_sleep_time);
        this.tvDeepSleepTime = (TextView) findViewById(C1680R.id.tv_deep_sleep_time);
        this.tvLightSleepTime = (TextView) findViewById(C1680R.id.tv_light_sleep_time);
        this.btnDetail = (Button) findViewById(C1680R.id.btn_detail);
        this.btnDay.setSelected(true);
    }

    private void initData() {
        ArrayList<MiColumnarSleepData> data = new ArrayList();
        for (int i = 0; i < 100; i++) {
            Calendar cal = Calendar.getInstance();
            cal.add(6, -i);
            MiColumnarSleepData mdata = SleepHelper.getSleepData(cal);
            if (mdata != null) {
                data.add(mdata);
            }
        }
        this.sleepView.setData(data);
        this.sleepView.setOnFlingListener(new C18081());
        this.sleepView.setListener(new C18092());
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case C1680R.id.btn_detail:
                Intent intent = new Intent(this, SleepOneActivity.class);
                intent.putExtra("key_target_data", ((MiColumnarSleepData) this.sleepView.getSelectedData()).getDate());
                startActivity(intent);
                return;
            case C1680R.id.btn_day:
                this.sleepView.setColumnarType(0);
                this.btnDay.setSelected(true);
                this.btnWeek.setSelected(false);
                this.btnMonth.setSelected(false);
                return;
            case C1680R.id.btn_week:
                this.sleepView.setColumnarType(1);
                this.btnDay.setSelected(false);
                this.btnWeek.setSelected(true);
                this.btnMonth.setSelected(false);
                this.btnDetail.setVisibility(8);
                return;
            case C1680R.id.btn_month:
                this.sleepView.setColumnarType(2);
                this.btnDay.setSelected(false);
                this.btnWeek.setSelected(false);
                this.btnMonth.setSelected(true);
                this.btnDetail.setVisibility(8);
                return;
            default:
                return;
        }
    }
}
