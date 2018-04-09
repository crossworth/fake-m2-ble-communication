package com.zhuoyou.plugin.running;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.fithealth.running.R;
import com.zhuoyi.system.util.constant.SeparatorConstants;
import com.zhuoyou.plugin.info.PersonalInformation;
import com.zhuoyou.plugin.view.NewRelativeLayout;
import com.zhuoyou.plugin.view.NewRelativeLayout.Operator;
import com.zhuoyou.plugin.view.NewScrollView;
import com.zhuoyou.plugin.view.NewTextView;
import com.zhuoyou.plugin.view.ViewWrapper;
import java.lang.ref.WeakReference;
import java.util.Calendar;

public class MotionDataActivity extends Activity implements OnClickListener, Operator, NewTextView.Operator {
    private RelativeLayout BMIIntroLayout;
    private RelativeLayout BMILayout;
    private NewTextView BMIText;
    private TextView BMIdescribe;
    private RelativeLayout BMRIntroLayout;
    private RelativeLayout BMRLayout;
    private NewTextView BMRText;
    private RelativeLayout avgCalLayout;
    private NewTextView avgCalText;
    private RelativeLayout avgStepLayout;
    private NewTextView avgStepText;
    private NewTextView bestCalText;
    private RelativeLayout bestCalorieLayout;
    private RelativeLayout bestStepLayout;
    private NewTextView bestStepText;
    private Bitmap bmp = null;
    private boolean canSendScroll = false;
    private boolean canWeightDisplay = false;
    public int clickTimes = 0;
    private TextView describe_total_calorie;
    private TextView describe_total_step;
    private TextView goalDays;
    private RelativeLayout goalLayout;
    private TextView goalPercent;
    private NewTextView goalStepText;
    private boolean isFirstProgressCal = true;
    private boolean isFirstProgressStep = true;
    private Context mCtx = null;
    private WRHandler mHandler = null;
    private MotionDataCenter mMotionDataCenter;
    private NewScrollView mNewScrollView;
    private Typeface mNumberTP;
    private PersonalConfig mPersonalConfig;
    private PersonalGoal mPersonalGoal;
    private int mWindowHeight;
    private NewRelativeLayout progressCal;
    private NewRelativeLayout progressStep;
    private ImageView progress_cal;
    private ImageView progress_step;
    private int targetCalWidth;
    private int targetStepWidth;
    private NewTextView totalCalText;
    private NewTextView totalStepText;
    private LinearLayout userInfoLayout;
    private NewTextView weightText;
    private RelativeLayout weightTrendLayout;

    class C13891 implements OnClickListener {
        C13891() {
        }

        public void onClick(View v) {
            MotionDataActivity.this.finish();
        }
    }

    class C13902 implements AnimationListener {
        C13902() {
        }

        public void onAnimationEnd(Animation animation) {
            MotionDataActivity.this.bestStepLayout.setVisibility(8);
            MotionDataActivity.this.setViewAvgStep();
        }

        public void onAnimationRepeat(Animation animation) {
        }

        public void onAnimationStart(Animation animation) {
        }
    }

    class C13913 implements AnimationListener {
        C13913() {
        }

        public void onAnimationEnd(Animation animation) {
            MotionDataActivity.this.bestCalorieLayout.setVisibility(8);
            MotionDataActivity.this.setViewAvgCal();
        }

        public void onAnimationRepeat(Animation animation) {
        }

        public void onAnimationStart(Animation animation) {
        }
    }

    class C13924 implements AnimationListener {
        C13924() {
        }

        public void onAnimationEnd(Animation animation) {
            MotionDataActivity.this.avgStepLayout.setVisibility(8);
            MotionDataActivity.this.setViewBestStep();
        }

        public void onAnimationRepeat(Animation animation) {
        }

        public void onAnimationStart(Animation animation) {
        }
    }

    class C13935 implements AnimationListener {
        C13935() {
        }

        public void onAnimationEnd(Animation animation) {
            MotionDataActivity.this.avgCalLayout.setVisibility(8);
            MotionDataActivity.this.setViewBestCalorie();
        }

        public void onAnimationRepeat(Animation animation) {
        }

