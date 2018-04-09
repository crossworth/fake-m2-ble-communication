package com.zhuoyou.plugin.running.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.droi.greendao.bean.SportBean;
import com.lemon.cx.micolumnar.MiColumnarSportData;
import com.lemon.cx.micolumnar.MiColumnarSportView;
import com.lemon.cx.micolumnar.MiColumnarView.OnFlingListener;
import com.lemon.cx.micolumnar.OnColumnarChangeListener;
import com.zhuoyou.plugin.running.C1680R;
import com.zhuoyou.plugin.running.base.BaseActivity;
import com.zhuoyou.plugin.running.database.SportHelper;
import com.zhuoyou.plugin.running.tools.GpsUtils;
import com.zhuoyou.plugin.running.tools.Tools;
import java.util.ArrayList;

public class SportHistoryActivity extends BaseActivity {
    private TextView btnDay;
    private Button btnDetail;
    private TextView btnMonth;
    private TextView btnWeek;
    private MiColumnarSportView dataView;
    private TextView tvCal;
    private TextView tvDistance;
    private TextView tvStep;

    class C18141 implements OnFlingListener {
        C18141() {
        }

        public void onFling(boolean isFling) {
            SportHistoryActivity.this.btnDetail.setEnabled(!isFling);
        }
    }

    class C18152 implements OnColumnarChangeListener<MiColumnarSportData> {
        C18152() {
        }

        public void onChange(MiColumnarSportData data) {
            SportHistoryActivity.this.tvStep.setText(String.valueOf(data.getStep()));
            SportHistoryActivity.this.tvDistance.setText(GpsUtils.getDisStrNoUnit(data.getMeter()));
            SportHistoryActivity.this.tvCal.setText(GpsUtils.getCalStrNoUnit(data.getCalories()));
            SportHistoryActivity.this.btnDetail.setEnabled(!TextUtils.isEmpty(SportHelper.getBeanByDate(Tools.formatDefDate(data.getDate())).getSportData()));
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) C1680R.layout.activity_sport_history);
        initView();
        long time = System.currentTimeMillis();
        initData();
        Log.i("zhuqichao", "initData用时：" + (System.currentTimeMillis() - time) + "毫秒");
    }

    private void initView() {
        this.dataView = (MiColumnarSportView) findViewById(C1680R.id.sport_data_view);
        this.tvDistance = (TextView) findViewById(C1680R.id.tv_distance);
        this.tvStep = (TextView) findViewById(C1680R.id.tv_step);
        this.tvCal = (TextView) findViewById(C1680R.id.tv_cal);
        this.btnDay = (TextView) findViewById(C1680R.id.btn_day);
        this.btnWeek = (TextView) findViewById(C1680R.id.btn_week);
        this.btnMonth = (TextView) findViewById(C1680R.id.btn_month);
        this.btnDetail = (Button) findViewById(C1680R.id.btn_detail);
        this.btnDay.setSelected(true);
    }

    private void initData() {
        ArrayList<MiColumnarSportData> data = new ArrayList();
        for (SportBean bean : SportHelper.getBeanDao().loadAll()) {
            data.add(new MiColumnarSportData(Tools.parseDefDate(bean.getDate()), bean.getStepPhone(), bean.getDistance(), bean.getCal()));
        }
        this.dataView.setData(data);
        this.dataView.setOnFlingListener(new C18141());
        this.dataView.setListener(new C18152());
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case C1680R.id.btn_detail:
                Intent intent = new Intent(this, SportOneActivity.class);
                intent.putExtra("key_target_data", ((MiColumnarSportData) this.dataView.getSelectedData()).getDate());
                startActivity(intent);
                return;
            case C1680R.id.btn_day:
                this.dataView.setColumnarType(0);
                this.btnDay.setSelected(true);
                this.btnWeek.setSelected(false);
                this.btnMonth.setSelected(false);
                return;
            case C1680R.id.btn_week:
                this.dataView.setColumnarType(1);
                this.btnDay.setSelected(false);
                this.btnWeek.setSelected(true);
                this.btnMonth.setSelected(false);
                this.btnDetail.setVisibility(8);
                return;
            case C1680R.id.btn_month:
                this.dataView.setColumnarType(2);
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
