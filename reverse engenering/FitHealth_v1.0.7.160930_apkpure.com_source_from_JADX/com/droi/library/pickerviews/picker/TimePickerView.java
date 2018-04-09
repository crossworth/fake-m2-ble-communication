package com.droi.library.pickerviews.picker;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import com.droi.library.pickerviews.C0545R;
import com.droi.library.pickerviews.adapter.NumericWheelAdapter;
import com.droi.library.pickerviews.lib.WheelView;
import com.droi.library.pickerviews.listener.OnItemSelectedListener;
import java.text.DecimalFormat;
import java.text.Format;

public class TimePickerView extends LinearLayout implements IPickerView {
    private static Format intformat = new DecimalFormat("00");
    private NumericWheelAdapter hourAdapter;
    private int hourIndex;
    private WheelView hourView;
    private boolean isRange;
    private OnTimePickedListener listener;
    private int maxHour = 23;
    private int maxMinute = 59;
    private int minHour = 0;
    private int minMinute = 0;
    private NumericWheelAdapter minuteAdapter;
    private int minuteIndex;
    private WheelView minuteView;

    public interface OnTimePickedListener {
        void onTimePicked(int i, int i2, String str);
    }

    class C16301 implements OnItemSelectedListener {
        C16301() {
        }

        public void onItemSelected(int index) {
            TimePickerView.this.hourIndex = index;
            TimePickerView.this.initMinuteView();
            TimePickerView.this.doListener();
        }
    }

    class C16312 implements OnItemSelectedListener {
        C16312() {
        }

        public void onItemSelected(int index) {
            TimePickerView.this.minuteIndex = index;
            TimePickerView.this.doListener();
        }
    }

    public TimePickerView(Context context) {
        super(context);
        initView(context);
    }

    public TimePickerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public TimePickerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context) {
        View contentView = LayoutInflater.from(context).inflate(C0545R.layout.time_picker_layout, this, true);
        this.hourView = (WheelView) contentView.findViewById(C0545R.id.hour_view);
        this.minuteView = (WheelView) contentView.findViewById(C0545R.id.minute_view);
        this.hourView.setOnItemSelectedListener(new C16301());
        this.minuteView.setOnItemSelectedListener(new C16312());
    }

    private void initHourView() {
        this.hourAdapter = new NumericWheelAdapter(0, 23);
        this.hourView.setAdapter(this.hourAdapter);
        this.hourView.setCurrentItem(this.hourIndex);
    }

    private void initMinuteView() {
        this.minuteAdapter = new NumericWheelAdapter(0, 59);
        this.minuteView.setAdapter(this.minuteAdapter);
        this.minuteView.setCurrentItem(this.minuteIndex);
    }

    private void doListener() {
        if (this.isRange) {
            if (this.hourIndex < this.minHour) {
                setTimeIndex(this.minHour, this.minMinute);
                this.hourView.setCurrentItem(this.hourIndex);
                this.minuteView.setCurrentItem(this.minuteIndex);
            } else if (this.hourIndex == this.minHour && this.minuteIndex < this.minMinute) {
                setTimeIndex(this.minHour, this.minMinute);
                this.minuteView.setCurrentItem(this.minuteIndex);
            } else if (this.hourIndex > this.maxHour) {
                setTimeIndex(this.maxHour, this.maxMinute);
                this.hourView.setCurrentItem(this.hourIndex);
                this.minuteView.setCurrentItem(this.minuteIndex);
            } else if (this.hourIndex == this.maxHour && this.minuteIndex > this.maxMinute) {
                setTimeIndex(this.maxHour, this.maxMinute);
                this.minuteView.setCurrentItem(this.minuteIndex);
            }
        }
        if (this.listener != null) {
            this.listener.onTimePicked(this.hourIndex, this.minuteIndex, getSelectedTime());
        }
    }

    public String getSelectedTime() {
        String hour = intformat.format(this.hourAdapter.getItem(this.hourIndex));
        return hour + ":" + intformat.format(this.minuteAdapter.getItem(this.minuteIndex));
    }

    public void show() {
        initHourView();
        initMinuteView();
    }

    public void showLabel(boolean show) {
        if (show) {
            this.hourView.setLabel(C0545R.string.pickerview_hours);
            this.minuteView.setLabel(C0545R.string.pickerview_minutes);
            return;
        }
        this.hourView.setLabel(null);
        this.minuteView.setLabel(null);
    }

    public void setTextSize(int size) {
        this.hourView.setTextSize((float) size);
        this.minuteView.setTextSize((float) size);
    }

    public void setTextColorOut(int textColorOut) {
        this.hourView.setTextColorOut(textColorOut);
        this.minuteView.setTextColorOut(textColorOut);
    }

    public void setTextColorCenter(int textColorCenter) {
        this.hourView.setTextColorCenter(textColorCenter);
        this.minuteView.setTextColorCenter(textColorCenter);
    }

    public void setDividerColor(int dividerColor) {
        this.hourView.setDividerColor(dividerColor);
        this.minuteView.setDividerColor(dividerColor);
    }

    public void setLabelColor(int labelColor) {
        this.hourView.setLabelColor(labelColor);
        this.minuteView.setLabelColor(labelColor);
    }

    public void setLineSpacingMultiplier(float space) {
        this.hourView.setLineSpacingMultiplier(space);
        this.minuteView.setLineSpacingMultiplier(space);
    }

    public void setTypeface(Typeface type) {
        this.hourView.setTypeface(type);
        this.minuteView.setTypeface(type);
    }

    public void setDividerWidth(float width) {
        this.hourView.setDividerWidth(width);
        this.minuteView.setDividerWidth(width);
    }

    public void setTimeRange(int minHour, int minMinute, int maxHour, int maxMinute) {
        this.isRange = true;
        this.minHour = minHour;
        this.minMinute = minMinute;
        this.maxHour = maxHour;
        this.maxMinute = maxMinute;
    }

    public void setInitTimeIndex(int hourIndex, int minuteIndex) {
        this.hourIndex = hourIndex;
        this.minuteIndex = minuteIndex;
    }

    public void setTimeIndex(int hourIndex, int minuteIndex) {
        this.hourIndex = hourIndex;
        this.minuteIndex = minuteIndex;
        this.hourView.setCurrentItem(hourIndex);
        this.minuteView.setCurrentItem(minuteIndex);
        doListener();
    }

    public void setOnTimePickedListener(OnTimePickedListener listener) {
        this.listener = listener;
    }
}