        public void onAnimationStart(Animation animation) {
        }
    }

    class C13946 implements AnimationListener {
        C13946() {
        }

        public void onAnimationEnd(Animation animation) {
            MotionDataActivity.this.BMILayout.setVisibility(8);
            MotionDataActivity.this.setViewBMIIntro();
        }

        public void onAnimationRepeat(Animation animation) {
        }

        public void onAnimationStart(Animation animation) {
        }
    }

    class C13957 implements AnimationListener {
        C13957() {
        }

        public void onAnimationEnd(Animation animation) {
            MotionDataActivity.this.BMRLayout.setVisibility(8);
            MotionDataActivity.this.setViewBMRIntro();
        }

        public void onAnimationRepeat(Animation animation) {
        }

        public void onAnimationStart(Animation animation) {
        }
    }

    class C13968 implements AnimationListener {
        C13968() {
        }

        public void onAnimationEnd(Animation animation) {
            MotionDataActivity.this.BMIIntroLayout.setVisibility(8);
            MotionDataActivity.this.setViewBMI();
        }

        public void onAnimationRepeat(Animation animation) {
        }

        public void onAnimationStart(Animation animation) {
        }
    }

    class C13979 implements AnimationListener {
        C13979() {
        }

        public void onAnimationEnd(Animation animation) {
            MotionDataActivity.this.BMRIntroLayout.setVisibility(8);
            MotionDataActivity.this.setViewBMR();
        }

        public void onAnimationRepeat(Animation animation) {
        }

        public void onAnimationStart(Animation animation) {
        }
    }

    private static class WRHandler extends Handler {
        WeakReference<MotionDataActivity> mMDActivity;

        public WRHandler(MotionDataActivity MDActivity) {
            this.mMDActivity = new WeakReference(MDActivity);
        }

        public void handleMessage(Message msg) {
            if (this.mMDActivity != null) {
                MotionDataActivity mda = (MotionDataActivity) this.mMDActivity.get();
                if (mda != null) {
                    if (!mda.goalStepText.refreshDisabled) {
                        mda.onMeasureTxt(mda.goalStepText);
                        mda.canSendScroll = true;
                    }
                    if (!mda.totalCalText.refreshDisabled) {
                        mda.onMeasureTxt(mda.totalCalText);
                        mda.canSendScroll = true;
                    }
                    if (!mda.BMIText.refreshDisabled) {
                        mda.onMeasureTxt(mda.BMIText);
                        mda.canSendScroll = true;
                    }
                    if (!mda.BMRText.refreshDisabled) {
                        mda.onMeasureTxt(mda.BMRText);
                        mda.canSendScroll = true;
                    }
                    if (!mda.weightText.refreshDisabled) {
                        mda.onMeasureTxt(mda.weightText);
                        mda.canSendScroll = true;
                    }
                    if (mda.isFirstProgressCal) {
                        mda.onMeasureLayout(mda.progressCal);
                        mda.canSendScroll = true;
                    }
                    if (mda.canSendScroll) {
                        mda.mNewScrollView.sendScroll(1, 0);
                    }
                }
            }
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mCtx = this;
        setContentView(R.layout.motion_data_layout);
        this.mMotionDataCenter = new MotionDataCenter(this.mCtx);
        initData();
        initView();
    }

    public void initData() {
        this.mPersonalConfig = Tools.getPersonalConfig();
        this.mPersonalGoal = Tools.getPersonalGoal();
        this.mNumberTP = RunningApp.getCustomNumberFont();
    }

