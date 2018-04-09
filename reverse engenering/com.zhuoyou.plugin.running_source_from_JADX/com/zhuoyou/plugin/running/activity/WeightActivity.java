package com.zhuoyou.plugin.running.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
import com.droi.greendao.bean.WeightBean;
import com.droi.library.pickerviews.picker.FloatPickerDialog;
import com.droi.library.pickerviews.picker.FloatPickerView;
import com.droi.sdk.DroiCallback;
import com.droi.sdk.DroiError;
import com.droi.sdk.core.DroiUser;
import com.umeng.socialize.UMShareAPI;
import com.zhuoyou.plugin.running.C1680R;
import com.zhuoyou.plugin.running.baas.BaasHelper;
import com.zhuoyou.plugin.running.baas.User;
import com.zhuoyou.plugin.running.base.BaseActivity;
import com.zhuoyou.plugin.running.bean.EventGetWeight;
import com.zhuoyou.plugin.running.database.WeightHelper;
import com.zhuoyou.plugin.running.tools.AnimUtils;
import com.zhuoyou.plugin.running.tools.SystemBarConfig;
import com.zhuoyou.plugin.running.tools.Tools;
import com.zhuoyou.plugin.running.view.FullShareDialog;
import com.zhuoyou.plugin.running.view.HintPagerDialog;
import com.zhuoyou.plugin.running.view.MyActionBar;
import com.zhuoyou.plugin.running.view.MyAlertDialog;
import com.zhuoyou.plugin.running.view.WeightLineChartView;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import lecho.lib.hellocharts.listener.LineChartOnValueSelectListener;
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.model.SelectedValue;
import lecho.lib.hellocharts.model.SelectedValue.SelectedValueType;
import lecho.lib.hellocharts.model.Viewport;
import org.greenrobot.eventbus.Subscribe;

public class WeightActivity extends BaseActivity {
    static final /* synthetic */ boolean $assertionsDisabled = (!WeightActivity.class.desiredAssertionStatus());
    private static final float BMI1 = 18.5f;
    private static final float BMI2 = 24.0f;
    private static final float BMI3 = 28.0f;
    private PagerAdapter adapter = new C18526();
    private WeightBean bean;
    private WeightLineChartView chartTop;
    private DecimalFormat decimalFormat = new DecimalFormat("0.0");
    private ImageView imgChange;
    private Line line;
    private ArrayList<Line> lines;
    private float max;
    private float min;
    private List<PointValue> points = new ArrayList();
    private int position;
    private int standardWeight;
    private TextView tvBmi;
    private TextView tvDate;
    private TextView tvWeight;
    private TextView tvWeightCompare;
    private TextView tvWeightState;
    private TextView tvWeightStateOverweight;
    private TextView tvWeightStateSlim;
    private TextView tvWeightStateStandard;
    private User user = ((User) DroiUser.getCurrentUser(User.class));
    private List<WeightBean> weights;

    class C18461 implements LineChartOnValueSelectListener {
        C18461() {
        }

        public void onValueSelected(int lineIndex, int pointIndex, PointValue value) {
            WeightActivity.this.bean = (WeightBean) WeightActivity.this.weights.get(pointIndex);
            WeightActivity.this.position = pointIndex;
            WeightActivity.this.updateViewData();
        }

        public void onValueDeselected() {
        }
    }

    class C18472 implements OnClickListener {
        C18472() {
        }

        public void onClick(View v) {
            WeightActivity.this.showShareDialog();
        }
    }

    class C18493 implements MyAlertDialog.OnClickListener {

        class C18481 implements DroiCallback<Boolean> {
            C18481() {
            }

            public void result(Boolean aBoolean, DroiError droiError) {
                if (droiError.isOk()) {
                    WeightActivity.this.deleteLocal();
                } else {
                    Tools.makeToast(Tools.getDroiError(droiError));
                }
            }
        }

        C18493() {
        }

        public void onClick(int witch) {
            Tools.makeToast((int) C1680R.string.string_delete_sync);
            BaasHelper.deleteWeightInBackground(WeightActivity.this.bean, new C18481());
        }
    }

    class C18504 implements MyAlertDialog.OnClickListener {
        C18504() {
        }

        public void onClick(int witch) {
            WeightActivity.this.deleteLocal();
        }
    }

