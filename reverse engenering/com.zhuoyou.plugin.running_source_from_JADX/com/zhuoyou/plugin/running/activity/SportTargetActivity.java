package com.zhuoyou.plugin.running.activity;

import android.os.Bundle;
import com.droi.library.pickerviews.picker.NumberPickerView;
import com.droi.library.pickerviews.picker.NumberPickerView.OnNumberPickedListener;
import com.zhuoyou.plugin.running.C1680R;
import com.zhuoyou.plugin.running.base.BaseActivity;
import com.zhuoyou.plugin.running.bean.EventStep;
import com.zhuoyou.plugin.running.database.SportHelper;
import com.zhuoyou.plugin.running.tools.SPUtils;
import org.greenrobot.eventbus.EventBus;

public class SportTargetActivity extends BaseActivity {
    static final /* synthetic */ boolean $assertionsDisabled = (!SportTargetActivity.class.desiredAssertionStatus());
    private static final int STEP_MAX = 50;
    private static final int STEP_MIN = 5;
    private static final int STEP_MUL = 1000;
    private int mTarget = SPUtils.getTargetStep();

    class C18181 implements OnNumberPickedListener {
        C18181() {
        }

        public void onNumberPicked(int index, int number) {
            SportTargetActivity.this.mTarget = number;
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) C1680R.layout.activity_sport_target);
        initView();
    }

    private void initView() {
        NumberPickerView mStepsPicker = (NumberPickerView) findViewById(C1680R.id.steps_pickerview);
        if ($assertionsDisabled || mStepsPicker != null) {
            mStepsPicker.setNumberRange(5, 50);
            mStepsPicker.setInitNumberPicked(this.mTarget / 1000);
            mStepsPicker.setMultiple(1000);
            mStepsPicker.setLabel(getString(C1680R.string.mine_goal_steps));
            int color = getResources().getColor(C1680R.color.main_picker_color);
            int colorout = getResources().getColor(C1680R.color.main_picker_color_out);
            mStepsPicker.setDividerColor(color);
            mStepsPicker.setLabelColor(color);
            mStepsPicker.setTextColorCenter(color);
            mStepsPicker.setTextColorOut(colorout);
            mStepsPicker.setTextSize(32);
            mStepsPicker.setDividerWidth(1.0f);
            mStepsPicker.setOnNumberPickedListener(new C18181());
            mStepsPicker.show();
            return;
        }
        throw new AssertionError();
    }

    public void onBackPressed() {
        SPUtils.setTargetStep(this.mTarget);
        SportHelper.updateTodayStepByPed(0);
        EventBus.getDefault().post(new EventStep(false));
        super.onBackPressed();
    }
}