    public void initView() {
        ((TextView) findViewById(R.id.title)).setText(R.string.data_center);
        ((RelativeLayout) findViewById(R.id.back)).setOnClickListener(new C13891());
        Rect rect = new Rect();
        getWindow().getDecorView().getWindowVisibleDisplayFrame(rect);
        this.mWindowHeight = rect.height();
        this.mNewScrollView = (NewScrollView) findViewById(R.id.new_scrollview);
        this.userInfoLayout = (LinearLayout) findViewById(R.id.dc_linearlayout_user_info);
        this.bestStepLayout = (RelativeLayout) findViewById(R.id.dc_relativelayout_record_step);
        this.bestCalorieLayout = (RelativeLayout) findViewById(R.id.dc_relativelayout_record_calorie);
        this.avgStepLayout = (RelativeLayout) findViewById(R.id.dc_relativelayout_avg_step);
        this.avgCalLayout = (RelativeLayout) findViewById(R.id.dc_relativelayout_avg_cal);
        this.goalLayout = (RelativeLayout) findViewById(R.id.dc_relativelayout_goal);
        this.BMILayout = (RelativeLayout) findViewById(R.id.dc_relativelayout_bmi);
        this.BMRLayout = (RelativeLayout) findViewById(R.id.dc_relativelayout_bmr);
        this.BMIIntroLayout = (RelativeLayout) findViewById(R.id.dc_relativelayout_intro_bmi);
        this.BMRIntroLayout = (RelativeLayout) findViewById(R.id.dc_relativelayout_intro_bmr);
        this.goalStepText = (NewTextView) findViewById(R.id.text_goal_step);
        this.bestStepText = (NewTextView) findViewById(R.id.record_text_step);
        this.bestCalText = (NewTextView) findViewById(R.id.record_text_calorie);
        this.avgStepText = (NewTextView) findViewById(R.id.text_avg_step);
        this.avgCalText = (NewTextView) findViewById(R.id.text_avg_cal);
        this.describe_total_step = (TextView) findViewById(R.id.describe_total_step);
        this.describe_total_calorie = (TextView) findViewById(R.id.describe_total_calorie);
        this.goalDays = (TextView) findViewById(R.id.text_goal_day);
        this.goalPercent = (TextView) findViewById(R.id.text_goal_percent);
        this.totalStepText = (NewTextView) findViewById(R.id.text_total_step);
        this.totalCalText = (NewTextView) findViewById(R.id.text_total_calorie);
        this.BMIText = (NewTextView) findViewById(R.id.text_bmi);
        this.BMRText = (NewTextView) findViewById(R.id.text_bmr);
        this.BMIdescribe = (TextView) findViewById(R.id.describe_bmi);
        this.weightTrendLayout = (RelativeLayout) findViewById(R.id.dc_relativelayout_weight_trend);
        this.weightText = (NewTextView) findViewById(R.id.dc_text_weight_trend);
        this.progressStep = (NewRelativeLayout) findViewById(R.id.relativelayout_progress_step);
        this.progressCal = (NewRelativeLayout) findViewById(R.id.relativelayout_progress_cal);
        this.progress_step = (ImageView) findViewById(R.id.progress_step);
        this.progress_cal = (ImageView) findViewById(R.id.progress_cal);
        this.progressStep.setOperator(this);
        this.progressCal.setOperator(this);
        this.goalStepText.setOperator(this);
        this.bestStepText.setOperator(this);
        this.bestCalText.setOperator(this);
        this.avgStepText.setOperator(this);
        this.avgCalText.setOperator(this);
        this.totalStepText.setOperator(this);
        this.totalCalText.setOperator(this);
        this.BMIText.setOperator(this);
        this.BMRText.setOperator(this);
        this.weightText.setOperator(this);
        setView();
        initListener();
        this.userInfoLayout.setOnClickListener(this);
        this.bestStepLayout.setOnClickListener(this);
        this.bestCalorieLayout.setOnClickListener(this);
        this.avgStepLayout.setOnClickListener(this);
        this.avgCalLayout.setOnClickListener(this);
        this.goalLayout.setOnClickListener(this);
        this.BMILayout.setOnClickListener(this);
        this.BMRLayout.setOnClickListener(this);
        this.BMIIntroLayout.setOnClickListener(this);
        this.BMRIntroLayout.setOnClickListener(this);
    }

    public void onResume() {
        super.onResume();
        Log.i("gchk", "MotionDataActivity onResume");
        initData();
        setView();
    }

    public void onPause() {
        super.onPause();
        Log.i("gchk", "MotionDataActivity onPause");
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() != 16908332) {
            return super.onOptionsItemSelected(item);
        }
        finish();
        return true;
    }

