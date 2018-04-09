package com.droi.library.pickerviews.picker;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import com.droi.library.pickerviews.C0738R;
import com.droi.library.pickerviews.adapter.NumericWheelAdapter;
import com.droi.library.pickerviews.lib.WheelView;
import com.droi.library.pickerviews.listener.OnItemSelectedListener;

public class NumberPickerView extends LinearLayout implements IPickerView {
    private NumericWheelAdapter adapter;
    private int index;
    private OnNumberPickedListener listener;
    private int max = 100;
    private int min = 0;
    private int multiple = 1;
    private WheelView numberView;

    class C07471 implements OnItemSelectedListener {
        C07471() {
        }

        public void onItemSelected(int index) {
            NumberPickerView.this.index = index;
            NumberPickerView.this.doListener();
        }
    }

    public interface OnNumberPickedListener {
        void onNumberPicked(int i, int i2);
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
        this.numberView = (WheelView) LayoutInflater.from(context).inflate(C0738R.layout.number_picker_layout, this, true).findViewById(C0738R.id.number_view);
        this.numberView.setOnItemSelectedListener(new C07471());
    }

    private void doListener() {
        if (this.listener != null) {
            this.listener.onNumberPicked(this.index, getSelectedNumber());
        }
    }

    public int getSelectedNumber() {
        return this.index + this.min;
    }

    public void setNumberRange(int min, int max) {
        this.min = min;
        this.max = max;
    }

    public void setMultiple(int multiple) {
        this.multiple = multiple;
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
        doListener();
    }

    public void setLabel(String label) {
        this.numberView.setLabel(label);
    }

    public void show() {
        this.adapter = new NumericWheelAdapter(this.min, this.max);
        this.adapter.setMultiple(this.multiple);
        this.numberView.setAdapter(this.adapter);
        this.numberView.setCurrentItem(this.index);
    }

    public void showLabel(boolean show) {
    }

    public void setTextSize(int size) {
        this.numberView.setTextSize((float) size);
    }

    public void setTextColorOut(int textColorOut) {
        this.numberView.setTextColorOut(textColorOut);
    }

    public void setTextColorCenter(int textColorCenter) {
        this.numberView.setTextColorCenter(textColorCenter);
    }

    public void setDividerColor(int dividerColor) {
        this.numberView.setDividerColor(dividerColor);
    }

    public void setLabelColor(int labelColor) {
        this.numberView.setLabelColor(labelColor);
    }

    public void setLineSpacingMultiplier(float space) {
        this.numberView.setLineSpacingMultiplier(space);
    }

    public void setTypeface(Typeface type) {
        this.numberView.setTypeface(type);
    }

    public void setDividerWidth(float width) {
        this.numberView.setDividerWidth(width);
    }

    public void setOnNumberPickedListener(OnNumberPickedListener listener) {
        this.listener = listener;
    }

    public WheelView getWheelView() {
        return this.numberView;
    }
}
