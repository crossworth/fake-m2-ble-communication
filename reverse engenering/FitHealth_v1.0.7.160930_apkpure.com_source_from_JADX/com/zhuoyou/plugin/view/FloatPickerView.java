package com.zhuoyou.plugin.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import com.droi.library.pickerviews.adapter.ArrayWheelAdapter;
import com.droi.library.pickerviews.adapter.NumericWheelAdapter;
import com.droi.library.pickerviews.adapter.WheelAdapter;
import com.droi.library.pickerviews.lib.WheelView;
import com.droi.library.pickerviews.listener.OnItemSelectedListener;
import com.fithealth.running.R;
import java.util.ArrayList;

public class FloatPickerView extends LinearLayout {
    private View contentView;
    private OnFloatPickedListener listener;
    private int max = 100;
    private int min = 0;
    private WheelAdapter numberAdapter;
    private int numberIndex;
    private WheelView numberView;
    private WheelAdapter pointAdapter;
    private int pointIndex;
    private WheelView pointView;

    public interface OnFloatPickedListener {
        void onFloatPicked(int i, int i2, String str);
    }

    class C19041 implements OnItemSelectedListener {
        C19041() {
        }

        public void onItemSelected(int index) {
            FloatPickerView.this.numberIndex = index;
            FloatPickerView.this.doListener();
        }
    }

    class C19052 implements OnItemSelectedListener {
        C19052() {
        }

        public void onItemSelected(int index) {
            FloatPickerView.this.pointIndex = index;
            FloatPickerView.this.doListener();
        }
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
        this.contentView = LayoutInflater.from(context).inflate(R.layout.float_pickerview_layout, this, true);
        this.numberView = (WheelView) this.contentView.findViewById(R.id.number_view);
        this.pointView = (WheelView) this.contentView.findViewById(R.id.point_view);
        this.numberView.setOnItemSelectedListener(new C19041());
        this.pointView.setCyclic(false);
        this.pointView.setOnItemSelectedListener(new C19052());
    }

    private void doListener() {
        String floatStr;
        if (this.pointIndex == 0) {
            floatStr = this.numberAdapter.getItem(this.numberIndex).toString();
        } else {
            floatStr = this.numberAdapter.getItem(this.numberIndex) + "." + this.pointAdapter.getItem(this.pointIndex);
        }
        this.listener.onFloatPicked(this.numberIndex, this.pointIndex, floatStr);
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
        this.pointIndex = point / 5;
    }

    public void setFloatPicked(int number, int point) {
        if (number > this.max || number < this.min) {
            number = this.min;
        }
        this.numberIndex = number - this.min;
        this.pointIndex = point / 5;
        this.numberView.setCurrentItem(this.numberIndex);
        this.pointView.setCurrentItem(this.pointIndex);
        doListener();
    }

    public void setLabel(String label) {
        this.numberView.setLabel(".");
        this.pointView.setLabel(label);
    }

    public void show() {
        this.numberAdapter = new NumericWheelAdapter(this.min, this.max);
        this.numberView.setAdapter(this.numberAdapter);
        this.numberView.setCurrentItem(this.numberIndex);
        ArrayList<String> list = new ArrayList();
        list.add("0");
        list.add("5");
        this.pointAdapter = new ArrayWheelAdapter(list);
        this.pointView.setAdapter(this.pointAdapter);
        this.pointView.setCurrentItem(this.pointIndex);
    }

    public void setOnFloatPickedListener(OnFloatPickedListener listener) {
        this.listener = listener;
    }
}