    public void onClick(View v) {
        Animation animation = AnimationUtils.loadAnimation(this.mCtx, R.anim.fade_out);
        Intent intent = new Intent();
        switch (v.getId()) {
            case R.id.dc_linearlayout_user_info:
                intent.setClass(this.mCtx, PersonalInformation.class);
                intent.putExtra("from_center", true);
                startActivity(intent);
                return;
            case R.id.dc_relativelayout_record_step:
                animation.setAnimationListener(new C13902());
                this.bestStepLayout.startAnimation(animation);
                return;
            case R.id.dc_relativelayout_avg_step:
                animation.setAnimationListener(new C13924());
                this.avgStepLayout.startAnimation(animation);
                return;
            case R.id.dc_relativelayout_record_calorie:
                animation.setAnimationListener(new C13913());
                this.bestCalorieLayout.startAnimation(animation);
                return;
            case R.id.dc_relativelayout_avg_cal:
                animation.setAnimationListener(new C13935());
                this.avgCalLayout.startAnimation(animation);
                return;
            case R.id.dc_relativelayout_goal:
                intent.setClass(this.mCtx, PersonalInformation.class);
                intent.putExtra("from_center", true);
                startActivity(intent);
                return;
            case R.id.dc_relativelayout_bmi:
                animation.setAnimationListener(new C13946());
                this.BMILayout.startAnimation(animation);
                return;
            case R.id.dc_relativelayout_intro_bmi:
                animation.setAnimationListener(new C13968());
                this.BMIIntroLayout.startAnimation(animation);
                return;
            case R.id.dc_relativelayout_bmr:
                animation.setAnimationListener(new C13957());
                this.BMRLayout.startAnimation(animation);
                return;
            case R.id.dc_relativelayout_intro_bmr:
                animation.setAnimationListener(new C13979());
                this.BMRIntroLayout.startAnimation(animation);
                return;
            default:
                return;
        }
    }

    private void setView() {
        setViewUserInfo();
        setViewGoalSetting();
        setViewBestStep();
        setViewBestCalorie();
        setViewTotalKM();
        setViewTotalCal();
        setViewBMI();
        setViewBMR();
        if (this.canWeightDisplay) {
            this.weightTrendLayout.setVisibility(0);
            setViewWeightTrend();
            return;
        }
        this.weightTrendLayout.setVisibility(8);
    }

    private void setViewUserInfo() {
        ImageView face = (ImageView) findViewById(R.id.dc_image_avatar);
        int headIndex = Tools.getHead(this);
        if (headIndex == 10000) {
            this.bmp = Tools.convertFileToBitmap("/Running/download/custom");
            face.setImageBitmap(this.bmp);
        } else if (headIndex == 1000) {
            this.bmp = Tools.convertFileToBitmap("/Running/download/logo");
            face.setImageBitmap(this.bmp);
        } else {
            face.setImageResource(Tools.selectByIndex(headIndex));
        }
        ((TextView) findViewById(R.id.dc_text_age)).setText((Calendar.getInstance().get(1) - this.mPersonalConfig.getYear()) + getResources().getString(R.string.unit_age));
        ((TextView) findViewById(R.id.dc_text_height)).setText(this.mPersonalConfig.getHeight() + getResources().getString(R.string.unit_length));
        ((TextView) findViewById(R.id.dc_text_weight)).setText(this.mPersonalConfig.getWeight() + getResources().getString(R.string.unit_weight));
    }

    private void setViewGoalSetting() {
        int goalStep = this.mPersonalGoal.mGoalSteps;
        this.goalStepText.setTypeface(this.mNumberTP);
        if (this.goalStepText.refreshDisabled) {
            this.goalStepText.setValue(goalStep);
            this.goalStepText.setText(goalStep);
        } else {
            this.goalStepText.setValue(goalStep);
        }
        String goal = this.mMotionDataCenter.getGoal();
        this.goalDays.setText(goal.split(SeparatorConstants.SEPARATOR_ADS_ID)[0] + " " + getResources().getString(R.string.day));
        this.goalPercent.setText(goal.split(SeparatorConstants.SEPARATOR_ADS_ID)[1]);
    }

