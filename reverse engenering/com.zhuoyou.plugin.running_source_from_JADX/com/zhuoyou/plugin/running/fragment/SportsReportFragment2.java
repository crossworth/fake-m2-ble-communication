package com.zhuoyou.plugin.running.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;
import com.tencent.open.yyb.TitleBar;
import com.zhuoyou.plugin.running.C1680R;
import com.zhuoyou.plugin.running.activity.SportsReportActivity;
import com.zhuoyou.plugin.running.base.BaseFragment;
import com.zhuoyou.plugin.running.bean.ReportBean.Range;
import java.util.ArrayList;
import java.util.List;
import lecho.lib.hellocharts.model.PieChartData;
import lecho.lib.hellocharts.model.SliceValue;
import lecho.lib.hellocharts.view.PieChartView;

public class SportsReportFragment2 extends BaseFragment {
    private BaseAdapter adapter = new C19012();
    private PieChartView chartView;
    private int[] colors = new int[]{-542721, -11944961, -6422535, -11075698, -1310822};
    private GridView gridView;
    private BroadcastReceiver mReceiver = new C19023();
    private List<SliceValue> slices = new ArrayList();

    class C19001 implements OnTouchListener {
        C19001() {
        }

        public boolean onTouch(View v, MotionEvent event) {
            return true;
        }
    }

    class C19012 extends BaseAdapter {

        class ViewHolder {
            View color;
            TextView label;

            ViewHolder() {
            }
        }

        C19012() {
        }

        public int getCount() {
            return SportsReportActivity.reportBean == null ? 0 : SportsReportActivity.reportBean.range.size();
        }

        public Range getItem(int position) {
            return (Range) SportsReportActivity.reportBean.range.get(position);
        }

        public long getItemId(int position) {
            return (long) position;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                holder = new ViewHolder();
                convertView = LayoutInflater.from(SportsReportFragment2.this.getContext()).inflate(C1680R.layout.item_report_range_grid, parent, false);
                holder.color = convertView.findViewById(C1680R.id.view_color);
                holder.label = (TextView) convertView.findViewById(C1680R.id.tv_label);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            Range item = (Range) SportsReportActivity.reportBean.range.get(position);
            holder.color.setBackgroundColor(item.color);
            holder.label.setText(item.label);
            return convertView;
        }
    }

    class C19023 extends BroadcastReceiver {
        C19023() {
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
                    SportsReportFragment2.this.updateReportData();
                    return;
                default:
                    return;
            }
        }
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(C1680R.layout.fragment_sports_report2, container, false);
    }

    protected void initView(View view) {
        this.chartView = (PieChartView) view.findViewById(C1680R.id.chart_view);
        this.gridView = (GridView) view.findViewById(C1680R.id.grid_view);
        this.gridView.setAdapter(this.adapter);
        this.gridView.setOnTouchListener(new C19001());
        initChartView();
        initReceiver();
    }

    private void initChartView() {
        this.slices.clear();
        for (int color : this.colors) {
            this.slices.add(new SliceValue(TitleBar.SHAREBTN_RIGHT_MARGIN, color));
        }
        PieChartData data = new PieChartData(this.slices);
        data.setSlicesSpacing(0);
        this.chartView.setPieChartData(data);
        this.chartView.setChartRotationEnabled(false);
        this.chartView.setValueTouchEnabled(false);
    }

    protected void onVisible() {
        if (SportsReportActivity.reportBean != null) {
            updateReportData();
        }
    }

    private void updateReportData() {
        generatePieData();
        this.adapter.notifyDataSetInvalidated();
    }

    private void generatePieData() {
        this.chartView.cancelDataAnimation();
        this.slices.clear();
        for (int i = 0; i < SportsReportActivity.reportBean.range.size(); i++) {
            Range range = (Range) SportsReportActivity.reportBean.range.get(i);
            SliceValue sliceValue = new SliceValue(TitleBar.SHAREBTN_RIGHT_MARGIN, range.color);
            sliceValue.setTarget((float) range.count);
            this.slices.add(sliceValue);
        }
        PieChartData data = new PieChartData(this.slices);
        data.setSlicesSpacing(0);
        this.chartView.setPieChartData(data);
        this.chartView.startDataAnimation(800);
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
