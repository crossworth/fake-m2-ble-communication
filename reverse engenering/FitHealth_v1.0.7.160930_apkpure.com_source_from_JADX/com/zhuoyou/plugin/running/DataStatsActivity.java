package com.zhuoyou.plugin.running;

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;
import com.fithealth.running.R;
import com.zhuoyou.plugin.view.BarChart;
import com.zhuoyou.plugin.view.HorScrollView;
import com.zhuoyou.plugin.view.HorScrollView.OnScrollListener;
import com.zhuoyou.plugin.view.LineNet;
import com.zhuoyou.plugin.view.PolylineChart;
import java.util.ArrayList;

public class DataStatsActivity extends Activity implements OnScrollListener {
    public static final int CALORIEVIEW = 1;
    public static final int DAILY = 11;
    public static final int MONTHLY = 33;
    public static final int SCROLLFINISHED = 1;
    public static final int STEPVIEW = 0;
    public static final int VIEWCREATED = 0;
    public static final int WEEKLY = 22;
    private ArrayList<Float> XList;
    private ArrayList<Float> YList;
    private View bg_bottom_line;
    private RelativeLayout bg_scrollview;
    private RelativeLayout bg_scrollview_circle;
    private View center_bar;
    private int chartViewHeight;
    private ImageView circle;
    private int currentStatsType = 0;
    private int dailyIndex;
    private final ArrayList<Double> dataList = new ArrayList();
    private RelativeLayout im_back;
    private Context mContext;
    private DataStatsCenter mDataStatsCenter;
    private final Handler mHandler = new C13501();
    private Typeface mNumberTP;
    private int monthlyIndex;
    private final OnTouchListener myDailyOnTouchListener = new C13533();
    private final OnCheckedChangeListener onCheckedChangeListener = new C13544();
    private RadioGroup radioGroup;
    private RelativeLayout relativelayout_point_window;
    private RelativeLayout relativelayout_stats_group2;
    private RelativeLayout relativelayout_stats_group3;
    private RelativeLayout scrollContentLayout;
    private HorScrollView scrollView;
    private TextView text_current_date;
    private TextView text_stats_group1;
    private TextView text_stats_group2;
    private TextView title_stats_group1;
    private TextView title_stats_group2;
    private TextView tv_title;
    private TextView unit_stats_group1;
    private int viewType;
    private int weeklyIndex;
    private int windowWidth;

    class C13501 extends Handler {
        C13501() {
        }

        public void handleMessage(Message paramMessage) {
            switch (paramMessage.what) {
                case 0:
                    if (DataStatsActivity.this.currentStatsType == 11) {
                        DataStatsActivity.this.scrollView.scrollTo(Tools.dip2px(DataStatsActivity.this.mContext, (float) ((DataStatsActivity.this.dataList.size() - 1) * 22)), 0);
                        return;
                    } else {
                        DataStatsActivity.this.scrollView.scrollTo(Tools.dip2px(DataStatsActivity.this.mContext, (float) ((DataStatsActivity.this.dataList.size() - 1) * 45)), 0);
                        return;
                    }
                case 1:
                    if (DataStatsActivity.this.currentStatsType == 11) {
                        DataStatsActivity.this.scrollView.scrollTo(Tools.dip2px(DataStatsActivity.this.mContext, (float) (DataStatsActivity.this.dailyIndex * 22)), 0);
                        return;
                    } else if (DataStatsActivity.this.currentStatsType == 22) {
                        DataStatsActivity.this.scrollView.smoothScrollTo(Tools.dip2px(DataStatsActivity.this.mContext, (float) (DataStatsActivity.this.weeklyIndex * 45)), 0);
                        return;
                    } else if (DataStatsActivity.this.currentStatsType == 33) {
                        DataStatsActivity.this.scrollView.smoothScrollTo(Tools.dip2px(DataStatsActivity.this.mContext, (float) (DataStatsActivity.this.monthlyIndex * 45)), 0);
                        return;
                    } else {
                        return;
                    }
                default:
                    return;
            }
        }
    }

    class C13512 implements OnClickListener {
        C13512() {
        }

        public void onClick(View v) {
            DataStatsActivity.this.finish();
        }
    }