    private void setViewBestStep() {
        int i = this.mMotionDataCenter.getBestSteps();
        this.bestStepText.setTypeface(this.mNumberTP);
        if (this.bestStepText.refreshDisabled) {
            this.bestStepText.setValue(i);
            this.bestStepText.setText(i);
        } else {
            this.bestStepText.setText("0");
            this.bestStepText.setValue(i);
        }
        this.avgStepLayout.setVisibility(8);
        Animation animation = AnimationUtils.loadAnimation(this.mCtx, R.anim.fade_in);
        animation.setAnimationListener(new AnimationListener() {
            public void onAnimationEnd(Animation animation) {
            }

            public void onAnimationRepeat(Animation animation) {
            }

            public void onAnimationStart(Animation animation) {
                MotionDataActivity.this.bestStepLayout.setVisibility(0);
            }
        });
        this.bestStepLayout.startAnimation(animation);
    }

    private void setViewAvgStep() {
        this.avgStepText.setText(this.mMotionDataCenter.getAvgSteps());
        this.avgStepText.setTypeface(this.mNumberTP);
        this.bestStepLayout.setVisibility(8);
        Animation animation = AnimationUtils.loadAnimation(this.mCtx, R.anim.fade_in);
        animation.setAnimationListener(new AnimationListener() {
            public void onAnimationEnd(Animation animation) {
            }

            public void onAnimationRepeat(Animation animation) {
            }

            public void onAnimationStart(Animation animation) {
                MotionDataActivity.this.avgStepLayout.setVisibility(0);
            }
        });
        this.avgStepLayout.startAnimation(animation);
    }

    private void setViewBestCalorie() {
        int i = this.mMotionDataCenter.getBestCalories();
        this.bestCalText.setTypeface(this.mNumberTP);
        if (this.bestCalText.refreshDisabled) {
            this.bestCalText.setValue(i);
            this.bestCalText.setText(i);
        } else {
            this.bestCalText.setText("0");
            this.bestCalText.setValue(i);
        }
        this.avgCalLayout.setVisibility(8);
        Animation animation = AnimationUtils.loadAnimation(this.mCtx, R.anim.fade_in);
        animation.setAnimationListener(new AnimationListener() {
            public void onAnimationEnd(Animation animation) {
            }

            public void onAnimationRepeat(Animation animation) {
            }

            public void onAnimationStart(Animation animation) {
                MotionDataActivity.this.bestCalorieLayout.setVisibility(0);
            }
        });
        this.bestCalorieLayout.startAnimation(animation);
    }

    private void setViewAvgCal() {
        this.avgCalText.setText(this.mMotionDataCenter.getAvgCalories());
        this.avgCalText.setTypeface(this.mNumberTP);
        this.bestCalorieLayout.setVisibility(8);
        Animation animation = AnimationUtils.loadAnimation(this.mCtx, R.anim.fade_in);
        animation.setAnimationListener(new AnimationListener() {
            public void onAnimationEnd(Animation animation) {
            }

            public void onAnimationRepeat(Animation animation) {
            }

            public void onAnimationStart(Animation animation) {
                MotionDataActivity.this.avgCalLayout.setVisibility(0);
            }
        });
        this.avgCalLayout.startAnimation(animation);
    }