    class C18526 extends PagerAdapter {
        private TextView tvBmi;
        private TextView tvBmi1;
        private TextView tvBmi2;
        private TextView tvBmi3;
        private View view;

        C18526() {
        }

        public int getCount() {
            return 1;
        }

        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        public Object instantiateItem(ViewGroup container, int position) {
            float leftMargin;
            if (this.view == null) {
                this.view = LayoutInflater.from(WeightActivity.this).inflate(C1680R.layout.layout_bmi_hint_pager, container, false);
                this.tvBmi = (TextView) this.view.findViewById(C1680R.id.tv_bmi_value);
                this.tvBmi1 = (TextView) this.view.findViewById(C1680R.id.tv_weight_state_slim);
                this.tvBmi2 = (TextView) this.view.findViewById(C1680R.id.tv_weight_state_standard);
                this.tvBmi3 = (TextView) this.view.findViewById(C1680R.id.tv_weight_state_overweight);
                this.tvBmi1.setText(String.valueOf(WeightActivity.BMI1));
                this.tvBmi2.setText(String.valueOf(WeightActivity.BMI2));
                this.tvBmi3.setText(String.valueOf(WeightActivity.BMI3));
            }
            float bmi = Tools.calculateBmi(WeightActivity.this.user.getHeight(), WeightActivity.this.bean.getWeight());
            this.tvBmi.setText(WeightActivity.this.decimalFormat.format((double) bmi));
            container.addView(this.view);
            if (bmi <= WeightActivity.BMI2 && bmi > WeightActivity.BMI1) {
                leftMargin = 10.5f + 65.0f;
            } else if (bmi <= WeightActivity.BMI3) {
                leftMargin = 10.5f + 130.0f;
            } else {
                leftMargin = 10.5f + 195.0f;
            }
            AnimUtils.playTranstionAnime(this.tvBmi, (float) Tools.dip2px(leftMargin), 2000, new AccelerateDecelerateInterpolator());
            return this.view;
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) C1680R.layout.activity_weight);
        initView();
        initChartTop();
        generateLineData();
        initWeightState();
    }

    private void initView() {
        this.imgChange = (ImageView) findViewById(C1680R.id.img_change);
        this.chartTop = (WeightLineChartView) findViewById(C1680R.id.chart_top);
        initViewHeight();
        this.tvDate = (TextView) findViewById(C1680R.id.tv_date);
        this.tvBmi = (TextView) findViewById(C1680R.id.tv_bmi);
        this.tvWeight = (TextView) findViewById(C1680R.id.tv_weight);
        this.tvWeightCompare = (TextView) findViewById(C1680R.id.tv_weight_compare);
        this.tvWeightState = (TextView) findViewById(C1680R.id.tv_weight_state);
        this.tvWeightStateSlim = (TextView) findViewById(C1680R.id.tv_weight_state_slim);
        this.tvWeightStateStandard = (TextView) findViewById(C1680R.id.tv_weight_state_standard);
        this.tvWeightStateOverweight = (TextView) findViewById(C1680R.id.tv_weight_state_overweight);
        this.chartTop.setOnValueTouchListener(new C18461());
        this.standardWeight = (int) Tools.calculateWeight(BMI2, this.user.getHeight());
        this.chartTop.setLine(this.standardWeight);
        MyActionBar actionBar = (MyActionBar) findViewById(C1680R.id.action_bar);
        if ($assertionsDisabled || actionBar != null) {
            actionBar.setOnRightButtonClickListener(new C18472());
            return;
        }
        throw new AssertionError();
    }

    private void initWeightState() {
        String weight1 = this.decimalFormat.format((double) Tools.calculateWeight(BMI1, this.user.getHeight())) + "㎏";
        String weight2 = this.decimalFormat.format((double) Tools.calculateWeight(BMI2, this.user.getHeight())) + "㎏";
        String weight3 = this.decimalFormat.format((double) Tools.calculateWeight(BMI3, this.user.getHeight())) + "㎏";
        this.tvWeightStateSlim.setText(weight1);
        this.tvWeightStateStandard.setText(weight2);
        this.tvWeightStateOverweight.setText(weight3);
    }

    private void initViewHeight() {
        LayoutParams params = (LayoutParams) this.chartTop.getLayoutParams();
        params.height -= SystemBarConfig.getNavigationBarHeight();
        this.chartTop.setLayoutParams(params);
    }

    private void initChartTop() {
        for (int i = 0; i < 7; i++) {
            this.points.add(new PointValue((float) i, (float) this.standardWeight));
        }
        this.line = new Line(this.points).setCubic(true);
        this.line.setPointColor(-1);
        this.line.setColor(-1258291201);
        this.line.setStrokeWidth(2);
        this.line.setPointRadius(4);
        this.lines = new ArrayList();
        this.lines.add(this.line);
        this.chartTop.setLineChartData(new LineChartData(this.lines));
        this.chartTop.setViewportCalculationEnabled(false);
        Viewport v = new Viewport(0.0f, 150.0f, 6.0f, 30.0f);
        this.chartTop.setMaximumViewport(v);
        this.chartTop.setCurrentViewport(v);
        this.chartTop.setZoomEnabled(false);
        this.chartTop.setValueSelectionEnabled(true);
    }

    private void generateLineData() {
        this.chartTop.cancelDataAnimation();
        this.weights = WeightHelper.getLastWeekWeight();
        List<PointValue> values = new ArrayList();
        for (int i = 0; i < this.weights.size(); i++) {
            PointValue value = (PointValue) this.points.get(i);
            value.setTarget(value.getX(), ((WeightBean) this.weights.get(i)).getWeight());
            values.add(value);
        }
        this.line.setValues(values);
        this.lines.clear();
        this.lines.add(this.line);
        getRange();
        Viewport v = new Viewport(0.0f, this.max, 6.0f, this.min);
        this.chartTop.setMaximumViewport(v);
        this.chartTop.setCurrentViewport(v);
        SelectedValue value2 = this.chartTop.getSelectedValue();
        value2.set(0, this.weights.size() - 1, SelectedValueType.LINE);
        this.chartTop.selectValue(value2);
        this.chartTop.startDataAnimation(800);
    }

    private void updateViewData() {
        this.tvWeight.setText(this.decimalFormat.format((double) this.bean.getWeight()));
        this.tvDate.setText(Tools.formatDate(this.bean.getDate(), Tools.DEFAULT_FORMAT_TIME, "MM/dd"));
        float bmi = Tools.calculateBmi(this.user.getHeight(), this.bean.getWeight());
        this.tvBmi.setText(this.decimalFormat.format((double) bmi));
        setWeightState(bmi);
        WeightBean before = WeightHelper.getBeforeWeight(this.bean);
        this.imgChange.setImageResource(this.bean.getWeight() > before.getWeight() ? C1680R.drawable.up : C1680R.drawable.down);
        this.tvWeightCompare.setText(this.decimalFormat.format((double) Math.abs(this.bean.getWeight() - before.getWeight())));
    }

    private void setWeightState(float bmi) {
        if (bmi <= BMI1) {
            this.tvWeightState.setText(C1680R.string.weight_state_slim);
        } else if (bmi <= BMI2) {
            this.tvWeightState.setText(C1680R.string.weight_state_standard);
        } else if (bmi <= BMI3) {
            this.tvWeightState.setText(C1680R.string.weight_state_overweight);
        } else {
            this.tvWeightState.setText(C1680R.string.weight_state_obesity);
        }
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case C1680R.id.btn_bmi_hint:
                showBmiHintDialog();
                return;
            case C1680R.id.btn_add_weight:
                showFloatPickerDialog();
                return;
            case C1680R.id.btn_delete_weight:
                if (this.weights.size() <= 1) {
                    Tools.makeToast((int) C1680R.string.weight_delete_least_one);
                    return;
                } else if (this.bean.getSync() == 1) {
                    showSyncDeleteDialog();
                    return;
                } else {
                    showLocalDeleteDialog();
                    return;
                }
            default:
                return;
        }
    }

    private void showSyncDeleteDialog() {
        MyAlertDialog dialog = new MyAlertDialog(this);
        dialog.setTitle((int) C1680R.string.string_reminder);
        dialog.setMessage((int) C1680R.string.string_delete_data_sync);
        dialog.setLeftButton((int) C1680R.string.string_cancel, null);
        dialog.setRightButton((int) C1680R.string.string_ok, new C18493());
        dialog.show();
    }

    private void showLocalDeleteDialog() {
        MyAlertDialog dialog = new MyAlertDialog(this);
        dialog.setTitle((int) C1680R.string.string_reminder);
        dialog.setMessage((int) C1680R.string.string_delete_data);
        dialog.setLeftButton((int) C1680R.string.string_cancel, null);
        dialog.setRightButton((int) C1680R.string.string_ok, new C18504());
        dialog.show();
    }

    private void deleteLocal() {
        WeightHelper.getBeanDao().deleteByKey(this.bean.getDate());
        ((PointValue) this.points.get(this.position)).set(((PointValue) this.points.get(this.position)).getX(), 70.0f);
        generateLineData();
        Tools.makeToast((int) C1680R.string.string_delete_complete);
    }

    private void showFloatPickerDialog() {
        final WeightBean newest = WeightHelper.getNewestWeight();
        final FloatPickerDialog dialog = new FloatPickerDialog(this);
        final FloatPickerView picker = (FloatPickerView) dialog.getPickerView();
        picker.setNumberRange(15, 200);
        picker.setLabel("㎏");
        picker.setInitFloatPicked(newest.getWeight());
        picker.show();
        dialog.setOKButton(new OnClickListener() {
            public void onClick(View v) {
                dialog.dismiss();
                String today = Tools.getToday();
                float value = picker.getSelectedFloat();
                if (value != newest.getWeight() || !today.equals(newest.getDate())) {
                    WeightHelper.getBeanDao().insertOrReplace(new WeightBean(today, WeightActivity.this.user.getUserId(), value, 0));
                    WeightActivity.this.generateLineData();
                    Tools.sendHeightWeightToDevice();
                }
            }
        }).setTextColor(getResources().getColor(C1680R.color.background_color_weight));
        dialog.show();
    }

    private void showBmiHintDialog() {
        HintPagerDialog dialog = new HintPagerDialog(this);
        dialog.setAdapter(this.adapter);
        dialog.show();
    }

    public void onBackPressed() {
        super.onBackPressed();
        BaasHelper.uploadWeightInBackground();
    }

    private void showShareDialog() {
        FullShareDialog dialog = new FullShareDialog(this);
        View convertView = getLayoutInflater().inflate(C1680R.layout.layout_share_weight, null);
        TextView change = (TextView) convertView.findViewById(C1680R.id.tv_change);
        ImageView imgStatus = (ImageView) convertView.findViewById(C1680R.id.img_status);
        TextView date = (TextView) convertView.findViewById(C1680R.id.tv_date);
        WeightBean before = WeightHelper.getBeforeWeight(this.bean);
        imgStatus.setImageResource(this.bean.getWeight() > before.getWeight() ? C1680R.drawable.share_up : C1680R.drawable.share_down);
        change.setText(this.decimalFormat.format((double) Math.abs(this.bean.getWeight() - before.getWeight())));
        date.setText(Tools.formatDate(this.bean.getDate(), Tools.DEFAULT_FORMAT_TIME, "yyyy.MM.dd"));
        dialog.setContentView(convertView);
        dialog.setMessage(getString(this.bean.getWeight() > before.getWeight() ? C1680R.string.share_weight_up : C1680R.string.share_weight_down, new Object[]{change.getText()}) + getString(C1680R.string.share_suffix));
        dialog.show();
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }

    @Subscribe
    public void onEventMainThread(EventGetWeight event) {
        generateLineData();
    }

    private void getRange() {
        this.min = ((WeightBean) this.weights.get(0)).getWeight();
        this.max = ((WeightBean) this.weights.get(0)).getWeight();
        for (WeightBean bean : this.weights) {
            if (this.min > bean.getWeight()) {
                this.min = bean.getWeight();
            }
            if (this.max < bean.getWeight()) {
                this.max = bean.getWeight();
            }
        }
        this.min -= 5.0f;
        this.max += 5.0f;
        if (this.min > ((float) (this.standardWeight - 20))) {
            this.min = (float) (this.standardWeight - 20);
        }
        if (this.max < ((float) (this.standardWeight + 40))) {
            this.max = (float) (this.standardWeight + 40);
        }
    }
}
