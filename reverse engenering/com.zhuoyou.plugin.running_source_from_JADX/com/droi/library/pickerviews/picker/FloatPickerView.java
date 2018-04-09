package com.droi.library.pickerviews.picker;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import com.droi.library.pickerviews.C0738R;
import com.droi.library.pickerviews.adapter.NumericWheelAdapter;
import com.droi.library.pickerviews.adapter.WheelAdapter;
import com.droi.library.pickerviews.lib.WheelView;
import com.droi.library.pickerviews.listener.OnItemSelectedListener;
import com.tencent.open.yyb.TitleBar;

public class FloatPickerView extends LinearLayout implements IPickerView {
    private OnFloatPickedListener listener;
    private int max = 100;
    private int min = 0;
    private WheelAdapter numberAdapter;
    private int numberIndex;
    private WheelView numberView;
    private WheelAdapter pointAdapter;
    private int pointIndex;
    private WheelView pointView;

    class C07451 implements OnItemSelectedListener {
        C07451() {
        }

        public void onItemSelected(int index) {
            FloatPickerView.this.numberIndex = index;
            FloatPickerView.this.doListener();
        }
    }

    class C07462 implements OnItemSelectedListener {
        C07462() {
        }

        public void onItemSelected(int index) {
            FloatPickerView.this.pointIndex = index;
            FloatPickerView.this.doListener();
        }
    }

    public interface OnFloatPickedListener {
        void onFloatPicked(int i, int i2, String str);
    }

    public FloatPickerView(Context context) {
        super(context);
        initView(context);
    }

    public FloatPickerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public FloatPickerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context) {
        View contentView = LayoutInflater.from(context).inflate(C0738R.layout.float_picker_layout, this, true);
        this.numberView = (WheelView) contentView.findViewById(C0738R.id.number_view);
        this.pointView = (WheelView) contentView.findViewById(C0738R.id.point_view);
        this.numberView.setOnItemSelectedListener(new C07451());
        this.pointView.setOnItemSelectedListener(new C07462());
    }

    private void doListener() {
        if (this.listener != null) {
            this.listener.onFloatPicked(this.numberIndex, this.pointIndex, getFloatStr());
        }
    }

    private String getFloatStr() {
        if (this.pointIndex == 0) {
            return this.numberAdapter.getItem(this.numberIndex).toString();
        }
        return this.numberAdapter.getItem(this.numberIndex) + "." + this.pointAdapter.getItem(this.pointIndex);
    }

    public void setNumberRange(int min, int max) {
        this.min = min;
        this.max = max;
    }

    public void setInitFloatPicked(int number, int point) {
        if (number > this.max || number < this.min) {
            number = this.min;
        }
        this.numberIndex = number - this.min;
        this.pointIndex = point;
    }

    public void setInitFloatPicked(float number) {
        setInitFloatPicked((int) number, ((int) (TitleBar.SHAREBTN_RIGHT_MARGIN * number)) % 10);
    }

    public void setFloatPicked(int number, int point) {
        if (number > this.max || number < this.min) {
            number = this.min;
        }
        this.numberIndex = number - this.min;
        this.pointIndex = point;
        this.numberView.setCurrentItem(this.numberIndex);
        this.pointView.setCurrentItem(this.pointIndex);
        doListener();
    }

    public void setFloatPicked(float number) {
        setFloatPicked((int) number, ((int) (TitleBar.SHAREBTN_RIGHT_MARGIN * number)) % 10);
    }

    public void setLabel(String label) {
        this.numberView.setLabel(".");
        this.pointView.setLabel(label);
    }

    public void show() {
        this.numberAdapter = new NumericWheelAdapter(this.min, this.max);
        this.numberView.setAdapter(this.numberAdapter);
        this.numberView.setCurrentItem(this.numberIndex);
        this.pointAdapter = new NumericWheelAdapter(0, 9);
        this.pointView.setAdapter(this.pointAdapter);
        this.pointView.setCurrentItem(this.pointIndex);
    }

    public float getSelectedFloat() {
        return Float.parseFloat(getFloatStr());
    }

    public void showLabel(boolean show) {
    }

    public void setTextSize(int size) {
        this.numberView.setTextSize((float) size);
        this.pointView.setTextSize((float) size);
    }

    public void setTextColorOut(int textColorOut) {
        this.numberView.setTextColorOut(textColorOut);
        this.pointView.setTextColorOut(textColorOut);
    }

    public void setTextColorCenter(int textColorCenter) {
        this.numberView.setTextColorCenter(textColorCenter);
        this.pointView.setTextColorCenter(textColorCenter);
    }

    public void setDividerColor(int dividerColor) {
        this.numberView.setDividerColor(dividerColor);
        this.pointView.setDividerColor(dividerColor);
    }

    public void setLabelColor(int labelColor) {
        this.numberView.setLabelColor(labelColor);
        this.pointView.setLabelColor(labelColor);
    }

    public void setLineSpacingMultiplier(float space) {
        this.numberView.setLineSpacingMultiplier(space);
        this.pointView.setLineSpacingMultiplier(space);
    }

    public void setTypeface(Typeface type) {
        this.numberView.setTypeface(type);
        this.pointView.setTypeface(type);
    }

    public void setDividerWidth(float width) {
        this.numberView.setDividerWidth(width);
        this.pointView.setDividerWidth(width);
    }

    public void setOnFloatPickedListener(OnFloatPickedListener listener) {
        this.listener = listener;
    }
}