    private void setViewTotalKM() {
        double d = this.mMotionDataCenter.getTotalKM();
        this.totalStepText.setTypeface(this.mNumberTP);
        if (d < 1.0E-4d) {
            this.totalStepText.setText(String.format("%.0f", new Object[]{Double.valueOf(d)}));
            this.describe_total_step.setText(R.string.describe_total_step_1);
        }
        if (this.totalStepText.refreshDisabled) {
            this.totalStepText.setValue(d);
            this.totalStepText.setText(String.format("%.1f", new Object[]{Double.valueOf(d)}));
        } else {
            this.totalStepText.setText("0");
            this.totalStepText.setValue(d);
        }
        if (d > 0.0d && d < 9.0d) {
            this.describe_total_step.setText(R.string.describe_total_step_1);
            this.targetStepWidth = (int) ((40.0d * d) / 9.0d);
        } else if (d >= 9.0d && d < 36.0d) {
            this.describe_total_step.setText(R.string.describe_total_step_2);
            this.targetStepWidth = (int) (40.0d + ((64.0d * d) / 36.0d));
        } else if (d >= 36.0d && d < 67.0d) {
            this.describe_total_step.setText(R.string.describe_total_step_3);
            this.targetStepWidth = (int) (104.0d + ((44.0d * d) / 67.0d));
        } else if (d >= 67.0d && d < 130.0d) {
            this.describe_total_step.setText(R.string.describe_total_step_4);
            this.targetStepWidth = (int) (148.0d + ((32.0d * d) / 130.0d));
        } else if (d >= 130.0d && d < 356.0d) {
            this.describe_total_step.setText(R.string.describe_total_step_5);
            this.targetStepWidth = (int) (180.0d + ((36.0d * d) / 356.0d));
        } else if (d >= 356.0d && d < 780.0d) {
            this.describe_total_step.setText(R.string.describe_total_step_6);
            this.targetStepWidth = (int) (216.0d + ((45.0d * d) / 780.0d));
        } else if (d >= 780.0d && d < 1140.0d) {
            this.describe_total_step.setText(R.string.describe_total_step_7);
            this.targetStepWidth = (int) (261.0d + ((14.0d * d) / 1140.0d));
        } else if (d >= 1140.0d && d < 1956.0d) {
            this.describe_total_step.setText(R.string.describe_total_step_8);
            this.targetStepWidth = (int) (275.0d + ((45.0d * d) / 1956.0d));
        } else if (d >= 1956.0d) {
            this.describe_total_step.setText(R.string.describe_total_step_9);
            this.targetStepWidth = 320;
        } else {
            this.describe_total_step.setText(R.string.describe_total_step_1);
            this.targetStepWidth = 0;
        }
    }

    private void setViewTotalCal() {
        int i = this.mMotionDataCenter.getTotalCalories();
        this.totalCalText.setTypeface(this.mNumberTP);
        if (this.totalCalText.refreshDisabled) {
            this.totalCalText.setValue(i);
            this.totalCalText.setText(i);
        } else {
            this.totalCalText.setText("0");
            this.totalCalText.setValue(i);
        }
        if (i > 0 && ((double) i) <= 500.0d) {
            this.describe_total_calorie.setText(getResources().getString(R.string.equal_to) + " " + String.format("%.1f", new Object[]{Double.valueOf(((double) i) / 127.0d)}) + " " + getResources().getString(R.string.ice_cream));
            this.targetCalWidth = (int) (((double) (i * 24)) / 500.0d);
        } else if (((double) i) > 500.0d && ((double) i) <= 1500.0d) {
            this.describe_total_calorie.setText(getResources().getString(R.string.equal_to) + " " + String.format("%.1f", new Object[]{Double.valueOf(((double) i) / 320.0d)}) + " " + getResources().getString(R.string.beer));
            this.targetCalWidth = (int) (24.0d + (((double) (i * 40)) / 1500.0d));
        } else if (((double) i) > 1500.0d && ((double) i) <= 5000.0d) {
            this.describe_total_calorie.setText(getResources().getString(R.string.equal_to) + " " + String.format("%.1f", new Object[]{Double.valueOf(((double) i) / 1400.0d)}) + " " + getResources().getString(R.string.duck));
            this.targetCalWidth = (int) (64.0d + (((double) (i * 44)) / 5000.0d));
        } else if (((double) i) > 5000.0d && ((double) i) <= 12000.0d) {
            this.describe_total_calorie.setText(getResources().getString(R.string.Ferrari) + " " + String.format("%.1f", new Object[]{Double.valueOf(((double) i) / 4320.0d)}) + " " + getResources().getString(R.string.minute));
            this.targetCalWidth = (int) (108.0d + (((double) (i * 57)) / 12000.0d));
        } else if (((double) i) > 12000.0d && ((double) i) <= 25000.0d) {
            this.describe_total_calorie.setText(getResources().getString(R.string.family_of_three) + " " + String.format("%.1f", new Object[]{Double.valueOf(((double) i) / 8600.0d)}) + " " + getResources().getString(R.string.day));
            this.targetCalWidth = (int) (163.0d + (((double) (i * 48)) / 25000.0d));
        } else if (((double) i) > 25000.0d && ((double) i) <= 50000.0d) {
            this.describe_total_calorie.setText(getResources().getString(R.string.equal_to) + " " + String.format("%.1f", new Object[]{Double.valueOf(((double) i) / 13000.0d)}) + " " + getResources().getString(R.string.elephant));
            this.targetCalWidth = (int) (211.0d + (((double) (i * 64)) / 50000.0d));
        } else if (((double) i) > 50000.0d && ((double) i) <= 100000.0d) {
            this.describe_total_calorie.setText(getResources().getString(R.string.airplane_747) + " " + String.format("%.1f", new Object[]{Double.valueOf((0.3d * ((double) i)) / 27109.0d)}) + " " + getResources().getString(R.string.kilometre));
            this.targetCalWidth = (int) (275.0d + (((double) (i * 45)) / 100000.0d));
        } else if (((double) i) > 100000.0d) {
            this.describe_total_calorie.setText(getResources().getString(R.string.airplane_747) + " " + String.format("%.1f", new Object[]{Double.valueOf((0.3d * ((double) i)) / 27109.0d)}) + " " + getResources().getString(R.string.kilometre));
            this.targetCalWidth = 320;
        } else {
            this.describe_total_calorie.setText(R.string.no_cal);
            this.targetCalWidth = 0;
        }
    }

