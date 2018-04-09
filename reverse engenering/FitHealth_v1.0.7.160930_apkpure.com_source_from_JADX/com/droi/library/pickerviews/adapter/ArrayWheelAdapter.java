package com.droi.library.pickerviews.adapter;

import java.util.ArrayList;

public class ArrayWheelAdapter<T> implements WheelAdapter {
    public static final int DEFAULT_LENGTH = 4;
    private ArrayList<T> items;
    private int length;

    public ArrayWheelAdapter(ArrayList<T> items, int length) {
        this.items = items;
        this.length = length;
    }

    public ArrayWheelAdapter(ArrayList<T> items) {
        this(items, 4);
    }

    public Object getItem(int index) {
        if (index < 0 || index >= this.items.size()) {
            return "";
        }
        return this.items.get(index);
    }

    public int getItemsCount() {
        return this.items.size();
    }

    public int indexOf(Object o) {
        return this.items.indexOf(o);
    }
}
