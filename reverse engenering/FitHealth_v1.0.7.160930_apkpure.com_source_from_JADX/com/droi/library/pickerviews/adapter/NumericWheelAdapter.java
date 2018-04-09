package com.droi.library.pickerviews.adapter;

public class NumericWheelAdapter implements WheelAdapter {
    public static final int DEFAULT_MAX_VALUE = 9;
    private static final int DEFAULT_MIN_VALUE = 0;
    private int maxValue;
    private int minValue;
    private int multiple;

    public NumericWheelAdapter() {
        this(0, 9);
    }

    public NumericWheelAdapter(int minValue, int maxValue) {
        this.multiple = 1;
        this.minValue = minValue;
        this.maxValue = maxValue;
    }

    public void setMultiple(int multiple) {
        this.multiple = multiple;
    }

    public Object getItem(int index) {
        if (index < 0 || index >= getItemsCount()) {
            return Integer.valueOf(0);
        }
        return Integer.valueOf(this.multiple * (this.minValue + index));
    }

    public int getItemsCount() {
        return (this.maxValue - this.minValue) + 1;
    }

    public int indexOf(Object o) {
        return ((Integer) o).intValue() - this.minValue;
    }
}
