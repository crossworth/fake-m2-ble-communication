package com.zhuoyou.plugin.running.view;

import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.zhuoyou.plugin.running.C1680R;
import com.zhuoyou.plugin.running.tools.Fonts;

public class StepView extends RelativeLayout {
    public static final int STEP_ID = 2131690053;
    private ArcView arcView;
    private int targetStep;
    private int totalStep;
    private TextView tvCal;
    private TextView tvDistance;
    private TextView tvStep;

    class C19571 implements AnimatorUpdateListener {
        C19571() {
        }

        public void onAnimationUpdate(ValueAnimator animation) {
            int current = ((Integer) animation.getAnimatedValue()).intValue();
            StepView.this.arcView.draw(StepView.this.targetStep, current);
            StepView.this.tvStep.setText(String.valueOf(current));
        }
    }

    public StepView(Context context) {
        super(context);
        init();
    }

    public StepView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public StepView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        View rootView = LayoutInflater.from(getContext()).inflate(C1680R.layout.layout_step_view, this, true);
        this.arcView = (ArcView) rootView.findViewById(C1680R.id.arc_step);
        this.tvStep = (TextView) rootView.findViewById(C1680R.id.tv_step);
        this.tvStep.setTypeface(Fonts.number);
        this.tvDistance = (TextView) rootView.findViewById(C1680R.id.tv_distance);
        this.tvDistance.setTypeface(Fonts.number);
        this.tvCal = (TextView) rootView.findViewById(C1680R.id.tv_cal);
        this.tvCal.setTypeface(Fonts.number);
        this.tvStep.setText("0");
        this.tvDistance.setText("--");
        this.tvCal.setText("--");
    }

    public void setStep(int target, int value) {
        this.targetStep = target;
        this.totalStep = value;
    }

    public void setStep(int target, int value, boolean anim) {
        this.targetStep = target;
        this.totalStep = value;
        if (!anim) {
            this.arcView.draw(this.targetStep, this.totalStep);
            this.tvStep.setText(String.valueOf(this.totalStep));
        }
    }

    public int getTargetStep() {
        return this.targetStep;
    }

    public int getTotalStep() {
        return this.totalStep;
    }

    public void setOnClickListener(OnClickListener l) {
        this.arcView.setOnClickListener(l);
    }

    public void setCalString(String cal) {
        this.tvCal.setText(cal);
    }

    public void setDistance(String distance) {
        this.tvDistance.setText(distance);
    }

    public void startAnim(long duration, long delay) {
        ValueAnimator stepAnimator = ValueAnimator.ofInt(new int[]{0, this.totalStep});
        stepAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        stepAnimator.addUpdateListener(new C19571());
        stepAnimator.setDuration(duration);
        stepAnimator.setStartDelay(delay);
        stepAnimator.start();
    }
}
