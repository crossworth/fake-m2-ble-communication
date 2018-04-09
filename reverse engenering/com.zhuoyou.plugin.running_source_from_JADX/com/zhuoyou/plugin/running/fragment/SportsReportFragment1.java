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
import com.zhuoyou.plugin.running.bean.ReportBean.Step;
import com.zhuoyou.plugin.running.tools.Tools;
import com.zhuoyou.plugin.running.view.StepsLineChartView;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.AxisValue;
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.model.SelectedValue;
import lecho.lib.hellocharts.model.SelectedValue.SelectedValueType;
import lecho.lib.hellocharts.model.Viewport;

public class SportsReportFragment1 extends BaseFragment {
    private static final int SHOW_COUNT = 7;
    private List<AxisValue> axisValues = new ArrayList();
    private StepsLineChartView chartView;
    private Line line = new Line().setCubic(false);
    private ArrayList<Line> lines = new ArrayList();
    private BroadcastReceiver mReceiver = new C18991();
    private List<PointValue> points = new ArrayList();
    private TextView tvSteps;
    private TextView tvStepsRank;

    class C18991 extends BroadcastReceiver {
        C18991() {
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
                    SportsReportFragment1.this.updateReportData();
                    return;
                default:
                    return;
            }
        }
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(C1680R.layout.fragment_sports_report1, container, false);
    }

    protected void initView(View view) {
        this.chartView = (StepsLineChartView) view.findViewById(C1680R.id.chart_view);
        this.tvSteps = (TextView) view.findViewById(C1680R.id.tv_steps);
        this.tvStepsRank = (TextView) view.findViewById(C1680R.id.tv_steps_rank);
        this.tvStepsRank.setText(getString(C1680R.string.sport_report_record_rank, "0%"));
        initChartView();
        initReceiver();
    }

    protected void onVisible() {
        if (SportsReportActivity.reportBean != null) {
            updateReportData();
        }
    }

    private void initChartView() {
        this.points.clear();
        this.axisValues.clear();
        Calendar calendar = Calendar.getInstance();
        calendar.add(6, -8);
        for (int i = 0; i < 7; i++) {
            this.points.add(new PointValue((float) i, 0.0f));
            calendar.add(6, 1);
            this.axisValues.add(new AxisValue((float) i).setLabel(Tools.formatDate(calendar, "MM/dd")));
        }
        this.line.setValues(this.points);
        this.line.setPointColor(-1);
        this.line.setColor(-1);
        this.line.setStrokeWidth(2);
        this.line.setPointRadius(4);
        this.line.setFilled(true);
        this.line.setFillColor(new int[]{-11223298, -14570754});
        this.lines.add(this.line);
        LineChartData lineData = new LineChartData(this.lines);
        lineData.setAxisXBottom(new Axis().setHasLines(false).setHasSeparationLine(true).setTextColor(-1).setTextSize(12).setValues(this.axisValues));
        this.chartView.setLineChartData(lineData);
        this.chartView.setViewportCalculationEnabled(false);
        this.chartView.setLine(10000);
        Viewport v = new Viewport(0.0f, 15000.0f, 6.0f, 0.0f);
        this.chartView.setMaximumViewport(v);
        this.chartView.setCurrentViewport(v);
        this.chartView.setZoomEnabled(false);
        this.chartView.setValueSelectionEnabled(true);
    }

    private void updateReportData() {
        generateLineData();
        this.tvSteps.setText(String.valueOf(SportsReportActivity.reportBean.record.data.stepPhone));
        this.tvStepsRank.setText(getString(C1680R.string.sport_report_record_rank, record.percent + "%"));
    }

    private void generateLineData() {
        this.chartView.cancelDataAnimation();
        formatLineData();
        SelectedValue value = this.chartView.getSelectedValue();
        if (!value.isSet()) {
            value.set(0, 6, SelectedValueType.LINE);
        }
        this.chartView.selectValue(value);
        this.chartView.startDataAnimation(800);
    }

    private void formatLineData() {
        int max = 0;
        for (int i = 0; i < 7; i++) {
            PointValue value = (PointValue) this.points.get(i);
            Step step = getStepByData(String.valueOf(((AxisValue) this.axisValues.get(i)).getLabelAsChars()), SportsReportActivity.reportBean.steps);
            if (step != null) {
                value.setTarget(value.getX(), (float) step.step);
                if (step.step > max) {
                    max = step.step;
                }
            }
        }
        if (max < 10000) {
            max = 10000;
            this.chartView.setLine(8000);
        } else {
            this.chartView.setLine(10000);
        }
        Viewport v = new Viewport(0.0f, (float) max, 6.0f, 0.0f);
        this.chartView.setMaximumViewport(v);
        this.chartView.setCurrentViewport(v);
    }

    private Step getStepByData(String data, List<Step> list) {
        for (Step item : list) {
            if (Tools.formatDate(item.date, Tools.DEFAULT_FORMAT_TIME, "MM/dd").equals(data)) {
                return item;
            }
        }
        return null;
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