    class C13533 implements OnTouchListener {
        Handler handler = new C13521();
        private int lastX = 0;
        private final int touchEventId = 4660;

        class C13521 extends Handler {
            C13521() {
            }

            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if (msg.what == 4660) {
                    if (C13533.this.lastX == DataStatsActivity.this.scrollView.getScrollX()) {
                        DataStatsActivity.this.mHandler.sendEmptyMessage(1);
                    }
                    C13533.this.lastX = DataStatsActivity.this.scrollView.getScrollX();
                    C13533.this.handler.sendMessageDelayed(C13533.this.handler.obtainMessage(4660, DataStatsActivity.this.scrollView), 5);
                }
            }
        }

        C13533() {
        }

        public boolean onTouch(View v, MotionEvent event) {
            this.lastX = 0;
            int i = DataStatsActivity.this.scrollView.getScrollX();
            switch (event.getAction()) {
                case 1:
                    if (this.lastX == i) {
                        this.handler.sendMessageDelayed(this.handler.obtainMessage(4660, v), 10);
                        break;
                    }
                    break;
            }
            return false;
        }
    }

    class C13544 implements OnCheckedChangeListener {
        C13544() {
        }

        public void onCheckedChanged(RadioGroup group, int checkedId) {
            DataStatsActivity.this.mHandler.sendEmptyMessageDelayed(0, 50);
            if (DataStatsActivity.this.viewType == 0) {
                switch (checkedId) {
                    case R.id.rb_stats_daily:
                        DataStatsActivity.this.currentStatsType = 11;
                        DataStatsActivity.this.bg_scrollview.setVisibility(0);
                        DataStatsActivity.this.bg_scrollview_circle.setVisibility(8);
                        DataStatsActivity.this.title_stats_group1.setText(R.string.total_steps);
                        DataStatsActivity.this.title_stats_group2.setText(R.string.total_distance);
                        DataStatsActivity.this.prepareDataSource();
                        DataStatsActivity.this.setBarView();
                        return;
                    case R.id.rb_stats_weekly:
                        DataStatsActivity.this.currentStatsType = 22;
                        DataStatsActivity.this.bg_scrollview.setVisibility(8);
                        DataStatsActivity.this.bg_scrollview_circle.setVisibility(0);
                        DataStatsActivity.this.title_stats_group1.setText(R.string.wtotal_steps);
                        DataStatsActivity.this.title_stats_group2.setText(R.string.wtotal_distance);
                        DataStatsActivity.this.prepareDataSource();
                        DataStatsActivity.this.setPolylineView();
                        DataStatsActivity.this.circle.setImageResource(R.drawable.circle_stats_blue);
                        DataStatsActivity.this.circle.setY(((Float) DataStatsActivity.this.YList.get(DataStatsActivity.this.YList.size() - 1)).floatValue());
                        return;
                    case R.id.rb_stats_monthly:
                        DataStatsActivity.this.currentStatsType = 33;
                        DataStatsActivity.this.bg_scrollview.setVisibility(8);
                        DataStatsActivity.this.bg_scrollview_circle.setVisibility(0);
                        DataStatsActivity.this.title_stats_group1.setText(R.string.mtotal_steps);
                        DataStatsActivity.this.title_stats_group2.setText(R.string.mtotal_distance);
                        DataStatsActivity.this.prepareDataSource();
                        DataStatsActivity.this.setPolylineView();
                        DataStatsActivity.this.circle.setImageResource(R.drawable.circle_stats_blue);
                        DataStatsActivity.this.circle.setY(((Float) DataStatsActivity.this.YList.get(DataStatsActivity.this.YList.size() - 1)).floatValue());
                        return;
                    default:
                        return;
                }
            } else if (DataStatsActivity.this.viewType == 1) {
                switch (checkedId) {
                    case R.id.rb_stats_daily:
                        DataStatsActivity.this.currentStatsType = 11;
                        DataStatsActivity.this.bg_scrollview.setVisibility(0);
                        DataStatsActivity.this.bg_scrollview_circle.setVisibility(8);
                        DataStatsActivity.this.title_stats_group1.setText(R.string.burn);
                        DataStatsActivity.this.prepareDataSource();
                        DataStatsActivity.this.setBarView();
                        return;
                    case R.id.rb_stats_weekly:
                        DataStatsActivity.this.currentStatsType = 22;
                        DataStatsActivity.this.bg_scrollview.setVisibility(8);
                        DataStatsActivity.this.bg_scrollview_circle.setVisibility(0);
                        DataStatsActivity.this.title_stats_group1.setText(R.string.wburn);
                        DataStatsActivity.this.prepareDataSource();
                        DataStatsActivity.this.setPolylineView();
                        DataStatsActivity.this.circle.setImageResource(R.drawable.circle_stats_orange);
                        DataStatsActivity.this.circle.setY(((Float) DataStatsActivity.this.YList.get(DataStatsActivity.this.YList.size() - 1)).floatValue());
                        return;
                    case R.id.rb_stats_monthly:
                        DataStatsActivity.this.currentStatsType = 33;
                        DataStatsActivity.this.bg_scrollview.setVisibility(8);
                        DataStatsActivity.this.bg_scrollview_circle.setVisibility(0);
                        DataStatsActivity.this.title_stats_group1.setText(R.string.mburn);
                        DataStatsActivity.this.prepareDataSource();
                        DataStatsActivity.this.setPolylineView();
                        DataStatsActivity.this.circle.setImageResource(R.drawable.circle_stats_orange);
                        DataStatsActivity.this.circle.setY(((Float) DataStatsActivity.this.YList.get(DataStatsActivity.this.YList.size() - 1)).floatValue());
                        return;
                    default:
                        return;
                }
            }
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.data_stats_layout);
        this.mContext = this;
        this.mNumberTP = RunningApp.getCustomNumberFont();
        this.tv_title = (TextView) findViewById(R.id.title);
        this.tv_title.setText(R.string.walk_primary_plan);
        this.im_back = (RelativeLayout) findViewById(R.id.back);
        this.im_back.setOnClickListener(new C13512());
        initView();
    }

    private void initView() {
        this.viewType = getIntent().getIntExtra("VIEWTYPE", 0);
        Rect localRect = new Rect();
        getWindow().getDecorView().getWindowVisibleDisplayFrame(localRect);
        this.windowWidth = localRect.width();
        this.chartViewHeight = Tools.dip2px(this, 198.0f);
        this.mDataStatsCenter = new DataStatsCenter(this.mContext);
        this.text_stats_group1 = (TextView) findViewById(R.id.text_stats_group1);
        this.unit_stats_group1 = (TextView) findViewById(R.id.unit_stats_group1);
        this.title_stats_group1 = (TextView) findViewById(R.id.title_stats_group1);
        this.relativelayout_stats_group2 = (RelativeLayout) findViewById(R.id.relativelayout_stats_group2);
        this.text_stats_group2 = (TextView) findViewById(R.id.text_stats_group2);
        this.title_stats_group2 = (TextView) findViewById(R.id.title_stats_group2);
        this.relativelayout_stats_group3 = (RelativeLayout) findViewById(R.id.relativelayout_stats_group3);
        this.scrollView = (HorScrollView) findViewById(R.id.linechat_scrollview);
        this.scrollContentLayout = (RelativeLayout) findViewById(R.id.relativelayout_scroll_content);
        this.bg_scrollview_circle = (RelativeLayout) findViewById(R.id.bg_scrollview_circle);
        this.bg_bottom_line = findViewById(R.id.bg_bottom_line);
        this.circle = (ImageView) findViewById(R.id.circle_stats);
        this.bg_scrollview = (RelativeLayout) findViewById(R.id.bg_scrollview);
        this.center_bar = findViewById(R.id.center_bar);
        this.relativelayout_point_window = (RelativeLayout) findViewById(R.id.relativelayout_point_window);
        this.text_current_date = (TextView) findViewById(R.id.text_current_date);
        this.radioGroup = (RadioGroup) findViewById(R.id.rg_group_stats);
        setViews();
    }

    private void setViews() {
        switch (this.viewType) {
            case 0:
                this.tv_title.setText(R.string.walking_statistics);
                setStepView();
                return;
            case 1:
                this.tv_title.setText(R.string.calories_statistics);
                setCalorieView();
                return;
            default:
                return;
        }
    }

    private void setStepView() {
        int count = this.mDataStatsCenter.getDailyStatsesCount();
        this.text_stats_group1.setText(String.valueOf(this.mDataStatsCenter.getDailyStatses(0).getSteps()));
        this.text_stats_group1.setTypeface(this.mNumberTP);
        this.text_stats_group2.setText(String.format("%.1f", new Object[]{Double.valueOf(((double) this.mDataStatsCenter.getDailyStatses(0).getMeter()) / 1000.0d)}));
        this.text_stats_group2.setTypeface(this.mNumberTP);
        this.relativelayout_stats_group3.setVisibility(8);
        this.text_current_date.setText(Tools.dateFormat(this.mDataStatsCenter.getDailyStatses(0).getDate(), "MM/dd"));
        this.text_current_date.setTextColor(-1);
        this.bg_bottom_line.setBackgroundColor(-11090191);
        this.center_bar.setBackgroundColor(1719329791);
        this.scrollView.setOnScrollListener(this);
        this.scrollView.setOnTouchListener(this.myDailyOnTouchListener);
        this.radioGroup.setOnCheckedChangeListener(this.onCheckedChangeListener);
        this.radioGroup.check(R.id.rb_stats_daily);
    }

    private void setBarView() {
        this.scrollContentLayout.removeAllViewsInLayout();
        BarChart barChart = new BarChart(this, this.dataList, this.viewType, this.chartViewHeight, this.windowWidth);
        RelativeLayout localRelativeLayout1 = new RelativeLayout(this);
        LayoutParams localLayoutParams1 = new LayoutParams(barChart.getCanvasWidth(), this.chartViewHeight);
        localLayoutParams1.addRule(12);
        localRelativeLayout1.setLayoutParams(localLayoutParams1);
        localRelativeLayout1.addView(barChart);
        this.scrollContentLayout.addView(localRelativeLayout1);
        LineNet localLineNet = new LineNet(this, this.chartViewHeight, this.windowWidth);
        RelativeLayout localRelativeLayout3 = new RelativeLayout(this);
        LayoutParams localLayoutParams2 = new LayoutParams(localLineNet.getCanvasWidth(), this.chartViewHeight);
        localLayoutParams2.addRule(12);
        localRelativeLayout3.setLayoutParams(localLayoutParams2);
        localRelativeLayout3.addView(localLineNet);
        this.bg_scrollview.addView(localRelativeLayout3);
    }

    private void setPolylineView() {
        this.scrollContentLayout.removeAllViewsInLayout();
        PolylineChart polylineChart = new PolylineChart(this, this.dataList, this.viewType, this.chartViewHeight, this.windowWidth);
        View relativeLayout = new RelativeLayout(this);
        ViewGroup.LayoutParams layoutParams = new LayoutParams(polylineChart.getCanvasWidth(), this.chartViewHeight);
        layoutParams.addRule(12);
        relativeLayout.setLayoutParams(layoutParams);
        relativeLayout.addView(polylineChart);
        this.scrollContentLayout.addView(relativeLayout);
        this.XList = new ArrayList();
        this.YList = new ArrayList();
        int i = Tools.dip2px(this, 118.0f);
        int j = Tools.dip2px(this, 158.0f);
        double d1 = ((Double) this.dataList.get(0)).doubleValue();
        double d2 = 0.0d;
        float f1 = 0.0f;
        for (int m = 0; m < this.dataList.size(); m++) {
            if (d2 < ((Double) this.dataList.get(m)).doubleValue()) {
                d2 = ((Double) this.dataList.get(m)).doubleValue();
            }
            if (d1 > ((Double) this.dataList.get(m)).doubleValue()) {
                d1 = ((Double) this.dataList.get(m)).doubleValue();
            }
        }
        if (d2 != d1) {
            double d3 = ((double) i) / (d2 - d1);
            for (int k = 0; k < this.dataList.size(); k++) {
                float f2 = f1 + ((float) Tools.dip2px(this, 45.0f));
                float f3 = (((float) j) - ((float) ((((Double) this.dataList.get(k)).doubleValue() - d1) * d3))) - ((float) Tools.dip2px(this, 12.0f));
                this.XList.add(Float.valueOf(f2));
                this.YList.add(Float.valueOf(f3));
                f1 = f2;
            }
            return;
        }
        this.XList.add(Float.valueOf(0.0f));
        this.YList.add(Float.valueOf((((float) j) - ((float) ((((Double) this.dataList.get(0)).doubleValue() - d1) * 0.0d))) - ((float) Tools.dip2px(this, 12.0f))));
    }

    private void setCalorieView() {
        this.relativelayout_stats_group2.setVisibility(8);
        this.relativelayout_stats_group3.setVisibility(0);
        this.text_stats_group1.setText(String.valueOf(this.mDataStatsCenter.getDailyStatses(0).getCalories()));
        this.text_stats_group1.setTypeface(this.mNumberTP);
        this.unit_stats_group1.setText(R.string.kcal);
        this.relativelayout_point_window.setBackgroundResource(R.drawable.bg_orange_window);
        this.text_current_date.setText(Tools.dateFormat(this.mDataStatsCenter.getDailyStatses(0).getDate(), "MM/dd"));
        this.text_current_date.setTextColor(-33280);
        this.bg_bottom_line.setBackgroundColor(-33280);
        this.center_bar.setBackgroundColor(1728036352);
        this.scrollView.setOnScrollListener(this);
        this.scrollView.setOnTouchListener(this.myDailyOnTouchListener);
        this.radioGroup.setOnCheckedChangeListener(this.onCheckedChangeListener);
        this.radioGroup.check(R.id.rb_stats_daily);
    }

    private void prepareDataSource() {
        if (this.mDataStatsCenter != null) {
            int i1;
            int i2;
            int i3;
            if (this.viewType == 0) {
                switch (this.currentStatsType) {
                    case 11:
                        this.dataList.clear();
                        for (i1 = 0; i1 < this.mDataStatsCenter.getDailyStatsesCount(); i1++) {
                            this.dataList.add(Double.valueOf((double) this.mDataStatsCenter.getDailyStatses(i1).getSteps()));
                        }
                        this.dailyIndex = this.dataList.size() - 1;
                        return;
                    case 22:
                        this.dataList.clear();
                        for (i2 = 0; i2 < this.mDataStatsCenter.getWeeklyStatsesCount(); i2++) {
                            this.dataList.add(Double.valueOf((double) this.mDataStatsCenter.getWeeklyStatses(i2).getSteps()));
                        }
                        this.weeklyIndex = this.dataList.size() - 1;
                        return;
                    case 33:
                        this.dataList.clear();
                        for (i3 = 0; i3 < this.mDataStatsCenter.getMonthlyStatsesCount(); i3++) {
                            this.dataList.add(Double.valueOf((double) this.mDataStatsCenter.getMonthlyStatses(i3).getSteps()));
                        }
                        this.monthlyIndex = this.dataList.size() - 1;
                        return;
                    default:
                        return;
                }
            } else if (this.viewType == 1) {
                switch (this.currentStatsType) {
                    case 11:
                        this.dataList.clear();
                        for (i1 = 0; i1 < this.mDataStatsCenter.getDailyStatsesCount(); i1++) {
                            this.dataList.add(Double.valueOf((double) this.mDataStatsCenter.getDailyStatses(i1).getCalories()));
                        }
                        this.dailyIndex = this.dataList.size() - 1;
                        return;
                    case 22:
                        this.dataList.clear();
                        for (i2 = 0; i2 < this.mDataStatsCenter.getWeeklyStatsesCount(); i2++) {
                            this.dataList.add(Double.valueOf((double) this.mDataStatsCenter.getWeeklyStatses(i2).getCalories()));
                        }
                        this.weeklyIndex = this.dataList.size() - 1;
                        return;
                    case 33:
                        this.dataList.clear();
                        for (i3 = 0; i3 < this.mDataStatsCenter.getMonthlyStatsesCount(); i3++) {
                            this.dataList.add(Double.valueOf((double) this.mDataStatsCenter.getMonthlyStatses(i3).getCalories()));
                        }
                        this.monthlyIndex = this.dataList.size() - 1;
                        return;
                    default:
                        return;
                }
            }
        }
    }

    public void onScroll(int scrollX) {
    }
}
