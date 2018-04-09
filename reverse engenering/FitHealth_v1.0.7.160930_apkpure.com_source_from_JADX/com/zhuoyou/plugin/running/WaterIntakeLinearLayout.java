package com.zhuoyou.plugin.running;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import com.fithealth.running.R;

public class WaterIntakeLinearLayout extends LinearLayout {
    private static int[] water_number = new int[]{R.drawable.water_zero, R.drawable.water_one, R.drawable.water_two, R.drawable.water_three, R.drawable.water_four, R.drawable.water_five, R.drawable.water_six, R.drawable.water_seven, R.drawable.water_eight, R.drawable.water_nine};
    private ImageView mWaterBitIcon = ((ImageView) findViewById(R.id.water_bit));
    private ImageView mWaterTenIcon = ((ImageView) findViewById(R.id.water_ten));

    public WaterIntakeLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        ((LayoutInflater) context.getSystemService("layout_inflater")).inflate(R.layout.water_num_layout, this, true);
    }

    public void setWaterNumber(int num) {
        if (num < 10) {
            this.mWaterTenIcon.setVisibility(8);
            this.mWaterBitIcon.setVisibility(0);
            this.mWaterBitIcon.setImageResource(water_number[num]);
        } else if (num >= 10 && num < 100) {
            this.mWaterTenIcon.setVisibility(0);
            this.mWaterBitIcon.setVisibility(0);
            this.mWaterTenIcon.setImageResource(water_number[num / 10]);
            this.mWaterBitIcon.setImageResource(water_number[num % 10]);
        }
    }
}
