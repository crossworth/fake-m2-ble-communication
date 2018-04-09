package com.zhuoyou.plugin.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import com.droi.library.pickerviews.adapter.NumericWheelAdapter;
import com.droi.library.pickerviews.adapter.WheelAdapter;
import com.droi.library.pickerviews.lib.WheelView;
import com.droi.library.pickerviews.listener.OnItemSelectedListener;
import com.fithealth.running.R;

public class NumberPickerView extends LinearLayout {
    private WheelAdapter adapter;
    private View contentView;
    private int index;
    private OnNumberPickedListener listener;
    private int max = 100;
    private int min = 0;
    private WheelView numberView;

    public interface OnNumberPickedListener {
        void onNumberPicked(int i, int i2);
    }

    class C19061 implements OnItemSelectedListener {
        C19061() {
        }

        public void onItemSelected(int index) {
            NumberPickerView.this.index = index;
            NumberPickerView.this.listener.onNumberPicked(index, ((Integer) NumberPickerView.this.adapter.getItem(index)).intValue());
        }
    }

    public NumberPickerView(Context context) {
        super(context);
        initView(context);
    }

    public NumberPickerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public NumberPickerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context) {
        this.contentView = LayoutInflater.from(context).inflate(R.layout.number_pickerview_layout, this, true);
        this.numberView = (WheelView) this.contentView.findViewById(R.id.number_view);
        this.numberView.setOnItemSelectedListener(new C19061());
    }

    public void setNumberRange(int min, int max) {
        this.min = min;
        this.max = max;
    }

    public void setInitNumberPicked(int number) {
        if (number > this.max || number < this.min) {
            number = this.min;
        }
        this.index = number - this.min;
    }

    public void setNumberPicked(int number) {
        if (number > this.max || number < this.min) {
            number = this.min;
        }
        this.index = number - this.min;
        this.numberView.setCurrentItem(this.index);
        this.listener.onNumberPicked(this.index, ((Integer) this.adapter.getItem(this.index)).intValue());
    }

    public void setLabel(String label) {
        this.numberView.setLabel(label);
    }

    public void show() {
        this.adapter = new NumericWheelAdapter(this.min, this.max);
        this.numberView.setAdapter(this.adapter);
        this.numberView.setCurrentItem(this.index);
    }

    public void setOnNumberPickedListener(OnNumberPickedListener listener) {
        this.listener = listener;
    }
}