    private void setViewBMI() {
        double d = this.mMotionDataCenter.getBMI();
        this.BMIText.setTypeface(this.mNumberTP);
        if (this.BMIText.refreshDisabled) {
            this.BMIText.setValue(d);
            this.BMIText.setText(String.format("%.1f", new Object[]{Double.valueOf(d)}));
        } else {
            this.BMIText.setText("0");
            this.BMIText.setValue(d);
        }
        if (this.mPersonalConfig.getSex() == 0) {
            if (d < 18.5d) {
                this.BMIdescribe.setText(R.string.bm1_describe_1);
            } else if (d >= 18.5d && d <= 23.0d) {
                this.BMIdescribe.setText(R.string.bm1_describe_2);
            } else if (d > 23.0d && d <= 24.0d) {
                this.BMIdescribe.setText(R.string.bm1_describe_3);
            } else if (d > 24.0d) {
                this.BMIdescribe.setText(R.string.bm1_describe_4);
            }
        } else if (this.mPersonalConfig.getSex() == 1) {
            if (d <= 17.0d) {
                this.BMIdescribe.setText(R.string.bm1_describe_5);
            } else if (d > 17.0d && d <= 19.0d) {
                this.BMIdescribe.setText(R.string.bm1_describe_6);
            } else if (d > 19.0d && d <= 23.0d) {
                this.BMIdescribe.setText(R.string.bm1_describe_7);
            } else if (d > 23.0d) {
                this.BMIdescribe.setText(R.string.bm1_describe_4);
            }
        }
        this.BMIIntroLayout.setVisibility(8);
        Animation animation = AnimationUtils.loadAnimation(this.mCtx, R.anim.fade_in);
        animation.setAnimationListener(new AnimationListener() {
            public void onAnimationEnd(Animation animation) {
            }

            public void onAnimationRepeat(Animation animation) {
            }

            public void onAnimationStart(Animation animation) {
                MotionDataActivity.this.BMILayout.setVisibility(0);
            }
        });
        this.BMILayout.startAnimation(animation);
    }

    private void setViewBMIIntro() {
        Animation animation = AnimationUtils.loadAnimation(this.mCtx, R.anim.fade_in);
        animation.setAnimationListener(new AnimationListener() {
            public void onAnimationEnd(Animation animation) {
            }

            public void onAnimationRepeat(Animation animation) {
            }

            public void onAnimationStart(Animation animation) {
                MotionDataActivity.this.BMIIntroLayout.setVisibility(0);
            }
        });
        this.BMIIntroLayout.startAnimation(animation);
    }

