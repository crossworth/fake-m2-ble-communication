package com.zhuoyou.plugin.running.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import com.droi.btlib.service.SubStep;
import com.droi.greendao.bean.SportBean;
import com.lemon.cx.histogra.MiHistogramSportData;
import com.lemon.cx.histogra.MiHistogramSportView;
import com.lemon.cx.histogra.OnHistogramClickListener;
import com.umeng.socialize.UMShareAPI;
import com.zhuoyou.plugin.running.C1680R;
import com.zhuoyou.plugin.running.base.BaseActivity;
import com.zhuoyou.plugin.running.database.SportHelper;
import com.zhuoyou.plugin.running.tools.GpsUtils;
import com.zhuoyou.plugin.running.tools.Tools;
import com.zhuoyou.plugin.running.view.FullShareDialog;
import com.zhuoyou.plugin.running.view.MyActionBar;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;

public class SportOneActivity extends BaseActivity {
    static final /* synthetic */ boolean $assertionsDisabled = (!SportOneActivity.class.desiredAssertionStatus());
    public static final String KEY_TARGET_DATA = "key_target_data";
    private SportBean bean;
    private MiHistogramSportView dataView;
    private Calendar mCalendar = Calendar.getInstance();
    private TextView tvCal;
    private TextView tvDistance;
    private TextView tvStep;
    private TextView tvSubStep;

    class C18161 implements OnClickListener {
        C18161() {
        }

        public void onClick(View v) {
            SportOneActivity.this.showShareDialog();
        }
    }

    class C18172 implements OnHistogramClickListener<MiHistogramSportData> {
        C18172() {
        }

        public void getData(MiHistogramSportData data) {
            SportOneActivity.this.tvSubStep.setText(SportOneActivity.this.getString(C1680R.string.sub_step_sport, new Object[]{SportOneActivity.this.getTime(data.getStartTime()), SportOneActivity.this.getTime(data.getEndTime()), Integer.valueOf(data.getStep())}));
            SportOneActivity.this.tvSubStep.setVisibility(0);
        }

        public void onTouchUp() {
            SportOneActivity.this.tvSubStep.setVisibility(8);
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) C1680R.layout.activity_sport_one);
        this.bean = SportHelper.getBeanByDate(Tools.getToday());
        initView();
        setUpView();
    }

    private void initView() {
        this.dataView = (MiHistogramSportView) findViewById(C1680R.id.sport_data);
        this.tvDistance = (TextView) findViewById(C1680R.id.tv_distance);
        this.tvStep = (TextView) findViewById(C1680R.id.tv_step);
        this.tvCal = (TextView) findViewById(C1680R.id.tv_cal);
        this.tvSubStep = (TextView) findViewById(C1680R.id.tv_substep);
        MyActionBar actionBar = (MyActionBar) findViewById(C1680R.id.action_bar);
        if ($assertionsDisabled || actionBar != null) {
            actionBar.setOnRightButtonClickListener(new C18161());
            return;
        }
        throw new AssertionError();
    }

    private void setUpView() {
        this.tvDistance.setText(GpsUtils.getDisStrNoUnit(this.bean.getDistance()));
        this.tvStep.setText(String.valueOf(this.bean.getStepPhone()));
        this.tvCal.setText(GpsUtils.getCalStrNoUnit(this.bean.getCal()));
        ArrayList<MiHistogramSportData> data = new ArrayList();
        Iterator it = SportHelper.formatSubStep(this.bean.getSportData()).iterator();
        while (it.hasNext()) {
            SubStep sub = (SubStep) it.next();
            data.add(new MiHistogramSportData(getMinute(sub.getStartTime().split(" +")[1]), getMinute(sub.getEndTime().split(" +")[1]), sub.getStep()));
        }
        this.dataView.setSportData(data);
        this.dataView.setSportListener(new C18172());
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case C1680R.id.btn_history:
                startActivity(new Intent(this, SportHistoryActivity.class));
                return;
            default:
                return;
        }
    }

    private String getTime(int minute) {
        Calendar today = Tools.parseDefDate(Tools.getToday());
        today.set(12, minute);
        return Tools.formatTime(today.getTimeInMillis(), "HH:mm");
    }

    private int getMinute(String str) {
        return (Integer.parseInt(str.substring(0, 2)) * 60) + Integer.parseInt(str.substring(2, 4));
    }

    private void showShareDialog() {
        FullShareDialog dialog = new FullShareDialog(this);
        View convertView = getLayoutInflater().inflate(C1680R.layout.layout_share_sport, null);
        TextView date = (TextView) convertView.findViewById(C1680R.id.tv_date);
        ((TextView) convertView.findViewById(C1680R.id.tv_step_count)).setText(String.valueOf(this.bean.getStepPhone()));
        date.setText(Tools.formatDate(this.mCalendar, "yyyy.MM.dd"));
        dialog.setContentView(convertView);
        dialog.setMessage(getString(this.bean.getComplete() == 1 ? C1680R.string.share_sport_complete : C1680R.string.share_sport_message, new Object[]{Integer.valueOf(this.bean.getStepPhone())}) + getString(C1680R.string.share_suffix));
        dialog.show();
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }
}
