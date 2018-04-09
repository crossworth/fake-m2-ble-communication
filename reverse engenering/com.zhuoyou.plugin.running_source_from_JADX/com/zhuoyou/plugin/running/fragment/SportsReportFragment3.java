package com.zhuoyou.plugin.running.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.zhuoyou.plugin.running.C1680R;
import com.zhuoyou.plugin.running.activity.SportsReportActivity;
import com.zhuoyou.plugin.running.base.BaseFragment;

public class SportsReportFragment3 extends BaseFragment {
    private BroadcastReceiver mReceiver = new C19031();
    private TextView tvStepAchieve;
    private TextView tvStepAvg;
    private TextView tvStepTotal;

    class C19031 extends BroadcastReceiver {
        C19031() {
        }

        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            Object obj = -1;
            switch (action.hashCode()) {
                case -253812654:
                    if (action.equals(SportsReportActivity.ACTION_DATA_HAS_CHANGED)) {
                        obj = null;
                        break;
                    }
                    break;
            }
            switch (obj) {
                case null:
                    SportsReportFragment3.this.updateReportData();
                    return;
                default:
                    return;
            }
        }
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(C1680R.layout.fragment_sports_report3, container, false);
    }

    protected void initView(View view) {
        this.tvStepTotal = (TextView) view.findViewById(C1680R.id.tv_steps_total);
        this.tvStepAvg = (TextView) view.findViewById(C1680R.id.tv_steps_avg);
        this.tvStepAchieve = (TextView) view.findViewById(C1680R.id.tv_steps_achieve);
        this.tvStepAvg.setText(getString(C1680R.string.sport_report_step_avg, Integer.valueOf(0)));
        initReceiver();
    }

    protected void onVisible() {
        if (SportsReportActivity.reportBean != null) {
            updateReportData();
        }
    }

    private void updateReportData() {
        this.tvStepTotal.setText(String.valueOf(SportsReportActivity.reportBean.statistics.total.stepPhone));
        this.tvStepAvg.setText(getString(C1680R.string.sport_report_step_avg, Integer.valueOf(SportsReportActivity.reportBean.statistics.average.stepPhone)));
        this.tvStepAchieve.setText(String.valueOf(SportsReportActivity.reportBean.standard.achieve));
    }

    private void initReceiver() {
        IntentFilter filter = new IntentFilter();
        filter.addAction(SportsReportActivity.ACTION_DATA_HAS_CHANGED);
        getContext().registerReceiver(this.mReceiver, filter);
    }

    public void onDestroy() {
        super.onDestroy();
        getContext().unregisterReceiver(this.mReceiver);
    }
}