    private void setViewBMR() {
        int i = this.mMotionDataCenter.getBMR();
        this.BMRText.setTypeface(this.mNumberTP);
        if (this.BMRText.refreshDisabled) {
            this.BMRText.setValue(i);
            this.BMRText.setText(i);
        } else {
            this.BMRText.setText("0");
            this.BMRText.setValue(i);
        }
        this.BMRIntroLayout.setVisibility(8);
        Animation animation = AnimationUtils.loadAnimation(this.mCtx, R.anim.fade_in);
        animation.setAnimationListener(new AnimationListener() {
            public void onAnimationEnd(Animation animation) {
            }

            public void onAnimationRepeat(Animation animation) {
            }

            public void onAnimationStart(Animation animation) {
                MotionDataActivity.this.BMRLayout.setVisibility(0);
            }
        });
        this.BMRLayout.startAnimation(animation);
    }

    private void setViewBMRIntro() {
        Animation animation = AnimationUtils.loadAnimation(this.mCtx, R.anim.fade_in);
        animation.setAnimationListener(new AnimationListener() {
            public void onAnimationEnd(Animation animation) {
            }

            public void onAnimationRepeat(Animation animation) {
            }

            public void onAnimationStart(Animation animation) {
                MotionDataActivity.this.BMRIntroLayout.setVisibility(0);
            }
        });
        this.BMRIntroLayout.startAnimation(animation);
    }

    private void setViewWeightTrend() {
    }

    private void initListener() {
        this.mNewScrollView.addListener(this.goalStepText);
        this.mNewScrollView.addListener(this.bestCalText);
        this.mNewScrollView.addListener(this.bestStepText);
        this.mNewScrollView.addListener(this.totalCalText);
        this.mNewScrollView.addListener(this.totalStepText);
        this.mNewScrollView.addListener(this.BMIText);
        this.mNewScrollView.addListener(this.BMRText);
        this.mNewScrollView.addListener(this.weightText);
        this.mNewScrollView.addListener(this.progressStep);
        this.mNewScrollView.addListener(this.progressCal);
        this.mHandler = new WRHandler(this);
        this.mHandler.sendEmptyMessageDelayed(0, 500);
    }

    private void onMeasureTxt(NewTextView newtextview) {
        int[] location = new int[2];
        newtextview.getLocationInWindow(location);
        newtextview.setLocHeight(location[1]);
    }

    private void onMeasureLayout(NewRelativeLayout newrelativelayout) {
        int[] location = new int[2];
        newrelativelayout.getLocationInWindow(location);
        newrelativelayout.setLocHeight(location[1]);
    }

    private void performPorpertyAnimate(View paramView, int paramInt1, int paramInt2, long paramLong) {
        ViewWrapper localViewWrapper = new ViewWrapper(paramView);
        int i = Tools.dip2px(this.mCtx, (float) paramInt2);
        ObjectAnimator.ofInt(localViewWrapper, "width", new int[]{paramInt1, i}).setDuration(paramLong).start();
    }

    protected void onDestroy() {
        super.onDestroy();
        if (this.bmp != null) {
            this.bmp.recycle();
            this.bmp = null;
            System.gc();
        }
    }

    public void refreshLayout() {
        if (this.isFirstProgressStep && this.progressStep.canAnimate) {
            performPorpertyAnimate(this.progressStep, 0, this.targetStepWidth, 1500);
            this.isFirstProgressStep = false;
        } else if (!(this.isFirstProgressStep || this.progress_step == null)) {
            LayoutParams localLayoutParams = this.progress_step.getLayoutParams();
            localLayoutParams.width = Tools.dip2px(this.mCtx, (float) this.targetStepWidth);
            this.progress_step.setLayoutParams(localLayoutParams);
        }
        if (this.isFirstProgressCal && this.progressCal.canAnimate) {
            performPorpertyAnimate(this.progressCal, 0, this.targetCalWidth, 1500);
            this.isFirstProgressCal = false;
        } else if (!this.isFirstProgressCal && this.progress_cal != null) {
            localLayoutParams = this.progress_cal.getLayoutParams();
            localLayoutParams.width = Tools.dip2px(this.mCtx, (float) this.targetCalWidth);
            this.progress_cal.setLayoutParams(localLayoutParams);
        }
    }

    public int getWindowHeight() {
        if (this.mWindowHeight != 0) {
            return this.mWindowHeight;
        }
        return 0;
    }
}
